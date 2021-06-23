package com.auth0.client.mgmt.tokens;

public interface TokenCache {

    TokenStorageItem get(String key);
    void remove(String key);
    void set(String  key, TokenStorageItem tokenStorageItem);
}
