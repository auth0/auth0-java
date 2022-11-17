package com.auth0.net.client;

import com.auth0.client.LoggingOptions;
import com.auth0.client.ProxyOptions;
import com.auth0.net.RateLimitInterceptor;
import com.auth0.net.TelemetryInterceptor;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okhttp3.mockwebserver.SocketPolicy;
import okio.Buffer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doAnswer;

public class DefaultHttpClientTest {

    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        server = new MockWebServer();
        server.start();
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

    @Test
    public void makesGetRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.GET)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        String responseBody = "{\"id\":\"123\", \"name\":\"timmy\"}";
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(200)
            .setHeader("fiz", "bang")
            .setBody(responseBody);

        server.enqueue(mockResponse);

        Auth0HttpResponse response = client.sendRequest(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("GET"));

        assertThat(response.getCode(), is(200));
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.getBody(), is(responseBody));
        assertThat(response.getHeader("fiz"), is("bang"));
    }

    @Test
    public void makesAsyncGetRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.GET)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        String responseBody = "{\"id\":\"123\", \"name\":\"timmy\"}";
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(200)
            .setHeader("fiz", "bang")
            .setBody(responseBody);

        server.enqueue(mockResponse);

        CompletableFuture<Auth0HttpResponse> response = client.sendRequestAsync(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("GET"));

        assertThat(response.get().getCode(), is(200));
        assertThat(response.get().isSuccessful(), is(true));
        assertThat(response.get().getBody(), is(responseBody));
        assertThat(response.get().getHeader("fiz"), is("bang"));
    }


    @Test
    public void makesPostRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.POST)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .withBody(HttpRequestBody.create(
                "application/json",
                "{\"id\":\"123\", \"name\":\"timmy\"}".getBytes(StandardCharsets.UTF_8)))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        String responseBody = "{\"id\":\"123\", \"name\":\"timmy\"}";
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(201)
            .setHeader("fiz", "bang")
            .setBody(responseBody);

        server.enqueue(mockResponse);

        Auth0HttpResponse response = client.sendRequest(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("POST"));

        assertThat(response.getCode(), is(201));
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.getBody(), is(responseBody));
        assertThat(response.getHeader("fiz"), is("bang"));
    }

    @Test
    public void makesAsyncPostRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.POST)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .withBody(HttpRequestBody.create(
                "application/json",
                "{\"id\":\"123\", \"name\":\"timmy\"}".getBytes(StandardCharsets.UTF_8)))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        String responseBody = "{\"id\":\"123\", \"name\":\"timmy\"}";
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(201)
            .setHeader("fiz", "bang")
            .setBody(responseBody);

        server.enqueue(mockResponse);

        CompletableFuture<Auth0HttpResponse> response = client.sendRequestAsync(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("POST"));

        assertThat(response.get().getCode(), is(201));
        assertThat(response.get().isSuccessful(), is(true));
        assertThat(response.get().getBody(), is(responseBody));
        assertThat(response.get().getHeader("fiz"), is("bang"));
    }

    @Test
    public void makesPatchRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.PATCH)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .withBody(HttpRequestBody.create(
                "application/json",
                "{\"id\":\"123\", \"name\":\"timmy\"}".getBytes(StandardCharsets.UTF_8)))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(204)
            .setHeader("fiz", "bang");

        server.enqueue(mockResponse);

        Auth0HttpResponse response = client.sendRequest(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("PATCH"));

        assertThat(response.getCode(), is(204));
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.getHeader("fiz"), is("bang"));
    }

    @Test
    public void makesAsyncPatchRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.PATCH)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .withBody(HttpRequestBody.create(
                "application/json",
                "{\"id\":\"123\", \"name\":\"timmy\"}".getBytes(StandardCharsets.UTF_8)))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(204)
            .setHeader("fiz", "bang");

        server.enqueue(mockResponse);

        CompletableFuture<Auth0HttpResponse> response = client.sendRequestAsync(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("PATCH"));

        assertThat(response.get().getCode(), is(204));
        assertThat(response.get().isSuccessful(), is(true));
        assertThat(response.get().getHeader("fiz"), is("bang"));
    }

    @Test
    public void makesPutRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.PUT)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .withBody(HttpRequestBody.create(
                "application/json",
                "{\"id\":\"123\", \"name\":\"timmy\"}".getBytes(StandardCharsets.UTF_8)))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(204)
            .setHeader("fiz", "bang");

        server.enqueue(mockResponse);

        Auth0HttpResponse response = client.sendRequest(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("PUT"));

        assertThat(response.getCode(), is(204));
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.getHeader("fiz"), is("bang"));
    }

    @Test
    public void makesAsyncPutRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.PUT)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .withBody(HttpRequestBody.create(
                "application/json",
                "{\"id\":\"123\", \"name\":\"timmy\"}".getBytes(StandardCharsets.UTF_8)))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(204)
            .setHeader("fiz", "bang");

        server.enqueue(mockResponse);

        CompletableFuture<Auth0HttpResponse> response = client.sendRequestAsync(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("PUT"));

        assertThat(response.get().getCode(), is(204));
        assertThat(response.get().isSuccessful(), is(true));
        assertThat(response.get().getHeader("fiz"), is("bang"));
    }

    @Test
    public void makesDeleteRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.DELETE)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        String responseBody = "{\"id\":\"123\", \"name\":\"timmy\"}";
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(202)
            .setHeader("fiz", "bang")
            .setBody(responseBody);

        server.enqueue(mockResponse);

        Auth0HttpResponse response = client.sendRequest(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("DELETE"));

        assertThat(response.getCode(), is(202));
        assertThat(response.isSuccessful(), is(true));
        assertThat(response.getBody(), is(responseBody));
        assertThat(response.getHeader("fiz"), is("bang"));
    }

    @Test
    public void makesAsyncDeleteRequest() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.DELETE)
            .withHeaders(Collections.singletonMap("foo", "bar"))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        String responseBody = "{\"id\":\"123\", \"name\":\"timmy\"}";
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(202)
            .setHeader("fiz", "bang")
            .setBody(responseBody);

        server.enqueue(mockResponse);

        CompletableFuture<Auth0HttpResponse> response = client.sendRequestAsync(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getHeader("foo"), is("bar"));
        assertThat(madeRequest.getMethod(), is("DELETE"));

        assertThat(response.get().getCode(), is(202));
        assertThat(response.get().isSuccessful(), is(true));
        assertThat(response.get().getBody(), is(responseBody));
        assertThat(response.get().getHeader("fiz"), is("bang"));
    }

    @Test
    public void handlesAPIError() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.GET)
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(404)
            .setBody("User not found");

        server.enqueue(mockResponse);

        Auth0HttpResponse response = client.sendRequest(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getMethod(), is("GET"));

        assertThat(response.getCode(), is(404));
        assertThat(response.isSuccessful(), is(false));
        assertThat(response.getBody(), is("User not found"));
    }

    @Test
    public void handlesAPIErrorAsync() throws Exception {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.GET)
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(404)
            .setBody("User not found");

        server.enqueue(mockResponse);

        CompletableFuture<Auth0HttpResponse> response = client.sendRequestAsync(request);
        RecordedRequest madeRequest = server.takeRequest();

        assertThat(madeRequest.getPath(), is("/users/2"));
        assertThat(madeRequest.getMethod(), is("GET"));

        assertThat(response.get().getCode(), is(404));
        assertThat(response.get().isSuccessful(), is(false));
        assertThat(response.get().getBody(), is("User not found"));
    }

    @Test
    public void throwsIOException() {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.GET)
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        // force IOException to be thrown by OkHttp when trying to parse response body
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(200)
            .setBody(new Buffer().write(new byte[2056]))
            .setSocketPolicy(SocketPolicy.DISCONNECT_DURING_RESPONSE_BODY);

        server.enqueue(mockResponse);

        assertThrows(IOException.class, () -> client.sendRequest(request));
    }

    @Test
    public void throwsIOExceptionAsync() {
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/2").toString(), HttpMethod.GET)
            .build();

        DefaultHttpClient client = new DefaultHttpClient(new OkHttpClient());
        // force IOException to be thrown by OkHttp when trying to parse response body
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(200)
            .setBody(new Buffer().write(new byte[2056]))
            .setSocketPolicy(SocketPolicy.DISCONNECT_DURING_RESPONSE_BODY);

        server.enqueue(mockResponse);

        CompletableFuture<Auth0HttpResponse> future = client.sendRequestAsync(request);
        ExecutionException e = assertThrows(ExecutionException.class, future::get);
        assertThat(e.getCause(), is(instanceOf(IOException.class)));
    }

    @Test
    public void asyncHandlesNetworkError() {
        OkHttpClient okClient = Mockito.mock(OkHttpClient.class);
        okhttp3.Response response = Mockito.mock(okhttp3.Response.class);
        Call call = Mockito.mock(Call.class);

        doReturn(call).when(okClient).newCall(any());

        doAnswer(invocation -> {
            ((Callback) invocation.getArgument(0)).onFailure(call, new IOException("Error!"));
            return null;
        }).when(call).enqueue(any());

        DefaultHttpClient client = new DefaultHttpClient(okClient);
        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/").toString(), HttpMethod.POST)
            .withBody(HttpRequestBody.create("application/json", "{}".getBytes()))
            .build();

        CompletableFuture<Auth0HttpResponse> future = client.sendRequestAsync(request);
        ExecutionException e = assertThrows(ExecutionException.class, future::get);
        assertThat(e.getCause(), is(instanceOf(IOException.class)));
    }

    @Test
    public void alwaysCloseResponseOnSuccessfulRequest() throws IOException {
        okhttp3.Response response = Mockito.mock(okhttp3.Response.class);

        Call call = Mockito.mock(Call.class);
        when(call.execute()).thenReturn(response);

        Headers headers = Mockito.mock(Headers.class);
        when(response.headers()).thenReturn(headers);

        OkHttpClient client = Mockito.mock(OkHttpClient.class);
        when(client.newCall(any())).thenReturn(call);

        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/").toString(), HttpMethod.POST)
            .withBody(HttpRequestBody.create("application/json", "{}".getBytes()))
            .build();

        DefaultHttpClient auth0Client = new DefaultHttpClient(client);
        auth0Client.sendRequest(request);

        verify(response, times(1)).close();
    }

    @Test
    public void closesResponseOnAPIError() throws Exception {
        okhttp3.Response response = Mockito.mock(okhttp3.Response.class);

        Call call = Mockito.mock(Call.class);
        when(call.execute()).thenReturn(response);

        Headers headers = Mockito.mock(Headers.class);
        when(response.headers()).thenReturn(headers);

        OkHttpClient okClient = Mockito.mock(OkHttpClient.class);
        when(okClient.newCall(any())).thenReturn(call);

        Auth0HttpRequest request = Auth0HttpRequest.newBuilder(server.url("/users/").toString(), HttpMethod.POST)
            .withBody(HttpRequestBody.create("application/json", "{}".getBytes()))
            .build();

        DefaultHttpClient client = new DefaultHttpClient(okClient);
        MockResponse mockResponse = new MockResponse()
            .setResponseCode(500)
            .setBody("server error");

        server.enqueue(mockResponse);

        Auth0HttpResponse aoResponse = client.sendRequest(request);
        verify(response, times(1)).close();
    }

    //////////////////////////////////////////////////////////
    //          HTTP CLIENT CONFIGURATION TESTS
    //////////////////////////////////////////////////////////

    @Test
    public void shouldUseDefaultTimeoutIfNotSpecified() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder().build();
        assertThat(client.getOkClient().connectTimeoutMillis(), is(10 * 1000));
        assertThat(client.getOkClient().readTimeoutMillis(), is(10 * 1000));
    }

    @Test
    public void shouldUseZeroIfNegativeTimeoutConfigured() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withConnectTimeout(-1)
            .withReadTimeout(-1)
            .build();

        assertThat(client.getOkClient().connectTimeoutMillis(), is(0));
        assertThat(client.getOkClient().readTimeoutMillis(), is(0));

    }

    @Test
    public void shouldSetTimeoutsIfConfigured() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withConnectTimeout(20)
            .withReadTimeout(30)
            .build();

        assertThat(client.getOkClient().connectTimeoutMillis(), is(20 * 1000));
        assertThat(client.getOkClient().readTimeoutMillis(), is(30 * 1000));
    }


    @Test
    public void shouldNotUseProxyByDefault() throws Exception {
        DefaultHttpClient client = DefaultHttpClient.newBuilder().build();
        assertThat(client.getOkClient().proxy(), is(nullValue()));
        Authenticator authenticator = client.getOkClient().proxyAuthenticator();
        assertThat(authenticator, is(notNullValue()));

        Route route = Mockito.mock(Route.class);
        okhttp3.Request nonAuthenticatedRequest = new okhttp3.Request.Builder()
                .url("https://test.com/app")
                .addHeader("some-header", "some-value")
                .build();
        okhttp3.Response nonAuthenticatedResponse = new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .request(nonAuthenticatedRequest)
                .build();

        okhttp3.Request processedRequest = authenticator.authenticate(route, nonAuthenticatedResponse);
        assertThat(processedRequest, is(nullValue()));
    }

    @Test
    public void shouldUseProxy() throws Exception {
        Proxy proxy = Mockito.mock(Proxy.class);
        ProxyOptions proxyOptions = new ProxyOptions(proxy);

        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withProxy(proxyOptions)
            .build();

        assertThat(client.getOkClient().proxy(), is(proxy));
        Authenticator authenticator = client.getOkClient().proxyAuthenticator();
        assertThat(authenticator, is(notNullValue()));

        Route route = Mockito.mock(Route.class);
        okhttp3.Request nonAuthenticatedRequest = new okhttp3.Request.Builder()
                .url("https://test.com/app")
                .addHeader("some-header", "some-value")
                .build();
        okhttp3.Response nonAuthenticatedResponse = new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .request(nonAuthenticatedRequest)
                .build();

        okhttp3.Request processedRequest = authenticator.authenticate(route, nonAuthenticatedResponse);

        assertThat(processedRequest, is(nullValue()));
    }

    @Test
    public void shouldUseProxyWithAuthentication() throws Exception {
        Proxy proxy = Mockito.mock(Proxy.class);
        ProxyOptions proxyOptions = new ProxyOptions(proxy);
        proxyOptions.setBasicAuthentication("johndoe", "psswd".toCharArray());
        assertThat(proxyOptions.getBasicAuthentication(), is("Basic am9obmRvZTpwc3N3ZA=="));

        DefaultHttpClient client = DefaultHttpClient.newBuilder()
                .withProxy(proxyOptions)
                .build();

        assertThat(client.getOkClient().proxy(), is(proxy));
        Authenticator authenticator = client.getOkClient().proxyAuthenticator();
        assertThat(authenticator, is(notNullValue()));

        Route route = Mockito.mock(Route.class);
        okhttp3.Request nonAuthenticatedRequest = new okhttp3.Request.Builder()
                .url("https://test.com/app")
                .addHeader("some-header", "some-value")
                .build();
        okhttp3.Response nonAuthenticatedResponse = new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .request(nonAuthenticatedRequest)
                .build();

        okhttp3.Request processedRequest = authenticator.authenticate(route, nonAuthenticatedResponse);

        assertThat(processedRequest, is(notNullValue()));
        assertThat(processedRequest.url(), is(HttpUrl.parse("https://test.com/app")));
        assertThat(processedRequest.header("Proxy-Authorization"), is(proxyOptions.getBasicAuthentication()));
        assertThat(processedRequest.header("some-header"), is("some-value"));
    }

    @Test
    public void proxyShouldNotProcessAlreadyAuthenticatedRequest() throws Exception {
        Proxy proxy = Mockito.mock(Proxy.class);
        ProxyOptions proxyOptions = new ProxyOptions(proxy);
        proxyOptions.setBasicAuthentication("johndoe", "psswd".toCharArray());
        assertThat(proxyOptions.getBasicAuthentication(), is("Basic am9obmRvZTpwc3N3ZA=="));

        DefaultHttpClient client = DefaultHttpClient.newBuilder()
                .withProxy(proxyOptions)
                .build();

        assertThat(client.getOkClient().proxy(), is(proxy));
        Authenticator authenticator = client.getOkClient().proxyAuthenticator();
        assertThat(authenticator, is(notNullValue()));

        Route route = Mockito.mock(Route.class);
        okhttp3.Request alreadyAuthenticatedRequest = new okhttp3.Request.Builder()
                .url("https://test.com/app")
                .addHeader("some-header", "some-value")
                .header("Proxy-Authorization", "pre-existing-value")
                .build();
        okhttp3.Response alreadyAuthenticatedResponse = new okhttp3.Response.Builder()
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("OK")
                .request(alreadyAuthenticatedRequest)
                .build();

        okhttp3.Request processedRequest = authenticator.authenticate(route, alreadyAuthenticatedResponse);
        assertThat(processedRequest, is(nullValue()));
    }

    @Test
    public void shouldAddAndEnableTelemetryInterceptor() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder().build();
        assertThat(client.getOkClient().interceptors(), hasItem(isA(TelemetryInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof TelemetryInterceptor) {
                TelemetryInterceptor telemetry = (TelemetryInterceptor) i;
                assertThat(telemetry.isEnabled(), is(true));
            }
        }
    }

    @Test
    public void shouldDisableTelemetryInterceptor() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .telemetryEnabled(false)
            .build();

        assertThat(client.getOkClient().interceptors(), hasItem(isA(TelemetryInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof TelemetryInterceptor) {
                TelemetryInterceptor telemetry = (TelemetryInterceptor) i;
                assertThat(telemetry.isEnabled(), is(false));
            }
        }
    }

    @Test
    public void shouldAddAndDisableLoggingInterceptor() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder().build();
        assertThat(client.getOkClient().interceptors(), hasItem(isA(HttpLoggingInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof HttpLoggingInterceptor) {
                HttpLoggingInterceptor logging = (HttpLoggingInterceptor) i;
                assertThat(logging.getLevel(), is(HttpLoggingInterceptor.Level.NONE));
            }
        }
    }

    @Test
    public void shouldConfigureNoneLoggingFromOptions() {
        LoggingOptions loggingOptions = new LoggingOptions(LoggingOptions.LogLevel.NONE);
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
                .withLogging(loggingOptions)
                .build();

        assertThat(client.getOkClient().interceptors(), hasItem(isA(HttpLoggingInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof HttpLoggingInterceptor) {
                HttpLoggingInterceptor logging = (HttpLoggingInterceptor) i;
                assertThat(logging.getLevel(), is(HttpLoggingInterceptor.Level.NONE));
            }
        }
    }

    @Test
    public void shouldConfigureBasicLoggingFromOptions() {
        LoggingOptions loggingOptions = new LoggingOptions(LoggingOptions.LogLevel.BASIC);
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withLogging(loggingOptions)
            .build();

        assertThat(client.getOkClient().interceptors(), hasItem(isA(HttpLoggingInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof HttpLoggingInterceptor) {
                HttpLoggingInterceptor logging = (HttpLoggingInterceptor) i;
                assertThat(logging.getLevel(), is(HttpLoggingInterceptor.Level.BASIC));
            }
        }
    }

    @Test
    public void shouldConfigureHeaderLoggingFromOptions() {
        LoggingOptions loggingOptions = new LoggingOptions(LoggingOptions.LogLevel.HEADERS);
        Set<String> headersToRedact = new HashSet<>();
        headersToRedact.add("Authorization");
        headersToRedact.add("Cookie");
        loggingOptions.setHeadersToRedact(headersToRedact);

        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withLogging(loggingOptions)
            .build();

        assertThat(client.getOkClient().interceptors(), hasItem(isA(HttpLoggingInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof HttpLoggingInterceptor) {
                HttpLoggingInterceptor logging = (HttpLoggingInterceptor) i;
                assertThat(logging.getLevel(), is(HttpLoggingInterceptor.Level.HEADERS));
            }
        }
    }

    @Test
    public void shouldConfigureBodyLoggingFromOptions() {
        LoggingOptions loggingOptions = new LoggingOptions(LoggingOptions.LogLevel.BODY);
        Set<String> headersToRedact = new HashSet<>();
        headersToRedact.add("Authorization");
        headersToRedact.add("Cookie");
        loggingOptions.setHeadersToRedact(headersToRedact);

        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withLogging(loggingOptions)
            .build();

        assertThat(client.getOkClient().interceptors(), hasItem(isA(HttpLoggingInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof HttpLoggingInterceptor) {
                HttpLoggingInterceptor logging = (HttpLoggingInterceptor) i;
                assertThat(logging.getLevel(), is(HttpLoggingInterceptor.Level.BODY));
            }
        }
    }

    @Test
    public void shouldUseDefaultMaxRetriesIfNotConfigured() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .build();

        assertThat(client.getOkClient().interceptors(), hasItem(isA(RateLimitInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof RateLimitInterceptor) {
                RateLimitInterceptor rateLimitInterceptor = (RateLimitInterceptor) i;
                assertThat(rateLimitInterceptor.getMaxRetries(), is(3));
            }
        }
    }

    @Test
    public void shouldUseCustomMaxRetriesIfConfigured() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withMaxRetries(5)
            .build();

        assertThat(client.getOkClient().interceptors(), hasItem(isA(RateLimitInterceptor.class)));

        for (Interceptor i : client.getOkClient().interceptors()) {
            if (i instanceof RateLimitInterceptor) {
                RateLimitInterceptor rateLimitInterceptor = (RateLimitInterceptor) i;
                assertThat(rateLimitInterceptor.getMaxRetries(), is(5));
            }
        }
    }

    @Test
    public void shouldThrowOnNegativeMaxRetriesConfiguration() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () ->
            DefaultHttpClient.newBuilder().withMaxRetries(-1).build()
        );
        assertThat(iae.getMessage(), is("Retries must be between zero and ten."));
    }

    @Test
    public void shouldThrowOnTooManyMaxRetriesConfiguration() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () ->
            DefaultHttpClient.newBuilder().withMaxRetries(11).build()
        );
        assertThat(iae.getMessage(), is("Retries must be between zero and ten."));
    }

    @Test
    public void shouldUseDefaultMaxRequests() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder().build();
        assertThat(client.getOkClient().dispatcher().getMaxRequests(), is(64));
    }

    @Test
    public void shouldUseConfiguredMaxRequests() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withMaxRequests(10)
            .build();

        assertThat(client.getOkClient().dispatcher().getMaxRequests(), is(10));
    }

    @Test
    public void shouldThrowOnInValidMaxRequestsConfiguration() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () -> DefaultHttpClient.newBuilder().withMaxRequests(0).build());
        assertThat(iae.getMessage(), is("maxRequests must be one or greater."));
    }

    @Test
    public void shouldUseDefaultMaxRequestsPerHost() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder().build();
        assertThat(client.getOkClient().dispatcher().getMaxRequestsPerHost(), is(5));
    }

    @Test
    public void shouldUseConfiguredMaxRequestsPerHost() {
        DefaultHttpClient client = DefaultHttpClient.newBuilder()
            .withMaxRequestsPerHost(10)
            .build();
        assertThat(client.getOkClient().dispatcher().getMaxRequestsPerHost(), is(10));
    }

    @Test
    public void shouldThrowOnInValidMaxRequestsPerHostConfiguration() {
        IllegalArgumentException iae = assertThrows(IllegalArgumentException.class, () ->
            DefaultHttpClient.newBuilder().withMaxRequestsPerHost(0).build());
        assertThat(iae.getMessage(), is("maxRequestsPerHost must be one or greater."));
    }

}
