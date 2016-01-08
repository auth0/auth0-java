package com.auth0.api.callback;

import com.auth0.core.Token;
import com.auth0.core.UserProfile;

/**
 * Callback for authentication API calls to Auth0 API.
 */
public interface AuthenticationCallback extends Callback {

    /**
     * Called when authentication is successful.
     * It might include user's profile and token information.
     * @param profile User's profile information or null.
     * @param token User's token information (e.g. id_token).
     */
    void onSuccess(UserProfile profile, Token token);

}
