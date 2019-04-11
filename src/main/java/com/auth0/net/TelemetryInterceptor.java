package com.auth0.net;

import com.auth0.BuildConfig;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class TelemetryInterceptor implements Interceptor {

    private Telemetry telemetry;
    private boolean enabled;

    public TelemetryInterceptor() {
        this(new Telemetry(BuildConfig.NAME, BuildConfig.VERSION));
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

    public void setTelemetry(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    public Telemetry getTelemetry() {
        return this.telemetry;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
