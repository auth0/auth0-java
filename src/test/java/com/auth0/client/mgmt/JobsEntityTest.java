package com.auth0.client.mgmt;

import com.auth0.json.mgmt.jobs.Job;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

public class JobsEntityTest extends BaseMgmtEntityTest {

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
//        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("user_id", "google-oauth2|1234"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnNullEmailRecipient() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'recipient' cannot be null!");
        api.jobs().sendVerificationEmail(null, null);
    }
}
