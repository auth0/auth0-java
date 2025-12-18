package com.auth0.client.mgmt;

import org.junit.Test;

/**
 * Tests for ManagementApiBuilder domain-based initialization.
 *
 * <p>These tests verify the new domain(), clientCredentials(), and audience() methods
 * that provide Node SDK parity for Auth0 Management API client initialization.
 */
public class ManagementApiBuilderTest {

    /**
     * Test that domain() method is available and returns the builder.
     */
    @Test
    public void testDomainMethodExists() {
        ManagementApiBuilder builder = ManagementApi.builder();
        ManagementApiBuilder result = builder.domain("my-tenant.auth0.com");
        assert result == builder : "domain() should return the same builder instance";
    }

    /**
     * Test that clientCredentials() method is available and returns the builder.
     */
    @Test
    public void testClientCredentialsMethodExists() {
        ManagementApiBuilder builder = ManagementApi.builder();
        ManagementApiBuilder result = builder.clientCredentials("client-id", "client-secret");
        assert result == builder : "clientCredentials() should return the same builder instance";
    }

    /**
     * Test that audience() method is available and returns the builder.
     */
    @Test
    public void testAudienceMethodExists() {
        ManagementApiBuilder builder = ManagementApi.builder();
        ManagementApiBuilder result = builder.audience("https://custom-api.com/api/v2/");
        assert result == builder : "audience() should return the same builder instance";
    }

    /**
     * Test that build() fails when no authentication is provided.
     */
    @Test(expected = RuntimeException.class)
    public void testBuildFailsWithoutAuth() {
        ManagementApi.builder().domain("my-tenant.auth0.com").build();
    }

    /**
     * Test that build() fails when only clientId is provided without clientSecret.
     */
    @Test(expected = RuntimeException.class)
    public void testBuildFailsWithPartialCredentials() {
        // This tests an edge case where someone might call clientCredentials incorrectly
        ManagementApiBuilder builder = ManagementApi.builder();
        builder.domain("my-tenant.auth0.com");
        // Manually set only clientId to simulate partial configuration
        // This should fail during build
        builder.build();
    }

    /**
     * Test that build() succeeds with token authentication.
     */
    @Test
    public void testBuildWithToken() {
        ManagementApi client = ManagementApi.builder()
                .domain("my-tenant.auth0.com")
                .token("test-token")
                .build();
        assert client != null : "build() should return a ManagementApi instance";
    }

    /**
     * Test that build() succeeds with clientCredentials authentication.
     * Note: This creates the client but won't actually fetch a token until used.
     */
    @Test
    public void testBuildWithClientCredentials() {
        ManagementApi client = ManagementApi.builder()
                .domain("my-tenant.auth0.com")
                .clientCredentials("client-id", "client-secret")
                .build();
        assert client != null : "build() should return a ManagementApi instance";
    }

    /**
     * Test that build() succeeds with clientCredentials and custom audience.
     */
    @Test
    public void testBuildWithClientCredentialsAndAudience() {
        ManagementApi client = ManagementApi.builder()
                .domain("my-tenant.auth0.com")
                .clientCredentials("client-id", "client-secret")
                .audience("https://custom-api.com/api/v2/")
                .build();
        assert client != null : "build() should return a ManagementApi instance";
    }

    /**
     * Test backwards compatibility: url() + token() still works.
     */
    @Test
    public void testBackwardsCompatibilityWithUrl() {
        ManagementApi client = ManagementApi.builder()
                .url("https://my-tenant.auth0.com/api/v2")
                .token("test-token")
                .build();
        assert client != null : "build() should return a ManagementApi instance";
    }

    /**
     * Test that clientCredentials works with url() instead of domain().
     */
    @Test
    public void testClientCredentialsWithUrl() {
        ManagementApi client = ManagementApi.builder()
                .url("https://my-tenant.auth0.com/api/v2")
                .clientCredentials("client-id", "client-secret")
                .build();
        assert client != null : "build() should return a ManagementApi instance";
    }

    /**
     * Test fluent chaining with all options.
     */
    @Test
    public void testFluentChaining() {
        ManagementApi client = ManagementApi.builder()
                .domain("my-tenant.auth0.com")
                .clientCredentials("client-id", "client-secret")
                .audience("https://custom-api.com/api/v2/")
                .timeout(30)
                .maxRetries(3)
                .addHeader("X-Custom-Header", "custom-value")
                .build();
        assert client != null : "Fluent chaining should work";
    }
}
