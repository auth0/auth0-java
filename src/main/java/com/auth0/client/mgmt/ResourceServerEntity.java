package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ResourceServersFilter;
import com.auth0.json.mgmt.ResourceServer;
import com.auth0.json.mgmt.ResourceServersPage;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Resource Server methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Resource_Servers
 */
public class ResourceServerEntity extends BaseManagementEntity {

    ResourceServerEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Creates request to fetch all resource servers.
     * See <a href=https://auth0.com/docs/api/management/v2#!/Resource_Servers/get_resource_servers>API documentation</a>
     *
     * @param filter the filter to use. Can be null.
     * @return request to execute
     */
    public Request<ResourceServersPage> list(ResourceServersFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/resource-servers");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }

        String url = builder.build().toString();
        CustomRequest<ResourceServersPage> request = new CustomRequest<>(client, url, "GET",
                new TypeReference<ResourceServersPage>() {
                });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Creates request to fetch all resource servers.
     * See <a href=https://auth0.com/docs/api/management/v2#!/Resource_Servers/get_resource_servers>API documentation</a>
     *
     * @return request to execute
     * @deprecated Calling this method will soon stop returning the complete list of resource servers and instead, limit to the first page of results.
     * Please use {@link #list(ResourceServersFilter)} instead as it provides pagination support.
     */
    @Deprecated
    public Request<List<ResourceServer>> list() {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/resource-servers");

        String url = builder.build().toString();
        CustomRequest<List<ResourceServer>> request = new CustomRequest<>(client, url, "GET",
                new TypeReference<List<ResourceServer>>() {
                });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/resource-servers")
                .addPathSegment(resourceServerIdOrIdentifier);

        String url = builder.build().toString();
        CustomRequest<ResourceServer> request = new CustomRequest<>(client, url, "GET",
                new TypeReference<ResourceServer>() {
                });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/resource-servers");

        String url = builder.build().toString();
        CustomRequest<ResourceServer> request = new CustomRequest<>(client, url, "POST",
                new TypeReference<ResourceServer>() {
                });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(resourceServer);
        return request;
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

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/resource-servers")
                .addPathSegment(resourceServerId);

        String url = builder.build().toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/resource-servers")
                .addPathSegment(resourceServerId);

        String url = builder.build().toString();
        CustomRequest<ResourceServer> request = new CustomRequest<ResourceServer>(client, url, "PATCH",
                new TypeReference<ResourceServer>() {
                });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(resourceServer);
        return request;
    }
}
