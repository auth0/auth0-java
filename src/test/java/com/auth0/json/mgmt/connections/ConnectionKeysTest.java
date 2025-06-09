package com.auth0.json.mgmt.connections;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ConnectionKeysTest extends JsonTest<ConnectionKeys> {
    private static final String json = "{\n" +
        "    \"kid\": \"kid-1\",\n" +
        "    \"algorithm\": \"RS256\",\n" +
        "    \"key_use\": \"signing\",\n" +
        "    \"subject_dn\": \"/CN=cajwt\",\n" +
        "    \"cert\": \"-----BEGIN CERTIFICATE-----\\r\\ncert-key-1\\r\\n-----END CERTIFICATE-----\\r\\n\",\n" +
        "    \"fingerprint\": \"F1\",\n" +
        "    \"thumbprint\": \"example-print\",\n" +
        "    \"pkcs\": \"-----BEGIN PKCS7-----\\r\\npkcs-1\\r\\n-----END PKCS7-----\\r\\n\",\n" +
        "    \"current\": true,\n" +
        "    \"current_since\": \"2025-05-29T13:17:24.850Z\"\n" +
        "  }";

    @Test
    public void shouldSerialize() throws Exception {
        ConnectionKeys connectionKeys = new ConnectionKeys();
        connectionKeys.setKid("kid-1");
        connectionKeys.setAlgorithm("RS256");
        connectionKeys.setKeyUse("signing");
        connectionKeys.setSubjectDn("/CN=cajwt");
        connectionKeys.setFingerprint("F1");
        connectionKeys.setThumbprint("example-print");
        connectionKeys.setPkcs("pkcs");
        connectionKeys.setCurrent(true);
        connectionKeys.setCurrentSince("2025-05-29T13:17:24.850Z");

        String serialized = toJSON(connectionKeys);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("kid", "kid-1"));
        assertThat(serialized, JsonMatcher.hasEntry("algorithm", "RS256"));
        assertThat(serialized, JsonMatcher.hasEntry("key_use", "signing"));
        assertThat(serialized, JsonMatcher.hasEntry("subject_dn", "/CN=cajwt"));
        assertThat(serialized, JsonMatcher.hasEntry("fingerprint", "F1"));
        assertThat(serialized, JsonMatcher.hasEntry("thumbprint", "example-print"));
        assertThat(serialized, JsonMatcher.hasEntry("pkcs", "pkcs"));
        assertThat(serialized, JsonMatcher.hasEntry("current", true));
        assertThat(serialized, JsonMatcher.hasEntry("current_since", "2025-05-29T13:17:24.850Z"));
    }

    @Test
    public void shouldDeserialize() throws Exception {
        ConnectionKeys connectionKey = fromJSON(json, ConnectionKeys.class);
        assertThat(connectionKey, is(notNullValue()));
        assertThat(connectionKey.getKid(), is("kid-1"));
        assertThat(connectionKey.getAlgorithm(), is("RS256"));
        assertThat(connectionKey.getKeyUse(), is("signing"));
        assertThat(connectionKey.getSubjectDn(), is("/CN=cajwt"));
        assertThat(connectionKey.getCert(), is("-----BEGIN CERTIFICATE-----\r\ncert-key-1\r\n-----END CERTIFICATE-----\r\n"));
        assertThat(connectionKey.getFingerprint(), is("F1"));
        assertThat(connectionKey.getThumbprint(), is("example-print"));
        assertThat(connectionKey.getPkcs(), is("-----BEGIN PKCS7-----\r\npkcs-1\r\n-----END PKCS7-----\r\n"));
        assertThat(connectionKey.getCurrent(), is(true));
        assertThat(connectionKey.getCurrentSince(), is("2025-05-29T13:17:24.850Z"));
    }
}
