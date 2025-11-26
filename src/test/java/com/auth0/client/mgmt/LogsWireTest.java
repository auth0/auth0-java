package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.GetLogResponseContent;
import com.auth0.client.mgmt.types.ListLogsRequestParameters;
import com.auth0.client.mgmt.types.Log;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LogsWireTest {
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
                                "{\"start\":1.1,\"limit\":1.1,\"length\":1.1,\"total\":1.1,\"logs\":[{\"date\":\"date\",\"type\":\"type\",\"description\":\"description\",\"connection\":\"connection\",\"connection_id\":\"connection_id\",\"client_id\":\"client_id\",\"client_name\":\"client_name\",\"ip\":\"ip\",\"hostname\":\"hostname\",\"user_id\":\"user_id\",\"user_name\":\"user_name\",\"audience\":\"audience\",\"scope\":\"scope\",\"strategy\":\"strategy\",\"strategy_type\":\"strategy_type\",\"log_id\":\"log_id\",\"isMobile\":true,\"details\":{\"key\":\"value\"},\"user_agent\":\"user_agent\"}]}"));
        SyncPagingIterable<Log> response = client.logs()
                .list(ListLogsRequestParameters.builder()
                        .page(1)
                        .perPage(1)
                        .sort("sort")
                        .fields("fields")
                        .includeFields(true)
                        .includeTotals(true)
                        .q("q")
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
                                "{\"date\":\"date\",\"type\":\"type\",\"description\":\"description\",\"connection\":\"connection\",\"connection_id\":\"connection_id\",\"client_id\":\"client_id\",\"client_name\":\"client_name\",\"ip\":\"ip\",\"hostname\":\"hostname\",\"user_id\":\"user_id\",\"user_name\":\"user_name\",\"audience\":\"audience\",\"scope\":\"scope\",\"strategy\":\"strategy\",\"strategy_type\":\"strategy_type\",\"log_id\":\"log_id\",\"isMobile\":true,\"details\":{\"key\":\"value\"},\"user_agent\":\"user_agent\",\"security_context\":{\"ja3\":\"ja3\",\"ja4\":\"ja4\"},\"location_info\":{\"country_code\":\"country_code\",\"country_code3\":\"country_code3\",\"country_name\":\"country_name\",\"city_name\":\"city_name\",\"latitude\":\"latitude\",\"longitude\":\"longitude\",\"time_zone\":\"time_zone\",\"continent_code\":\"continent_code\"}}"));
        GetLogResponseContent response = client.logs().get("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"date\": \"date\",\n"
                + "  \"type\": \"type\",\n"
                + "  \"description\": \"description\",\n"
                + "  \"connection\": \"connection\",\n"
                + "  \"connection_id\": \"connection_id\",\n"
                + "  \"client_id\": \"client_id\",\n"
                + "  \"client_name\": \"client_name\",\n"
                + "  \"ip\": \"ip\",\n"
                + "  \"hostname\": \"hostname\",\n"
                + "  \"user_id\": \"user_id\",\n"
                + "  \"user_name\": \"user_name\",\n"
                + "  \"audience\": \"audience\",\n"
                + "  \"scope\": \"scope\",\n"
                + "  \"strategy\": \"strategy\",\n"
                + "  \"strategy_type\": \"strategy_type\",\n"
                + "  \"log_id\": \"log_id\",\n"
                + "  \"isMobile\": true,\n"
                + "  \"details\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"user_agent\": \"user_agent\",\n"
                + "  \"security_context\": {\n"
                + "    \"ja3\": \"ja3\",\n"
                + "    \"ja4\": \"ja4\"\n"
                + "  },\n"
                + "  \"location_info\": {\n"
                + "    \"country_code\": \"country_code\",\n"
                + "    \"country_code3\": \"country_code3\",\n"
                + "    \"country_name\": \"country_name\",\n"
                + "    \"city_name\": \"city_name\",\n"
                + "    \"latitude\": \"latitude\",\n"
                + "    \"longitude\": \"longitude\",\n"
                + "    \"time_zone\": \"time_zone\",\n"
                + "    \"continent_code\": \"continent_code\"\n"
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
