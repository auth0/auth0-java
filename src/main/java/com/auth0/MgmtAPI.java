package com.auth0;

import com.auth0.json.mgmt.clientgrant.ClientGrant;
import com.auth0.json.mgmt.client.Client;
import com.auth0.net.CustomRequest;
import com.auth0.net.EmptyBodyRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.util.List;

public class MgmtAPI {

    private final String baseUrl;
    private final String apiToken;
    private final OkHttpClient client;

    public MgmtAPI(String domain, String apiToken) {
        Asserts.assertNotNull(domain, "domain");
        Asserts.assertNotNull(apiToken, "api token");

        baseUrl = createBaseUrl(domain);
        if (baseUrl == null) {
            throw new IllegalArgumentException("The domain had an invalid format and couldn't be parsed as an URL.");
        }
        this.apiToken = apiToken;

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    String getBaseUrl() {
        return baseUrl;
    }

    private String createBaseUrl(String domain) {
        String url = domain;
        if (!domain.startsWith("https://") && !domain.startsWith("http://")) {
            url = "https://" + domain;
        }
        HttpUrl baseUrl = HttpUrl.parse(url);
        return baseUrl == null ? null : baseUrl.newBuilder().build().toString();
    }

    /**
     * Request all the Client Grants. A token with scope read:client_grants is needed.
     *
     * @return a Request to execute.
     */
    public Request<List<ClientGrant>> listClientGrants() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("client-grants")
                .build()
                .toString();
        CustomRequest<List<ClientGrant>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<ClientGrant>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Client Grant. A token with scope create:client_grants is needed.
     *
     * @return a Request to execute.
     */
    public Request<ClientGrant> createClientGrant(String clientId, String audience, String[] scope) {
        Asserts.assertNotNull(clientId, "client id");
        Asserts.assertNotNull(audience, "audience");
        Asserts.assertNotNull(scope, "scope");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("client-grants")
                .build()
                .toString();
        CustomRequest<ClientGrant> request = new CustomRequest<>(client, url, "POST", new TypeReference<ClientGrant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.addParameter("client_id", clientId);
        request.addParameter("audience", audience);
        request.addParameter("scope", scope);
        return request;
    }


    /**
     * Delete an existing Client Grant. A token with scope delete:client_grants is needed.
     *
     * @return a Request to execute.
     */
    public Request deleteClientGrant(String clientGrantId) {
        Asserts.assertNotNull(clientGrantId, "client grant id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("client-grants")
                .addPathSegment(clientGrantId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Client Grant scope. A token with scope update:client_grants is needed.
     *
     * @return a Request to execute.
     */
    public Request<ClientGrant> updateClientGrant(String clientGrantId, String[] scope) {
        Asserts.assertNotNull(clientGrantId, "client grant id");
        Asserts.assertNotNull(scope, "scope");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("client-grants")
                .addPathSegment(clientGrantId)
                .build()
                .toString();
        CustomRequest<ClientGrant> request = new CustomRequest<>(client, url, "PATCH", new TypeReference<ClientGrant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.addParameter("scope", scope);
        return request;
    }

    /**
     * Request all the Clients. A token with scope read:clients is needed. If you also need the client_secret and encryption_key attributes the token must have read:client_keys scope.
     *
     * @return a Request to execute.
     */
    public Request<List<Client>> listClients() {
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
     * @return a Request to execute.
     */
    public Request<Client> getClient(String clientId) {
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
     * @return a Request to execute.
     */
    public Request<Client> createClient(Client client) {
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
     * @return a Request to execute.
     */
    public Request deleteClient(String clientId) {
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
     * Update an existing Client Grant scope. A token with scope update:clients is needed. If you also need to update the client_secret and encryption_key attributes the token must have update:client_keys scope.
     *
     * @return a Request to execute.
     */
    public Request<Client> updateClient(String clientId, Client client) {
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
     * @return a Request to execute.
     */
    public Request<Client> rotateClientSecret(String clientId) {
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
