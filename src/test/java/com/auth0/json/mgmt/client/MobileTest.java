package com.auth0.json.mgmt.client;

import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class MobileTest extends JsonTest<Mobile> {

    private static final String json = "{\"android\":{},\"ios\":{}}";

    @Test
    public void shouldSerialize() throws Exception {
        Android android = new Android(null, null);
        IOS ios = new IOS(null, null);
        Mobile mobile = new Mobile(android, ios);

        String serialized = toJSON(mobile);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, is(equalTo(json)));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        Mobile mobile = fromJSON(json, Mobile.class);

        assertThat(mobile, is(notNullValue()));
        assertThat(mobile.getAndroid(), is(notNullValue()));
        assertThat(mobile.getIos(), is(notNullValue()));
    }
}