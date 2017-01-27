package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.DeviceCredentialsFilter;
import com.auth0.json.mgmt.DeviceCredentials;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Device Credentials methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Device_Credentials
 */
@SuppressWarnings("WeakerAccess")
public class DeviceCredentialsEntity extends BaseManagementEntity {

    DeviceCredentialsEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the Device Credentials. A token with scope read:device_credentials is needed.
     * See https://auth0.com/docs/api/management/v2#!/Device_Credentials/get_device_credentials
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<DeviceCredentials>> list(DeviceCredentialsFilter filter) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("device-credentials");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<List<DeviceCredentials>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<DeviceCredentials>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Device Credentials. A token with scope create:current_user_device_credentials is needed.
     * See https://auth0.com/docs/api/management/v2#!/Device_Credentials/post_device_credentials
     *
     * @param deviceCredentials the device credentials data to set.
     * @return a Request to execute.
     */
    public Request<DeviceCredentials> create(DeviceCredentials deviceCredentials) {
        Asserts.assertNotNull(deviceCredentials, "device credentials");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("device-credentials")
                .build()
                .toString();
        CustomRequest<DeviceCredentials> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<DeviceCredentials>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
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
    public Request delete(String deviceCredentialsId) {
        Asserts.assertNotNull(deviceCredentialsId, "device credentials id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("device-credentials")
                .addPathSegment(deviceCredentialsId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
}
