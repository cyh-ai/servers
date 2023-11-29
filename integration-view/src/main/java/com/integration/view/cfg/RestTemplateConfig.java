package com.integration.view.cfg;

import com.integration.core.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Duration;
import java.util.List;

/**
 * @author cyh
 * 配置resttemplate
 */
@Configuration
public class RestTemplateConfig {

    @Value("${customRestTemplate.readTimeout}")
    private long readTimeout;
    @Value("${customRestTemplate.connectTimeout}")
    private long connectTimeout;

    @Bean
    public RestTemplate RestTemplate(RestTemplateBuilder restTemplateBuilder) {
        RestTemplate restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(readTimeout))
                .setConnectTimeout(Duration.ofSeconds(connectTimeout))
                .build();
        List<HttpMessageConverter<?>> list = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> httpMessageConverter : list) {
            if (httpMessageConverter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) httpMessageConverter).setDefaultCharset(charset("UTF-8"));
                break;
            }
        }
        return restTemplate;
    }

    public long getReadTimeout() {
        return readTimeout;
    }

    public long getConnectTimeout() {
        return connectTimeout;
    }


    private static Charset charset(String charsetName){
        return StringUtils.isBlank(charsetName)? Charset.defaultCharset():Charset.forName(charsetName);
    }
}
