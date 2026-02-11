package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.CreateResourceServerRequestContent;
import com.auth0.client.mgmt.types.CreateResourceServerResponseContent;
import com.auth0.client.mgmt.types.GetResourceServerRequestParameters;
import com.auth0.client.mgmt.types.GetResourceServerResponseContent;
import com.auth0.client.mgmt.types.ListResourceServerRequestParameters;
import com.auth0.client.mgmt.types.ResourceServer;
import com.auth0.client.mgmt.types.UpdateResourceServerRequestContent;
import com.auth0.client.mgmt.types.UpdateResourceServerResponseContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResourceServersWireTest {
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
                                "{\"start\":1.1,\"limit\":1.1,\"total\":1.1,\"resource_servers\":[{\"id\":\"id\",\"name\":\"name\",\"is_system\":true,\"identifier\":\"identifier\",\"scopes\":[{\"value\":\"value\"}],\"signing_alg\":\"HS256\",\"signing_secret\":\"signing_secret\",\"allow_offline_access\":true,\"skip_consent_for_verifiable_first_party_clients\":true,\"token_lifetime\":1,\"token_lifetime_for_web\":1,\"enforce_policies\":true,\"token_dialect\":\"access_token\",\"token_encryption\":{\"format\":\"compact-nested-jwe\",\"encryption_key\":{\"alg\":\"RSA-OAEP-256\",\"pem\":\"pem\"}},\"consent_policy\":\"transactional-authorization-with-mfa\",\"proof_of_possession\":{\"mechanism\":\"mtls\",\"required\":true},\"client_id\":\"client_id\"}]}"));
        SyncPagingIterable<ResourceServer> response = client.resourceServers()
                .list(ListResourceServerRequestParameters.builder()
                        .page(OptionalNullable.of(1))
                        .perPage(OptionalNullable.of(1))
                        .includeTotals(OptionalNullable.of(true))
                        .includeFields(OptionalNullable.of(true))
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
                                "{\"id\":\"id\",\"name\":\"name\",\"is_system\":true,\"identifier\":\"identifier\",\"scopes\":[{\"value\":\"value\",\"description\":\"description\"}],\"signing_alg\":\"HS256\",\"signing_secret\":\"signing_secret\",\"allow_offline_access\":true,\"skip_consent_for_verifiable_first_party_clients\":true,\"token_lifetime\":1,\"token_lifetime_for_web\":1,\"enforce_policies\":true,\"token_dialect\":\"access_token\",\"token_encryption\":{\"format\":\"compact-nested-jwe\",\"encryption_key\":{\"name\":\"name\",\"alg\":\"RSA-OAEP-256\",\"kid\":\"kid\",\"pem\":\"pem\"}},\"consent_policy\":\"transactional-authorization-with-mfa\",\"authorization_details\":[{\"key\":\"value\"}],\"proof_of_possession\":{\"mechanism\":\"mtls\",\"required\":true,\"required_for\":\"public_clients\"},\"subject_type_authorization\":{\"user\":{\"policy\":\"allow_all\"},\"client\":{\"policy\":\"deny_all\"}},\"client_id\":\"client_id\"}"));
        CreateResourceServerResponseContent response = client.resourceServers()
                .create(CreateResourceServerRequestContent.builder()
                        .identifier("identifier")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"identifier\": \"identifier\"\n" + "}";
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
                + "  \"is_system\": true,\n"
                + "  \"identifier\": \"identifier\",\n"
                + "  \"scopes\": [\n"
                + "    {\n"
                + "      \"value\": \"value\",\n"
                + "      \"description\": \"description\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"signing_alg\": \"HS256\",\n"
                + "  \"signing_secret\": \"signing_secret\",\n"
                + "  \"allow_offline_access\": true,\n"
                + "  \"skip_consent_for_verifiable_first_party_clients\": true,\n"
                + "  \"token_lifetime\": 1,\n"
                + "  \"token_lifetime_for_web\": 1,\n"
                + "  \"enforce_policies\": true,\n"
                + "  \"token_dialect\": \"access_token\",\n"
                + "  \"token_encryption\": {\n"
                + "    \"format\": \"compact-nested-jwe\",\n"
                + "    \"encryption_key\": {\n"
                + "      \"name\": \"name\",\n"
                + "      \"alg\": \"RSA-OAEP-256\",\n"
                + "      \"kid\": \"kid\",\n"
                + "      \"pem\": \"pem\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"consent_policy\": \"transactional-authorization-with-mfa\",\n"
                + "  \"authorization_details\": [\n"
                + "    {\n"
                + "      \"key\": \"value\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"proof_of_possession\": {\n"
                + "    \"mechanism\": \"mtls\",\n"
                + "    \"required\": true,\n"
                + "    \"required_for\": \"public_clients\"\n"
                + "  },\n"
                + "  \"subject_type_authorization\": {\n"
                + "    \"user\": {\n"
                + "      \"policy\": \"allow_all\"\n"
                + "    },\n"
                + "    \"client\": {\n"
                + "      \"policy\": \"deny_all\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"client_id\": \"client_id\"\n"
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
                                "{\"id\":\"id\",\"name\":\"name\",\"is_system\":true,\"identifier\":\"identifier\",\"scopes\":[{\"value\":\"value\",\"description\":\"description\"}],\"signing_alg\":\"HS256\",\"signing_secret\":\"signing_secret\",\"allow_offline_access\":true,\"skip_consent_for_verifiable_first_party_clients\":true,\"token_lifetime\":1,\"token_lifetime_for_web\":1,\"enforce_policies\":true,\"token_dialect\":\"access_token\",\"token_encryption\":{\"format\":\"compact-nested-jwe\",\"encryption_key\":{\"name\":\"name\",\"alg\":\"RSA-OAEP-256\",\"kid\":\"kid\",\"pem\":\"pem\"}},\"consent_policy\":\"transactional-authorization-with-mfa\",\"authorization_details\":[{\"key\":\"value\"}],\"proof_of_possession\":{\"mechanism\":\"mtls\",\"required\":true,\"required_for\":\"public_clients\"},\"subject_type_authorization\":{\"user\":{\"policy\":\"allow_all\"},\"client\":{\"policy\":\"deny_all\"}},\"client_id\":\"client_id\"}"));
        GetResourceServerResponseContent response = client.resourceServers()
                .get(
                        "id",
                        GetResourceServerRequestParameters.builder()
                                .includeFields(OptionalNullable.of(true))
                                .build());
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
                + "  \"is_system\": true,\n"
                + "  \"identifier\": \"identifier\",\n"
                + "  \"scopes\": [\n"
                + "    {\n"
                + "      \"value\": \"value\",\n"
                + "      \"description\": \"description\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"signing_alg\": \"HS256\",\n"
                + "  \"signing_secret\": \"signing_secret\",\n"
                + "  \"allow_offline_access\": true,\n"
                + "  \"skip_consent_for_verifiable_first_party_clients\": true,\n"
                + "  \"token_lifetime\": 1,\n"
                + "  \"token_lifetime_for_web\": 1,\n"
                + "  \"enforce_policies\": true,\n"
                + "  \"token_dialect\": \"access_token\",\n"
                + "  \"token_encryption\": {\n"
                + "    \"format\": \"compact-nested-jwe\",\n"
                + "    \"encryption_key\": {\n"
                + "      \"name\": \"name\",\n"
                + "      \"alg\": \"RSA-OAEP-256\",\n"
                + "      \"kid\": \"kid\",\n"
                + "      \"pem\": \"pem\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"consent_policy\": \"transactional-authorization-with-mfa\",\n"
                + "  \"authorization_details\": [\n"
                + "    {\n"
                + "      \"key\": \"value\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"proof_of_possession\": {\n"
                + "    \"mechanism\": \"mtls\",\n"
                + "    \"required\": true,\n"
                + "    \"required_for\": \"public_clients\"\n"
                + "  },\n"
                + "  \"subject_type_authorization\": {\n"
                + "    \"user\": {\n"
                + "      \"policy\": \"allow_all\"\n"
                + "    },\n"
                + "    \"client\": {\n"
                + "      \"policy\": \"deny_all\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"client_id\": \"client_id\"\n"
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
        client.resourceServers().delete("id");
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
                                "{\"id\":\"id\",\"name\":\"name\",\"is_system\":true,\"identifier\":\"identifier\",\"scopes\":[{\"value\":\"value\",\"description\":\"description\"}],\"signing_alg\":\"HS256\",\"signing_secret\":\"signing_secret\",\"allow_offline_access\":true,\"skip_consent_for_verifiable_first_party_clients\":true,\"token_lifetime\":1,\"token_lifetime_for_web\":1,\"enforce_policies\":true,\"token_dialect\":\"access_token\",\"token_encryption\":{\"format\":\"compact-nested-jwe\",\"encryption_key\":{\"name\":\"name\",\"alg\":\"RSA-OAEP-256\",\"kid\":\"kid\",\"pem\":\"pem\"}},\"consent_policy\":\"transactional-authorization-with-mfa\",\"authorization_details\":[{\"key\":\"value\"}],\"proof_of_possession\":{\"mechanism\":\"mtls\",\"required\":true,\"required_for\":\"public_clients\"},\"subject_type_authorization\":{\"user\":{\"policy\":\"allow_all\"},\"client\":{\"policy\":\"deny_all\"}},\"client_id\":\"client_id\"}"));
        UpdateResourceServerResponseContent response = client.resourceServers()
                .update("id", UpdateResourceServerRequestContent.builder().build());
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
                + "  \"is_system\": true,\n"
                + "  \"identifier\": \"identifier\",\n"
                + "  \"scopes\": [\n"
                + "    {\n"
                + "      \"value\": \"value\",\n"
                + "      \"description\": \"description\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"signing_alg\": \"HS256\",\n"
                + "  \"signing_secret\": \"signing_secret\",\n"
                + "  \"allow_offline_access\": true,\n"
                + "  \"skip_consent_for_verifiable_first_party_clients\": true,\n"
                + "  \"token_lifetime\": 1,\n"
                + "  \"token_lifetime_for_web\": 1,\n"
                + "  \"enforce_policies\": true,\n"
                + "  \"token_dialect\": \"access_token\",\n"
                + "  \"token_encryption\": {\n"
                + "    \"format\": \"compact-nested-jwe\",\n"
                + "    \"encryption_key\": {\n"
                + "      \"name\": \"name\",\n"
                + "      \"alg\": \"RSA-OAEP-256\",\n"
                + "      \"kid\": \"kid\",\n"
                + "      \"pem\": \"pem\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"consent_policy\": \"transactional-authorization-with-mfa\",\n"
                + "  \"authorization_details\": [\n"
                + "    {\n"
                + "      \"key\": \"value\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"proof_of_possession\": {\n"
                + "    \"mechanism\": \"mtls\",\n"
                + "    \"required\": true,\n"
                + "    \"required_for\": \"public_clients\"\n"
                + "  },\n"
                + "  \"subject_type_authorization\": {\n"
                + "    \"user\": {\n"
                + "      \"policy\": \"allow_all\"\n"
                + "    },\n"
                + "    \"client\": {\n"
                + "      \"policy\": \"deny_all\"\n"
                + "    }\n"
                + "  },\n"
                + "  \"client_id\": \"client_id\"\n"
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
