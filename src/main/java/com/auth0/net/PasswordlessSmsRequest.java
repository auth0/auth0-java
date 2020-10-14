package com.auth0.net;

import com.auth0.json.auth.PasswordlessSmsResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;

/**
 * Represents a request to send a code via SMS to begin the authentication flow for passwordless connections.
 *
 * @see <a href="https://auth0.com/docs/connections/passwordless/guides/sms-otp">Passwordless Authentication with SMS documentation</a>
 * @see <a href="https://auth0.com/docs/api/authentication#get-code-or-link">Get code or link API reference documentation</a>
 */
public class PasswordlessSmsRequest extends CustomRequest<PasswordlessSmsResponse> {

    /**
     * Create a new instance.
     *
     * @param client The client to use for this request.
     * @param url The URL to make the request to.
     */
    public PasswordlessSmsRequest(OkHttpClient client, String url) {
        super(client, url, "POST", new TypeReference<PasswordlessSmsResponse>() {});
    }
}