package com.auth0.net;

import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;

import java.util.concurrent.CompletableFuture;

/**
 * Class that represents an HTTP Request that can be executed.
 *
 * @param <T> the type of payload expected in the response after the execution.
 */
public interface Request<T> {

    /**
     * Executes this request synchronously.
     *
     * @return the response body JSON decoded as T
     * @throws APIException   if the request was executed but the response wasn't successful.
     * @throws Auth0Exception if the request couldn't be created or executed successfully.
     */
    T execute() throws Auth0Exception;

    /**
     * Executes this request asynchronously.
     *
     * @apiNote This method was added after the interface was released in version 1.0.
     *          It is defined as a default method for compatibility reasons.
     *          From version 2.0 on, the method will be abstract and all implementations of this interface
     *          will have to provide their own implementation.
     *
     * @implSpec The default implementation throws an {@linkplain UnsupportedOperationException}.
     *
     * @return a {@linkplain CompletableFuture} representing the specified request.
     */
    default CompletableFuture<T> executeAsync() {
        throw new UnsupportedOperationException("executeAsync");
    }
}
