package com.auth0.json.mgmt.actions;

import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TriggersTest extends JsonTest<Triggers> {

    @Test
    public void shouldDeserialize() throws Exception {
        String json = "{\n" +
            "  \"triggers\": [\n" +
            "    {\n" +
            "      \"id\": \"post-login\",\n" +
            "      \"version\": \"v1\",\n" +
            "      \"status\": \"DEPRECATED\",\n" +
            "      \"runtimes\": [\n" +
            "        \"node12\"\n" +
            "      ],\n" +
            "      \"default_runtime\": \"node12\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"post-login\",\n" +
            "      \"version\": \"v2\",\n" +
            "      \"status\": \"CURRENT\",\n" +
            "      \"runtimes\": [\n" +
            "        \"node12\",\n" +
            "        \"node16\"\n" +
            "      ],\n" +
            "      \"default_runtime\": \"node16\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n";

        Triggers triggers = fromJSON(json, Triggers.class);
        assertThat(triggers, is(notNullValue()));

        assertThat(triggers.getTriggers(), hasSize(2));
        Trigger trigger = triggers.getTriggers().get(0);
        assertThat(trigger, is(notNullValue()));
        assertThat(trigger.getId(), is("post-login"));
        assertThat(trigger.getVersion(), is("v1"));
        assertThat(trigger.getStatus(), is("DEPRECATED"));
        assertThat(trigger.getDefaultRuntime(), is("node12"));
        assertThat(trigger.getRuntimes(), hasSize(1));
        assertThat(trigger.getRuntimes().get(0), is("node12"));
    }
}
