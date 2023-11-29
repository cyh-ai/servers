package com.integration.server.service.impl;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.excp.FaInsExcept;
import com.integration.core.service.RedisService;
import com.integration.core.util.StringUtils;
import com.integration.server.dto.redisCache.RedisRegisterDTO;
import com.integration.server.repoService.RedisRegisterRepoService;
import com.integration.server.service.RedisRegisterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author cyh
 * 缓存维护
 */
@Service
public class RedisRegisterServiceImpl implements RedisRegisterService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final static String ALL = "all";
    private final static String REFRESH = "refresh";

    @Autowired
    RedisService redisService;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    RedisRegisterRepoService redisRegisterRepoService;


    @Override
    public List<RedisRegisterDTO> findAllRedisRegister() {
        List<RedisRegisterDTO> redisRegisterList = new ArrayList<>();
        redisRegisterRepoService.findAll().forEach(redisRegisterList::add);
        return redisRegisterList;
    }

    @Override
    public void clearRedisRegister(String business, String subBusiness) {
        if (StringUtils.isBlank(business) || StringUtils.isBlank(subBusiness)) {
            throw new FaInsExcept(ErrorConstans.REFERENCE_PARAMS_CHECK, "入参校验不通过");
        }
        String pattern = business + ":" + subBusiness + "*";
        List<String> patterns = new ArrayList<>();
        patterns.add(pattern);

        //刷新所有缓存
        if (ALL.equals(business) && REFRESH.equals(subBusiness)) {
            //方法
            return;
        }


        //删除所有缓存
        if (ALL.equals(business) && ALL.equals(subBusiness)) {
            patterns = findAllRedisRegister().stream().filter(this::isNotSpecialTreatmentKey).map(redisRegister ->
                    redisRegister.getBusiness() + ":" + redisRegister.getSubBusiness() + "*"
            ).collect(Collectors.toList());
        }
        patterns.forEach(this::deleteKeys);


    }

    public boolean isNotSpecialTreatmentKey(RedisRegisterDTO redisRegisterDTO) {
        return !ALL.equals(redisRegisterDTO.getBusiness());
    }

    public void deleteKeys(String patten) {
        Set<Object> keys = keys(patten);
        for (Object key : keys) {
            try {
                redisService.delete((String) key);
            } catch (Exception e) {
                logger.error("redis 删除操作发生异常", e);
            }
        }
    }


    private Set<Object> keys(String patten){
        return redisTemplate.keys(patten);
    }
}
