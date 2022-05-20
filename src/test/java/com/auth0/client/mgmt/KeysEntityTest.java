package com.auth0.client.mgmt;

import com.auth0.json.mgmt.Key;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class KeysEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnGetWithIdNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'kid' cannot be null!");
        api.keys().get(null);
    }

    @Test
    public void shouldThrowOnRevokeWithIdNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'kid' cannot be null!");
        api.keys().revoke(null);
    }

    @Test
    public void shouldListKeys() throws Exception {
        Request<List<Key>> request = api.keys().list();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(KEY_LIST, 200);
        List<Key> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/keys/signing"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldGetKeyWithId() throws Exception {
        Request<Key> request = api.keys().get("123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(KEY, 200);
        Key response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/keys/signing/123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRotateKey() throws Exception {
        Request<Key> request = api.keys().rotate();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(KEY_ROTATE, 200);
        Key response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/keys/signing/rotate"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRevokeKey() throws Exception {
        Request<Key> request = api.keys().revoke("123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(KEY_REVOKE, 200);
        Key response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PUT", "/api/v2/keys/signing/123/revoke"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }
}
