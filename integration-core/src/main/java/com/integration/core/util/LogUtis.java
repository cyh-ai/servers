package com.integration.core.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.google.common.cache.CacheLoader;
import com.itextpdf.text.log.Logger;
import com.itextpdf.text.log.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class LogUtis {
    private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static final ObjectMapper shortMapper = new ObjectMapper();
    private static final ObjectMapper commonMapper = new ObjectMapper();

    private final static AntPathMatcher pathmatcher = new AntPathMatcher();

    private final static List<String> filterLogURIList = Arrays.asList("/**/salePlan/getIdCardImg", "/**/salePlan/getOrdImg");

    static {
        SimpleModule module = new SimpleModule();
        module.addSerializer(String.class, new JsonSerializer<String>() {
            @Override
            public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(StringUtils.abbreviate(value, 512));
            }
        });
        module.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                gen.writeString(TimeUtils.format(value, "yyyy-MM-dd HH:mm:ss"));
            }
        });

        shortMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        shortMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        shortMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        shortMapper.registerModule(module);

        commonMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        commonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        commonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    }

    /**
     * 将对象转换为Json串
     */
    public static String toLogStr(Object o) {
        try {
            if (o instanceof String) {
                return (String) o;
            }
            if (shortEnable()) {
                return shortMapper.writeValueAsString(o);
            } else {
                return commonMapper.writeValueAsString(o);
            }
        } catch (Exception e) {
            logger.error("render object to json error: {}",  e);
            return "";
        }
    }

    /**
     * 返回日志，按照uri过滤，目前过滤img返回
     *
     * @param req
     * @param formatString
     * @return
     */
    public static String toLogStr(ServerHttpRequest req, String formatString) {
        ServletServerHttpRequest servletServerHttpRequest = (ServletServerHttpRequest) req;
        HttpServletRequest request = servletServerHttpRequest.getServletRequest();
        String requestURI = request.getRequestURI();
        if (matherPatch(requestURI)) {
            return StringUtils.abbreviate(formatString, 50);
        } else {
            return formatString;
        }
    }

    private static Boolean matherPatch(String uri) {
        for (String pattern : filterLogURIList) {
            if (pathmatcher.match(pattern, uri)) {
                return true;
            }
        }
        return false;
    }


    private static boolean shortEnable() {
            try {
                //PropertiesService propertiesService = (PropertiesService) SpringUtil.getBeanOfType(PropertiesService.class);获取service
                //String value = propertiesService.findPropertiesByKey("logger.abbreviate.disable");取配置：true或false
                String value = "true";
                //logger.info("加载日志缩减开关: {}", value);
                return value == null || !"true".equalsIgnoreCase(value);
            } catch (Exception ex) {
                logger.error("加载日志缩减开关", ex);
                return true;
            }
    }

    /**
     * @Date 2023/11/28
     * @Description 外系统请求星云 敏感信息打印
     **/
    public static void printLog(ExternalRequestInfoPrint externalRequestInfoPrint) {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        externalRequestInfoPrint.setOccurTime(new Date());
        externalRequestInfoPrint.setSrcAddress(sra.getRequest().getHeader("X-real-ip"));
        externalRequestInfoPrint.setEventId((String) MDC.get("指定值"));
        externalRequestInfoPrint.setEventName("文件查询");
        externalRequestInfoPrint.setcQueryUrl(sra.getRequest().getRequestURI());
        //logger.info("外部请求星云信息打印：{}", externalRequestInfoPrint);
    }
}
