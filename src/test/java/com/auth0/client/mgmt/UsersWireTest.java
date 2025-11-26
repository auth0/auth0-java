package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.CreateUserRequestContent;
import com.auth0.client.mgmt.types.CreateUserResponseContent;
import com.auth0.client.mgmt.types.GetUserRequestParameters;
import com.auth0.client.mgmt.types.GetUserResponseContent;
import com.auth0.client.mgmt.types.ListUsersByEmailRequestParameters;
import com.auth0.client.mgmt.types.ListUsersRequestParameters;
import com.auth0.client.mgmt.types.RegenerateUsersRecoveryCodeResponseContent;
import com.auth0.client.mgmt.types.RevokeUserAccessRequestContent;
import com.auth0.client.mgmt.types.SearchEngineVersionsEnum;
import com.auth0.client.mgmt.types.UpdateUserRequestContent;
import com.auth0.client.mgmt.types.UpdateUserResponseContent;
import com.auth0.client.mgmt.types.UserResponseSchema;
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

public class UsersWireTest {
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
                                "{\"start\":1.1,\"limit\":1.1,\"length\":1.1,\"total\":1.1,\"users\":[{\"user_id\":\"user_id\",\"email\":\"email\",\"email_verified\":true,\"username\":\"username\",\"phone_number\":\"phone_number\",\"phone_verified\":true,\"created_at\":\"created_at\",\"updated_at\":\"updated_at\",\"identities\":[{}],\"app_metadata\":{\"key\":\"value\"},\"user_metadata\":{\"key\":\"value\"},\"picture\":\"picture\",\"name\":\"name\",\"nickname\":\"nickname\",\"multifactor\":[\"multifactor\"],\"last_ip\":\"last_ip\",\"last_login\":\"last_login\",\"logins_count\":1,\"blocked\":true,\"given_name\":\"given_name\",\"family_name\":\"family_name\"}]}"));
        SyncPagingIterable<UserResponseSchema> response = client.users()
                .list(ListUsersRequestParameters.builder()
                        .page(1)
                        .perPage(1)
                        .includeTotals(true)
                        .sort("sort")
                        .connection("connection")
                        .fields("fields")
                        .includeFields(true)
                        .q("q")
                        .searchEngine(SearchEngineVersionsEnum.V1)
                        .primaryOrder(true)
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
                                "{\"user_id\":\"user_id\",\"email\":\"email\",\"email_verified\":true,\"username\":\"username\",\"phone_number\":\"phone_number\",\"phone_verified\":true,\"created_at\":\"created_at\",\"updated_at\":\"updated_at\",\"identities\":[{\"connection\":\"connection\",\"user_id\":\"user_id\",\"provider\":\"ad\",\"isSocial\":true,\"access_token\":\"access_token\",\"access_token_secret\":\"access_token_secret\",\"refresh_token\":\"refresh_token\"}],\"app_metadata\":{\"key\":\"value\"},\"user_metadata\":{\"key\":\"value\"},\"picture\":\"picture\",\"name\":\"name\",\"nickname\":\"nickname\",\"multifactor\":[\"multifactor\"],\"last_ip\":\"last_ip\",\"last_login\":\"last_login\",\"logins_count\":1,\"blocked\":true,\"given_name\":\"given_name\",\"family_name\":\"family_name\"}"));
        CreateUserResponseContent response = client.users()
                .create(CreateUserRequestContent.builder()
                        .connection("connection")
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = "" + "{\n" + "  \"connection\": \"connection\"\n" + "}";
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
                + "  \"user_id\": \"user_id\",\n"
                + "  \"email\": \"email\",\n"
                + "  \"email_verified\": true,\n"
                + "  \"username\": \"username\",\n"
                + "  \"phone_number\": \"phone_number\",\n"
                + "  \"phone_verified\": true,\n"
                + "  \"created_at\": \"created_at\",\n"
                + "  \"updated_at\": \"updated_at\",\n"
                + "  \"identities\": [\n"
                + "    {\n"
                + "      \"connection\": \"connection\",\n"
                + "      \"user_id\": \"user_id\",\n"
                + "      \"provider\": \"ad\",\n"
                + "      \"isSocial\": true,\n"
                + "      \"access_token\": \"access_token\",\n"
                + "      \"access_token_secret\": \"access_token_secret\",\n"
                + "      \"refresh_token\": \"refresh_token\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"app_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"user_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"picture\": \"picture\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"nickname\": \"nickname\",\n"
                + "  \"multifactor\": [\n"
                + "    \"multifactor\"\n"
                + "  ],\n"
                + "  \"last_ip\": \"last_ip\",\n"
                + "  \"last_login\": \"last_login\",\n"
                + "  \"logins_count\": 1,\n"
                + "  \"blocked\": true,\n"
                + "  \"given_name\": \"given_name\",\n"
                + "  \"family_name\": \"family_name\"\n"
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
    public void testListUsersByEmail() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "[{\"user_id\":\"user_id\",\"email\":\"email\",\"email_verified\":true,\"username\":\"username\",\"phone_number\":\"phone_number\",\"phone_verified\":true,\"created_at\":\"created_at\",\"updated_at\":\"updated_at\",\"identities\":[{}],\"app_metadata\":{\"key\":\"value\"},\"user_metadata\":{\"key\":\"value\"},\"picture\":\"picture\",\"name\":\"name\",\"nickname\":\"nickname\",\"multifactor\":[\"multifactor\"],\"last_ip\":\"last_ip\",\"last_login\":\"last_login\",\"logins_count\":1,\"blocked\":true,\"given_name\":\"given_name\",\"family_name\":\"family_name\"}]"));
        List<UserResponseSchema> response = client.users()
                .listUsersByEmail(ListUsersByEmailRequestParameters.builder()
                        .email("email")
                        .fields("fields")
                        .includeFields(true)
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
                + "    \"user_id\": \"user_id\",\n"
                + "    \"email\": \"email\",\n"
                + "    \"email_verified\": true,\n"
                + "    \"username\": \"username\",\n"
                + "    \"phone_number\": \"phone_number\",\n"
                + "    \"phone_verified\": true,\n"
                + "    \"created_at\": \"created_at\",\n"
                + "    \"updated_at\": \"updated_at\",\n"
                + "    \"identities\": [\n"
                + "      {}\n"
                + "    ],\n"
                + "    \"app_metadata\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"user_metadata\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"picture\": \"picture\",\n"
                + "    \"name\": \"name\",\n"
                + "    \"nickname\": \"nickname\",\n"
                + "    \"multifactor\": [\n"
                + "      \"multifactor\"\n"
                + "    ],\n"
                + "    \"last_ip\": \"last_ip\",\n"
                + "    \"last_login\": \"last_login\",\n"
                + "    \"logins_count\": 1,\n"
                + "    \"blocked\": true,\n"
                + "    \"given_name\": \"given_name\",\n"
                + "    \"family_name\": \"family_name\"\n"
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
    public void testGet() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"user_id\":\"user_id\",\"email\":\"email\",\"email_verified\":true,\"username\":\"username\",\"phone_number\":\"phone_number\",\"phone_verified\":true,\"created_at\":\"created_at\",\"updated_at\":\"updated_at\",\"identities\":[{\"connection\":\"connection\",\"user_id\":\"user_id\",\"provider\":\"ad\",\"isSocial\":true,\"access_token\":\"access_token\",\"access_token_secret\":\"access_token_secret\",\"refresh_token\":\"refresh_token\"}],\"app_metadata\":{\"key\":\"value\"},\"user_metadata\":{\"key\":\"value\"},\"picture\":\"picture\",\"name\":\"name\",\"nickname\":\"nickname\",\"multifactor\":[\"multifactor\"],\"last_ip\":\"last_ip\",\"last_login\":\"last_login\",\"logins_count\":1,\"blocked\":true,\"given_name\":\"given_name\",\"family_name\":\"family_name\"}"));
        GetUserResponseContent response = client.users()
                .get(
                        "id",
                        GetUserRequestParameters.builder()
                                .fields("fields")
                                .includeFields(true)
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"user_id\": \"user_id\",\n"
                + "  \"email\": \"email\",\n"
                + "  \"email_verified\": true,\n"
                + "  \"username\": \"username\",\n"
                + "  \"phone_number\": \"phone_number\",\n"
                + "  \"phone_verified\": true,\n"
                + "  \"created_at\": \"created_at\",\n"
                + "  \"updated_at\": \"updated_at\",\n"
                + "  \"identities\": [\n"
                + "    {\n"
                + "      \"connection\": \"connection\",\n"
                + "      \"user_id\": \"user_id\",\n"
                + "      \"provider\": \"ad\",\n"
                + "      \"isSocial\": true,\n"
                + "      \"access_token\": \"access_token\",\n"
                + "      \"access_token_secret\": \"access_token_secret\",\n"
                + "      \"refresh_token\": \"refresh_token\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"app_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"user_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"picture\": \"picture\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"nickname\": \"nickname\",\n"
                + "  \"multifactor\": [\n"
                + "    \"multifactor\"\n"
                + "  ],\n"
                + "  \"last_ip\": \"last_ip\",\n"
                + "  \"last_login\": \"last_login\",\n"
                + "  \"logins_count\": 1,\n"
                + "  \"blocked\": true,\n"
                + "  \"given_name\": \"given_name\",\n"
                + "  \"family_name\": \"family_name\"\n"
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
        client.users().delete("id");
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
                                "{\"user_id\":\"user_id\",\"email\":\"email\",\"email_verified\":true,\"username\":\"username\",\"phone_number\":\"phone_number\",\"phone_verified\":true,\"created_at\":\"created_at\",\"updated_at\":\"updated_at\",\"identities\":[{\"connection\":\"connection\",\"user_id\":\"user_id\",\"provider\":\"ad\",\"isSocial\":true,\"access_token\":\"access_token\",\"access_token_secret\":\"access_token_secret\",\"refresh_token\":\"refresh_token\"}],\"app_metadata\":{\"key\":\"value\"},\"user_metadata\":{\"key\":\"value\"},\"picture\":\"picture\",\"name\":\"name\",\"nickname\":\"nickname\",\"multifactor\":[\"multifactor\"],\"last_ip\":\"last_ip\",\"last_login\":\"last_login\",\"logins_count\":1,\"blocked\":true,\"given_name\":\"given_name\",\"family_name\":\"family_name\"}"));
        UpdateUserResponseContent response =
                client.users().update("id", UpdateUserRequestContent.builder().build());
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
                + "  \"user_id\": \"user_id\",\n"
                + "  \"email\": \"email\",\n"
                + "  \"email_verified\": true,\n"
                + "  \"username\": \"username\",\n"
                + "  \"phone_number\": \"phone_number\",\n"
                + "  \"phone_verified\": true,\n"
                + "  \"created_at\": \"created_at\",\n"
                + "  \"updated_at\": \"updated_at\",\n"
                + "  \"identities\": [\n"
                + "    {\n"
                + "      \"connection\": \"connection\",\n"
                + "      \"user_id\": \"user_id\",\n"
                + "      \"provider\": \"ad\",\n"
                + "      \"isSocial\": true,\n"
                + "      \"access_token\": \"access_token\",\n"
                + "      \"access_token_secret\": \"access_token_secret\",\n"
                + "      \"refresh_token\": \"refresh_token\"\n"
                + "    }\n"
                + "  ],\n"
                + "  \"app_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"user_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"picture\": \"picture\",\n"
                + "  \"name\": \"name\",\n"
                + "  \"nickname\": \"nickname\",\n"
                + "  \"multifactor\": [\n"
                + "    \"multifactor\"\n"
                + "  ],\n"
                + "  \"last_ip\": \"last_ip\",\n"
                + "  \"last_login\": \"last_login\",\n"
                + "  \"logins_count\": 1,\n"
                + "  \"blocked\": true,\n"
                + "  \"given_name\": \"given_name\",\n"
                + "  \"family_name\": \"family_name\"\n"
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
    public void testRegenerateRecoveryCode() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"recovery_code\":\"recovery_code\"}"));
        RegenerateUsersRecoveryCodeResponseContent response = client.users().regenerateRecoveryCode("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = "" + "{\n" + "  \"recovery_code\": \"recovery_code\"\n" + "}";
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
    public void testRevokeAccess() throws Exception {
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{}"));
        client.users()
                .revokeAccess("id", RevokeUserAccessRequestContent.builder().build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
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
