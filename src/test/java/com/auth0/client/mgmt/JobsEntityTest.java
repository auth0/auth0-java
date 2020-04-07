package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.UsersExportFilter;
import com.auth0.json.mgmt.jobs.Job;
import com.auth0.json.mgmt.jobs.UsersExportField;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

public class JobsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnGetJobWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'job id' cannot be null!");
        api.jobs().get(null);
    }

    @Test
    public void shouldGetJob() throws Exception {
        Request<Job> request = api.jobs().get("1");
        assertThat(request, Matchers.is(Matchers.notNullValue()));

        server.jsonResponse(MGMT_JOB, 200);
        Job response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/jobs/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, Matchers.is(Matchers.notNullValue()));
    }

    @Test
    public void shouldRequestAUsersExport() throws Exception {
        Request<Job> request = api.jobs().requestUsersExport("con_123456789", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORTS, 200);
        Job response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-exports"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("connection_id", "con_123456789"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRequestAUsersExportWithLimit() throws Exception {
        UsersExportFilter filter = new UsersExportFilter();
        filter.withLimit(82);
        Request<Job> request = api.jobs().requestUsersExport("con_123456789", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORTS, 200);
        Job response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-exports"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("connection_id", "con_123456789"));
        assertThat(body, hasEntry("limit", 82));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRequestAUsersExportWithFormat() throws Exception {
        UsersExportFilter filter = new UsersExportFilter();
        filter.withFormat("csv");
        Request<Job> request = api.jobs().requestUsersExport("con_123456789", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORTS, 200);
        Job response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-exports"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("connection_id", "con_123456789"));
        assertThat(body, hasEntry("format", "csv"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRequestAUsersExportWithFields() throws Exception {
        UsersExportFilter filter = new UsersExportFilter();
        ArrayList<UsersExportField> fields = new ArrayList<>();
        fields.add(new UsersExportField("full_name"));
        fields.add(new UsersExportField("user_metadata.company_name", "company"));
        filter.withFields(fields);
        Request<Job> request = api.jobs().requestUsersExport("con_123456789", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORTS, 200);
        Job response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-exports"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("connection_id", "con_123456789"));
        assertThat(body, hasKey("fields"));
        @SuppressWarnings("unchecked")
        List<Map<String, String>> bodyFields = (List<Map<String, String>>) body.get("fields");
        assertThat(bodyFields.get(0).get("name"), is("full_name"));
        assertThat(bodyFields.get(0).get("export_as"), is(nullValue()));
        assertThat(bodyFields.get(1).get("name"), is("user_metadata.company_name"));
        assertThat(bodyFields.get(1).get("export_as"), is("company"));

        assertThat(response, is(notNullValue()));
    }

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
        assertThat(body, hasEntry("user_id", "google-oauth2|1234"));
        assertThat(body, hasEntry("client_id", "AaiyAPdpYdesoKnqjj8HJqRn4T5titww"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnNullUserId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'user id' cannot be null!");
        api.jobs().sendVerificationEmail(null, null);
    }
}
