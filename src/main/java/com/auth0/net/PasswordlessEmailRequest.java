package com.auth0.net;

import com.auth0.json.auth.PasswordlessEmailResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;

import java.util.Map;

/**
 * Represents a request to send a code or link via email to begin the authentication flow for passwordless connections.
 *
 * @see <a href="https://auth0.com/docs/connections/passwordless/guides/email-otp">Passwordless Authentication with Email documentation</a>
 * @see <a href="https://auth0.com/docs/api/authentication#get-code-or-link">Get code or link API reference documentation</a>
 */
public class PasswordlessEmailRequest extends CustomRequest<PasswordlessEmailResponse> {

    /**
     * Create a new instance.
     *
     * @param client The client to use for this request.
     * @param url The URL to make the request to.
     */
    public PasswordlessEmailRequest(OkHttpClient client, String url) {
        super(client, url, "POST", new TypeReference<PasswordlessEmailResponse>() {});
    }

    /**
     * Set additional authorization params to append or override the link parameters (only applicable when sending a
     * link to the user)
     *
     * @param authParams The link parameters (like scope, redirect_uri, protocol, response_type), to override or send when you send a link using email.
     * @return this instance.
     *
     * @see com.auth0.client.auth.AuthAPI#startPasswordlessEmailFlow(String, com.auth0.client.auth.PasswordlessEmailType)
     */
    public PasswordlessEmailRequest setAuthParams(Map<String, String> authParams) {
        super.addParameter("authParams", authParams);
        return this;
    }
}
