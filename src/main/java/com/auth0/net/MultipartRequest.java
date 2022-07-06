package com.auth0.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

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
    @SuppressWarnings("deprecation")
    public MultipartRequest<T> addPart(String name, File file, String mediaType) {
        assertNotNull(name, "name");
        assertNotNull(name, "file");
        if (!file.exists()) {
            throw new IllegalArgumentException("Failed to add part because the file specified cannot be found.");
        }
        // Use OkHttp v3 signature to ensure binary compatibility between v3 and v4
        // https://github.com/auth0/auth0-java/issues/324
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

    @Override
    public void registerModules() {
        this.mapper.findAndRegisterModules();
    }
}
