package com.auth0.client.mgmt.tokens;

import com.auth0.client.auth.AuthAPI;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.auth.TokenHolder;
import com.auth0.net.AuthRequest;

import java.time.Instant;

public class CachedTokenProvider implements TokenProvider {

    private final String domain;
    private final String clientId;
    private final String clientSecret;
    private final TokenCache tokenCache;
    private final AuthAPI authAPI;

    public CachedTokenProvider(String domain, String clientId, String clientSecret, TokenCache tokenCache) {
        this.domain = domain;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.tokenCache = tokenCache;

        // TODO allow customers to reuse an existing AuthAPI instance?
        this.authAPI = new AuthAPI(domain, clientId, clientSecret);
    }

    @Override
    public String getToken() {
        String cacheKey = this.domain + "-" + this.clientId;
        TokenStorageItem tokenStorageItem = tokenCache.get(cacheKey);

        if (tokenStorageItem == null || !isValid(tokenStorageItem)) {

            if (tokenStorageItem != null) {
                tokenCache.remove(cacheKey);
            }

            AuthRequest authRequest = authAPI.requestToken("https://" + this.domain + "/api/v2/");
            TokenHolder holder;
            try {
                holder = authRequest.execute();
            } catch (Auth0Exception e) {
                // TODO need to figure out how to handle exception if getting a token fails
                //  Don't want exception to bubble all the way up to every mgmt API call, but don't want to be
                //  inconsistent and want to allow customers to reasonably handle the  exception case...
                throw new IllegalStateException("UGGGGGGGHHHHH", e);
            }

//            TokenStorageItem item = new TokenStorageItem(holder.getAccessToken(), Instant.now().plusSeconds(holder.getExpiresIn()));
            TokenStorageItem item = new TokenStorageItem();
            item.setTokenResponse(holder.getAccessToken());
            item.setExpiresAt(Instant.now().plusSeconds(holder.getExpiresIn()));
            tokenCache.set(cacheKey, item);
            return holder.getAccessToken();
        }

        return tokenStorageItem.getTokenResponse();
    }

    private boolean isValid(TokenStorageItem tokenStorageItem) {
        // TODO calculate if the expiresAt is greater than 60 seconds from now
        return true;

//        Duration leeway = Duration.ofSeconds(60);
//        Instant nowPlusLeeway = Instant.now().plusSeconds(60);
//
//        Duration between = Duration.between(nowPlusLeeway, tokenStorageItem.getExpiresAt());
//
//        if (between.toSeconds() < 60) {
//
//        }

//        int leeway = 60;
//
//        Duration.between(Instant.now(), Instant.of)
//
//        Instant diff = Instant.now().minusSeconds(leeway);
//
//        return diff.is
//        Instant.now().minutokenStorageItem.getExpiresAt();
//
//        return tokenStorageItem.getExpiresAt().
    }
}
