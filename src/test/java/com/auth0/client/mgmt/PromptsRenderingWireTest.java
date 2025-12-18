package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.prompts.types.BulkUpdateAculRequestContent;
import com.auth0.client.mgmt.prompts.types.ListAculsRequestParameters;
import com.auth0.client.mgmt.prompts.types.UpdateAculRequestContent;
import com.auth0.client.mgmt.types.AculConfigsItem;
import com.auth0.client.mgmt.types.AculRenderingModeEnum;
import com.auth0.client.mgmt.types.AculResponseContent;
import com.auth0.client.mgmt.types.BulkUpdateAculResponseContent;
import com.auth0.client.mgmt.types.GetAculResponseContent;
import com.auth0.client.mgmt.types.PromptGroupNameEnum;
import com.auth0.client.mgmt.types.ScreenGroupNameEnum;
import com.auth0.client.mgmt.types.UpdateAculResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PromptsRenderingWireTest {
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
                                "{\"configs\":[{\"rendering_mode\":\"advanced\",\"context_configuration\":[\"context_configuration\"],\"default_head_tags_disabled\":true,\"use_page_template\":true,\"head_tags\":[{}]}],\"start\":1.1,\"limit\":1.1,\"total\":1.1}"));
        SyncPagingIterable<AculResponseContent> response = client.prompts()
                .rendering()
                .list(ListAculsRequestParameters.builder()
                        .fields(OptionalNullable.of("fields"))
                        .includeFields(OptionalNullable.of(true))
                        .page(OptionalNullable.of(1))
                        .perPage(OptionalNullable.of(1))
                        .includeTotals(OptionalNullable.of(true))
                        .prompt(OptionalNullable.of("prompt"))
                        .screen(OptionalNullable.of("screen"))
                        .renderingMode(OptionalNullable.of(AculRenderingModeEnum.ADVANCED))
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
    public void testBulkUpdate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"configs\":[{\"prompt\":\"login\",\"screen\":\"login\",\"rendering_mode\":\"advanced\",\"context_configuration\":[\"branding.settings\"],\"default_head_tags_disabled\":true,\"use_page_template\":true,\"head_tags\":[{}]}]}"));
        BulkUpdateAculResponseContent response = client.prompts()
                .rendering()
                .bulkUpdate(BulkUpdateAculRequestContent.builder()
                        .configs(Arrays.asList(AculConfigsItem.builder()
                                .prompt(PromptGroupNameEnum.LOGIN)
                                .screen(ScreenGroupNameEnum.LOGIN)
                                .build()))
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PATCH", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"configs\": [\n"
                + "    {\n"
                + "      \"prompt\": \"login\",\n"
                + "      \"screen\": \"login\"\n"
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
                + "  \"configs\": [\n"
                + "    {\n"
                + "      \"prompt\": \"login\",\n"
                + "      \"screen\": \"login\",\n"
                + "      \"rendering_mode\": \"advanced\",\n"
                + "      \"context_configuration\": [\n"
                + "        \"branding.settings\"\n"
                + "      ],\n"
                + "      \"default_head_tags_disabled\": true,\n"
                + "      \"use_page_template\": true,\n"
                + "      \"head_tags\": [\n"
                + "        {}\n"
                + "      ]\n"
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
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"tenant\":\"tenant\",\"prompt\":\"prompt\",\"screen\":\"screen\",\"rendering_mode\":\"advanced\",\"context_configuration\":[\"context_configuration\"],\"default_head_tags_disabled\":true,\"use_page_template\":true,\"head_tags\":[{\"tag\":\"tag\",\"attributes\":{\"key\":\"value\"},\"content\":\"content\"}],\"filters\":{\"match_type\":\"includes_any\",\"clients\":[{\"id\":\"id\"}],\"organizations\":[{\"id\":\"id\"}],\"domains\":[{\"id\":\"id\"}]}}"));
        GetAculResponseContent response =
                client.prompts().rendering().get(PromptGroupNameEnum.LOGIN, ScreenGroupNameEnum.LOGIN);
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"tenant\": \"tenant\",\n"
                + "  \"prompt\": \"prompt\",\n"
                + "  \"screen\": \"screen\",\n"
                + "  \"rendering_mode\": \"advanced\",\n"
                + "  \"context_configuration\": [\n"
                + "    \"context_configuration\"\n"
                + "  ],\n"
                + "  \"default_head_tags_disabled\": true,\n"
                + "  \"use_page_template\": true,\n"
                + "  \"head_tags\": [\n"
                + "    {\n"
                + "      \"tag\": \"tag\",\n"
                + "      \"attributes\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"content\": \"content\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"filters\": {\n"
                + "    \"match_type\": \"includes_any\",\n"
                + "    \"clients\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"organizations\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"domains\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
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
    public void testUpdate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"rendering_mode\":\"advanced\",\"context_configuration\":[\"context_configuration\"],\"default_head_tags_disabled\":true,\"use_page_template\":true,\"head_tags\":[{\"tag\":\"tag\",\"attributes\":{\"key\":\"value\"},\"content\":\"content\"}],\"filters\":{\"match_type\":\"includes_any\",\"clients\":[{\"id\":\"id\"}],\"organizations\":[{\"id\":\"id\"}],\"domains\":[{\"id\":\"id\"}]}}"));
        UpdateAculResponseContent response = client.prompts()
                .rendering()
                .update(
                        PromptGroupNameEnum.LOGIN,
                        ScreenGroupNameEnum.LOGIN,
                        UpdateAculRequestContent.builder().build());
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
                + "  \"rendering_mode\": \"advanced\",\n"
                + "  \"context_configuration\": [\n"
                + "    \"context_configuration\"\n"
                + "  ],\n"
                + "  \"default_head_tags_disabled\": true,\n"
                + "  \"use_page_template\": true,\n"
                + "  \"head_tags\": [\n"
                + "    {\n"
                + "      \"tag\": \"tag\",\n"
                + "      \"attributes\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"content\": \"content\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"filters\": {\n"
                + "    \"match_type\": \"includes_any\",\n"
                + "    \"clients\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"organizations\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"domains\": [\n"
                + "      {\n"
                + "        \"id\": \"id\"\n"
                + "      }\n"
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
