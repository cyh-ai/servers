package com.integration.core.reposervice.impl;

import com.integration.core.Repository.PropertiesRepository;
import com.integration.core.config.PropertiesEnvironment;
import com.integration.core.dto.PropertiesDTO;
import com.integration.core.entity.Properties;
import com.integration.core.reposervice.PropertiesRepoService;
import com.integration.core.reposervice.support.SimpleRepoServiceSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PropertiesRepoServiceImpl extends SimpleRepoServiceSupport<PropertiesDTO, Properties> implements PropertiesRepoService {


    @Autowired
    PropertiesRepository propertiesRepository;
    @Autowired
    PropertiesEnvironment propertiesEnvironment;

    public PropertiesRepoServiceImpl() {
        super(PropertiesDTO.class);
    }

    @Override
    public Optional<PropertiesDTO> findByKey(String key) {
        Properties byKeyAndEnvironment = propertiesRepository.findByKeyAndEnvironment(key, propertiesEnvironment.getEnvironment());
        return Optional.ofNullable(populateDTO(byKeyAndEnvironment));
    }
}
