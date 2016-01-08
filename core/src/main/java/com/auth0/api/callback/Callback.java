package com.auth0.api.callback;

/**
 * Interface for all callbacks used with Auth0 API clients
 */
public interface Callback {

    /**
     * Method called on Auth0 API request failure
     * @param error Error with the reason of the failure
     */
    void onFailure(Throwable error);

}
