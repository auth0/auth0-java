package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.json.mgmt.tenants.Tenant;
import com.auth0.net.ExtendedBaseRequest;
import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.Map;

/**
 * Class that provides an implementation of the Tenant Settings methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Tenants
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class TenantsEntity extends BaseManagementEntity {

    TenantsEntity(Auth0HttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request the Tenant Settings. A token with scope read:tenant_settings is needed.
     * See https://auth0.com/docs/api/management/v2#!/Tenants/get_settings
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<Tenant> get(FieldsFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/tenants/settings");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        ExtendedBaseRequest<Tenant> request = new ExtendedBaseRequest<>(client, url, HttpMethod.GET, new TypeReference<Tenant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update the Tenant Settings. A token with scope update:tenant_settings is needed.
     * See https://auth0.com/docs/api/management/v2#!/Tenants/patch_settings
     *
     * @param tenant the tenant data to set.
     * @return a Request to execute.
     */
    public Request<Tenant> update(Tenant tenant) {
        Asserts.assertNotNull(tenant, "tenant");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/tenants/settings")
                .build()
                .toString();

        ExtendedBaseRequest<Tenant> request = new ExtendedBaseRequest<>(client, url, HttpMethod.PATCH, new TypeReference<Tenant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(tenant);
        return request;
    }
}
