package com.integration.core.util;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.integration.core.serializer.CustomizeBeanSerializerModifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * json串和对象之间相互转换工具类
 */
public class JsonUtil {
    private static Logger logger = LoggerFactory.getLogger(JsonUtil.class);
    private static ObjectMapper mapper = new ObjectMapper();
    //序列化时String 为null时变成""
    private static ObjectMapper mapperContainEmpty = new ObjectMapper();

    static {
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(Include.NON_NULL);

        mapperContainEmpty.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapperContainEmpty.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapperContainEmpty.setSerializerFactory(mapperContainEmpty.getSerializerFactory()
                .withSerializerModifier(new CustomizeBeanSerializerModifier()));
    }

    /**
     * 将对象转换为Json串
     */
    public static String toJson(Object o) {
        try {
            if(o instanceof String){
                return (String)o;
            }
            return mapper.writeValueAsString(o);
        } catch (IOException e) {
            logger.error("render object to json error: {}", e.getMessage(), e);
            throw new RuntimeException("render object to json error!", e);
        }
    }

    /**
     * 将对象转换为Json串,针对String 类型 null 转成""
     */
    public static String toJsonContainEmpty(Object o) {
        try {
            return mapperContainEmpty.writeValueAsString(o);
        } catch (IOException e) {
            logger.error("render object to json error: {}", e.getMessage(), e);
            throw new RuntimeException("render object to json error!", e);
        }
    }

    /**
     * 将Json串转换为对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (IOException e) {
            logger.error("render json to object error: {}", e.getMessage(), e);
            throw new RuntimeException("render json to object error!", e);
        }
    }

    /**
     * 将Json串转换为List
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            logger.error("render json to List<T> error: {}", e.getMessage(), e);
            throw new RuntimeException("render json to List<T> error!", e);
        }
    }

    /**
     * 将Json串转换为Map
     */
    public static <K, V> Map<K, V> toMap(String json, Class<K> clazzKey, Class<V> clazzValue) {
        try {
            JavaType javaType = mapper.getTypeFactory().constructParametricType(Map.class, clazzKey, clazzValue);
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            logger.error("render json to Map<K, V> error: {}", e.getMessage(), e);
            throw new RuntimeException("render json to Map<K, V> error!", e);
        }
    }

    /**
     * 将对象转换为Json串,针对String 类型 null 转成""，并格式化
     */
    public static String toJsonFormat(Object o) {
        try {
            String jsonStr = toJsonContainEmpty(o);
            JSONObject jsonObject = JSONObject.parseObject(jsonStr);
            return JSON.toJSONString(jsonObject,
                    SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue);
        } catch (Exception e) {
            logger.error("render object to json error: {}", e.getMessage(), e);
            throw new RuntimeException("render object to json error!", e);
        }
    }
}