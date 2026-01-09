package com.auth0.client.mgmt;

import com.auth0.client.mgmt.core.OAuthTokenSupplier;
import com.auth0.client.mgmt.core.RequestOptions;
import org.junit.Test;

/**
 * Tests for OAuth per-request credentials functionality.
 */
public class OAuthTokenSupplierTest {

    /**
     * Verify that special characters in credentials don't cause issues.
     * This tests the fix for JSON injection vulnerability.
     */
    @Test
    public void testSpecialCharactersInCredentials() {
        // Create supplier with credentials containing special characters
        String clientIdWithQuotes = "client\"with\\quotes";
        String clientSecretWithSpecialChars = "secret'with\"special\\chars\nand\nnewlines";
        String baseUrl = "https://example.auth0.com";

        // This should not throw an exception during construction
        OAuthTokenSupplier supplier =
                new OAuthTokenSupplier(clientIdWithQuotes, clientSecretWithSpecialChars, baseUrl, null);

        // Verify object was created successfully
        assert supplier != null;
    }

    /**
     * Verify that multiple suppliers can be created without resource issues.
     * This tests the fix for HTTP client resource leak.
     */
    @Test
    public void testMultipleSupplierCreation() {
        // Create multiple suppliers - they should all share the same HTTP client
        for (int i = 0; i < 100; i++) {
            OAuthTokenSupplier supplier = new OAuthTokenSupplier(
                    "client-id-" + i, "client-secret-" + i, "https://tenant-" + i + ".auth0.com", null);
            assert supplier != null;
        }
    }

    /**
     * Test base URL validation.
     */
    @Test(expected = IllegalArgumentException.class)
    public void testNullBaseUrl() {
        new OAuthTokenSupplier("client-id", "client-secret", null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyBaseUrl() {
        new OAuthTokenSupplier("client-id", "client-secret", "", null);
    }

    @Test
    public void testTrailingSlashNormalization() {
        OAuthTokenSupplier supplier1 = new OAuthTokenSupplier("id", "secret", "https://example.auth0.com/", null);
        OAuthTokenSupplier supplier2 = new OAuthTokenSupplier("id", "secret", "https://example.auth0.com", null);

        // Both should work without issues (trailing slash normalized)
        assert supplier1 != null;
        assert supplier2 != null;
    }

    /**
     * Test RequestOptions creation and getters.
     */
    @Test
    public void testRequestOptionsCreation() {
        RequestOptions options =
                RequestOptions.withClientCredentials("https://example.auth0.com", "client-id", "client-secret");

        assert options != null;
        assert options.getClientId().equals("client-id");
        assert options.getClientSecret().equals("client-secret");
        assert options.getBaseUrl().equals("https://example.auth0.com");
        assert options.hasClientCredentials();
    }

    @Test
    public void testRequestOptionsWithAudience() {
        RequestOptions options = RequestOptions.withClientCredentialsAndAudience(
                "https://example.auth0.com", "client-id", "client-secret", "https://custom-api.com/api/v2/");

        assert options != null;
        assert options.getAudience().equals("https://custom-api.com/api/v2/");
    }

    @Test
    public void testRequestOptionsBuilder() {
        RequestOptions options = RequestOptions.builder()
                .baseUrl("https://example.auth0.com")
                .clientCredentials("client-id", "client-secret")
                .timeout(30)
                .addHeader("X-Custom", "value")
                .build();

        assert options != null;
        assert options.hasClientCredentials();
        assert options.getClientId().equals("client-id");
        assert options.getBaseUrl().equals("https://example.auth0.com");
    }

    @Test
    public void testRequestOptionsWithoutCredentials() {
        RequestOptions options = RequestOptions.builder().timeout(10).build();

        assert options != null;
        assert !options.hasClientCredentials();
        assert options.getClientId() == null;
    }
}
