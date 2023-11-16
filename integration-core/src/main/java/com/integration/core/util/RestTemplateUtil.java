package com.integration.core.util;

import com.integration.core.Enum.ErrorConstans;
import com.integration.core.excp.FaInsExcept;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

/**
 * @author cyh
 */
@Component
public class RestTemplateUtil {

    private final static Logger logger = LoggerFactory.getLogger(RestTemplateUtil.class);

    @Autowired
    private RestTemplate restTemplate;


    private void checkSchema(String url){
        if (url.startsWith("http://")||url.startsWith("https://")){
            return;
        }
        throw new FaInsExcept(ErrorConstans.PROTOCOL_ANOMALY,"只支持HTTP或HTTPS协议");
    }

    private static void initHeaders(HttpHeaders httpHeaders, Map<String, String> baseHeaders){
        if(baseHeaders != null){
            for (String key : baseHeaders.keySet()) {
                httpHeaders.set(key, baseHeaders.get(key));
            }
        }
    }


    public <T> T httpPost(String url, Map<String, String> header, Object request, Class<T> clazz) {
        checkSchema(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));
        initHeaders(headers, header);

        String reqJson = null;
        if (request != null) {
            reqJson = JsonUtil.toJson(request);
        }
        TimerRecord timer = TimerRecord.start();
        logger.info("httppost request begin ,url is {} ,header is {},requestjson is {}", url, JsonUtil.toJson(headers), LogUtis.toLogStr(request));
        HttpEntity<String> entity = new HttpEntity<>(reqJson, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.POST, entity, clazz);
        logger.info("httpstatus is {}, 耗时:{},返回结果是{}",
                response.getStatusCode(),
                timer.getCost(),
                JsonUtil.toJson(response.getBody()));
        return response.getBody();
    }

    /**
     * restTemplate发送get请求，无参数
     *
     * @param url   请求地址
     * @param clazz 返回对象类型
     * @return rs
     */
    public <T> T httpGet(String url, Class<T> clazz) {//...
        TimerRecord timer = TimerRecord.start();
        logger.info("httpget reqquesturl is {}", url);
        checkSchema(url);
        ResponseEntity<T> response = restTemplate.getForEntity(url, clazz);
        logger.info("httpstatus is {},耗时:{}, 返回结果是{}",
                response.getStatusCode(),
                timer.getCost(),
                JsonUtil.toJson(response.getBody()));
        return response.getBody();
    }


    public <T> T httpGet(String url, Map<String, String> header, Object request, Class<T> clazz) {//....
        HttpHeaders headers = new HttpHeaders();
        initHeaders(headers, header);
        logger.info("headers  is {}", JsonUtil.toJson(headers));

        String reqJson = null;
        if (request != null) {
            reqJson = JsonUtil.toJsonContainEmpty(request);
        }
        logger.info("httpGet request begin ,url is {} ,header is {},requestjson is {}", url, JsonUtil.toJson(headers), reqJson);
        HttpEntity<String> entity = new HttpEntity<>(reqJson, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
        logger.info("httpGet is {},返回结果是{}", response.getStatusCode(), JsonUtil.toJson(response.getBody()));
        return response.getBody();
    }

    /**
     * restTemplate 发送自定义header的请求,，默认已设置contentType：application/json;charset=UTF-8
     *
     * @param url     请求地址
     * @param header  header的key&value
     * @param request 请求对象
     * @param clazz   返回对象类型
     * @param <T> t
     * @return rs
     */
    public <T> T httpPut(String url, Map<String, String> header, Object request, Class<T> clazz) {//...
        checkSchema(url);
        HttpHeaders headers = new HttpHeaders();
        initHeaders(headers, header);
        headers.setContentType(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE));


        String reqJson = null;
        if (request != null) {
            reqJson = JsonUtil.toJson(request);
        }
        TimerRecord timer = TimerRecord.start();
        logger.info("httppost request begin ,url is {} ,header is {},requestjson is {}", url, JsonUtil.toJson(headers), LogUtis.toLogStr(request));
        HttpEntity<String> entity = new HttpEntity<>(reqJson, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.PUT, entity, clazz);
        logger.info("httpstatus is {}, 耗时:{},返回结果是{}",
                response.getStatusCode(),
                timer.getCost(),
                JsonUtil.toJson(response.getBody()));
        return response.getBody();
    }




    /**
     * restTemplate 发送post请求
     *
     * @param url url
     * @param reqJson json
     * @param clazz   返回对象类型
     * @return rs
     */
    public <T> T httpPost(String url, String reqJson, Class<T> clazz) {//...
        logger.info("httppost request begin ,url is {},requestjson is {}", url, reqJson);
        checkSchema(url);
        ResponseEntity<T> response = restTemplate.postForEntity(url, reqJson, clazz);
        logger.info("httpstatus is {},返回结果是{}", response.getStatusCode(), JsonUtil.toJson(response.getBody()));
        return response.getBody();
    }

    /**
     * restTemplate 发送post请求form
     *
     */
    public <T> T httpPostForm(String url, MultiValueMap<String, String> map, Class<T> clazz) {//...
        logger.info("httppost request begin ,url is {},requestjson is {}", url, JsonUtil.toJson(map));
        checkSchema(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        ResponseEntity<T> response = restTemplate.postForEntity(url, request, clazz);
        logger.info("httpstatus is {},返回结果是{}", response.getStatusCode(), JsonUtil.toJson(response.getBody()));
        return response.getBody();
    }
}
