package com.integration.server.service;

import com.integration.server.dto.redisCache.RedisRegisterDTO;

import java.util.List;

/**
 * @author cyh
 * 缓存维护
 */
public interface RedisRegisterService {

    /**
     * 查询所有缓存项
     * @return 所有缓存项
     */
    List<RedisRegisterDTO> findAllRedisRegister();




    /**
     * 清空/刷新缓存
     *
     * @param business    大类
     * @param subBusiness 小类
     */
    void clearRedisRegister(String business, String subBusiness);
}
