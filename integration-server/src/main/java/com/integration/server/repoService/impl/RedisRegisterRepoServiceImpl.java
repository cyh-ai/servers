package com.integration.server.repoService.impl;

import com.integration.core.util.CopyUtil;
import com.integration.server.dto.redisCache.RedisRegisterDTO;
import com.integration.server.eneity.RedisRegister;
import com.integration.server.repoService.RedisRegisterRepoService;
import com.integration.server.repository.RedisRegisterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author cyh
 */
@Service
public class RedisRegisterRepoServiceImpl implements RedisRegisterRepoService {

    @Autowired
    RedisRegisterRepository redisRegisterRepository;

    @Override
    public Iterable<RedisRegisterDTO> findAll() {
        return CopyUtil.convertList(redisRegisterRepository.findAll(), this::toDTO);
    }

    private RedisRegisterDTO toDTO(RedisRegister redisRegister) {
        return CopyUtil.convert(redisRegister, RedisRegisterDTO.class);
    }
}
