package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.EncryptionKeyFilter;
import com.auth0.json.mgmt.keys.EncryptionKey;
import com.auth0.json.mgmt.keys.EncryptionKeysPage;
import com.auth0.json.mgmt.keys.EncryptionWrappingKeyResponse;
import com.auth0.json.mgmt.keys.Key;
import com.auth0.net.EmptyBodyRequest;
import com.auth0.net.BaseRequest;
import com.auth0.net.EmptyBodyVoidRequest;
import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Keys methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Keys
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
public class KeysEntity extends BaseManagementEntity {

    KeysEntity(Auth0HttpClient client, HttpUrl baseUrl,
               TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all Application Signing Keys.
     * A token with read:signing_keys is needed
     * See https://auth0.com/docs/api/management/v2#!/Keys/get_signing_keys
     *
     * @return a Request to execute
     */
    public Request<List<Key>> list() {
        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addEncodedPathSegments("api/v2/keys/signing");
        String url = builder.build().toString();
        return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<List<Key>>() {
        });
    }


    /**
     * Request an Application Signing Key. A token with scope read:signing_keys is needed.
     * See https://auth0.com/docs/api/management/v2#!/Keys/get_signing_key
     *
     * @param kid the id of the Application Signing Key to retrieve.
     * @return a Request to execute.
     */
    public Request<Key> get(String kid) {
        Asserts.assertNotNull(kid, "kid");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/signing")
            .addPathSegment(kid);
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Key>() {
        });
    }

    /**
     * Rotate the Application Signing Key.
     * A token with scope create:signing_keys and update:signing_keys is needed.
     * See https://auth0.com/docs/api/management/v2#!/Keys/post_signing_keys
     *
     * @return a Request to execute.
     */
    public Request<Key> rotate() {
        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/signing/rotate")
            .build()
            .toString();
        return new EmptyBodyRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<Key>() {
        });
    }

    /**
     * Revoke an Application Signing Key.
     * A token with scope update:signing_keys is needed.
     * See https://auth0.com/docs/api/management/v2#!/Keys/put_signing_keys
     *
     * @param kid the id of the Application Signing Key to revoke.
     * @return a Request to execute.
     */
    public Request<Key> revoke(String kid) {
        Asserts.assertNotNull(kid, "kid");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/signing/")
            .addPathSegment(kid)
            .addPathSegment("revoke")
            .build()
            .toString();
        return new EmptyBodyRequest<>(this.client, tokenProvider, url, HttpMethod.PUT, new TypeReference<Key>() {
        });
    }

    /**
     * Perform rekeying operation on the key hierarchy.
     * A token with scope create:encryption_keys and update:encryption_keys is needed
     * See https://auth0.com/docs/api/management/v2#!/Keys/post-encryption-rekey
     * @return a Request to execute.
     */
    public Request<Void> postEncryptionRekey(){
        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/encryption/rekey")
            .build()
            .toString();

        return new EmptyBodyVoidRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<Void>() {});
    }

    /**
     * Get all encryption keys.
     * A token with scope read:encryption_keys is needed
     * See https://auth0.com/docs/api/management/v2#!/Keys/get-encryption-keys
     * @param filter Filter to apply. Use null to get all encryption keys.
     * @return a Request to execute.
     */
    public Request<EncryptionKeysPage> listEncryptionKeys(EncryptionKeyFilter filter){

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/encryption");

        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }

        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<EncryptionKeysPage>() {
        });
    }

    /**
     * Get the encryption key with the given kid.
     * A token with scope read:encryption_keys is needed
     * See https://auth0.com/docs/api/management/v2#!/Keys/get-encryption-key
     * @param kid Encryption key ID
     * @return A Request to execute
     */
    public Request<EncryptionKey> getEncryptionKey(String kid){
        Asserts.assertNotNull(kid, "kid");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/encryption")
            .addPathSegment(kid)
            .build()
            .toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<EncryptionKey>() {
        });
    }

    /**
     * Create a new encryption key.
     * A token with scope create:encryption_keys is needed
     * See https://auth0.com/docs/api/management/v2#!/Keys/post-encryption
     * @param type key type
     * @return a Request to execute.
     */
    public Request<EncryptionKey> createEncryptionKey(String type) {
        Asserts.assertNotNull(type, "type");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/encryption")
            .build()
            .toString();

        BaseRequest<EncryptionKey> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<EncryptionKey>() {
        });

        request.addParameter("type", type);
        return request;
    }

    /**
     * Import an encryption key.
     * A token with scope update:encryption_keys is needed
     * See https://auth0.com/docs/api/management/v2#!/Keys/post-encryption-key
     * @param wrappedKey Base64 encoded ciphertext of key material wrapped by public wrapping key.
     * @param kid Encryption key ID
     * @return A Request to execute
     */
    public Request<EncryptionKey> importEncryptionKey(String wrappedKey, String kid) {
        Asserts.assertNotNull(wrappedKey, "wrappedKey");
        Asserts.assertNotNull(kid, "kid");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/encryption")
            .addPathSegment(kid)
            .build()
            .toString();

        BaseRequest<EncryptionKey> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<EncryptionKey>() {
        });

        request.addParameter("wrapped_key", wrappedKey);
        return request;
    }

    /**
     * Delete the encryption key with the given kid.
     * A token with scope delete:encryption_keys is needed
     * See https://auth0.com/docs/api/management/v2#!/delete-encryption-key
     * @param kid Encryption key ID
     * @return a Request to execute.
     */
    public Request<Void> deleteEncryptionKey(String kid) {
        Asserts.assertNotNull(kid, "kid");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/encryption")
            .addPathSegment(kid)
            .build()
            .toString();

        return new EmptyBodyVoidRequest<>(client, tokenProvider, url, HttpMethod.DELETE, new TypeReference<Void>() {
        });
    }

    /**
     * Create a new encryption wrapping key.
     * A token with scope create:encryption_keys is needed
     * See https://auth0.com/docs/api/management/v2#!/Keys/post-encryption-wrapping-key
     * @param kid Encryption key ID
     * @return a Request to execute.
     */
    public Request<EncryptionWrappingKeyResponse> createEncryptionWrappingKey(String kid) {
        Asserts.assertNotNull(kid, "kid");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/keys/encryption")
            .addPathSegment(kid)
            .addPathSegment("wrapping-key")
            .build()
            .toString();

        return new EmptyBodyRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<EncryptionWrappingKeyResponse>() {
        });
    }
}
