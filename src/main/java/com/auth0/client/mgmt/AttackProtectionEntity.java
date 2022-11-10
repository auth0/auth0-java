package com.auth0.client.mgmt;

import com.auth0.json.mgmt.attackprotection.BreachedPassword;
import com.auth0.json.mgmt.attackprotection.BruteForceConfiguration;
import com.auth0.json.mgmt.attackprotection.SuspiciousIPThrottlingConfiguration;
import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

/**
 * Class that provides an implementation of the Attack Protection methods of the Management API as defined in <a href="https://auth0.com/docs/api/management/v2#!/Attack_Protection/"></a>
 * @see ManagementAPI
 */
public class AttackProtectionEntity extends BaseManagementEntity {
    AttackProtectionEntity(Auth0HttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Gets the breached password detection settings.
     * @return the breached password detection settings.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Attack_Protection/get_breached_password_detection"></a>
     */
    public Request<BreachedPassword> getBreachedPasswordSettings() {
        return request(
            HttpMethod.GET,
            new TypeReference<BreachedPassword>() {},
            (builder) -> builder
                .withPathSegments("api/v2/attack-protection/breached-password-detection")

        );
    }

    /**
     * Update the breached password detection settings.
     * @param breachedPassword the updated breached password detection settings.
     * @return the updated breached password detection settings.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Attack_Protection/patch_breached_password_detection"></a>
     */
    public Request<BreachedPassword> updateBreachedPasswordSettings(BreachedPassword breachedPassword) {
        Asserts.assertNotNull(breachedPassword, "breached password");

        return request(
            HttpMethod.PATCH,
            new TypeReference<BreachedPassword>() {},
            (builder) -> builder
                .withPathSegments("api/v2/attack-protection/breached-password-detection")
                .withBody(breachedPassword)
        );
    }

    /**
     * Gets the brute force configuration
     * @return the brute force configuration
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Attack_Protection/get_brute_force_protection"></a>
     */
    public Request<BruteForceConfiguration> getBruteForceConfiguration() {
        return request(
            HttpMethod.GET,
            new TypeReference<BruteForceConfiguration>() {},
            (builder) -> builder
                .withPathSegments("api/v2/attack-protection/brute-force-protection")
        );
    }

    /**
     * Update the brute force configuration
     * @param configuration the updated brute force configuration
     * @return the updated brute force configuration
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Attack_Protection/patch_brute_force_protection"></a>
     */
    public Request<BruteForceConfiguration> updateBruteForceConfiguration(BruteForceConfiguration configuration) {
        Asserts.assertNotNull(configuration, "configuration");

        return request(
            HttpMethod.PATCH,
            new TypeReference<BruteForceConfiguration>() {},
            (builder) -> builder
                .withPathSegments("api/v2/attack-protection/brute-force-protection")
                .withBody(configuration)
        );
    }

    /**
     * Gets the suspicious IP throttling configuration
     * @return the suspicious IP throttling configuration
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Attack_Protection/get_suspicious_ip_throttling"></a>
     */
    public Request<SuspiciousIPThrottlingConfiguration> getSuspiciousIPThrottlingConfiguration() {
        return request(
            HttpMethod.GET,
            new TypeReference<SuspiciousIPThrottlingConfiguration>() {},
            (builder) -> builder
                .withPathSegments("api/v2/attack-protection/suspicious-ip-throttling")
        );
    }

    /**
     * Update the suspicious IP throttling configuration
     * @param configuration the updated suspicious IP throttling configuration
     * @return the updated suspicious IP throttling configuration
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Attack_Protection/patch_suspicious_ip_throttling"></a>
     */
    public Request<SuspiciousIPThrottlingConfiguration> updateSuspiciousIPThrottlingConfiguration(SuspiciousIPThrottlingConfiguration configuration) {
        Asserts.assertNotNull(configuration, "configuration");

        return request(
            HttpMethod.PATCH,
            new TypeReference<SuspiciousIPThrottlingConfiguration>() {},
            (builder) -> builder
                .withPathSegments("api/v2/attack-protection/suspicious-ip-throttling")
                .withBody(configuration)
        );
    }
}
