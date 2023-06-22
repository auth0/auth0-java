package com.auth0.json.mgmt.actions;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class VersionTest  extends JsonTest<Version> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"code\": \"some code\",\n" +
            "  \"dependencies\": [\n" +
            "    {\n" +
            "      \"name\": \"slack-notify\",\n" +
            "      \"version\": \"0.1.7\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"id\": \"version-id\",\n" +
            "  \"deployed\": false,\n" +
            "  \"number\": 19,\n" +
            "  \"secrets\": [\n" +
            "    {\n" +
            "      \"name\": \"secret-key-1\",\n" +
            "      \"updated_at\": \"2021-08-12T17:09:42.071504554Z\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"status\": \"built\",\n" +
            "  \"created_at\": \"2021-08-17T20:34:25.304585709Z\",\n" +
            "  \"updated_at\": \"2021-08-17T20:34:25.304585709Z\",\n" +
            "  \"runtime\": \"node16\",\n" +
            "  \"action\": {\n" +
            "    \"id\": \"action-id\",\n" +
            "    \"name\": \"action name\",\n" +
            "    \"supported_triggers\": [\n" +
            "      {\n" +
            "        \"id\": \"post-login\",\n" +
            "        \"version\": \"v2\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"created_at\": \"2021-08-11T15:44:05.330221459Z\",\n" +
            "    \"updated_at\": \"2021-08-16T20:24:57.101751726Z\",\n" +
            "    \"current_version\": {\n" +
            "      \"id\": \"current-version-id\",\n" +
            "      \"code\": \"some code\",\n" +
            "      \"runtime\": \"node16\",\n" +
            "      \"status\": \"BUILT\",\n" +
            "      \"number\": 15,\n" +
            "      \"build_time\": \"2021-08-16T20:24:10.450936369Z\",\n" +
            "      \"created_at\": \"2021-08-16T20:24:10.269996405Z\",\n" +
            "      \"updated_at\": \"2021-08-16T20:24:10.452885076Z\"\n" +
            "    },\n" +
            "    \"deployed_version\": {\n" +
            "      \"code\": \"some code\",\n" +
            "      \"dependencies\": [\n" +
            "        {\n" +
            "          \"name\": \"slack-notify\",\n" +
            "          \"version\": \"0.1.7\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"id\": \"deployed-version-id\",\n" +
            "      \"deployed\": true,\n" +
            "      \"number\": 15,\n" +
            "      \"built_at\": \"2021-08-16T20:24:10.450936369Z\",\n" +
            "      \"secrets\": [\n" +
            "        {\n" +
            "          \"name\": \"secret-key-1\",\n" +
            "          \"updated_at\": \"2021-08-12T17:09:42.071504554Z\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"status\": \"built\",\n" +
            "      \"created_at\": \"2021-08-16T20:24:10.269996405Z\",\n" +
            "      \"updated_at\": \"2021-08-16T20:24:10.452885076Z\",\n" +
            "      \"runtime\": \"node16\"\n" +
            "    },\n" +
            "    \"all_changes_deployed\": false\n" +
            "  }\n" +
            "}\n";

        Version version = fromJSON(json, Version.class);
        assertThat(version, is(notNullValue()));

        assertThat(version.getCode(), is("some code"));
        assertThat(version.getDependencies(), is(notNullValue()));
        assertThat(version.getDependencies().size(), is(1));
        assertThat(version.getDependencies().get(0).getName(), is("slack-notify"));
        assertThat(version.getDependencies().get(0).getVersion(), is("0.1.7"));
        assertThat(version.getId(), is("version-id"));
        assertThat(version.isDeployed(), is(false));
        assertThat(version.getNumber(), is(19));
        assertThat(version.getSecrets(), is(notNullValue()));
        assertThat(version.getSecrets().size(), is(1));
        assertThat(version.getSecrets().get(0).getName(), is("secret-key-1"));
        assertThat(version.getSecrets().get(0).getUpdatedAt(), is(parseJSONDate("2021-08-12T17:09:42.071504554Z")));
        assertThat(version.getStatus(), is("built"));
        assertThat(version.getCreatedAt(), is(parseJSONDate("2021-08-17T20:34:25.304585709Z")));
        assertThat(version.getUpdatedAt(), is(parseJSONDate("2021-08-17T20:34:25.304585709Z")));
        assertThat(version.getRuntime(), is("node16"));

        Action action = version.getAction();
        assertThat(action, is(notNullValue()));
        assertThat(action.getId(), is("action-id"));
        assertThat(action.getName(), is("action name"));
        assertThat(action.getSupportedTriggers(), is(notNullValue()));
        assertThat(action.getSupportedTriggers().size(), is(1));
        assertThat(action.getSupportedTriggers().get(0).getId(), is("post-login"));
        assertThat(action.getSupportedTriggers().get(0).getVersion(), is("v2"));
        assertThat(action.getCreatedAt(), is(parseJSONDate("2021-08-11T15:44:05.330221459Z")));
        assertThat(action.getUpdatedAt(), is(parseJSONDate("2021-08-16T20:24:57.101751726Z")));

        Version deployedVersion = action.getDeployedVersion();
        assertThat(deployedVersion, is(notNullValue()));
        assertThat(deployedVersion.getCode(), is("some code"));
        assertThat(deployedVersion.getDependencies(), is(notNullValue()));
        assertThat(deployedVersion.getDependencies().size(), is(1));
        assertThat(deployedVersion.getDependencies().get(0).getName(), is("slack-notify"));
        assertThat(deployedVersion.getDependencies().get(0).getVersion(), is("0.1.7"));
        assertThat(deployedVersion.getId(), is("deployed-version-id"));
        assertThat(deployedVersion.isDeployed(), is(true));
        assertThat(deployedVersion.getNumber(), is(15));
        assertThat(deployedVersion.getBuiltAt(), is(parseJSONDate("2021-08-16T20:24:10.450936369Z")));
        assertThat(deployedVersion.getSecrets(), is(notNullValue()));
        assertThat(deployedVersion.getSecrets().size(), is(1));
        assertThat(deployedVersion.getSecrets().get(0).getName(), is("secret-key-1"));
        assertThat(deployedVersion.getSecrets().get(0).getUpdatedAt(), is(parseJSONDate("2021-08-12T17:09:42.071504554Z")));
        assertThat(deployedVersion.getStatus(), is("built"));
        assertThat(deployedVersion.getCreatedAt(), is(parseJSONDate("2021-08-16T20:24:10.269996405Z")));
        assertThat(deployedVersion.getUpdatedAt(), is(parseJSONDate("2021-08-16T20:24:10.452885076Z")));
        assertThat(deployedVersion.getRuntime(), is("node16"));
        assertThat(action.getAllChangesDeployed(), is(false));
    }
}
