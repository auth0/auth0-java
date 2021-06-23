package com.auth0.client.mgmt;

import com.auth0.client.mgmt.tokens.TokenProvider;
import com.auth0.json.mgmt.guardian.*;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
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

    GuardianEntity(OkHttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/enrollments/ticket")
                .build()
                .toString();

        CustomRequest<EnrollmentTicket> request = new CustomRequest<>(client, url, "POST", new TypeReference<EnrollmentTicket>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        request.setBody(enrollmentTicket);
        return request;
    }

    /**
     * Delete an existing Guardian Enrollment. A token with scope delete:guardian_enrollments is needed.
     *
     * @param enrollmentId the id of the enrollment to retrieve.
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/delete_enrollments_by_id">Management API2 docs</a>
     */
    public Request deleteEnrollment(String enrollmentId) {
        Asserts.assertNotNull(enrollmentId, "enrollment id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/enrollments")
                .addPathSegment(enrollmentId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
    }

    /**
     * Request the Guardian SMS enrollment and verification templates.
     * A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_templates">Management API2 docs</a>
     */
    public Request<GuardianTemplates> getTemplates() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/factors/sms/templates")
                .build()
                .toString();
        CustomRequest<GuardianTemplates> request = new CustomRequest<>(client, url, "GET", new TypeReference<GuardianTemplates>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/factors/sms/templates")
                .build()
                .toString();
        CustomRequest<GuardianTemplates> request = new CustomRequest<>(client, url, "PUT", new TypeReference<GuardianTemplates>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        request.setBody(guardianTemplates);
        return request;
    }

    /**
     * Request all the Guardian Factors. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_factors">Management API2 docs</a>
     */
    public Request<List<Factor>> listFactors() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/factors")
                .build()
                .toString();
        CustomRequest<List<Factor>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Factor>>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/factors")
                .addPathSegment(name)
                .build()
                .toString();
        CustomRequest<Factor> request = new CustomRequest<>(client, url, "PUT", new TypeReference<Factor>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/factors/sms/providers/twilio")
                .build()
                .toString();
        CustomRequest<TwilioFactorProvider> request = new CustomRequest<>(client, url, "GET", new TypeReference<TwilioFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/factors/sms/providers/twilio")
                .build()
                .toString();
        CustomRequest<TwilioFactorProvider> request = new CustomRequest<>(client, url, "PUT", new TypeReference<TwilioFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        request.setBody(provider);
        return request;
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/factors/push-notification/providers/sns")
                .build()
                .toString();
        CustomRequest<SNSFactorProvider> request = new CustomRequest<>(client, url, "GET", new TypeReference<SNSFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/guardian/factors/push-notification/providers/sns")
                .build()
                .toString();
        CustomRequest<SNSFactorProvider> request = new CustomRequest<>(client, url, "PUT", new TypeReference<SNSFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        request.setBody(provider);
        return request;
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
}
