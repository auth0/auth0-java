package com.auth0.client.mgmt;

import com.auth0.json.mgmt.branding.BrandingSettings;
import com.auth0.json.mgmt.branding.UniversalLoginTemplate;
import com.auth0.json.mgmt.branding.UniversalLoginTemplateUpdate;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Branding methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Branding
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class BrandingEntity extends BaseManagementEntity {

    BrandingEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Requests the branding settings for this tenant.
     * See https://auth0.com/docs/api/management/v2#!/Branding/get_branding
     *
     * @return a Request to execute.
     */
    public Request<BrandingSettings> getBrandingSettings() {
        return request(
            "GET",
            new TypeReference<BrandingSettings>() {
            },
            (builder) -> builder.withPathSegments("api/v2/branding")
        );
    }

    /**
     * Update the branding settings for this tenant.
     * See https://auth0.com/docs/api/management/v2#!/Branding/patch_branding
     *
     * @param settings the new branding settings.
     * @return a Request to execute.
     */
    public Request<BrandingSettings> updateBrandingSettings(BrandingSettings settings) {
        Asserts.assertNotNull(settings, "settings");

        return request(
            "PATCH",
            new TypeReference<BrandingSettings>() {
            },
            (builder) -> builder
                .withPathSegments("api/v2/branding")
                .withBody(settings)
        );
    }


    /**
     * Gets the template for the universal login page.
     * See https://auth0.com/docs/api/management/v2#!/Branding/get_universal_login
     *
     * @return a Request to execute.
     */
    public Request<UniversalLoginTemplate> getUniversalLoginTemplate() {
        return request(
            "GET",
            new TypeReference<UniversalLoginTemplate>() {
            },
            (builder) -> builder.withPathSegments("api/v2/branding/templates/universal-login")
        );
    }

    /**
     * Delete the template for the universal login page.
     * See https://auth0.com/docs/api/management/v2#!/Branding/delete_universal_login
     *
     * @return a Request to execute.
     */
    public Request<Void> deleteUniversalLoginTemplate() {
        return voidRequest(
            "DELETE",
            (builder) -> builder.withPathSegments("api/v2/branding/templates/universal-login")
        );
    }

    /**
     * Sets the template for the universal login page.
     * See https://auth0.com/docs/api/management/v2#!/Branding/put_universal_login
     *
     * @return a Request to execute.
     */
    public Request<Void> setUniversalLoginTemplate(UniversalLoginTemplateUpdate template) {
        Asserts.assertNotNull(template, "template");

        return voidRequest(
            "PUT",
            (builder) -> builder
                .withPathSegments("api/v2/branding/templates/universal-login")
                .withBody(template)
        );
    }
}
