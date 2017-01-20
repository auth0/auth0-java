package com.auth0.client.mgmt;

import com.auth0.Asserts;
import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.json.mgmt.tenants.Tenant;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.Map;

public class TenantsEntity extends BaseManagementEntity {

    TenantsEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request the Tenant Settings. A token with scope read:tenant_settings is needed.
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<Tenant> get(FieldsFilter filter) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("tenants")
                .addPathSegment("settings");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<Tenant> request = new CustomRequest<>(client, url, "GET", new TypeReference<Tenant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update the Tenant Settings. A token with scope update:tenant_settings is needed.
     *
     * @param tenant the tenant data to set.
     * @return a Request to execute.
     */
    public Request<Tenant> update(Tenant tenant) {
        Asserts.assertNotNull(tenant, "tenant");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("tenants")
                .addPathSegment("settings")
                .build()
                .toString();

        CustomRequest<Tenant> request = new CustomRequest<>(client, url, "PATCH", new TypeReference<Tenant>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(tenant);
        return request;
    }


}
