package com.auth0.client.mgmt;

import com.auth0.Asserts;
import com.auth0.json.mgmt.guardian.*;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

public class GuardianEntity extends BaseManagementEntity {

    GuardianEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Create a new Guardian Enrollment Ticket. A token with scope create:guardian_enrollment_tickets is needed.
     *
     * @param enrollmentTicket the enrollment ticket data to set.
     * @return a Request to execute.
     */
    public Request<EnrollmentTicket> createEnrollmentTicket(EnrollmentTicket enrollmentTicket) {
        Asserts.assertNotNull(enrollmentTicket, "enrollment ticket");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("enrollments")
                .addPathSegment("ticket")
                .build()
                .toString();

        CustomRequest<EnrollmentTicket> request = new CustomRequest<>(client, url, "POST", new TypeReference<EnrollmentTicket>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(enrollmentTicket);
        return request;
    }

    /**
     * Delete an existing Guardian Enrollment. A token with scope delete:guardian_enrollments is needed.
     *
     * @param enrollmentId the id of the enrollment to retrieve.
     * @return a Request to execute.
     */
    public Request deleteEnrollment(String enrollmentId) {
        Asserts.assertNotNull(enrollmentId, "enrollment id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("enrollments")
                .addPathSegment(enrollmentId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request the Guardian enrollment and verification Templates. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     */
    public Request<GuardianTemplates> getTemplates() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .addPathSegment("sms")
                .addPathSegment("templates")
                .build()
                .toString();
        CustomRequest<GuardianTemplates> request = new CustomRequest<>(client, url, "GET", new TypeReference<GuardianTemplates>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Updates the existing Guardian enrollment and verification Templates. A token with scope update:guardian_factors is needed.
     *
     * @param guardianTemplates the templates data to set.
     * @return a Request to execute.
     */
    public Request<GuardianTemplates> updateTemplates(GuardianTemplates guardianTemplates) {
        Asserts.assertNotNull(guardianTemplates, "guardian templates");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .addPathSegment("sms")
                .addPathSegment("templates")
                .build()
                .toString();
        CustomRequest<GuardianTemplates> request = new CustomRequest<>(client, url, "PUT", new TypeReference<GuardianTemplates>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(guardianTemplates);
        return request;
    }

    /**
     * Request all the Guardian Factors. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     */
    public Request<List<Factor>> listFactors() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .build()
                .toString();
        CustomRequest<List<Factor>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Factor>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Guardian Factor. A token with scope update:guardian_factors is needed.
     *
     * @return a Request to execute.
     */
    public Request<Factor> updateFactor(String name, Boolean enabled) {
        Asserts.assertNotNull(name, "name");
        Asserts.assertNotNull(enabled, "enabled");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .addPathSegment(name)
                .build()
                .toString();
        CustomRequest<Factor> request = new CustomRequest<>(client, url, "PUT", new TypeReference<Factor>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.addParameter("enabled", enabled);
        return request;
    }

    /**
     * Request the Guardian's Twilio Factor Provider. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     */
    public Request<TwilioFactorProvider> getTwilioFactorProvider() {

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .addPathSegment("sms")
                .addPathSegment("providers")
                .addPathSegment("twilio")
                .build()
                .toString();
        CustomRequest<TwilioFactorProvider> request = new CustomRequest<>(client, url, "GET", new TypeReference<TwilioFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update the existing Guardian's Twilio Factor Provider. A token with scope update:guardian_factors is needed.
     *
     * @param provider the provider data to set.
     * @return a Request to execute.
     */
    public Request<TwilioFactorProvider> updateTwilioFactorProvider(TwilioFactorProvider provider) {
        Asserts.assertNotNull(provider, "provider");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .addPathSegment("sms")
                .addPathSegment("providers")
                .addPathSegment("twilio")
                .build()
                .toString();
        CustomRequest<TwilioFactorProvider> request = new CustomRequest<>(client, url, "PUT", new TypeReference<TwilioFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(provider);
        return request;
    }

    /**
     * Request the Guardian's Sns Factor Provider. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     */
    public Request<SnsFactorProvider> getSnsFactorProvider() {

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .addPathSegment("push-notification")
                .addPathSegment("providers")
                .addPathSegment("sns")
                .build()
                .toString();
        CustomRequest<SnsFactorProvider> request = new CustomRequest<>(client, url, "GET", new TypeReference<SnsFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update the existing Guardian's Sns Factor Provider. A token with scope update:guardian_factors is needed.
     *
     * @param provider the provider data to set.
     * @return a Request to execute.
     */
    public Request<SnsFactorProvider> updateSnsFactorProvider(SnsFactorProvider provider) {
        Asserts.assertNotNull(provider, "provider");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .addPathSegment("push-notification")
                .addPathSegment("providers")
                .addPathSegment("sns")
                .build()
                .toString();
        CustomRequest<SnsFactorProvider> request = new CustomRequest<>(client, url, "PUT", new TypeReference<SnsFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(provider);
        return request;
    }


}
