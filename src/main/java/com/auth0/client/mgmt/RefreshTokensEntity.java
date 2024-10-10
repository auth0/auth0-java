package com.auth0.client.mgmt;

import com.auth0.json.mgmt.refreshtokens.RefreshToken;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

/**
 * Class that provides an implementation of the Refresh Tokens methods of the Management API as defined in <a href="https://auth0.com/docs/api/management/v2#!/Refresh_Tokens">https://auth0.com/docs/api/management/v2#!/Refresh_Tokens</a>
 * <p>
 * This class is not thread-safe.
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class RefreshTokensEntity extends BaseManagementEntity{

    RefreshTokensEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request the refresh token for a given refresh token ID.
     * A token with scope {@code read:refresh_tokens} is needed.
     * See <a href="https://auth0.com/docs/api/management/v2/refresh-tokens/get-refresh-token">https://auth0.com/docs/api/management/v2/refresh-tokens/get-refresh-token</a>
     * @param refreshTokenId the refresh token ID.
     * @return a Request to execute.
     */
    public Request<RefreshToken> get(String refreshTokenId){
        Asserts.assertNotNull(refreshTokenId, "refresh token ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/refresh-tokens")
            .addPathSegment(refreshTokenId)
            .build()
            .toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<RefreshToken>() {
        });
    }

    /**
     * Delete the refresh token for a given refresh token ID.
     * * A token with scope {@code delete:refresh_tokens} is needed.
     * See <a href="https://auth0.com/docs/api/management/v2/refresh-tokens/delete-refresh-token">https://auth0.com/docs/api/management/v2/refresh-tokens/delete-refresh-token</a>
     * @param refreshTokenId the refresh token ID.
     * @return a Request to execute.
     */
    public Request<Void> delete(String refreshTokenId){
        Asserts.assertNotNull(refreshTokenId, "refresh token ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/refresh-tokens")
            .addPathSegment(refreshTokenId)
            .build()
            .toString();

        return new VoidRequest(client, tokenProvider, url, HttpMethod.DELETE);
    }
}
