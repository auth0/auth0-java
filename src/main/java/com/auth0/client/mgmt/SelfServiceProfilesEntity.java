package com.auth0.client.mgmt;

import com.auth0.client.auth.AuthAPI;
import com.auth0.client.mgmt.filter.PageBasedPaginationFilter;
import com.auth0.json.mgmt.selfserviceprofiles.*;
import com.auth0.net.*;
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
     * A token with {@code read:self_service_profiles} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/get-self-service-profiles">https://auth0.com/docs/api/management/v2#!/self-service-profiles/get-self-service-profiles</a>
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
     * A token with {@code create:self_service_profiles} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/post-self-service-profiles">https://auth0.com/docs/api/management/v2#!/self-service-profiles/post-self-service-profiles</a>
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
     * A token with {@code read:self_service_profiles} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/get-self-service-profiles-by-id">https://auth0.com/docs/api/management/v2#!/self-service-profiles/get-self-service-profiles-by-id</a>
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
     * A token with {@code delete:self_service_profiles} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/delete-self-service-profiles-by-id">https://auth0.com/docs/api/management/v2#!/self-service-profiles/delete-self-service-profiles-by-id</a>
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
     * A token with {@code update:self_service_profiles} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/patch-self-service-profiles-by-id">https://auth0.com/docs/api/management/v2#!/self-service-profiles/patch-self-service-profiles-by-id</a>
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

    /**
     * Get the custom text for specific self-service profile and language.
     * A token with {@code read:self_service_profile_custom_texts} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/get-self-service-profile-custom-text">https://auth0.com/docs/api/management/v2#!/self-service-profiles/get-self-service-profile-custom-text</a>
     * @param id the self-service profile ID.
     * @param language the language.
     * @param page the page.
     * @return a Request to execute.
     */
    public Request<Object> getCustomText(String id, String language, String page) {
        Asserts.assertNotNull(id, "id");
        Asserts.assertNotNull(language, "language");
        Asserts.assertNotNull(page, "page");

        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .addPathSegment(id)
                .addPathSegment("custom-text")
                .addPathSegment(language)
                .addPathSegment(page);

        String url = builder.build().toString();

        return new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.GET, new TypeReference<Object>() {
        });
    }

    /**
     * Set the custom text for specific self-service profile and language.
     * A token with {@code update:self_service_profile_custom_texts} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/put-self-service-profile-custom-text">https://auth0.com/docs/api/management/v2#!/self-service-profiles/put-self-service-profile-custom-text</a>
     * @param id the self-service profile ID.
     * @param language the language.
     * @param page the page.
     * @param customText the custom text.
     * @return a Request to execute.
     */
    public Request<Object> setCustomText(String id, String language, String page, Object customText) {
        Asserts.assertNotNull(id, "id");
        Asserts.assertNotNull(language, "language");
        Asserts.assertNotNull(page, "page");
        Asserts.assertNotNull(customText, "custom text");

        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .addPathSegment(id)
                .addPathSegment("custom-text")
                .addPathSegment(language)
                .addPathSegment(page);

        String url = builder.build().toString();

        BaseRequest<Object> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PUT, new TypeReference<Object>() {
        });
        request.setBody(customText);
        return request;
    }

    /**
     * Create a new SSO access ticket.
     * A token with {@code create:sso_access_tickets} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/post-sso-ticket">https://auth0.com/docs/api/management/v2#!/self-service-profiles/post-sso-ticket</a>
     * @param id the self-service profile ID.
     * @param requestBody the payload.
     * @return a Request to execute.
     */
    public Request<SsoAccessTicketResponse> createSsoAccessTicket(String id, SsoAccessTicketRequest requestBody) {
        Asserts.assertNotNull(id, "id");
        Asserts.assertNotNull(requestBody, "request body");

        HttpUrl.Builder builder = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .addPathSegment("sso-ticket");

        String url = builder.build().toString();

        BaseRequest<SsoAccessTicketResponse> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<SsoAccessTicketResponse>() {
        });
        request.setBody(requestBody);
        return request;
    }


    /**
     * Create a new SSO access ticket.
     * A token with {@code create:sso_access_tickets} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/post-sso-ticket">https://auth0.com/docs/api/management/v2#!/self-service-profiles/post-sso-ticket</a>
     * @param id the self-service profile ID.
     * @param payload the payload.
     * @return a Request to execute.
     *
     * @deprecated Use {@link #createSsoAccessTicket(String, SsoAccessTicketRequest)} to create sso access ticket.
     */
    @Deprecated
    public Request<SsoAccessTicketResponse> createSsoAccessTicket(String id, Object payload) {
        Asserts.assertNotNull(id, "id");
        Asserts.assertNotNull(payload, "payload");

        HttpUrl.Builder builder = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .addPathSegment("sso-ticket");
        String url = builder.build().toString();

        BaseRequest<SsoAccessTicketResponse> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<SsoAccessTicketResponse>() {
        });
        request.setBody(payload);
        return request;
    }

    /**
     * Revoke an SSO ticket.
     * A token with {@code delete:sso_access_tickets} scope is needed
     * @see <a href="https://auth0.com/docs/api/management/v2#!/self-service-profiles/post-revoke">https://auth0.com/docs/api/management/v2#!/self-service-profiles/post-revoke</a>
     * @param id the self-service profile ID.
     * @param ticketId the ticket ID.
     * @return a Request to execute.
     */
    public Request<Void> revokeSsoTicket(String id, String ticketId) {
        Asserts.assertNotNull(id, "id");
        Asserts.assertNotNull(ticketId, "ticket id");

        HttpUrl.Builder builder = baseUrl.newBuilder()
            .addPathSegments(ORGS_PATH)
            .addPathSegment(id)
            .addPathSegment("sso-ticket")
            .addPathSegment(ticketId)
            .addPathSegment("revoke");

        String url = builder.build().toString();

        return new EmptyBodyVoidRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<Void>() {
        });
    }
}
