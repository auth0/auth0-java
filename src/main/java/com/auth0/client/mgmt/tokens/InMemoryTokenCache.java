package com.auth0.client.mgmt.tokens;

import java.util.concurrent.ConcurrentHashMap;

public class InMemoryTokenCache implements TokenCache {

    private ConcurrentHashMap<String, TokenStorageItem> cache = new ConcurrentHashMap<>();

    @Override
    public TokenStorageItem get(String key) {
        return cache.get(key);
    }

    @Override
    public void remove(String key) {
        cache.remove(key); // TODO return it too?
    }

    @Override
    public void set(String key, TokenStorageItem tokenStorageItem) {
        cache.put(key, tokenStorageItem);
    }
}
