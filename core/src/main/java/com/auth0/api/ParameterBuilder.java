package com.auth0.api;

import android.os.Build;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.util.CheckHelper.checkArgument;

/**
 * Builder class for Auth API parameters.
 */
public class ParameterBuilder {

    public static final String SCOPE_OPENID = "openid";
    public static final String SCOPE_OFFLINE_ACCESS = "openid offline_access";
    public static final String ACCESS_TOKEN = "access_token";
    public static final String CONNECTION = "connection";

    public static final String GRANT_TYPE_PASSWORD = "password";
    public static final String GRANT_TYPE_JWT = "urn:ietf:params:oauth:grant-type:jwt-bearer";

    private Map<String, Object> parameters;

    /**
     * Creates a new builder
     */
    public ParameterBuilder() {
        this.parameters = new HashMap<>();
        setScope(SCOPE_OFFLINE_ACCESS);
    }

    /**
     * Creates a new builder with default parameters
     * @param parameters default parameters
     */
    public ParameterBuilder(Map<String, Object> parameters) {
        checkArgument(parameters != null, "Must provide non-null parameters");
        this.parameters = new HashMap<>(parameters);
    }

    /**
     * Sets the 'client_id' parameter
     * @param clientId clientID
     * @return itself
     */
    public com.auth0.api.ParameterBuilder setClientId(String clientId) {
        return set("client_id", clientId);
    }

    /**
     * Sets the 'grant_type' parameter
     * @param grantType grant type
     * @return itself
     */
    public com.auth0.api.ParameterBuilder setGrantType(String grantType) {
        return set("grant_type", grantType);
    }

    /**
     * Sets the 'connection' parameter
     * @param connection name of the connection
     * @return itself
     */
    public com.auth0.api.ParameterBuilder setConnection(String connection) {
        return set(CONNECTION, connection);
    }

    /**
     * Sets the 'scope' parameter. If the scope includes 'offline_access', it will set the 'device' parameter.
     * @param scope a scope value
     * @return itself
     */
    public com.auth0.api.ParameterBuilder setScope(String scope) {
        if (scope.contains("offline_access")) {
            setDevice(Build.MODEL);
        } else {
            setDevice(null);
        }
        return set("scope", scope);
    }

    /**
     * Sets the 'device' parameter
     * @param device a device name
     * @return itself
     */
    public com.auth0.api.ParameterBuilder setDevice(String device) {
        return set("device", device);
    }

    /**
     * Sets the 'access_token' parameter
     * @param accessToken a access token
     * @return itself
     */
    public com.auth0.api.ParameterBuilder setAccessToken(String accessToken) {
        return set(ACCESS_TOKEN, accessToken);
    }

    /**
     * Sets a parameter
     * @param key parameter name
     * @param value parameter value
     * @return itself
     */
    public com.auth0.api.ParameterBuilder set(String key, Object value) {
        this.parameters.put(key, value);
        return this;
    }

    /**
     * Adds all parameter from a map
     * @param parameters map with parameters to add
     * @return itself
     */
    public com.auth0.api.ParameterBuilder addAll(Map<String, Object> parameters) {
        if (parameters != null) {
            this.parameters.putAll(parameters);
        }
        return this;
    }

    /**
     * Clears all existing parameters
     * @return itself
     */
    public com.auth0.api.ParameterBuilder clearAll() {
        parameters.clear();
        return this;
    }

    /**
     * Create a {@link Map} with all the parameters
     * @return a new map with the parameters
     */
    public Map<String, Object> asDictionary() {
        return new HashMap<>(this.parameters);
    }

    /**
     * Creates a new instance of the builder with default values
     * @return a new builder
     */
    public static com.auth0.api.ParameterBuilder newBuilder() {
        return new com.auth0.api.ParameterBuilder();
    }

    /**
     * Creates a new instance of the builder without any default values
     * @return a new builder
     */
    public static com.auth0.api.ParameterBuilder newEmptyBuilder() {
        return new com.auth0.api.ParameterBuilder(new HashMap<String, Object>());
    }

    /**
     * Creates a new instance of the builder with parameters.
     * @param parameters default parameters
     * @return a new builder
     */
    public static com.auth0.api.ParameterBuilder newBuilder(Map<String, Object> parameters) {
        return new com.auth0.api.ParameterBuilder(parameters);
    }

}
