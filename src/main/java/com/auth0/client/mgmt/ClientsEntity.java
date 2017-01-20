package com.auth0.client.mgmt;

import com.auth0.Asserts;
import com.auth0.json.mgmt.client.Client;
import com.auth0.net.CustomRequest;
import com.auth0.net.EmptyBodyRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

public class ClientsEntity extends BaseManagementEntity {

    ClientsEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the ClientsEntity. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     *
     * @return a Request to execute.
     */
    public Request<List<Client>> list() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
                .build()
                .toString();
        CustomRequest<List<Client>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Client>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Client. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     *
     * @param clientId the client id.
     * @return a Request to execute.
     */
    public Request<Client> get(String clientId) {
        Asserts.assertNotNull(clientId, "client id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
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
     *
     * @param client the client data to set.
     * @return a Request to execute.
     */
    public Request<Client> create(Client client) {
        Asserts.assertNotNull(client, "client");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
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
     *
     * @param clientId the client id.
     * @return a Request to execute.
     */
    public Request delete(String clientId) {
        Asserts.assertNotNull(clientId, "client id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
                .addPathSegment(clientId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Client. A token with scope update:clients is needed. If you also need to update the client_secret and encryption_key attributes the token must have update:client_keys scope.
     *
     * @param clientId the client id.
     * @param client   the client data to set.
     * @return a Request to execute.
     */
    public Request<Client> update(String clientId, Client client) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(client, "client");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
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
     *
     * @param clientId the client id.
     * @return a Request to execute.
     */
    public Request<Client> rotateSecret(String clientId) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(client, "client");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("clients")
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
