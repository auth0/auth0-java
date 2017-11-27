package com.auth0.client.mgmt;

import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.client.mgmt.filter.ConnectionFilter;
import com.auth0.json.mgmt.Connection;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Clients methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Connections
 */
@SuppressWarnings("WeakerAccess")
public class ConnectionsEntity {

    private final RequestBuilder requestBuilder;

    ConnectionsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request all the ConnectionsEntity. A token with scope read:connections is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/get_connections
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<Connection>> list(ConnectionFilter filter) {
        return requestBuilder.get("api/v2/connections")
                             .queryParameters(filter)
                             .request(new TypeReference<List<Connection>>() {
                             });
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
        return requestBuilder.get("api/v2/connections", connectionId)
                             .queryParameters(filter)
                             .request(new TypeReference<Connection>() {
                             });
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

        return requestBuilder.post("api/v2/connections")
                             .body(connection)
                             .request(new TypeReference<Connection>() {
                             });
    }

    /**
     * Delete an existing Connection. A token with scope delete:connections is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/delete_connections_by_id
     *
     * @param connectionId the connection id.
     * @return a Request to execute.
     */
    public Request delete(String connectionId) {
        Asserts.assertNotNull(connectionId, "connection id");

        return requestBuilder.delete("api/v2/connections", connectionId)
                             .request();
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

        return requestBuilder.patch("api/v2/connections", connectionId)
                             .body(connection)
                             .request(new TypeReference<Connection>() {
                             });
    }

    /**
     * Delete an existing User from the given Database Connection. A token with scope delete:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Connections/delete_users_by_email
     *
     * @param connectionId the connection id where the user is stored.
     * @param email        the email of the user to delete.
     * @return a Request to execute.
     */
    public Request deleteUser(String connectionId, String email) {
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(email, "email");

        return requestBuilder.delete("api/v2/connections", connectionId, "users")
                             .queryParameter("email", email)
                             .request();
    }
}
