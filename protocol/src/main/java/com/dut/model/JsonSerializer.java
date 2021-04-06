package com.dut.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * @author algorithm
 */
public class JsonSerializer implements ISerializer {

    private ObjectMapper objectMapper;

    public JsonSerializer(){
        objectMapper = new ObjectMapper();
    }

    @Override
    public <T> byte[] serialize(T obj) {
        byte[] ret = null;
        try {
            ret = objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public <T> T deSerialize(byte[] data, Class<T> clz) {
        T ret = null;
        try {
            ret = objectMapper.readValue(data, clz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
