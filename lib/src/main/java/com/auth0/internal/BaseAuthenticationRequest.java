package com.auth0.internal;

import com.auth0.authentication.result.Credentials;
import com.auth0.request.AuthenticationRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.util.Map;

import static com.auth0.authentication.ParameterBuilder.ACCESS_TOKEN_KEY;
import static com.auth0.authentication.ParameterBuilder.CONNECTION_KEY;
import static com.auth0.authentication.ParameterBuilder.DEVICE_KEY;
import static com.auth0.authentication.ParameterBuilder.GRANT_TYPE_KEY;
import static com.auth0.authentication.ParameterBuilder.SCOPE_KEY;

/**
 * Created by hernan on 3/16/16.
 */
public class BaseAuthenticationRequest extends SimpleRequest<Credentials> implements AuthenticationRequest {

    public BaseAuthenticationRequest(HttpUrl url, OkHttpClient client, ObjectMapper mapper, String httpMethod, Class clazz) {
        super(url, client, mapper, httpMethod, clazz);
    }

    public BaseAuthenticationRequest(HttpUrl url, OkHttpClient client, ObjectMapper mapper, String httpMethod) {
        super(url, client, mapper, httpMethod);
    }

    /**
     * Sets the 'grant_type' parameter
     *
     * @param grantType grant type
     * @return itself
     */
    @Override
    public AuthenticationRequest setGrantType(String grantType) {
        addParameter(GRANT_TYPE_KEY, grantType);
        return this;
    }

    /**
     * Sets the 'connection' parameter
     *
     * @param connection name of the connection
     * @return itself
     */
    @Override
    public AuthenticationRequest setConnection(String connection) {
        addParameter(CONNECTION_KEY, connection);
        return this;
    }

    /**
     * Sets the 'scope' parameter.
     *
     * @param scope a scope value
     * @return itself
     */
    public AuthenticationRequest setScope(String scope) {
        addParameter(SCOPE_KEY, scope);
        return this;
    }

    /**
     * Sets the 'device' parameter
     *
     * @param device a device name
     * @return itself
     */
    public AuthenticationRequest setDevice(String device) {
        addParameter(DEVICE_KEY, device);
        return this;
    }

    /**
     * Sets the 'access_token' parameter
     *
     * @param accessToken a access token
     * @return itself
     */
    public AuthenticationRequest setAccessToken(String accessToken) {
        addParameter(ACCESS_TOKEN_KEY, accessToken);
        return this;
    }

    @Override
    public AuthenticationRequest addAuthenticationParameters(Map<String, Object> parameters) {
        addParameters(parameters);
        return this;
    }
}
