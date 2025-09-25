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

public class UserAttributeProfileTemplateTest extends JsonTest<UserAttributeProfileTemplate> {


    private static final String fullJson = "{\n" +
            "  \"id\": \"auth0-generic\",\n" +
            "  \"display_name\": \"A standard user attribute profile template\",\n" +
            "  \"template\": {\n" +
            "    \"name\": \"This is just a test\",\n" +
            "    \"user_id\": {\n" +
            "      \"oidc_mapping\": \"sub\",\n" +
            "      \"saml_mapping\": [\n" +
            "        \"urn:oid:0.9.10.10.100.1.1\"\n" +
            "      ],\n" +
            "      \"scim_mapping\": \"userName\"\n" +
            "    },\n" +
            "    \"user_attributes\": {\n" +
            "      \"username\": {\n" +
            "        \"label\": \"test User\",\n" +
            "        \"description\": \"This is just a test\",\n" +
            "        \"oidc_mapping\": {\n" +
            "          \"mapping\": \"preferred_username\",\n" +
            "          \"display_name\": \"UserName\"\n" +
            "        },\n" +
            "        \"saml_mapping\":[\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress\"],\n" +
            "        \"scim_mapping\":\"displayName\",\n" +
            "        \"auth0_mapping\": \"testUser\",\n" +
            "        \"profile_required\": false,\n" +
            "        \"strategy_overrides\": {\n" +
            "          \"oidc\": {\n" +
            "            \"scim_mapping\":\"name.givenName\"\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  }\n" +
            "}";

    @Test
    public void shouldSerialize() throws Exception {
        UserAttributeProfileTemplate template = new UserAttributeProfileTemplate();
        template.setDisplayName("A standard user attribute profile template");

        // Create nested UserAttributeProfile template
        UserAttributeProfile userAttributeProfile = new UserAttributeProfile();
        userAttributeProfile.setName("Test Template Profile");

        // Create and set UserId
        UserId userId = new UserId();
        userId.setOidcMapping("sub");
        userId.setSamlMapping(Arrays.asList("urn:oid:0.9.10.10.100.1.1"));
        userId.setScimMapping("userName");
        userAttributeProfile.setUserId(userId);

        // Create and set UserAttributes
        Map<String, UserAttributes> userAttributesMap = new HashMap<>();
        UserAttributes userAttributes = new UserAttributes();
        userAttributes.setLabel("test User");
        userAttributes.setDescription("This is just a test");
        userAttributes.setAuth0Mapping("testUser");
        userAttributes.setProfileRequired(false);
        userAttributesMap.put("username", userAttributes);
        userAttributeProfile.setUserAttributes(userAttributesMap);

        template.setTemplate(userAttributeProfile);

        String serialized = toJSON(template);
        assertThat(serialized, is(notNullValue()));

        // Parse the serialized JSON into a Map
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> jsonMap = objectMapper.readValue(serialized, Map.class);

        // Validate the structure
        assertThat(jsonMap, hasKey("display_name"));
        assertThat(jsonMap.get("display_name"), is("A standard user attribute profile template"));
        assertThat(jsonMap, hasKey("template"));

        Map<String, Object> templateMap = (Map<String, Object>) jsonMap.get("template");
        assertThat(templateMap, hasKey("name"));
        assertThat(templateMap.get("name"), is("Test Template Profile"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        UserAttributeProfileTemplate template = fromJSON(fullJson, UserAttributeProfileTemplate.class);

        assertThat(template, is(notNullValue()));
        assertThat(template.getId(), is("auth0-generic"));
        assertThat(template.getDisplayName(), is("A standard user attribute profile template"));

        // Verify nested UserAttributeProfile template
        UserAttributeProfile userAttributeProfile = template.getTemplate();
        assertThat(userAttributeProfile, is(notNullValue()));
        assertThat(userAttributeProfile.getName(), is("This is just a test"));

        // Verify UserId in template
        UserId userId = userAttributeProfile.getUserId();
        assertThat(userId, is(notNullValue()));
        assertThat(userId.getOidcMapping(), is("sub"));
        assertThat(userId.getSamlMapping(), hasSize(1));
        assertThat(userId.getSamlMapping().get(0), is("urn:oid:0.9.10.10.100.1.1"));
        assertThat(userId.getScimMapping(), is("userName"));

        // Verify UserAttributes in template
        Map<String, UserAttributes> userAttributes = userAttributeProfile.getUserAttributes();
        assertThat(userAttributes, is(notNullValue()));
        assertThat(userAttributes, hasKey("username"));

        UserAttributes usernameAttr = userAttributes.get("username");
        assertThat(usernameAttr, is(notNullValue()));
        assertThat(usernameAttr.getLabel(), is("test User"));
        assertThat(usernameAttr.getDescription(), is("This is just a test"));
        assertThat(usernameAttr.getAuth0Mapping(), is("testUser"));
        assertThat(usernameAttr.isProfileRequired(), is(false));
    }
}
