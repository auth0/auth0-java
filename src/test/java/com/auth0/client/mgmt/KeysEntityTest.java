package com.auth0.client.mgmt;

import com.auth0.json.mgmt.keys.Key;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class KeysEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnGetWithIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.keys().get(null),
            "'kid' cannot be null!");
    }

    @Test
    public void shouldThrowOnRevokeWithIdNull() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.keys().revoke(null),
            "'kid' cannot be null!");
    }

    @Test
    public void shouldListKeys() throws Exception {
        Request<List<Key>> request = api.keys().list();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(KEY_LIST, 200);
        List<Key> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/keys/signing"));
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
        Key response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/keys/signing/123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRotateKey() throws Exception {
        Request<Key> request = api.keys().rotate();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(KEY_ROTATE, 200);
        Key response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/keys/signing/rotate"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRevokeKey() throws Exception {
        Request<Key> request = api.keys().revoke("123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(KEY_REVOKE, 200);
        Key response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/keys/signing/123/revoke"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRekey() throws Exception {
        Request<Void> request = api.keys().rekey();
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/keys/encryption/rekey"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}
