package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.experimentation.types.AdvanceRampRequestContent;
import com.auth0.client.mgmt.experimentation.types.CreateExperimentRequestContent;
import com.auth0.client.mgmt.experimentation.types.ListExperimentsRequestParameters;
import com.auth0.client.mgmt.experimentation.types.UpdateExperimentRequestParameters;
import com.auth0.client.mgmt.experimentation.types.UpdateExperimentStatusRequestContent;
import com.auth0.client.mgmt.types.AdvanceRampResponseContent;
import com.auth0.client.mgmt.types.AuthenticationFlowEnum;
import com.auth0.client.mgmt.types.CreateExperimentResponseContent;
import com.auth0.client.mgmt.types.ExperimentListItem;
import com.auth0.client.mgmt.types.ExperimentStatusEnum;
import com.auth0.client.mgmt.types.ExperimentTransitionStatusEnum;
import com.auth0.client.mgmt.types.GetExperimentResponseContent;
import com.auth0.client.mgmt.types.UpdateExperimentResponseContent;
import com.auth0.client.mgmt.types.UpdateExperimentStatusResponseContent;
import com.auth0.client.mgmt.types.ValidateExperimentResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ExperimentationExperimentsWireTest {
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
                                "{\"experiments\":[{\"id\":\"id\",\"name\":\"name\",\"description\":\"description\",\"feature_flag_id\":\"feature_flag_id\",\"feature_flag_name\":\"feature_flag_name\",\"authentication_flow\":\"authentication_flow\",\"allocation_strategy\":\"percentage\",\"status\":\"draft\",\"is_valid\":true,\"default_config\":\"tenant\",\"feature_flag_snapshot\":{\"key\":\"value\"},\"allocations\":[{}],\"editable_fields\":[\"editable_fields\"],\"levels\":[1],\"current_level\":1,\"started_at\":\"2024-01-15T09:30:00Z\",\"ended_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}],\"next\":\"next\"}"));
        SyncPagingIterable<ExperimentListItem> response = client.experimentation()
                .experiments()
                .list(ListExperimentsRequestParameters.builder()
                        .from("from")
                        .take(1)
                        .status(ExperimentStatusEnum.DRAFT)
                        .authenticationFlow("authentication_flow")
                        .featureFlagId("feature_flag_id")
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
                                "{\"id\":\"id\",\"name\":\"name\",\"description\":\"description\",\"feature_flag_id\":\"feature_flag_id\",\"feature_flag_name\":\"feature_flag_name\",\"authentication_flow\":\"authentication_flow\",\"allocation_strategy\":\"percentage\",\"status\":\"draft\",\"is_valid\":true,\"default_config\":\"tenant\",\"feature_flag_snapshot\":{\"key\":\"value\"},\"allocations\":[{\"variation_id\":\"variation_id\",\"variation_name\":\"variation_name\",\"segment_id\":\"segment_id\",\"segment_name\":\"segment_name\",\"weight\":1,\"priority\":1,\"is_control\":true,\"is_fallback\":true,\"variation_snapshot\":{\"key\":\"value\"},\"segment_snapshot\":{\"key\":\"value\"}}],\"editable_fields\":[\"editable_fields\"],\"levels\":[1],\"current_level\":1,\"started_at\":\"2024-01-15T09:30:00Z\",\"ended_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        CreateExperimentResponseContent response = client.experimentation()
                .experiments()
                .create(CreateExperimentRequestContent.builder()
                        .name("name")
                        .featureFlagId("feature_flag_id")
                        .authenticationFlow(AuthenticationFlowEnum.AUTHENTICATION)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"name\": \"name\",\n"
                + "  \"feature_flag_id\": \"feature_flag_id\",\n"
                + "  \"authentication_flow\": \"authentication\"\n"
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
                + "  \"description\": \"description\",\n"
                + "  \"feature_flag_id\": \"feature_flag_id\",\n"
                + "  \"feature_flag_name\": \"feature_flag_name\",\n"
                + "  \"authentication_flow\": \"authentication_flow\",\n"
                + "  \"allocation_strategy\": \"percentage\",\n"
                + "  \"status\": \"draft\",\n"
                + "  \"is_valid\": true,\n"
                + "  \"default_config\": \"tenant\",\n"
                + "  \"feature_flag_snapshot\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"allocations\": [\n"
                + "    {\n"
                + "      \"variation_id\": \"variation_id\",\n"
                + "      \"variation_name\": \"variation_name\",\n"
                + "      \"segment_id\": \"segment_id\",\n"
                + "      \"segment_name\": \"segment_name\",\n"
                + "      \"weight\": 1,\n"
                + "      \"priority\": 1,\n"
                + "      \"is_control\": true,\n"
                + "      \"is_fallback\": true,\n"
                + "      \"variation_snapshot\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"segment_snapshot\": {\n"
                + "        \"key\": \"value\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"editable_fields\": [\n"
                + "    \"editable_fields\"\n"
                + "  ],\n"
                + "  \"levels\": [\n"
                + "    1\n"
                + "  ],\n"
                + "  \"current_level\": 1,\n"
                + "  \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"description\":\"description\",\"feature_flag_id\":\"feature_flag_id\",\"feature_flag_name\":\"feature_flag_name\",\"authentication_flow\":\"authentication_flow\",\"allocation_strategy\":\"percentage\",\"status\":\"draft\",\"is_valid\":true,\"default_config\":\"tenant\",\"feature_flag_snapshot\":{\"key\":\"value\"},\"allocations\":[{\"variation_id\":\"variation_id\",\"variation_name\":\"variation_name\",\"segment_id\":\"segment_id\",\"segment_name\":\"segment_name\",\"weight\":1,\"priority\":1,\"is_control\":true,\"is_fallback\":true,\"variation_snapshot\":{\"key\":\"value\"},\"segment_snapshot\":{\"key\":\"value\"}}],\"editable_fields\":[\"editable_fields\"],\"levels\":[1],\"current_level\":1,\"started_at\":\"2024-01-15T09:30:00Z\",\"ended_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        GetExperimentResponseContent response =
                client.experimentation().experiments().get("id");
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
                + "  \"description\": \"description\",\n"
                + "  \"feature_flag_id\": \"feature_flag_id\",\n"
                + "  \"feature_flag_name\": \"feature_flag_name\",\n"
                + "  \"authentication_flow\": \"authentication_flow\",\n"
                + "  \"allocation_strategy\": \"percentage\",\n"
                + "  \"status\": \"draft\",\n"
                + "  \"is_valid\": true,\n"
                + "  \"default_config\": \"tenant\",\n"
                + "  \"feature_flag_snapshot\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"allocations\": [\n"
                + "    {\n"
                + "      \"variation_id\": \"variation_id\",\n"
                + "      \"variation_name\": \"variation_name\",\n"
                + "      \"segment_id\": \"segment_id\",\n"
                + "      \"segment_name\": \"segment_name\",\n"
                + "      \"weight\": 1,\n"
                + "      \"priority\": 1,\n"
                + "      \"is_control\": true,\n"
                + "      \"is_fallback\": true,\n"
                + "      \"variation_snapshot\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"segment_snapshot\": {\n"
                + "        \"key\": \"value\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"editable_fields\": [\n"
                + "    \"editable_fields\"\n"
                + "  ],\n"
                + "  \"levels\": [\n"
                + "    1\n"
                + "  ],\n"
                + "  \"current_level\": 1,\n"
                + "  \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
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
        client.experimentation().experiments().delete("id");
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
                                "{\"id\":\"id\",\"name\":\"name\",\"description\":\"description\",\"feature_flag_id\":\"feature_flag_id\",\"feature_flag_name\":\"feature_flag_name\",\"authentication_flow\":\"authentication_flow\",\"allocation_strategy\":\"percentage\",\"status\":\"draft\",\"is_valid\":true,\"default_config\":\"tenant\",\"feature_flag_snapshot\":{\"key\":\"value\"},\"allocations\":[{\"variation_id\":\"variation_id\",\"variation_name\":\"variation_name\",\"segment_id\":\"segment_id\",\"segment_name\":\"segment_name\",\"weight\":1,\"priority\":1,\"is_control\":true,\"is_fallback\":true,\"variation_snapshot\":{\"key\":\"value\"},\"segment_snapshot\":{\"key\":\"value\"}}],\"editable_fields\":[\"editable_fields\"],\"levels\":[1],\"current_level\":1,\"started_at\":\"2024-01-15T09:30:00Z\",\"ended_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        UpdateExperimentResponseContent response = client.experimentation()
                .experiments()
                .update("id", UpdateExperimentRequestParameters.builder().build());
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
                + "  \"description\": \"description\",\n"
                + "  \"feature_flag_id\": \"feature_flag_id\",\n"
                + "  \"feature_flag_name\": \"feature_flag_name\",\n"
                + "  \"authentication_flow\": \"authentication_flow\",\n"
                + "  \"allocation_strategy\": \"percentage\",\n"
                + "  \"status\": \"draft\",\n"
                + "  \"is_valid\": true,\n"
                + "  \"default_config\": \"tenant\",\n"
                + "  \"feature_flag_snapshot\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"allocations\": [\n"
                + "    {\n"
                + "      \"variation_id\": \"variation_id\",\n"
                + "      \"variation_name\": \"variation_name\",\n"
                + "      \"segment_id\": \"segment_id\",\n"
                + "      \"segment_name\": \"segment_name\",\n"
                + "      \"weight\": 1,\n"
                + "      \"priority\": 1,\n"
                + "      \"is_control\": true,\n"
                + "      \"is_fallback\": true,\n"
                + "      \"variation_snapshot\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"segment_snapshot\": {\n"
                + "        \"key\": \"value\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"editable_fields\": [\n"
                + "    \"editable_fields\"\n"
                + "  ],\n"
                + "  \"levels\": [\n"
                + "    1\n"
                + "  ],\n"
                + "  \"current_level\": 1,\n"
                + "  \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
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
    public void testAdvanceRamp() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"experiment_id\":\"experiment_id\",\"from_level\":1,\"to_level\":1,\"current_level\":1}"));
        AdvanceRampResponseContent response = client.experimentation()
                .experiments()
                .advanceRamp(
                        "id", AdvanceRampRequestContent.builder().targetLevel(1).build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"target_level\": 1\n" + "}";
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
                + "  \"experiment_id\": \"experiment_id\",\n"
                + "  \"from_level\": 1,\n"
                + "  \"to_level\": 1,\n"
                + "  \"current_level\": 1\n"
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
    public void testUpdateStatus() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"name\":\"name\",\"description\":\"description\",\"feature_flag_id\":\"feature_flag_id\",\"feature_flag_name\":\"feature_flag_name\",\"authentication_flow\":\"authentication_flow\",\"allocation_strategy\":\"percentage\",\"status\":\"draft\",\"is_valid\":true,\"default_config\":\"tenant\",\"feature_flag_snapshot\":{\"key\":\"value\"},\"allocations\":[{\"variation_id\":\"variation_id\",\"variation_name\":\"variation_name\",\"segment_id\":\"segment_id\",\"segment_name\":\"segment_name\",\"weight\":1,\"priority\":1,\"is_control\":true,\"is_fallback\":true,\"variation_snapshot\":{\"key\":\"value\"},\"segment_snapshot\":{\"key\":\"value\"}}],\"editable_fields\":[\"editable_fields\"],\"levels\":[1],\"current_level\":1,\"started_at\":\"2024-01-15T09:30:00Z\",\"ended_at\":\"2024-01-15T09:30:00Z\",\"created_at\":\"2024-01-15T09:30:00Z\",\"updated_at\":\"2024-01-15T09:30:00Z\"}"));
        UpdateExperimentStatusResponseContent response = client.experimentation()
                .experiments()
                .updateStatus(
                        "id",
                        UpdateExperimentStatusRequestContent.builder()
                                .status(ExperimentTransitionStatusEnum.ACTIVE)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"status\": \"active\"\n" + "}";
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
                + "  \"description\": \"description\",\n"
                + "  \"feature_flag_id\": \"feature_flag_id\",\n"
                + "  \"feature_flag_name\": \"feature_flag_name\",\n"
                + "  \"authentication_flow\": \"authentication_flow\",\n"
                + "  \"allocation_strategy\": \"percentage\",\n"
                + "  \"status\": \"draft\",\n"
                + "  \"is_valid\": true,\n"
                + "  \"default_config\": \"tenant\",\n"
                + "  \"feature_flag_snapshot\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"allocations\": [\n"
                + "    {\n"
                + "      \"variation_id\": \"variation_id\",\n"
                + "      \"variation_name\": \"variation_name\",\n"
                + "      \"segment_id\": \"segment_id\",\n"
                + "      \"segment_name\": \"segment_name\",\n"
                + "      \"weight\": 1,\n"
                + "      \"priority\": 1,\n"
                + "      \"is_control\": true,\n"
                + "      \"is_fallback\": true,\n"
                + "      \"variation_snapshot\": {\n"
                + "        \"key\": \"value\"\n"
                + "      },\n"
                + "      \"segment_snapshot\": {\n"
                + "        \"key\": \"value\"\n"
                + "      }\n"
                + "    }\n"
                + "  ],\n"
                + "  \"editable_fields\": [\n"
                + "    \"editable_fields\"\n"
                + "  ],\n"
                + "  \"levels\": [\n"
                + "    1\n"
                + "  ],\n"
                + "  \"current_level\": 1,\n"
                + "  \"started_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"ended_at\": \"2024-01-15T09:30:00Z\",\n"
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
    public void testValidate() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody("{\"is_valid\":true,\"errors\":[{\"code\":\"code\",\"message\":\"message\"}]}"));
        ValidateExperimentResponseContent response =
                client.experimentation().experiments().validate("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"is_valid\": true,\n"
                + "  \"errors\": [\n"
                + "    {\n"
                + "      \"code\": \"code\",\n"
                + "      \"message\": \"message\"\n"
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
                if (actualValue == null) {
                    if (!entry.getValue().isNull()) return false;
                } else if (!jsonEquals(entry.getValue(), actualValue)) return false;
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
