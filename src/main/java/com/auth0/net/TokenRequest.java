package com.auth0.net;

import com.auth0.json.auth.TokenHolder;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * Class that represents an OAuth 2.0 Authentication/Authorization request. The execution will return a {@link TokenHolder}.
 */
public class TokenRequest extends BaseRequest<TokenHolder> {

    public TokenRequest(Auth0HttpClient client, String url) {
        super(client, null, url, HttpMethod.POST, new TypeReference<TokenHolder>() {});
    }

    /**
     * Setter for the realm value to request
     *
     * @param realm the realm to request
     * @return this request instance.
     */
    public TokenRequest setRealm(String realm) {
        super.addParameter("realm", realm);
        return this;
    }

    /**
     * Setter for the audience value to request
     *
     * @param audience the audience to request
     * @return this request instance.
     */
    public TokenRequest setAudience(String audience) {
        super.addParameter("audience", audience);
        return this;
    }

    /**
     * Setter for the scope value to request
     *
     * @param scope the scope to request
     * @return this request instance.
     */
    public TokenRequest setScope(String scope) {
        super.addParameter("scope", scope);
        return this;
    }

    /**
     * Setter for the login hint value to request. For the Token Vault federated connection
     * token exchange, this is the user's ID within the identity provider specified by the
     * connection (for example, the Google user ID when the connection is {@code google-oauth2}).
     *
     * @param loginHint the login hint to request
     * @return this request instance.
     */
    public TokenRequest setLoginHint(String loginHint) {
        super.addParameter("login_hint", loginHint);
        return this;
    }
}
