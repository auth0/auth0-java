package com.auth0.client.mgmt;

import com.auth0.Asserts;
import com.auth0.json.mgmt.client.ResourceServer;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

public class ResourceServersEntity extends BaseManagementEntity {

    ResourceServersEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the Resource Servers. A token with scope read:resource_servers is needed.
     *
     * @return a Request to execute.
     */
    public Request<List<ResourceServer>> listResourceServers() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .build()
                .toString();
        CustomRequest<List<ResourceServer>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<ResourceServer>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Resource Server. A token with scope read:resource_servers is needed.
     *
     * @param resourceServerId the id of the resource server to retrieve.
     * @return a Request to execute.
     */
    public Request<ResourceServer> getResourceServer(String resourceServerId) {
        Asserts.assertNotNull(resourceServerId, "resource server id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .addPathSegment(resourceServerId)
                .build()
                .toString();
        CustomRequest<ResourceServer> request = new CustomRequest<>(client, url, "GET", new TypeReference<ResourceServer>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Resource Server. A token with scope create:resource_servers is needed.
     *
     * @param resourceServer the resource server data to set
     * @return a Request to execute.
     */
    public Request<ResourceServer> createResourceServer(ResourceServer resourceServer) {
        Asserts.assertNotNull(resourceServer, "resource server");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .build()
                .toString();
        CustomRequest<ResourceServer> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<ResourceServer>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(resourceServer);
        return request;
    }

    /**
     * Delete an existing Resource Server. A token with scope delete:resource_servers is needed.
     *
     * @param resourceServerId the resource server id
     * @return a Request to execute.
     */
    public Request deleteResourceServer(String resourceServerId) {
        Asserts.assertNotNull(resourceServerId, "resource server id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .addPathSegment(resourceServerId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Resource Server. A token with scope update:resource_servers is needed.
     *
     * @param resourceServerId the resource server id
     * @param resourceServer   the resource server data to set. It can't include identifier.
     * @return a Request to execute.
     */
    public Request<ResourceServer> updateResourceServer(String resourceServerId, ResourceServer resourceServer) {
        Asserts.assertNotNull(resourceServerId, "resource server id");
        Asserts.assertNotNull(resourceServer, "resource server");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("resource-servers")
                .addPathSegment(resourceServerId)
                .build()
                .toString();
        CustomRequest<ResourceServer> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<ResourceServer>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(resourceServer);
        return request;
    }

}
