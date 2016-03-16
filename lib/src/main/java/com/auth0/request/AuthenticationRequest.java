package com.auth0.request;

import com.auth0.authentication.result.Credentials;

import java.util.Map;

/**
 * Request to authenticate a user with Auth0 Authentication API
 */
public interface AuthenticationRequest extends Request<Credentials> {

    /**
     * Sets the 'client_id' parameter
     *
     * @param clientId clientID
     * @return itself
     */
    AuthenticationRequest setClientId(String clientId);

    /**
     * Sets the 'grant_type' parameter
     *
     * @param grantType grant type
     * @return itself
     */
    AuthenticationRequest setGrantType(String grantType);

    /**
     * Sets the 'connection' parameter
     *
     * @param connection name of the connection
     * @return itself
     */
    AuthenticationRequest setConnection(String connection);

    /**
     * Sets the 'scope' parameter.
     *
     * @param scope a scope value
     * @return itself
     */
    AuthenticationRequest setScope(String scope);

    /**
     * Sets the 'device' parameter
     *
     * @param device a device name
     * @return itself
     */
    AuthenticationRequest setDevice(String device);

    /**
     * Sets the 'access_token' parameter
     *
     * @param accessToken a access token
     * @return itself
     */
    AuthenticationRequest setAccessToken(String accessToken);

    /**
     * All all entries of the map as parameters of this request
     * @param parameters to be added to the request
     * @return itself
     */
    AuthenticationRequest addAuthenticationParameters(Map<String, Object> parameters);

}
