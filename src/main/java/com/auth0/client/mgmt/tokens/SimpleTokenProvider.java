package com.auth0.client.mgmt.tokens;

public class SimpleTokenProvider implements TokenProvider {

    private String apiToken;

    public SimpleTokenProvider(String apiToken) {
        this.apiToken = apiToken;
    }

    @Override
    public String getToken() {
        return apiToken;
    }

    public void setToken(String apiToken) {
        this.apiToken = apiToken;
    }
}
