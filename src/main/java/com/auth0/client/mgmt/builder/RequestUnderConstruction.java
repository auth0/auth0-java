package com.auth0.client.mgmt.builder;

import java.util.HashMap;
import java.util.Map;

import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.net.CustomRequest;
import com.auth0.net.EmptyBodyRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import static com.auth0.client.mgmt.filter.QueryFilter.KEY_QUERY;

public class RequestUnderConstruction {
    private final HttpUrl baseUrl;
    private final String apiToken;
    private final OkHttpClient client;
    private final String method;
    private final String path;
    private final String[] segments;
    private Object body;
    private Map<String, Object> queryParameters = new HashMap<>();

    public RequestUnderConstruction(HttpUrl baseUrl, String apiToken, OkHttpClient client, String method,
                                    String path, String... segments) {
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
        this.client = client;
        this.method = method;
        this.path = path;
        this.segments = segments == null ? new String[0] : segments;
    }

    /**
     * Adds each filter's field as query parameter.
     * If field name is "q" parameter is encoded
     *
     * @param filter filter object
     * @return Builder for further construction
     */
    public RequestUnderConstruction queryParameters(FieldsFilter filter) {
        if (filter != null) {
            for (Map.Entry<String, Object> param : filter.getAsMap().entrySet()) {
                queryParameter(param.getKey(), param.getValue());
            }
        }
        return this;
    }

    /**
     * Adds single query parameter
     *
     * @param name parameter name
     * @param value parameter value
     * @return Builder for further construction
     */
    public RequestUnderConstruction queryParameter(String name, Object value) {
        queryParameters.put(name, value);
        return this;
    }

    /**
     * Adds request body
     *
     * @param body is transformed to JSON using Jackson
     * @return Builder for further construction
     */
    public RequestUnderConstruction body(Object body) {
        this.body = body;
        return this;
    }

    /**
     * "Terminal" operation, it completes request building, providing Request with specified result.
     *
     * @param typeReference reference to type, which will be in query result (is used by Jackson for deserialization)
     * @param <T> type, which will be in query result
     * @return request to execute
     */
    public <T> Request<T> request(TypeReference<T> typeReference) {

        String url = buildUrl();
        CustomRequest<T> customOrEmptyRequest = createCustomOrEmptyRequest(url, typeReference);
        return setBodyAndToken(customOrEmptyRequest);
    }

    /**
     * "Terminal" operation, it completes request building, providing void Request (without any result).
     *
     * @return request to execute
     */
    public Request<Void> request() {

        String url = buildUrl();
        CustomRequest<Void> voidRequest = createVoidRequest(url);
        return setBodyAndToken(voidRequest);
    }

    private String buildUrl() {
        HttpUrl.Builder builder = baseUrl.newBuilder()
                                         .addPathSegments(path);

        for (String segment : segments) {
            builder.addPathSegment(segment);
        }

        for (Map.Entry<String, Object> param : queryParameters.entrySet()) {
            if (param.getKey().equals(KEY_QUERY)) {
                builder.addEncodedQueryParameter(param.getKey(), String.valueOf(param.getValue()));
            } else {
                builder.addQueryParameter(param.getKey(), String.valueOf(param.getValue()));
            }
        }

        return builder.build().toString();
    }

    private <T> CustomRequest<T> createCustomOrEmptyRequest(String url, TypeReference<T> typeReference) {
        if (body == null && (method.equals("PATCH") || method.equals("POST") || method.equals("PUT"))) {
            return new EmptyBodyRequest<>(client, url, method, typeReference);
        }
        return new CustomRequest<>(client, url, method, typeReference);
    }

    private CustomRequest<Void> createVoidRequest(String url) {
        return new VoidRequest(client, url, method);
    }

    private <T> Request<T> setBodyAndToken(CustomRequest<T> request) {
        request.addHeader("Authorization", "Bearer " + apiToken);
        if (body != null) {
            request.setBody(body);
        }
        return request;
    }
}
