package com.auth0.net;

import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import okhttp3.Request;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.utils.Asserts.assertNotNull;

@SuppressWarnings("WeakerAccess")
public class MultipartRequest<T> extends BaseRequest<T> implements FormDataRequest<T> {

    private static final String CONTENT_TYPE_FORM_DATA = "multipart/form-data";
    private final MultipartBody.Builder bodyBuilder;

    private final String url;
    private final String method;
    private final HashMap<String, String> headers;
    private final ObjectMapper mapper;
    private final TypeReference<T> tType;

    private static final int STATUS_CODE_TOO_MANY_REQUEST = 429;

    MultipartRequest(OkHttpClient client, String url, String method, ObjectMapper mapper, TypeReference<T> tType, MultipartBody.Builder multipartBuilder) {
        super(client);
        if ("GET".equalsIgnoreCase(method)) {
            throw new IllegalArgumentException("The HTTP method GET is not supported");
        }
        this.url = url;
        this.method = method;
        this.mapper = mapper;
        this.tType = tType;
        this.headers = new HashMap<>();
        this.bodyBuilder = multipartBuilder
                .setType(MultipartBody.FORM);
    }

    public MultipartRequest(OkHttpClient client, String url, String method, TypeReference<T> tType) {
        this(client, url, method, new ObjectMapper(), tType, new MultipartBody.Builder());
    }

    @Override
    public FormDataRequest<T> addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    public MultipartRequest<T> addPart(String name, File file, String mediaType) {
        assertNotNull(name, "name");
        assertNotNull(name, "file");
        if (!file.exists()) {
            throw new IllegalArgumentException("The file does not exist");
        }
        bodyBuilder.addFormDataPart(name, file.getName(),
                RequestBody.create(MediaType.parse(mediaType), file));
        return this;
    }

    public MultipartRequest<T> addPart(String name, String value) {
        assertNotNull(name, "name");
        assertNotNull(value, "value");
        bodyBuilder.addFormDataPart(name, value);
        return this;
    }

    @Override
    protected Request createRequest() throws Auth0Exception {
        //FIXME: Copies from #com.auth0.net.CustomRequest
        MultipartBody body;
        try {
            body = bodyBuilder.build();
        } catch (IllegalStateException e) {
            throw new Auth0Exception("Couldn't create the request body.", e);
        }
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(method, body);
        //TODO: Move to "getBody" abstract method. Handle parts size.
        for (Map.Entry<String, String> e : headers.entrySet()) {
            builder.addHeader(e.getKey(), e.getValue());
        }
        builder.addHeader("Content-Type", CONTENT_TYPE_FORM_DATA);
        return builder.build();
    }

    @Override
    protected T parseResponse(Response response) throws Auth0Exception {
        //FIXME: Copies from #com.auth0.net.CustomRequest
        if (!response.isSuccessful()) {
            throw createResponseException(response);
        }

        try (ResponseBody body = response.body()) {
            String payload = body.string();
            //TODO: Move to "readBody" abstract method.
            return mapper.readValue(payload, tType);
        } catch (IOException e) {
            throw new APIException("Failed to parse json body", response.code(), e);
        }
    }

    protected Auth0Exception createResponseException(Response response) {
        //FIXME: Copies from #com.auth0.net.CustomRequest
        if (response.code() == STATUS_CODE_TOO_MANY_REQUEST) {
            return createRateLimitException(response);
        }

        String payload = null;
        try (ResponseBody body = response.body()) {
            payload = body.string();
            MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
            Map<String, Object> values = mapper.readValue(payload, mapType);
            return new APIException(values, response.code());
        } catch (IOException e) {
            return new APIException(payload, response.code(), e);
        }
    }

    private RateLimitException createRateLimitException(Response response) {
        //FIXME: Copies from #com.auth0.net.CustomRequest
        // -1 as default value if the header could not be found.
        long limit = Long.parseLong(response.header("X-RateLimit-Limit", "-1"));
        long remaining = Long.parseLong(response.header("X-RateLimit-Remaining", "-1"));
        long reset = Long.parseLong(response.header("X-RateLimit-Reset", "-1"));
        return new RateLimitException(limit, remaining, reset);
    }
}
