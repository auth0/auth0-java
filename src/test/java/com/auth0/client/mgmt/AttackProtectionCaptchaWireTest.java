package com.auth0.client.mgmt;

import com.auth0.client.mgmt.attackprotection.types.UpdateAttackProtectionCaptchaRequestContent;
import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.types.GetAttackProtectionCaptchaResponseContent;
import com.auth0.client.mgmt.types.UpdateAttackProtectionCaptchaResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttackProtectionCaptchaWireTest {
    private MockWebServer server;
    private ManagementApi client;
    private ObjectMapper objectMapper = ObjectMappers.JSON_MAPPER;

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
        client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .build();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"active_provider_id\":\"active_provider_id\",\"arkose\":{\"site_key\":\"site_key\",\"fail_open\":true,\"client_subdomain\":\"client_subdomain\",\"verify_subdomain\":\"verify_subdomain\"},\"auth_challenge\":{\"fail_open\":true},\"hcaptcha\":{\"site_key\":\"site_key\"},\"friendly_captcha\":{\"site_key\":\"site_key\"},\"recaptcha_enterprise\":{\"site_key\":\"site_key\",\"project_id\":\"project_id\"},\"recaptcha_v2\":{\"site_key\":\"site_key\"},\"simple_captcha\":{\"key\":\"value\"}}"));
        GetAttackProtectionCaptchaResponseContent response =
                client.attackProtection().captcha().get();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"active_provider_id\": \"active_provider_id\",\n"
                + "  \"arkose\": {\n"
                + "    \"site_key\": \"site_key\",\n"
                + "    \"fail_open\": true,\n"
                + "    \"client_subdomain\": \"client_subdomain\",\n"
                + "    \"verify_subdomain\": \"verify_subdomain\"\n"
                + "  },\n"
                + "  \"auth_challenge\": {\n"
                + "    \"fail_open\": true\n"
                + "  },\n"
                + "  \"hcaptcha\": {\n"
                + "    \"site_key\": \"site_key\"\n"
                + "  },\n"
                + "  \"friendly_captcha\": {\n"
                + "    \"site_key\": \"site_key\"\n"
                + "  },\n"
                + "  \"recaptcha_enterprise\": {\n"
                + "    \"site_key\": \"site_key\",\n"
                + "    \"project_id\": \"project_id\"\n"
                + "  },\n"
                + "  \"recaptcha_v2\": {\n"
                + "    \"site_key\": \"site_key\"\n"
                + "  },\n"
                + "  \"simple_captcha\": {\n"
                + "    \"key\": \"value\"\n"
                + "  }\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    @Test
    public void testUpdate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"active_provider_id\":\"active_provider_id\",\"arkose\":{\"site_key\":\"site_key\",\"fail_open\":true,\"client_subdomain\":\"client_subdomain\",\"verify_subdomain\":\"verify_subdomain\"},\"auth_challenge\":{\"fail_open\":true},\"hcaptcha\":{\"site_key\":\"site_key\"},\"friendly_captcha\":{\"site_key\":\"site_key\"},\"recaptcha_enterprise\":{\"site_key\":\"site_key\",\"project_id\":\"project_id\"},\"recaptcha_v2\":{\"site_key\":\"site_key\"},\"simple_captcha\":{\"key\":\"value\"}}"));
        UpdateAttackProtectionCaptchaResponseContent response = client.attackProtection()
                .captcha()
                .update(UpdateAttackProtectionCaptchaRequestContent.builder().build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PATCH", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{}";
        JsonNode actualJson = objectMapper.readTree(actualRequestBody);
        JsonNode expectedJson = objectMapper.readTree(expectedRequestBody);
        Assertions.assertTrue(jsonEquals(expectedJson, actualJson), "Request body structure does not match expected");
        if (actualJson.has("type") || actualJson.has("_type") || actualJson.has("kind")) {
            String discriminator = null;
            if (actualJson.has("type")) discriminator = actualJson.get("type").asText();
            else if (actualJson.has("_type"))
                discriminator = actualJson.get("_type").asText();
            else if (actualJson.has("kind"))
                discriminator = actualJson.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualJson.isNull()) {
            Assertions.assertTrue(
                    actualJson.isObject() || actualJson.isArray() || actualJson.isValueNode(),
                    "request should be a valid JSON value");
        }

        if (actualJson.isArray()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Array should have valid size");
        }
        if (actualJson.isObject()) {
            Assertions.assertTrue(actualJson.size() >= 0, "Object should have valid field count");
        }

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"active_provider_id\": \"active_provider_id\",\n"
                + "  \"arkose\": {\n"
                + "    \"site_key\": \"site_key\",\n"
                + "    \"fail_open\": true,\n"
                + "    \"client_subdomain\": \"client_subdomain\",\n"
                + "    \"verify_subdomain\": \"verify_subdomain\"\n"
                + "  },\n"
                + "  \"auth_challenge\": {\n"
                + "    \"fail_open\": true\n"
                + "  },\n"
                + "  \"hcaptcha\": {\n"
                + "    \"site_key\": \"site_key\"\n"
                + "  },\n"
                + "  \"friendly_captcha\": {\n"
                + "    \"site_key\": \"site_key\"\n"
                + "  },\n"
                + "  \"recaptcha_enterprise\": {\n"
                + "    \"site_key\": \"site_key\",\n"
                + "    \"project_id\": \"project_id\"\n"
                + "  },\n"
                + "  \"recaptcha_v2\": {\n"
                + "    \"site_key\": \"site_key\"\n"
                + "  },\n"
                + "  \"simple_captcha\": {\n"
                + "    \"key\": \"value\"\n"
                + "  }\n"
                + "}";
        JsonNode actualResponseNode = objectMapper.readTree(actualResponseJson);
        JsonNode expectedResponseNode = objectMapper.readTree(expectedResponseBody);
        Assertions.assertTrue(
                jsonEquals(expectedResponseNode, actualResponseNode),
                "Response body structure does not match expected");
        if (actualResponseNode.has("type") || actualResponseNode.has("_type") || actualResponseNode.has("kind")) {
            String discriminator = null;
            if (actualResponseNode.has("type"))
                discriminator = actualResponseNode.get("type").asText();
            else if (actualResponseNode.has("_type"))
                discriminator = actualResponseNode.get("_type").asText();
            else if (actualResponseNode.has("kind"))
                discriminator = actualResponseNode.get("kind").asText();
            Assertions.assertNotNull(discriminator, "Union type should have a discriminator field");
            Assertions.assertFalse(discriminator.isEmpty(), "Union discriminator should not be empty");
        }

        if (!actualResponseNode.isNull()) {
            Assertions.assertTrue(
                    actualResponseNode.isObject() || actualResponseNode.isArray() || actualResponseNode.isValueNode(),
                    "response should be a valid JSON value");
        }

        if (actualResponseNode.isArray()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Array should have valid size");
        }
        if (actualResponseNode.isObject()) {
            Assertions.assertTrue(actualResponseNode.size() >= 0, "Object should have valid field count");
        }
    }

    /**
     * Compares two JsonNodes with numeric equivalence and null safety.
     * For objects, checks that all fields in 'expected' exist in 'actual' with matching values.
     * Allows 'actual' to have extra fields (e.g., default values added during serialization).
     */
    private boolean jsonEquals(JsonNode expected, JsonNode actual) {
        if (expected == null && actual == null) return true;
        if (expected == null || actual == null) return false;
        if (expected.equals(actual)) return true;
        if (expected.isNumber() && actual.isNumber())
            return Math.abs(expected.doubleValue() - actual.doubleValue()) < 1e-10;
        if (expected.isObject() && actual.isObject()) {
            java.util.Iterator<java.util.Map.Entry<String, JsonNode>> iter = expected.fields();
            while (iter.hasNext()) {
                java.util.Map.Entry<String, JsonNode> entry = iter.next();
                JsonNode actualValue = actual.get(entry.getKey());
                if (actualValue == null || !jsonEquals(entry.getValue(), actualValue)) return false;
            }
            return true;
        }
        if (expected.isArray() && actual.isArray()) {
            if (expected.size() != actual.size()) return false;
            for (int i = 0; i < expected.size(); i++) {
                if (!jsonEquals(expected.get(i), actual.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}
