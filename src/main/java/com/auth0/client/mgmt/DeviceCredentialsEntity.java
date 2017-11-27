package com.auth0.client.mgmt;

import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.client.mgmt.filter.DeviceCredentialsFilter;
import com.auth0.json.mgmt.DeviceCredentials;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Device Credentials methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Device_Credentials
 */
@SuppressWarnings("WeakerAccess")
public class DeviceCredentialsEntity {
    private final RequestBuilder requestBuilder;
    DeviceCredentialsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request all the Device Credentials. A token with scope read:device_credentials is needed.
     * See https://auth0.com/docs/api/management/v2#!/Device_Credentials/get_device_credentials
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<DeviceCredentials>> list(DeviceCredentialsFilter filter) {
        return requestBuilder.get("api/v2/device-credentials")
                             .queryParameters(filter)
                             .request(new TypeReference<List<DeviceCredentials>>() {
                             });
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

        return requestBuilder.post("api/v2/device-credentials")
                             .body(deviceCredentials)
                             .request(new TypeReference<DeviceCredentials>() {
                             });
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

        return requestBuilder.delete("api/v2/device-credentials", deviceCredentialsId)
                             .request();
    }
}
