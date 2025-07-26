package com.auth0.utils;

import com.auth0.net.TokenQuotaBucket;
import com.auth0.net.TokenQuotaLimit;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNull;

public class HttpResponseHeadersUtilsTest {

    @Test
    public void testGetClientQuotaLimitWithValidHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("auth0-client-quota-limit", "per_hour;q=100;r=50;t=3600,per_day;q=1000;r=500;t=86400");

        TokenQuotaBucket quotaBucket = HttpResponseHeadersUtils.getClientQuotaLimit(headers);

        assertNotNull(quotaBucket);
        assertNotNull(quotaBucket.getPerHour());
        assertEquals(100, quotaBucket.getPerHour().getQuota());
        assertEquals(50, quotaBucket.getPerHour().getRemaining());
        assertEquals(3600, quotaBucket.getPerHour().getResetAfter());

        assertNotNull(quotaBucket.getPerDay());
        assertEquals(1000, quotaBucket.getPerDay().getQuota());
        assertEquals(500, quotaBucket.getPerDay().getRemaining());
        assertEquals(86400, quotaBucket.getPerDay().getResetAfter());
    }

    @Test
    public void testGetClientQuotaLimitWithMissingHeader() {
        Map<String, String> headers = new HashMap<>();

        TokenQuotaBucket quotaBucket = HttpResponseHeadersUtils.getClientQuotaLimit(headers);

        assertNull(quotaBucket);
    }

    @Test
    public void testGetClientQuotaLimitWithOneBucketHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("auth0-client-quota-limit", "per_hour;q=200;r=100;t=3600");

        TokenQuotaBucket quotaBucket = HttpResponseHeadersUtils.getClientQuotaLimit(headers);

        assertNotNull(quotaBucket);
        assertNotNull(quotaBucket.getPerHour());
        assertEquals(200, quotaBucket.getPerHour().getQuota());
        assertEquals(100, quotaBucket.getPerHour().getRemaining());
        assertEquals(3600, quotaBucket.getPerHour().getResetAfter());

        assertNull(quotaBucket.getPerDay());
    }

    @Test
    public void testGetOrganizationQuotaLimitWithValidHeader() {
        Map<String, String> headers = new HashMap<>();
        headers.put("auth0-organization-quota-limit", "per_hour;q=200;r=100;t=3600");

        TokenQuotaBucket quotaBucket = HttpResponseHeadersUtils.getOrganizationQuotaLimit(headers);

        assertNotNull(quotaBucket);
        assertNotNull(quotaBucket.getPerHour());
        assertEquals(200, quotaBucket.getPerHour().getQuota());
        assertEquals(100, quotaBucket.getPerHour().getRemaining());
        assertEquals(3600, quotaBucket.getPerHour().getResetAfter());

        assertNull(quotaBucket.getPerDay());
    }

    @Test
    public void testGetOrganizationQuotaLimitWithMissingHeader() {
        Map<String, String> headers = new HashMap<>();

        TokenQuotaBucket quotaBucket = HttpResponseHeadersUtils.getOrganizationQuotaLimit(headers);

        assertNull(quotaBucket);
    }

    @Test
    public void testParseQuotaWithValidInput() {
        String quotaHeader = "per_hour;q=300;r=150;t=3600,per_day;q=3000;r=1500;t=86400";

        TokenQuotaBucket quotaBucket = HttpResponseHeadersUtils.parseQuota(quotaHeader);

        assertNotNull(quotaBucket);
        assertNotNull(quotaBucket.getPerHour());
        assertEquals(300, quotaBucket.getPerHour().getQuota());
        assertEquals(150, quotaBucket.getPerHour().getRemaining());
        assertEquals(3600, quotaBucket.getPerHour().getResetAfter());

        assertNotNull(quotaBucket.getPerDay());
        assertEquals(3000, quotaBucket.getPerDay().getQuota());
        assertEquals(1500, quotaBucket.getPerDay().getRemaining());
        assertEquals(86400, quotaBucket.getPerDay().getResetAfter());
    }

    @Test
    public void testParseQuotaWithInvalidInput() {
        String quotaHeader = "invalid_format";

        TokenQuotaBucket quotaBucket = HttpResponseHeadersUtils.parseQuota(quotaHeader);

        assertNull(quotaBucket);
    }

    @Test
    public void testParseQuotaWithEmptyInput() {
        String quotaHeader = "";

        TokenQuotaBucket quotaBucket = HttpResponseHeadersUtils.parseQuota(quotaHeader);

        assertNull(quotaBucket);
    }
}
