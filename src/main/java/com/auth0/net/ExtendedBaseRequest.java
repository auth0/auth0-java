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

/**
 * A request class that is able to interact fluently with the Auth0 server.
 * The default content type of this request is "application/json".
 * <p>
 * This class is not thread-safe:
 * It makes use of {@link HashMap} for storing the parameters. Make sure to not modify headers or the parameters
 * from a different or un-synchronized thread.
 * //TODO: Merge with #BaseRequest on next major
 *
 * @param <T> The type expected to be received as part of the response.
 */
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
    protected T parseResponseBody(okhttp3.Response response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw createResponseException(response);
        }

        try (ResponseBody body = response.body()) {
            return readResponseBody(body);
        } catch (IOException e) {
            throw new APIException("Failed to parse the response body.", response.code(), e);
        }
    }

    /**
     * Getter for the content-type header value to use on this request
     *
     * @return the content-type
     */
    protected String getContentType() {
        return CONTENT_TYPE_APPLICATION_JSON;
    }

    /**
     * Responsible for creating the payload that will be set as body on this request.
     *
     * @return the body to send as part of the request.
     * @throws IOException if an error is raised during the creation of the body.
     */
    protected abstract RequestBody createRequestBody() throws IOException;

    /**
     * Responsible for parsing the payload that is received as part of the response.
     *
     * @param body the received body payload. The body buffer will automatically closed.
     * @return the instance of type T, result of interpreting the payload.
     * @throws IOException if an error is raised during the parsing of the body.
     */
    protected abstract T readResponseBody(ResponseBody body) throws IOException;

    /**
     * Adds an HTTP header to the request
     *
     * @param name  the name of the header
     * @param value the value of the header
     * @return this same request instance
     */
    public ExtendedBaseRequest<T> addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    /**
     * Responsible for parsing an unsuccessful request (status code other than 200)
     * and generating a developer-friendly exception with the error details.
     *
     * @param response the unsuccessful response, as received. If its body is accessed, the buffer must be closed.
     * @return the exception with the error details.
     */
    protected Auth0Exception createResponseException(okhttp3.Response response) {
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

    private RateLimitException createRateLimitException(okhttp3.Response response) {
        // -1 as default value if the header could not be found.
        long limit = Long.parseLong(response.header("X-RateLimit-Limit", "-1"));
        long remaining = Long.parseLong(response.header("X-RateLimit-Remaining", "-1"));
        long reset = Long.parseLong(response.header("X-RateLimit-Reset", "-1"));
        return new RateLimitException(limit, remaining, reset);
    }
}
