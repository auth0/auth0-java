package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import com.auth0.exception.RequestFailedException;

public interface Request<T> {

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws Auth0Exception if the request execution fails.
     */
    T execute() throws Auth0Exception;
}
