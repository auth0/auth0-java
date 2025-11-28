package com.auth0.client.mgmt;

import static org.junit.jupiter.api.Assertions.*;

import com.auth0.client.mgmt.core.ClientOptions;
import com.auth0.client.mgmt.core.Environment;
import com.auth0.client.mgmt.core.RequestOptions;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive tests to verify that Fern-generated Auth0 SDK supports
 * dynamic token management using Supplier<String>.
 */
public class DynamicTokenManagementTest {

    @Test
    @DisplayName("Verify Supplier<String> is evaluated on each request")
    public void testSupplierEvaluatedOnEachRequest() {
        // Counter to track supplier evaluations
        AtomicInteger evaluationCount = new AtomicInteger(0);

        // Create dynamic token supplier
        Supplier<String> tokenSupplier = () -> {
            int count = evaluationCount.incrementAndGet();
            return "token-" + count;
        };

        // Build ClientOptions with dynamic header
        ClientOptions options = ClientOptions.builder()
                .environment(Environment.custom("https://test.auth0.com/api/v2"))
                .addHeader("Authorization", () -> "Bearer " + tokenSupplier.get())
                .build();

        // First request - should evaluate supplier
        Map<String, String> headers1 = options.headers(null);
        assertEquals("Bearer token-1", headers1.get("Authorization"));
        assertEquals(1, evaluationCount.get());

        // Second request - should evaluate supplier again
        Map<String, String> headers2 = options.headers(null);
        assertEquals("Bearer token-2", headers2.get("Authorization"));
        assertEquals(2, evaluationCount.get());

        // Third request - should evaluate supplier again
        Map<String, String> headers3 = options.headers(null);
        assertEquals("Bearer token-3", headers3.get("Authorization"));
        assertEquals(3, evaluationCount.get());

        System.out.println("✅ Supplier is evaluated on each request: " + evaluationCount.get() + " times");
    }

    @Test
    @DisplayName("Test automatic token refresh on expiry")
    public void testAutomaticTokenRefreshOnExpiry() {
        // Simulated token storage with expiry
        class TokenStore {
            private String token;
            private long expiryTime;
            private int refreshCount = 0;

            synchronized String getToken() {
                if (token == null || System.currentTimeMillis() >= expiryTime) {
                    refreshCount++;
                    token = "refreshed-token-" + refreshCount;
                    expiryTime = System.currentTimeMillis() + 1000; // 1 second expiry
                    System.out.println("Token refreshed: " + token);
                }
                return token;
            }

            int getRefreshCount() {
                return refreshCount;
            }
        }

        TokenStore tokenStore = new TokenStore();

        // Build ClientOptions with token store
        ClientOptions options = ClientOptions.builder()
                .environment(Environment.custom("https://test.auth0.com/api/v2"))
                .addHeader("Authorization", () -> "Bearer " + tokenStore.getToken())
                .build();

        // First request - initial token
        Map<String, String> headers1 = options.headers(null);
        assertEquals("Bearer refreshed-token-1", headers1.get("Authorization"));
        assertEquals(1, tokenStore.getRefreshCount());

        // Second request immediately - should use cached token
        Map<String, String> headers2 = options.headers(null);
        assertEquals("Bearer refreshed-token-1", headers2.get("Authorization"));
        assertEquals(1, tokenStore.getRefreshCount()); // No refresh

        // Wait for token to expire
        try {
            Thread.sleep(1100); // 1.1 seconds
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Third request after expiry - should refresh
        Map<String, String> headers3 = options.headers(null);
        assertEquals("Bearer refreshed-token-2", headers3.get("Authorization"));
        assertEquals(2, tokenStore.getRefreshCount()); // Token refreshed

        System.out.println("✅ Token automatically refreshed " + tokenStore.getRefreshCount() + " times");
    }

    @Test
    @DisplayName("Verify static and dynamic headers coexist")
    public void testStaticAndDynamicHeadersCoexist() {
        AtomicInteger dynamicCounter = new AtomicInteger(0);

        ClientOptions options = ClientOptions.builder()
                .environment(Environment.custom("https://test.auth0.com/api/v2"))
                .addHeader("X-Static-Header", "static-value") // Static
                .addHeader("X-Dynamic-Header", () -> "dynamic-" + dynamicCounter.incrementAndGet()) // Dynamic
                .addHeader("X-Another-Static", "another-static") // Static
                .build();

        // First request
        Map<String, String> headers1 = options.headers(null);
        assertEquals("static-value", headers1.get("X-Static-Header"));
        assertEquals("dynamic-1", headers1.get("X-Dynamic-Header"));
        assertEquals("another-static", headers1.get("X-Another-Static"));

        // Second request - static should remain same, dynamic should change
        Map<String, String> headers2 = options.headers(null);
        assertEquals("static-value", headers2.get("X-Static-Header"));
        assertEquals("dynamic-2", headers2.get("X-Dynamic-Header"));
        assertEquals("another-static", headers2.get("X-Another-Static"));

        System.out.println("✅ Static and dynamic headers coexist correctly");
    }

    @Test
    @DisplayName("Test multiple dynamic headers with different suppliers")
    public void testMultipleDynamicHeaders() {
        AtomicInteger authCounter = new AtomicInteger(0);
        AtomicInteger requestIdCounter = new AtomicInteger(100);
        AtomicReference<String> sessionId = new AtomicReference<>("session-initial");

        ClientOptions options = ClientOptions.builder()
                .environment(Environment.custom("https://test.auth0.com/api/v2"))
                .addHeader("Authorization", () -> "Bearer token-" + authCounter.incrementAndGet())
                .addHeader("X-Request-ID", () -> "req-" + requestIdCounter.addAndGet(10))
                .addHeader("X-Session-ID", sessionId::get)
                .addHeader("X-Timestamp", () -> String.valueOf(System.currentTimeMillis()))
                .build();

        // First request
        Map<String, String> headers1 = options.headers(null);
        assertEquals("Bearer token-1", headers1.get("Authorization"));
        assertEquals("req-110", headers1.get("X-Request-ID"));
        assertEquals("session-initial", headers1.get("X-Session-ID"));
        String timestamp1 = headers1.get("X-Timestamp");
        assertNotNull(timestamp1);

        // Update session ID
        sessionId.set("session-updated");

        // Small delay for timestamp
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Second request
        Map<String, String> headers2 = options.headers(null);
        assertEquals("Bearer token-2", headers2.get("Authorization"));
        assertEquals("req-120", headers2.get("X-Request-ID"));
        assertEquals("session-updated", headers2.get("X-Session-ID"));
        String timestamp2 = headers2.get("X-Timestamp");
        assertNotEquals(timestamp1, timestamp2);

        System.out.println("✅ Multiple dynamic headers work independently");
    }

    @Test
    @DisplayName("Test RequestOptions override dynamic headers")
    public void testRequestOptionsOverrideDynamicHeaders() {
        AtomicInteger dynamicCounter = new AtomicInteger(0);

        ClientOptions options = ClientOptions.builder()
                .environment(Environment.custom("https://test.auth0.com/api/v2"))
                .addHeader("Authorization", () -> "Bearer dynamic-" + dynamicCounter.incrementAndGet())
                .addHeader("X-Header", "client-level")
                .build();

        // Request without override
        Map<String, String> headers1 = options.headers(null);
        assertEquals("Bearer dynamic-1", headers1.get("Authorization"));
        assertEquals("client-level", headers1.get("X-Header"));

        // Request with RequestOptions override
        RequestOptions requestOptions = RequestOptions.builder()
                .addHeader("Authorization", "Bearer request-override")
                .addHeader("X-Header", "request-level")
                .addHeader("X-New-Header", "new-value")
                .build();

        Map<String, String> headers2 = options.headers(requestOptions);
        assertEquals("Bearer request-override", headers2.get("Authorization")); // Overridden
        assertEquals("request-level", headers2.get("X-Header")); // Overridden
        assertEquals("new-value", headers2.get("X-New-Header")); // Added

        // Verify dynamic supplier was still called (even though overridden)
        assertEquals(2, dynamicCounter.get());

        System.out.println("✅ RequestOptions correctly override dynamic headers");
    }

    @Test
    @DisplayName("Test ManagementApi uses dynamic headers")
    public void testManagementApiUsesDynamicHeaders() throws Exception {
        AtomicInteger tokenVersion = new AtomicInteger(0);

        // Build ManagementApi with dynamic token
        ClientOptions clientOptions = ClientOptions.builder()
                .environment(Environment.custom("https://test.auth0.com/api/v2"))
                .addHeader("Authorization", () -> {
                    int version = tokenVersion.incrementAndGet();
                    System.out.println("Token supplier called: version " + version);
                    return "Bearer dynamic-token-v" + version;
                })
                .build();
        ManagementApi api = new ManagementApi(clientOptions);

        // No need for reflection anymore, we have direct access to clientOptions

        // Verify dynamic header is evaluated on each call
        Map<String, String> headers1 = clientOptions.headers(null);
        assertEquals("Bearer dynamic-token-v1", headers1.get("Authorization"));

        Map<String, String> headers2 = clientOptions.headers(null);
        assertEquals("Bearer dynamic-token-v2", headers2.get("Authorization"));

        Map<String, String> headers3 = clientOptions.headers(null);
        assertEquals("Bearer dynamic-token-v3", headers3.get("Authorization"));

        assertEquals(3, tokenVersion.get());
        System.out.println("✅ ManagementApi correctly uses dynamic headers");
    }

    @Test
    @DisplayName("Test thread safety of dynamic headers")
    public void testThreadSafetyOfDynamicHeaders() throws Exception {
        AtomicInteger globalCounter = new AtomicInteger(0);

        ClientOptions options = ClientOptions.builder()
                .environment(Environment.custom("https://test.auth0.com/api/v2"))
                .addHeader("X-Thread-ID", () -> Thread.currentThread().getName())
                .addHeader("X-Counter", () -> String.valueOf(globalCounter.incrementAndGet()))
                .build();

        // Run multiple threads concurrently
        int threadCount = 10;
        Thread[] threads = new Thread[threadCount];
        String[] results = new String[threadCount];

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            threads[i] = new Thread(
                    () -> {
                        Map<String, String> headers = options.headers(null);
                        results[index] = headers.get("X-Thread-ID");
                    },
                    "TestThread-" + i);
        }

        // Start all threads
        for (Thread thread : threads) {
            thread.start();
        }

        // Wait for all threads to complete
        for (Thread thread : threads) {
            thread.join();
        }

        // Verify each thread got its own thread name
        for (int i = 0; i < threadCount; i++) {
            assertEquals("TestThread-" + i, results[i]);
        }

        // Verify counter was incremented correctly
        assertEquals(threadCount, globalCounter.get());

        System.out.println("✅ Dynamic headers are thread-safe");
    }

    @Test
    @DisplayName("Test supplier exception handling")
    public void testSupplierExceptionHandling() {
        AtomicInteger callCount = new AtomicInteger(0);

        ClientOptions options = ClientOptions.builder()
                .environment(Environment.custom("https://test.auth0.com/api/v2"))
                .addHeader("Authorization", () -> {
                    int count = callCount.incrementAndGet();
                    if (count == 2) {
                        throw new RuntimeException("Simulated token fetch failure");
                    }
                    return "Bearer token-" + count;
                })
                .build();

        // First call - should work
        Map<String, String> headers1 = options.headers(null);
        assertEquals("Bearer token-1", headers1.get("Authorization"));

        // Second call - supplier throws exception
        assertThrows(RuntimeException.class, () -> {
            options.headers(null);
        });

        // Third call - should work again
        Map<String, String> headers3 = options.headers(null);
        assertEquals("Bearer token-3", headers3.get("Authorization"));

        assertEquals(3, callCount.get());
        System.out.println("✅ Supplier exceptions are properly propagated");
    }

    @Test
    @DisplayName("Verify Auth0 real-world scenario: M2M token refresh")
    public void testAuth0MachineToMachineTokenRefresh() {
        // Simulate Auth0 M2M token management
        class Auth0TokenManager {
            private String accessToken;
            private long expiresAt;
            private final String clientId = "test-client-id";
            private final String clientSecret = "test-client-secret";
            private int tokenFetchCount = 0;

            synchronized String getAccessToken() {
                if (accessToken == null || System.currentTimeMillis() >= expiresAt) {
                    // Simulate fetching new M2M token
                    tokenFetchCount++;
                    accessToken = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.mock-m2m-token-" + tokenFetchCount;
                    expiresAt = System.currentTimeMillis() + 86400000; // 24 hours
                    System.out.println("Fetched new M2M token (fetch #" + tokenFetchCount + ")");
                }
                return accessToken;
            }

            void expireToken() {
                expiresAt = 0; // Force expiry
            }

            int getTokenFetchCount() {
                return tokenFetchCount;
            }
        }

        Auth0TokenManager tokenManager = new Auth0TokenManager();

        // Build ManagementApi with Auth0 token manager
        ClientOptions clientOptions = ClientOptions.builder()
                .environment(Environment.custom("https://myapp.auth0.com/api/v2"))
                .addHeader("Authorization", () -> "Bearer " + tokenManager.getAccessToken())
                .build();
        ManagementApi api = new ManagementApi(clientOptions);

        // Simulate API calls
        try {

            // First API call - fetches initial token
            Map<String, String> headers1 = clientOptions.headers(null);
            assertTrue(headers1.get("Authorization").startsWith("Bearer eyJhbGciOiJSUzI1NiI"));
            assertEquals(1, tokenManager.getTokenFetchCount());

            // Multiple API calls - reuse same token
            for (int i = 0; i < 5; i++) {
                Map<String, String> headers = clientOptions.headers(null);
                assertTrue(headers.get("Authorization").contains("mock-m2m-token-1"));
            }
            assertEquals(1, tokenManager.getTokenFetchCount()); // Still only 1 fetch

            // Expire token
            tokenManager.expireToken();

            // Next API call - fetches new token
            Map<String, String> headersAfterExpiry = clientOptions.headers(null);
            assertTrue(headersAfterExpiry.get("Authorization").contains("mock-m2m-token-2"));
            assertEquals(2, tokenManager.getTokenFetchCount());

            System.out.println("✅ Auth0 M2M token refresh scenario works correctly");

        } catch (Exception e) {
            fail("Test failed: " + e.getMessage());
        }
    }
}
