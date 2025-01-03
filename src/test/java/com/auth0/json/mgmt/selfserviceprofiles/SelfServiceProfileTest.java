package com.auth0.json.mgmt.selfserviceprofiles;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class SelfServiceProfileTest extends JsonTest<SelfServiceProfile> {
    private final static String SELF_SERVICE_PROFILE_JSON = "src/test/resources/mgmt/self_service_profile.json";

    @Test
    public void deserialize() throws Exception {
        SelfServiceProfile deserialized = fromJSON(readTextFile(SELF_SERVICE_PROFILE_JSON), SelfServiceProfile.class);

        assertThat(deserialized.getName(), is("Test"));
        assertThat(deserialized.getDescription(), is("This is for Test"));
        assertThat(deserialized.getUserAttributes().get(0).getName(), is("Phone"));
        assertThat(deserialized.getUserAttributes().get(0).getDescription(), is("This is Phone Number"));
        assertThat(deserialized.getUserAttributes().get(0).getIsOptional(), is(true));
        assertThat(deserialized.getBranding().getColors().getPrimary(), is("#ffffff"));
        assertThat(deserialized.getBranding().getLogoUrl(), is("https://www.google.com"));
        assertThat(deserialized.getAllowedStrategies().get(0), is("oidc"));
    }

    @Test
    public void serialize() throws Exception {
        SelfServiceProfile selfServiceProfile = new SelfServiceProfile();
        selfServiceProfile.setName("Test");
        selfServiceProfile.setDescription("This is for Test");

        UserAttribute userAttribute = new UserAttribute("Phone", "This is Phone Number", true);
        List<UserAttribute> userAttributes = new ArrayList<>();
        userAttributes.add(userAttribute);
        selfServiceProfile.setUserAttributes(userAttributes);

        Branding branding = new Branding();
        branding.setColors(new Color("#ffffff"));
        branding.setLogoUrl("https://www.google.com");
        selfServiceProfile.setBranding(branding);

        List<String> allowedStrategies = new ArrayList<>();
        allowedStrategies.add("oidc");
        selfServiceProfile.setAllowedStrategies(allowedStrategies);

        String serialized = toJSON(selfServiceProfile);
        assertThat(serialized, is(notNullValue()));

        assertThat(serialized, hasEntry("name", "Test"));
        assertThat(serialized, hasEntry("description", "This is for Test"));
        assertThat(serialized, hasEntry("user_attributes", notNullValue()));
        assertThat(serialized, containsString("\"user_attributes\":[{\"name\":\"Phone\",\"description\":\"This is Phone Number\",\"is_optional\":true}]"));
        assertThat(serialized, hasEntry("branding", notNullValue()));
        assertThat(serialized, containsString("\"branding\":{\"logo_url\":\"https://www.google.com\",\"colors\":{\"primary\":\"#ffffff\"}}"));
        assertThat(serialized, hasEntry("allowed_strategies", notNullValue()));
        assertThat(serialized, containsString("\"allowed_strategies\":[\"oidc\"]"));
    }
}
