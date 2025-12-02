package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.Action;
import com.auth0.client.mgmt.types.ActionTrigger;
import com.auth0.client.mgmt.types.CreateActionRequestContent;
import com.auth0.client.mgmt.types.CreateActionResponseContent;
import com.auth0.client.mgmt.types.DeleteActionRequestParameters;
import com.auth0.client.mgmt.types.DeployActionResponseContent;
import com.auth0.client.mgmt.types.GetActionResponseContent;
import com.auth0.client.mgmt.types.ListActionsRequestParameters;
import com.auth0.client.mgmt.types.TestActionRequestContent;
import com.auth0.client.mgmt.types.TestActionResponseContent;
import com.auth0.client.mgmt.types.UpdateActionRequestContent;
import com.auth0.client.mgmt.types.UpdateActionResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ActionsWireTest {
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
                                "{\"total\":1.1,\"page\":1.1,\"per_page\":1.1,\"actions\":[{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"code\":\"code\",\"dependencies\":[{}],\"runtime\":\"runtime\",\"secrets\":[{}],\"installed_integration_id\":\"installed_integration_id\",\"status\":\"pending\",\"built_at\":\"2024-01-15T09:30:00Z\",\"deploy\":true}]}"));
        SyncPagingIterable<Action> response = client.actions()
                .list(ListActionsRequestParameters.builder()
                        .triggerId("triggerId")
                        .actionName("actionName")
                        .deployed(true)
                        .page(1)
                        .perPage(1)
                        .installed(true)
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
                                "{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\",\"version\":\"version\",\"status\":\"status\",\"runtimes\":[\"runtimes\"],\"default_runtime\":\"default_runtime\",\"compatible_triggers\":[{\"id\":\"id\",\"version\":\"version\"}],\"binding_policy\":\"trigger-bound\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\",\"registry_url\":\"registry_url\"}],\"runtime\":\"runtime\",\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"deployed_version\":{\"id\":\"id\",\"action_id\":\"action_id\",\"code\":\"code\",\"dependencies\":[{}],\"deployed\":true,\"runtime\":\"runtime\",\"secrets\":[{}],\"status\":\"pending\",\"number\":1.1,\"errors\":[{}],\"action\":{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"built_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"supported_triggers\":[{\"id\":\"id\"}]},\"installed_integration_id\":\"installed_integration_id\",\"integration\":{\"id\":\"id\",\"catalog_id\":\"catalog_id\",\"url_slug\":\"url_slug\",\"partner_id\":\"partner_id\",\"name\":\"name\",\"description\":\"description\",\"short_description\":\"short_description\",\"logo\":\"logo\",\"feature_type\":\"unspecified\",\"terms_of_use_url\":\"terms_of_use_url\",\"privacy_policy_url\":\"privacy_policy_url\",\"public_support_link\":\"public_support_link\",\"current_release\":{\"id\":\"id\",\"trigger\":{\"id\":\"id\"},\"required_secrets\":[{}],\"required_configuration\":[{}]},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"status\":\"pending\",\"built_at\":\"2024-01-15T09:30:00Z\",\"deploy\":true}"));
        CreateActionResponseContent response = client.actions()
                .create(CreateActionRequestContent.builder()
                        .name("name")
                        .supportedTriggers(
                                Arrays.asList(ActionTrigger.builder().id("id").build()))
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"name\": \"name\",\n"
                + "  \"supported_triggers\": [\n"
                + "    {\n"
                + "      \"id\": \"id\"\n"
                + "    }\n"
                + "  ]\n"
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
                + "  \"supported_triggers\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"runtimes\": [\n"
                + "        \"runtimes\"\n"
                + "      ],\n"
                + "      \"default_runtime\": \"default_runtime\",\n"
                + "      \"compatible_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"version\": \"version\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"binding_policy\": \"trigger-bound\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"all_changes_deployed\": true,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"registry_url\": \"registry_url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"runtime\": \"runtime\",\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"deployed_version\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"action_id\": \"action_id\",\n"
                + "    \"code\": \"code\",\n"
                + "    \"dependencies\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"deployed\": true,\n"
                + "    \"runtime\": \"runtime\",\n"
                + "    \"secrets\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"status\": \"pending\",\n"
                + "    \"number\": 1.1,\n"
                + "    \"errors\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"action\": {\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"supported_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"all_changes_deployed\": true,\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"supported_triggers\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"installed_integration_id\": \"installed_integration_id\",\n"
                + "  \"integration\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"catalog_id\": \"catalog_id\",\n"
                + "    \"url_slug\": \"url_slug\",\n"
                + "    \"partner_id\": \"partner_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"short_description\": \"short_description\",\n"
                + "    \"logo\": \"logo\",\n"
                + "    \"feature_type\": \"unspecified\",\n"
                + "    \"terms_of_use_url\": \"terms_of_use_url\",\n"
                + "    \"privacy_policy_url\": \"privacy_policy_url\",\n"
                + "    \"public_support_link\": \"public_support_link\",\n"
                + "    \"current_release\": {\n"
                + "      \"id\": \"id\",\n"
                + "      \"trigger\": {\n"
                + "        \"id\": \"id\"\n"
                + "      },\n"
                + "      \"required_secrets\": [\n"
                + "        {}\n"
                + "      ],\n"
                + "      \"required_configuration\": [\n"
                + "        {}\n"
                + "      ]\n"
                + "    },\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  },\n"
                + "  \"status\": \"pending\",\n"
                + "  \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"deploy\": true\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\",\"version\":\"version\",\"status\":\"status\",\"runtimes\":[\"runtimes\"],\"default_runtime\":\"default_runtime\",\"compatible_triggers\":[{\"id\":\"id\",\"version\":\"version\"}],\"binding_policy\":\"trigger-bound\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\",\"registry_url\":\"registry_url\"}],\"runtime\":\"runtime\",\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"deployed_version\":{\"id\":\"id\",\"action_id\":\"action_id\",\"code\":\"code\",\"dependencies\":[{}],\"deployed\":true,\"runtime\":\"runtime\",\"secrets\":[{}],\"status\":\"pending\",\"number\":1.1,\"errors\":[{}],\"action\":{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"built_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"supported_triggers\":[{\"id\":\"id\"}]},\"installed_integration_id\":\"installed_integration_id\",\"integration\":{\"id\":\"id\",\"catalog_id\":\"catalog_id\",\"url_slug\":\"url_slug\",\"partner_id\":\"partner_id\",\"name\":\"name\",\"description\":\"description\",\"short_description\":\"short_description\",\"logo\":\"logo\",\"feature_type\":\"unspecified\",\"terms_of_use_url\":\"terms_of_use_url\",\"privacy_policy_url\":\"privacy_policy_url\",\"public_support_link\":\"public_support_link\",\"current_release\":{\"id\":\"id\",\"trigger\":{\"id\":\"id\"},\"required_secrets\":[{}],\"required_configuration\":[{}]},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"status\":\"pending\",\"built_at\":\"2024-01-15T09:30:00Z\",\"deploy\":true}"));
        GetActionResponseContent response = client.actions().get("id");
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
                + "  \"supported_triggers\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"runtimes\": [\n"
                + "        \"runtimes\"\n"
                + "      ],\n"
                + "      \"default_runtime\": \"default_runtime\",\n"
                + "      \"compatible_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"version\": \"version\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"binding_policy\": \"trigger-bound\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"all_changes_deployed\": true,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"registry_url\": \"registry_url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"runtime\": \"runtime\",\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"deployed_version\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"action_id\": \"action_id\",\n"
                + "    \"code\": \"code\",\n"
                + "    \"dependencies\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"deployed\": true,\n"
                + "    \"runtime\": \"runtime\",\n"
                + "    \"secrets\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"status\": \"pending\",\n"
                + "    \"number\": 1.1,\n"
                + "    \"errors\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"action\": {\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"supported_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"all_changes_deployed\": true,\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"supported_triggers\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"installed_integration_id\": \"installed_integration_id\",\n"
                + "  \"integration\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"catalog_id\": \"catalog_id\",\n"
                + "    \"url_slug\": \"url_slug\",\n"
                + "    \"partner_id\": \"partner_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"short_description\": \"short_description\",\n"
                + "    \"logo\": \"logo\",\n"
                + "    \"feature_type\": \"unspecified\",\n"
                + "    \"terms_of_use_url\": \"terms_of_use_url\",\n"
                + "    \"privacy_policy_url\": \"privacy_policy_url\",\n"
                + "    \"public_support_link\": \"public_support_link\",\n"
                + "    \"current_release\": {\n"
                + "      \"id\": \"id\",\n"
                + "      \"trigger\": {\n"
                + "        \"id\": \"id\"\n"
                + "      },\n"
                + "      \"required_secrets\": [\n"
                + "        {}\n"
                + "      ],\n"
                + "      \"required_configuration\": [\n"
                + "        {}\n"
                + "      ]\n"
                + "    },\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  },\n"
                + "  \"status\": \"pending\",\n"
                + "  \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"deploy\": true\n"
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
        client.actions()
                .delete(
                        "id",
                        DeleteActionRequestParameters.builder().force(true).build());
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
                                "{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\",\"version\":\"version\",\"status\":\"status\",\"runtimes\":[\"runtimes\"],\"default_runtime\":\"default_runtime\",\"compatible_triggers\":[{\"id\":\"id\",\"version\":\"version\"}],\"binding_policy\":\"trigger-bound\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\",\"registry_url\":\"registry_url\"}],\"runtime\":\"runtime\",\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"deployed_version\":{\"id\":\"id\",\"action_id\":\"action_id\",\"code\":\"code\",\"dependencies\":[{}],\"deployed\":true,\"runtime\":\"runtime\",\"secrets\":[{}],\"status\":\"pending\",\"number\":1.1,\"errors\":[{}],\"action\":{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"built_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"supported_triggers\":[{\"id\":\"id\"}]},\"installed_integration_id\":\"installed_integration_id\",\"integration\":{\"id\":\"id\",\"catalog_id\":\"catalog_id\",\"url_slug\":\"url_slug\",\"partner_id\":\"partner_id\",\"name\":\"name\",\"description\":\"description\",\"short_description\":\"short_description\",\"logo\":\"logo\",\"feature_type\":\"unspecified\",\"terms_of_use_url\":\"terms_of_use_url\",\"privacy_policy_url\":\"privacy_policy_url\",\"public_support_link\":\"public_support_link\",\"current_release\":{\"id\":\"id\",\"trigger\":{\"id\":\"id\"},\"required_secrets\":[{}],\"required_configuration\":[{}]},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"status\":\"pending\",\"built_at\":\"2024-01-15T09:30:00Z\",\"deploy\":true}"));
        UpdateActionResponseContent response = client.actions()
                .update("id", UpdateActionRequestContent.builder().build());
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
                + "  \"supported_triggers\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"runtimes\": [\n"
                + "        \"runtimes\"\n"
                + "      ],\n"
                + "      \"default_runtime\": \"default_runtime\",\n"
                + "      \"compatible_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"version\": \"version\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"binding_policy\": \"trigger-bound\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"all_changes_deployed\": true,\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"registry_url\": \"registry_url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"runtime\": \"runtime\",\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"deployed_version\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"action_id\": \"action_id\",\n"
                + "    \"code\": \"code\",\n"
                + "    \"dependencies\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"deployed\": true,\n"
                + "    \"runtime\": \"runtime\",\n"
                + "    \"secrets\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"status\": \"pending\",\n"
                + "    \"number\": 1.1,\n"
                + "    \"errors\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"action\": {\n"
                + "      \"id\": \"id\",\n"
                + "      \"name\": \"name\",\n"
                + "      \"supported_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"all_changes_deployed\": true,\n"
                + "      \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    },\n"
                + "    \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"supported_triggers\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ]\n"
                + "  },\n"
                + "  \"installed_integration_id\": \"installed_integration_id\",\n"
                + "  \"integration\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"catalog_id\": \"catalog_id\",\n"
                + "    \"url_slug\": \"url_slug\",\n"
                + "    \"partner_id\": \"partner_id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"description\": \"description\",\n"
                + "    \"short_description\": \"short_description\",\n"
                + "    \"logo\": \"logo\",\n"
                + "    \"feature_type\": \"unspecified\",\n"
                + "    \"terms_of_use_url\": \"terms_of_use_url\",\n"
                + "    \"privacy_policy_url\": \"privacy_policy_url\",\n"
                + "    \"public_support_link\": \"public_support_link\",\n"
                + "    \"current_release\": {\n"
                + "      \"id\": \"id\",\n"
                + "      \"trigger\": {\n"
                + "        \"id\": \"id\"\n"
                + "      },\n"
                + "      \"required_secrets\": [\n"
                + "        {}\n"
                + "      ],\n"
                + "      \"required_configuration\": [\n"
                + "        {}\n"
                + "      ]\n"
                + "    },\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  },\n"
                + "  \"status\": \"pending\",\n"
                + "  \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"deploy\": true\n"
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
    public void testDeploy() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"action_id\":\"action_id\",\"code\":\"code\",\"dependencies\":[{\"name\":\"name\",\"version\":\"version\",\"registry_url\":\"registry_url\"}],\"deployed\":true,\"runtime\":\"runtime\",\"secrets\":[{\"name\":\"name\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"status\":\"pending\",\"number\":1.1,\"errors\":[{\"id\":\"id\",\"msg\":\"msg\",\"url\":\"url\"}],\"action\":{\"id\":\"id\",\"name\":\"name\",\"supported_triggers\":[{\"id\":\"id\"}],\"all_changes_deployed\":true,\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"},\"built_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"supported_triggers\":[{\"id\":\"id\",\"version\":\"version\",\"status\":\"status\",\"runtimes\":[\"runtimes\"],\"default_runtime\":\"default_runtime\",\"compatible_triggers\":[{\"id\":\"id\",\"version\":\"version\"}],\"binding_policy\":\"trigger-bound\"}]}"));
        DeployActionResponseContent response = client.actions().deploy("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"action_id\": \"action_id\",\n"
                + "  \"code\": \"code\",\n"
                + "  \"dependencies\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"registry_url\": \"registry_url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"deployed\": true,\n"
                + "  \"runtime\": \"runtime\",\n"
                + "  \"secrets\": [\n"
                + "    {\n"
                + "      \"name\": \"name\",\n"
                + "      \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"status\": \"pending\",\n"
                + "  \"number\": 1.1,\n"
                + "  \"errors\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"msg\": \"msg\",\n"
                + "      \"url\": \"url\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"action\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"supported_triggers\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"all_changes_deployed\": true,\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
                + "  },\n"
                + "  \"built_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"supported_triggers\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"version\": \"version\",\n"
                + "      \"status\": \"status\",\n"
                + "      \"runtimes\": [\n"
                + "        \"runtimes\"\n"
                + "      ],\n"
                + "      \"default_runtime\": \"default_runtime\",\n"
                + "      \"compatible_triggers\": [\n"
                + "        {\n"
                + "          \"id\": \"id\",\n"
                + "          \"version\": \"version\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"binding_policy\": \"trigger-bound\"\n"
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
    public void testTest() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"payload\":{\"key\":\"value\"}}"));
        TestActionResponseContent response = client.actions()
                .test(
                        "id",
                        TestActionRequestContent.builder()
                                .payload(new HashMap<String, Object>() {
                                    {
                                        put("key", "value");
                                    }
                                })
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"payload\": {\n" + "    \"key\": \"value\"\n" + "  }\n" + "}";
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
        String expectedResponseBody = "" + "{\n" + "  \"payload\": {\n" + "    \"key\": \"value\"\n" + "  }\n" + "}";
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
     * Compares two JsonNodes with numeric equivalence.
     */
    private boolean jsonEquals(JsonNode a, JsonNode b) {
        if (a.equals(b)) return true;
        if (a.isNumber() && b.isNumber()) return Math.abs(a.doubleValue() - b.doubleValue()) < 1e-10;
        if (a.isObject() && b.isObject()) {
            if (a.size() != b.size()) return false;
            java.util.Iterator<java.util.Map.Entry<String, JsonNode>> iter = a.fields();
            while (iter.hasNext()) {
                java.util.Map.Entry<String, JsonNode> entry = iter.next();
                if (!jsonEquals(entry.getValue(), b.get(entry.getKey()))) return false;
            }
            return true;
        }
        if (a.isArray() && b.isArray()) {
            if (a.size() != b.size()) return false;
            for (int i = 0; i < a.size(); i++) {
                if (!jsonEquals(a.get(i), b.get(i))) return false;
            }
            return true;
        }
        return false;
    }
}
