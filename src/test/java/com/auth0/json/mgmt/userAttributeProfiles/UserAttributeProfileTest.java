package com.auth0.json.mgmt.userAttributeProfiles;

import com.auth0.json.JsonTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.hasSize;

public class UserAttributeProfileTest extends JsonTest<UserAttributeProfile> {

    private static final String fullJson = "{\n" +
            "  \"id\": \"uap_1234567890\",\n" +
            "  \"name\": \"This is just a test\",\n" +
            "  \"user_id\": {\n" +
            "    \"oidc_mapping\": \"sub\",\n" +
            "    \"saml_mapping\": [\n" +
            "      \"urn:oid:0.9.10.10.100.1.1\"\n" +
            "    ],\n" +
            "    \"scim_mapping\": \"userName\"\n" +
            "  },\n" +
            "  \"user_attributes\": {\n" +
            "    \"username\": {\n" +
            "      \"label\": \"test User\",\n" +
            "      \"description\": \"This is just a test\",\n" +
            "      \"oidc_mapping\": {\n" +
            "        \"mapping\": \"preferred_username\",\n" +
            "        \"display_name\": \"UserName\"\n" +
            "      },\n" +
            "      \"saml_mapping\":[\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress\"],\n" +
            "      \"scim_mapping\":\"displayName\",\n" +
            "      \"auth0_mapping\": \"testUser\",\n" +
            "      \"profile_required\": false,\n" +
            "      \"strategy_overrides\": {\n" +
            "        \"oidc\": {\n" +
            "          \"scim_mapping\":\"name.givenName\"\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    public void shouldSerialize() throws Exception {
        UserAttributeProfile profile = new UserAttributeProfile();
        profile.setName("Test Profile");

        // Create and set UserId
        UserId userId = new UserId();
        userId.setOidcMapping("sub");
        userId.setSamlMapping(Arrays.asList("urn:oid:0.9.10.10.100.1.1"));
        userId.setScimMapping("userName");
        profile.setUserId(userId);

        // Create and set UserAttributes
        Map<String, UserAttributes> userAttributesMap = new HashMap<>();
        UserAttributes userAttributes = new UserAttributes();
        userAttributes.setLabel("test User");
        userAttributes.setDescription("This is just a test");
        userAttributes.setAuth0Mapping("testUser");
        userAttributes.setProfileRequired(false);
        userAttributesMap.put("username", userAttributes);
        profile.setUserAttributes(userAttributesMap);

        String serialized = toJSON(profile);
        assertThat(serialized, is(notNullValue()));

        // Parse the serialized JSON into a Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(serialized, Map.class);

        // Validate the structure
        assertThat(jsonMap, hasKey("name"));
        assertThat(jsonMap.get("name"), is("Test Profile"));
        assertThat(jsonMap, hasKey("user_id"));
        assertThat(jsonMap, hasKey("user_attributes"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        UserAttributeProfile profile = fromJSON(fullJson, UserAttributeProfile.class);

        assertThat(profile, is(notNullValue()));
        assertThat(profile.getId(), is("uap_1234567890"));
        assertThat(profile.getName(), is("This is just a test"));

        // Verify UserId deserialization
        UserId userId = profile.getUserId();
        assertThat(userId, is(notNullValue()));
        assertThat(userId.getOidcMapping(), is("sub"));
        assertThat(userId.getSamlMapping(), hasSize(1));
        assertThat(userId.getSamlMapping().get(0), is("urn:oid:0.9.10.10.100.1.1"));
        assertThat(userId.getScimMapping(), is("userName"));

        // Verify UserAttributes deserialization
        Map<String, UserAttributes> userAttributes = profile.getUserAttributes();
        assertThat(userAttributes, is(notNullValue()));
        assertThat(userAttributes, hasKey("username"));

        UserAttributes usernameAttr = userAttributes.get("username");
        assertThat(usernameAttr, is(notNullValue()));
        assertThat(usernameAttr.getLabel(), is("test User"));
        assertThat(usernameAttr.getDescription(), is("This is just a test"));
        assertThat(usernameAttr.getAuth0Mapping(), is("testUser"));
        assertThat(usernameAttr.isProfileRequired(), is(false));

        // Verify OIDC mapping
        assertThat(usernameAttr.getOidcMapping(), is(notNullValue()));
        assertThat(usernameAttr.getOidcMapping().getMapping(), is("preferred_username"));
        assertThat(usernameAttr.getOidcMapping().getDisplayName(), is("UserName"));

        // Verify SAML mapping
        assertThat(usernameAttr.getSamlMapping(), hasSize(1));
        assertThat(usernameAttr.getSamlMapping().get(0),
            is("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress"));

        // Verify SCIM mapping
        assertThat(usernameAttr.getScimMapping(), is("displayName"));

        // Verify strategy overrides
        assertThat(usernameAttr.getStrategyOverrides(), is(notNullValue()));
        assertThat(usernameAttr.getStrategyOverrides(), hasKey("oidc"));
    }

}
