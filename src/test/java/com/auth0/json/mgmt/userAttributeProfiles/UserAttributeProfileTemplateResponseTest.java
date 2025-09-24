package com.auth0.json.mgmt.userAttributeProfiles;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
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

    private static final String readOnlyJson = "{\"id\":\"auth0-generic\",\"display_name\":\"A standard user attribute profile template\"}";

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
        assertThat(serialized, JsonMatcher.hasEntry("display_name", "A standard user attribute profile template"));
        assertThat(serialized, JsonMatcher.hasEntry("template", userAttributeProfile));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        UserAttributeProfileTemplate template = fromJSON(readOnlyJson, UserAttributeProfileTemplate.class);

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

        // Verify OIDC mapping in template
        assertThat(usernameAttr.getOidcMapping(), is(notNullValue()));
        assertThat(usernameAttr.getOidcMapping().getMapping(), is("preferred_username"));
        assertThat(usernameAttr.getOidcMapping().getDisplayName(), is("UserName"));

        // Verify SAML mapping in template
        assertThat(usernameAttr.getSamlMapping(), hasSize(1));
        assertThat(usernameAttr.getSamlMapping().get(0),
                is("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress"));

        // Verify SCIM mapping in template
        assertThat(usernameAttr.getScimMapping(), is("displayName"));

        // Verify strategy overrides in template
        assertThat(usernameAttr.getStrategyOverrides(), is(notNullValue()));
        assertThat(usernameAttr.getStrategyOverrides(), hasKey("oidc"));
    }
}
