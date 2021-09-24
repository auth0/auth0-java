package com.auth0.json.mgmt.actions;

import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ExecutionTest extends JsonTest<Execution> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"id\": \"execution-id\",\n" +
            "  \"trigger_id\": \"post-login\",\n" +
            "  \"status\": \"partial\",\n" +
            "  \"results\": [\n" +
            "    {\n" +
            "      \"binding_id\": \"18490d5b-7d34-42c5-a21d-37cb77c666a5\",\n" +
            "      \"version_id\": \"fbc0chac-2863-4c47-8445-11c2881fd1e9\",\n" +
            "      \"action_name\": \"my action\",\n" +
            "      \"response\": {\n" +
            "        \"error\": {\n" +
            "          \"message\": \"oops\",\n" +
            "          \"name\": \"Error\"\n" +
            "        },\n" +
            "        \"stats\": {\n" +
            "          \"total_request_duration_ms\": 383,\n" +
            "          \"total_runtime_execution_duration_ms\": 377,\n" +
            "          \"runtime_processing_duration_ms\": 6,\n" +
            "          \"action_duration_ms\": 40,\n" +
            "          \"runtime_external_call_duration_ms\": 331,\n" +
            "          \"boot_duration_ms\": 337,\n" +
            "          \"network_duration_ms\": 6\n" +
            "        }\n" +
            "      },\n" +
            "      \"error\": {\n" +
            "        \"id\": \"invalid_argument\",\n" +
            "        \"msg\": \"Invalid Argument\"\n" +
            "      },\n" +
            "      \"started_at\": \"2021-08-16T19:14:04.218396333Z\",\n" +
            "      \"ended_at\": \"2021-08-16T19:14:04.602232335Z\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"created_at\": \"2021-08-16T19:14:04.218386241Z\",\n" +
            "  \"updated_at\": \"2021-08-16T19:14:04.218386241Z\"\n" +
            "}\n";

        Execution execution = fromJSON(json, Execution.class);
        assertThat(execution, is(notNullValue()));

        assertThat(execution.getCreatedAt(), is(parseJSONDate("2021-08-16T19:14:04.218386241Z")));
        assertThat(execution.getUpdatedAt(), is(parseJSONDate("2021-08-16T19:14:04.218386241Z")));
        assertThat(execution.getId(), is("execution-id"));
        assertThat(execution.getStatus(), is("partial"));
        assertThat(execution.getTriggerId(), is("post-login"));

        List<ExecutionResult> results = execution.getResults();
        assertThat(results, is(notNullValue()));
        assertThat(results, hasSize(1));
        assertThat(results.get(0).getActionName(), is("my action"));
        assertThat(results.get(0).getStartedAt(), is(parseJSONDate("2021-08-16T19:14:04.218396333Z")));
        assertThat(results.get(0).getEndedAt(), is(parseJSONDate("2021-08-16T19:14:04.602232335Z")));

        Error error = results.get(0).getError();
        assertThat(error, is(notNullValue()));
        assertThat(error.getId(), is("invalid_argument"));
        assertThat(error.getMessage(), is("Invalid Argument"));
        assertThat(error.getUrl(), is(nullValue()));
    }
}
