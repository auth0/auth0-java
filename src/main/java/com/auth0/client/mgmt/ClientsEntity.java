package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ClientFilter;
import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.json.mgmt.client.Client;
import com.auth0.json.mgmt.client.ClientsPage;
import com.auth0.net.EmptyBodyRequest;
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
 * Class that provides an implementation of the Application methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Clients
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class ClientsEntity extends BaseManagementEntity {

    ClientsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all the Applications. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     * See https://auth0.com/docs/api/management/v2#!/Clients/get_clients
     *
     * @return a Request to execute.
     * @deprecated Calling this method will soon stop returning the complete list of clients and instead, limit to the first page of results.
     * Please use {@link #list(ClientFilter)} instead as it provides pagination support.
     */
    @Deprecated
    public Request<List<Client>> list() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .build()
                .toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<List<Client>>() {
        });
    }

    /**
     * Request all the Applications. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     * See https://auth0.com/docs/api/management/v2#!/Clients/get_clients
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<ClientsPage> list(ClientFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<ClientsPage>() {
        });
    }

    /**
     * Request an Application. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     * See https://auth0.com/docs/api/management/v2#!/Clients/get_clients_by_id
     *
     * @param clientId the application's client id.
     * @return a Request to execute.
     */
    public Request<Client> get(String clientId) {
        Asserts.assertNotNull(clientId, "client id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .addPathSegment(clientId)
                .build()
                .toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Client>() {
        });
    }

    /**
     * Request an Application. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     * See https://auth0.com/docs/api/management/v2#!/Clients/get_clients_by_id
     *
     * @param clientId the application's client id.
     * @param filter optional filter to restrict fields (to be included/excluded in response)
     * @return a Request to execute.
     */
    public Request<Client> get(String clientId, FieldsFilter filter) {
        Asserts.assertNotNull(clientId, "client id");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/clients")
            .addPathSegment(clientId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Client>() {
        });
    }

    /**
     * Create a new Application. A token with scope create:clients is needed.
     * See https://auth0.com/docs/api/management/v2#!/Clients/post_clients
     *
     * @param client the application data to set.
     * @return a Request to execute.
     */
    public Request<Client> create(Client client) {
        Asserts.assertNotNull(client, "client");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .build()
                .toString();
        BaseRequest<Client> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<Client>() {
        });
        request.setBody(client);
        return request;
    }

    /**
     * Delete an existing Application. A token with scope delete:clients is needed.
     * See https://auth0.com/docs/api/management/v2#!/Clients/delete_clients_by_id
     *
     * @param clientId the application's client id.
     * @return a Request to execute.
     */
    public Request<Void> delete(String clientId) {
        Asserts.assertNotNull(clientId, "client id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .addPathSegment(clientId)
                .build()
                .toString();
        return new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
    }

    /**
     * Update an existing Application. A token with scope update:clients is needed. If you also need to update the client_secret and encryption_key attributes the token must have update:client_keys scope.
     * See https://auth0.com/docs/api/management/v2#!/Clients/patch_clients_by_id
     *
     * @param clientId the application's client id.
     * @param client   the application data to set.
     * @return a Request to execute.
     */
    public Request<Client> update(String clientId, Client client) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(client, "client");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .addPathSegment(clientId)
                .build()
                .toString();
        BaseRequest<Client> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<Client>() {
        });
        request.setBody(client);
        return request;
    }

    /**
     * Rotates an Application's client secret. A token with scope update:client_keys is needed. Note that the generated secret is NOT base64 encoded.
     * See https://auth0.com/docs/api/management/v2#!/Clients/post_rotate_secret
     *
     * @param clientId the application's client id.
     * @return a Request to execute.
     */
    public Request<Client> rotateSecret(String clientId) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(client, "client");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .addPathSegment(clientId)
                .addPathSegment("rotate-secret")
                .build()
                .toString();
        return new EmptyBodyRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<Client>() {
        });
    }
}
