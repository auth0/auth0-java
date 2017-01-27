package com.auth0.json.mgmt;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class DeviceCredentialsTest extends JsonTest<DeviceCredentials> {

    private static final String json = "{\"device_name\":\"devName\",\"type\":\"publicKey\",\"device_id\":\"dev123\",\"user_id\":\"theUserId\"}";
    private static final String readOnlyJson = "{\"id\":\"credentialsId\"}";

    @Test
    public void shouldSerialize() throws Exception {
        DeviceCredentials credentials = new DeviceCredentials("devName", "publicKey", "val123", "dev123", "client123");
        credentials.setUserId("theUserId");

        String serialized = toJSON(credentials);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("device_name", "devName"));
        assertThat(serialized, JsonMatcher.hasEntry("type", "publicKey"));
        assertThat(serialized, JsonMatcher.hasEntry("value", "val123"));
        assertThat(serialized, JsonMatcher.hasEntry("device_id", "dev123"));
        assertThat(serialized, JsonMatcher.hasEntry("client_id", "client123"));
        assertThat(serialized, JsonMatcher.hasEntry("user_id", "theUserId"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        DeviceCredentials credentials = fromJSON(json, DeviceCredentials.class);

        assertThat(credentials, is(notNullValue()));
        assertThat(credentials.getDeviceName(), is("devName"));
        assertThat(credentials.getType(), is("publicKey"));
        assertThat(credentials.getDeviceId(), is("dev123"));
        assertThat(credentials.getUserId(), is("theUserId"));
    }

    @Test
    public void shouldIncludeReadOnlyValuesOnDeserialize() throws Exception {
        DeviceCredentials credentials = fromJSON(readOnlyJson, DeviceCredentials.class);
        assertThat(credentials, is(notNullValue()));

        assertThat(credentials.getId(), is("credentialsId"));
    }
}