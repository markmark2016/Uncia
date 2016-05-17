package com.markeveryday.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import org.springframework.stereotype.Component;

/**
 * @author liming
 */
@Component
public class JsonHelpler {
    private static ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private JsonHelpler() {
    }

    public static String toJsonString(Object o) {
        try {
            return objectWriter.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "";
    }

}
