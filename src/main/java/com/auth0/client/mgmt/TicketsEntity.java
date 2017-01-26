package com.auth0.client.mgmt;

import com.auth0.utils.Asserts;
import com.auth0.json.mgmt.tickets.EmailVerificationTicket;
import com.auth0.json.mgmt.tickets.PasswordChangeTicket;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class TicketsEntity extends BaseManagementEntity {

    TicketsEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Create an Email Verification Ticket. A token with scope create:user_tickets is needed.
     *
     * @param emailVerificationTicket the email verification ticket data to set.
     * @return a Request to execute.
     */
    public Request<EmailVerificationTicket> requestEmailVerification(EmailVerificationTicket emailVerificationTicket) {
        Asserts.assertNotNull(emailVerificationTicket, "email verification ticket");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("tickets")
                .addPathSegment("email-verification")
                .build()
                .toString();

        CustomRequest<EmailVerificationTicket> request = new CustomRequest<>(client, url, "POST", new TypeReference<EmailVerificationTicket>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(emailVerificationTicket);
        return request;
    }

    /**
     * Create a Password Change Ticket. A token with scope create:user_tickets is needed.
     *
     * @param passwordChangeTicket the password change ticket data to set.
     * @return a Request to execute.
     */
    public Request<PasswordChangeTicket> requestPasswordChange(PasswordChangeTicket passwordChangeTicket) {
        Asserts.assertNotNull(passwordChangeTicket, "password change ticket");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("tickets")
                .addPathSegment("password-change")
                .build()
                .toString();

        CustomRequest<PasswordChangeTicket> request = new CustomRequest<>(client, url, "POST", new TypeReference<PasswordChangeTicket>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(passwordChangeTicket);
        return request;
    }

}
