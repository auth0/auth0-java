package com.auth0.client.mgmt;

import com.auth0.json.mgmt.guardian.*;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.function.Function;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;
import org.jetbrains.annotations.Nullable;

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

        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/enrollments/ticket"),
            "POST",
            new TypeReference<EnrollmentTicket>() {
            },
            enrollmentTicket
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

        return createVoidRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/enrollments").addPathSegment(enrollmentId),
            "DELETE"
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
        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/factors/sms/templates"),
            "GET",
            new TypeReference<GuardianTemplates>() {
            }
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

        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/factors/sms/templates"),
            "PUT",
            new TypeReference<GuardianTemplates>() {
            },
            guardianTemplates
        );
    }

    /**
     * Request all the Guardian Factors. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_factors">Management API2 docs</a>
     */
    public Request<List<Factor>> listFactors() {
        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/factors"),
            "GET",
            new TypeReference<List<Factor>>() {
            }
        );
    }

    /**
     * Update an existing Guardian Factor. A token with scope update:guardian_factors is needed.
     *
     * @param name    the name of the Factor to update.
     * @param enabled  whether to enable or disable the Factor.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_factors_by_name">Management API2 docs</a>
     */
    public Request<Factor> updateFactor(String name, Boolean enabled) {
        Asserts.assertNotNull(name, "name");
        Asserts.assertNotNull(enabled, "enabled");

        CustomRequest<Factor> request = createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/factors").addPathSegments(name),
            "PUT",
            new TypeReference<Factor>() {
            }
        );

        request.addParameter("enabled", enabled);
        return request;
    }

    /**
     * Request Guardian's Twilio SMS Factor Provider settings. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_twilio">Management API2 docs</a>
     */
    public Request<TwilioFactorProvider> getTwilioFactorProvider() {
        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/factors/sms/providers/twilio"),
            "GET",
            new TypeReference<TwilioFactorProvider>() {
            }
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

        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/factors/sms/providers/twilio"),
            "PUT",
            new TypeReference<TwilioFactorProvider>() {
            },
            provider
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
        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/factors/push-notification/providers/sns"),
            "GET",
            new TypeReference<SNSFactorProvider>() {
            }
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

        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/factors/push-notification/providers/sns"),
            "PUT",
            new TypeReference<SNSFactorProvider>() {
            },
            provider
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
        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/policies"),
            "GET",
            new TypeReference<List<String>>() {
            }
        );
    }

    /**
     * Updates Guardian's MFA authentication policies. A token with scope update:mfa_policies is needed.
     *
     * @return a Request to execute
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/put_policies">Management API2 docs</a>
     */
    public Request<List<String>> updateAuthenticationPolicies(List<String> policies) {
        Asserts.assertNotNull(policies, "policies");

        return createRequest(
            (builder) -> builder.addPathSegments("api/v2/guardian/policies"),
            "PUT",
            new TypeReference<List<String>>() {
            },
            policies
        );
    }
}
