package com.auth0.net;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class TelemetryInterceptor implements Interceptor {

    private Telemetry telemetry;
    private boolean enabled;

    public TelemetryInterceptor() {
        this(new Telemetry("auth0-java", "1.0"));
    }

    TelemetryInterceptor(Telemetry telemetry) {
        this.telemetry = telemetry;
        this.enabled = true;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!enabled) {
            return chain.proceed(chain.request());
        }

        okhttp3.Request request = chain.request()
                .newBuilder()
                .addHeader(Telemetry.HEADER_NAME, telemetry.getValue())
                .build();
        return chain.proceed(request);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
