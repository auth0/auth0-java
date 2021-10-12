package com.auth0.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

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

    CustomRequest(OkHttpClient client, String url, String method, ObjectMapper mapper, TypeReference<T> tType) {
        super(client, url, method, mapper);
        this.mapper = mapper;
        this.tType = tType;
        this.parameters = new HashMap<>();
    }

    public CustomRequest(OkHttpClient client, String url, String method, TypeReference<T> tType) {
        this(client, url, method, new ObjectMapper(), tType);
    }

    @Override
    @SuppressWarnings("deprecation")
    protected RequestBody createRequestBody() throws IOException {
        if (body == null && parameters.isEmpty()) {
            return null;
        }
        byte[] jsonBody = mapper.writeValueAsBytes(body != null ? body : parameters);
        // Use OkHttp v3 signature to ensure binary compatibility between v3 and v4
        // https://github.com/auth0/auth0-java/issues/324
        return RequestBody.create(MediaType.parse(CONTENT_TYPE_APPLICATION_JSON), jsonBody);
    }

    @Override
    protected T readResponseBody(ResponseBody body) throws IOException {
        String payload = body.string();
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
