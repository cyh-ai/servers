package com.integration.core.reposervice;

import com.integration.core.dto.PropertiesDTO;

import java.util.Optional;

public interface PropertiesRepoService {

    Optional<PropertiesDTO> findByKey(String key);
}
