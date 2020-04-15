package com.auth0.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.io.File;
import java.io.IOException;

import static com.auth0.utils.Asserts.assertNotNull;

/**
 * Request class that accepts parts to be sent encoded in a form.
 * The content type of this request is "multipart/form-data".
 *
 * @param <T> The type expected to be received as part of the response.
 */
public class MultipartRequest<T> extends ExtendedBaseRequest<T> implements FormDataRequest<T> {

    private static final String CONTENT_TYPE_FORM_DATA = "multipart/form-data";

    private final MultipartBody.Builder bodyBuilder;
    private final TypeReference<T> tType;
    private final ObjectMapper mapper;
    private int partsCount;

    MultipartRequest(OkHttpClient client, String url, String method, ObjectMapper mapper, TypeReference<T> tType, MultipartBody.Builder multipartBuilder) {
        super(client, url, method, mapper);
        if ("GET".equalsIgnoreCase(method)) {
            throw new IllegalArgumentException("Multipart/form-data requests do not support the GET method.");
        }
        this.mapper = mapper;
        this.tType = tType;
        this.bodyBuilder = multipartBuilder
                .setType(MultipartBody.FORM);
    }

    public MultipartRequest(OkHttpClient client, String url, String method, TypeReference<T> tType) {
        this(client, url, method, new ObjectMapper(), tType, new MultipartBody.Builder());
    }

    @Override
    protected String getContentType() {
        return CONTENT_TYPE_FORM_DATA;
    }

    @Override
    protected RequestBody createRequestBody() throws IOException {
        if (partsCount == 0) {
            throw new IOException("Cannot create multipart/form-data request body with zero parts.");
        }
        return bodyBuilder.build();
    }

    @Override
    protected T readResponseBody(ResponseBody body) throws IOException {
        String payload = body.string();
        return mapper.readValue(payload, tType);
    }

    @Override
    public MultipartRequest<T> addHeader(String name, String value) {
        //This is to avoid returning a different type
        super.addHeader(name, value);
        return this;
    }

    @Override
    public MultipartRequest<T> addPart(String name, File file, String mediaType) {
        assertNotNull(name, "name");
        assertNotNull(name, "file");
        if (!file.exists()) {
            throw new IllegalArgumentException("Failed to add part because the file specified cannot be found.");
        }
        bodyBuilder.addFormDataPart(name, file.getName(),
                RequestBody.create(MediaType.parse(mediaType), file));
        partsCount++;
        return this;
    }

    @Override
    public MultipartRequest<T> addPart(String name, String value) {
        assertNotNull(name, "name");
        assertNotNull(value, "value");
        bodyBuilder.addFormDataPart(name, value);
        partsCount++;
        return this;
    }
}
