package com.integration.core.service;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.Enum.RedisKeyEnum;
import com.integration.core.excp.FaInsExcept;
import com.integration.core.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;
import java.util.concurrent.TimeUnit;

/**
 * @author cyh
 * redis工具类
 */
@Component
public class RedisService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SPLIT_COLON = ":";


    @Autowired
    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造方法
     *
     * @param redisTemplate 变量
     */
    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


    /**
     * 根据key获取redis缓存中的数据
     *
     * @param key 唯一值
     * @return 对应的数据
     */
    public Object get(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("RedisService get error: {}", e.getMessage(), e);
            return null;
        }
    }

    /**
     * 根据业务键和编码生成key - prefix:businessKey:code
     *
     * @param business    业务大类
     * @param subBusiness 业务键
     * @param code        业务编码
     * @return 组装好的key
     */
    public String generateKey(String business, String subBusiness, String code) {
        return business + SPLIT_COLON + subBusiness + SPLIT_COLON + code;
    }

    /**
     * 存入redis（有相关时效时间和时间单位）
     *
     * @param key      唯一值
     * @param value    要存的数据
     * @param timeout  时效时间
     * @param timeUnit 时效时间单位
     * @return 是否存成功
     */
    public boolean set(String key, Object value, Long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
            return true;
        } catch (Exception e) {
            logger.error("RedisService set error: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 存入redis
     *
     * @param key   唯一值
     * @param value 要存的数据
     * @return 是否存成功
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            logger.error("RedisService set error: {}", e.getMessage(), e);
            return false;
        }
    }


    /**
     * 删除某个key
     *
     * @param key 要删除的key
     */
    public void delete(String key) {
        //判断这个key是否存在
        if (this.hasKey(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断redis中某个key是否存在
     *
     * @param key 谓唯一值
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 拼接key，有redis枚举业务大类，小类，多个指定的标识组成  business:subBusiness:(String... params)
     *
     * @param keyE   redis枚举类
     * @param params 多个指定的标识
     * @return 拼接好的key
     */
    public String generateKey(RedisKeyEnum keyE, String... params) {
        StringBuilder sb = new StringBuilder();
        sb.append(keyE.getBusiness()).append(SPLIT_COLON).append(keyE.getSubBusiness());
        if (null != params) {
            for (String param : params) {
                sb.append(SPLIT_COLON).append(param);
            }
        }
        return sb.toString();
    }


    /**
     * key为层级多的缓存方法（多个标记组成的key） business:subBusiness:(String... params)
     * 存redis 调用this.set方法，先拼接key，有redis枚举业务大类，小类，多个指定标识组成，然后存储，不包含时效时间和时间单位
     *
     * @param keyE   redis枚举类
     * @param value  要存的数据
     * @param params 多个指定的标识
     * @return 是否成功
     */
    public boolean set(RedisKeyEnum keyE, Object value, String... params) {
        return this.set(keyE, value, null, null, params);
    }

    /**
     * key为层级多的缓存方法（多个标记组成的key） business:subBusiness:(String... params)
     * 存redis 先拼接key，有redis枚举业务大类，小类，多个指定标识组成，然后存储，可设置时效时间和时间单位
     *
     * @param keyE     redis枚举类
     * @param value    要存的数据
     * @param timeout  时效时间
     * @param timeUnit 时效时间单位
     * @param params   多个指定的标识
     * @return 是否成功
     */
    public boolean set(RedisKeyEnum keyE, Object value, Long timeout, TimeUnit timeUnit, String... params) {
        try {
            String key = this.generateKey(keyE, params);
            redisTemplate.opsForValue().set(key, value);
            if (null != timeUnit && null != timeout) {
                redisTemplate.expire(key, timeout, timeUnit);
            } else {
                redisTemplate.expire(key, keyE.getTime(), keyE.getTimeUnit());
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisService set error: {}", e.getMessage(), e);
            return false;
        }
    }


    /**
     * key为层级多的获取方法（多个标记组成的key） business:subBusiness:(String... params)
     * 根据组装好的key获取redis缓存中的数据
     *
     * @param keyE   redis枚举类
     * @param params 多个指定的标识
     * @return 对应的数据
     */
    public Object get(RedisKeyEnum keyE, String... params) {
        String key = this.generateKey(keyE, params);
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            logger.error("RedisService get error: {}", e.getMessage(), e);
            return null;
        }
    }


    /**
     * redis锁，可用于判断接口是否重复调用
     *
     * @param lock    指定标识，key的一部分
     * @param seconds 生成key时的时效时间计算使用
     * @return 返回一个redis分布式锁对象
     */
    public RedisLock tryLock(String lock, Long seconds) {
        String key = "lock:" + lock;
        long now = System.currentTimeMillis();
        //重新设置key对应的值，如果存在返回false，否则返回true
        Boolean getRs = redisTemplate.opsForValue().setIfAbsent(key, now, Duration.ofSeconds(seconds));
        return new RedisLock() {
            // 锁获取结果
            public boolean successGet() {
                return getRs != null && getRs;
            }

            // 释放锁
            public void releaseLock() {
                if (successGet()) {
                    redisTemplate.delete(key);
                }
            }

            // 续期
            public void lease(long leaseSec) {
                if (successGet()) {
                    Long ttl = redisTemplate.getExpire(key);
                    long sec = (leaseSec > 0 ? leaseSec : seconds);
                    if (ttl != null && ttl < sec) {
                        redisTemplate.opsForValue().set(key, now, Duration.ofSeconds(sec));
                    }
                }
            }
        };
    }


    /**
     * 删除hashmap相应值
     * 删除key对应的map集合中指定的一个或多个键值对数据
     *
     * @param key      唯一值
     * @param hashKeys redis中缓存的map数据中一个或多个键的值
     */
    public void delete(String key, String... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 获取hashmap所有entry
     * 根据key获取redis缓存中的数据
     *
     * @param key 唯一值
     * @return 对应的数据
     */
    public Map<Object, Object> entries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * key为层级多的获取方法（多个标记组成的key） business:subBusiness:(String... params)
     * 删除某个key
     *
     * @param keyE   redis枚举类
     * @param params 多个指定的标识
     */
    public void delete(RedisKeyEnum keyE, String... params) {
        String key = this.generateKey(keyE, params);
        redisTemplate.delete(key);
    }


    /**
     * 获取hashmap所有entry
     * key为层级多的获取方法（多个标记组成的key） business:subBusiness:(String... params)
     * 根据key获取redis缓存中的数据
     *
     * @param keyE   redis枚举类
     * @param params 多个指定的标识
     * @return 对应的数据
     */
    public Map<Object, Object> entries(RedisKeyEnum keyE, String... params) {
        String key = this.generateKey(keyE, params);
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * key为层级多的缓存方法（多个标记组成的key） business:subBusiness:(String... params)
     * 存redis 调用this.hsetAll方法，先拼接key，有redis枚举业务大类，小类，多个指定标识组成，然后存储，不包含时效时间和时间单位
     *
     * @param keyE   redis枚举类
     * @param map    要缓存的数据（map）
     * @param params 多个指定的标识
     * @return 是否成功
     */
    public boolean hSetAll(RedisKeyEnum keyE, Map<Object, Object> map, String... params) {
        return this.hSetAll(keyE, map, null, null, params);
    }

    /**
     * key为层级多的缓存方法（多个标记组成的key） business:subBusiness:(String... params)
     * 存redis 先拼接key，有redis枚举业务大类，小类，多个指定标识组成，然后存储，可设置时效时间和时间单位
     *
     * @param keyE     redis枚举类
     * @param map      要缓存的数据（map）
     * @param timeout  时效时间
     * @param timeUnit 时效时间单位
     * @param params   多个指定的标识
     * @return 是否成功
     */
    public boolean hSetAll(RedisKeyEnum keyE, Map<Object, Object> map, Long timeout, TimeUnit timeUnit, String... params) {
        try {
            String key = this.generateKey(keyE, params);
            redisTemplate.opsForHash().putAll(key, map);
            if (null != timeUnit && null != timeout) {
                redisTemplate.expire(key, timeout, timeUnit);
            } else {
                redisTemplate.expire(key, keyE.getTime(), keyE.getTimeUnit());
            }
            return true;
        } catch (Exception e) {
            logger.error("RedisService hsetAll error: {}", e.getMessage(), e);
            return false;
        }
    }


    //   ---------------------------------------------------------------map集合的所有类型所用方法-----------------------------------------------

    /**
     * hashmap存值并设置有效期
     * 存redis 根据进行存储，可设置时效时间和时间单位
     *
     * @param key      唯一值
     * @param map      要缓存的数据（map）
     * @param timeout  时效时间
     * @param timeUnit 时效时间单位
     * @return 是否成功
     */
    public boolean set(String key, Map map, Long timeout, TimeUnit timeUnit) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            redisTemplate.expire(key, timeout, timeUnit);
            return true;
        } catch (Exception e) {
            logger.error("RedisService set error: {}", e.getMessage(), e);
            return false;
        }
    }


    /**
     * hashmap存值并设置有效期
     * 存redis 根据进行存储，不可设置时效时间和时间单位
     *
     * @param key 唯一值
     * @param map 要缓存的数据（map）
     * @return 是否成功
     */
    public boolean set(String key, Map map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error("RedisService set error: {}", e.getMessage(), e);
            return false;
        }
    }

    /**
     * 获取hashmap存值
     * 根据key获取对应的map集合中指定的键的值
     *
     * @param key     唯一值
     * @param hashKey map中的键的值
     * @return 对应键的数据
     */
    public Object get(String key, String hashKey) {
        try {
            return redisTemplate.opsForHash().get(key, hashKey);
        } catch (Exception e) {
            logger.error("RedisService get error: {}", e.getMessage(), e);
            return null;
        }
    }


    /**
     * 刷新某个key失效时间
     *
     * @param key      唯一值
     * @param timeout  时效时间
     * @param timeUnit 时效时间单位
     */
    public void expireTime(String key, Long timeout, TimeUnit timeUnit) {
        redisTemplate.expire(key, timeout, timeUnit);
    }


    /**
     * 按照 business：subBusiness：s1_s2_s3 格式拼接出一个字符串
     *
     * @param business    大类值
     * @param subBusiness 小类值
     * @param s           一个或多个指定的值
     * @return 拼接好的字符串
     */
    public static String generateKey(String business, String subBusiness, String... s) {
        String result = business + SPLIT_COLON + subBusiness;
        if (null != s) {
            result += SPLIT_COLON;
            StringJoiner stringJoiner = new StringJoiner("_");
            for (String element : s) {
                stringJoiner.add(element);
            }
            result += stringJoiner;
        }
        return result;
    }


    /**
     * 将某个key的值增加1 当当前key不在时,创建并且赋值1，当前key存在时，将当前key对应的值+1并返回
     * 通过increment(K key, long delta)方法以增量方式存储long值（正值则自增，负值则自减）
     *
     * @param key 唯一值
     * @return 增量的long值
     */
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key, 1L);
    }

    /**
     * 根据key和field批量查询
     *
     * @param key       键
     * @param fieldList 域
     * @return Map
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getHashBatch(String key, List<String> fieldList) {
        Map<String, Object> resultMap = new HashMap<>();
        List<Object> valueList = redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            RedisSerializer redisSerializer = redisTemplate.getStringSerializer();
            byte[] serializeKey = redisSerializer.serialize(key);
            if (serializeKey != null) {
                fieldList.forEach(field -> {
                    byte[] serializeField = redisSerializer.serialize(field);
                    if (serializeField != null) {
                        redisConnection.hGet(serializeKey, serializeField);
                    }
                });
            }
            return null;
        }, redisTemplate.getValueSerializer());

        if (CollectionUtils.isNotEmpty(valueList)) {
            for (int i = 0; i < valueList.size(); i++) {
                resultMap.put(fieldList.get(i), valueList.get(i));
            }
        }

        return resultMap;
    }


    /**
     * 根据枚举类和params数据，组装多个key
     *
     * @param keyE   redis枚举类
     * @param params 一个或多个指定的标识
     * @return 对应key集合
     */
    public List<Object> generateMultiKeys(RedisKeyEnum keyE, Object... params) {
        if (null == params) {
            throw new FaInsExcept(ErrorConstans.REFERENCE_CORE_UTIL_CHECK, "params不能为空");
        }
        List<Object> keys = new ArrayList<>();
        for (Object param : params) {
            StringBuilder sb = new StringBuilder();
            sb.append(keyE.getBusiness()).append(SPLIT_COLON).append(keyE.getSubBusiness());
            sb.append(SPLIT_COLON).append(param);
            keys.add(sb.toString());
        }
        return keys;
    }

//    public List<Object> multiGet(RedisKeyEnum keyE, Object... params) {
//        List<Object> keys = this.generateMultiKeys(keyE, params);
//        return redisTemplate.opsForValue().multiGet(keys);
//    }


    /**
     * scan命令
     *
     * @param matchKey
     * @param count
     * @return
     */
    public Set<String> scan(String matchKey, long count) {
        return redisTemplate.execute((RedisCallback<Set<String>>) connection -> {
            Set<String> keys = new HashSet<>();
            Cursor<byte[]> cursor = connection.scan(new ScanOptions.ScanOptionsBuilder()
                    .match(matchKey + "*").count(count).build());
            while (cursor.hasNext()) {
                keys.add(new String(cursor.next()));
            }
            return keys;
        });
    }

    /**
     * 批量执行reids删除命令
     */
    public void executePipelined(Collection<String> collection) {
        redisTemplate.executePipelined(new RedisCallback() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                collection.forEach(key -> {
                    redisConnection.del(key.getBytes());
                });
                return null;
            }
        });
    }

//    /**
//     * 获取列表元素（分页）
//     *
//     * @param key         redis key
//     * @param currentPage 当前页
//     * @param pageSize    每页条数
//     * @return Page
//     */
//    public Page<Object> getPage(String key, long currentPage, long pageSize) {
//        ListOperations<Object, Object> operations = redisTemplate.opsForList();
//
//        Page<Object> page = new Page<>();
//        long totalSize = operations.size(key);
//        page.setTotalSize(totalSize);
//
//        long mod = totalSize & (pageSize - 1);
//        page.setTotalPage(totalSize / pageSize + (mod > 0 ? 1 : 0));
//
//        long start = pageSize * (currentPage - 1) - 1;
//        long end = start + pageSize - 1;
//        page.setData(operations.range(key, start, end));
//
//        return page;
//    }

//    /**
//     * 获取列表元素（分页）
//     *
//     * @param key  redis key
//     * @param data 列表数据
//     */
//    public void setList(String key, List<?> data) {
//        redisTemplate.delete(key);
//        redisTemplate.opsForList().rightPushAll(key, data);
//    }

//    /**
//     * 获取列表元素（分页）
//     *
//     * @param key redis key
//     */
//    public List<Object> getList(String key) {
//        long size = redisTemplate.opsForList().size(key);
//        return redisTemplate.opsForList().range(key, 0, size - 1);
//    }
//
//    public Long getExpire(String key) {
//        return redisTemplate.getExpire(key);
//    }

}
