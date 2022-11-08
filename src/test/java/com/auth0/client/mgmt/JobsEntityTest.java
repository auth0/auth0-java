package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.UsersExportFilter;
import com.auth0.client.mgmt.filter.UsersImportOptions;
import com.auth0.json.mgmt.EmailVerificationIdentity;
import com.auth0.json.mgmt.jobs.Job;
import com.auth0.json.mgmt.jobs.JobErrorDetails;
import com.auth0.json.mgmt.jobs.UsersExportField;
import com.auth0.net.Request;
import com.auth0.net.multipart.FilePart;
import com.auth0.net.multipart.KeyValuePart;
import com.auth0.net.multipart.RecordedMultipartRequest;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB, 200);
        Job response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/jobs/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetJobErrorDetailsWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'job id' cannot be null!");
        api.jobs().getErrorDetails(null);
    }

    @Test
    public void shouldGetJobErrorDetails() throws Exception {
        Request<List<JobErrorDetails>> request = api.jobs().getErrorDetails("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_ERROR_DETAILS, 200);
        List<JobErrorDetails> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/jobs/1/errors"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(1));
        assertThat(response.get(0).getErrors(), hasSize(1));
    }

    @Test
    public void shouldGetJobErrorDetails_noErrors() throws Exception {
        Request<List<JobErrorDetails>> request = api.jobs().getErrorDetails("1");
        assertThat(request, is(notNullValue()));

        server.noContentResponse();
        List<JobErrorDetails> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/jobs/1/errors"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, is(empty()));
    }

    @Test
    public void shouldThrowOnRequestUsersExportWithNullConnectionId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection id' cannot be null!");
        api.jobs().exportUsers(null, null);
    }

    @Test
    public void shouldNotThrowOnRequestUsersExportWithNullFilter() {
        api.jobs().exportUsers("con_123456789", null);
    }

    @Test
    public void shouldRequestUsersExport() throws Exception {
        Request<Job> request = api.jobs().exportUsers("con_123456789", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORTS, 200);
        Job response = request.execute().getBody();
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
    public void shouldRequestUsersExportWithLimit() throws Exception {
        UsersExportFilter filter = new UsersExportFilter();
        filter.withLimit(82);
        Request<Job> request = api.jobs().exportUsers("con_123456789", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORTS, 200);
        Job response = request.execute().getBody();
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
    public void shouldRequestUsersExportWithFormat() throws Exception {
        UsersExportFilter filter = new UsersExportFilter();
        filter.withFormat("csv");
        Request<Job> request = api.jobs().exportUsers("con_123456789", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORTS, 200);
        Job response = request.execute().getBody();
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
    public void shouldRequestUsersExportWithFields() throws Exception {
        UsersExportFilter filter = new UsersExportFilter();
        ArrayList<UsersExportField> fields = new ArrayList<>();
        fields.add(new UsersExportField("full_name"));
        fields.add(new UsersExportField("user_metadata.company_name", "company"));
        filter.withFields(fields);
        Request<Job> request = api.jobs().exportUsers("con_123456789", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_EXPORTS, 200);
        Job response = request.execute().getBody();
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
    public void shouldSendUserAVerificationEmailWithNullClientIdAndEmailVerification() throws Exception {
        Request<Job> request = api.jobs().sendVerificationEmail("google-oauth2|1234", null, null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_VERIFICATION_EMAIL, 200);
        Job response = request.execute().getBody();
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
    public void shouldSendUserVerificationEmailWithClientId() throws Exception {
        Request<Job> request = api.jobs().sendVerificationEmail("google-oauth2|1234", "AaiyAPdpYdesoKnqjj8HJqRn4T5titww");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_VERIFICATION_EMAIL, 200);
        Job response = request.execute().getBody();
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
    public void shouldSendUserVerificationEmailWithIdentity() throws Exception {
        EmailVerificationIdentity identity = new EmailVerificationIdentity("google-oauth2", "1234");
        Request<Job> request = api.jobs().sendVerificationEmail("google-oauth2|1234", "AaiyAPdpYdesoKnqjj8HJqRn4T5titww", identity);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_VERIFICATION_EMAIL, 200);
        Job response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/verification-email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("user_id", "google-oauth2|1234"));
        assertThat(body, hasEntry("client_id", "AaiyAPdpYdesoKnqjj8HJqRn4T5titww"));

        Map<String, String> identityMap = new HashMap<>();
        identityMap.put("provider", "google-oauth2");
        identityMap.put("user_id", "1234");
        assertThat(body, hasEntry("identity", identityMap));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldSendUserVerificationEmailWithOrgId() throws Exception {
        Request<Job> request = api.jobs().sendVerificationEmail("google-oauth2|1234", "client_abc", null, "org_abc");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_VERIFICATION_EMAIL, 200);
        Job response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/verification-email"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("user_id", "google-oauth2|1234"));
        assertThat(body, hasEntry("client_id", "client_abc"));
        assertThat(body, hasEntry("organization_id", "org_abc"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnSendUserVerificationEmailWithNullIdentityProvider() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'identity provider' cannot be null!");
        api.jobs().sendVerificationEmail("google-oauth2|1234", null, new EmailVerificationIdentity(null, "user-id"));
    }

    @Test
    public void shouldThrowOnSendUserVerificationEmailWithNullIdentityUserId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'identity user id' cannot be null!");
        api.jobs().sendVerificationEmail("google-oauth2|1234", null, new EmailVerificationIdentity("google-oauth2", null));
    }

    @Test
    public void shouldThrowOnSendUserVerificationEmailWithNullIdentityProviderAndUserId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'identity provider' cannot be null!");
        api.jobs().sendVerificationEmail("google-oauth2|1234", null, new EmailVerificationIdentity(null, null));
    }

    @Test
    public void shouldThrowOnRequestUsersImportWithNullConnectionId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'connection id' cannot be null!");
        File usersFile = mock(File.class);
        when(usersFile.exists()).thenReturn(true);
        UsersImportOptions options = mock(UsersImportOptions.class);
        api.jobs().importUsers(null, usersFile, options);
    }

    @Test
    public void shouldThrowOnRequestUsersImportWithNullUsersFile() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'users file' cannot be null!");
        UsersImportOptions options = mock(UsersImportOptions.class);
        api.jobs().importUsers("con_123456789", null, options);
    }

    @Test
    public void shouldNotThrowOnRequestUsersImportWithNullOptions() {
        File usersFile = mock(File.class);
        when(usersFile.exists()).thenReturn(true);
        api.jobs().importUsers("con_123456789", usersFile, null);
    }

    @Test
    public void shouldRequestUsersImport() throws Exception {
        File usersFile = new File(MGMT_JOB_POST_USERS_IMPORTS_INPUT);
        Request<Job> request = api.jobs().importUsers("con_123456789", usersFile, null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_IMPORTS, 200);
        Job response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-imports"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        String ctHeader = recordedRequest.getHeader("Content-Type");
        assertThat(ctHeader, startsWith("multipart/form-data"));
        String[] ctParts = ctHeader.split("multipart/form-data; boundary=");

        RecordedMultipartRequest recordedMultipartRequest = new RecordedMultipartRequest(recordedRequest);
        assertThat(recordedMultipartRequest.getPartsCount(), is(2));
        assertThat(recordedMultipartRequest.getBoundary(), is(notNullValue()));
        assertThat(recordedMultipartRequest.getBoundary(), is(ctParts[1]));

        //Connection ID
        KeyValuePart formParam = recordedMultipartRequest.getKeyValuePart("connection_id");
        assertThat(formParam, is(notNullValue()));
        assertThat(formParam.getValue(), is("con_123456789"));

        //Users JSON
        FilePart jsonFile = recordedMultipartRequest.getFilePart("users");
        assertThat(jsonFile, is(notNullValue()));
        String utf8Contents = new String(Files.readAllBytes(usersFile.toPath()));
        assertThat(jsonFile.getContentType(), is("text/json"));
        assertThat(jsonFile.getFilename(), is("job_post_users_imports_input.json"));
        assertThat(jsonFile.getValue(), is(utf8Contents));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldRequestUsersImportWithOptions() throws Exception {
        UsersImportOptions options = new UsersImportOptions();
        options.withExternalId("ext_id123");
        options.withUpsert(true);
        options.withSendCompletionEmail(false);
        File usersFile = new File(MGMT_JOB_POST_USERS_IMPORTS_INPUT);
        Request<Job> request = api.jobs().importUsers("con_123456789", usersFile, options);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_JOB_POST_USERS_IMPORTS, 200);
        Job response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/jobs/users-imports"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        String ctHeader = recordedRequest.getHeader("Content-Type");
        assertThat(ctHeader, startsWith("multipart/form-data"));
        String[] ctParts = ctHeader.split("multipart/form-data; boundary=");

        RecordedMultipartRequest recordedMultipartRequest = new RecordedMultipartRequest(recordedRequest);
        assertThat(recordedMultipartRequest.getPartsCount(), is(5));
        assertThat(recordedMultipartRequest.getBoundary(), is(notNullValue()));
        assertThat(recordedMultipartRequest.getBoundary(), is(ctParts[1]));

        //Connection ID
        KeyValuePart connectionIdParam = recordedMultipartRequest.getKeyValuePart("connection_id");
        assertThat(connectionIdParam, is(notNullValue()));
        assertThat(connectionIdParam.getValue(), is("con_123456789"));

        //External ID
        KeyValuePart externalIdParam = recordedMultipartRequest.getKeyValuePart("external_id");
        assertThat(externalIdParam, is(notNullValue()));
        assertThat(externalIdParam.getValue(), is("ext_id123"));

        //Upsert
        KeyValuePart upsertParam = recordedMultipartRequest.getKeyValuePart("upsert");
        assertThat(upsertParam, is(notNullValue()));
        assertThat(upsertParam.getValue(), is("true"));

        //Send Completion Email
        KeyValuePart sendCompletionEmailParam = recordedMultipartRequest.getKeyValuePart("send_completion_email");
        assertThat(sendCompletionEmailParam, is(notNullValue()));
        assertThat(sendCompletionEmailParam.getValue(), is("false"));

        //Users JSON
        FilePart jsonFile = recordedMultipartRequest.getFilePart("users");
        assertThat(jsonFile, is(notNullValue()));
        String utf8Contents = new String(Files.readAllBytes(usersFile.toPath()));
        assertThat(jsonFile.getContentType(), is("text/json"));
        assertThat(jsonFile.getFilename(), is("job_post_users_imports_input.json"));
        assertThat(jsonFile.getValue(), is(utf8Contents));

        assertThat(response, is(notNullValue()));
    }

}
