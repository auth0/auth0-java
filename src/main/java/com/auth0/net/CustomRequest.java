package com.auth0.net;

import com.auth0.json.ObjectMapperProvider;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.Auth0HttpResponse;
import com.auth0.net.client.HttpMethod;
import com.auth0.net.client.HttpRequestBody;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Request class that accepts parameters to be sent as part of its body.
 * The content type of this request is "application/json".
 * <p>
 * This class is not thread-safe:
 * It makes use of {@link HashMap} for storing the parameters. Make sure to not modify headers or the parameters
 * from a different or un-synchronized thread.
 *
 * @param <T> The type expected to be received as part of the response.
 */
public class CustomRequest<T> extends ExtendedBaseRequest<T> implements CustomizableRequest<T> {

    private static final String CONTENT_TYPE_APPLICATION_JSON = "application/json";

    private final ObjectMapper mapper;
    private final TypeReference<T> tType;
    private final Map<String, Object> parameters;
    private Object body;

    CustomRequest(Auth0HttpClient client, String url, HttpMethod method, ObjectMapper mapper, TypeReference<T> tType) {
        super(client, url, method, mapper);
        this.mapper = mapper;
        this.tType = tType;
        this.parameters = new HashMap<>();
    }

    public CustomRequest(Auth0HttpClient client, String url, HttpMethod method, TypeReference<T> tType) {
        this(client, url, method, ObjectMapperProvider.getMapper(), tType);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected HttpRequestBody createRequestBody() throws IOException {
        if (body == null && parameters.isEmpty()) {
            return null;
        }
        byte[] jsonBody = mapper.writeValueAsBytes(body != null ? body : parameters);
        return HttpRequestBody.create(CONTENT_TYPE_APPLICATION_JSON, jsonBody);
    }

    @Override
    protected T readResponseBody(Auth0HttpResponse response) throws IOException {
        String payload = response.getBody();
        return mapper.readValue(payload, tType);
    }

    @Override
    public CustomRequest<T> addHeader(String name, String value) {
        //This is to avoid returning a different type
        super.addHeader(name, value);
        return this;
    }

    @Override
    public CustomRequest<T> addParameter(String name, Object value) {
        parameters.put(name, value);
        return this;
    }

    @Override
    public CustomRequest<T> setBody(Object value) {
        body = value;
        return this;
    }
}
