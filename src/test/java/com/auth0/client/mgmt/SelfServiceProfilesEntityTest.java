package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.PageBasedPaginationFilter;
import com.auth0.json.mgmt.selfserviceprofiles.*;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
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
        assertThat(response.getItems(), hasSize(3));
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
        assertThat(response.getItems(), hasSize(3));
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
        assertThat(response.getItems(), hasSize(3));
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

        server.jsonResponse(SELF_SERVICE_PROFILE_RESPONSE, 201);
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

        server.jsonResponse(SELF_SERVICE_PROFILE_RESPONSE, 200);
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

        server.jsonResponse(SELF_SERVICE_PROFILE_RESPONSE, 200);
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
        SelfServiceProfile profile = new SelfServiceProfile();
        profile.setDescription("This is Test is updated");
        Request<SelfServiceProfileResponse> request = api.selfServiceProfiles().update(profile, "id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILE_RESPONSE, 200);
        SelfServiceProfileResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/self-service-profiles/id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("description", "This is Test is updated"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetCustomTextWhenIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().getCustomText(null, "language", "page"), "'id' cannot be null!");
    }

    @Test
    public void shouldThrowOnGetCustomTextWhenLanguageIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().getCustomText("id", null, "page"), "'language' cannot be null!");
    }

    @Test
    public void shouldThrowOnGetCustomTextWhenPageIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().getCustomText("id", "language", null), "'page' cannot be null!");
    }

    @Test
    public void shouldGetCustomText() throws Exception {
        Request<Object> request = api.selfServiceProfiles().getCustomText("id", "language", "page");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILE_CUSTOM_TEXT, 200);
        Object response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/self-service-profiles/id/custom-text/language/page"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnSetCustomTextWhenIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().setCustomText(null, "language", "page", new Object()), "'id' cannot be null!");
    }

    @Test
    public void shouldThrowOnSetCustomTextWhenLanguageIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().setCustomText("id", null, "page", new Object()), "'language' cannot be null!");
    }

    @Test
    public void shouldThrowOnSetCustomTextWhenPageIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().setCustomText("id", "language", null, new Object()), "'page' cannot be null!");
    }

    @Test
    public void shouldThrowOnSetCustomTextWhenCustomTextIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().setCustomText("id", "language", "page", null), "'custom text' cannot be null!");
    }

    @Test
    public void shouldSetCustomText() throws Exception {
        Map<String, Object> customText = new HashMap<>();
        customText.put("introduction", "Welcome! With <b>only a few steps</b>");
        Request<Object> request = api.selfServiceProfiles().setCustomText("id", "language", "page", customText);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILE_CUSTOM_TEXT, 200);
        Object response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/self-service-profiles/id/custom-text/language/page"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateSsoAccessTicketWhenIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().createSsoAccessTicket(null, new SsoAccessTicketRequest()), "'id' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateSsoAccessTicketWhenPayloadIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().createSsoAccessTicket("id", null), "'request body' cannot be null!");
    }

    @Test
    public void shouldCreateSsoAccessTicket() throws Exception{
        SsoAccessTicketRequest requestBody = new SsoAccessTicketRequest();
        requestBody.setConnectionId("test-connection");

        Request<SsoAccessTicketResponse> request = api.selfServiceProfiles().createSsoAccessTicket("id", requestBody);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(SELF_SERVICE_PROFILE_SSO_TICKET, 200);
        SsoAccessTicketResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/self-service-profiles/id/sso-ticket"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnRevokeSsoTicketWhenIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().revokeSsoTicket(null, "ticketId"), "'id' cannot be null!");
    }

    @Test
    public void shouldThrowOnRevokeSsoTicketWhenTicketIdIsNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.selfServiceProfiles().revokeSsoTicket("id", null), "'ticket id' cannot be null!");
    }

    @Test
    public void shouldRevokeSsoTicket() throws Exception{
        Request<Void> request = api.selfServiceProfiles().revokeSsoTicket("id", "ticketId");
        assertThat(request, is(notNullValue()));

        server.noContentResponse();
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/self-service-profiles/id/sso-ticket/ticketId/revoke"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}
