package com.auth0.json.mgmt.actions;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ActionTest extends JsonTest<Action> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"id\": \"action-id\",\n" +
            "  \"name\": \"my action\",\n" +
            "  \"supported_triggers\": [\n" +
            "    {\n" +
            "      \"id\": \"post-login\",\n" +
            "      \"version\": \"v2\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"created_at\": \"2021-08-11T15:44:05.330221459Z\",\n" +
            "  \"updated_at\": \"2021-08-16T20:24:57.108759345Z\",\n" +
            "  \"code\": \"some code\",\n" +
            "  \"dependencies\": [\n" +
            "    {\n" +
            "      \"name\": \"slack-notify\",\n" +
            "      \"version\": \"0.1.7\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"runtime\": \"node16\",\n" +
            "  \"status\": \"built\",\n" +
            "  \"secrets\": [\n" +
            "    {\n" +
            "      \"name\": \"secret-key-1\",\n" +
            "      \"updated_at\": \"2021-08-12T17:09:42.071504554Z\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"current_version\": {\n" +
            "    \"id\": \"c4d1c41e-a14d-441a-bbbe-da50871f0ce90\",\n" +
            "    \"code\": \"/**\\n* Handler that will be called during the execution of a PostLogin flow.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\nexports.onExecutePostLogin = async (event, api) => {\\n  // var a = null;\\n  // a.boom(;)\\n  // throw new Error(\\\"oops\\\");\\n  console.log(\\\"testing updated!\\\");\\n};\\n\\n\\n/**\\n* Handler that will be invoked when this action is resuming after an external redirect. If your\\n* onExecutePostLogin function does not perform a redirect, this function can be safely ignored.\\n*\\n* @param {Event} event - Details about the user and the context in which they are logging in.\\n* @param {PostLoginAPI} api - Interface whose methods can be used to change the behavior of the login.\\n*/\\n// exports.onContinuePostLogin = async (event, api) => {\\n// };\\n\",\n" +
            "    \"runtime\": \"node16\",\n" +
            "    \"status\": \"BUILT\",\n" +
            "    \"number\": 18,\n" +
            "    \"build_time\": \"2021-08-16T20:26:42.425146135Z\",\n" +
            "    \"created_at\": \"2021-08-16T20:26:42.253086110Z\",\n" +
            "    \"updated_at\": \"2021-08-16T20:26:42.427338891Z\"\n" +
            "  },\n" +
            "  \"deployed_version\": {\n" +
            "    \"code\": \"some code\",\n" +
            "    \"dependencies\": [\n" +
            "      {\n" +
            "        \"name\": \"slack-notify\",\n" +
            "        \"version\": \"0.1.7\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"id\": \"some-id\",\n" +
            "    \"deployed\": true,\n" +
            "    \"number\": 18,\n" +
            "    \"built_at\": \"2021-08-16T20:26:42.425146135Z\",\n" +
            "    \"secrets\": [\n" +
            "      {\n" +
            "        \"name\": \"secret-key-1\",\n" +
            "        \"updated_at\": \"2021-08-12T17:09:42.071504554Z\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"status\": \"built\",\n" +
            "    \"created_at\": \"2021-08-16T20:26:42.253086110Z\",\n" +
            "    \"updated_at\": \"2021-08-16T20:26:42.427338891Z\",\n" +
            "    \"runtime\": \"node16\"\n" +
            "  },\n" +
            "  \"all_changes_deployed\": true\n" +
            "}\n";

        Action action = fromJSON(json, Action.class);

        assertThat(action, is(notNullValue()));
        assertThat(action.getId(), is("action-id"));
        assertThat(action.getName(), is("my action"));
        assertThat(action.getSupportedTriggers(), is(notNullValue()));
        assertThat(action.getSupportedTriggers().size(), is(1));
        assertThat(action.getSupportedTriggers().get(0).getId(), is("post-login"));
        assertThat(action.getSupportedTriggers().get(0).getVersion(), is("v2"));
        assertThat(action.getCreatedAt(), is(parseJSONDate("2021-08-11T15:44:05.330221459Z")));
        assertThat(action.getUpdatedAt(), is(parseJSONDate("2021-08-16T20:24:57.108759345Z")));
        assertThat(action.getCode(), is("some code"));
        assertThat(action.getDependencies(), is(notNullValue()));
        assertThat(action.getDependencies().size(), is(1));
        assertThat(action.getDependencies().get(0).getName(), is("slack-notify"));
        assertThat(action.getDependencies().get(0).getVersion(), is("0.1.7"));
        assertThat(action.getRuntime(), is("node16"));
        assertThat(action.getStatus(), is("built"));
        assertThat(action.getSecrets(), is(notNullValue()));
        assertThat(action.getSecrets().size(), is(1));
        assertThat(action.getSecrets().get(0).getName(), is("secret-key-1"));
        assertThat(action.getSecrets().get(0).getUpdatedAt(), is(parseJSONDate("2021-08-12T17:09:42.071504554Z")));
        assertThat(action.getAllChangesDeployed(), is(true));

        // current_version hidden from API explorer, should it be on model?

        assertThat(action.getDeployedVersion(), is(notNullValue()));
        assertThat(action.getDeployedVersion().getCode(), is("some code"));
        assertThat(action.getDeployedVersion().getDependencies(), is(notNullValue()));
        assertThat(action.getDeployedVersion().getDependencies().size(), is(1));
        assertThat(action.getDeployedVersion().getDependencies().get(0).getName(), is("slack-notify"));
        assertThat(action.getDeployedVersion().getDependencies().get(0).getVersion(), is("0.1.7"));
        assertThat(action.getDeployedVersion().getId(), is("some-id"));
        assertThat(action.getDeployedVersion().getDeployed(), is(true));
        assertThat(action.getDeployedVersion().getNumber(), is(18));
        assertThat(action.getDeployedVersion().getBuiltAt(), is(parseJSONDate("2021-08-16T20:26:42.425146135Z")));
        assertThat(action.getDeployedVersion().getSecrets(), is(notNullValue()));
        assertThat(action.getDeployedVersion().getSecrets().size(), is(1));
        assertThat(action.getDeployedVersion().getSecrets().get(0).getName(), is("secret-key-1"));
        assertThat(action.getDeployedVersion().getSecrets().get(0).getUpdatedAt(), is(parseJSONDate("2021-08-12T17:09:42.071504554Z")));
        assertThat(action.getDeployedVersion().getStatus(), is("built"));
        assertThat(action.getDeployedVersion().getCreatedAt(), is(parseJSONDate("2021-08-16T20:26:42.253086110Z")));
        assertThat(action.getDeployedVersion().getUpdatedAt(), is(parseJSONDate("2021-08-16T20:26:42.427338891Z")));
    }

    @Test
    public void shouldSerialize() throws Exception {
        Action action = new Action();

        Trigger trigger = new Trigger();
        trigger.setId("post-login");
        trigger.setVersion("v1");
        trigger.setDefaultRuntime("node12");
        trigger.setStatus("CURRENT");
        trigger.setRuntimes(Arrays.asList("node12", "node16"));

        Secret secret1 = new Secret("secret-1", "hidden-1");
        Secret secret2 = new Secret("secret-2", "hidden-2");

        Dependency dependency = new Dependency("lowdash", "1.5.8");

        action.setName("action-name");
        action.setRuntime("node16");
        action.setCode("some code");
        action.setSupportedTriggers(Collections.singletonList(trigger));
        action.setSecrets(Arrays.asList(secret1, secret2));
        action.setDependencies(Collections.singletonList(dependency));

        String serialized = toJSON(action);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("name", "action-name"));
        assertThat(serialized, JsonMatcher.hasEntry("runtime", "node16"));
        assertThat(serialized, JsonMatcher.hasEntry("code", "some code"));
        assertThat(serialized, JsonMatcher.hasEntry("supported_triggers", is(notNullValue())));
        assertThat(serialized, JsonMatcher.hasEntry("dependencies", is(notNullValue())));
        assertThat(serialized, JsonMatcher.hasEntry("secrets", is(notNullValue())));

    }
}
