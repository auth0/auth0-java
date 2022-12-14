package com.auth0.net;

import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RateLimitException;
import com.auth0.json.ObjectMapperProvider;
import com.auth0.net.client.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;

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
public class ExtendedBaseRequest<T> extends BaseRequest<T> {

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    private final String url;
    private final HttpMethod method;
    private final ObjectMapper mapper;
    private final Map<String, String> headers;
    private final TypeReference<T> tType;
    private final Map<String, Object> parameters;
    private Object body;

    private static final int STATUS_CODE_TOO_MANY_REQUEST = 429;

    ExtendedBaseRequest(Auth0HttpClient client, String url, HttpMethod method, ObjectMapper mapper, TypeReference<T> tType) {
        super(client);
        this.url = url;
        this.method = method;
        this.mapper = mapper;
        this.headers = new HashMap<>();
        this.tType = tType;
        this.parameters = new HashMap<>();
    }

    public ExtendedBaseRequest(Auth0HttpClient client, String url, HttpMethod method, TypeReference<T> tType) {
        this(client, url, method, ObjectMapperProvider.getMapper(), tType);
    }

    @Override
    protected Auth0HttpRequest createRequest() throws Auth0Exception {
        HttpRequestBody body;
        try {
            body = this.createRequestBody();
        } catch (IOException e) {
            throw new Auth0Exception("Couldn't create the request body.", e);
        }
        headers.put("Content-Type", getContentType());
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(url, method)
            .withBody(body)
            .withHeaders(headers)
            .build();

        return request;
    }

    @Override
    protected T parseResponseBody(Auth0HttpResponse response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw createResponseException(response);
        }

        try {
            return readResponseBody(response);
        } catch (IOException e) {
            throw new APIException("Failed to parse the response body.", response.getCode(), e);
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
    protected Auth0Exception createResponseException(Auth0HttpResponse response) {
        if (response.getCode() == STATUS_CODE_TOO_MANY_REQUEST) {
            return createRateLimitException(response);
        }

        String payload = response.getBody();
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, Object.class);
        try {
            Map<String, Object> values = mapper.readValue(payload, mapType);
            return new APIException(values, response.getCode());
        } catch (IOException e) {
            return new APIException(payload, response.getCode(), e);
        }
    }

    private RateLimitException createRateLimitException(Auth0HttpResponse response) {
        // -1 as default value if the header could not be found.
        long limit = Long.parseLong(response.getHeader("X-RateLimit-Limit", "-1"));
        long remaining = Long.parseLong(response.getHeader("X-RateLimit-Remaining", "-1"));
        long reset = Long.parseLong(response.getHeader("X-RateLimit-Reset", "-1"));
        return new RateLimitException(limit, remaining, reset);
    }

    /**
     * Responsible for creating the payload that will be set as body on this request.
     *
     * @return the body to send as part of the request.
     * @throws IOException if an error is raised during the creation of the body.
     */
    @SuppressWarnings("deprecation")
    protected HttpRequestBody createRequestBody() throws IOException {
        if (body == null && parameters.isEmpty()) {
            return null;
        }
        byte[] jsonBody = mapper.writeValueAsBytes(body != null ? body : parameters);
        return HttpRequestBody.create(CONTENT_TYPE_APPLICATION_JSON, jsonBody);
    }

    /**
     * Responsible for parsing the payload that is received as part of the response.
     *
     * @param response the received response. The body buffer will automatically closed.
     * @return the instance of type T, result of interpreting the payload.
     * @throws IOException if an error is raised during the parsing of the body.
     */
    protected T readResponseBody(Auth0HttpResponse response) throws IOException {
        String payload = response.getBody();
        return mapper.readValue(payload, tType);
    }

    @Override
    public ExtendedBaseRequest<T> addParameter(String name, Object value) {
        parameters.put(name, value);
        return this;
    }

    @Override
    public ExtendedBaseRequest<T> setBody(Object value) {
        body = value;
        return this;
    }
}
