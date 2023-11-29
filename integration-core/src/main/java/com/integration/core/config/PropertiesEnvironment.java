package com.integration.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author cyh
 * 系统环境变量
 */
@Configuration
@ConfigurationProperties(prefix = PropertiesEnvironment.PREFIX,ignoreInvalidFields = true)
public class PropertiesEnvironment {

    public static final String PREFIX = "property";

    private String environment;


    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
