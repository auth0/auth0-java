package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import com.auth0.exception.AuthAPIException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import okhttp3.*;
import okhttp3.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomRequest<T> extends BaseRequest<T> implements CustomizableRequest<T> {
    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    private final String url;
    private final String method;
    private final Class<T> tClazz;
    private final ObjectMapper mapper;
    private final Map<String, String> headers;
    private final Map<String, Object> parameters;

    CustomRequest(OkHttpClient client, ObjectMapper mapper, String url, String method, Class<T> tClazz) {
        super(client);
        this.url = url;
        this.method = method;
        this.tClazz = tClazz;
        this.mapper = mapper;
        this.headers = new HashMap<>();
        this.parameters = new HashMap<>();
    }

    public CustomRequest(OkHttpClient client, String url, String method, Class<T> tClazz) {
        this(client, createUnknownSafeMapper(), url, method, tClazz);
    }

    private static ObjectMapper createUnknownSafeMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }

    @Override
    protected Request createRequest() throws Auth0Exception {
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(method, createBody());
        for (Map.Entry<String, String> e : headers.entrySet()) {
            builder.addHeader(e.getKey(), e.getValue());
        }
        builder.addHeader("Content-Type", CONTENT_TYPE_APPLICATION_JSON);
        return builder.build();
    }

    @Override
    protected T parseResponse(Response response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw createResponseException(response);
        }

        String payload;
        try {
            payload = response.body().string();
            return mapper.readValue(payload, tClazz);
        } catch (IOException e) {
            throw new AuthAPIException(String.format("Failed to parse body as %s", tClazz.getSimpleName()), response.code(), e);
        }
    }

    @Override
    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public void addParameter(String name, Object value) {
        parameters.put(name, value);
    }

    private RequestBody createBody() throws Auth0Exception {
        if (parameters.isEmpty()) {
            return null;
        }
        try {
            byte[] jsonBody = mapper.writeValueAsBytes(parameters);
            return RequestBody.create(MediaType.parse(CONTENT_TYPE_APPLICATION_JSON), jsonBody);
        } catch (JsonProcessingException e) {
            throw new Auth0Exception("Couldn't create the request body.", e);
        }
    }

    protected Auth0Exception createResponseException(Response response) {
        String payload = null;
        try {
            payload = response.body().string();
            MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
            Map<String, Object> values = mapper.readValue(payload, mapType);
            return new AuthAPIException(values, response.code());
        } catch (IOException e) {
            return new AuthAPIException(payload, response.code(), e);
        }
    }
}
