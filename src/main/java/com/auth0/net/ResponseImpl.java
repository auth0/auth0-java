package com.auth0.net;

import com.auth0.json.auth.TokenQuotaLimit;

import java.util.Collections;
import java.util.Map;

class ResponseImpl<T> implements Response<T> {

    private final Map<String, String> headers;
    private final T body;
    private final int statusCode;

    ResponseImpl(Map<String, String> headers, T body, int statusCode) {
        this.headers = Collections.unmodifiableMap(headers);
        this.body = body;
        this.statusCode = statusCode;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public T getBody() {
        return body;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public TokenQuotaBucket getClientQuotaLimit() {
        String quotaHeader = headers.get("X-Quota-Client-Limit");
        if (quotaHeader != null) {
            return parseQuota(quotaHeader);
        }
        return null;
    }

    @Override
    public TokenQuotaBucket getOrganizationQuotaLimit() {
        String quotaHeader = headers.get("X-Quota-Organization-Limit");
        if (quotaHeader != null) {
            return parseQuota(quotaHeader);
        }
        return null;
    }

    public static TokenQuotaBucket parseQuota(String tokenQuota) {

        TokenQuotaLimit perHour = null;
        TokenQuotaLimit perDay = null;

        String[] parts = tokenQuota.split(",");
        for (String part : parts) {
            String[] attributes = part.split(";");
            int quota = 0, remaining = 0, time = 0;

            for (String attribute : attributes) {
                String[] keyValue = attribute.split("=");
                if (keyValue.length != 2) continue;

                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "q":
                        quota = Integer.parseInt(value);
                        break;
                    case "r":
                        remaining = Integer.parseInt(value);
                        break;
                    case "t":
                        time = Integer.parseInt(value);
                        break;
                }
            }

            if (attributes[0].contains("per_hour")) {
                perHour = new TokenQuotaLimit(quota, remaining, time);
            } else if (attributes[0].contains("per_day")) {
                perDay = new TokenQuotaLimit(quota, remaining, time);
            }
        }

        return new TokenQuotaBucket(perHour, perDay);
    }
}
