package com.auth0.authentication;

import com.auth0.Auth0Exception;
import com.auth0.callback.BaseCallback;
import com.auth0.request.ParameterizableRequest;

import java.util.Map;

/**
 * Request to perform a non-authentication related action
 * like creating a user or requesting a change password
 */
public class DatabaseConnectionRequest<T> {

    private final ParameterizableRequest<T> request;

    public DatabaseConnectionRequest(ParameterizableRequest<T> request) {
        this.request = request;
    }

    /**
     * Add the given parameters to the request
     * @param parameters to be sent with the request
     * @return itself
     */
    public DatabaseConnectionRequest<T> addParameters(Map<String, Object> parameters) {
        request.addParameters(parameters);
        return this;
    }

    /**
     * Add a parameter by name to the request
     * @param name of the parameter
     * @param value of the parameter
     * @return itself
     */
    public DatabaseConnectionRequest<T> addParameter(String name, Object value) {
        request.addParameter(name, value);
        return this;
    }

    /**
     * Add a header for the request, e.g. "Authorization"
     * @param name of the header
     * @param value of the header
     * @return itself
     */
    public DatabaseConnectionRequest<T> addHeader(String name, String value) {
        request.addHeader(name, value);
        return this;
    }

    /**
     * Set the Auth0 Database Connection used for this request using its name.
     * @param connection name
     * @return itself
     */
    public DatabaseConnectionRequest<T> setConnection(String connection) {
        request.addParameter(ParameterBuilder.CONNECTION_KEY, connection);
        return this;
    }

    /**
     * Executes the request async and returns its results via callback
     * @param callback called on success or failure of the request
     */
    public void start(BaseCallback<T> callback) {
        request.start(callback);
    }

    /**
     * Executes the request synchronously
     * @return the request result
     * @throws Auth0Exception if the request failed
     */
    public T execute() throws Auth0Exception {
        return request.execute();
    }
}
