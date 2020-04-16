package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.UsersExportFilter;
import com.auth0.json.mgmt.jobs.Job;
import com.auth0.net.CustomRequest;
import com.auth0.net.MultipartRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.io.File;
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
     * Request a Job. A token with scope create:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/get_jobs_by_id.
     *
     * @param jobId the id of the job to retrieve.
     * @return a Request to execute.
     */
    public Request<Job> get(String jobId) {
        Asserts.assertNotNull(jobId, "job id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/jobs")
                .addPathSegment(jobId)
                .build()
                .toString();

        CustomRequest<Job> request = new CustomRequest<>(client, url, "GET", new TypeReference<Job>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Sends an Email Verification. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/post_verification_email.
     *
     * @param userId   The user_id of the user to whom the email will be sent.
     * @param clientId The id of the client, if not provided the global one will be used.
     * @return a Request to execute.
     */
    public Request<Job> sendVerificationEmail(String userId, String clientId) {
        Asserts.assertNotNull(userId, "user id");

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

    /**
     * Requests a Users Exports job. A token with scope read:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/post_users_exports.
     * See https://auth0.com/docs/users/guides/bulk-user-exports.
     *
     * @param connectionId The id of the connection to export the users from.
     * @param filter       the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<Job> exportUsers(String connectionId, UsersExportFilter filter) {
        Asserts.assertNotNull(connectionId, "connection id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/jobs/users-exports")
                .build()
                .toString();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("connection_id", connectionId);
        if (filter != null) {
            requestBody.putAll(filter.getAsMap());
        }

        CustomRequest<Job> request = new CustomRequest<>(client, url, "POST", new TypeReference<Job>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(requestBody);
        return request;
    }

    /**
     * Requests a Users Imports job. A token with scope write:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/post_users_imports.
     * See https://auth0.com/docs/users/guides/bulk-user-imports.
     *
     * @param connectionId The id of the connection to import the users to.
     * @param users        The users file. Must have an array with the users' information in JSON format.
     * @return a Request to execute.
     */
    public Request<Job> importUsers(String connectionId, File users) {
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(users, "users file");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/jobs/users-exports")
                .build()
                .toString();
        MultipartRequest<Job> request = new MultipartRequest<>(client, url, "POST", new TypeReference<Job>() {
        });
        request.addPart("connection_id", connectionId);
        request.addPart("users", users, "text/json");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
}
