//package com.auth0.client.mgmt;
//
//import com.auth0.exception.Auth0Exception;
//import com.auth0.json.mgmt.branding.BrandingSettings;
//import com.auth0.json.mgmt.branding.UniversalLoginTemplate;
//import com.auth0.json.mgmt.branding.UniversalLoginTemplateUpdate;
//import com.auth0.net.Request;
//import com.auth0.net.client.Auth0HttpClient;
//import com.auth0.net.client.HttpMethod;
//import com.auth0.utils.Asserts;
//import com.fasterxml.jackson.core.type.TypeReference;
//import okhttp3.HttpUrl;
//
///**
// * Class that provides an implementation of the Branding methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Branding
// * <p>
// * This class is not thread-safe.
// *
// * @see ManagementAPI
// */
//@SuppressWarnings("WeakerAccess")
//public class BrandingEntity2 extends BaseManagementEntity2 {
//
//    // TODO PROBLEM:
//    //   tokenProvider throws Auth0Exception if error fetching token. Means all methods would now throw...
//
//    BrandingEntity2(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
//        super(client, baseUrl, tokenProvider);
//    }
//
//    /**
//     * Requests the branding settings for this tenant.
//     * See https://auth0.com/docs/api/management/v2#!/Branding/get_branding
//     *
//     * @return a Request to execute.
//     */
//    public Request<BrandingSettings> getBrandingSettings() throws Auth0Exception {
//        return request(
//            HttpMethod.GET,
//            new TypeReference<BrandingSettings>() {
//            },
//            (builder) -> builder.withPathSegments("api/v2/branding")
//        );
//    }
//
//    /**
//     * Update the branding settings for this tenant.
//     * See https://auth0.com/docs/api/management/v2#!/Branding/patch_branding
//     *
//     * @param settings the new branding settings.
//     * @return a Request to execute.
//     */
//    public Request<BrandingSettings> updateBrandingSettings(BrandingSettings settings) throws Auth0Exception {
//        Asserts.assertNotNull(settings, "settings");
//
//        return request(
//            HttpMethod.PATCH,
//            new TypeReference<BrandingSettings>() {
//            },
//            (builder) -> builder
//                .withPathSegments("api/v2/branding")
//                .withBody(settings)
//        );
//    }
//
//
//    /**
//     * Gets the template for the universal login page.
//     * See https://auth0.com/docs/api/management/v2#!/Branding/get_universal_login
//     *
//     * @return a Request to execute.
//     */
//    public Request<UniversalLoginTemplate> getUniversalLoginTemplate() throws Auth0Exception {
//        return request(
//            HttpMethod.GET,
//            new TypeReference<UniversalLoginTemplate>() {
//            },
//            (builder) -> builder.withPathSegments("api/v2/branding/templates/universal-login")
//        );
//    }
//
//    /**
//     * Delete the template for the universal login page.
//     * See https://auth0.com/docs/api/management/v2#!/Branding/delete_universal_login
//     *
//     * @return a Request to execute.
//     */
//    public Request<Void> deleteUniversalLoginTemplate() throws Auth0Exception {
//        return voidRequest(
//            HttpMethod.DELETE,
//            (builder) -> builder.withPathSegments("api/v2/branding/templates/universal-login")
//        );
//    }
//
//    /**
//     * Sets the template for the universal login page.
//     * See https://auth0.com/docs/api/management/v2#!/Branding/put_universal_login
//     *
//     * @return a Request to execute.
//     */
//    public Request<Void> setUniversalLoginTemplate(UniversalLoginTemplateUpdate template) throws Auth0Exception {
//        Asserts.assertNotNull(template, "template");
//
//        return voidRequest(
//            HttpMethod.PUT,
//            (builder) -> builder
//                .withPathSegments("api/v2/branding/templates/universal-login")
//                .withBody(template)
//        );
//    }
//}
