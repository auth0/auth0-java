package com.auth0.net;

import com.auth0.net.client.HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.net.client.HttpRequestBody;
import com.auth0.net.client.HttpResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

public class FileUploadRequest<T> extends ExtendedBaseRequest<T> {

    private final File file;
    private final Map<String, String> params;

    private final ObjectMapper mapper;

    private final TypeReference<T> tType;


    // FileUploadRequest<Job> request = new FileUploadRequest<>(client, url, HttpMethod.POST, new TypeReference<Job>(){},
    //      file, params
    public FileUploadRequest(HttpClient client, String url, HttpMethod method, TypeReference<T> tType, File file, Map<String, String> params) {
        this(client, url, method, new ObjectMapper(), tType, file, params);
    }

    FileUploadRequest(HttpClient client, String url, HttpMethod method, ObjectMapper mapper, TypeReference<T> tType, File file, Map<String, String> params) {
        super(client, url, method, mapper);
        this.mapper = mapper;
        this.tType = tType;
        this.file = file; // TODO null check
        this.params = Collections.unmodifiableMap(params);
    }

    @Override
    protected HttpRequestBody createRequestBody() throws IOException {
        return new HttpRequestBody.Builder()
            .withFile(file)
            .withParams(params)
            .build();
    }

    @Override
    protected T readResponseBody(HttpResponse response) throws IOException {
        String payload = response.getBody();
        return mapper.readValue(payload, tType);
    }
}
