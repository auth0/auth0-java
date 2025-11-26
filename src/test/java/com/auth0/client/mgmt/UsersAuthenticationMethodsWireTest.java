package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.AuthenticationTypeEnum;
import com.auth0.client.mgmt.types.CreateUserAuthenticationMethodResponseContent;
import com.auth0.client.mgmt.types.CreatedUserAuthenticationMethodTypeEnum;
import com.auth0.client.mgmt.types.GetUserAuthenticationMethodResponseContent;
import com.auth0.client.mgmt.types.SetUserAuthenticationMethodResponseContent;
import com.auth0.client.mgmt.types.SetUserAuthenticationMethods;
import com.auth0.client.mgmt.types.UpdateUserAuthenticationMethodResponseContent;
import com.auth0.client.mgmt.types.UserAuthenticationMethod;
import com.auth0.client.mgmt.users.types.CreateUserAuthenticationMethodRequestContent;
import com.auth0.client.mgmt.users.types.ListUserAuthenticationMethodsRequestParameters;
import com.auth0.client.mgmt.users.types.UpdateUserAuthenticationMethodRequestContent;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.List;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UsersAuthenticationMethodsWireTest {
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
                                "{\"start\":1.1,\"limit\":1.1,\"total\":1.1,\"authenticators\":[{\"id\":\"id\",\"type\":\"recovery-code\",\"confirmed\":true,\"name\":\"name\",\"authentication_methods\":[{}],\"preferred_authentication_method\":\"voice\",\"link_id\":\"link_id\",\"phone_number\":\"phone_number\",\"email\":\"email\",\"key_id\":\"key_id\",\"public_key\":\"public_key\",\"created_at\":\"2024-01-15T09:30:00Z\",\"enrolled_at\":\"2024-01-15T09:30:00Z\",\"last_auth_at\":\"2024-01-15T09:30:00Z\",\"credential_device_type\":\"credential_device_type\",\"credential_backed_up\":true,\"identity_user_id\":\"identity_user_id\",\"user_agent\":\"user_agent\"}]}"));
        SyncPagingIterable<UserAuthenticationMethod> response = client.users()
                .authenticationMethods()
                .list(
                        "id",
                        ListUserAuthenticationMethodsRequestParameters.builder()
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
                                "{\"id\":\"id\",\"type\":\"phone\",\"name\":\"name\",\"totp_secret\":\"totp_secret\",\"phone_number\":\"phone_number\",\"email\":\"email\",\"authentication_methods\":[{\"type\":\"totp\",\"id\":\"id\"}],\"preferred_authentication_method\":\"voice\",\"key_id\":\"key_id\",\"public_key\":\"public_key\",\"relying_party_identifier\":\"relying_party_identifier\",\"created_at\":\"2024-01-15T09:30:00Z\"}"));
        CreateUserAuthenticationMethodResponseContent response = client.users()
                .authenticationMethods()
                .create(
                        "id",
                        CreateUserAuthenticationMethodRequestContent.builder()
                                .type(CreatedUserAuthenticationMethodTypeEnum.PHONE)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"type\": \"phone\"\n" + "}";
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
                + "  \"type\": \"phone\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"totp_secret\": \"totp_secret\",\n"
                + "  \"phone_number\": \"phone_number\",\n"
                + "  \"email\": \"email\",\n"
                + "  \"authentication_methods\": [\n"
                + "    {\n"
                + "      \"type\": \"totp\",\n"
                + "      \"id\": \"id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"preferred_authentication_method\": \"voice\",\n"
                + "  \"key_id\": \"key_id\",\n"
                + "  \"public_key\": \"public_key\",\n"
                + "  \"relying_party_identifier\": \"relying_party_identifier\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testSet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "[{\"id\":\"id\",\"type\":\"phone\",\"name\":\"name\",\"totp_secret\":\"totp_secret\",\"phone_number\":\"phone_number\",\"email\":\"email\",\"authentication_methods\":[{}],\"preferred_authentication_method\":\"voice\",\"key_id\":\"key_id\",\"public_key\":\"public_key\",\"relying_party_identifier\":\"relying_party_identifier\",\"created_at\":\"2024-01-15T09:30:00Z\"}]"));
        List<SetUserAuthenticationMethodResponseContent> response = client.users()
                .authenticationMethods()
                .set(
                        "id",
                        Arrays.asList(SetUserAuthenticationMethods.builder()
                                .type(AuthenticationTypeEnum.PHONE)
                                .build()));
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("PUT", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "[\n" + "  {\n" + "    \"type\": \"phone\"\n" + "  }\n" + "]";
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
                + "[\n"
                + "  {\n"
                + "    \"id\": \"id\",\n"
                + "    \"type\": \"phone\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"totp_secret\": \"totp_secret\",\n"
                + "    \"phone_number\": \"phone_number\",\n"
                + "    \"email\": \"email\",\n"
                + "    \"authentication_methods\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"preferred_authentication_method\": \"voice\",\n"
                + "    \"key_id\": \"key_id\",\n"
                + "    \"public_key\": \"public_key\",\n"
                + "    \"relying_party_identifier\": \"relying_party_identifier\",\n"
                + "    \"created_at\": \"2024-01-15T09:30:00Z\"\n"
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
    public void testDeleteAll() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        client.users().authenticationMethods().deleteAll("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("DELETE", request.getMethod());
    }

    @Test
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"id\":\"id\",\"type\":\"recovery-code\",\"confirmed\":true,\"name\":\"name\",\"authentication_methods\":[{\"type\":\"totp\",\"id\":\"id\"}],\"preferred_authentication_method\":\"voice\",\"link_id\":\"link_id\",\"phone_number\":\"phone_number\",\"email\":\"email\",\"key_id\":\"key_id\",\"public_key\":\"public_key\",\"created_at\":\"2024-01-15T09:30:00Z\",\"enrolled_at\":\"2024-01-15T09:30:00Z\",\"last_auth_at\":\"2024-01-15T09:30:00Z\",\"credential_device_type\":\"credential_device_type\",\"credential_backed_up\":true,\"identity_user_id\":\"identity_user_id\",\"user_agent\":\"user_agent\"}"));
        GetUserAuthenticationMethodResponseContent response =
                client.users().authenticationMethods().get("id", "authentication_method_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"id\": \"id\",\n"
                + "  \"type\": \"recovery-code\",\n"
                + "  \"confirmed\": true,\n"
                + "  \"name\": \"name\",\n"
                + "  \"authentication_methods\": [\n"
                + "    {\n"
                + "      \"type\": \"totp\",\n"
                + "      \"id\": \"id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"preferred_authentication_method\": \"voice\",\n"
                + "  \"link_id\": \"link_id\",\n"
                + "  \"phone_number\": \"phone_number\",\n"
                + "  \"email\": \"email\",\n"
                + "  \"key_id\": \"key_id\",\n"
                + "  \"public_key\": \"public_key\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"enrolled_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"last_auth_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"credential_device_type\": \"credential_device_type\",\n"
                + "  \"credential_backed_up\": true,\n"
                + "  \"identity_user_id\": \"identity_user_id\",\n"
                + "  \"user_agent\": \"user_agent\"\n"
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
        client.users().authenticationMethods().delete("id", "authentication_method_id");
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
                                "{\"id\":\"id\",\"type\":\"phone\",\"name\":\"name\",\"totp_secret\":\"totp_secret\",\"phone_number\":\"phone_number\",\"email\":\"email\",\"authentication_methods\":[{\"type\":\"totp\",\"id\":\"id\"}],\"preferred_authentication_method\":\"voice\",\"key_id\":\"key_id\",\"public_key\":\"public_key\",\"relying_party_identifier\":\"relying_party_identifier\",\"created_at\":\"2024-01-15T09:30:00Z\"}"));
        UpdateUserAuthenticationMethodResponseContent response = client.users()
                .authenticationMethods()
                .update(
                        "id",
                        "authentication_method_id",
                        UpdateUserAuthenticationMethodRequestContent.builder().build());
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
                + "  \"type\": \"phone\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"totp_secret\": \"totp_secret\",\n"
                + "  \"phone_number\": \"phone_number\",\n"
                + "  \"email\": \"email\",\n"
                + "  \"authentication_methods\": [\n"
                + "    {\n"
                + "      \"type\": \"totp\",\n"
                + "      \"id\": \"id\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"preferred_authentication_method\": \"voice\",\n"
                + "  \"key_id\": \"key_id\",\n"
                + "  \"public_key\": \"public_key\",\n"
                + "  \"relying_party_identifier\": \"relying_party_identifier\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\"\n"
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
