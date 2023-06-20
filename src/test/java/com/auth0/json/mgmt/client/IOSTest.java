package com.auth0.json.mgmt.client;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class IOSTest extends JsonTest<IOS> {

    private static final String json = "{\"team_id\":\"team\",\"app_bundle_identifier\":\"identifier\"}";

    @Test
    public void shouldSerialize() throws Exception {
        IOS ios = new IOS("team", "identifier");

        String serialized = toJSON(ios);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("team_id", "team"));
        assertThat(serialized, JsonMatcher.hasEntry("app_bundle_identifier", "identifier"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        IOS ios = fromJSON(json, IOS.class);

        assertThat(ios, is(notNullValue()));

        assertThat(ios.getTeamId(), is("team"));
        assertThat(ios.getAppBundleIdentifier(), is("identifier"));
    }
}
