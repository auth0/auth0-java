package com.auth0.client;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class RecordedRequestMatcher extends TypeSafeDiagnosingMatcher<RecordedRequest> {

    private static final int METHOD_PATH = 0;
    private static final int HEADER = 1;
    private static final int QUERY_PARAMETER = 2;
    private static final int QUERY_PARAMETER_PRESENCE = 3;

    private final int checkingOption;
    private final String first;
    private final String second;


    private RecordedRequestMatcher(String first, String second, int checkingOption) {
        this.checkingOption = checkingOption;
        this.first = first;
        this.second = second;
    }

    @Override
    protected boolean matchesSafely(RecordedRequest item, Description mismatchDescription) {
        if (item == null) {
            mismatchDescription.appendText("was null");
            return false;
        }

        switch (checkingOption) {
            default:
            case METHOD_PATH:
                return matchesMethodAndPath(item, mismatchDescription);
            case HEADER:
                return matchesHeader(item, mismatchDescription);
            case QUERY_PARAMETER:
                return matchesQueryParameter(item, mismatchDescription);
            case QUERY_PARAMETER_PRESENCE:
                return hasQueryParameter(item, mismatchDescription);
        }
    }

    private boolean matchesMethodAndPath(RecordedRequest item, Description mismatchDescription) {
        if (!item.getMethod().equalsIgnoreCase(first)) {
            mismatchDescription.appendText("method was ").appendValue(item.getMethod());
            return false;
        }
        String path = item.getPath();
        boolean hasQuery = path.indexOf("?") > 0;
        if (hasQuery) {
            path = path.substring(0, path.indexOf("?"));
        }
        if (!path.equals(second)) {
            mismatchDescription.appendText("path was ").appendValue(path);
            return false;
        }

        return true;
    }

    private boolean matchesHeader(RecordedRequest item, Description mismatchDescription) {
        String value = item.getHeader(first);
        if (value != null && !value.equals(second) || value == null && second != null) {
            mismatchDescription.appendText(first).appendText(" header was ").appendValue(value);
            return false;
        }

        return true;
    }

    private boolean matchesQueryParameter(RecordedRequest item, Description mismatchDescription) {
        HttpUrl requestUrl = item.getRequestUrl();
        if (requestUrl.querySize() == 0) {
            mismatchDescription.appendText(" query was empty");
            return false;
        }

        String queryParamValue = requestUrl.queryParameter(first);
        if (second.equals(queryParamValue)) {
            return true;
        }

        mismatchDescription.appendValueList("Query parameters were {", ", ", "}.",
                requestUrl.query().split("&"));
        return false;
    }

    private boolean hasQueryParameter(RecordedRequest item, Description mismatchDescription) {
        String path = item.getPath();
        boolean hasQuery = path.indexOf("?") > 0;
        if (!hasQuery) {
            mismatchDescription.appendText(" query was empty");
            return false;
        }

        String query = path.substring(path.indexOf("?") + 1, path.length());
        String[] parameters = query.split("&");
        for (String p : parameters) {
            if (p.startsWith(String.format("%s=", first))) {
                return true;
            }
        }
        mismatchDescription.appendValueList("Query parameters were {", ", ", "}.", parameters);
        return false;
    }

    @Override
    public void describeTo(Description description) {
        switch (checkingOption) {
            default:
            case METHOD_PATH:
                description.appendText("A request with method ")
                        .appendValue(first)
                        .appendText(" and path ")
                        .appendValue(second);
                break;
            case HEADER:
                description.appendText("A request containing header ")
                        .appendValue(first)
                        .appendText(" with value ")
                        .appendValue(second);
                break;
            case QUERY_PARAMETER:
                description.appendText("A request containing query parameter ")
                        .appendValue(first)
                        .appendText(" with value ")
                        .appendValue(second);
                break;
            case QUERY_PARAMETER_PRESENCE:
                description.appendText("A request containing query parameter ")
                        .appendValue(first);
                break;
        }
    }

    public static RecordedRequestMatcher hasMethodAndPath(String method, String path) {
        return new RecordedRequestMatcher(method, path, METHOD_PATH);
    }

    public static RecordedRequestMatcher hasHeader(String name, String value) {
        return new RecordedRequestMatcher(name, value, HEADER);
    }

    public static RecordedRequestMatcher hasQueryParameter(String name, String value) {
        return new RecordedRequestMatcher(name, value, QUERY_PARAMETER);
    }

    public static RecordedRequestMatcher hasQueryParameter(String name) {
        return new RecordedRequestMatcher(name, null, QUERY_PARAMETER_PRESENCE);
    }
}
