package com.auth0.net;

import com.auth0.json.ObjectMapperProvider;
import com.auth0.net.client.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import static com.auth0.utils.Asserts.assertNotNull;

/**
 * Request class that accepts parts to be sent encoded in a form.
 * The content type of this request is "multipart/form-data".
 * <p>
 * This class is not thread-safe:
 * It makes use of {@link HashMap} for storing the parameters. Make sure to not modify headers or the parameters
 * from a different or un-synchronized thread.
 *
 * @param <T> The type expected to be received as part of the response.
 * @see ExtendedBaseRequest
 */
public class MultipartRequest<T> extends ExtendedBaseRequest<T> implements FormDataRequest<T> {

    private static final String CONTENT_TYPE_FORM_DATA = "multipart/form-data";
    private final Auth0MultipartRequestBody.Builder bodyBuilder;

    private final TypeReference<T> tType;
    private final ObjectMapper mapper;
    private int partsCount;

    MultipartRequest(Auth0HttpClient client, String url, HttpMethod method, ObjectMapper mapper, TypeReference<T> tType, Auth0MultipartRequestBody.Builder multipartBuilder) {
        super(client, url, method, mapper);
        if (HttpMethod.GET.equals(method)) {
            throw new IllegalArgumentException("Multipart/form-data requests do not support the GET method.");
        }
        this.mapper = mapper;
        this.tType = tType;
        this.bodyBuilder = Auth0MultipartRequestBody.newBuilder();
    }

    public MultipartRequest(Auth0HttpClient client, String url, HttpMethod method, TypeReference<T> tType) {
        this(client, url, method, ObjectMapperProvider.getMapper(), tType, Auth0MultipartRequestBody.newBuilder());
    }

    @Override
    protected String getContentType() {
        return CONTENT_TYPE_FORM_DATA;
    }

    @Override
    protected HttpRequestBody createRequestBody() throws IOException {
        if (partsCount == 0) {
            throw new IOException("Cannot create multipart/form-data request body with zero parts.");
        }
        return HttpRequestBody.create("application/json", bodyBuilder.build());
    }

    @Override
    protected T readResponseBody(Auth0HttpResponse response) throws IOException {
        String payload = response.getBody();
        return mapper.readValue(payload, tType);
    }

    @Override
    public MultipartRequest<T> addHeader(String name, String value) {
        //This is to avoid returning a different type
        super.addHeader(name, value);
        return this;
    }

    @Override
    @SuppressWarnings("deprecation")
    public MultipartRequest<T> addPart(String name, File file, String mediaType) {
        assertNotNull(name, "name");
        assertNotNull(name, "file");
        if (!file.exists()) {
            throw new IllegalArgumentException("Failed to add part because the file specified cannot be found.");
        }
        bodyBuilder.withFilePart(new Auth0MultipartRequestBody.FilePart(name, file, mediaType));
        partsCount++;
        return this;
    }

    @Override
    public MultipartRequest<T> addPart(String name, String value) {
        assertNotNull(name, "name");
        assertNotNull(value, "value");
        bodyBuilder.withPart(name, value);
        partsCount++;
        return this;
    }
}
