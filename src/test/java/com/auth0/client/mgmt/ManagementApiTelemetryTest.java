package com.auth0.client.mgmt;

import static com.auth0.AssertsUtil.verifyThrows;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import com.auth0.client.mgmt.core.ClientOptions;
import com.auth0.json.ObjectMapperProvider;
import com.auth0.net.Telemetry;
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
    }

    @Test
    public void shouldNestAuth0JavaInTelemetryEnv() throws Exception {
        ClientOptions options = ClientOptions.builder()
                .environment(com.auth0.client.mgmt.core.Environment.DEFAULT)
                .telemetry(new Telemetry("my-wrapper-sdk", "1.2.3", "auth0-java-9.9.9"))
                .build();

        String value = options.headers(null).get("Auth0-Client");
        assertThat(value, is(notNullValue()));
        JsonNode telemetry =
                ObjectMapperProvider.getMapper().readTree(Base64.getUrlDecoder().decode(value));
        assertThat(telemetry.get("name").asText(), is("my-wrapper-sdk"));
        assertThat(telemetry.get("version").asText(), is("1.2.3"));
        assertThat(telemetry.get("env").get("auth0-java").asText(), is("auth0-java-9.9.9"));
    }

    @Test
    public void shouldPreserveTelemetryAndHeadersWhenCopyingOptions() throws Exception {
        ClientOptions original = ClientOptions.builder()
                .environment(com.auth0.client.mgmt.core.Environment.DEFAULT)
                .telemetry(new Telemetry("my-wrapper-sdk", "1.2.3"))
                .addHeader("Authorization", "Bearer test-token")
                .addHeader("X-Dynamic", () -> "dynamic-value")
                .maxRetries(5)
                .build();

        ClientOptions copy = ClientOptions.Builder.from(original).build();

        Map<String, String> headers = copy.headers(null);
        JsonNode telemetry =
                ObjectMapperProvider.getMapper().readTree(Base64.getUrlDecoder().decode(headers.get("Auth0-Client")));
        assertThat(telemetry.get("name").asText(), is("my-wrapper-sdk"));
        assertThat(telemetry.get("version").asText(), is("1.2.3"));
        assertThat(headers.get("Authorization"), is("Bearer test-token"));
        assertThat(headers.get("X-Dynamic"), is("dynamic-value"));
        assertThat(copy.maxRetries(), is(5));
    }

    @Test
    public void shouldReturnBuilderFromWithTelemetry() {
        ManagementApiBuilder builder = ManagementApi.builder();
        ManagementApiBuilder result = builder.withTelemetry("my-wrapper-sdk", "1.2.3");
        assertThat(result, is(sameInstance(builder)));
    }

    @Test
    public void shouldThrowWhenTelemetryNameIsNull() {
        verifyThrows(
                IllegalArgumentException.class,
                () -> ManagementApi.builder().withTelemetry(null, "1.2.3"),
                "'name' cannot be null!");
    }
}
