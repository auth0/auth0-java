package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ConnectionFilter;
import com.auth0.json.mgmt.Connection;
import com.auth0.json.mgmt.ConnectionsPage;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Connections methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Connections
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class ConnectionsEntity extends BaseManagementEntity {

    ConnectionsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }


    /**
     * Request all the ConnectionsEntity. A token with scope read:connections is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/get_connections
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<ConnectionsPage> listAll(ConnectionFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        BaseRequest<ConnectionsPage> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<ConnectionsPage>() {
        });
        return request;
    }


    /**
     * Request all the ConnectionsEntity. A token with scope read:connections is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/get_connections
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     * @deprecated Calling this method will soon stop returning the complete list of connections and instead, limit to the first page of results.
     * Please use {@link #listAll(ConnectionFilter)} instead as it provides pagination support.
     */
    @Deprecated
    public Request<List<Connection>> list(ConnectionFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                //This check below is to prevent JSON parsing errors
                if (!e.getKey().equalsIgnoreCase("include_totals")) {
                    builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
                }
            }
        }
        String url = builder.build().toString();
        BaseRequest<List<Connection>> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<List<Connection>>() {
        });
        return request;
    }

    /**
     * Request a Connection. A token with scope read:connections is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/get_connections_by_id
     *
     * @param connectionId the id of the connection to retrieve.
     * @param filter       the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<Connection> get(String connectionId, ConnectionFilter filter) {
        Asserts.assertNotNull(connectionId, "connection id");

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        BaseRequest<Connection> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<Connection>() {
        });
        return request;
    }

    /**
     * Create a Connection. A token with scope create:connections is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/post_connections
     *
     * @param connection the connection data to set.
     * @return a Request to execute.
     */
    public Request<Connection> create(Connection connection) {
        Asserts.assertNotNull(connection, "connection");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .build()
                .toString();
        BaseRequest<Connection> request = new BaseRequest<>(this.client, tokenProvider, url,  HttpMethod.POST, new TypeReference<Connection>() {
        });
        request.setBody(connection);
        return request;
    }

    /**
     * Delete an existing Connection. A token with scope delete:connections is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/delete_connections_by_id
     *
     * @param connectionId the connection id.
     * @return a Request to execute.
     */
    public Request<Void> delete(String connectionId) {
        Asserts.assertNotNull(connectionId, "connection id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .build()
                .toString();
        VoidRequest request =  new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
        return request;
    }

    /**
     * Update an existing Connection. A token with scope update:connections is needed. Note that if the 'options' value is present it will override all the 'options' values that currently exist.
     * See https://auth0.com/docs/api/management/v2#!/Connections/patch_connections_by_id
     *
     * @param connectionId the connection id.
     * @param connection   the connection data to set. It can't include name or strategy.
     * @return a Request to execute.
     */
    public Request<Connection> update(String connectionId, Connection connection) {
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(connection, "connection");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .build()
                .toString();
        BaseRequest<Connection> request = new BaseRequest<>(this.client, tokenProvider, url,  HttpMethod.PATCH, new TypeReference<Connection>() {
        });
        request.setBody(connection);
        return request;
    }

    /**
     * Delete an existing User from the given Database Connection. A token with scope delete:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/delete_users_by_email
     *
     * @param connectionId the connection id where the user is stored.
     * @param email        the email of the user to delete.
     * @return a Request to execute.
     */
    public Request<Void> deleteUser(String connectionId, String email) {
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(email, "email");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegment("users")
                .addQueryParameter("email", email)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(this.client, tokenProvider, url,  HttpMethod.DELETE);
        return request;
    }
}
