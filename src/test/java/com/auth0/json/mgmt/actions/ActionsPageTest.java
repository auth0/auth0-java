package com.auth0.json.mgmt.actions;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ActionsPageTest extends JsonTest<ActionsPage> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"actions\": [\n" +
            "    {\n" +
            "      \"id\": \"action-id\",\n" +
            "      \"name\": \"my action\",\n" +
            "      \"supported_triggers\": [\n" +
            "        {\n" +
            "          \"id\": \"post-login\",\n" +
            "          \"version\": \"v2\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"created_at\": \"2021-08-16T19:38:33.018980733Z\",\n" +
            "      \"updated_at\": \"2021-08-16T19:38:33.047653418Z\",\n" +
            "      \"code\": \"some code\",\n" +
            "      \"dependencies\": [],\n" +
            "      \"runtime\": \"node16\",\n" +
            "      \"status\": \"built\",\n" +
            "      \"secrets\": [],\n" +
            "      \"deployed_version\": {\n" +
            "        \"code\": \"/**\\n* Handler that will be called during the execution of a PostLogin flow.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\nexports.onExecutePostLogin = async (event, api) => {\\n};\\n\\n\\n/**\\n* Handler that will be invoked when this action is resuming after an external redirect. If your\\n* onExecutePostLogin function does not perform a redirect, this function can be safely ignored.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\n// exports.onContinuePostLogin = async (event, api) => {\\n// };\\n\",\n" +
            "        \"dependencies\": [],\n" +
            "        \"id\": \"a1fc2198-119dd4403-8a45-9f3b6bf32a6e\",\n" +
            "        \"deployed\": true,\n" +
            "        \"number\": 1,\n" +
            "        \"built_at\": \"2021-08-16T19:39:37.789535383Z\",\n" +
            "        \"secrets\": [],\n" +
            "        \"status\": \"built\",\n" +
            "        \"created_at\": \"2021-08-16T19:39:37.655559720Z\",\n" +
            "        \"updated_at\": \"2021-08-16T19:39:37.791508099Z\",\n" +
            "        \"runtime\": \"node16\"\n" +
            "      },\n" +
            "      \"all_changes_deployed\": true\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"ff66aa6f-69c2-4b66-8d6d-5b4f0cd94820\",\n" +
            "      \"name\": \"new-action-6\",\n" +
            "      \"supported_triggers\": [\n" +
            "        {\n" +
            "          \"id\": \"post-login\",\n" +
            "          \"version\": \"v2\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"created_at\": \"2021-08-17T19:06:56.702356177Z\",\n" +
            "      \"updated_at\": \"2021-08-17T19:06:56.726302287Z\",\n" +
            "      \"code\": \"some code\",\n" +
            "      \"dependencies\": [\n" +
            "        {\n" +
            "          \"name\": \"my-dep\",\n" +
            "          \"version\": \"1.0.0\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"runtime\": \"node16\",\n" +
            "      \"status\": \"built\",\n" +
            "      \"secrets\": [\n" +
            "        {\n" +
            "          \"name\": \"my-secret-key\",\n" +
            "          \"updated_at\": \"2021-08-17T19:06:56.726302287Z\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"all_changes_deployed\": false\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total\": 4,\n" +
            "  \"page\": 1,\n" +
            "  \"per_page\": 2\n" +
            "}\n";

        ActionsPage page = fromJSON(json, ActionsPage.class);

        assertThat(page, is(notNullValue()));
        assertThat(page.getItems().size(), is(2));
        assertThat(page.getItems().get(0), is(instanceOf(Action.class)));
        assertThat(page.getItems().get(1), is(instanceOf(Action.class)));
        assertThat(page.getTotal(), is(4));
    }
}
