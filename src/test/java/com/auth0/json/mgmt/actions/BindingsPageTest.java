package com.auth0.json.mgmt.actions;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class BindingsPageTest extends JsonTest<BindingsPage> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"bindings\": [\n" +
            "    {\n" +
            "      \"id\": \"binding-id\",\n" +
            "      \"trigger_id\": \"post-login\",\n" +
            "      \"action\": {\n" +
            "        \"id\": \"action-id\",\n" +
            "        \"name\": \"my action\",\n" +
            "        \"supported_triggers\": [\n" +
            "          {\n" +
            "            \"id\": \"post-login\",\n" +
            "            \"version\": \"v2\"\n" +
            "          }\n" +
            "        ],\n" +
            "        \"created_at\": \"2021-08-11T15:44:05.330221459Z\",\n" +
            "        \"updated_at\": \"2021-08-16T20:24:57.101751726Z\",\n" +
            "        \"current_version\": {\n" +
            "          \"id\": \"1baa9d7e-49db-4e3f-b026-3c1a77dc9a99\",\n" +
            "          \"code\": \"/**\\n* Handler that will be called during the execution of a PostLogin flow.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\nexports.onExecutePostLogin = async (event, api) => {\\n  // var a = null;\\n  // a.boom(;)\\n  // throw new Error(\\\"oops\\\");\\n  console.log(\\\"testing updated!\\\");\\n};\\n\\n\\n/**\\n* Handler that will be invoked when this action is resuming after an external redirect. If your\\n* onExecutePostLogin function does not perform a redirect, this function can be safely ignored.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\n// exports.onContinuePostLogin = async (event, api) => {\\n// };\\n\",\n" +
            "          \"runtime\": \"node16\",\n" +
            "          \"status\": \"BUILT\",\n" +
            "          \"number\": 19,\n" +
            "          \"build_time\": \"2021-08-17T20:34:25.421459622Z\",\n" +
            "          \"created_at\": \"2021-08-17T20:34:25.304585709Z\",\n" +
            "          \"updated_at\": \"2021-08-17T20:34:25.422251817Z\"\n" +
            "        },\n" +
            "        \"deployed_version\": {\n" +
            "          \"code\": \"/**\\n* Handler that will be called during the execution of a PostLogin flow.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\nexports.onExecutePostLogin = async (event, api) => {\\n  // var a = null;\\n  // a.boom(;)\\n  // throw new Error(\\\"oops\\\");\\n  console.log(\\\"testing updated!\\\");\\n};\\n\\n\\n/**\\n* Handler that will be invoked when this action is resuming after an external redirect. If your\\n* onExecutePostLogin function does not perform a redirect, this function can be safely ignored.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\n// exports.onContinuePostLogin = async (event, api) => {\\n// };\\n\",\n" +
            "          \"dependencies\": [\n" +
            "            {\n" +
            "              \"name\": \"slack-notify\",\n" +
            "              \"version\": \"0.1.7\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"id\": \"1bfc9d7e-49db-4e3f-b026-3c1a77dc9a99\",\n" +
            "          \"deployed\": true,\n" +
            "          \"number\": 19,\n" +
            "          \"built_at\": \"2021-08-17T20:34:25.421459622Z\",\n" +
            "          \"secrets\": [\n" +
            "            {\n" +
            "              \"name\": \"secret-key-1\",\n" +
            "              \"updated_at\": \"2021-08-12T17:09:42.071504554Z\"\n" +
            "            }\n" +
            "          ],\n" +
            "          \"status\": \"built\",\n" +
            "          \"created_at\": \"2021-08-17T20:34:25.304585709Z\",\n" +
            "          \"updated_at\": \"2021-08-17T20:34:25.422251817Z\",\n" +
            "          \"runtime\": \"node16\"\n" +
            "        },\n" +
            "        \"all_changes_deployed\": false\n" +
            "      },\n" +
            "      \"created_at\": \"2021-08-16T20:21:06.548275270Z\",\n" +
            "      \"updated_at\": \"2021-08-16T20:21:06.548275270Z\",\n" +
            "      \"display_name\": \"display name\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"total\": 1,\n" +
            "  \"per_page\": 20\n" +
            "}\n";

        BindingsPage page = fromJSON(json, BindingsPage.class);
        assertThat(page, is(notNullValue()));
        assertThat(page.getTotal(), is(1));
        assertThat(page.getItems(), is(notNullValue()));
        assertThat(page.getItems().size(), is(1));

        Binding binding = page.getItems().get(0);
        assertThat(binding, is(notNullValue()));
        assertThat(binding.getId(), is("binding-id"));
        assertThat(binding.getTriggerId(), is("post-login"));
        assertThat(binding.getDisplayName(), is("display name"));
        assertThat(binding.getCreatedAt(), is(parseJSONDate("2021-08-16T20:21:06.548275270Z")));
        assertThat(binding.getUpdatedAt(), is(parseJSONDate("2021-08-16T20:21:06.548275270Z")));
        assertThat(binding.getAction(), is(notNullValue()));
    }
}
