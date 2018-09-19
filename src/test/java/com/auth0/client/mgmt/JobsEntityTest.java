package com.auth0.client.mgmt;

import com.auth0.json.mgmt.jobs.Job;
import com.auth0.json.mgmt.jobs.JobError;
import com.auth0.json.mgmt.jobs.UsersExport;
import com.auth0.net.Request;
import com.auth0.net.StatusCodeRequest;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.MGMT_JOB_GET;
import static com.auth0.client.MockServer.MGMT_JOB_POST_USERS_EXPORT;
import static com.auth0.client.MockServer.MGMT_JOB_POST_VERIFICATION_EMAIL;
import static com.auth0.client.MockServer.bodyFromRequest;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class JobsEntityTest extends BaseMgmtEntityTest {

    private static final String CONNECTION_ID = "con_0000000000000001";
    private static final String CSV = "csv";
    private static final String JOB_ID = "job_0000000000000001";

    @Test
    public void shouldSendAUserAVerificationEmail() throws Exception {
        Request<Job> request = api.jobs().sendVerificationEmail("google-oauth2|1234", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_VERIFICATION_EMAIL, 200);
        Job response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/verification-email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("user_id", "google-oauth2|1234"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldSendUserAVerificationEmailWithClientId() throws Exception {
        Request<Job> request = api.jobs().sendVerificationEmail("google-oauth2|1234", "AaiyAPdpYdesoKnqjj8HJqRn4T5titww");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_VERIFICATION_EMAIL, 200);
        Job response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/verification-email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("user_id", (Object) "google-oauth2|1234"));
        assertThat(body, hasEntry("client_id", (Object) "AaiyAPdpYdesoKnqjj8HJqRn4T5titww"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnNullUserId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.jobs().sendVerificationEmail(null, null);
    }

    @Test
    public void shouldRequestUsersExport() throws Exception {
        final List<List<Map<String, String>>> fields = Collections.singletonList(new ArrayList<Map<String, String>>() {{
            add(Collections.singletonMap("name", "user_id"));
        }});

        final UsersExport usersExport = new UsersExport(
                CONNECTION_ID,
                CSV,
                5,
                fields
        );

        final Request<Job> request = api.jobs().exportUsers(usersExport);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORT, 200);
        final Job response = request.execute();
        final RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-exports"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        final Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(4));
        assertThat(body, hasEntry("connection_id", CONNECTION_ID));
        assertThat(body, hasEntry("format", CSV));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRequestUsersExportWithEmptyFields() throws Exception {
        final List<List<Map<String, String>>> fields = Collections.singletonList(new ArrayList<>());
        final UsersExport usersExport = new UsersExport(
                CONNECTION_ID,
                "",
                5,
                fields
        );

        final Request<Job> request = api.jobs().exportUsers(usersExport);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORT, 200);
        final Job response = request.execute();
        final RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-exports"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        final Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("connection_id", CONNECTION_ID));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRequestUsersExportWithNullFields() throws Exception {
        final UsersExport usersExport = new UsersExport(
                CONNECTION_ID,
                CSV,
                5,
                null
        );

        final Request<Job> request = api.jobs().exportUsers(usersExport);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORT, 200);
        final Job response = request.execute();
        final RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-exports"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        final Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("connection_id", CONNECTION_ID));
        assertThat(body, hasEntry("format", CSV));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRequestUsersExportWithoutUsersExportObject() throws Exception {
        final Request<Job> request = api.jobs().exportUsers();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORT, 200);
        final Job response = request.execute();
        final RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-exports"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        final Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(0));
        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnNullUsersExport() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'usersExport' cannot be null!");
        api.jobs().exportUsers(null);
    }

    @Test
    public void shouldGetJob() throws Exception {
        final Request<Job> request = api.jobs().getJob(JOB_ID);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_GET, 200);
        final Job response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is("pending"));
    }

    @Test
    public void getJobShouldThrowOnNullJobId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'jobId' cannot be null!");
        api.jobs().getJob(null);
    }

    @Test
    public void shouldGetJobError() throws Exception {
        final StatusCodeRequest request = api.jobs().getJobError(JOB_ID);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);

        final JobError jobError = request.execute();
        assertThat(jobError.getStatusCode(), is(204));

        final RecordedRequest recordedRequest = server.takeRequest();
        assertThat(recordedRequest, hasMethodAndPath("GET", String.format("/api/v2/jobs/%s/errors", JOB_ID)));
    }

    @Test
    public void getJobErrorShouldThrowOnNullJobId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'jobId' cannot be null!");
        api.jobs().getJobError(null);
    }
}
