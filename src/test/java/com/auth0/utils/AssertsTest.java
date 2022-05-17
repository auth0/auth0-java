package com.auth0.utils;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AssertsTest {

    private static final String URI_NAME = "name";

    @SuppressWarnings("deprecation")
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void assertValidUri_succeedsForHttps() {
        Asserts.assertValidUri("https://me.auth0.com", URI_NAME);
    }

    @Test
    public void assertValidUri_succeedsForHttp() {
        Asserts.assertValidUri("http://me.auth0.com", URI_NAME);
    }

    @Test
    public void assertValidUri_succeedsWithPath() {
        Asserts.assertValidUri("https://me.auth0.com/path", URI_NAME);
    }

    @Test
    public void assertValidUri_succeedsWithQueryParams() {
        Asserts.assertValidUri("https://me.auth0.com?query=params&moreQuery=params", URI_NAME);
    }

    @Test
    public void assertValidUri_succeedsWithCustomDomain() {
        Asserts.assertValidUri("https://a.custom.domain", URI_NAME);
    }

    @Test
    public void assertValidUri_succeedsWithCustomAppScheme() {
        Asserts.assertValidUri("custom.app.scheme://custom.domain.com/path/callback", URI_NAME);
    }

    @Test
    public void assertValidUri_succeedsWithCustomAppSchemeWithQueryParams() {
        Asserts.assertValidUri("custom.app.scheme://custom.domain.com/path/callback?query=params&moreQuery=params", URI_NAME);
    }

    @Test
    public void assertValidUri_throwsIllegalArgumentExceptionWhenValueIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(String.format("'%s' cannot be null!", URI_NAME));
        Asserts.assertValidUri(null, URI_NAME);
    }

    @Test
    public void assertValidUri_throwsIllegalArgumentExceptionWhenValueIsInvalid() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(String.format("'%s' must be a valid URI!", URI_NAME));
        Asserts.assertValidUri("http://test/this is invalid", URI_NAME);
    }
}
