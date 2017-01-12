package com.auth0.json.mgmt.client;

import com.auth0.json.JsonTest;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AddonTest extends JsonTest<Addon> {

    private static final String json = "{\"a-boolean\":true,\"a-string\":\"text\"}";

    @Test
    public void shouldSerialize() throws Exception {
        Addon addon = new Addon();
        addon.setProperty("a-boolean", true);
        addon.setProperty("a-string", "text");

        String serialized = toJSON(addon);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, is(equalTo(json)));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Addon addon = fromJSON(json, Addon.class);

        assertThat(addon, is(notNullValue()));

        assertThat(addon.getProperties(), IsMapContaining.hasEntry("a-boolean", (Object) true));
        assertThat(addon.getProperties(), IsMapContaining.hasEntry("a-string", (Object) "text"));
        assertThat(addon.getProperty("a-boolean"), is((Object) true));
        assertThat(addon.getProperty("a-string"), is((Object) "text"));
    }
}