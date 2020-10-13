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
import com.fasterxml.jackson.databind.util.StdDateFormat;

public class JsonTest<T> {

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

    protected Date parseJSONDate(String dateString) throws ParseException, JsonProcessingException {
        // StdDateFormat is the DateFormat Jackson uses by default for date fields (uses UTC timezone by default)
        // https://fasterxml.github.io/jackson-databind/javadoc/2.9/com/fasterxml/jackson/databind/util/StdDateFormat.html
        return new StdDateFormat().parse(dateString);
    }
}