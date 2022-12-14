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
     * @return a {@link Response} containing information about the response.
     * @throws APIException   if the request was executed but the response wasn't successful.
     * @throws Auth0Exception if the request couldn't be created or executed successfully.
     */
    Response<T> execute() throws Auth0Exception;

    /**
     * Executes this request asynchronously.
     *
     * Note: This method was added after the interface was released in version 1.0.
     * It is defined as a default method for compatibility reasons.
     * From version 2.0 on, the method will be abstract and all implementations of this interface
     * will have to provide their own implementation.
     *
     * The default implementation throws an {@linkplain UnsupportedOperationException}.
     *
     * @return a {@linkplain CompletableFuture} representing the specified request.
     */
    default CompletableFuture<Response<T>> executeAsync() {
        throw new UnsupportedOperationException("executeAsync");
    }

    /**
     * Adds an HTTP header to the request
     *
     * @param name  the name of the header
     * @param value the value of the header
     * @return this same request instance
     */
    Request<T> addHeader(String name, String value);

    /**
     * Adds an body parameter to the request
     *
     * @param name  the name of the parameter
     * @param value the value of the parameter
     * @return this same request instance
     */
    Request<T> addParameter(String name, Object value);

    /**
     * Sets the response's body directly
     *
     * @param body the value to set as body
     * @return this same request instance
     */
    Request<T> setBody(Object body);
}
