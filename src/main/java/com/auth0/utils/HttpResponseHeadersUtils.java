package com.auth0.utils;

import com.auth0.net.TokenQuotaLimit;
import com.auth0.net.TokenQuotaBucket;

import java.util.Map;

public class HttpResponseHeadersUtils {

    /**
     * Gets the client token quota limits from the provided headers.
     *
     * @param headers the HTTP response headers.
     * @return a TokenQuotaBucket containing client rate limits, or null if not present.
     */
    public static TokenQuotaBucket getClientQuotaLimit(Map<String, String> headers) {
        String quotaHeader = headers.get("auth0-client-quota-limit");
        if (quotaHeader != null) {
            return parseQuota(quotaHeader);
        }
        return null;
    }

    /**
     * Gets the organization token quota limits from the provided headers.
     *
     * @param headers the HTTP response headers.
     * @return a TokenQuotaBucket containing organization rate limits, or null if not present.
     */
    public static TokenQuotaBucket getOrganizationQuotaLimit(Map<String, String> headers) {
        String quotaHeader = headers.get("auth0-organization-quota-limit");
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

        if(perHour == null && perDay == null) {
            return null;
        }

        return new TokenQuotaBucket(perHour, perDay);
    }

}
