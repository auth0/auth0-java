package com.auth0.net;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;
import org.junit.Test;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class TelemetryTest {

    @Test
    public void shouldReturnBasicTelemetryBase64Value() throws Exception {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0");
        String value = telemetry.getValue();
        assertThat(value, is(notNullValue()));
        assertThat(value, is("eyJuYW1lIjoiYXV0aDAtamF2YSIsImVudiI6eyJqYXZhIjoiMS44In0sInZlcnNpb24iOiIxLjAuMCJ9"));

        String completeString = new String(Base64.decodeBase64(value), "UTF-8");
        TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> complete = new ObjectMapper().readValue(completeString, mapType);
        assertThat((String) complete.get("name"), is("auth0-java"));
        assertThat((String) complete.get("version"), is("1.0.0"));
        Map<String, Object> completeEnv = (Map<String, Object>) complete.get("env");
        assertThat((String) completeEnv.get("core"), is(nullValue()));
        assertThat((String) completeEnv.get("java"), is("1.8"));
    }

    @Test
    public void shouldReturnCompleteTelemetryBase64Value() throws Exception {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0", "2.1.3");
        String value = telemetry.getValue();
        assertThat(value, is(notNullValue()));
        assertThat(value, is("eyJuYW1lIjoiYXV0aDAtamF2YSIsImVudiI6eyJjb3JlIjoiMi4xLjMiLCJqYXZhIjoiMS44In0sInZlcnNpb24iOiIxLjAuMCJ9"));

        String completeString = new String(Base64.decodeBase64(value), "UTF-8");
        TypeReference<Map<String, Object>> mapType = new TypeReference<Map<String, Object>>() {
        };
        Map<String, Object> complete = new ObjectMapper().readValue(completeString, mapType);
        assertThat((String) complete.get("name"), is("auth0-java"));
        assertThat((String) complete.get("version"), is("1.0.0"));
        Map<String, Object> completeEnv = (Map<String, Object>) complete.get("env");
        assertThat((String) completeEnv.get("core"), is("2.1.3"));
        assertThat((String) completeEnv.get("java"), is("1.8"));
    }

    @Test
    public void shouldAlwaysIncludeJavaVersion() throws Exception {
        Telemetry telemetry = new Telemetry(null, null);
        assertThat(telemetry.getValue(), is(notNullValue()));
        assertThat(telemetry.getEnvironment(), is(notNullValue()));
    }

    @Test
    public void shouldGetName() throws Exception {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0");
        assertThat(telemetry.getName(), is("auth0-java"));
    }

    @Test
    public void shouldGetVersion() throws Exception {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0");
        assertThat(telemetry.getVersion(), is("1.0.0"));
    }

    @Test
    public void shouldNotIncludeCoreIfNotProvided() throws Exception {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0");
        assertThat(telemetry.getLibraryVersion(), is(nullValue()));
        assertThat(telemetry.getEnvironment().containsKey("core"), is(false));
    }

    @Test
    public void shouldGetLibraryVersion() throws Exception {
        Telemetry telemetry = new Telemetry("auth0-java", "1.0.0", "2.1.3");
        assertThat(telemetry.getLibraryVersion(), is("2.1.3"));
        assertThat(telemetry.getEnvironment().get("core"), is("2.1.3"));
    }

}