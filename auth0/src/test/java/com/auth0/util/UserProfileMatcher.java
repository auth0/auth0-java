package com.auth0.util;

import com.auth0.authentication.result.UserProfile;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.*;

public class UserProfileMatcher extends AttributeMatcher<UserProfile> {

    private final Matcher<String> idMatcher;
    private final Matcher<String> nameMatcher;
    private final Matcher<String> nicknameMatcher;
    private final Matcher<String> pictureMatcher;

    private UserProfileMatcher(Matcher<String> idMatcher, Matcher<String> nameMatcher, Matcher<String> nicknameMatcher, Matcher<String> pictureMatcher) {
        this.idMatcher = idMatcher;
        this.nameMatcher = nameMatcher;
        this.nicknameMatcher = nicknameMatcher;
        this.pictureMatcher = pictureMatcher;
    }

    @Override
    protected boolean matchesSafely(UserProfile profile, Description mismatchDescription) {
        return performMatch(idMatcher, profile.getId(), mismatchDescription, "id")
                && performMatch(nameMatcher, profile.getName(), mismatchDescription, "name")
                && performMatch(nicknameMatcher, profile.getNickname(), mismatchDescription, "nickname")
                && performMatch(pictureMatcher, profile.getPictureURL(), mismatchDescription, "picture");
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a normalized profile with ")
                .appendText("id: ").appendDescriptionOf(idMatcher).appendText(" ")
                .appendText("name: ").appendDescriptionOf(nameMatcher).appendText(" ")
                .appendText("nickname: ").appendDescriptionOf(nicknameMatcher).appendText(" ")
                .appendText("picture: ").appendDescriptionOf(pictureMatcher);
    }

    public static Matcher<UserProfile> isNormalizedProfile(String id, String name, String nickname) {
        return new UserProfileMatcher(equalTo(id), equalTo(name), equalTo(nickname), not(emptyOrNullString()));
    }
}
