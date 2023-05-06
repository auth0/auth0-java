package com.auth0.net.client;

import com.auth0.client.LoggingOptions;
import com.auth0.client.ProxyOptions;
import com.auth0.net.RateLimitInterceptor;
import com.auth0.net.TelemetryInterceptor;
import okhttp3.*;
import okhttp3.logging.HttpLoggingInterceptor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of {@link Auth0HttpClient} responsible for performing HTTP requests
 * to the Auth0 APIs. Instances can be configured and created using the {@link Builder}.
 * <p>
 * To minimize resource usage, instances should be created once and used in both the
 * {@link com.auth0.client.mgmt.ManagementAPI} and {@link com.auth0.client.auth.AuthAPI}
 * API clients.
 * </p>
 * <p>
 * For most use cases, usage of this client is recommended. If you have more advanced use cases,
 * such as the need to re-use an existing HTTP client, you may consider providing a custom
 * implementation of {@link Auth0HttpClient}.
 * </p>
 */
public class DefaultHttpClient implements Auth0HttpClient {

    private final okhttp3.OkHttpClient client;

    public static DefaultHttpClient.Builder newBuilder() {
        return new DefaultHttpClient.Builder();
    }

    /**
     * For testing purposes only.
     * @param client The client to inject for testing purposes.
     */
    DefaultHttpClient(OkHttpClient client) {
        this.client = client;
    }

    /**
     * For testing purposes only.
     * @return the OkHttpClient
     */
    OkHttpClient getOkClient() {
        return this.client;
    }

    private DefaultHttpClient(Builder builder) {
        okhttp3.OkHttpClient.Builder clientBuilder = new okhttp3.OkHttpClient.Builder();
        clientBuilder.readTimeout(sanitizeTimeout(builder.readTimeout), TimeUnit.SECONDS);
        clientBuilder.connectTimeout(sanitizeTimeout(builder.connectTimeout), TimeUnit.SECONDS);
        clientBuilder.addInterceptor(getLoggingInterceptor(builder.loggingOptions));
        clientBuilder.addInterceptor(getTelemetryInterceptor(builder.telemetryEnabled));
        clientBuilder.addInterceptor(getRateLimitInterceptor(builder.maxRetries));
        clientBuilder.dispatcher(getDispatcher(builder.maxRequests, builder.maxRequestsPerHost));

        configureProxy(clientBuilder, builder.proxyOptions);
        this.client = clientBuilder.build();
    }

    /**
     * Ensures that a timeout value is a number greater than zero. If not, zero will be returned.
     * @param val the timeout value
     * @return the timeout value if it is greater than zero; else zero is returned.
     */
    private int sanitizeTimeout(int val) {
        return Math.max(val, 0);
    }

    @Override
    public Auth0HttpResponse sendRequest(Auth0HttpRequest request) throws IOException {
        Request okRequest = buildRequest(request);
        try (Response response = client.newCall(okRequest).execute()) {
            return buildResponse(response);
        }
    }

    @Override
    public CompletableFuture<Auth0HttpResponse> sendRequestAsync(Auth0HttpRequest request) {
        final CompletableFuture<Auth0HttpResponse> future = new CompletableFuture<>();
        Request okRequest = buildRequest(request);

        client.newCall(okRequest).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                future.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                try {
                    future.complete(buildResponse(response));
                } catch (IOException e) {
                    future.completeExceptionally(e);
                }
            }
        });

        return future;
    }

    private Request buildRequest(Auth0HttpRequest a0Request) {
        RequestBody okBody = addBody(a0Request);

        okhttp3.Request.Builder builder = new okhttp3.Request.Builder()
            .url(a0Request.getUrl())
            .method(a0Request.getMethod().toString(), okBody);
        for (Map.Entry<String, String> e : a0Request.getHeaders().entrySet()) {
            builder.addHeader(e.getKey(), e.getValue());
        }

        return builder.build();
    }

    /**
     * Creates an {@link Auth0HttpResponse} from an OkHttp {@link Response}.
     *
     * @param okResponse the OkHttp response.
     * @return the created Auth0HttpResponse
     * @throws IOException if there is an issue reading the OkHttp response body.
     */
    private Auth0HttpResponse buildResponse(Response okResponse) throws IOException {
        Headers okHeaders = okResponse.headers();
        Map<String, String> headers = new HashMap<>();

        for (int i = 0; i < okHeaders.size(); i++) {
            headers.put(okHeaders.name(i), okHeaders.value(i));
        }

        ResponseBody responseBody = okResponse.body();
        String content = null;

        // The RateLimitInterceptor needs to close the response; we don't need the body in that case and trying to
        // get the body will result in an exception because the responsebody has been closed
        if (Objects.nonNull(responseBody) && okResponse.code() != 429) {
            content = responseBody.string();
        }
        return Auth0HttpResponse.newBuilder()
            .withStatusCode(okResponse.code())
            .withBody(content)
            .withHeaders(headers)
            .build();
    }

    @SuppressWarnings("deprecation")
    private RequestBody addBody(Auth0HttpRequest request) {
        // null body added to request results in request without body
        if (Objects.isNull(request.getBody()) ||
                HttpMethod.GET.equals(request.getMethod())) {
            return null;
        }

        HttpRequestBody body = request.getBody();
        RequestBody okBody;

        if (Objects.nonNull(body.getFormRequestBody())) {
            Auth0FormRequestBody formData = body.getFormRequestBody();
            FormBody.Builder builder = new FormBody.Builder();
            for (Map.Entry<String, Object> entry : formData.getParams().entrySet()) {
                Object val = entry.getValue();
                builder.add(entry.getKey(), val instanceof String ? (String) val : val.toString());
            }
            okBody = builder.build();
        }
        else if (Objects.nonNull(body.getMultipartRequestBody())) {
            Auth0MultipartRequestBody multipartRequestBody = body.getMultipartRequestBody();
            MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
            if (Objects.nonNull(multipartRequestBody.getFilePart())) {
                bodyBuilder.addFormDataPart(multipartRequestBody.getFilePart().getPartName(),
                    multipartRequestBody.getFilePart().getFile().getName(),
                    RequestBody.create(MediaType.parse(multipartRequestBody.getFilePart().getMediaType()), multipartRequestBody.getFilePart().getFile()));
            }
            multipartRequestBody.getParts().forEach(bodyBuilder::addFormDataPart);
            okBody = bodyBuilder.build();
        } else {
            okBody = RequestBody.create(MediaType.parse(request.getBody().getContentType()), request.getBody().getContent());
        }

        return okBody;
    }

    private HttpLoggingInterceptor getLoggingInterceptor(LoggingOptions loggingOptions) {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        if (Objects.isNull(loggingOptions)) {
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
            return loggingInterceptor;
        }

        switch (loggingOptions.getLogLevel()) {
            case BASIC:
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                break;
            case HEADERS:
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
                break;
            case BODY:
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                break;
            case NONE:
            default:
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        for (String header : loggingOptions.getHeadersToRedact()) {
            loggingInterceptor.redactHeader(header);
        }
        return loggingInterceptor;
    }

    private void configureProxy(okhttp3.OkHttpClient.Builder clientBuilder, ProxyOptions proxyOptions) {
        if (Objects.nonNull(proxyOptions)) {
            //Set proxy
            clientBuilder.proxy(proxyOptions.getProxy());
            //Set authentication, if present
            final String proxyAuth = proxyOptions.getBasicAuthentication();
            if (Objects.nonNull(proxyAuth)) {
                clientBuilder.proxyAuthenticator(new Authenticator() {

                    private static final String PROXY_AUTHORIZATION_HEADER = "Proxy-Authorization";

                    @Override
                    public okhttp3.Request authenticate(Route route, @NotNull Response response) {
                        if (Objects.nonNull(response.request().header(PROXY_AUTHORIZATION_HEADER))) {
                            return null;
                        }
                        return response.request().newBuilder()
                            .header(PROXY_AUTHORIZATION_HEADER, proxyAuth)
                            .build();
                    }
                });
            }
        }
    }

    private TelemetryInterceptor getTelemetryInterceptor(boolean telemetryEnabled) {
        TelemetryInterceptor interceptor = new TelemetryInterceptor();
        interceptor.setEnabled(telemetryEnabled);
        return interceptor;
    }

    private RateLimitInterceptor getRateLimitInterceptor(int maxRetries) {
        if (maxRetries < 0 || maxRetries > 10) {
            throw new IllegalArgumentException("Retries must be between zero and ten.");
        }
        return new RateLimitInterceptor(maxRetries);
    }

    private Dispatcher getDispatcher(int maxRequests, int maxRequestsPerHost) {
        if (maxRequests < 1) {
            throw new IllegalArgumentException("maxRequests must be one or greater.");
        }
        if (maxRequestsPerHost < 1) {
            throw new IllegalArgumentException("maxRequestsPerHost must be one or greater.");
        }

        Dispatcher dispatcher = new Dispatcher();
        dispatcher.setMaxRequests(maxRequests);
        dispatcher.setMaxRequestsPerHost(maxRequestsPerHost);
        return dispatcher;
    }

    /**
     * Builder for {@link DefaultHttpClient} instances.
     */
    public static class Builder {
        private int readTimeout = 10;
        private int connectTimeout = 10;
        private ProxyOptions proxyOptions;
        private LoggingOptions loggingOptions;
        private boolean telemetryEnabled = true;
        private int maxRetries = 3;
        private int maxRequests = 64;
        private int maxRequestsPerHost = 5;

        /**
         * Sets the value of the read timeout, in seconds. Defaults to ten seconds. A value of zero results in no read timeout.
         * Negative numbers will be treated as zero.
         *
         * @param readTimeout the value of the read timeout to use.
         * @return this builder instance.
         */
        public Builder withReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
            return this;
        }

        /**
         * Sets the value of the connect timeout, in seconds. Defaults to ten seconds. A value of zero results in no connect timeout.
         * Negative numbers will be treated as zero.
         * @param connectTimeout the value of the connect timeout to use.
         * @return this builder instance.
         */
        public Builder withConnectTimeout(int connectTimeout) {
            this.connectTimeout = connectTimeout;
            return this;
        }

        /**
         * Set the HTTP logging configuration options. If not set, no logs will be captured.
         * @param loggingOptions the Logging configuration options.
         * @return this builder instance.
         */
        public Builder withLogging(LoggingOptions loggingOptions) {
            this.loggingOptions = loggingOptions;
            return this;
        }

        /**
         * Configure this client for use with a proxy.
         *
         * @param proxyOptions the Proxy configuration options
         * @return this builder instance.
         */
        public Builder withProxy(ProxyOptions proxyOptions) {
            this.proxyOptions = proxyOptions;
            return this;
        }

        /**
         * Configure this client to enable or disable sending telemetry data to Auth0 servers (on by default).
         * @param telemetryEnabled true send telemetry data, false to not send.
         * @return this builder instance.
         */
        public Builder telemetryEnabled(boolean telemetryEnabled) {
            this.telemetryEnabled = telemetryEnabled;
            return this;
        }

        /**
         * Sets the maximum number of consecutive retries for API requests that fail due to rate-limits being reached.
         * By default, rate-limited requests will be retried a maximum of three times. To disable retries on rate-limit
         * errors, set this value to zero.
         *
         * @param maxRetries the maximum number of consecutive retries to attempt upon a rate-limit error. Defaults to three.
         *                   Must be a number between zero (do not retry) and ten.
         *
         * @return this builder instance.
         */
        public Builder withMaxRetries(int maxRetries) {
            this.maxRetries = maxRetries;
            return this;
        }

        /**
         * Sets the maximum number of requests to execute concurrently.
         *
         * @param maxRequests the number of requests to execute concurrently. Must be equal to or greater than one.
         * @return this builder instance.
         */
        public Builder withMaxRequests(int maxRequests) {
            this.maxRequests = maxRequests;
            return this;
        }

        /**
         * Sets the maximum number of requests for each host to execute concurrently.
         *
         * @param maxRequestsPerHost the maximum number of requests for each host to execute concurrently. Must be equal to or greater than one.
         * @return this builder instance.
         */
        public Builder withMaxRequestsPerHost(int maxRequestsPerHost) {
            this.maxRequestsPerHost = maxRequestsPerHost;
            return this;
        }

        /**
         * Create a {@code DefaultHttpClient} from this configured builder.
         * @return the created {@code DefaultHttpClient}.
         */
        public DefaultHttpClient build() {
            return new DefaultHttpClient(this);
        }
    }

}
