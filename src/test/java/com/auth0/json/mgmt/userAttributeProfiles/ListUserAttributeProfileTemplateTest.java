package com.auth0.json.mgmt.userAttributeProfiles;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.hasKey;

public class ListUserAttributeProfileTemplateTest extends JsonTest<ListUserAttributeProfileTemplate> {

    private static final String jsonWithTemplates = "{\n" +
            "  \"user_attribute_profile_templates\": [\n" +
            "    {\n" +
            "      \"id\": \"auth0-generic\",\n" +
            "      \"display_name\": \"Auth0 Generic User Attribute Profile Template\",\n" +
            "      \"template\": {\n" +
            "        \"name\": \"This is just a test\",\n" +
            "        \"user_id\": {\n" +
            "          \"oidc_mapping\": \"sub\",\n" +
            "          \"saml_mapping\": [\n" +
            "            \"urn:oid:0.9.10.10.100.1.1\"\n" +
            "          ],\n" +
            "          \"scim_mapping\": \"userName\"\n" +
            "        },\n" +
            "        \"user_attributes\": {\n" +
            "          \"username\": {\n" +
            "            \"label\": \"test User\",\n" +
            "            \"description\": \"This is just a test\",\n" +
            "            \"oidc_mapping\": {\n" +
            "              \"mapping\": \"preferred_username\",\n" +
            "              \"display_name\": \"UserName\"\n" +
            "            },\n" +
            "            \"saml_mapping\": [\n" +
            "              \"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress\"\n" +
            "            ],\n" +
            "            \"scim_mapping\": \"displayName\",\n" +
            "            \"auth0_mapping\": \"testUser\",\n" +
            "            \"profile_required\": false,\n" +
            "            \"strategy_overrides\": {\n" +
            "              \"oidc\": {\n" +
            "                \"scim_mapping\": \"name.givenName\"\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private static final String emptyListJson = "{\n" +
            "  \"user_attribute_profile_templates\": []\n" +
            "}";

    @Test
    public void shouldSerialize() throws Exception {
        ListUserAttributeProfileTemplate listTemplates = new ListUserAttributeProfileTemplate();

        UserAttributeProfileTemplate template = new UserAttributeProfileTemplate();
        template.setDisplayName("Auth0 Generic User Attribute Profile Template");

        // Create nested UserAttributeProfile for template1
        UserAttributeProfile userProfile = new UserAttributeProfile();
        userProfile.setName("This is just a test");
        template.setTemplate(userProfile);

        listTemplates.setUserAttributeProfileTemplates(Arrays.asList(template));

        String serialized = toJSON(listTemplates);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, hasEntry("display_name", "Auth0 Generic User Attribute Profile Template"));
        assertThat(serialized, hasEntry("template", notNullValue()));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ListUserAttributeProfileTemplate listTemplates = fromJSON(jsonWithTemplates,
                ListUserAttributeProfileTemplate.class);

        assertThat(listTemplates, is(notNullValue()));
        assertThat(listTemplates.getUserAttributeProfileTemplates(), is(notNullValue()));
        assertThat(listTemplates.getUserAttributeProfileTemplates(), hasSize(1));

        List<UserAttributeProfileTemplate> templates = listTemplates.getUserAttributeProfileTemplates();
        assertThat(templates, hasSize(1));

        // Test template details
        UserAttributeProfileTemplate template = templates.get(0);
        assertThat(template.getId(), is("auth0-generic"));
        assertThat(template.getDisplayName(), is("Auth0 Generic User Attribute Profile Template"));

        // Test nested UserAttributeProfile template
        UserAttributeProfile userAttributeProfile = template.getTemplate();
        assertThat(userAttributeProfile, is(notNullValue()));
        assertThat(userAttributeProfile.getName(), is("This is just a test"));

        // Test UserId in nested template
        UserId userId = userAttributeProfile.getUserId();
        assertThat(userId, is(notNullValue()));
        assertThat(userId.getOidcMapping(), is("sub"));
        assertThat(userId.getSamlMapping(), hasSize(1));
        assertThat(userId.getSamlMapping().get(0), is("urn:oid:0.9.10.10.100.1.1"));
        assertThat(userId.getScimMapping(), is("userName"));

        // Test UserAttributes in nested template
        Map<String, UserAttributes> userAttributes = userAttributeProfile.getUserAttributes();
        assertThat(userAttributes, is(notNullValue()));
        assertThat(userAttributes, hasKey("username"));

        UserAttributes usernameAttr = userAttributes.get("username");
        assertThat(usernameAttr, is(notNullValue()));
        assertThat(usernameAttr.getLabel(), is("test User"));
        assertThat(usernameAttr.getDescription(), is("This is just a test"));
        assertThat(usernameAttr.getAuth0Mapping(), is("testUser"));
        assertThat(usernameAttr.isProfileRequired(), is(false));

        // Test OIDC mapping in nested template
        assertThat(usernameAttr.getOidcMapping(), is(notNullValue()));
        assertThat(usernameAttr.getOidcMapping().getMapping(), is("preferred_username"));
        assertThat(usernameAttr.getOidcMapping().getDisplayName(), is("UserName"));

        // Test SAML mapping in nested template
        assertThat(usernameAttr.getSamlMapping(), hasSize(1));
        assertThat(usernameAttr.getSamlMapping().get(0),
                is("http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress"));

        // Test SCIM mapping in nested template
        assertThat(usernameAttr.getScimMapping(), is("displayName"));

        // Test strategy overrides in nested template
        assertThat(usernameAttr.getStrategyOverrides(), is(notNullValue()));
        assertThat(usernameAttr.getStrategyOverrides(), hasKey("oidc"));
    }
}
