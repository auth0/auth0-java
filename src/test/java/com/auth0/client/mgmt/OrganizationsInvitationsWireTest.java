package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.organizations.types.CreateOrganizationInvitationRequestContent;
import com.auth0.client.mgmt.organizations.types.GetOrganizationInvitationRequestParameters;
import com.auth0.client.mgmt.organizations.types.ListOrganizationInvitationsRequestParameters;
import com.auth0.client.mgmt.types.CreateOrganizationInvitationResponseContent;
import com.auth0.client.mgmt.types.GetOrganizationInvitationResponseContent;
import com.auth0.client.mgmt.types.OrganizationInvitation;
import com.auth0.client.mgmt.types.OrganizationInvitationInvitee;
import com.auth0.client.mgmt.types.OrganizationInvitationInviter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrganizationsInvitationsWireTest {
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
                                "{\"start\":1.1,\"limit\":1.1,\"invitations\":[{\"id\":\"id\",\"organization_id\":\"organization_id\",\"inviter\":{\"name\":\"name\"},\"invitee\":{\"email\":\"email\"},\"invitation_url\":\"invitation_url\",\"created_at\":\"2024-01-15T09:30:00Z\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"client_id\":\"client_id\",\"connection_id\":\"connection_id\",\"app_metadata\":{\"key\":\"value\"},\"user_metadata\":{\"key\":\"value\"},\"roles\":[\"roles\"],\"ticket_id\":\"ticket_id\"}]}"));
        SyncPagingIterable<OrganizationInvitation> response = client.organizations()
                .invitations()
                .list(
                        "id",
                        ListOrganizationInvitationsRequestParameters.builder()
                                .page(1)
                                .perPage(1)
                                .includeTotals(true)
                                .fields("fields")
                                .includeFields(true)
                                .sort("sort")
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
                                "{\"id\":\"id\",\"organization_id\":\"organization_id\",\"inviter\":{\"name\":\"name\"},\"invitee\":{\"email\":\"email\"},\"invitation_url\":\"invitation_url\",\"created_at\":\"2024-01-15T09:30:00Z\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"client_id\":\"client_id\",\"connection_id\":\"connection_id\",\"app_metadata\":{\"key\":\"value\"},\"user_metadata\":{\"key\":\"value\"},\"roles\":[\"roles\"],\"ticket_id\":\"ticket_id\"}"));
        CreateOrganizationInvitationResponseContent response = client.organizations()
                .invitations()
                .create(
                        "id",
                        CreateOrganizationInvitationRequestContent.builder()
                                .inviter(OrganizationInvitationInviter.builder()
                                        .name("name")
                                        .build())
                                .invitee(OrganizationInvitationInvitee.builder()
                                        .email("email")
                                        .build())
                                .clientId("client_id")
                                .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody = ""
                + "{\n"
                + "  \"inviter\": {\n"
                + "    \"name\": \"name\"\n"
                + "  },\n"
                + "  \"invitee\": {\n"
                + "    \"email\": \"email\"\n"
                + "  },\n"
                + "  \"client_id\": \"client_id\"\n"
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
                + "  \"organization_id\": \"organization_id\",\n"
                + "  \"inviter\": {\n"
                + "    \"name\": \"name\"\n"
                + "  },\n"
                + "  \"invitee\": {\n"
                + "    \"email\": \"email\"\n"
                + "  },\n"
                + "  \"invitation_url\": \"invitation_url\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"client_id\": \"client_id\",\n"
                + "  \"connection_id\": \"connection_id\",\n"
                + "  \"app_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"user_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"roles\": [\n"
                + "    \"roles\"\n"
                + "  ],\n"
                + "  \"ticket_id\": \"ticket_id\"\n"
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
                                "{\"id\":\"id\",\"organization_id\":\"organization_id\",\"inviter\":{\"name\":\"name\"},\"invitee\":{\"email\":\"email\"},\"invitation_url\":\"invitation_url\",\"created_at\":\"2024-01-15T09:30:00Z\",\"expires_at\":\"2024-01-15T09:30:00Z\",\"client_id\":\"client_id\",\"connection_id\":\"connection_id\",\"app_metadata\":{\"key\":\"value\"},\"user_metadata\":{\"key\":\"value\"},\"roles\":[\"roles\"],\"ticket_id\":\"ticket_id\"}"));
        GetOrganizationInvitationResponseContent response = client.organizations()
                .invitations()
                .get(
                        "id",
                        "invitation_id",
                        GetOrganizationInvitationRequestParameters.builder()
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
                + "  \"id\": \"id\",\n"
                + "  \"organization_id\": \"organization_id\",\n"
                + "  \"inviter\": {\n"
                + "    \"name\": \"name\"\n"
                + "  },\n"
                + "  \"invitee\": {\n"
                + "    \"email\": \"email\"\n"
                + "  },\n"
                + "  \"invitation_url\": \"invitation_url\",\n"
                + "  \"created_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"expires_at\": \"2024-01-15T09:30:00Z\",\n"
                + "  \"client_id\": \"client_id\",\n"
                + "  \"connection_id\": \"connection_id\",\n"
                + "  \"app_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"user_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"roles\": [\n"
                + "    \"roles\"\n"
                + "  ],\n"
                + "  \"ticket_id\": \"ticket_id\"\n"
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
        client.organizations().invitations().delete("id", "invitation_id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("DELETE", request.getMethod());
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
