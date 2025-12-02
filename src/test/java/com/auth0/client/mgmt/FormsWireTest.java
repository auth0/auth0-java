package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.CreateFormRequestContent;
import com.auth0.client.mgmt.types.CreateFormResponseContent;
import com.auth0.client.mgmt.types.FormSummary;
import com.auth0.client.mgmt.types.GetFormRequestParameters;
import com.auth0.client.mgmt.types.GetFormResponseContent;
import com.auth0.client.mgmt.types.ListFormsRequestParameters;
import com.auth0.client.mgmt.types.UpdateFormRequestContent;
import com.auth0.client.mgmt.types.UpdateFormResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FormsWireTest {
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
                                "{\"start\":1.1,\"limit\":1.1,\"total\":1.1,\"forms\":[{\"id\":\"id\",\"name\":\"name\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"embedded_at\":\"embedded_at\",\"submitted_at\":\"submitted_at\"}]}"));
        SyncPagingIterable<FormSummary> response = client.forms()
                .list(ListFormsRequestParameters.builder()
                        .page(1)
                        .perPage(1)
                        .includeTotals(true)
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
                                "{\"id\":\"id\",\"name\":\"name\",\"messages\":{\"errors\":{\"key\":\"value\"},\"custom\":{\"key\":\"value\"}},\"languages\":{\"primary\":\"primary\",\"default\":\"default\"},\"translations\":{\"key\":\"value\"},\"nodes\":[{\"id\":\"id\",\"type\":\"FLOW\",\"coordinates\":{\"x\":1,\"y\":1},\"alias\":\"alias\",\"config\":{\"flow_id\":\"flow_id\"}}],\"start\":{\"hidden_fields\":[{\"key\":\"key\"}],\"next_node\":\"next_node\",\"coordinates\":{\"x\":1,\"y\":1}},\"ending\":{\"redirection\":{\"delay\":1,\"target\":\"target\"},\"after_submit\":{\"flow_id\":\"flow_id\"},\"coordinates\":{\"x\":1,\"y\":1},\"resume_flow\":true},\"style\":{\"css\":\"css\"},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"embedded_at\":\"embedded_at\",\"submitted_at\":\"submitted_at\"}"));
        CreateFormResponseContent response = client.forms()
                .create(CreateFormRequestContent.builder().name("name").build());
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
                + "  \"messages\": {\n"
                + "    \"errors\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"custom\": {\n"
                + "      \"key\": \"value\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"languages\": {\n"
                + "    \"primary\": \"primary\",\n"
                + "    \"default\": \"default\"\n"
                + "  },\n"
                + "  \"translations\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"nodes\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"type\": \"FLOW\",\n"
                + "      \"coordinates\": {\n"
                + "        \"x\": 1,\n"
                + "        \"y\": 1\n"
                + "      },\n"
                + "      \"alias\": \"alias\",\n"
                + "      \"config\": {\n"
                + "        \"flow_id\": \"flow_id\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"start\": {\n"
                + "    \"hidden_fields\": [\n"
                + "      {\n"
                + "        \"key\": \"key\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"next_node\": \"next_node\",\n"
                + "    \"coordinates\": {\n"
                + "      \"x\": 1,\n"
                + "      \"y\": 1\n"
                + "    }\n"
                + "  },\n"
                + "  \"ending\": {\n"
                + "    \"redirection\": {\n"
                + "      \"delay\": 1,\n"
                + "      \"target\": \"target\"\n"
                + "    },\n"
                + "    \"after_submit\": {\n"
                + "      \"flow_id\": \"flow_id\"\n"
                + "    },\n"
                + "    \"coordinates\": {\n"
                + "      \"x\": 1,\n"
                + "      \"y\": 1\n"
                + "    },\n"
                + "    \"resume_flow\": true\n"
                + "  },\n"
                + "  \"style\": {\n"
                + "    \"css\": \"css\"\n"
                + "  },\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"embedded_at\": \"embedded_at\",\n"
                + "  \"submitted_at\": \"submitted_at\"\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"messages\":{\"errors\":{\"key\":\"value\"},\"custom\":{\"key\":\"value\"}},\"languages\":{\"primary\":\"primary\",\"default\":\"default\"},\"translations\":{\"key\":\"value\"},\"nodes\":[{\"id\":\"id\",\"type\":\"FLOW\",\"coordinates\":{\"x\":1,\"y\":1},\"alias\":\"alias\",\"config\":{\"flow_id\":\"flow_id\"}}],\"start\":{\"hidden_fields\":[{\"key\":\"key\"}],\"next_node\":\"next_node\",\"coordinates\":{\"x\":1,\"y\":1}},\"ending\":{\"redirection\":{\"delay\":1,\"target\":\"target\"},\"after_submit\":{\"flow_id\":\"flow_id\"},\"coordinates\":{\"x\":1,\"y\":1},\"resume_flow\":true},\"style\":{\"css\":\"css\"},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"embedded_at\":\"embedded_at\",\"submitted_at\":\"submitted_at\"}"));
        GetFormResponseContent response =
                client.forms().get("id", GetFormRequestParameters.builder().build());
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
                + "  \"messages\": {\n"
                + "    \"errors\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"custom\": {\n"
                + "      \"key\": \"value\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"languages\": {\n"
                + "    \"primary\": \"primary\",\n"
                + "    \"default\": \"default\"\n"
                + "  },\n"
                + "  \"translations\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"nodes\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"type\": \"FLOW\",\n"
                + "      \"coordinates\": {\n"
                + "        \"x\": 1,\n"
                + "        \"y\": 1\n"
                + "      },\n"
                + "      \"alias\": \"alias\",\n"
                + "      \"config\": {\n"
                + "        \"flow_id\": \"flow_id\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"start\": {\n"
                + "    \"hidden_fields\": [\n"
                + "      {\n"
                + "        \"key\": \"key\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"next_node\": \"next_node\",\n"
                + "    \"coordinates\": {\n"
                + "      \"x\": 1,\n"
                + "      \"y\": 1\n"
                + "    }\n"
                + "  },\n"
                + "  \"ending\": {\n"
                + "    \"redirection\": {\n"
                + "      \"delay\": 1,\n"
                + "      \"target\": \"target\"\n"
                + "    },\n"
                + "    \"after_submit\": {\n"
                + "      \"flow_id\": \"flow_id\"\n"
                + "    },\n"
                + "    \"coordinates\": {\n"
                + "      \"x\": 1,\n"
                + "      \"y\": 1\n"
                + "    },\n"
                + "    \"resume_flow\": true\n"
                + "  },\n"
                + "  \"style\": {\n"
                + "    \"css\": \"css\"\n"
                + "  },\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"embedded_at\": \"embedded_at\",\n"
                + "  \"submitted_at\": \"submitted_at\"\n"
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
        client.forms().delete("id");
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
                                "{\"id\":\"id\",\"name\":\"name\",\"messages\":{\"errors\":{\"key\":\"value\"},\"custom\":{\"key\":\"value\"}},\"languages\":{\"primary\":\"primary\",\"default\":\"default\"},\"translations\":{\"key\":\"value\"},\"nodes\":[{\"id\":\"id\",\"type\":\"FLOW\",\"coordinates\":{\"x\":1,\"y\":1},\"alias\":\"alias\",\"config\":{\"flow_id\":\"flow_id\"}}],\"start\":{\"hidden_fields\":[{\"key\":\"key\"}],\"next_node\":\"next_node\",\"coordinates\":{\"x\":1,\"y\":1}},\"ending\":{\"redirection\":{\"delay\":1,\"target\":\"target\"},\"after_submit\":{\"flow_id\":\"flow_id\"},\"coordinates\":{\"x\":1,\"y\":1},\"resume_flow\":true},\"style\":{\"css\":\"css\"},\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\",\"embedded_at\":\"embedded_at\",\"submitted_at\":\"submitted_at\"}"));
        UpdateFormResponseContent response =
                client.forms().update("id", UpdateFormRequestContent.builder().build());
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
                + "  \"messages\": {\n"
                + "    \"errors\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"custom\": {\n"
                + "      \"key\": \"value\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"languages\": {\n"
                + "    \"primary\": \"primary\",\n"
                + "    \"default\": \"default\"\n"
                + "  },\n"
                + "  \"translations\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"nodes\": [\n"
                + "    {\n"
                + "      \"id\": \"id\",\n"
                + "      \"type\": \"FLOW\",\n"
                + "      \"coordinates\": {\n"
                + "        \"x\": 1,\n"
                + "        \"y\": 1\n"
                + "      },\n"
                + "      \"alias\": \"alias\",\n"
                + "      \"config\": {\n"
                + "        \"flow_id\": \"flow_id\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"start\": {\n"
                + "    \"hidden_fields\": [\n"
                + "      {\n"
                + "        \"key\": \"key\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"next_node\": \"next_node\",\n"
                + "    \"coordinates\": {\n"
                + "      \"x\": 1,\n"
                + "      \"y\": 1\n"
                + "    }\n"
                + "  },\n"
                + "  \"ending\": {\n"
                + "    \"redirection\": {\n"
                + "      \"delay\": 1,\n"
                + "      \"target\": \"target\"\n"
                + "    },\n"
                + "    \"after_submit\": {\n"
                + "      \"flow_id\": \"flow_id\"\n"
                + "    },\n"
                + "    \"coordinates\": {\n"
                + "      \"x\": 1,\n"
                + "      \"y\": 1\n"
                + "    },\n"
                + "    \"resume_flow\": true\n"
                + "  },\n"
                + "  \"style\": {\n"
                + "    \"css\": \"css\"\n"
                + "  },\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"embedded_at\": \"embedded_at\",\n"
                + "  \"submitted_at\": \"submitted_at\"\n"
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
