package com.auth0.json.mgmt.selfserviceprofiles;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SelfServiceProfileResponseTest extends JsonTest<SelfServiceProfileResponse> {

    private final static String SELF_SERVICE_PROFILE_RESPONSE_JSON = "src/test/resources/mgmt/self_service_profile_response.json";

    @Test
    public void deserialize() throws Exception {
        SelfServiceProfileResponse deserialized = fromJSON(readTextFile(SELF_SERVICE_PROFILE_RESPONSE_JSON), SelfServiceProfileResponse.class);

        assertThat(deserialized.getId(), is("id"));
        assertThat(deserialized.getName(), is("Test"));
        assertThat(deserialized.getDescription(), is("This is a Test"));
        assertThat(deserialized.getUserAttributes().get(0).getName(), is("Phone"));
        assertThat(deserialized.getUserAttributes().get(0).getDescription(), is("This is Phone Number"));
        assertThat(deserialized.getUserAttributes().get(0).getIsOptional(), is(true));
        assertThat(deserialized.getBranding().getColors().getPrimary(), is("#ffffff"));
        assertThat(deserialized.getBranding().getLogoUrl(), is("https://www.google.com"));
        assertThat(deserialized.getAllowedStrategies().get(0), is("oidc"));
        assertThat(deserialized.getCreatedAt(), is("2024-12-20T09:32:13.885Z"));
        assertThat(deserialized.getCreatedAt(), is("2024-12-20T09:32:13.885Z"));
    }

    @Test
    public void serialize() throws Exception {
        SelfServiceProfileResponse selfServiceProfileResponse = new SelfServiceProfileResponse();
        selfServiceProfileResponse.setId("id");
        selfServiceProfileResponse.setName("Test");
        selfServiceProfileResponse.setDescription("This is for Test");

        UserAttribute userAttribute = new UserAttribute("Phone", "This is Phone Number", true);
        List<UserAttribute> userAttributes = new ArrayList<>();
        userAttributes.add(userAttribute);
        selfServiceProfileResponse.setUserAttributes(userAttributes);

        Branding branding = new Branding();
        branding.setColors(new Color("#ffffff"));
        branding.setLogoUrl("https://www.google.com");
        selfServiceProfileResponse.setBranding(branding);

        List<String> allowedStrategies = new ArrayList<>();
        allowedStrategies.add("oidc");
        selfServiceProfileResponse.setAllowedStrategies(allowedStrategies);

        selfServiceProfileResponse.setCreatedAt("2024-12-20T09:32:13.885Z");
        selfServiceProfileResponse.setUpdatedAt("2024-12-20T09:32:13.885Z");

        String serialized = toJSON(selfServiceProfileResponse);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, hasEntry("id", "id"));
        assertThat(serialized, hasEntry("name", "Test"));
        assertThat(serialized, hasEntry("description", "This is for Test"));
        assertThat(serialized, hasEntry("user_attributes", notNullValue()));
        assertThat(serialized, containsString("\"user_attributes\":[{\"name\":\"Phone\",\"description\":\"This is Phone Number\",\"is_optional\":true}]"));
        assertThat(serialized, hasEntry("branding", notNullValue()));
        assertThat(serialized, containsString("\"branding\":{\"logo_url\":\"https://www.google.com\",\"colors\":{\"primary\":\"#ffffff\"}}"));
        assertThat(serialized, hasEntry("allowed_strategies", notNullValue()));
        assertThat(serialized, containsString("\"allowed_strategies\":[\"oidc\"]"));
        assertThat(serialized, hasEntry("created_at", "2024-12-20T09:32:13.885Z"));
        assertThat(serialized, hasEntry("updated_at", "2024-12-20T09:32:13.885Z"));
    }
}
