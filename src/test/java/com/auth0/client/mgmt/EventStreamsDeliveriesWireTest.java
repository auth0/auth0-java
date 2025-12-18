package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.eventstreams.types.ListEventStreamDeliveriesRequestParameters;
import com.auth0.client.mgmt.types.EventStreamDelivery;
import com.auth0.client.mgmt.types.GetEventStreamDeliveryHistoryResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class EventStreamsDeliveriesWireTest {
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
                                "[{\"id\":\"id\",\"event_stream_id\":\"event_stream_id\",\"status\":\"failed\",\"event_type\":\"user.created\",\"attempts\":[{\"status\":\"failed\",\"timestamp\":\"2024-01-15T09:30:00Z\"}],\"event\":{\"id\":\"id\",\"source\":\"source\",\"specversion\":\"specversion\",\"type\":\"type\",\"time\":\"2024-01-15T09:30:00Z\",\"data\":\"data\"}}]"));
        List<EventStreamDelivery> response = client.eventStreams()
                .deliveries()
                .list(
                        "id",
                        ListEventStreamDeliveriesRequestParameters.builder()
                                .statuses(OptionalNullable.of("statuses"))
                                .eventTypes(OptionalNullable.of("event_types"))
                                .dateFrom(OptionalNullable.of("date_from"))
                                .dateTo(OptionalNullable.of("date_to"))
                                .from(OptionalNullable.of("from"))
                                .take(OptionalNullable.of(1))
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "[\n"
                + "  {\n"
                + "    \"id\": \"id\",\n"
                + "    \"event_stream_id\": \"event_stream_id\",\n"
                + "    \"status\": \"failed\",\n"
                + "    \"event_type\": \"user.created\",\n"
                + "    \"attempts\": [\n"
                + "      {\n"
                + "        \"status\": \"failed\",\n"
                + "        \"timestamp\": \"2024-01-15T09:30:00Z\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"event\": {\n"
                + "      \"id\": \"id\",\n"
                + "      \"source\": \"source\",\n"
                + "      \"specversion\": \"specversion\",\n"
                + "      \"type\": \"type\",\n"
                + "      \"time\": \"2024-01-15T09:30:00Z\",\n"
                + "      \"data\": \"data\"\n"
                + "    }\n"
                + "  }\n"
                + "]";
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
    public void testGetHistory() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"event_stream_id\":\"event_stream_id\",\"status\":\"failed\",\"event_type\":\"user.created\",\"attempts\":[{\"status\":\"failed\",\"timestamp\":\"2024-01-15T09:30:00Z\",\"error_message\":\"error_message\"}],\"event\":{\"id\":\"id\",\"source\":\"source\",\"specversion\":\"specversion\",\"type\":\"type\",\"time\":\"2024-01-15T09:30:00Z\",\"data\":\"data\"}}"));
        GetEventStreamDeliveryHistoryResponseContent response =
                client.eventStreams().deliveries().getHistory("id", "event_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

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
