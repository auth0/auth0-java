package com.auth0.client.mgmt;

import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.json.mgmt.guardian.EnrollmentTicket;
import com.auth0.json.mgmt.guardian.Factor;
import com.auth0.json.mgmt.guardian.GuardianTemplates;
import com.auth0.json.mgmt.guardian.SNSFactorProvider;
import com.auth0.json.mgmt.guardian.TwilioFactorProvider;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Guardian methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Guardian
 */
@SuppressWarnings("WeakerAccess")
public class GuardianEntity {
    private final RequestBuilder requestBuilder;
    GuardianEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
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

        return requestBuilder.post("api/v2/guardian/enrollments/ticket")
                             .body(enrollmentTicket)
                             .request(new TypeReference<EnrollmentTicket>() {
                             });
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

        return requestBuilder.delete("api/v2/guardian/enrollments", enrollmentId)
                             .request();
    }

    /**
     * Request the Guardian SMS enrollment and verification templates.
     * A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_templates">Management API2 docs</a>
     */
    public Request<GuardianTemplates> getTemplates() {

        return requestBuilder.get("api/v2/guardian/factors/sms/templates")
                             .request(new TypeReference<GuardianTemplates>() {
                             });
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

        return requestBuilder.put("api/v2/guardian/factors/sms/templates")
                             .body(guardianTemplates)
                             .request(new TypeReference<GuardianTemplates>() {
                             });
    }

    /**
     * Request all the Guardian Factors. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_factors">Management API2 docs</a>
     */
    public Request<List<Factor>> listFactors() {
        return requestBuilder.get("api/v2/guardian/factors")
                             .request(new TypeReference<List<Factor>>() {
                             });
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

        return requestBuilder.put("api/v2/guardian/factors", name)
                             .body(new Factor(enabled))
                             .request(new TypeReference<Factor>() {
                             });
    }

    /**
     * Request Guardian's Twilio SMS Factor Provider settings. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Guardian/get_twilio">Management API2 docs</a>
     */
    public Request<TwilioFactorProvider> getTwilioFactorProvider() {
        return requestBuilder.get("api/v2/guardian/factors/sms/providers/twilio")
                             .request(new TypeReference<TwilioFactorProvider>() {
                             });
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

        return requestBuilder.put("api/v2/guardian/factors/sms/providers/twilio")
                             .body(provider)
                             .request(new TypeReference<TwilioFactorProvider>() {
                             });
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

        return requestBuilder.get("api/v2/guardian/factors/push-notification/providers/sns")
                             .request(new TypeReference<SNSFactorProvider>() {
                             });
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

        return requestBuilder.put("api/v2/guardian/factors/push-notification/providers/sns")
                             .body(provider)
                             .request(new TypeReference<SNSFactorProvider>() {
                             });
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
