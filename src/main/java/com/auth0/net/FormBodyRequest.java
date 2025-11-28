package com.auth0.net;

import com.auth0.client.mgmt.TokenProvider;
import com.auth0.json.ObjectMapperProvider;
import com.auth0.net.client.*;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

/**
 * Represents a form request.
 * @param <T> The type expected to be received as part of the response.
 */
public class FormBodyRequest<T> extends BaseRequest<T> {

    private static final String CONTENT_TYPE_FORM_DATA = "application/x-www-form-urlencoded";

    FormBodyRequest(
            Auth0HttpClient client,
            TokenProvider tokenProvider,
            String url,
            HttpMethod method,
            ObjectMapper mapper,
            TypeReference<T> tType) {
        super(client, tokenProvider, url, method, mapper, tType);
    }

    public FormBodyRequest(
            Auth0HttpClient client,
            TokenProvider tokenProvider,
            String url,
            HttpMethod method,
            TypeReference<T> tType) {
        this(client, tokenProvider, url, method, ObjectMapperProvider.getMapper(), tType);
    }

    @Override
    protected HttpRequestBody createRequestBody() throws IOException {
        return HttpRequestBody.create(CONTENT_TYPE_FORM_DATA, new Auth0FormRequestBody(super.getParameters()));
    }

    @Override
    protected String getContentType() {
        return CONTENT_TYPE_FORM_DATA;
    }
}
