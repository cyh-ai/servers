package com.integration.core.service;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.Enum.RedisKeyEnum;
import com.integration.core.excp.FaInsExcept;
import com.integration.core.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.DataType;
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
 */
@Component
public class RedisService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String SPLIT_COLON = ":";


    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }


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
     * @return String
     */
    public String generateKey(String business, String subBusiness, String code) {
        return business + SPLIT_COLON + subBusiness + SPLIT_COLON + code;
    }

    /**
     * 存入redis
     *
     * @param key
     * @param value
     * @param timeout  失效时间
     * @param timeUnit 时效时间单位
     * @return
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
     * @param key
     */
    public void delete(String key) {
        if (this.hasKey(key)){
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断redis中某个key是否存在
     *
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 多个标识key组装方法
     * @param keyE
     * @param params
     * @return
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
     * 层级多的缓存方法（多个标记组成的key）
     * @param keyE
     * @param value
     * @param params
     * @return
     */
    public boolean set(RedisKeyEnum keyE, Object value, String... params) {
        return this.set(keyE, value, null, null, params);
    }

    public boolean set(RedisKeyEnum keyE, Object value, TimeUnit timeUnit, Long timeout, String... params) {
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
     * 层级多的获取缓存（多个标记组成的key）
     * @param keyE
     * @param params
     * @return
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
     * @param lock
     * @param seconds
     * @return
     */
    public RedisLock tryLock(String lock, Long seconds) {
        String key = "lock:" + lock;
        long now = System.currentTimeMillis();
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
     *
     * @param key
     * @param hashKeys
     */
    public void delete(String key, String... hashKeys) {
        redisTemplate.opsForHash().delete(key, hashKeys);
    }

    /**
     * 获取hashmap所有entry
     *
     * @param key
     * @return
     */
    public Map<Object, Object> entries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    public void delete(RedisKeyEnum keyE, String... params) {
        String key = this.generateKey(keyE, params);
        redisTemplate.delete(key);
    }





    public Map<Object, Object> entries(RedisKeyEnum keyE, String... params) {
        String key = this.generateKey(keyE, params);
        return redisTemplate.opsForHash().entries(key);
    }

    public boolean hsetAll(RedisKeyEnum keyE, Map<Object, Object> map, String... params) {
        return this.hsetAll(keyE, map, null, null, params);
    }

    /**
     * map集合 保存缓存
     * @param keyE
     * @param map
     * @param timeUnit
     * @param timeout
     * @param params
     * @return
     */
    public boolean hsetAll(RedisKeyEnum keyE, Map<Object, Object> map, TimeUnit timeUnit, Long timeout, String... params) {
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



    //   map集合的所有类型所用方法

    /**
     * hashmap存值并设置有效期
     *
     * @param key
     * @param map
     * @param timeout
     * @param timeUnit
     * @return
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
     *
     * @param key
     * @param hashKey map中的key
     * @return
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
     * @param key
     * @param timeUnit
     * @param timeout
     */
    public void expireTime(String key, TimeUnit timeUnit, Long timeout) {
        redisTemplate.expire(key, timeout, timeUnit);
    }


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
     *
     * @param key
     * @return
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
