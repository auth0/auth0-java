package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.UserAttributeProfilesFilter;
import com.auth0.json.mgmt.userAttributeProfiles.*;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

public class UserAttributeProfilesEntity  extends BaseManagementEntity {

    private final static String ORGS_PATH = "api/v2/user-attribute-profiles";

    UserAttributeProfilesEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Get a user attribute profile by its ID. A token with {@code read:user_attribute_profiles} scope is required.
     * @param id the ID of the user attribute profile to retrieve.
     * @return a Request to execute.
     */
    public Request<UserAttributeProfile> get(String id) {
        Asserts.assertNotNull(id, "id");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .build().toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<UserAttributeProfile>() {
        });
    }

    /**
     * Get all user attribute profiles. A token with {@code read:user_attribute_profiles} scope is required.
     *
     * @param filter an optional pagination filter
     * @return a Request to execute
     *
     */
    public Request<ListUserAttributeProfile> getAll(UserAttributeProfilesFilter filter) {
        HttpUrl.Builder builder = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH);

        applyFilter(filter, builder);

        String url = builder.build().toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<ListUserAttributeProfile>() {
        });
    }

    private void applyFilter(UserAttributeProfilesFilter filter, HttpUrl.Builder builder) {
        if (filter != null) {
            filter.getAsMap().forEach((k, v) -> builder.addQueryParameter(k, String.valueOf(v)));
        }
    }


    /**
     * Update a user attribute profile. A token with {@code update:user_attribute_profiles} scope is required.
     *
     * @param userAttributeProfile the user attribute profile to update.
     * @return a Request to execute.
     */
    public Request<UserAttributeProfile> update(String id, UserAttributeProfile userAttributeProfile) {
        Asserts.assertNotNull(id, "id");
        Asserts.assertNotNull(userAttributeProfile, "userAttributeProfile");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .build().toString();

        BaseRequest<UserAttributeProfile> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<UserAttributeProfile>() {
        });

        request.setBody(userAttributeProfile);
        return request;
    }

    /**
     * Create a new user attribute profile. A token with {@code create:user_attribute_profiles} scope is required.
     * @param userAttributeProfile the user attribute profile to create.
     * @return a Request to execute.
     */
    public Request<UserAttributeProfile> create(UserAttributeProfile userAttributeProfile) {
        Asserts.assertNotNull(userAttributeProfile.getName(), "name");
        Asserts.assertNotNull(userAttributeProfile.getUserAttributes(), "userAttributes");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .build().toString();

        BaseRequest<UserAttributeProfile> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.POST, new TypeReference<UserAttributeProfile>() {
        });

        request.setBody(userAttributeProfile);
        return request;
    }

    /**
     * Delete a user attribute profile by its ID. A token with {@code delete:user_attribute_profiles} scope is required.
     * @param id the ID of the user attribute profile to delete.
     * @return a Request to execute.
     */
    public Request<Void> delete(String id) {
        Asserts.assertNotNull(id, "id");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id);

        String url = builder.build().toString();
        return new VoidRequest(client, tokenProvider, url, HttpMethod.DELETE);
    }

    /**
     * Get a user attribute profile template by its ID. A token with {@code read:user_attribute_profiles} scope is required.
     * @param id the ID of the user attribute profile template to retrieve.
     * @return a Request to execute.
     */
    public Request<UserAttributeProfileTemplateResponse> getTemplate(String id) {
        Asserts.assertNotNull(id, "id");

        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment("templates")
            .addPathSegment(id)
            .build().toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<UserAttributeProfileTemplateResponse>() {
        });
    }

    /**
     * Get all user attribute profile templates. A token with {@code read:user_attribute_profiles} scope is required.
     *
     * @return a Request to execute
     */
    public Request<ListUserAttributeProfileTemplate> getAllTemplates() {
        String url = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment("templates")
            .build().toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<ListUserAttributeProfileTemplate>() {
        });
    }
}
