package com.auth0;

public interface Request<T> {

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws RequestFailedException if the request execution fails.
     */
    T execute() throws RequestFailedException;
}
