package com.auth0.net;

import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import okhttp3.Request;
import okhttp3.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Package-Private on purpose
//TODO: Merge with #BaseRequest on next major
abstract class ExtendedBaseRequest<T> extends BaseRequest<T> {

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    private final String url;
    private final String method;
    private final ObjectMapper mapper;
    private final Map<String, String> headers;

    private static final int STATUS_CODE_TOO_MANY_REQUEST = 429;

    ExtendedBaseRequest(OkHttpClient client, String url, String method, ObjectMapper mapper) {
        super(client);
        this.url = url;
        this.method = method;
        this.mapper = mapper;
        this.headers = new HashMap<>();
    }

    @Override
    protected Request createRequest() throws Auth0Exception {
        RequestBody body;
        try {
            body = this.createRequestBody();
        } catch (IOException e) {
            throw new Auth0Exception("Couldn't create the request body.", e);
        }
        Request.Builder builder = new Request.Builder()
                .url(url)
                .method(method, body);
        for (Map.Entry<String, String> e : headers.entrySet()) {
            builder.addHeader(e.getKey(), e.getValue());
        }
        builder.addHeader("Content-Type", getContentType());
        return builder.build();
    }

    @Override
    protected T parseResponse(Response response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw createResponseException(response);
        }

        try (ResponseBody body = response.body()) {
            return readResponseBody(body);
        } catch (IOException e) {
            throw new APIException("Failed to parse the response body.", response.code(), e);
        }
    }

    protected String getContentType() {
        return CONTENT_TYPE_APPLICATION_JSON;
    }

    protected abstract RequestBody createRequestBody() throws IOException;

    protected abstract T readResponseBody(ResponseBody body) throws IOException;

    public ExtendedBaseRequest<T> addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    protected Auth0Exception createResponseException(Response response) {
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
        // -1 as default value if the header could not be found.
        long limit = Long.parseLong(response.header("X-RateLimit-Limit", "-1"));
        long remaining = Long.parseLong(response.header("X-RateLimit-Remaining", "-1"));
        long reset = Long.parseLong(response.header("X-RateLimit-Reset", "-1"));
        return new RateLimitException(limit, remaining, reset);
    }
}
