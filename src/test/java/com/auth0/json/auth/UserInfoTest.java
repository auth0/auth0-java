package com.auth0.json.auth;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.hamcrest.collection.IsMapContaining;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class UserInfoTest extends JsonTest<UserInfo> {

    private static final String json = "{\"email_verified\":false,\"client_id\":\"q2hnj2iu...\",\"updated_at\":\"2016-12-05T15:15:40.545Z\",\"name\":\"test.account@userinfo.com\",\"email\":\"test.account@userinfo.com\"}";

    @Test
    public void shouldSerialize() throws Exception {
        UserInfo info = new UserInfo();
        info.setValue("email_verified", false);
        info.setValue("email", "test.account@userinfo.com");
        info.setValue("client_id", "q2hnj2iu...");
        info.setValue("updated_at", "2016-12-05T15:15:40.545Z");
        info.setValue("name", "test.account@userinfo.com");

        String serialized = toJSON(info);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("email_verified", false));
        assertThat(serialized, JsonMatcher.hasEntry("email", "test.account@userinfo.com"));
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "q2hnj2iu..."));
        assertThat(serialized, JsonMatcher.hasEntry("updated_at", "2016-12-05T15:15:40.545Z"));
        assertThat(serialized, JsonMatcher.hasEntry("name", "test.account@userinfo.com"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        UserInfo info = fromJSON(json, UserInfo.class);

        assertThat(info, is(notNullValue()));

        assertThat(info.getValues(), IsMapContaining.hasEntry("email_verified", false));
        assertThat(info.getValues(), IsMapContaining.hasEntry("email", "test.account@userinfo.com"));
        assertThat(info.getValues(), IsMapContaining.hasEntry("client_id", "q2hnj2iu..."));
        assertThat(info.getValues(), IsMapContaining.hasEntry("updated_at", "2016-12-05T15:15:40.545Z"));
        assertThat(info.getValues(), IsMapContaining.hasEntry("name", "test.account@userinfo.com"));
    }
}
