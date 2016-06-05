package com.auth0.util;

import com.auth0.authentication.result.UserIdentity;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;

public class UserIdentityMatcher extends AttributeMatcher<UserIdentity> {

    private final Matcher<String> idMatcher;
    private final Matcher<String> providerMatcher;
    private final Matcher<String> connectionMatcher;

    private UserIdentityMatcher(Matcher<String> idMatcher, Matcher<String> providerMatcher, Matcher<String> connectionMatcher) {
        this.idMatcher = idMatcher;
        this.providerMatcher = providerMatcher;
        this.connectionMatcher = connectionMatcher;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a user identity with ")
                .appendText("id: ").appendDescriptionOf(idMatcher).appendText(" ")
                .appendText("provider: ").appendDescriptionOf(providerMatcher).appendText(" ")
                .appendText("connection: ").appendDescriptionOf(connectionMatcher);
    }

    @Override
    protected boolean matchesSafely(UserIdentity identity, Description mismatchDescription) {
        return performMatch(idMatcher, identity.getId(), mismatchDescription, "id")
                && performMatch(providerMatcher, identity.getProvider(), mismatchDescription, "provider")
                && performMatch(connectionMatcher, identity.getConnection(), mismatchDescription, "connection");
    }

    public static Matcher<UserIdentity> isUserIdentity(String id, String provider, String connection) {
        return new UserIdentityMatcher(equalTo(id), equalTo(provider), equalTo(connection));
    }
}
