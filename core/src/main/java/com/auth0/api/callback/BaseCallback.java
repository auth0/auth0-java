package com.auth0.api.callback;

/**
 * Callback that receives a single value on success.
 */
public interface BaseCallback<T> extends Callback {

    /**
     * Method called on success with the payload or null.
     * @param payload Request payload or null
     */
    void onSuccess(T payload);

}
