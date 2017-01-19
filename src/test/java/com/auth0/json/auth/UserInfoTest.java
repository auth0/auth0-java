package com.auth0.json.auth;

import com.auth0.json.JsonTest;
import org.hamcrest.collection.IsMapContaining;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserInfoTest extends JsonTest<UserInfo> {

    private static final String json = "{\"email\":\"test.account@userinfo.com\",\"client_id\":\"q2hnj2iu...\"}";

    @Test
    public void shouldSerialize() throws Exception {
        UserInfo info = new UserInfo();
        info.setValue("email", "test.account@userinfo.com");
        info.setValue("client_id", "q2hnj2iu...");

        String serialized = toJSON(info);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, is(equalTo(json)));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        UserInfo info = fromJSON(json, UserInfo.class);

        assertThat(info, is(notNullValue()));

        assertThat(info.getValues(), IsMapContaining.hasEntry("email", (Object) "test.account@userinfo.com"));
        assertThat(info.getValues(), IsMapContaining.hasEntry("client_id", (Object) "q2hnj2iu..."));
    }
}