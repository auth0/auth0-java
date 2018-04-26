package com.auth0.client.mgmt;

import com.auth0.json.mgmt.jobs.Job;
import com.auth0.json.mgmt.jobs.EmailRecipient;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Jobs methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Jobs
 */
@SuppressWarnings("WeakerAccess")
public class JobsEntity extends BaseManagementEntity {

    JobsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Sends an Email Verification. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/post_verification_email
     *
     * @param recipient the email verification recipient.
     * @return a Request to execute.
     */
    public Request<Job> sendVerificationEmail(EmailRecipient recipient) {
        Asserts.assertNotNull(recipient, "recipient");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/jobs/verification-email")
                .build()
                .toString();

        CustomRequest<Job> request = new CustomRequest<>(client, url, "POST", new TypeReference<Job>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(recipient);
        return request;
    }
}
