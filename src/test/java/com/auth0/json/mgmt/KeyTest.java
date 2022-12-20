package com.auth0.json.mgmt;

import com.auth0.json.JsonTest;
import com.auth0.json.mgmt.keys.Key;
import org.junit.Test;

import java.time.Instant;
import java.util.Date;

import static com.auth0.json.JsonMatcher.hasEntry;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class KeyTest extends JsonTest<Key> {
    private final static String KEY_JSON = "src/test/resources/mgmt/key.json";

    @Test
    public void deserialize() throws Exception {
        Key deserialized = fromJSON(readTextFile(KEY_JSON), Key.class);

        assertThat(deserialized.getKid(), is("21hi274Rp02112mgkUGma"));
        assertThat(deserialized.getCert(), is("-----BEGIN CERTIFICATE-----\r\nMIIDDTCCA...YiA0TQhAt8=\r\n-----END CERTIFICATE-----"));
        assertThat(deserialized.getPkcs7(), is("-----BEGIN PKCS7-----\r\nMIIDPA....t8xAA==\r\n-----END PKCS7-----"));
        assertThat(deserialized.getCurrent(), is(true));
        assertThat(deserialized.getNext(), is(false));
        assertThat(deserialized.getPrevious(), is(false));
        assertThat(deserialized.getCurrentSince(), is(parseJSONDate("2021-08-16T20:26:42.253086110Z")));
        assertThat(deserialized.getCurrentUntil(), is(parseJSONDate("2021-08-16T20:26:42.253086110Z")));
        assertThat(deserialized.getFingerprint(), is("CC:FB:DD:D8:9A:B5:DE:1B:F0:CC:36:D2:99:59:21:12:03:DD:A8:25"));
        assertThat(deserialized.getThumbprint(), is("CCFBDDD89AB5DE1BF0CC36D29959211203DDA825"));
        assertThat(deserialized.getRevoked(), is(false));
        assertThat(deserialized.getRevokedAt(), is(parseJSONDate("2021-08-16T20:26:42.253086110Z")));
    }

    @Test
    public void serialize() throws Exception {
        Key key = new Key();
        key.setKid("21hi274Rp02112mgkUGma");
        key.setCert("-----BEGIN CERTIFICATE-----\r\nMIIDDTCCA...YiA0TQhAt8=\r\n-----END CERTIFICATE-----");
        key.setPkcs7("-----BEGIN PKCS7-----\r\nMIIDPA....t8xAA==\r\n-----END PKCS7-----");
        key.setCurrent(true);
        key.setNext(false);
        key.setPrevious(false);
        key.setCurrentSince(Date.from(Instant.parse("2016-12-05T15:15:40.545Z")));
        key.setCurrentUntil(Date.from(Instant.parse("2016-12-05T15:15:40.545Z")));
        key.setFingerprint("CC:FB:DD:D8:9A:B5:DE:1B:F0:CC:36:D2:99:59:21:12:03:DD:A8:25");
        key.setThumbprint("CCFBDDD89AB5DE1BF0CC36D29959211203DDA825");
        key.setRevoked(false);
        key.setRevokedAt(Date.from(Instant.parse("2016-12-05T15:15:40.545Z")));

        String json = toJSON(key);

        assertThat(json, hasEntry("kid", "21hi274Rp02112mgkUGma"));
        assertThat(json, hasEntry("cert", "-----BEGIN CERTIFICATE-----\\r\\nMIIDDTCCA...YiA0TQhAt8=\\r\\n-----END CERTIFICATE-----"));
        assertThat(json, hasEntry("pkcs7", "-----BEGIN PKCS7-----\\r\\nMIIDPA....t8xAA==\\r\\n-----END PKCS7-----"));
        assertThat(json, hasEntry("current", true));
        assertThat(json, hasEntry("next", false));
        assertThat(json, hasEntry("previous", false));
        assertThat(json, hasEntry("fingerprint", "CC:FB:DD:D8:9A:B5:DE:1B:F0:CC:36:D2:99:59:21:12:03:DD:A8:25"));
        assertThat(json, hasEntry("thumbprint", "CCFBDDD89AB5DE1BF0CC36D29959211203DDA825"));
        assertThat(json, hasEntry("revoked", false));
        assertThat(json, hasEntry("current_since", "2016-12-05T15:15:40.545+00:00"));
        assertThat(json, hasEntry("current_until", "2016-12-05T15:15:40.545+00:00"));
        assertThat(json, hasEntry("revoked_at", "2016-12-05T15:15:40.545+00:00"));
    }
}
