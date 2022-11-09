package com.auth0.net;

import com.auth0.net.client.HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.net.client.HttpRequestBody;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Request class that does not accept parameters to be sent as part of its body.
 * The content type of this request is "application/json".
 *
 * @param <T> The type expected to be received as part of the response.
 * @see CustomRequest
 */
public class EmptyBodyRequest<T> extends CustomRequest<T> {
    public EmptyBodyRequest(HttpClient client, String url, HttpMethod method, TypeReference<T> tType) {
        super(client, url, method, tType);
    }

//    @Override
//    @SuppressWarnings("deprecation")
//    protected byte[] createRequestBody() {
//        return new byte[0];
//        // Use OkHttp v3 signature to ensure binary compatibility between v3 and v4
//        // https://github.com/auth0/auth0-java/issues/324
////        return RequestBody.create(null, new byte[0]);
//    }

    @Override
    @SuppressWarnings("deprecation")
    protected HttpRequestBody createRequestBody() {
        return HttpRequestBody.newBuilder().withContent(new byte[0]).build();
//        return new byte[0];
        // Use OkHttp v3 signature to ensure binary compatibility between v3 and v4
        // https://github.com/auth0/auth0-java/issues/324
//        return RequestBody.create(null, new byte[0]);
    }

    @Override
    public EmptyBodyRequest<T> addParameter(String name, Object value) {
        //do nothing
        return this;
    }
}
