package com.auth0.json.mgmt.client;

import com.auth0.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AndroidTest extends JsonTest<Android> {

    private static final String json = "{\"app_package_name\":\"pkg\",\"sha256_cert_fingerprints\":[\"cert1\",\"cert2\"]}";

    @Test
    public void shouldSerialize() throws Exception {
        Android android = new Android("pkg", Arrays.asList("cert1", "cert2"));

        String serialized = toJSON(android);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("app_package_name", "pkg"));
        assertThat(serialized, JsonMatcher.hasEntry("sha256_cert_fingerprints", Arrays.asList("cert1", "cert2")));
    }


    @Test
    public void shouldDeserialize() throws Exception {
        Android android = fromJSON(json, Android.class);

        assertThat(android, is(notNullValue()));

        assertThat(android.getAppPackageName(), is("pkg"));
        assertThat(android.getSha256CertFingerprints(), contains("cert1", "cert2"));
        assertThat(android.getSha256CertFingerprints().size(), is(2));
    }
}