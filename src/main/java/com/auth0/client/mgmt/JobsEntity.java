package com.auth0.client.mgmt;

import com.auth0.json.mgmt.jobs.Job;
import com.auth0.json.mgmt.jobs.UsersExport;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.StatusCodeRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.HashMap;
import java.util.List;
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
     * See <a href=https://auth0.com/docs/api/management/v2#!/Jobs/post_verification_email>Post Verification Email</a>
     *
     * @param userId The user_id of the user to whom the email will be sent.
     * @param clientId The id of the client, if not provided the global one will be used.
     * @return a Request to execute.
     */
    public Request<Job> sendVerificationEmail(String userId, String clientId) {
        Asserts.assertNotNull(userId, "user id");

        final String url = this.generateUrl("api/v2/jobs/verification-email");

        final Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("user_id", userId);
        if (this.isValued(clientId)) {
            requestBody.put("client_id", clientId);
        }

        return this.generatePostRequest(url, requestBody);
    }

    /**
     * Generates a job request to produce a long-running user export job.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Jobs/post_users_exports">Post Users Exports</a>
     *
     * @return A request to execute the job.
     */
    public Request<Job> exportUsers() { return this.exportUsers(new UsersExport()); }

    /**
     * Generates a job request to produce a long-running user export job.
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Jobs/post_users_exports">Post Users Exports</a>
     *
     * @param usersExport Object containing the parameters by which the user export job will be run.
     * @return A request to execute the job.
     */
    public Request<Job> exportUsers(final UsersExport usersExport) {
        Asserts.assertNotNull(usersExport, "usersExport");

        final String url = this.generateUrl("api/v2/jobs/users-exports");

        final Map<String, Object> requestBody = new HashMap<>();
        final String connectionId = usersExport.getConnectionId();
        final String format = usersExport.getFormat();
        final int limit = usersExport.getLimit();
        final List<List<Map<String, String>>> fields = usersExport.getFields();

        if (this.isValued(connectionId)) {
            requestBody.put("connection_id", connectionId);
        }

        if (this.isValued(format)) {
            requestBody.put("format", format);
        }

        if (limit != 0) {
            requestBody.put("limit", Integer.toString(limit));
        }

        if ((fields != null) && (fields.size() > 0)) {
            requestBody.put("fields", fields);
        }

        return this.generatePostRequest(url, requestBody);
    }

    /**
     * Generates a request to get a job.  Useful to check its status.
     * @see <a href="https://auth0.com/docs/api/management/v2/#!/Jobs/get_results">Get a Job</a>
     *
     * @param jobId Id of the job to be retrieved
     * @return A request to execute the job details retrieval.
     */
    public Request<Job> getJob(final String jobId) {
        Asserts.assertNotNull(jobId, "jobId");
        final String url = this.generateUrl(String.format("api/v2/jobs/%s", jobId));
        return this.generateJobRequest(url);
    }

    /**
     * Generates a request to get the details of failed a job.
     * @see <a href="https://auth0.com/docs/api/management/v2/#!/Jobs/get_errors">Get Job Error Details</a>
     *
     * @param jobId Id of the job error details to be retrieved
     * @return A request to execute the job error details retrieval.
     */
    public StatusCodeRequest getJobError(final String jobId) {
        Asserts.assertNotNull(jobId, "jobId");
        final String url = this.generateUrl(String.format("api/v2/jobs/%s/errors", jobId));
        return this.generateJobErrorRequest(url);
    }

    private String generateUrl(final String restEndpoint) {
        return baseUrl
                .newBuilder()
                .addPathSegments(restEndpoint)
                .build()
                .toString();
    }

    private boolean isValued(final String string) {
        return (string != null) && (!string.isEmpty());
    }

    private CustomRequest<Job> generatePostRequest(final String url, final Map<String, Object> requestBody) {
        final CustomRequest<Job> request = new CustomRequest<>(client, url, "POST", new TypeReference<Job>() {});
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(requestBody);
        return request;
    }

    private CustomRequest<Job> generateJobRequest(final String url) {
        final CustomRequest<Job> request = new CustomRequest<>(client, url, "GET", new TypeReference<Job>() {});
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    private StatusCodeRequest generateJobErrorRequest(final String url) {
        final StatusCodeRequest request = new StatusCodeRequest(client, url);
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
}
