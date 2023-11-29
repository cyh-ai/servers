package com.integration.server.repoService;

import com.integration.server.dto.redisCache.RedisRegisterDTO;

/**
 * @author cyh
 */
public interface RedisRegisterRepoService {

    /**
     * 所有数据
     * @return 所有数据
     */
    Iterable<RedisRegisterDTO> findAll();

}
