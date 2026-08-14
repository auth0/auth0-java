package com.auth0.client.mgmt;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.auth0.json.ObjectMapperProvider;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Base64;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class ManagementApiTelemetryTest {

    private JsonNode decodeTelemetry(ManagementApi api) throws Exception {
        Map<String, String> headers = api.clientOptions.headers(null);
        String value = headers.get("Auth0-Client");
        assertThat(value, is(notNullValue()));
        byte[] json = Base64.getUrlDecoder().decode(value);
        return ObjectMapperProvider.getMapper().readTree(json);
    }

    @Test
    public void shouldSendDefaultTelemetryWhenNotConfigured() throws Exception {
        ManagementApi api = ManagementApi.builder()
                .domain("my-tenant.auth0.com")
                .token("test-token")
                .build();

        JsonNode telemetry = decodeTelemetry(api);
        assertThat(telemetry.get("name").asText(), is("auth0-java"));
    }

    @Test
    public void shouldSendCustomTelemetryWhenConfigured() throws Exception {
        ManagementApi api = ManagementApi.builder()
                .domain("my-tenant.auth0.com")
                .token("test-token")
                .withTelemetry("my-wrapper-sdk", "1.2.3")
                .build();

        JsonNode telemetry = decodeTelemetry(api);
        assertThat(telemetry.get("name").asText(), is("my-wrapper-sdk"));
        assertThat(telemetry.get("version").asText(), is("1.2.3"));
        assertThat(telemetry.get("env"), is(notNullValue()));
    }

    @Test
    public void shouldReturnBuilderFromWithTelemetry() {
        ManagementApiBuilder builder = ManagementApi.builder();
        ManagementApiBuilder result = builder.withTelemetry("my-wrapper-sdk", "1.2.3");
        assertThat(result, is(sameInstance(builder)));
    }
}
