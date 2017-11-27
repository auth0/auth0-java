package com.auth0.client.mgmt;

import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.json.mgmt.ResourceServer;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Resource Server methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Resource_Servers
 */
public class ResourceServerEntity  {
    private final RequestBuilder requestBuilder;

    ResourceServerEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Creates request to fetch all resource servers.
     * See <a href=https://auth0.com/docs/api/management/v2#!/Resource_Servers/get_resource_servers>API documentation</a>
     *
     * @return request to execute
     */
    public Request<List<ResourceServer>> list() {

        return requestBuilder.get("api/v2/resource-servers")
                             .request(new TypeReference<List<ResourceServer>>() {
                             });
    }

    /**
     * Cretes request for fetching single resource server by it's ID.
     * See <a href=https://auth0.com/docs/api/management/v2#!/Resource_Servers/get_resource_servers_by_id>API documentation</a>
     *
     * @param resourceServerIdOrIdentifier {@link ResourceServer#id} or {@link ResourceServer#identifier} (audience) field
     * @return request to execute
     */
    public Request<ResourceServer> get(String resourceServerIdOrIdentifier) {
        Asserts.assertNotNull(resourceServerIdOrIdentifier, "Resource server ID");

        return requestBuilder.get("api/v2/resource-servers", resourceServerIdOrIdentifier)
                             .request(new TypeReference<ResourceServer>() {
                             });
    }

    /**
     * Cretes request for creation resource server
     * See <a href=https://auth0.com/docs/api/management/v2#!/Resource_Servers/post_resource_servers>API documentation</a>
     *
     * @param resourceServer resource server body
     * @return request to execute
     */
    public Request<ResourceServer> create(ResourceServer resourceServer) {
        Asserts.assertNotNull(resourceServer, "Resource server");

        return requestBuilder.post("api/v2/resource-servers")
                             .body(resourceServer)
                             .request(new TypeReference<ResourceServer>() {
                             });
    }

    /**
     * Creates request for delete resource server by it's ID
     * See <a href=https://auth0.com/docs/api/management/v2#!/Resource_Servers/delete_resource_servers_by_id>API documentation</a>
     *
     * @param resourceServerId {@link ResourceServer#id} field
     * @return request to execute
     */
    public Request<Void> delete(String resourceServerId) {
        Asserts.assertNotNull(resourceServerId, "Resource server ID");

        return requestBuilder.delete("api/v2/resource-servers", resourceServerId)
                             .request();
    }

    /**
     * Creates request for partial update of resource server. All null fields stay not changed.
     * See <a href=https://auth0.com/docs/api/management/v2#!/Resource_Servers/patch_resource_servers_by_id>API documentation</a>
     *
     * @param resourceServerId {@link ResourceServer#id} field
     * @param resourceServer   {@link ResourceServer} body
     * @return request to execute
     */
    public Request<ResourceServer> update(String resourceServerId, ResourceServer resourceServer) {
        Asserts.assertNotNull(resourceServerId, "resourceServerId");
        Asserts.assertNotNull(resourceServer, "resourceServer");

        return requestBuilder.patch("api/v2/resource-servers", resourceServerId)
                             .body(resourceServer)
                             .request(new TypeReference<ResourceServer>() {
                             });
    }
}
