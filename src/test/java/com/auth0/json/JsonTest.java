package com.auth0.json;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonTest<T> {

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    private ObjectMapper mapper;

    public JsonTest() {
        this.mapper = new ObjectMapper();
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

    public String readTextFile(String path) throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }

    protected Date parseJSONDate(String dateString) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        return sdf.parse(dateString);
    }
}