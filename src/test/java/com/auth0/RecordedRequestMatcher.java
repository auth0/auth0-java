package com.auth0;

import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class RecordedRequestMatcher extends TypeSafeDiagnosingMatcher<RecordedRequest> {

    private static final int METHOD_PATH = 0;
    private static final int HEADER = 1;

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
        }
    }

    private boolean matchesMethodAndPath(RecordedRequest item, Description mismatchDescription) {
        if (!item.getMethod().equalsIgnoreCase(first)) {
            mismatchDescription.appendText("method was ").appendValue(item.getMethod());
            return false;
        }
        if (!item.getPath().equals(second)) {
            mismatchDescription.appendText("path was ").appendValue(item.getPath());
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
        }
    }

    public static RecordedRequestMatcher hasMethodAndPath(String method, String path) {
        return new RecordedRequestMatcher(method, path, METHOD_PATH);
    }

    public static RecordedRequestMatcher hasHeader(String name, String value) {
        return new RecordedRequestMatcher(name, value, HEADER);
    }
}
