package com.auth0.client.mgmt;

import com.auth0.json.mgmt.sessions.Session;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;


/**
 * Class that provides an implementation of the Sessions methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Sessions
 * <p>
 * This class is not thread-safe.
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class SessionsEntity extends BaseManagementEntity{

    SessionsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request the session for a given session ID.
     * A token with scope {@code read:sessions} is needed.
     * See <a href="https://auth0.com/docs/api/management/v2/sessions/get-session">https://auth0.com/docs/api/management/v2/sessions/get-session</a>
     * @param sessionId the session ID.
     * @return a Request to execute.
     */
    public Request<Session> get(String sessionId){
        Asserts.assertNotNull(sessionId, "session ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/sessions")
            .addPathSegment(sessionId)
            .build()
            .toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Session>() {
            });
    }

    /**
     * Delete the session for a given session ID.
     * A token with scope {@code delete:sessions} is needed.
     * See <a href="https://auth0.com/docs/api/management/v2/sessions/delete-session">https://auth0.com/docs/api/management/v2/sessions/delete-session</a>
     * @param sessionId the session ID.
     * @return a Request to execute.
     */
    public Request<Void> delete(String sessionId){
        Asserts.assertNotNull(sessionId, "session ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/sessions")
            .addPathSegment(sessionId)
            .build()
            .toString();

        return new VoidRequest(client, tokenProvider, url, HttpMethod.DELETE);
    }
}
