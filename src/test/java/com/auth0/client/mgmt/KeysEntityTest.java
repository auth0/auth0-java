package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.EncryptionKeyFilter;
import com.auth0.json.mgmt.keys.EncryptionKey;
import com.auth0.json.mgmt.keys.EncryptionKeysPage;
import com.auth0.json.mgmt.keys.EncryptionWrappingKeyResponse;
import com.auth0.json.mgmt.keys.Key;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
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
        Request<Void> request = api.keys().postEncryptionRekey();
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/keys/encryption/rekey"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldListEncryptionKeysWithoutFilter() throws Exception {
        Request<EncryptionKeysPage> request = api.keys().listEncryptionKeys(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ENCRYPTION_KEYS_LIST, 200);
        EncryptionKeysPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/keys/encryption"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldListEncryptionKeysWithPage() throws Exception {
        EncryptionKeyFilter filter = new EncryptionKeyFilter().withPage(1, 5);
        Request<EncryptionKeysPage> request = api.keys().listEncryptionKeys(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ENCRYPTION_KEYS_LIST, 200);
        EncryptionKeysPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/keys/encryption"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "1"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldListEncryptionKeysWithTotals() throws Exception {
        EncryptionKeyFilter filter = new EncryptionKeyFilter().withTotals(true);
        Request<EncryptionKeysPage> request = api.keys().listEncryptionKeys(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ENCRYPTION_KEYS_LIST, 200);
        EncryptionKeysPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/keys/encryption"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(3));
    }

    @Test
    public void shouldGetEncryptionKeyWithKid() throws Exception {
        Request<EncryptionKey> request = api.keys().getEncryptionKey("123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ENCRYPTION_KEY, 200);
        EncryptionKey response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/keys/encryption/123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldCreateEncryptionKey() throws Exception {
        Request<EncryptionKey> request = api.keys().createEncryptionKey("tenant-master-key");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ENCRYPTION_KEY, 201);
        EncryptionKey response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/keys/encryption"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("type", "tenant-master-key"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldImportEncryptionKey() throws Exception {
        Request<EncryptionKey> request = api.keys().importEncryptionKey("key@1234", "123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ENCRYPTION_KEY, 201);
        EncryptionKey response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/keys/encryption/123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("wrapped_key", "key@1234"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldCreateEncryptionWrappingKey() throws Exception {
        Request<EncryptionWrappingKeyResponse> request = api.keys().createEncryptionWrappingKey("123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ENCRYPTION_KEY, 201);
        EncryptionWrappingKeyResponse response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/keys/encryption/123/wrapping-key"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldDeleteEncryptionKey() throws Exception {
        Request<Void> request = api.keys().deleteEncryptionKey("123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(ENCRYPTION_KEY, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/keys/encryption/123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnDeleteEncryptionKeyWithNullKid(){
        verifyThrows(IllegalArgumentException.class,
            () -> api.keys().deleteEncryptionKey(null),
            "'kid' cannot be null!");
    }
}
