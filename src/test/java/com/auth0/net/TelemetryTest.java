package com.auth0.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TelemetryTest {

    @Test
    public void shouldReturnBasicTelemetryBase64Value() throws Exception {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0");
        String value = telemetry.getValue();
        assertThat(value, is(notNullValue()));
        assertThat(value, is("eyJuYW1lIjoiYXV0aDAtamF2YSIsImVudiI6eyJqYXZhIjoiMS44In0sInZlcnNpb24iOiIxLjAuMCJ9"));

        String completeString = new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
        TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> complete = new ObjectMapper().readValue(completeString, mapType);
        assertThat((String) complete.get("name"), is("auth0-java"));
        assertThat((String) complete.get("version"), is("1.0.0"));
        Map<String, Object> completeEnv = (Map<String, Object>) complete.get("env");
        assertThat((String) completeEnv.get("auth0-java"), is(nullValue()));
        assertThat((String) completeEnv.get("java"), is("1.8"));
    }

    @Test
    public void shouldReturnCompleteTelemetryBase64Value() throws Exception {
        Telemetry telemetry = new Telemetry("lock", "1.0.0", "2.1.3");
        String value = telemetry.getValue();
        assertThat(value, is(notNullValue()));
        assertThat(value, is("eyJuYW1lIjoibG9jayIsImVudiI6eyJqYXZhIjoiMS44IiwiYXV0aDAtamF2YSI6IjIuMS4zIn0sInZlcnNpb24iOiIxLjAuMCJ9"));

        String completeString = new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
        TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> complete = new ObjectMapper().readValue(completeString, mapType);
        assertThat((String) complete.get("name"), is("lock"));
        assertThat((String) complete.get("version"), is("1.0.0"));
        Map<String, Object> completeEnv = (Map<String, Object>) complete.get("env");
        assertThat((String) completeEnv.get("auth0-java"), is("2.1.3"));
        assertThat((String) completeEnv.get("java"), is("1.8"));
    }

    @Test
    public void shouldAcceptNullVersionAlwaysIncludeJavaVersion() {
        Telemetry telemetry = new Telemetry("auth0-java", null);
        assertThat(telemetry.getValue(), is(notNullValue()));
        assertThat(telemetry.getEnvironment(), is(notNullValue()));
    }

    @Test
    public void shouldNotAcceptNullName() {
        Telemetry telemetry = new Telemetry(null, null);
        assertThat(telemetry.getValue(), is(nullValue()));
        assertThat(telemetry.getEnvironment(), is(notNullValue()));
    }

    @Test
    public void shouldGetName() {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0");
        assertThat(telemetry.getName(), is("auth0-java"));
    }

    @Test
    public void shouldGetVersion() {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0");
        assertThat(telemetry.getVersion(), is("1.0.0"));
    }

    @Test
    public void shouldNotIncludeLibraryVersionIfNotProvided() {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0");
        assertThat(telemetry.getLibraryVersion(), is(nullValue()));
        assertThat(telemetry.getEnvironment().containsKey("auth0-java"), is(false));
    }

    @Test
    public void shouldGetLibraryVersion() {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0", "2.1.3");
        assertThat(telemetry.getLibraryVersion(), is("2.1.3"));
        assertThat(telemetry.getEnvironment().get("auth0-java"), is("2.1.3"));
    }

}
