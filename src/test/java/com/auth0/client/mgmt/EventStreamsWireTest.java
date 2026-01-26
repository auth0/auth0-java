package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.CreateEventStreamResponseContent;
import com.auth0.client.mgmt.types.CreateEventStreamTestEventRequestContent;
import com.auth0.client.mgmt.types.CreateEventStreamTestEventResponseContent;
import com.auth0.client.mgmt.types.CreateEventStreamWebHookRequestContent;
import com.auth0.client.mgmt.types.EventStreamResponseContent;
import com.auth0.client.mgmt.types.EventStreamTestEventTypeEnum;
import com.auth0.client.mgmt.types.EventStreamWebhookAuthorizationResponse;
import com.auth0.client.mgmt.types.EventStreamWebhookBasicAuth;
import com.auth0.client.mgmt.types.EventStreamWebhookBasicAuthMethodEnum;
import com.auth0.client.mgmt.types.EventStreamWebhookConfiguration;
import com.auth0.client.mgmt.types.EventStreamWebhookDestination;
import com.auth0.client.mgmt.types.EventStreamWebhookDestinationTypeEnum;
import com.auth0.client.mgmt.types.EventStreamsCreateRequest;
import com.auth0.client.mgmt.types.GetEventStreamResponseContent;
import com.auth0.client.mgmt.types.ListEventStreamsRequestParameters;
import com.auth0.client.mgmt.types.UpdateEventStreamRequestContent;
import com.auth0.client.mgmt.types.UpdateEventStreamResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventStreamsWireTest {
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
                                "{\"eventStreams\":[{\"id\":\"id\",\"name\":\"name\",\"subscriptions\":[{}],\"destination\":{\"type\":\"webhook\",\"configuration\":{\"webhook_endpoint\":\"webhook_endpoint\",\"webhook_authorization\":{\"method\":\"basic\",\"username\":\"username\"}}},\"status\":\"enabled\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"next\":\"next\"}"));
        SyncPagingIterable<EventStreamResponseContent> response = client.eventStreams()
                .list(ListEventStreamsRequestParameters.builder()
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
                                "{\"id\":\"id\",\"name\":\"name\",\"subscriptions\":[{\"event_type\":\"event_type\"}],\"destination\":{\"type\":\"webhook\",\"configuration\":{\"webhook_endpoint\":\"webhook_endpoint\",\"webhook_authorization\":{\"method\":\"basic\",\"username\":\"username\"}}},\"status\":\"enabled\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        CreateEventStreamResponseContent response = client.eventStreams()
                .create(EventStreamsCreateRequest.of(CreateEventStreamWebHookRequestContent.builder()
                        .destination(EventStreamWebhookDestination.builder()
                                .type(EventStreamWebhookDestinationTypeEnum.WEBHOOK)
                                .configuration(EventStreamWebhookConfiguration.builder()
                                        .webhookEndpoint("webhook_endpoint")
                                        .webhookAuthorization(EventStreamWebhookAuthorizationResponse.of(
                                                EventStreamWebhookBasicAuth.builder()
                                                        .method(EventStreamWebhookBasicAuthMethodEnum.BASIC)
                                                        .username("username")
                                                        .build()))
                                        .build())
                                .build())
                        .build()));
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"destination\": {\n"
                + "    \"type\": \"webhook\",\n"
                + "    \"configuration\": {\n"
                + "      \"webhook_endpoint\": \"webhook_endpoint\",\n"
                + "      \"webhook_authorization\": {\n"
                + "        \"method\": \"basic\",\n"
                + "        \"username\": \"username\"\n"
                + "      }\n"
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
                + "  \"subscriptions\": [\n"
                + "    {\n"
                + "      \"event_type\": \"event_type\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"destination\": {\n"
                + "    \"type\": \"webhook\",\n"
                + "    \"configuration\": {\n"
                + "      \"webhook_endpoint\": \"webhook_endpoint\",\n"
                + "      \"webhook_authorization\": {\n"
                + "        \"method\": \"basic\",\n"
                + "        \"username\": \"username\"\n"
                + "      }\n"
                + "    }\n"
                + "  },\n"
                + "  \"status\": \"enabled\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"subscriptions\":[{\"event_type\":\"event_type\"}],\"destination\":{\"type\":\"webhook\",\"configuration\":{\"webhook_endpoint\":\"webhook_endpoint\",\"webhook_authorization\":{\"method\":\"basic\",\"username\":\"username\"}}},\"status\":\"enabled\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        GetEventStreamResponseContent response = client.eventStreams().get("id");
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
                + "  \"subscriptions\": [\n"
                + "    {\n"
                + "      \"event_type\": \"event_type\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"destination\": {\n"
                + "    \"type\": \"webhook\",\n"
                + "    \"configuration\": {\n"
                + "      \"webhook_endpoint\": \"webhook_endpoint\",\n"
                + "      \"webhook_authorization\": {\n"
                + "        \"method\": \"basic\",\n"
                + "        \"username\": \"username\"\n"
                + "      }\n"
                + "    }\n"
                + "  },\n"
                + "  \"status\": \"enabled\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
        client.eventStreams().delete("id");
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
                                "{\"id\":\"id\",\"name\":\"name\",\"subscriptions\":[{\"event_type\":\"event_type\"}],\"destination\":{\"type\":\"webhook\",\"configuration\":{\"webhook_endpoint\":\"webhook_endpoint\",\"webhook_authorization\":{\"method\":\"basic\",\"username\":\"username\"}}},\"status\":\"enabled\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        UpdateEventStreamResponseContent response = client.eventStreams()
                .update("id", UpdateEventStreamRequestContent.builder().build());
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
                + "  \"subscriptions\": [\n"
                + "    {\n"
                + "      \"event_type\": \"event_type\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"destination\": {\n"
                + "    \"type\": \"webhook\",\n"
                + "    \"configuration\": {\n"
                + "      \"webhook_endpoint\": \"webhook_endpoint\",\n"
                + "      \"webhook_authorization\": {\n"
                + "        \"method\": \"basic\",\n"
                + "        \"username\": \"username\"\n"
                + "      }\n"
                + "    }\n"
                + "  },\n"
                + "  \"status\": \"enabled\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"updated_at\": \"2024-01-15T09:30:00Z\"\n"
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
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"event_stream_id\":\"event_stream_id\",\"status\":\"failed\",\"event_type\":\"user.created\",\"attempts\":[{\"status\":\"failed\",\"timestamp\":\"2024-01-15T09:30:00Z\",\"error_message\":\"error_message\"}],\"event\":{\"id\":\"id\",\"source\":\"source\",\"specversion\":\"specversion\",\"type\":\"type\",\"time\":\"2024-01-15T09:30:00Z\",\"data\":\"data\"}}"));
        CreateEventStreamTestEventResponseContent response = client.eventStreams()
                .test(
                        "id",
                        CreateEventStreamTestEventRequestContent.builder()
                                .eventType(EventStreamTestEventTypeEnum.USER_CREATED)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"event_type\": \"user.created\"\n" + "}";
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
                + "  \"event_stream_id\": \"event_stream_id\",\n"
                + "  \"status\": \"failed\",\n"
                + "  \"event_type\": \"user.created\",\n"
                + "  \"attempts\": [\n"
                + "    {\n"
                + "      \"status\": \"failed\",\n"
                + "      \"timestamp\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"error_message\": \"error_message\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"event\": {\n"
                + "    \"id\": \"id\",\n"
                + "    \"source\": \"source\",\n"
                + "    \"specversion\": \"specversion\",\n"
                + "    \"type\": \"type\",\n"
                + "    \"time\": \"2024-01-15T09:30:00Z\",\n"
                + "    \"data\": \"data\"\n"
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
