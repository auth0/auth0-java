package com.auth0.client.mgmt;

import com.auth0.json.mgmt.guardian.*;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

/**
 * Class that provides an implementation of the Guardian methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Guardian
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class GuardianEntity extends BaseManagementEntity {

    GuardianEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Create a Guardian Enrollment Ticket. A token with scope create:guardian_enrollment_tickets is needed.
     *
     * @param enrollmentTicket the enrollment ticket data to set.
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/post_ticket">Management API2 docs</a>
     */
    public Request<EnrollmentTicket> createEnrollmentTicket(EnrollmentTicket enrollmentTicket) {
        Asserts.assertNotNull(enrollmentTicket, "enrollment ticket");

        return request(
            "POST",
            new TypeReference<EnrollmentTicket>() {
            },
            (builder) -> builder
                .withPathSegments("api/v2/guardian/enrollments/ticket")
                .withBody(enrollmentTicket)
        );
    }

    /**
     * Delete an existing Guardian Enrollment. A token with scope delete:guardian_enrollments is needed.
     *
     * @param enrollmentId the id of the enrollment to retrieve.
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/delete_enrollments_by_id">Management API2 docs</a>
     */
    public Request<Void> deleteEnrollment(String enrollmentId) {
        Asserts.assertNotNull(enrollmentId, "enrollment id");

        return voidRequest(
            "DELETE",
            (builder) -> builder.withPathSegments("api/v2/guardian/enrollments").withPathSegments(enrollmentId)
        );
    }

    /**
     * Request the Guardian SMS enrollment and verification templates.
     * A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_templates">Management API2 docs</a>
     */
    public Request<GuardianTemplates> getTemplates() {
        return request(
            "GET",
            new TypeReference<GuardianTemplates>() {
            },
            (builder) -> builder.withPathSegments("api/v2/guardian/factors/sms/templates")
        );
    }

    /**
     * Updates the existing Guardian SMS enrollment and verification templates.
     * A token with scope update:guardian_factors is needed.
     *
     * @param guardianTemplates the templates data to set.
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_templates">Management API2 docs</a>
     */
    public Request<GuardianTemplates> updateTemplates(GuardianTemplates guardianTemplates) {
        Asserts.assertNotNull(guardianTemplates, "guardian templates");

        return request(
            "PUT",
            new TypeReference<GuardianTemplates>() {
            },
            (builder) -> builder
                .withPathSegments("api/v2/guardian/factors/sms/templates")
                .withBody(guardianTemplates)
        );
    }

    /**
     * Request all the Guardian Factors. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_factors">Management API2 docs</a>
     */
    public Request<List<Factor>> listFactors() {
        return request(
            "GET",
            new TypeReference<List<Factor>>() {
            },
            (builder) -> builder.withPathSegments("api/v2/guardian/factors")
        );
    }

    /**
     * Update an existing Guardian Factor. A token with scope update:guardian_factors is needed.
     *
     * @param name    the name of the Factor to update.
     * @param enabled whether to enable or disable the Factor.
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_factors_by_name">Management API2 docs</a>
     */
    public Request<Factor> updateFactor(String name, Boolean enabled) {
        Asserts.assertNotNull(name, "name");
        Asserts.assertNotNull(enabled, "enabled");

        return request(
            "PUT",
            new TypeReference<Factor>() {
            },
            (builder) -> builder
                .withPathSegments("api/v2/guardian/factors").withPathSegments(name)
                .withParameter("enabled", enabled)
        );
    }

    /**
     * Request Guardian's Twilio SMS Factor Provider settings. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_twilio">Management API2 docs</a>
     */
    public Request<TwilioFactorProvider> getTwilioFactorProvider() {
        return request(
            "GET",
            new TypeReference<TwilioFactorProvider>() {
            },
            (builder) -> builder.withPathSegments("api/v2/guardian/factors/sms/providers/twilio")
        );
    }

    /**
     * Update Guardian's Twilio SMS Factor Provider. A token with scope update:guardian_factors is needed.
     *
     * @param provider the provider data to set.
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_twilio">Management API2 docs</a>
     */
    public Request<TwilioFactorProvider> updateTwilioFactorProvider(TwilioFactorProvider provider) {
        Asserts.assertNotNull(provider, "provider");

        return request(
            "PUT",
            new TypeReference<TwilioFactorProvider>() {
            },
            (builder) -> builder
                .withPathSegments("api/v2/guardian/factors/sms/providers/twilio")
                .withBody(provider)
        );
    }

    /**
     * Reset Guardian's Twilio SMS Factor Provider to the defaults.
     * A token with scope update:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_twilio">Management API2 docs</a>
     */
    public Request<TwilioFactorProvider> resetTwilioFactorProvider() {
        return updateTwilioFactorProvider(new TwilioFactorProvider(null, null, null, null));
    }

    /**
     * Request Guardian's SNS push-notification Factor Provider. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_sns">Management API2 docs</a>
     */
    public Request<SNSFactorProvider> getSNSFactorProvider() {
        return request(
            "GET",
            new TypeReference<SNSFactorProvider>() {
            },
            (builder) -> builder.withPathSegments("api/v2/guardian/factors/push-notification/providers/sns")
        );
    }

    /**
     * Update Guardian's SNS push-notification Factor Provider. A token with scope update:guardian_factors is needed.
     *
     * @param provider the provider data to set.
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_sns">Management API2 docs</a>
     */
    public Request<SNSFactorProvider> updateSNSFactorProvider(SNSFactorProvider provider) {
        Asserts.assertNotNull(provider, "provider");

        return request(
            "PUT",
            new TypeReference<SNSFactorProvider>() {
            },
            (builder) -> builder
                .withPathSegments("api/v2/guardian/factors/push-notification/providers/sns")
                .withBody(provider)
        );
    }

    /**
     * Reset Guardian's SNS push-notification Factor Provider to the defaults.
     * A token with scope update:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_sns">Management API2 docs</a>
     */
    public Request<SNSFactorProvider> resetSNSFactorProvider() {
        return updateSNSFactorProvider(new SNSFactorProvider(null, null, null, null, null));
    }

    /**
     * Get Guardian's MFA authentication policies. A token with scope read:mfa_policies is needed.
     *
     * @return a Request to execute
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_policies">Management API2 docs</a>
     */
    public Request<List<String>> getAuthenticationPolicies() {
        return request(
            "GET",
            new TypeReference<List<String>>() {
            },
            (builder) -> builder.withPathSegments("api/v2/guardian/policies")
        );
    }

    /**
     * Updates Guardian's MFA authentication policies. A token with scope update:mfa_policies is needed.
     *
     * @param policies the list of MFA policies to enable
     * @return a Request to execute
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_policies">Management API2 docs</a>
     */
    public Request<List<String>> updateAuthenticationPolicies(List<String> policies) {
        Asserts.assertNotNull(policies, "policies");

        return request(
            "PUT",
            new TypeReference<List<String>>() {
            },
            (builder) -> builder
                .withPathSegments("api/v2/guardian/policies")
                .withBody(policies)
        );
    }
}
