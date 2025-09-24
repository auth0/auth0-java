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

public class ListUserAttributeProfileTest extends JsonTest<ListUserAttributeProfile> {

    private static final String jsonWithProfiles = "{\n" +
            "  \"user_attribute_profiles\": [\n" +
            "    {\n" +
            "      \"id\": \"uap_1234567890\",\n" +
            "      \"name\": \"This is just a test\",\n" +
            "      \"user_id\": {\n" +
            "        \"oidc_mapping\": \"sub\",\n" +
            "        \"saml_mapping\": [\n" +
            "          \"urn:oid:0.9.10.10.100.1.1\"\n" +
            "        ],\n" +
            "        \"scim_mapping\": \"userName\"\n" +
            "      },\n" +
            "      \"user_attributes\": {\n" +
            "        \"username\": {\n" +
            "          \"label\": \"test User\",\n" +
            "          \"description\": \"This is just a test\",\n" +
            "          \"oidc_mapping\": {\n" +
            "            \"mapping\": \"preferred_username\",\n" +
            "            \"display_name\": \"UserName\"\n" +
            "          },\n" +
            "          \"saml_mapping\":[\"http://schemas.xmlsoap.org/ws/2005/05/identity/claims/emailaddress\"],\n" +
            "          \"scim_mapping\":\"displayName\",\n" +
            "          \"auth0_mapping\": \"testUser\",\n" +
            "          \"profile_required\": false,\n" +
            "          \"strategy_overrides\": {\n" +
            "            \"oidc\": {\n" +
            "              \"scim_mapping\":\"name.givenName\"\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"uap_123456789012345\",\n" +
            "      \"name\": \"Updated Test Organization\",\n" +
            "      \"user_id\": {\n" +
            "        \"oidc_mapping\": \"sub\",\n" +
            "        \"saml_mapping\": [\n" +
            "          \"urn:oid:0.9.10.10.100.1.1\"\n" +
            "        ],\n" +
            "        \"scim_mapping\": \"userName\"\n" +
            "      },\n" +
            "      \"user_attributes\": {\n" +
            "        \"username\": {\n" +
            "          \"label\": \"test User\",\n" +
            "          \"description\": \"This is just a test\",\n" +
            "          \"oidc_mapping\": {\n" +
            "            \"mapping\": \"preferred_username\",\n" +
            "            \"display_name\": \"Display Name\"\n" +
            "          },\n" +
            "          \"auth0_mapping\": \"testUser\",\n" +
            "          \"profile_required\": false\n" +
            "        }\n" +
            "      }\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"uap_1234\",\n" +
            "      \"name\": \"This is another test\",\n" +
            "      \"user_id\": {\n" +
            "        \"oidc_mapping\": \"sub\",\n" +
            "        \"saml_mapping\": [\n" +
            "          \"urn:oid:0.9.10.10.100.1.1\"\n" +
            "        ],\n" +
            "        \"scim_mapping\": \"userName\",\n" +
            "        \"strategy_overrides\": {\n" +
            "          \"google-apps\": {\n" +
            "            \"oidc_mapping\": \"email\"\n" +
            "          }\n" +
            "        }\n" +
            "      },\n" +
            "      \"user_attributes\": {\n" +
            "        \"username\": {\n" +
            "          \"label\": \"test User\",\n" +
            "          \"description\": \"This is just a test\",\n" +
            "          \"oidc_mapping\": {\n" +
            "            \"mapping\": \"preferred_username\",\n" +
            "            \"display_name\": \"Display Name\"\n" +
            "          },\n" +
            "          \"auth0_mapping\": \"testUser\",\n" +
            "          \"profile_required\": false,\n" +
            "          \"strategy_overrides\": {\n" +
            "            \"okta\": {\n" +
            "              \"oidc_mapping\": {\n" +
            "                \"mapping\": \"${context.userinfo.groups}\",\n" +
            "                \"display_name\": \"groups\"\n" +
            "              }\n" +
            "            }\n" +
            "          }\n" +
            "        }\n" +
            "      }\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    private static final String emptyListJson = "{\n" +
            "  \"user_attribute_profiles\": []\n" +
            "}";

    @Test
    public void shouldSerialize() throws Exception {
        ListUserAttributeProfile listProfiles = new ListUserAttributeProfile();

        // Create first profile
        UserAttributeProfile profile1 = new UserAttributeProfile();
        profile1.setName("Test Profile 1");

        // Create second profile
        UserAttributeProfile profile2 = new UserAttributeProfile();
        profile2.setName("Test Profile 2");

        listProfiles.setUserAttributeProfiles(Arrays.asList(profile1, profile2));

        String serialized = toJSON(listProfiles);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("user_attribute_profiles", listProfiles));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ListUserAttributeProfile listProfiles = fromJSON(jsonWithProfiles, ListUserAttributeProfile.class);

        assertThat(listProfiles, is(notNullValue()));
        assertThat(listProfiles.getUserAttributeProfiles(), is(notNullValue()));
        assertThat(listProfiles.getUserAttributeProfiles(), hasSize(3));

        List<UserAttributeProfile> profiles = listProfiles.getUserAttributeProfiles();

        // Test first profile - full details
        UserAttributeProfile firstProfile = profiles.get(0);
        assertThat(firstProfile.getId(), is("uap_1234567890"));
        assertThat(firstProfile.getName(), is("This is just a test"));

        // Test UserId of first profile
        UserId userId = firstProfile.getUserId();
        assertThat(userId, is(notNullValue()));
        assertThat(userId.getOidcMapping(), is("sub"));
        assertThat(userId.getSamlMapping(), hasSize(1));
        assertThat(userId.getSamlMapping().get(0), is("urn:oid:0.9.10.10.100.1.1"));
        assertThat(userId.getScimMapping(), is("userName"));

        // Test UserAttributes of first profile
        Map<String, UserAttributes> userAttributes = firstProfile.getUserAttributes();
        assertThat(userAttributes, is(notNullValue()));
        assertThat(userAttributes, hasKey("username"));

        UserAttributes usernameAttr = userAttributes.get("username");
        assertThat(usernameAttr.getLabel(), is("test User"));
        assertThat(usernameAttr.getDescription(), is("This is just a test"));
        assertThat(usernameAttr.getAuth0Mapping(), is("testUser"));
        assertThat(usernameAttr.isProfileRequired(), is(false));

        // Test strategy overrides in first profile
        assertThat(usernameAttr.getStrategyOverrides(), is(notNullValue()));
        assertThat(usernameAttr.getStrategyOverrides(), hasKey("oidc"));

        // Test second profile - basic details
        UserAttributeProfile secondProfile = profiles.get(1);
        assertThat(secondProfile.getId(), is("uap_123456789012345"));
        assertThat(secondProfile.getName(), is("Updated Test Organization"));

        // Test third profile - with different strategy overrides
        UserAttributeProfile thirdProfile = profiles.get(2);
        assertThat(thirdProfile.getId(), is("uap_1234"));
        assertThat(thirdProfile.getName(), is("This is another test"));

        // Test UserId strategy overrides in third profile
        UserId thirdUserId = thirdProfile.getUserId();
        assertThat(thirdUserId.getStrategyOverrides(), is(notNullValue()));
        assertThat(thirdUserId.getStrategyOverrides(), hasKey("google-apps"));

        // Test UserAttributes strategy overrides in third profile
        Map<String, UserAttributes> thirdUserAttributes = thirdProfile.getUserAttributes();
        UserAttributes thirdUsernameAttr = thirdUserAttributes.get("username");
        assertThat(thirdUsernameAttr.getStrategyOverrides(), is(notNullValue()));
        assertThat(thirdUsernameAttr.getStrategyOverrides(), hasKey("okta"));
    }

}
