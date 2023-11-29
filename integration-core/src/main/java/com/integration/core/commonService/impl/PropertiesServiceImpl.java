package com.integration.core.commonService.impl;

import com.integration.core.commonService.PropertiesService;
import com.integration.core.dto.PropertiesDTO;
import com.integration.core.entity.Properties;
import com.integration.core.reposervice.PropertiesRepoService;
import com.integration.core.service.RedisService;
import com.integration.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cyh
 */
@Service
public class PropertiesServiceImpl implements PropertiesService {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public static final String PROPERTIES_PREFIX = "Properties";

    @Autowired
    RedisService redisService;
    @Autowired
    PropertiesRepoService propertiesRepoService;

    @Override
    public String findOneByKey(String key, String hashKey) {
        String propertyValue = (String) redisService.get(generateKey(hashKey));
        if (propertyValue == null) {
            propertyValue = findDbByKeyEnabled(hashKey);
        }
        if (propertyValue == null) {
            logger.error("{} 未配置，请仔细检查配置", StringUtils.isNotBlank(hashKey) ? key + ":" + hashKey : key);
            throw new RuntimeException("数据库未配置，请检查数据库配置");
        }
        return propertyValue;
    }



    public String findDbByKeyEnabled(String key){
        String propertyValue = propertiesRepoService.findByKey(key).map(PropertiesDTO::getValue).orElse(null);
        if (StringUtils.isNotBlank(propertyValue)){
            redisService.set(generateKey(key),propertyValue);
        }
        logger.info("find Property from db completed");
        return propertyValue;
    }












    private String generateKey(String key) {
        return PROPERTIES_PREFIX + ":" + key;
    }
}
