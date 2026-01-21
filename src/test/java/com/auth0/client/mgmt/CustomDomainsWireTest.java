package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.ObjectMappers;
import com.auth0.client.mgmt.core.OptionalNullable;
import com.auth0.client.mgmt.types.CreateCustomDomainRequestContent;
import com.auth0.client.mgmt.types.CreateCustomDomainResponseContent;
import com.auth0.client.mgmt.types.CustomDomain;
import com.auth0.client.mgmt.types.CustomDomainProvisioningTypeEnum;
import com.auth0.client.mgmt.types.GetCustomDomainResponseContent;
import com.auth0.client.mgmt.types.ListCustomDomainsRequestParameters;
import com.auth0.client.mgmt.types.TestCustomDomainResponseContent;
import com.auth0.client.mgmt.types.UpdateCustomDomainRequestContent;
import com.auth0.client.mgmt.types.UpdateCustomDomainResponseContent;
import com.auth0.client.mgmt.types.VerifyCustomDomainResponseContent;
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

public class CustomDomainsWireTest {
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
                                "[{\"custom_domain_id\":\"custom_domain_id\",\"domain\":\"domain\",\"primary\":true,\"is_default\":true,\"status\":\"pending_verification\",\"type\":\"auth0_managed_certs\",\"origin_domain_name\":\"origin_domain_name\",\"verification\":{\"methods\":[{\"name\":\"cname\",\"record\":\"record\"}],\"status\":\"verified\",\"error_msg\":\"error_msg\",\"last_verified_at\":\"last_verified_at\"},\"custom_client_ip_header\":\"custom_client_ip_header\",\"tls_policy\":\"tls_policy\",\"domain_metadata\":{\"key\":\"value\"},\"certificate\":{\"status\":\"provisioning\",\"error_msg\":\"error_msg\",\"certificate_authority\":\"letsencrypt\",\"renews_before\":\"renews_before\"},\"relying_party_identifier\":\"relying_party_identifier\"}]"));
        List<CustomDomain> response = client.customDomains()
                .list(ListCustomDomainsRequestParameters.builder()
                        .q(OptionalNullable.of("q"))
                        .fields(OptionalNullable.of("fields"))
                        .includeFields(OptionalNullable.of(true))
                        .sort(OptionalNullable.of("sort"))
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
                + "    \"custom_domain_id\": \"custom_domain_id\",\n"
                + "    \"domain\": \"domain\",\n"
                + "    \"primary\": true,\n"
                + "    \"is_default\": true,\n"
                + "    \"status\": \"pending_verification\",\n"
                + "    \"type\": \"auth0_managed_certs\",\n"
                + "    \"origin_domain_name\": \"origin_domain_name\",\n"
                + "    \"verification\": {\n"
                + "      \"methods\": [\n"
                + "        {\n"
                + "          \"name\": \"cname\",\n"
                + "          \"record\": \"record\"\n"
                + "        }\n"
                + "      ],\n"
                + "      \"status\": \"verified\",\n"
                + "      \"error_msg\": \"error_msg\",\n"
                + "      \"last_verified_at\": \"last_verified_at\"\n"
                + "    },\n"
                + "    \"custom_client_ip_header\": \"custom_client_ip_header\",\n"
                + "    \"tls_policy\": \"tls_policy\",\n"
                + "    \"domain_metadata\": {\n"
                + "      \"key\": \"value\"\n"
                + "    },\n"
                + "    \"certificate\": {\n"
                + "      \"status\": \"provisioning\",\n"
                + "      \"error_msg\": \"error_msg\",\n"
                + "      \"certificate_authority\": \"letsencrypt\",\n"
                + "      \"renews_before\": \"renews_before\"\n"
                + "    },\n"
                + "    \"relying_party_identifier\": \"relying_party_identifier\"\n"
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
    public void testCreate() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"custom_domain_id\":\"custom_domain_id\",\"domain\":\"domain\",\"primary\":true,\"is_default\":true,\"status\":\"pending_verification\",\"type\":\"auth0_managed_certs\",\"verification\":{\"methods\":[{\"name\":\"cname\",\"record\":\"record\"}],\"status\":\"verified\",\"error_msg\":\"error_msg\",\"last_verified_at\":\"last_verified_at\"},\"custom_client_ip_header\":\"custom_client_ip_header\",\"tls_policy\":\"tls_policy\",\"domain_metadata\":{\"key\":\"value\"},\"certificate\":{\"status\":\"provisioning\",\"error_msg\":\"error_msg\",\"certificate_authority\":\"letsencrypt\",\"renews_before\":\"renews_before\"},\"relying_party_identifier\":\"relying_party_identifier\"}"));
        CreateCustomDomainResponseContent response = client.customDomains()
                .create(CreateCustomDomainRequestContent.builder()
                        .domain("domain")
                        .type(CustomDomainProvisioningTypeEnum.AUTH0MANAGED_CERTS)
                        .build());
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());
        // Validate request body
        String actualRequestBody = request.getBody().readUtf8();
        String expectedRequestBody =
                "" + "{\n" + "  \"domain\": \"domain\",\n" + "  \"type\": \"auth0_managed_certs\"\n" + "}";
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
                + "  \"custom_domain_id\": \"custom_domain_id\",\n"
                + "  \"domain\": \"domain\",\n"
                + "  \"primary\": true,\n"
                + "  \"is_default\": true,\n"
                + "  \"status\": \"pending_verification\",\n"
                + "  \"type\": \"auth0_managed_certs\",\n"
                + "  \"verification\": {\n"
                + "    \"methods\": [\n"
                + "      {\n"
                + "        \"name\": \"cname\",\n"
                + "        \"record\": \"record\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"status\": \"verified\",\n"
                + "    \"error_msg\": \"error_msg\",\n"
                + "    \"last_verified_at\": \"last_verified_at\"\n"
                + "  },\n"
                + "  \"custom_client_ip_header\": \"custom_client_ip_header\",\n"
                + "  \"tls_policy\": \"tls_policy\",\n"
                + "  \"domain_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"certificate\": {\n"
                + "    \"status\": \"provisioning\",\n"
                + "    \"error_msg\": \"error_msg\",\n"
                + "    \"certificate_authority\": \"letsencrypt\",\n"
                + "    \"renews_before\": \"renews_before\"\n"
                + "  },\n"
                + "  \"relying_party_identifier\": \"relying_party_identifier\"\n"
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
                                "{\"custom_domain_id\":\"custom_domain_id\",\"domain\":\"domain\",\"primary\":true,\"is_default\":true,\"status\":\"pending_verification\",\"type\":\"auth0_managed_certs\",\"origin_domain_name\":\"origin_domain_name\",\"verification\":{\"methods\":[{\"name\":\"cname\",\"record\":\"record\"}],\"status\":\"verified\",\"error_msg\":\"error_msg\",\"last_verified_at\":\"last_verified_at\"},\"custom_client_ip_header\":\"custom_client_ip_header\",\"tls_policy\":\"tls_policy\",\"domain_metadata\":{\"key\":\"value\"},\"certificate\":{\"status\":\"provisioning\",\"error_msg\":\"error_msg\",\"certificate_authority\":\"letsencrypt\",\"renews_before\":\"renews_before\"},\"relying_party_identifier\":\"relying_party_identifier\"}"));
        GetCustomDomainResponseContent response = client.customDomains().get("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("GET", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"custom_domain_id\": \"custom_domain_id\",\n"
                + "  \"domain\": \"domain\",\n"
                + "  \"primary\": true,\n"
                + "  \"is_default\": true,\n"
                + "  \"status\": \"pending_verification\",\n"
                + "  \"type\": \"auth0_managed_certs\",\n"
                + "  \"origin_domain_name\": \"origin_domain_name\",\n"
                + "  \"verification\": {\n"
                + "    \"methods\": [\n"
                + "      {\n"
                + "        \"name\": \"cname\",\n"
                + "        \"record\": \"record\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"status\": \"verified\",\n"
                + "    \"error_msg\": \"error_msg\",\n"
                + "    \"last_verified_at\": \"last_verified_at\"\n"
                + "  },\n"
                + "  \"custom_client_ip_header\": \"custom_client_ip_header\",\n"
                + "  \"tls_policy\": \"tls_policy\",\n"
                + "  \"domain_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"certificate\": {\n"
                + "    \"status\": \"provisioning\",\n"
                + "    \"error_msg\": \"error_msg\",\n"
                + "    \"certificate_authority\": \"letsencrypt\",\n"
                + "    \"renews_before\": \"renews_before\"\n"
                + "  },\n"
                + "  \"relying_party_identifier\": \"relying_party_identifier\"\n"
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
        client.customDomains().delete("id");
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
                                "{\"custom_domain_id\":\"custom_domain_id\",\"domain\":\"domain\",\"primary\":true,\"is_default\":true,\"status\":\"pending_verification\",\"type\":\"auth0_managed_certs\",\"verification\":{\"methods\":[{\"name\":\"cname\",\"record\":\"record\"}],\"status\":\"verified\",\"error_msg\":\"error_msg\",\"last_verified_at\":\"last_verified_at\"},\"custom_client_ip_header\":\"custom_client_ip_header\",\"tls_policy\":\"tls_policy\",\"domain_metadata\":{\"key\":\"value\"},\"certificate\":{\"status\":\"provisioning\",\"error_msg\":\"error_msg\",\"certificate_authority\":\"letsencrypt\",\"renews_before\":\"renews_before\"},\"relying_party_identifier\":\"relying_party_identifier\"}"));
        UpdateCustomDomainResponseContent response = client.customDomains()
                .update("id", UpdateCustomDomainRequestContent.builder().build());
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
                + "  \"custom_domain_id\": \"custom_domain_id\",\n"
                + "  \"domain\": \"domain\",\n"
                + "  \"primary\": true,\n"
                + "  \"is_default\": true,\n"
                + "  \"status\": \"pending_verification\",\n"
                + "  \"type\": \"auth0_managed_certs\",\n"
                + "  \"verification\": {\n"
                + "    \"methods\": [\n"
                + "      {\n"
                + "        \"name\": \"cname\",\n"
                + "        \"record\": \"record\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"status\": \"verified\",\n"
                + "    \"error_msg\": \"error_msg\",\n"
                + "    \"last_verified_at\": \"last_verified_at\"\n"
                + "  },\n"
                + "  \"custom_client_ip_header\": \"custom_client_ip_header\",\n"
                + "  \"tls_policy\": \"tls_policy\",\n"
                + "  \"domain_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"certificate\": {\n"
                + "    \"status\": \"provisioning\",\n"
                + "    \"error_msg\": \"error_msg\",\n"
                + "    \"certificate_authority\": \"letsencrypt\",\n"
                + "    \"renews_before\": \"renews_before\"\n"
                + "  },\n"
                + "  \"relying_party_identifier\": \"relying_party_identifier\"\n"
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
        server.enqueue(new MockResponse().setResponseCode(200).setBody("{\"success\":true,\"message\":\"message\"}"));
        TestCustomDomainResponseContent response = client.customDomains().test("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = "" + "{\n" + "  \"success\": true,\n" + "  \"message\": \"message\"\n" + "}";
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
    public void testVerify() throws Exception {
        server.enqueue(
                new MockResponse()
                        .setResponseCode(200)
                        .setBody(
                                "{\"custom_domain_id\":\"custom_domain_id\",\"domain\":\"domain\",\"primary\":true,\"status\":\"pending_verification\",\"type\":\"auth0_managed_certs\",\"cname_api_key\":\"cname_api_key\",\"origin_domain_name\":\"origin_domain_name\",\"verification\":{\"methods\":[{\"name\":\"cname\",\"record\":\"record\"}],\"status\":\"verified\",\"error_msg\":\"error_msg\",\"last_verified_at\":\"last_verified_at\"},\"custom_client_ip_header\":\"custom_client_ip_header\",\"tls_policy\":\"tls_policy\",\"domain_metadata\":{\"key\":\"value\"},\"certificate\":{\"status\":\"provisioning\",\"error_msg\":\"error_msg\",\"certificate_authority\":\"letsencrypt\",\"renews_before\":\"renews_before\"}}"));
        VerifyCustomDomainResponseContent response = client.customDomains().verify("id");
        RecordedRequest request = server.takeRequest();
        Assertions.assertNotNull(request);
        Assertions.assertEquals("POST", request.getMethod());

        // Validate response body
        Assertions.assertNotNull(response, "Response should not be null");
        String actualResponseJson = objectMapper.writeValueAsString(response);
        String expectedResponseBody = ""
                + "{\n"
                + "  \"custom_domain_id\": \"custom_domain_id\",\n"
                + "  \"domain\": \"domain\",\n"
                + "  \"primary\": true,\n"
                + "  \"status\": \"pending_verification\",\n"
                + "  \"type\": \"auth0_managed_certs\",\n"
                + "  \"cname_api_key\": \"cname_api_key\",\n"
                + "  \"origin_domain_name\": \"origin_domain_name\",\n"
                + "  \"verification\": {\n"
                + "    \"methods\": [\n"
                + "      {\n"
                + "        \"name\": \"cname\",\n"
                + "        \"record\": \"record\"\n"
                + "      }\n"
                + "    ],\n"
                + "    \"status\": \"verified\",\n"
                + "    \"error_msg\": \"error_msg\",\n"
                + "    \"last_verified_at\": \"last_verified_at\"\n"
                + "  },\n"
                + "  \"custom_client_ip_header\": \"custom_client_ip_header\",\n"
                + "  \"tls_policy\": \"tls_policy\",\n"
                + "  \"domain_metadata\": {\n"
                + "    \"key\": \"value\"\n"
                + "  },\n"
                + "  \"certificate\": {\n"
                + "    \"status\": \"provisioning\",\n"
                + "    \"error_msg\": \"error_msg\",\n"
                + "    \"certificate_authority\": \"letsencrypt\",\n"
                + "    \"renews_before\": \"renews_before\"\n"
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
