package com.auth0;

import okhttp3.HttpUrl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.Asserts.assertNotNull;
import static com.auth0.Asserts.assertValidUrl;

public class AuthorizeUrlBuilder {

    private final HttpUrl.Builder builder;
    private final HashMap<String, String> parameters;

    static AuthorizeUrlBuilder newInstance(String domain, String clientId, String redirectUrl) {
        return new AuthorizeUrlBuilder(domain, clientId, redirectUrl);
    }

    private AuthorizeUrlBuilder(String domain, String clientId, String redirectUrl) {
        assertValidUrl(domain, "domain");
        assertNotNull(clientId, "client id");
        assertValidUrl(redirectUrl, "redirect url");

        redirectUrl = urlEncode(redirectUrl);

        builder = HttpUrl.parse(domain).newBuilder();
        parameters = new HashMap<>();
        parameters.put("response_type", "code");
        parameters.put("client_id", clientId);
        parameters.put("redirect_url", redirectUrl);
    }

    public AuthorizeUrlBuilder withConnection(String connection) {
        assertNotNull(connection, "connection");
        parameters.put("connection", connection);
        return this;
    }

    public AuthorizeUrlBuilder withAudience(String audience) {
        assertNotNull(audience, "audience");
        parameters.put("audience", audience);
        return this;
    }

    public AuthorizeUrlBuilder withState(String state) {
        assertNotNull(state, "state");
        parameters.put("state", state);
        return this;
    }

    public AuthorizeUrlBuilder withScope(String scope) {
        assertNotNull(scope, "scope");
        parameters.put("scope", scope);
        return this;
    }

    public String build() {
        for (Map.Entry<String, String> p : parameters.entrySet()) {
            builder.addQueryParameter(p.getKey(), p.getValue());
        }
        return builder.build().toString();
    }

    private String urlEncode(String url) {
        try {
            URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
