package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.PageBasedPaginationFilter;
import com.auth0.json.mgmt.selfserviceprofiles.SelfServiceProfile;
import com.auth0.json.mgmt.selfserviceprofiles.SelfServiceProfileResponse;
import com.auth0.json.mgmt.selfserviceprofiles.SelfServiceProfileResponsePage;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SelfServiceProfilesEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldGetSelfServiceProfilesWithoutFilter() throws Exception{
        Request<SelfServiceProfileResponsePage> request = api.selfServiceProfiles().get(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILES_LIST, 200);
        SelfServiceProfileResponsePage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/self-service-profiles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetSelfServiceProfilesWithPage() throws Exception {
        PageBasedPaginationFilter filter = new PageBasedPaginationFilter().withPage(1, 5);
        Request<SelfServiceProfileResponsePage> request = api.selfServiceProfiles().get(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILES_LIST, 200);
        SelfServiceProfileResponsePage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/self-service-profiles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "1"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldGetSelfServiceProfilesWithTotals() throws Exception {
        PageBasedPaginationFilter filter = new PageBasedPaginationFilter().withTotals(true);
        Request<SelfServiceProfileResponsePage> request = api.selfServiceProfiles().get(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILES_LIST, 200);
        SelfServiceProfileResponsePage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/self-service-profiles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnCreateWhenSelfServiceProfileIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().create(null), "'self service profile' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateWhenNameInSelfServiceProfileIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().create(new SelfServiceProfile()), "'name' cannot be null!");
    }

    @Test
    public void shouldCreateSelfServiceProfile() throws Exception {
        SelfServiceProfile profile = new SelfServiceProfile();
        profile.setName("Test");
        profile.setDescription("This is for Test");
        Request<SelfServiceProfileResponse> request = api.selfServiceProfiles().create(profile);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILE, 201);
        SelfServiceProfileResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/self-service-profiles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("name", "Test"));
        assertThat(body, hasEntry("description", "This is for Test"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetByIdWhenIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().getById(null), "'id' cannot be null!");
    }

    @Test
    public void shouldGetSelfServiceProfileById() throws Exception {
        Request<SelfServiceProfileResponse> request = api.selfServiceProfiles().getById("id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILE, 200);
        SelfServiceProfileResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/self-service-profiles/id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteWhenIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().delete(null), "'id' cannot be null!");
    }

    @Test
    public void shouldDeleteSelfServiceProfile() throws Exception {
        Request<Void> request = api.selfServiceProfiles().delete("id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILE, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/self-service-profiles/id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateWhenIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().update(new SelfServiceProfile(), null), "'id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateWhenSelfServiceProfileIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().update(null, "id"), "'self service profile' cannot be null!");
    }

    @Test
    public void shouldUpdateSelfServiceProfile() throws Exception {
        Request<SelfServiceProfileResponse> request = api.selfServiceProfiles().update(new SelfServiceProfile(), "id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILE, 200);
        SelfServiceProfileResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/self-service-profiles/id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);

        assertThat(response, is(notNullValue()));
    }
}
