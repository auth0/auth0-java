/*
 * UsersAPIClient.java
 *
 * Copyright (c) 2016 Auth0 (http://auth0.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.auth0.management.users;


import com.auth0.Auth0;
import com.auth0.management.result.LogsListPage;
import com.auth0.management.result.User;
import com.auth0.management.result.UsersListPage;
import com.auth0.request.ParameterizableRequest;
import com.auth0.request.Request;
import com.auth0.request.internal.RequestFactory;
import com.google.gson.Gson;
import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.Map;


/**
 * API client for Auth0 Users Management API.
 *
 * @see <a href="https://auth0.com/docs/api/management/v2#!/Users/get_users">Users management API docs</a>
 */
public class UsersAPIClient {

    private final Auth0 auth0;
    private final OkHttpClient client;
    private final Gson gson;
    private final RequestFactory factory;
    private final String token;

    public UsersAPIClient(Auth0 auth0, OkHttpClient client, Gson gson, RequestFactory factory, String token) {
        this.auth0 = auth0;
        this.client = client;
        this.gson = gson;
        this.factory = factory;
        this.token = token;
    }

    /**
     * Gets a user
     *
     * @param userId the 'user_id' of the user to retrieve
     * @return an {@link UserRequest} to configure and execute
     */
    public UserRequest get(String userId) {
        HttpUrl url = getUserUrl(userId)
                .build();

        ParameterizableRequest<User> request = factory.GET(url, client, gson, User.class)
                .setBearer(token);

        return new UserRequest(request);
    }

    /**
     * Updates a user
     *
     * @param userId the 'user_id' of the user to update
     * @return an {@link UpdateUserRequest} to configure and execute
     */
    public UpdateUserRequest update(String userId) {
        HttpUrl url = getUserUrl(userId).build();

        ParameterizableRequest<User> request = factory.PATCH(url, client, gson, User.class)
                .setBearer(token);

        return new UpdateUserRequest(request);
    }

    /**
     * Deletes a user
     *
     * @param userId the 'user_id' of the user to delete
     * @return a {@link Request} to execute
     */
    public Request<Void> delete(String userId) {
        HttpUrl url = getUserUrl(userId).build();

        return factory.DELETE(url, client, gson)
                .setBearer(token);
    }

    /**
     * Links the user with a secondary account
     *
     * @param userId the 'user_id' of the user
     * @return a {@link LinkUserRequest} to configure and execute
     */
    public LinkUserRequest link(String userId) {
        HttpUrl url = getUserUrl(userId)
                .addPathSegment("identities")
                .build();

        ParameterizableRequest<List<Map<String, Object>>> request = factory.rawPOSTList(url, client, gson)
                .setBearer(token);

        return new LinkUserRequest(request);
    }

    /**
     * Unlinks the user from a secondary account
     *
     * @param userId the 'user_id' of the user
     * @return an {@link UnlinkUserRequest} to configure and execute
     */
    public UnlinkUserRequest unlink(String userId) {
        return new UnlinkUserRequest(auth0, client, gson, factory, token, userId);
    }

    /**
     * Deletes a user's multifactor provider
     *
     * @param userId the 'user_id' of the user
     * @return an {@link DeleteUserMultifactorProviderRequest} to configure and execute
     */
    public DeleteUserMultifactorProviderRequest multifactor(String userId) {
        return new DeleteUserMultifactorProviderRequest(auth0, client, gson, factory, token, userId);
    }

    /**
     * Obtains a list of logs of a user
     *
     * @param userId the 'user_id' of the user
     * @return an {@link UserLogsRequest} to configure and execute
     */
    public UserLogsRequest logs(String userId) {
        HttpUrl url = getUserUrl(userId)
                .addPathSegment("logs")
                .build();

        ParameterizableRequest<LogsListPage> request = factory.GET(url, client, gson, LogsListPage.class)
                .setBearer(token);

        return new UserLogsRequest(request);
    }

    /**
     * Obtains a list of users
     *
     * @return an {@link UserListRequest} to configure and execute
     */
    public UserListRequest list() {
        HttpUrl url = HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .build();

        ParameterizableRequest<UsersListPage> request = factory.GET(url, client, gson, UsersListPage.class)
                .setBearer(token);

        return new UserListRequest(request);
    }

    private HttpUrl.Builder getUserUrl(String userId) {
        return HttpUrl.parse(auth0.getDomainUrl()).newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("users")
                .addPathSegment(userId);
    }
}
