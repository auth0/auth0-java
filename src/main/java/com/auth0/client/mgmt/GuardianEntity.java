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
     * Delete an existing Guardian Enrollment. A token with scope read:guardian_enrollments is needed.
     *
     * @param enrollmentId the id of the enrollment to retrieve.
     * @return a Request to execute.
     */
    public Request deleteGuardianEnrollment(String enrollmentId) {
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
     * Create a new Guardian Enrollment Ticket. A token with scope create:guardian_enrollment_tickets is needed.
     *
     * @param enrollmentTicket the enrollment ticket data to set.
     * @return a Request to execute.
     */
    public Request<GuardianEnrollmentTicket> createGuardianEnrollmentTicket(GuardianEnrollmentTicket enrollmentTicket) {
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

        CustomRequest<GuardianEnrollmentTicket> request = new CustomRequest<>(client, url, "POST", new TypeReference<GuardianEnrollmentTicket>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(enrollmentTicket);
        return request;
    }

    /**
     * Request the Guardian enrollment and verification Templates. A token with scope read:guardian_factors is needed.
     *
     * @return a Request to execute.
     */
    public Request<GuardianTemplates> getGuardianTemplates() {
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
    public Request<GuardianTemplates> updateGuardianTemplates(GuardianTemplates guardianTemplates) {
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
    public Request<List<GuardianFactor>> listGuardianFactors() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("guardian")
                .addPathSegment("factors")
                .build()
                .toString();
        CustomRequest<List<GuardianFactor>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<GuardianFactor>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Guardian Factor. A token with scope update:guardian_factors is needed.
     *
     * @return a Request to execute.
     */
    public Request<GuardianFactor> updateGuardianFactor(String name, Boolean enabled) {
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
        CustomRequest<GuardianFactor> request = new CustomRequest<>(client, url, "PUT", new TypeReference<GuardianFactor>() {
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
    public Request<GuardianTwilioFactorProvider> getGuardianTwilioFactorProvider() {

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
        CustomRequest<GuardianTwilioFactorProvider> request = new CustomRequest<>(client, url, "GET", new TypeReference<GuardianTwilioFactorProvider>() {
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
    public Request<GuardianTwilioFactorProvider> updateGuardianTwilioFactorProvider(GuardianTwilioFactorProvider provider) {
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
        CustomRequest<GuardianTwilioFactorProvider> request = new CustomRequest<>(client, url, "PUT", new TypeReference<GuardianTwilioFactorProvider>() {
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
    public Request<GuardianSnsFactorProvider> getGuardianSnsFactorProvider() {

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
        CustomRequest<GuardianSnsFactorProvider> request = new CustomRequest<>(client, url, "GET", new TypeReference<GuardianSnsFactorProvider>() {
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
    public Request<GuardianSnsFactorProvider> updateGuardianSnsFactorProvider(GuardianSnsFactorProvider provider) {
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
        CustomRequest<GuardianSnsFactorProvider> request = new CustomRequest<>(client, url, "PUT", new TypeReference<GuardianSnsFactorProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(provider);
        return request;
    }


}
