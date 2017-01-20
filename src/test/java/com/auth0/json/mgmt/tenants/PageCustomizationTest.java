package com.auth0.json.mgmt.tenants;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class PageCustomizationTest extends JsonTest<PageCustomization> {

    private static final String json = "{\"enabled\":true,\"html\":\"thewebpage\",\"show_log_link\":true,\"url\":\"https://page.auth0.com/main\"}";

    @Test
    public void shouldSerialize() throws Exception {
        PageCustomization customization = new PageCustomization();
        customization.setEnabled(true);
        customization.setHtml("thewebpage");
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
        PageCustomization customization = fromJSON(json, PageCustomization.class);

        assertThat(customization, is(notNullValue()));
        assertThat(customization.getEnabled(), is(true));
        assertThat(customization.getHtml(), is("thewebpage"));
        assertThat(customization.getShowLogLink(), is(true));
        assertThat(customization.getUrl(), is("https://page.auth0.com/main"));
    }

}