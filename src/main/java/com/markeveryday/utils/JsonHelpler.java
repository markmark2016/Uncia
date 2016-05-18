package com.markeveryday.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author liming
 */
@Component
public class JsonHelpler {


    private static ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    }

    private static ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();

    private JsonHelpler() {
    }

    /**
     * 返回一个对象的json表示
     */
    public static String toJsonString(Object o) {
        try {
            return objectWriter.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * 根据json字符串和给定的class,解析为指定class的一个实例
     */
    public static <T> T getObjectByJsonString(String jsonString, Class<T> clazz) {
        if (null == jsonString || null == clazz) {
            return null;
        }
        try {
            T obj = mapper.readValue(jsonString, clazz);
            return obj;
        } catch (Exception e) {
            throw new RuntimeException("解析json数据时发生异常:");
        }
    }

    /**
     * 根据json字符串和给定的typeReference,解析为指定class的一个实例
     */
    public static <T> T getObjectByJsonString(String jsonString, TypeReference<T> type) {
        try {
            return mapper.<T>readValue(jsonString, type);
        } catch (Exception e) {
            throw new RuntimeException("解析json数据时发生异常:");
        }
    }

    public static ArrayNode getArrayNode() throws Exception {
        return mapper.createArrayNode();
    }


    public static JsonNode getJsonTree(String json) {
        JsonNode node = null;
        try {
            node = mapper.readTree(json);
        } catch (IOException e) {
            throw new RuntimeException("生成json node发生异常:");
        }
        return node;
    }

}
