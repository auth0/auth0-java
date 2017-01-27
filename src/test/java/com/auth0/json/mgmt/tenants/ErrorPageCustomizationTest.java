package com.auth0.json.mgmt.tenants;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ErrorPageCustomizationTest extends JsonTest<ErrorPageCustomization> {

    private static final String json = "{\"enabled\":true,\"html\":\"thewebpage\",\"show_log_link\":true,\"url\":\"https://page.auth0.com/main\"}";

    @Test
    public void shouldSerialize() throws Exception {
        ErrorPageCustomization customization = new ErrorPageCustomization();
        customization.setEnabled(true);
        customization.setHTML("thewebpage");
        customization.setShowLogLink(true);
        customization.setUrl("https://page.auth0.com/main");

        String serialized = toJSON(customization);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("enabled", true));
        assertThat(serialized, JsonMatcher.hasEntry("html", "thewebpage"));
        assertThat(serialized, JsonMatcher.hasEntry("show_log_link", true));
        assertThat(serialized, JsonMatcher.hasEntry("url", "https://page.auth0.com/main"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ErrorPageCustomization customization = fromJSON(json, ErrorPageCustomization.class);

        assertThat(customization, is(notNullValue()));
        assertThat(customization.isEnabled(), is(true));
        assertThat(customization.getHTML(), is("thewebpage"));
        assertThat(customization.willShowLogLink(), is(true));
        assertThat(customization.getUrl(), is("https://page.auth0.com/main"));
    }

}