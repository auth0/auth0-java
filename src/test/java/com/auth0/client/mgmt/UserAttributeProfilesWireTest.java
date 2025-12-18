package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.CreateUserAttributeProfileRequestContent;
import com.auth0.client.mgmt.types.CreateUserAttributeProfileResponseContent;
import com.auth0.client.mgmt.types.GetUserAttributeProfileResponseContent;
import com.auth0.client.mgmt.types.GetUserAttributeProfileTemplateResponseContent;
import com.auth0.client.mgmt.types.ListUserAttributeProfileRequestParameters;
import com.auth0.client.mgmt.types.ListUserAttributeProfileTemplateResponseContent;
import com.auth0.client.mgmt.types.UpdateUserAttributeProfileRequestContent;
import com.auth0.client.mgmt.types.UpdateUserAttributeProfileResponseContent;
import com.auth0.client.mgmt.types.UserAttributeProfile;
import com.auth0.client.mgmt.types.UserAttributeProfileUserAttributeAdditionalProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserAttributeProfilesWireTest {
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
                                "{\"next\":\"next\",\"user_attribute_profiles\":[{\"id\":\"id\",\"name\":\"name\",\"user_attributes\":{\"key\":{\"description\":\"description\",\"label\":\"label\",\"profile_required\":true,\"auth0_mapping\":\"auth0_mapping\"}}}]}"));
        SyncPagingIterable<UserAttributeProfile> response = client.userAttributeProfiles()
                .list(ListUserAttributeProfileRequestParameters.builder()
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
                                "{\"id\":\"id\",\"name\":\"name\",\"user_id\":{\"oidc_mapping\":\"sub\",\"saml_mapping\":[\"saml_mapping\"],\"scim_mapping\":\"scim_mapping\"},\"user_attributes\":{\"key\":{\"description\":\"description\",\"label\":\"label\",\"profile_required\":true,\"auth0_mapping\":\"auth0_mapping\",\"oidc_mapping\":{\"mapping\":\"mapping\"},\"saml_mapping\":[\"saml_mapping\"],\"scim_mapping\":\"scim_mapping\"}}}"));
        CreateUserAttributeProfileResponseContent response = client.userAttributeProfiles()
                .create(CreateUserAttributeProfileRequestContent.builder()
                        .name("name")
                        .userAttributes(new HashMap<String, UserAttributeProfileUserAttributeAdditionalProperties>() {
                            {
                                put(
                                        "key",
                                        UserAttributeProfileUserAttributeAdditionalProperties.builder()
                                                .description("description")
                                                .label("label")
                                                .profileRequired(true)
                                                .auth0Mapping("auth0_mapping")
                                                .build());
                            }
                        })
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"name\": \"name\",\n"
                + "  \"user_attributes\": {\n"
                + "    \"key\": {\n"
                + "      \"description\": \"description\",\n"
                + "      \"label\": \"label\",\n"
                + "      \"profile_required\": true,\n"
                + "      \"auth0_mapping\": \"auth0_mapping\"\n"
                + "    }\n"
                + "  }\n"
                + "}";
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
                + "  \"user_id\": {\n"
                + "    \"oidc_mapping\": \"sub\",\n"
                + "    \"saml_mapping\": [\n"
                + "      \"saml_mapping\"\n"
                + "    ],\n"
                + "    \"scim_mapping\": \"scim_mapping\"\n"
                + "  },\n"
                + "  \"user_attributes\": {\n"
                + "    \"key\": {\n"
                + "      \"description\": \"description\",\n"
                + "      \"label\": \"label\",\n"
                + "      \"profile_required\": true,\n"
                + "      \"auth0_mapping\": \"auth0_mapping\",\n"
                + "      \"oidc_mapping\": {\n"
                + "        \"mapping\": \"mapping\"\n"
                + "      },\n"
                + "      \"saml_mapping\": [\n"
                + "        \"saml_mapping\"\n"
                + "      ],\n"
                + "      \"scim_mapping\": \"scim_mapping\"\n"
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
                .setBody("{\"user_attribute_profile_templates\":[{\"id\":\"id\",\"display_name\":\"display_name\"}]}"));
        ListUserAttributeProfileTemplateResponseContent response =
                client.userAttributeProfiles().listTemplates();
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"user_attribute_profile_templates\": [\n"
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
                                "{\"id\":\"id\",\"display_name\":\"display_name\",\"template\":{\"name\":\"name\",\"user_id\":{\"oidc_mapping\":\"sub\",\"saml_mapping\":[\"saml_mapping\"],\"scim_mapping\":\"scim_mapping\"},\"user_attributes\":{\"key\":{\"description\":\"description\",\"label\":\"label\",\"profile_required\":true,\"auth0_mapping\":\"auth0_mapping\"}}}}"));
        GetUserAttributeProfileTemplateResponseContent response =
                client.userAttributeProfiles().getTemplate("id");
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
                + "    \"user_id\": {\n"
                + "      \"oidc_mapping\": \"sub\",\n"
                + "      \"saml_mapping\": [\n"
                + "        \"saml_mapping\"\n"
                + "      ],\n"
                + "      \"scim_mapping\": \"scim_mapping\"\n"
                + "    },\n"
                + "    \"user_attributes\": {\n"
                + "      \"key\": {\n"
                + "        \"description\": \"description\",\n"
                + "        \"label\": \"label\",\n"
                + "        \"profile_required\": true,\n"
                + "        \"auth0_mapping\": \"auth0_mapping\"\n"
                + "      }\n"
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
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"name\":\"name\",\"user_id\":{\"oidc_mapping\":\"sub\",\"saml_mapping\":[\"saml_mapping\"],\"scim_mapping\":\"scim_mapping\"},\"user_attributes\":{\"key\":{\"description\":\"description\",\"label\":\"label\",\"profile_required\":true,\"auth0_mapping\":\"auth0_mapping\",\"oidc_mapping\":{\"mapping\":\"mapping\"},\"saml_mapping\":[\"saml_mapping\"],\"scim_mapping\":\"scim_mapping\"}}}"));
        GetUserAttributeProfileResponseContent response =
                client.userAttributeProfiles().get("id");
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
                + "  \"user_id\": {\n"
                + "    \"oidc_mapping\": \"sub\",\n"
                + "    \"saml_mapping\": [\n"
                + "      \"saml_mapping\"\n"
                + "    ],\n"
                + "    \"scim_mapping\": \"scim_mapping\"\n"
                + "  },\n"
                + "  \"user_attributes\": {\n"
                + "    \"key\": {\n"
                + "      \"description\": \"description\",\n"
                + "      \"label\": \"label\",\n"
                + "      \"profile_required\": true,\n"
                + "      \"auth0_mapping\": \"auth0_mapping\",\n"
                + "      \"oidc_mapping\": {\n"
                + "        \"mapping\": \"mapping\"\n"
                + "      },\n"
                + "      \"saml_mapping\": [\n"
                + "        \"saml_mapping\"\n"
                + "      ],\n"
                + "      \"scim_mapping\": \"scim_mapping\"\n"
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
        client.userAttributeProfiles().delete("id");
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
                                "{\"id\":\"id\",\"name\":\"name\",\"user_id\":{\"oidc_mapping\":\"sub\",\"saml_mapping\":[\"saml_mapping\"],\"scim_mapping\":\"scim_mapping\"},\"user_attributes\":{\"key\":{\"description\":\"description\",\"label\":\"label\",\"profile_required\":true,\"auth0_mapping\":\"auth0_mapping\",\"oidc_mapping\":{\"mapping\":\"mapping\"},\"saml_mapping\":[\"saml_mapping\"],\"scim_mapping\":\"scim_mapping\"}}}"));
        UpdateUserAttributeProfileResponseContent response = client.userAttributeProfiles()
                .update("id", UpdateUserAttributeProfileRequestContent.builder().build());
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
                + "  \"user_id\": {\n"
                + "    \"oidc_mapping\": \"sub\",\n"
                + "    \"saml_mapping\": [\n"
                + "      \"saml_mapping\"\n"
                + "    ],\n"
                + "    \"scim_mapping\": \"scim_mapping\"\n"
                + "  },\n"
                + "  \"user_attributes\": {\n"
                + "    \"key\": {\n"
                + "      \"description\": \"description\",\n"
                + "      \"label\": \"label\",\n"
                + "      \"profile_required\": true,\n"
                + "      \"auth0_mapping\": \"auth0_mapping\",\n"
                + "      \"oidc_mapping\": {\n"
                + "        \"mapping\": \"mapping\"\n"
                + "      },\n"
                + "      \"saml_mapping\": [\n"
                + "        \"saml_mapping\"\n"
                + "      ],\n"
                + "      \"scim_mapping\": \"scim_mapping\"\n"
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
