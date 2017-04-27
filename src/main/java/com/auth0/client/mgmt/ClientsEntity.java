package com.auth0.client.mgmt;

import com.auth0.json.mgmt.client.Client;
import com.auth0.net.CustomRequest;
import com.auth0.net.EmptyBodyRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

/**
 * Class that provides an implementation of the Clients methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Clients
 */
@SuppressWarnings("WeakerAccess")
public class ClientsEntity extends BaseManagementEntity {

    ClientsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the ClientsEntity. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     * See https://auth0.com/docs/api/management/v2#!/Clients/get_clients
     *
     * @return a Request to execute.
     */
    public Request<List<Client>> list() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .build()
                .toString();
        CustomRequest<List<Client>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Client>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Client. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     * See https://auth0.com/docs/api/management/v2#!/Clients/get_clients_by_id
     *
     * @param clientId the client id.
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
        CustomRequest<Client> request = new CustomRequest<>(client, url, "GET", new TypeReference<Client>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Client. A token with scope create:clients is needed.
     * See https://auth0.com/docs/api/management/v2#!/Clients/post_clients
     *
     * @param client the client data to set.
     * @return a Request to execute.
     */
    public Request<Client> create(Client client) {
        Asserts.assertNotNull(client, "client");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .build()
                .toString();
        CustomRequest<Client> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<Client>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(client);
        return request;
    }

    /**
     * Delete an existing Client. A token with scope delete:clients is needed.
     * See https://auth0.com/docs/api/management/v2#!/Clients/delete_clients_by_id
     *
     * @param clientId the client id.
     * @return a Request to execute.
     */
    public Request delete(String clientId) {
        Asserts.assertNotNull(clientId, "client id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/clients")
                .addPathSegment(clientId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Client. A token with scope update:clients is needed. If you also need to update the client_secret and encryption_key attributes the token must have update:client_keys scope.
     * See https://auth0.com/docs/api/management/v2#!/Clients/patch_clients_by_id
     *
     * @param clientId the client id.
     * @param client   the client data to set.
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
        CustomRequest<Client> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<Client>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(client);
        return request;
    }

    /**
     * Rotates a Client secret. A token with scope update:client_keys is needed. Note that the generated secret is NOT base64 encoded.
     * See https://auth0.com/docs/api/management/v2#!/Clients/post_rotate_secret
     *
     * @param clientId the client id.
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
        CustomRequest<Client> request = new EmptyBodyRequest<>(this.client, url, "POST", new TypeReference<Client>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
}
