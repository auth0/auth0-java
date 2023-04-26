package com.auth0.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static com.auth0.utils.Asserts.assertNotNull;

/**
 * Request class that represents a application/x-www-form-urlencoded request
 *
 * @param <T> The type expected to be received as part of the response.
 * @see ExtendedBaseRequest
 */
public class FormBodyRequest<T> extends ExtendedBaseRequest<T> {

    private static final String CONTENT_TYPE_FORM_DATA = "application/x-www-form-urlencoded";

    private final TypeReference<T> tType;
    private final ObjectMapper mapper;
    private final FormBody.Builder bodyBuilder;

    FormBodyRequest(OkHttpClient client, String url, String method, ObjectMapper mapper, TypeReference<T> tType, FormBody.Builder bodyBuilder) {
        super(client, url, method, mapper);
        if ("GET".equalsIgnoreCase(method)) {
            throw new IllegalArgumentException("application/x-www-form-urlencoded requests do not support the GET method.");
        }
        this.mapper = mapper;
        this.tType = tType;
        this.bodyBuilder = bodyBuilder;
    }

    public FormBodyRequest(OkHttpClient client, String url, String method, TypeReference<T> tType) {
        this(client, url, method, new ObjectMapper(), tType, new FormBody.Builder());
    }

    @Override
    protected String getContentType() {
        return CONTENT_TYPE_FORM_DATA;
    }

    @Override
    protected RequestBody createRequestBody() throws IOException {
        return bodyBuilder.build();
    }

    @Override
    protected T readResponseBody(ResponseBody body) throws IOException {
        String payload = body.string();
        return mapper.readValue(payload, tType);
    }

    public FormBodyRequest<T> addData(String name, String value) {
        assertNotNull(name, "name");
        assertNotNull(value, "value");
        bodyBuilder.add(name, value);
        return this;
    }
}
