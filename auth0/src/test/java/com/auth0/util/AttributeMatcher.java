package com.auth0.util;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public abstract class AttributeMatcher<T> extends TypeSafeDiagnosingMatcher<T> {
    protected <E> boolean performMatch(Matcher<E> matcher, E value, Description description, String attributeName) {
        boolean matches = matcher.matches(value);
        if (!matches) {
            description.appendText(attributeName).appendText(" ");
            matcher.describeMismatch(value, description);
            description.appendText(" and not expected: ").appendDescriptionOf(matcher);
        }
        return matches;
    }
}
