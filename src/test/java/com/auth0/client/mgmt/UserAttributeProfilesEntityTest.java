package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.filter.UserAttributeProfilesFilter;
import com.auth0.json.mgmt.userAttributeProfiles.*;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserAttributeProfilesEntityTest extends BaseMgmtEntityTest {

    // User Attribute Profiles entity tests

    @Test
    public void shouldThrowOnGetUserAttributeProfileWithNullId() {
        verifyThrows(IllegalArgumentException.class,
                () -> api.userAttributeProfiles().get(null),
                "'id' cannot be null!");
    }

    @Test
    public void shouldGetUserAttributeProfile() throws Exception {
        Request<UserAttributeProfile> request = api.userAttributeProfiles().get("uap_1234567890");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_ATTRIBUTE_PROFILE, 200);
        UserAttributeProfile response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/user-attribute-profiles/uap_1234567890"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("uap_1234567890"));
        assertThat(response.getName(), is("This is just a test"));
    }

    @Test
    public void shouldGetAllUserAttributeProfilesWithoutFilter() throws Exception {
        Request<UserAttributeProfilePage> request = api.userAttributeProfiles().getAll(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_ATTRIBUTE_PROFILES_LIST, 200);
        UserAttributeProfilePage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/user-attribute-profiles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldGetAllUserAttributeProfilesWithFilter() throws Exception {
        UserAttributeProfilesFilter filter = new UserAttributeProfilesFilter()
                .withCheckpointPagination("uap_1234567890", 2);

        Request<UserAttributeProfilePage> request = api.userAttributeProfiles().getAll(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_ATTRIBUTE_PROFILES_LIST, 200);
        UserAttributeProfilePage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/user-attribute-profiles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("from", "uap_1234567890"));
        assertThat(recordedRequest, hasQueryParameter("take", "10"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldThrowOnUpdateUserAttributeProfileWithNullId() {
        verifyThrows(IllegalArgumentException.class,
                () -> api.userAttributeProfiles().update(null, new UserAttributeProfile()),
                "'id' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateUserAttributeProfileWithNullProfile() {
        verifyThrows(IllegalArgumentException.class,
                () -> api.userAttributeProfiles().update("uap_1234567890", null),
                "'userAttributeProfile' cannot be null!");
    }

    @Test
    public void shouldUpdateUserAttributeProfile() throws Exception {
        UserAttributeProfile profileToUpdate = new UserAttributeProfile();
        profileToUpdate.setName("This is just a test");

        UserId userId = new UserId();
        userId.setOidcMapping("sub");
        userId.setSamlMapping(Arrays.asList("urn:oid:0.9.10.10.100.1.1")); // Replace List.of() with Arrays.asList()
        userId.setScimMapping("userName");
        profileToUpdate.setUserId(userId);

        Map<String, UserAttributes> userAttributes = new HashMap<>();
        UserAttributes usernameAttr = new UserAttributes();
        usernameAttr.setLabel("test User");
        usernameAttr.setDescription("This is just a test");

        OidcMapping oidcMapping = new OidcMapping();
        oidcMapping.setMapping("preferred_username");
        oidcMapping.setDisplayName("UserName");
        usernameAttr.setOidcMapping(oidcMapping);

        usernameAttr.setSamlMapping(Arrays.asList("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress")); // Replace List.of() with Arrays.asList()
        usernameAttr.setScimMapping("displayName");
        usernameAttr.setAuth0Mapping("testUser");
        usernameAttr.setProfileRequired(false);

        StrategyOverridesUserAttributes strategyOverridesUserAttributes = new StrategyOverridesUserAttributes();
        strategyOverridesUserAttributes.setScimMapping("name.givenName");
        Map<String, StrategyOverridesUserAttributes> strategyOverridesMap = new HashMap<>();
        strategyOverridesMap.put("oidc", strategyOverridesUserAttributes);
        usernameAttr.setStrategyOverrides(strategyOverridesMap);

        userAttributes.put("username", usernameAttr);
        profileToUpdate.setUserAttributes(userAttributes);

        Request<UserAttributeProfile> request = api.userAttributeProfiles().update("uap_1csqEmz4TE2P1o7izaATmb", profileToUpdate);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_ATTRIBUTE_PROFILE, 200);
        UserAttributeProfile response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest,
            hasMethodAndPath(HttpMethod.PATCH, "/api/v2/user-attribute-profiles/uap_1csqEmz4TE2P1o7izaATmb"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, hasEntry("name", "This is just a test"));
        assertThat(body, hasEntry(is("user_id"), is(notNullValue())));
        assertThat(body, hasEntry(is("user_attributes"), is(notNullValue())));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateUserAttributeProfileWithNullName() {
        UserAttributeProfile profile = new UserAttributeProfile();
        profile.setName(null);
        Map<String, UserAttributes> userAttributes = new HashMap<>();
        profile.setUserAttributes(userAttributes);

        verifyThrows(IllegalArgumentException.class,
                () -> api.userAttributeProfiles().create(profile),
                "'name' cannot be null!");
    }

    @Test
    public void shouldThrowOnCreateUserAttributeProfileWithNullUserAttributes() {
        UserAttributeProfile profile = new UserAttributeProfile();
        profile.setName("Test Profile");
        profile.setUserAttributes(null);

        verifyThrows(IllegalArgumentException.class,
                () -> api.userAttributeProfiles().create(profile),
                "'userAttributes' cannot be null!");
    }

    @Test
    public void shouldCreateUserAttributeProfile() throws Exception {
        UserAttributeProfile profileToCreate = new UserAttributeProfile();
        profileToCreate.setName("This is just a test");

        UserId userId = new UserId();
        userId.setOidcMapping("sub");
        userId.setSamlMapping(Arrays.asList("urn:oid:0.9.10.10.100.1.1"));
        userId.setScimMapping("userName");
        profileToCreate.setUserId(userId);

        Map<String, UserAttributes> userAttributes = new HashMap<>();
        UserAttributes usernameAttr = new UserAttributes();
        usernameAttr.setLabel("test User");
        usernameAttr.setDescription("This is just a test");

        OidcMapping oidcMapping = new OidcMapping();
        oidcMapping.setMapping("preferred_username");
        oidcMapping.setDisplayName("UserName");
        usernameAttr.setOidcMapping(oidcMapping);

        usernameAttr.setSamlMapping(Arrays.asList("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress"));
        usernameAttr.setScimMapping("displayName");
        usernameAttr.setAuth0Mapping("testUser");
        usernameAttr.setProfileRequired(false);

        StrategyOverridesUserAttributes strategyOverridesUserAttributes = new StrategyOverridesUserAttributes();
        strategyOverridesUserAttributes.setScimMapping("name.givenName");
        Map<String, StrategyOverridesUserAttributes> strategyOverridesMap = new HashMap<>();
        strategyOverridesMap.put("oidc", strategyOverridesUserAttributes);
        usernameAttr.setStrategyOverrides(strategyOverridesMap);

        userAttributes.put("username", usernameAttr);
        profileToCreate.setUserAttributes(userAttributes);

        Request<UserAttributeProfile> request = api.userAttributeProfiles().create(profileToCreate);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_ATTRIBUTE_PROFILE, 201);
        UserAttributeProfile response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/user-attribute-profiles"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(3));
        assertThat(body, hasEntry("name", "This is just a test"));
        assertThat(body, hasEntry(is("user_id"), is(notNullValue())));
        assertThat(body, hasEntry(is("user_attributes"), is(notNullValue())));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteUserAttributeProfileWithNullId() {
        verifyThrows(IllegalArgumentException.class,
                () -> api.userAttributeProfiles().delete(null),
                "'id' cannot be null!");
    }

    @Test
    public void shouldDeleteUserAttributeProfile() throws Exception {
        Request<Void> request = api.userAttributeProfiles().delete("uap_1234567890");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest,
                hasMethodAndPath(HttpMethod.DELETE, "/api/v2/user-attribute-profiles/uap_1234567890"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    // User Attribute Profile Templates tests

    @Test
    public void shouldThrowOnGetUserAttributeProfileTemplateWithNullId() {
        verifyThrows(IllegalArgumentException.class,
                () -> api.userAttributeProfiles().getTemplate(null),
                "'id' cannot be null!");
    }

    @Test
    public void shouldGetUserAttributeProfileTemplate() throws Exception {
        Request<UserAttributeProfileTemplate> request = api.userAttributeProfiles().getTemplate("auth0-generic");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_ATTRIBUTE_PROFILE_TEMPLATE, 200);
        UserAttributeProfileTemplate response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest,
                hasMethodAndPath(HttpMethod.GET, "/api/v2/user-attribute-profiles/templates/auth0-generic"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getId(), is("auth0-generic"));
        assertThat(response.getDisplayName(), is("A standard user attribute profile template"));
    }

    @Test
    public void shouldGetAllUserAttributeProfileTemplates() throws Exception {
        Request<ListUserAttributeProfileTemplate> request = api.userAttributeProfiles().getAllTemplates();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_USER_ATTRIBUTE_PROFILE_TEMPLATES_LIST, 200);
        ListUserAttributeProfileTemplate response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/user-attribute-profiles/templates"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getUserAttributeProfileTemplates(), hasSize(1));
    }
}
