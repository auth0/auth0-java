package com.auth0.client.mgmt;

import com.auth0.json.mgmt.jobs.Job;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.Map;

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
     * @param userId The user_id of the user to whom the email will be sent.
     * @param clientId The id of the client, if not provided the global one will be used.
     * @return a Request to execute.
     */
    public Request<Job> sendVerificationEmail(String userId, String clientId) {
        Asserts.assertNotNull(userId, "recipient");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/jobs/verification-email")
                .build()
                .toString();

        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("user_id", userId);
        if (clientId != null && !clientId.isEmpty()) {
            requestBody.put("client_id", clientId);
        }

        CustomRequest<Job> request = new CustomRequest<>(client, url, "POST", new TypeReference<Job>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(requestBody);
        return request;
    }
}
