package com.integration.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;

/**
 * @author cyh
 * 数据源配置
 */
@Configuration
@EnableTransactionManagement(order = 2)
public class DataSourceConfig {
    @Resource
    private DruidProperties druidProperties;

    /**
     * 单数据源连接池配置
     */
    @Bean
    public DruidDataSource singleDatasource() {
        DruidDataSource dataSource = new DruidDataSource();
        druidProperties.config(dataSource);
        return dataSource;
    }
}