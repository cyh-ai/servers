package com.integration.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * 2.创建swaggerConfig类
 * swagger配置类
 *
 * @author 23921
 * Configuration 添加到配置里面
 */
@Configuration
@EnableSwagger2 //开启swagger2
//@ConditionalOnProperty(prefix = "swagger",value = {"show"},havingValue = "true")
public class SwaggerConfig {


    @Value("${swagger.show}")
    private Boolean isShow;

    /**
     * 作者信息
     */
    public static final Contact DEFAULT_CONTACT = new Contact("程亚辉", "http://localhost:8081/swagger-ui.html#/", "@163.com");

    /**
     * 配置swagger的bean实例
     *
     * @param environment 对象
     * @return 数据
     */
    @Bean
    public Docket docket(Environment environment) {
        Docket docket = new Docket(DocumentationType.SWAGGER_2);
        //获取项目的环境
        if (isShow) {

            //配置swagger信息
            docket.apiInfo(apiInfo());
            //工厂模式
            //启用swagger,根据flag
            docket.enable(true)
                    .select()
                    //配置要扫描接口的方式
                    //basePackage 指定要扫描的包
                    //withClassAnnotation 扫描类上的注解
                    //.apis(RequestHandlerSelectors.withClassAnnotation(GetMapping.class)).build();
                    //.paths()  过滤
                    .apis(RequestHandlerSelectors.basePackage("com.integration"))
                    .build();
            //any扫描全部   none 都不扫描
            //.apis(RequestHandlerSelectors.any()).build();
            return docket;
        } else {
            docket.enable(false);
        }
        return docket;
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "程亚辉的接口文档",
                "介绍：飞了",
                "版本1.0",
                "http://localhost:8081/swagger-ui.html#/",
                DEFAULT_CONTACT,
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList()
        );
    }
}
