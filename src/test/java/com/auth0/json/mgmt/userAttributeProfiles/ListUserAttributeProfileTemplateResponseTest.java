package com.auth0.json.mgmt.userAttributeProfiles;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        // Create first template
        UserAttributeProfileTemplate template1 = new UserAttributeProfileTemplate();
        template1.setDisplayName("Test Template 1");

        // Create nested UserAttributeProfile for template1
        UserAttributeProfile userProfile1 = new UserAttributeProfile();
        userProfile1.setName("Profile 1");
        template1.setTemplate(userProfile1);

        // Create second template
        UserAttributeProfileTemplate template2 = new UserAttributeProfileTemplate();
        template2.setDisplayName("Test Template 2");

        listTemplates.setUserAttributeProfileTemplates(Arrays.asList(template1, template2));

        String serialized = toJSON(listTemplates);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_attribute_profile_templates", listTemplates));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ListUserAttributeProfileTemplate listTemplates = fromJSON(jsonWithTemplates,
                ListUserAttributeProfileTemplate.class);

        assertThat(listTemplates, is(notNullValue()));
        assertThat(listTemplates.getUserAttributeProfileTemplates(), is(notNullValue()));
        assertThat(listTemplates.getUserAttributeProfileTemplates(), hasSize(1));
    }

    @Test
    public void shouldDeserializeWithFullTemplateDetails() throws Exception {
        ListUserAttributeProfileTemplate listTemplates = fromJSON(jsonWithTemplates,
                ListUserAttributeProfileTemplate.class);

        assertThat(listTemplates, is(notNullValue()));
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

    @Test
    public void shouldDeserializeEmptyList() throws Exception {
        ListUserAttributeProfileTemplate listTemplates = fromJSON(emptyListJson,
                ListUserAttributeProfileTemplate.class);

        assertThat(listTemplates, is(notNullValue()));
        assertThat(listTemplates.getUserAttributeProfileTemplates(), is(notNullValue()));
        assertThat(listTemplates.getUserAttributeProfileTemplates(), hasSize(0));
    }

    @Test
    public void shouldHandleNullList() throws Exception {
        ListUserAttributeProfileTemplate listTemplates = new ListUserAttributeProfileTemplate();
        listTemplates.setUserAttributeProfileTemplates(null);

        String serialized = toJSON(listTemplates);
        assertThat(serialized, is(notNullValue()));
    }

    @Test
    public void shouldHandleTemplatesWithoutNestedProfile() throws Exception {
        String minimalJson = "{\n" +
                "  \"user_attribute_profile_templates\": [\n" +
                "    {\n" +
                "      \"id\": \"minimal-template\",\n" +
                "      \"display_name\": \"Minimal Template\"\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        ListUserAttributeProfileTemplate listTemplates = fromJSON(minimalJson, ListUserAttributeProfileTemplate.class);

        assertThat(listTemplates, is(notNullValue()));
        List<UserAttributeProfileTemplate> templates = listTemplates.getUserAttributeProfileTemplates();
        assertThat(templates, hasSize(1));

        UserAttributeProfileTemplate template = templates.get(0);
        assertThat(template.getId(), is("minimal-template"));
        assertThat(template.getDisplayName(), is("Minimal Template"));
    }

    @Test
    public void shouldSerializeMultipleTemplates() throws Exception {
        ListUserAttributeProfileTemplate listTemplates = new ListUserAttributeProfileTemplate();

        // Create multiple templates with different structures
        UserAttributeProfileTemplate template1 = new UserAttributeProfileTemplate();
        template1.setDisplayName("Template 1");

        UserAttributeProfile profile1 = new UserAttributeProfile();
        profile1.setName("Profile 1");
        Map<String, UserAttributes> attrs1 = new HashMap<>();
        UserAttributes userAttr1 = new UserAttributes();
        userAttr1.setLabel("User 1");
        userAttr1.setDescription("Description 1");
        attrs1.put("user1", userAttr1);
        profile1.setUserAttributes(attrs1);
        template1.setTemplate(profile1);

        UserAttributeProfileTemplate template2 = new UserAttributeProfileTemplate();
        template2.setDisplayName("Template 2");

        listTemplates.setUserAttributeProfileTemplates(Arrays.asList(template1, template2));

        String serialized = toJSON(listTemplates);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_attribute_profile_templates", listTemplates));
    }
}
