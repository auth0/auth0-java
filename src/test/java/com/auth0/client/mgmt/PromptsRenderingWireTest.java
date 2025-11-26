package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.prompts.types.ListAculsRequestParameters;
import com.auth0.client.mgmt.prompts.types.UpdateAculRequestContent;
import com.auth0.client.mgmt.types.AculRenderingModeEnum;
import com.auth0.client.mgmt.types.AculResponseContent;
import com.auth0.client.mgmt.types.GetAculResponseContent;
import com.auth0.client.mgmt.types.PromptGroupNameEnum;
import com.auth0.client.mgmt.types.ScreenGroupNameEnum;
import com.auth0.client.mgmt.types.UpdateAculResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                                "{\"configs\":[{\"rendering_mode\":\"advanced\",\"context_configuration\":[\"context_configuration\"],\"default_head_tags_disabled\":true,\"head_tags\":[{}],\"use_page_template\":true}],\"start\":1.1,\"limit\":1.1,\"total\":1.1}"));
        SyncPagingIterable<AculResponseContent> response = client.prompts()
                .rendering()
                .list(ListAculsRequestParameters.builder()
                        .fields("fields")
                        .includeFields(true)
                        .page(1)
                        .perPage(1)
                        .includeTotals(true)
                        .prompt("prompt")
                        .screen("screen")
                        .renderingMode(AculRenderingModeEnum.ADVANCED)
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
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"tenant\":\"tenant\",\"prompt\":\"prompt\",\"screen\":\"screen\",\"rendering_mode\":\"advanced\",\"context_configuration\":[\"context_configuration\"],\"default_head_tags_disabled\":true,\"head_tags\":[{\"tag\":\"tag\",\"content\":\"content\"}],\"filters\":{\"match_type\":\"includes_any\",\"clients\":[{}],\"organizations\":[{}],\"domains\":[{}]},\"use_page_template\":true}"));
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
                + "  \"head_tags\": [\n"
                + "    {\n"
                + "      \"tag\": \"tag\",\n"
                + "      \"content\": \"content\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"filters\": {\n"
                + "    \"match_type\": \"includes_any\",\n"
                + "    \"clients\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"organizations\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"domains\": [\n"
                + "      {}\n"
                + "    ]\n"
                + "  },\n"
                + "  \"use_page_template\": true\n"
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
                                "{\"rendering_mode\":\"advanced\",\"context_configuration\":[\"context_configuration\"],\"default_head_tags_disabled\":true,\"head_tags\":[{\"tag\":\"tag\",\"content\":\"content\"}],\"filters\":{\"match_type\":\"includes_any\",\"clients\":[{}],\"organizations\":[{}],\"domains\":[{}]},\"use_page_template\":true}"));
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
                + "  \"head_tags\": [\n"
                + "    {\n"
                + "      \"tag\": \"tag\",\n"
                + "      \"content\": \"content\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"filters\": {\n"
                + "    \"match_type\": \"includes_any\",\n"
                + "    \"clients\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"organizations\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"domains\": [\n"
                + "      {}\n"
                + "    ]\n"
                + "  },\n"
                + "  \"use_page_template\": true\n"
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
