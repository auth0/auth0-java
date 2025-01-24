package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ConnectionFilter;
import com.auth0.json.mgmt.connections.*;
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
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<ConnectionsPage>() {
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
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Connection>() {
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .build()
                .toString();
        BaseRequest<Connection> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<Connection>() {
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
        return new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
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
        BaseRequest<Connection> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<Connection>() {
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
        return new VoidRequest(this.client, tokenProvider, url, HttpMethod.DELETE);
    }

    /**
     * Get the Connections Scim Configuration.
     * A token with scope read:scim_config is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/get-scim-configuration">https://auth0.com/docs/api/management/v2#!/connections/get-scim-configuration</a>
     * @param connectionId the connection id.
     * @return a Request to execute.
     */
    public Request<ScimConfigurationResponse> getScimConfiguration( String connectionId) {
        Asserts.assertNotNull(connectionId, "connection id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegment("scim-configuration")
                .build()
                .toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<ScimConfigurationResponse>() {
        });
    }

    /**
     * Delete the Connections Scim Configuration.
     * A token with scope delete:scim_config is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/delete-scim-configuration">https://auth0.com/docs/api/management/v2#!/connections/delete-scim-configuration</a>
     * @param connectionId the connection id.
     * @return a Request to execute.
     */
    public Request<Void> deleteScimConfiguration(String connectionId) {
        Asserts.assertNotNull(connectionId, "connection id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegment("scim-configuration")
                .build()
                .toString();
        return new VoidRequest(client, tokenProvider, url, HttpMethod.DELETE);
    }

    /**
     * Update the Connections Scim Configuration.
     * A token with scope update:scim_config is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/patch-scim-configuration">https://auth0.com/docs/api/management/v2#!/connections/patch-scim-configuration</a>
     * @param connectionId the connection id.
     * @param scimConfigurationRequest the scim configuration request.
     * @return a Request to execute.
     */
    public Request<ScimConfigurationResponse> updateScimConfiguration(String connectionId, ScimConfigurationRequest scimConfigurationRequest){
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(scimConfigurationRequest, "scim configuration request");
        Asserts.assertNotNull(scimConfigurationRequest.getUserIdAttribute(), "user id attribute");
        Asserts.assertNotNull(scimConfigurationRequest.getMapping(), "mapping");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegment("scim-configuration")
                .build()
                .toString();

        BaseRequest<ScimConfigurationResponse> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<ScimConfigurationResponse>() {
        });
        request.setBody(scimConfigurationRequest);
        return request;
    }

    /**
     * Create the Connections Scim Configuration.
     * A token with scope create:scim_config is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/post-scim-configuration">https://auth0.com/docs/api/management/v2#!/connections/post-scim-configuration</a>
     * @param connectionId the connection id.
     * @param request the scim configuration request.
     * @return a Request to execute.
     */
    public Request<ScimConfigurationResponse> createScimConfiguration(String connectionId, ScimConfigurationRequest request){
        Asserts.assertNotNull(connectionId, "connection id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegment("scim-configuration")
                .build()
                .toString();

        BaseRequest<ScimConfigurationResponse> baseRequest = new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<ScimConfigurationResponse>() {
        });
        baseRequest.setBody(request);
        return baseRequest;
    }

    /**
     * Get the Scim Configuration default mapping by its connection Id.
     * A token with scope read:scim_config is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/get-default-mapping">https://auth0.com/docs/api/management/v2#!/connections/get-default-mapping</a>
     * @param connectionId the connection id.
     * @return a Request to execute.
     */
    public Request<DefaultScimMappingResponse> getDefaultScimConfiguration(String connectionId){
        Asserts.assertNotNull(connectionId, "connection id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegment("scim-configuration")
                .addPathSegment("default-mapping")
                .build()
                .toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<DefaultScimMappingResponse>() {
        });
    }

    /**
     * Get the Connections Scim Tokens.
     * A token with scope read:scim_token is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/get-scim-tokens">https://auth0.com/docs/api/management/v2#!/connections/get-scim-tokens</a>
     * @param connectionId the connection id.
     * @return a Request to execute.
     */
    public Request<List<ScimTokenResponse>> getScimToken(String connectionId){
        Asserts.assertNotNull(connectionId, "connection id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegments("scim-configuration/tokens")
                .build()
                .toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<List<ScimTokenResponse>>() {
        });
    }

    /**
     * Create a Connections Scim Token.
     * A token with scope create:scim_token is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/post-scim-token">https://auth0.com/docs/api/management/v2#!/connections/post-scim-token</a>
     * @param connectionId the connection id.
     * @param scimTokenRequest the scim token request.
     * @return a Request to execute.
     */
    public Request<ScimTokenCreateResponse> createScimToken(String connectionId, ScimTokenRequest scimTokenRequest){
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(scimTokenRequest, "scim token request");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegments("scim-configuration/tokens")
                .build()
                .toString();

        BaseRequest<ScimTokenCreateResponse> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<ScimTokenCreateResponse>() {
        });
        request.setBody(scimTokenRequest);
        return request;
    }

    /**
     * Delete a Connections Scim Token.
     * A token with scope delete:scim_token is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/delete-tokens-by-token-id">https://auth0.com/docs/api/management/v2#!/connections/delete-tokens-by-token-id</a>
     * @param connectionId the connection id.
     * @param tokenId the token id.
     * @return a Request to execute.
     */
    public Request<Void> deleteScimToken(String connectionId, String tokenId){
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(tokenId, "token id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegments("scim-configuration/tokens")
                .addPathSegment(tokenId)
                .build()
                .toString();

        return new VoidRequest(client, tokenProvider, url, HttpMethod.DELETE);
    }

    /**
     * Check the Connection Status.
     * A token with scope read:connections is needed.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/connections/get-status">https://auth0.com/docs/api/management/v2#!/connections/get-status</a>
     * @param connectionId the connection id.
     * @return a Request to execute.
     */
    public Request<Void> checkConnectionStatus(String connectionId){
        Asserts.assertNotNull(connectionId, "connection id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/connections")
                .addPathSegment(connectionId)
                .addPathSegments("status")
                .build()
                .toString();

        return new VoidRequest(client, tokenProvider, url, HttpMethod.GET);
    }
}
