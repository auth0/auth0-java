package com.auth0.authentication;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

abstract class GsonBaseTest {

    static final String EMPTY_OBJECT = "src/test/resources/empty_object.json";
    static final String INVALID = "src/test/resources/invalid.json";


    protected Gson gson;

    <T> T pojoFrom(FileReader json, Class<T> clazz) throws IOException {
        return gson.getAdapter(clazz).fromJson(json);
    }

    FileReader json(String name) throws FileNotFoundException {
        return new FileReader(name);
    }

}