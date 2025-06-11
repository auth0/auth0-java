package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.NetworkAclsFilter;
import com.auth0.json.mgmt.networkacls.NetworkAcls;
import com.auth0.json.mgmt.networkacls.NetworkAclsPage;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.Map;

public class NetworkAclsEntity extends BaseManagementEntity {

    private final static String ORGS_PATH = "api/v2/network-acls";

    NetworkAclsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Lists all Network ACLs for the Auth0 tenant.
     * This method allows you to filter the results using the provided {@link NetworkAclsFilter}.
     * A token with scope read:network_acls is needed.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/network-acls/get-network-acls">https://auth0.com/docs/api/management/v2#!/network-acls/get-network-acls</a>
     * @param filter the filter to apply to the request, can be null.
     * @return a Request that can be executed to retrieve a page of Network ACLs.
     */
    public Request<NetworkAclsPage> list(NetworkAclsFilter filter) {
        HttpUrl.Builder builder = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH);

        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }

        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<NetworkAclsPage>(){
        });
    }

    /**
     * Creates a new Network ACL for the Auth0 tenant.
     * A token with scope create:network_acls is needed.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/network-acls/post-network-acls">https://auth0.com/docs/api/management/v2#!/network-acls/post-network-acls</a>
     * @param networkAcls the Network ACL to create.
     * @return a Request that can be executed to create the Network ACL.
     */
    public Request<NetworkAcls> create(NetworkAcls networkAcls) {
        Asserts.assertNotNull(networkAcls, "network acls");
        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .build().toString();
        BaseRequest<NetworkAcls> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<NetworkAcls>(){});
        request.setBody(networkAcls);
        return request;
    }

    /**
     * Get a Network ACL by its ID.
     * A token with scope read:network_acls is needed.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/network-acls/get-network-acls-by-id">https://auth0.com/docs/api/management/v2#!/network-acls/get-network-acls-by-id</a>
     * @param id the ID of the Network ACL to delete.
     * @return a Request that can be executed to delete the Network ACL.
     */
    public Request<NetworkAcls> get(String id) {
        Asserts.assertNotNull(id, "id");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .build().toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<NetworkAcls>(){});
    }

    /**
     * Deletes a Network ACL by its ID.
     * A token with scope delete:network_acls is needed.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/network-acls/delete-network-acls-by-id">https://auth0.com/docs/api/management/v2#!/network-acls/delete-network-acls-by-id</a>
     * @param id the ID of the Network ACL to delete.
     * @return a Request that can be executed to delete the Network ACL.
     */
    public Request<Void> delete(String id) {
        Asserts.assertNotNull(id, "id");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .build().toString();

        return new VoidRequest(client, tokenProvider, url, HttpMethod.DELETE);
    }

    /**
     * Updates a Network ACL by its ID.
     * A token with scope update:network_acls is needed.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/network-acls/put-network-acls-by-id">https://auth0.com/docs/api/management/v2#!/network-acls/put-network-acls-by-id</a>
     * @param id the ID of the Network ACL to update.
     * @param networkAcls the Network ACL to update.
     * @return a Request that can be executed to update the Network ACL.
     */
    public Request<NetworkAcls> update(String id, NetworkAcls networkAcls) {
        Asserts.assertNotNull(id, "id");
        Asserts.assertNotNull(networkAcls, "network acls");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .build().toString();

        BaseRequest<NetworkAcls> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.PUT, new TypeReference<NetworkAcls>() {
        });
        request.setBody(networkAcls);
        return request;
    }

    /**
     * Partially updates a Network ACL by its ID.
     * A token with scope update:network_acls is needed.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/network-acls/patch-network-acls-by-id">https://auth0.com/docs/api/management/v2#!/network-acls/patch-network-acls-by-id</a>
     * @param id the ID of the Network ACL to update.
     * @param networkAcls the Network ACL to update.
     * @return a Request that can be executed to partially update the Network ACL.
     */
    Request<NetworkAcls> patch(String id, NetworkAcls networkAcls) {
        Asserts.assertNotNull(id, "id");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .build().toString();

        BaseRequest<NetworkAcls> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<NetworkAcls>() {
        });
        request.setBody(networkAcls);
        return request;
    }

}
