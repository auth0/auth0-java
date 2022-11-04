package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.DeviceCredentialsFilter;
import com.auth0.json.mgmt.DeviceCredentials;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class DeviceCredentialsEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldListDeviceCredentials() throws Exception {
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListDeviceCredentialsWithClientId() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withClientId("client_23");
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("client_id", "client_23"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldListDeviceCredentialsWithUserId() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withUserId("user_23");
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("user_id", "user_23"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }


    @Test
    public void shouldListDeviceCredentialsWithType() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withType("public_key");
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("type", "public_key"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }


    @Test
    public void shouldListDeviceCredentialsWithFields() throws Exception {
        DeviceCredentialsFilter filter = new DeviceCredentialsFilter().withFields("some,random,fields", true);
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS_LIST, 200);
        List<DeviceCredentials> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyDeviceCredentials() throws Exception {
        Request<List<DeviceCredentials>> request = api.deviceCredentials().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<DeviceCredentials> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(DeviceCredentials.class)));
    }

    @Test
    public void shouldThrowOnCreateDeviceCredentialsWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'device credentials' cannot be null!");
        api.deviceCredentials().create(null);
    }

    @Test
    public void shouldCreateDeviceCredentials() throws Exception {
        Request<DeviceCredentials> request = api.deviceCredentials().create(new DeviceCredentials("device", "public_key", "val123", "id123", "clientId"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS, 200);
        DeviceCredentials response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/device-credentials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(5));
        assertThat(body, hasEntry("device_name", "device"));
        assertThat(body, hasEntry("type", "public_key"));
        assertThat(body, hasEntry("value", "val123"));
        assertThat(body, hasEntry("device_id", "id123"));
        assertThat(body, hasEntry("client_id", "clientId"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteDeviceCredentialsWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'device credentials id' cannot be null!");
        api.deviceCredentials().delete(null);
    }

    @Test
    public void shouldDeleteDeviceCredentials() throws Exception {
        Request<Void> request = api.deviceCredentials().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DEVICE_CREDENTIALS, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/device-credentials/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}
