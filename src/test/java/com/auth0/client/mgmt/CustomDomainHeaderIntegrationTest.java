package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.CustomDomainInterceptor;
import com.auth0.client.mgmt.core.RequestOptions;
import com.auth0.client.mgmt.types.ListUsersRequestParameters;
import com.auth0.client.mgmt.types.VerifyEmailTicketRequestContent;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Integration tests for the Auth0-Custom-Domain header feature using MockWebServer.
 */
public class CustomDomainHeaderIntegrationTest {

    private static final String USERS_RESPONSE =
            "{\"start\":0,\"limit\":50,\"length\":0,\"total\":0,\"users\":[]}";
    private static final String CONNECTIONS_RESPONSE = "{\"connections\":[]}";
    private static final String CUSTOM_DOMAINS_RESPONSE = "[]";
    private static final String TICKET_RESPONSE = "{\"ticket\":\"https://example.com\"}";

    private MockWebServer server;

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testGlobalCustomDomainHeaderOnWhitelistedPath() throws Exception {
        ManagementApi client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .customDomain("login.mycompany.com")
                .build();

        server.enqueue(new MockResponse().setResponseCode(200).setBody(USERS_RESPONSE));
        client.users().list();

        RecordedRequest request = server.takeRequest();
        Assertions.assertEquals(
                "login.mycompany.com", request.getHeader(CustomDomainInterceptor.HEADER_NAME));
    }

    @Test
    public void testGlobalCustomDomainHeaderStrippedOnNonWhitelistedPath() throws Exception {
        ManagementApi client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .customDomain("login.mycompany.com")
                .build();

        server.enqueue(new MockResponse().setResponseCode(200).setBody(CUSTOM_DOMAINS_RESPONSE));
        client.customDomains().list();

        RecordedRequest request = server.takeRequest();
        Assertions.assertNull(request.getHeader(CustomDomainInterceptor.HEADER_NAME));
    }

    @Test
    public void testPerRequestCustomDomainOverride() throws Exception {
        ManagementApi client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .customDomain("login.mycompany.com")
                .build();

        server.enqueue(new MockResponse().setResponseCode(200).setBody(USERS_RESPONSE));
        client.users()
                .list(ListUsersRequestParameters.builder().build(), CustomDomainHeader.of("other.mycompany.com"));

        RecordedRequest request = server.takeRequest();
        Assertions.assertEquals(
                "other.mycompany.com", request.getHeader(CustomDomainInterceptor.HEADER_NAME));
    }

    @Test
    public void testNoCustomDomainConfigured() throws Exception {
        ManagementApi client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .build();

        server.enqueue(new MockResponse().setResponseCode(200).setBody(USERS_RESPONSE));
        client.users().list();

        RecordedRequest request = server.takeRequest();
        Assertions.assertNull(request.getHeader(CustomDomainInterceptor.HEADER_NAME));
    }

    @Test
    public void testPerRequestCustomDomainWithoutGlobal() throws Exception {
        ManagementApi client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .build();

        server.enqueue(new MockResponse().setResponseCode(200).setBody(USERS_RESPONSE));

        RequestOptions options = RequestOptions.builder()
                .addHeader(CustomDomainInterceptor.HEADER_NAME, "login.mycompany.com")
                .build();
        client.users().list(ListUsersRequestParameters.builder().build(), options);

        RecordedRequest request = server.takeRequest();
        // Without the interceptor registered (no customDomain on builder), the header passes through as-is
        Assertions.assertEquals(
                "login.mycompany.com", request.getHeader(CustomDomainInterceptor.HEADER_NAME));
    }

    @Test
    public void testCustomDomainHeaderOnTicketsEndpoint() throws Exception {
        ManagementApi client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .customDomain("login.mycompany.com")
                .build();

        server.enqueue(new MockResponse().setResponseCode(200).setBody(TICKET_RESPONSE));
        client.tickets()
                .verifyEmail(VerifyEmailTicketRequestContent.builder()
                        .userId("auth0|123")
                        .build());

        RecordedRequest request = server.takeRequest();
        Assertions.assertEquals(
                "login.mycompany.com", request.getHeader(CustomDomainInterceptor.HEADER_NAME));
    }

    @Test
    public void testCustomDomainHeaderOnConnectionsStripped() throws Exception {
        ManagementApi client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .customDomain("login.mycompany.com")
                .build();

        server.enqueue(new MockResponse().setResponseCode(200).setBody(CONNECTIONS_RESPONSE));
        client.connections().list();

        RecordedRequest request = server.takeRequest();
        Assertions.assertNull(request.getHeader(CustomDomainInterceptor.HEADER_NAME));
    }

    @Test
    public void testCustomDomainHeaderHelperCreatesValidRequestOptions() {
        RequestOptions options = CustomDomainHeader.of("login.mycompany.com");
        Assertions.assertEquals(
                "login.mycompany.com", options.getHeaders().get(CustomDomainInterceptor.HEADER_NAME));
    }
}
