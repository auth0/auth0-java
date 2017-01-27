package com.auth0.net;

import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;

/**
 * Class that represents an HTTP Request that can be executed.
 *
 * @param <T> the type of payload expected in the response after the execution.
 */
public interface Request<T> {

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws APIException   if the request was executed but the response wasn't successful.
     * @throws Auth0Exception if the request couldn't be created or executed successfully.
     */
    T execute() throws Auth0Exception;
}
