package com.auth0.utils;

import okhttp3.HttpUrl;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertsTest {

    private static final String URI_NAME = "name";

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void succeedsForHttps() {
        Asserts.assertValidUrl("https://me.auth0.com", URI_NAME);
    }

    @Test
    public void succeedsForHttp() {
        Asserts.assertValidUrl("http://me.auth0.com", URI_NAME);
    }

    @Test
    public void succeedsWithPath() {
        Asserts.assertValidUrl("https://me.auth0.com/path", URI_NAME);
    }

    @Test
    public void succeedsWithQueryParams() {
        Asserts.assertValidUrl("https://me.auth0.com?query=params&moreQuery=params", URI_NAME);
    }

    @Test
    public void succeedsWithCustomDomain() {
        Asserts.assertValidUrl("https://a.custom.domain", URI_NAME);
    }

    @Test
    public void succeedsWithCustomAppScheme() {
        Asserts.assertValidUrl("custom.app.scheme://custom.domain.com/path/callback", URI_NAME);
    }

    @Test
    public void succeedsWithCustomAppSchemeWithQueryParams() {
        Asserts.assertValidUrl("custom.app.scheme://custom.domain.com/path/callback?query=params&moreQuery=params", URI_NAME);
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenValueIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(String.format("'%s' must be a valid URL!", URI_NAME));
        Asserts.assertValidUrl(null, URI_NAME);
    }

    @Test
    public void throwsIllegalArgumentExceptionWhenValueIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(String.format("'%s' must be a valid URL!", URI_NAME));
        Asserts.assertValidUrl("http://test/this is invalid", URI_NAME);
    }
}
