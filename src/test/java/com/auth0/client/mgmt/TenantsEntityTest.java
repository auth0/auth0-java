package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.json.mgmt.tenants.Clients;
import com.auth0.json.mgmt.tenants.DefaultTokenQuota;
import com.auth0.json.mgmt.tenants.Organizations;
import com.auth0.json.mgmt.tenants.Tenant;
import com.auth0.json.mgmt.tokenquota.ClientCredentials;
import com.auth0.json.mgmt.tokenquota.TokenQuota;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.MGMT_TENANT;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TenantsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldGetTenantSettings() throws Exception {
        Request<Tenant> request = api.tenants().get(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_TENANT, 200);
        Tenant response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/tenants/settings"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetTenantSettingsWithFields() throws Exception {
        FieldsFilter filter = new FieldsFilter().withFields("some,random,fields", true);
        Request<Tenant> request = api.tenants().get(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_TENANT, 200);
        Tenant response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/tenants/settings"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateTenantSettingsWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.tenants().update(null),
            "'tenant' cannot be null!");
    }

    @Test
    public void shouldUpdateTenantSettings() throws Exception {
        Tenant tenant = new Tenant();

        DefaultTokenQuota defaultTokenQuota = new DefaultTokenQuota(
            new Clients(new ClientCredentials(100, 20, true)),
            new Organizations(new ClientCredentials(100, 20, true)));

        tenant.setDefaultTokenQuota(defaultTokenQuota);
        Request<Tenant> request = api.tenants().update(tenant);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_TENANT, 200);
        Tenant response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/tenants/settings"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));


        // Parse and validate the request body
        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, is(notNullValue()));

        Map<String, Object> tokenQuotaMap = (Map<String, Object>) body.get("default_token_quota");
        assertThat(tokenQuotaMap, is(notNullValue()));

        // Validate "clients" nested structure
        Map<String, Object> clientsMap = (Map<String, Object>) tokenQuotaMap.get("clients");
        assertThat(clientsMap, is(notNullValue()));
        Map<String, Object> clientCredentialsMap = (Map<String, Object>) clientsMap.get("client_credentials");
        assertThat(clientCredentialsMap, is(notNullValue()));
        assertThat(clientCredentialsMap, hasEntry("per_day", 100));
        assertThat(clientCredentialsMap, hasEntry("per_hour", 20));
        assertThat(clientCredentialsMap, hasEntry("enforce", true));

        // Validate "organizations" nested structure
        Map<String, Object> organizationsMap = (Map<String, Object>) tokenQuotaMap.get("organizations");
        assertThat(organizationsMap, is(notNullValue()));
        Map<String, Object> organizationCredentialsMap = (Map<String, Object>) organizationsMap.get("client_credentials");
        assertThat(organizationCredentialsMap, is(notNullValue()));
        assertThat(organizationCredentialsMap, hasEntry("per_day", 100));
        assertThat(organizationCredentialsMap, hasEntry("per_hour", 20));
        assertThat(organizationCredentialsMap, hasEntry("enforce", true));

        assertThat(response, is(notNullValue()));
    }
}
