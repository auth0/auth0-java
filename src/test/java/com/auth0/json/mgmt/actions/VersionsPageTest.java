package com.auth0.json.mgmt.actions;

import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class VersionsPageTest extends JsonTest<VersionsPage> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"versions\": [\n" +
            "    {\n" +
            "      \"code\": \"/**\\n* Handler that will be called during the execution of a PostLogin flow.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\nexports.onExecutePostLogin = async (event, api) => {\\n};\\n\\n\\n/**\\n* Handler that will be invoked when this action is resuming after an external redirect. If your\\n* onExecutePostLogin function does not perform a redirect, this function can be safely ignored.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\n// exports.onContinuePostLogin = async (event, api) => {\\n// };\\n\",\n" +
            "      \"dependencies\": [],\n" +
            "      \"id\": \"a1fcaa98-1199-4403-8a45-9f3b6bf32a6e\",\n" +
            "      \"deployed\": true,\n" +
            "      \"number\": 1,\n" +
            "      \"built_at\": \"2021-08-16T19:39:37.789535383Z\",\n" +
            "      \"secrets\": [],\n" +
            "      \"status\": \"built\",\n" +
            "      \"created_at\": \"2021-08-16T19:39:37.655559720Z\",\n" +
            "      \"updated_at\": \"2021-08-16T19:39:37.791508099Z\",\n" +
            "      \"runtime\": \"node16\",\n" +
            "      \"action\": {\n" +
            "        \"id\": \"a80ae4c9-43c0-4301-9eb7-e9da7852022b\",\n" +
            "        \"name\": \"new-action\",\n" +
            "        \"supported_triggers\": [\n" +
            "          {\n" +
            "            \"id\": \"post-login\",\n" +
            "            \"version\": \"v2\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"created_at\": \"2021-08-16T19:38:33.018980733Z\",\n" +
            "        \"updated_at\": \"2021-08-16T19:38:33.018980733Z\",\n" +
            "        \"all_changes_deployed\": false\n" +
            "      }\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total\": 1,\n" +
            "  \"per_page\": 20\n" +
            "}\n";

        VersionsPage page = fromJSON(json, VersionsPage.class);
        assertThat(page, is(notNullValue()));
        assertThat(page.getItems().size(), is(1));
        assertThat(page.getItems().get(0), is(instanceOf(Version.class)));
        assertThat(page.getTotal(), is(1));
    }

    @Test
    public void shouldCreatePageFromList() {
        VersionsPage page = new VersionsPageDeserializer().createPage(Collections.singletonList(new Version()));

        assertThat(page, is(notNullValue()));
        assertThat(page.getItems(), hasSize(1));
    }

    @Test
    public void shouldCreatePageWithAllParams() {
        VersionsPage page = new VersionsPage(0, 1, 2, 3, "next", Collections.singletonList(new Version()));

        assertThat(page, is(notNullValue()));
        assertThat(page.getItems(), hasSize(1));
        assertThat(page.getStart(), is(0));
        assertThat(page.getLength(), is(1));
        assertThat(page.getTotal(), is(2));
        assertThat(page.getLimit(), is(3));
        assertThat(page.getNext(), is("next"));
    }
}
