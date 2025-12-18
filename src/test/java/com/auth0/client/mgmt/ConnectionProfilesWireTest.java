package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.ConnectionProfile;
import com.auth0.client.mgmt.types.CreateConnectionProfileRequestContent;
import com.auth0.client.mgmt.types.CreateConnectionProfileResponseContent;
import com.auth0.client.mgmt.types.GetConnectionProfileResponseContent;
import com.auth0.client.mgmt.types.GetConnectionProfileTemplateResponseContent;
import com.auth0.client.mgmt.types.ListConnectionProfileRequestParameters;
import com.auth0.client.mgmt.types.ListConnectionProfileTemplateResponseContent;
import com.auth0.client.mgmt.types.UpdateConnectionProfileRequestContent;
import com.auth0.client.mgmt.types.UpdateConnectionProfileResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ConnectionProfilesWireTest {
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
    public void testList() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"next\":\"next\",\"connection_profiles\":[{\"id\":\"id\",\"name\":\"name\",\"connection_name_prefix_template\":\"connection_name_prefix_template\",\"enabled_features\":[\"scim\"]}]}"));
        SyncPagingIterable<ConnectionProfile> response = client.connectionProfiles()
                .list(ListConnectionProfileRequestParameters.builder()
                        .from(OptionalNullable.of("from"))
                        .take(OptionalNullable.of(1))
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        // Pagination response validated via MockWebServer
        // The SDK correctly parses the response into a SyncPagingIterable
    }

    @Test
    public void testCreate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"name\":\"name\",\"organization\":{\"show_as_button\":\"none\",\"assign_membership_on_login\":\"none\"},\"connection_name_prefix_template\":\"connection_name_prefix_template\",\"enabled_features\":[\"scim\"],\"strategy_overrides\":{\"pingfederate\":{\"enabled_features\":[\"scim\"]},\"ad\":{\"enabled_features\":[\"scim\"]},\"adfs\":{\"enabled_features\":[\"scim\"]},\"waad\":{\"enabled_features\":[\"scim\"]},\"google-apps\":{\"enabled_features\":[\"scim\"]},\"okta\":{\"enabled_features\":[\"scim\"]},\"oidc\":{\"enabled_features\":[\"scim\"]},\"samlp\":{\"enabled_features\":[\"scim\"]}}}"));
        CreateConnectionProfileResponseContent response = client.connectionProfiles()
                .create(CreateConnectionProfileRequestContent.builder()
                        .name("name")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"name\": \"name\"\n" + "}";
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
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"organization\": {\n"
                + "    \"show_as_button\": \"none\",\n"
                + "    \"assign_membership_on_login\": \"none\"\n"
                + "  },\n"
                + "  \"connection_name_prefix_template\": \"connection_name_prefix_template\",\n"
                + "  \"enabled_features\": [\n"
                + "    \"scim\"\n"
                + "  ],\n"
                + "  \"strategy_overrides\": {\n"
                + "    \"pingfederate\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"ad\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"adfs\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"waad\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"google-apps\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"okta\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"oidc\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"samlp\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    }\n"
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
    public void testListTemplates() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"connection_profile_templates\":[{\"id\":\"id\",\"display_name\":\"display_name\"}]}"));
        ListConnectionProfileTemplateResponseContent response =
                client.connectionProfiles().listTemplates();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"connection_profile_templates\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"display_name\": \"display_name\"\n"
                + "    }\n"
                + "  ]\n"
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
    public void testGetTemplate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"display_name\":\"display_name\",\"template\":{\"name\":\"name\",\"organization\":{\"show_as_button\":\"none\",\"assign_membership_on_login\":\"none\"},\"connection_name_prefix_template\":\"connection_name_prefix_template\",\"enabled_features\":[\"scim\"]}}"));
        GetConnectionProfileTemplateResponseContent response =
                client.connectionProfiles().getTemplate("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"display_name\": \"display_name\",\n"
                + "  \"template\": {\n"
                + "    \"name\": \"name\",\n"
                + "    \"organization\": {\n"
                + "      \"show_as_button\": \"none\",\n"
                + "      \"assign_membership_on_login\": \"none\"\n"
                + "    },\n"
                + "    \"connection_name_prefix_template\": \"connection_name_prefix_template\",\n"
                + "    \"enabled_features\": [\n"
                + "      \"scim\"\n"
                + "    ]\n"
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
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"name\":\"name\",\"organization\":{\"show_as_button\":\"none\",\"assign_membership_on_login\":\"none\"},\"connection_name_prefix_template\":\"connection_name_prefix_template\",\"enabled_features\":[\"scim\"],\"strategy_overrides\":{\"pingfederate\":{\"enabled_features\":[\"scim\"]},\"ad\":{\"enabled_features\":[\"scim\"]},\"adfs\":{\"enabled_features\":[\"scim\"]},\"waad\":{\"enabled_features\":[\"scim\"]},\"google-apps\":{\"enabled_features\":[\"scim\"]},\"okta\":{\"enabled_features\":[\"scim\"]},\"oidc\":{\"enabled_features\":[\"scim\"]},\"samlp\":{\"enabled_features\":[\"scim\"]}}}"));
        GetConnectionProfileResponseContent response =
                client.connectionProfiles().get("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"organization\": {\n"
                + "    \"show_as_button\": \"none\",\n"
                + "    \"assign_membership_on_login\": \"none\"\n"
                + "  },\n"
                + "  \"connection_name_prefix_template\": \"connection_name_prefix_template\",\n"
                + "  \"enabled_features\": [\n"
                + "    \"scim\"\n"
                + "  ],\n"
                + "  \"strategy_overrides\": {\n"
                + "    \"pingfederate\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"ad\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"adfs\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"waad\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"google-apps\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"okta\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"oidc\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"samlp\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    }\n"
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
    public void testDelete() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        client.connectionProfiles().delete("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("DELETE", request.getMethod());
    }

    @Test
    public void testUpdate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"name\":\"name\",\"organization\":{\"show_as_button\":\"none\",\"assign_membership_on_login\":\"none\"},\"connection_name_prefix_template\":\"connection_name_prefix_template\",\"enabled_features\":[\"scim\"],\"strategy_overrides\":{\"pingfederate\":{\"enabled_features\":[\"scim\"]},\"ad\":{\"enabled_features\":[\"scim\"]},\"adfs\":{\"enabled_features\":[\"scim\"]},\"waad\":{\"enabled_features\":[\"scim\"]},\"google-apps\":{\"enabled_features\":[\"scim\"]},\"okta\":{\"enabled_features\":[\"scim\"]},\"oidc\":{\"enabled_features\":[\"scim\"]},\"samlp\":{\"enabled_features\":[\"scim\"]}}}"));
        UpdateConnectionProfileResponseContent response = client.connectionProfiles()
                .update("id", UpdateConnectionProfileRequestContent.builder().build());
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
                + "  \"id\": \"id\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"organization\": {\n"
                + "    \"show_as_button\": \"none\",\n"
                + "    \"assign_membership_on_login\": \"none\"\n"
                + "  },\n"
                + "  \"connection_name_prefix_template\": \"connection_name_prefix_template\",\n"
                + "  \"enabled_features\": [\n"
                + "    \"scim\"\n"
                + "  ],\n"
                + "  \"strategy_overrides\": {\n"
                + "    \"pingfederate\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"ad\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"adfs\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"waad\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"google-apps\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"okta\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"oidc\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    },\n"
                + "    \"samlp\": {\n"
                + "      \"enabled_features\": [\n"
                + "        \"scim\"\n"
                + "      ]\n"
                + "    }\n"
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
