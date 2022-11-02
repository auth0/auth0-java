package com.auth0.client.mgmt;

import com.auth0.json.mgmt.Key;
import com.auth0.net.HttpClient;
import com.auth0.net.CustomRequest;
import com.auth0.net.EmptyBodyRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.List;

/**
 * Class that provides an implementation of the Keys methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Keys
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
public class KeysEntity extends BaseManagementEntity {

    KeysEntity(HttpClient client, HttpUrl baseUrl,
               String apiToken) {
        super(client, baseUrl, apiToken);
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
        CustomRequest<List<Key>> request = new CustomRequest<>(this.client, url, "GET", new TypeReference<List<Key>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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
        CustomRequest<Key> request = new CustomRequest<>(client, url, "GET", new TypeReference<Key>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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
        CustomRequest<Key> request = new EmptyBodyRequest<>(this.client, url, "POST", new TypeReference<Key>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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
        CustomRequest<Key> request = new EmptyBodyRequest<>(this.client, url, "PUT", new TypeReference<Key>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
}
