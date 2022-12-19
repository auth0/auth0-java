package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.DeviceCredentialsFilter;
import com.auth0.json.mgmt.DeviceCredentials;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Device Credentials methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Device_Credentials
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class DeviceCredentialsEntity extends BaseManagementEntity {

    DeviceCredentialsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all the Device Credentials. A token with scope read:device_credentials is needed.
     * See https://auth0.com/docs/api/management/v2#!/Device_Credentials/get_device_credentials
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<DeviceCredentials>> list(DeviceCredentialsFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/device-credentials");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        BaseRequest<List<DeviceCredentials>> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<List<DeviceCredentials>>() {
        });
        return request;
    }

    /**
     * Create a Device Credentials. A token with scope create:current_user_device_credentials is needed.
     * See https://auth0.com/docs/api/management/v2#!/Device_Credentials/post_device_credentials
     *
     * @param deviceCredentials the device credentials data to set.
     * @return a Request to execute.
     */
    public Request<DeviceCredentials> create(DeviceCredentials deviceCredentials) {
        Asserts.assertNotNull(deviceCredentials, "device credentials");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/device-credentials")
                .build()
                .toString();
        BaseRequest<DeviceCredentials> request = new BaseRequest<>(this.client, tokenProvider, url,  HttpMethod.POST, new TypeReference<DeviceCredentials>() {
        });
        request.setBody(deviceCredentials);
        return request;
    }

    /**
     * Delete an existing Device Credentials. A token with scope delete:device_credentials is needed.
     * See https://auth0.com/docs/api/management/v2#!/Device_Credentials/post_device_credentials
     *
     * @param deviceCredentialsId the device credentials id
     * @return a Request to execute.
     */
    public Request<Void> delete(String deviceCredentialsId) {
        Asserts.assertNotNull(deviceCredentialsId, "device credentials id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/device-credentials")
                .addPathSegment(deviceCredentialsId)
                .build()
                .toString();
        VoidRequest request =  new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
        return request;
    }
}
