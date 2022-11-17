package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.UsersExportFilter;
import com.auth0.client.mgmt.filter.UsersImportOptions;
import com.auth0.json.mgmt.EmailVerificationIdentity;
import com.auth0.json.mgmt.jobs.Job;
import com.auth0.json.mgmt.jobs.JobErrorDetails;
import com.auth0.net.CustomRequest;
import com.auth0.net.MultipartRequest;
import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.Auth0HttpResponse;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Jobs methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Jobs
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class JobsEntity extends BaseManagementEntity {

    JobsEntity(Auth0HttpClient client, HttpUrl baseUrl, String apiToken) {
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

        CustomRequest<Job> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<Job>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Get error details of a failed job. A token with scope create:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/get_errors.
     *
     * @param jobId the id of the job to retrieve.
     * @return a Request to execute.
     */
    public Request<List<JobErrorDetails>> getErrorDetails(String jobId) {
        Asserts.assertNotNull(jobId, "job id");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/jobs")
            .addPathSegment(jobId)
            .addPathSegment("errors")
            .build()
            .toString();

        TypeReference<List<JobErrorDetails>> jobErrorDetailsListType = new TypeReference<List<JobErrorDetails>>() {
        };
        CustomRequest<List<JobErrorDetails>> request = new CustomRequest<List<JobErrorDetails>>(client, url, HttpMethod.GET, jobErrorDetailsListType) {
            @Override
            protected List<JobErrorDetails> readResponseBody(Auth0HttpResponse response) throws IOException {
                if (response.getBody() == null || response.getBody().length() == 0) {
                    return Collections.emptyList();
                }
                return super.readResponseBody(response);
            }
        };
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Sends an Email Verification. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/post_verification_email.
     *
     * @param userId   The user_id of the user to whom the email will be sent.
     * @param clientId The id of the client, if not provided the global one will be used.
     *
     * @return a Request to execute.
     *
     * @see JobsEntity#sendVerificationEmail(String, String, EmailVerificationIdentity)
     */
    public Request<Job> sendVerificationEmail(String userId, String clientId) {
        return sendVerificationEmail(userId, clientId, null);
    }

    /**
     * Sends an Email Verification. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/post_verification_email.
     *
     * @param userId   The user_id of the user to whom the email will be sent.
     * @param clientId The id of the client, if not provided the global one will be used.
     * @param emailVerificationIdentity The identity of the user. Required to verify primary identities when using social, enterprise, or passwordless connections. It is also required to verify secondary identities.
     *
     * @return a Request to execute.
     *
     * @see JobsEntity#sendVerificationEmail(String, String)
     */
    public Request<Job> sendVerificationEmail(String userId, String clientId, EmailVerificationIdentity emailVerificationIdentity) {
        return sendVerificationEmail(userId, clientId, emailVerificationIdentity, null);
    }

    /**
     * Sends an Email Verification. A token with scope update:users is needed.
     * See https://auth0.com/docs/api/management/v2#!/Jobs/post_verification_email.
     *
     * @param userId   The user_id of the user to whom the email will be sent.
     * @param clientId The id of the client, if not provided the global one will be used.
     * @param emailVerificationIdentity The identity of the user. Required to verify primary identities when using social, enterprise, or passwordless connections. It is also required to verify secondary identities.
     * @param orgId The organization ID. If provided, the organization_id and organization_name will be included as query arguments in the link back to the application.
     *
     * @return a Request to execute.
     *
     * @see JobsEntity#sendVerificationEmail(String, String)
     */
    public Request<Job> sendVerificationEmail(String userId, String clientId, EmailVerificationIdentity emailVerificationIdentity, String orgId) {
        Asserts.assertNotNull(userId, "user id");

        String url = baseUrl
            .newBuilder()
            .addPathSegments("api/v2/jobs/verification-email")
            .build()
            .toString();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", userId);
        if (clientId != null && !clientId.isEmpty()) {
            requestBody.put("client_id", clientId);
        }
        if (orgId != null && !orgId.isEmpty()) {
            requestBody.put("organization_id", orgId);
        }
        if (emailVerificationIdentity != null) {
            Asserts.assertNotNull(emailVerificationIdentity.getProvider(), "identity provider");
            Asserts.assertNotNull(emailVerificationIdentity.getUserId(), "identity user id");
            requestBody.put("identity", emailVerificationIdentity);
        }
        CustomRequest<Job> request = new CustomRequest<>(client, url, HttpMethod.POST, new TypeReference<Job>() {
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

        CustomRequest<Job> request = new CustomRequest<>(client, url, HttpMethod.POST, new TypeReference<Job>() {
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
     * @param options      Optional parameters to set. Can be null.
     * @return a Request to execute.
     */
    public Request<Job> importUsers(String connectionId, File users, UsersImportOptions options) {
        Asserts.assertNotNull(connectionId, "connection id");
        Asserts.assertNotNull(users, "users file");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/jobs/users-imports")
                .build()
                .toString();

        MultipartRequest<Job> request = new MultipartRequest<>(client, url, HttpMethod.POST, new TypeReference<Job>() {
        });
        if (options != null) {
            for (Map.Entry<String, Object> e : options.getAsMap().entrySet()) {
                request.addPart(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        request.addPart("connection_id", connectionId);
        request.addPart("users", users, "text/json");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
}
