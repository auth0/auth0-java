package com.auth0.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonTest<T> {

    private ObjectMapper mapper;

    public JsonTest() {
        this.mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public String toJSON(T value) throws JsonProcessingException {
        return mapper.writeValueAsString(value);
    }

    public T fromJSON(String json, Class<T> tClazz) throws IOException {
        return mapper.readValue(json, tClazz);
    }

    public T fromJSON(String json, TypeReference<T> tReference) throws IOException {
        return mapper.readValue(json, tReference);
    }
}