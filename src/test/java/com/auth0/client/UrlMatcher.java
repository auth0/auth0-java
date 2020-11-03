package com.auth0.client;

import okhttp3.HttpUrl;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;

public class UrlMatcher extends TypeSafeDiagnosingMatcher<String> {


    private static final int BASE_URL = 0;
    private static final int QUERY_PARAMETER = 1;
    private static final int ENCODED_QUERY = 2;
    private final int checkingOption;

    private String encodedQueryContains;
    private String scheme;
    private String host;
    private String path;
    private String paramKey;
    private String paramValue;

    private UrlMatcher(String scheme, String host, String path) {
        this.checkingOption = BASE_URL;
        this.scheme = scheme;
        this.host = host;
        this.path = path;
    }

    private UrlMatcher(String paramKey, String paramValue) {
        this.checkingOption = QUERY_PARAMETER;
        this.paramKey = paramKey;
        this.paramValue = paramValue;
    }

    private UrlMatcher(String encodedQueryContains) {
        this.checkingOption = ENCODED_QUERY;
        this.encodedQueryContains = encodedQueryContains;
    }

    @Override
    protected boolean matchesSafely(String item, Description mismatchDescription) {
        if (item == null) {
            mismatchDescription.appendText("was null");
            return false;
        }
        HttpUrl url = HttpUrl.parse(item);
        if (url == null) {
            mismatchDescription.appendText("was not a valid url");
            return false;
        }

        switch (checkingOption) {
            default:
            case BASE_URL:
                return matchesBaseUrl(url, mismatchDescription);
            case QUERY_PARAMETER:
                return matchesParameter(url, mismatchDescription);
            case ENCODED_QUERY:
                return matchesEncodedQuery(url, mismatchDescription);
        }
    }

    private boolean matchesEncodedQuery(HttpUrl url, Description mismatchDescription) {
        if (!url.encodedQuery().contains(encodedQueryContains)) {
            mismatchDescription.appendText("encoded query was ").appendValue(url.encodedQuery());
            return false;
        }

        return true;
    }

    private boolean matchesBaseUrl(HttpUrl url, Description mismatchDescription) {
        if (!url.scheme().equals(scheme)) {
            mismatchDescription.appendText("scheme was ").appendValue(url.scheme());
            return false;
        }
        if (!url.host().equals(host)) {
            mismatchDescription.appendText("host was ").appendValue(url.host());
            return false;
        }
        if (path == null) {
            return true;
        }
        if (path.startsWith("/")) {
            path = path.substring(1);
        }
        if (path.endsWith("/")) {
            path = path.substring(0, path.length() - 1);
        }
        if (!url.pathSegments().equals(Arrays.asList(path.split("/")))) {
            StringBuilder sb = new StringBuilder();
            for (String p : url.pathSegments()) {
                sb.append(p).append("/");
            }
            sb.deleteCharAt(sb.length() - 1);
            mismatchDescription.appendText("path was ").appendValue(sb.toString());
            return false;
        }

        return true;
    }

    private boolean matchesParameter(HttpUrl url, Description mismatchDescription) {
        String value = url.queryParameter(paramKey);
        if (value != null && !value.equals(paramValue) || value == null && paramValue != null) {
            mismatchDescription.appendText("value was ").appendValue(value);
            return false;
        }

        return true;
    }

    @Override
    public void describeTo(Description description) {
        switch (checkingOption) {
            default:
            case BASE_URL:
                description.appendText("An url with scheme ")
                        .appendValue(scheme)
                        .appendText(", host ")
                        .appendValue(host)
                        .appendText("and path ")
                        .appendValue(path);
                break;
            case QUERY_PARAMETER:
                description.appendText("An url with the query parameter ")
                        .appendValue(paramKey)
                        .appendText(" with value ")
                        .appendValue(paramValue);
                break;
            case ENCODED_QUERY:
                description.appendText("An url with encoded query containing ")
                        .appendValue(encodedQueryContains);
                break;
        }
    }

    public static UrlMatcher isUrl(String scheme, String host, String path) {
        return new UrlMatcher(scheme, host, path);
    }

    public static UrlMatcher isUrl(String scheme, String host) {
        return new UrlMatcher(scheme, host, null);
    }

    public static UrlMatcher hasQueryParameter(String key, String value) {
        return new UrlMatcher(key, value);
    }

    public static UrlMatcher encodedQueryContains(String text) {
        return new UrlMatcher(text);
    }
}
