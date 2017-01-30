package com.auth0.net;

import com.auth0.json.auth.TokenHolder;

/**
 * Class that represents an OAuth 2.0 Authentication/Authorization request. The execution will return a {@link TokenHolder}.
 */
public interface AuthRequest extends Request<TokenHolder> {

    /**
     * Setter for the realm value to request
     *
     * @param realm the realm to request
     * @return this request instance.
     */
    AuthRequest setRealm(String realm);

    /**
     * Setter for the audience value to request
     *
     * @param audience the audience to request
     * @return this request instance.
     */
    AuthRequest setAudience(String audience);

    /**
     * Setter for the scope value to request
     *
     * @param scope the scope to request
     * @return this request instance.
     */
    AuthRequest setScope(String scope);
}
