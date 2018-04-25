package com.auth0.client.mgmt;

import com.auth0.json.mgmt.jobs.Job;
import com.auth0.json.mgmt.jobs.EmailRecipient;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

@SuppressWarnings("WeakerAccess")
public class JobsEntity extends BaseManagementEntity {

    JobsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

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
