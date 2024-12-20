package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.PageBasedPaginationFilter;
import com.auth0.json.mgmt.selfserviceprofiles.SelfServiceProfile;
import com.auth0.json.mgmt.selfserviceprofiles.SelfServiceProfileResponse;
import com.auth0.json.mgmt.selfserviceprofiles.SelfServiceProfileResponsePage;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.Map;

public class SelfServiceProfilesEntity extends BaseManagementEntity {

    private final static String ORGS_PATH = "api/v2/self-service-profiles";

    SelfServiceProfilesEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request the list of self-service profiles.
     * A token with scope read:self_service_profiles is needed
     * See <a href="https://auth0.com/docs/api/management/v2/self-service-profiles/get-self-service-profiles">https://auth0.com/docs/api/management/v2/self-service-profiles/get-self-service-profiles</a>
     * @param pageFilter the pagination filter to apply. Can be null to use the default values.
     * @return a Request to execute.
     */
    public Request<SelfServiceProfileResponsePage> get(PageBasedPaginationFilter pageFilter) {
        HttpUrl.Builder builder = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH);

        if (pageFilter != null) {
            for (Map.Entry<String, Object> e : pageFilter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }

        String url = builder.build().toString();
        return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<SelfServiceProfileResponsePage>() {
        });
    }

    /**
     * Create a new self-service profile.
     * A token with scope create:self_service_profiles is needed
     * See <a href="https://auth0.com/docs/api/management/v2/self-service-profiles/post-self-service-profiles">https://auth0.com/docs/api/management/v2/self-service-profiles/post-self-service-profiles</a>
     * @param selfServiceProfile the self-service profile to create.
     * @return a Request to execute.
     */
    public Request<SelfServiceProfileResponse> create(SelfServiceProfile selfServiceProfile) {
        Asserts.assertNotNull(selfServiceProfile, "self service profile");
        Asserts.assertNotNull(selfServiceProfile.getName(), "name");

        String url = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .build()
                .toString();

        BaseRequest<SelfServiceProfileResponse>  request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<SelfServiceProfileResponse>() {
        });
        request.setBody(selfServiceProfile);
        return request;
    }

    /**
     * Request the self-service profile with the given ID.
     * A token with scope read:self_service_profiles is needed
     * See <a href="https://auth0.com/docs/api/management/v2/self-service-profiles/get-self-service-profiles-by-id">https://auth0.com/docs/api/management/v2/self-service-profiles/get-self-service-profiles-by-id</a>
     * @param id the self-service profile ID.
     * @return a Request to execute.
     */
    public Request<SelfServiceProfileResponse> getById(String id) {
        Asserts.assertNotNull(id, "id");

        String url = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .addPathSegment(id)
                .build()
                .toString();

        return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<SelfServiceProfileResponse>() {
        });
    }

    /**
     * Delete the self-service profile with the given ID.
     * A token with scope delete:self_service_profiles is needed
     * See <a href="https://auth0.com/docs/api/management/v2/self-service-profiles/delete-self-service-profiles-by-id">https://auth0.com/docs/api/management/v2/self-service-profiles/delete-self-service-profiles-by-id</a>
     * @param id the self-service profile ID.
     * @return a Request to execute.
     */
    public Request<Void> delete(String id) {
        Asserts.assertNotNull(id, "id");

        String url = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .addPathSegment(id)
                .build()
                .toString();

        return new VoidRequest(this.client, tokenProvider, url, HttpMethod.DELETE);
    }

    /**
     * Update the self-service profile with the given ID.
     * A token with scope update:self_service_profiles is needed
     * See <a href="https://auth0.com/docs/api/management/v2/self-service-profiles/patch-self-service-profiles-by-id">https://auth0.com/docs/api/management/v2/self-service-profiles/patch-self-service-profiles-by-id</a>
     * @param selfServiceProfile the self-service profile to update.
     * @param id the self-service profile ID.
     * @return a Request to execute.
     */
    public Request<SelfServiceProfileResponse> update(SelfServiceProfile selfServiceProfile, String id) {
        Asserts.assertNotNull(selfServiceProfile, "self service profile");
        Asserts.assertNotNull(id, "id");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .build()
            .toString();

        BaseRequest<SelfServiceProfileResponse>  request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<SelfServiceProfileResponse>() {
        });
        request.setBody(selfServiceProfile);
        return request;
    }
}
