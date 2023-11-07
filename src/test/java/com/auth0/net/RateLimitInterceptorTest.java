package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static com.auth0.AssertsUtil.verifyThrows;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.greaterThan;

public class RateLimitInterceptorTest {

    MockWebServer server = new MockWebServer();

    @BeforeEach
    public void setUp() throws Exception {
        server.start();
    }

    @AfterEach
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void shouldRetryRateLimitResponses() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new RateLimitInterceptor(3))
            .build();

        server.enqueue(new MockResponse().setResponseCode(429));
        server.enqueue(new MockResponse().setResponseCode(429));
        server.enqueue(new MockResponse().setResponseCode(429));
        server.enqueue(new MockResponse().setResponseCode(200));

        okhttp3.Request request = new Request.Builder()
            .get()
            .url(server.url("/"))
            .build();

        Response resp = client.newCall(request).execute();

        server.takeRequest();
        server.takeRequest();
        server.takeRequest();
        RecordedRequest finalRequest = server.takeRequest();

        assertThat(resp.code(), is(200));

        // verify that a total of 4 requests were made (zero index; 3 failures and one successful retry)
        assertThat(finalRequest.getSequenceNumber(), is(3));
    }

    @Test
    public void shouldNotRetryIfMaxRetriesIsZero() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new RateLimitInterceptor(0))
            .build();

        server.enqueue(new MockResponse().setResponseCode(429));

        okhttp3.Request request = new Request.Builder()
            .get()
            .url(server.url("/"))
            .build();

        Response resp = client.newCall(request).execute();
        assertThat(resp.code(), is(429));

        // verify that only one request was made (no retries)
        assertThat(server.takeRequest().getSequenceNumber(), is(0));
    }

    @Test
    public void shouldReturnResponseWhenMaxRetriesHit() throws Exception {
        int maxRetries = 3;

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new RateLimitInterceptor(maxRetries))
            .build();

        MockResponse mockResponse = new MockResponse().setResponseCode(429);

        for (int i = 0; i < maxRetries + 1; i++) {
            server.enqueue(mockResponse);
        }

        okhttp3.Request request = new Request.Builder()
            .get()
            .url(server.url("/"))
            .build();

        Response response = client.newCall(request).execute();

        int index = 0;
        for (int i = 0; i < maxRetries + 1; i++) {
            index = server.takeRequest().getSequenceNumber();
        }

        assertThat(response.code(), is(429));

        // Verify that a total of four requests were made (original plus three retries)
        assertThat(index, is(3));
    }

    @Test
    public void shouldNotRetryNonRateLimitErrors() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new RateLimitInterceptor(3))
            .build();

        server.enqueue(new MockResponse().setResponseCode(500));

        okhttp3.Request request = new Request.Builder()
            .get()
            .url(server.url("/"))
            .build();

        Response response = client.newCall(request).execute();

        assertThat(response.code(), is(500));

        // verify only one request was made (not retried)
        assertThat(server.takeRequest().getSequenceNumber(), is(0));
    }

    @Test
    public void shouldBackOffOnRetries()  throws Exception {
        int maxRetries = 10;
        List<Long> retryTimings = new ArrayList<>();

        RateLimitInterceptor interceptor = new RateLimitInterceptor(maxRetries, c -> {
            // keep a sequential list of the elapsed time since last execution request (retry delay)
            retryTimings.add(c.getElapsedAttemptTime().toMillis());
        });

        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .build();

        MockResponse mockResponse = new MockResponse().setResponseCode(429);

        for (int i = 0; i < maxRetries + 1; i++) {
            server.enqueue(mockResponse);
        }

        okhttp3.Request request = new Request.Builder()
            .get()
            .url(server.url("/"))
            .build();

        client.newCall(request).execute();

        // Verify that the last retry attempt is at least 3x greater than the first
        assertThat(retryTimings.get(maxRetries - 1), greaterThan(retryTimings.get(0) * 3L));

        // Verify that the final retry is close to the maximum delay of 1000ms, within variance from jitter
        assertThat(retryTimings.get(maxRetries - 1).doubleValue(), closeTo(1000, 220));

        // Basic checks that retry retryTimings are increasing as retry count increases
        // Different retry attempts are compared to account for backoff growth as well as random jitter
        assertThat(retryTimings.get(2), greaterThan(retryTimings.get(0)));
        assertThat(retryTimings.get(5), greaterThan(retryTimings.get(2)));
    }

    @Test
    public void shouldThrowAuth0Exception() {
        OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new RateLimitInterceptor(3))
            .readTimeout(Duration.ofSeconds(1))
            .build();

        server.enqueue(new MockResponse().setSocketPolicy(SocketPolicy.NO_RESPONSE));

        okhttp3.Request request = new Request.Builder()
            .get()
            .url(server.url("/"))
            .build();

        Auth0Exception e = verifyThrows(Auth0Exception.class,
            () -> client.newCall(request).execute(),
            "Failed to execute request");
    }
}
