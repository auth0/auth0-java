package com.auth0.client.mgmt;

import com.auth0.json.mgmt.guardian.*;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class GuardianEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnDeleteGuardianEnrollmentWithNullId() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'enrollment id' cannot be null!");
        api.guardian().deleteGuardianEnrollment(null);
    }

    @Test
    public void shouldDeleteGuardianEnrollment() throws Exception {
        Request request = api.guardian().deleteGuardianEnrollment("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_ENROLLMENT, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/guardian/enrollments/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnCreateGuardianEnrollmentTicketWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'enrollment ticket' cannot be null!");
        api.guardian().createGuardianEnrollmentTicket(null);
    }

    @Test
    public void shouldCreateGuardianEnrollmentTicket() throws Exception {
        Request<GuardianEnrollmentTicket> request = api.guardian().createGuardianEnrollmentTicket(new GuardianEnrollmentTicket("1"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_ENROLLMENT_TICKET, 200);
        GuardianEnrollmentTicket response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/guardian/enrollments/ticket"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("user_id", (Object) "1"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetGuardianTemplates() throws Exception {
        Request<GuardianTemplates> request = api.guardian().getGuardianTemplates();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TEMPLATES, 200);
        GuardianTemplates response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/guardian/factors/sms/templates"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateGuardianTemplatesWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'guardian templates' cannot be null!");
        api.guardian().updateGuardianTemplates(null);
    }

    @Test
    public void shouldUpdateGuardianTemplates() throws Exception {
        Request<GuardianTemplates> request = api.guardian().updateGuardianTemplates(new GuardianTemplates("msg", "msg"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TEMPLATES, 200);
        GuardianTemplates response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PUT", "/api/v2/guardian/factors/sms/templates"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldListGuardianFactors() throws Exception {
        Request<List<GuardianFactor>> request = api.guardian().listGuardianFactors();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_FACTORS_LIST, 200);
        List<GuardianFactor> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/guardian/factors"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyGuardianFactors() throws Exception {
        Request<List<GuardianFactor>> request = api.guardian().listGuardianFactors();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<GuardianFactor> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(GuardianFactor.class)));
    }

    @Test
    public void shouldThrowOnUpdateGuardianFactorWithNullName() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'name' cannot be null!");
        api.guardian().updateGuardianFactor(null, true);
    }

    @Test
    public void shouldThrowOnUpdateGuardianFactorWithNullEnabled() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'enabled' cannot be null!");
        api.guardian().updateGuardianFactor("my-factor", null);
    }

    @Test
    public void shouldUpdateGuardianFactor() throws Exception {
        Request<GuardianFactor> request = api.guardian().updateGuardianFactor("my-factor", true);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_FACTOR, 200);
        GuardianFactor response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PUT", "/api/v2/guardian/factors/my-factor"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("enabled", (Object) true));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetGuardianTwilioFactorProvider() throws Exception {
        Request<GuardianTwilioFactorProvider> request = api.guardian().getGuardianTwilioFactorProvider();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TWILIO_FACTOR_PROVIDER, 200);
        GuardianTwilioFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/guardian/factors/sms/providers/twilio"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateGuardianTwilioFactorProviderWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.guardian().updateGuardianTwilioFactorProvider(null);
    }

    @Test
    public void shouldUpdateGuardianTwilioFactorProvider() throws Exception {
        GuardianTwilioFactorProvider provider = new GuardianTwilioFactorProvider();
        provider.setAuthToken("token");
        Request<GuardianTwilioFactorProvider> request = api.guardian().updateGuardianTwilioFactorProvider(provider);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TWILIO_FACTOR_PROVIDER, 200);
        GuardianTwilioFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PUT", "/api/v2/guardian/factors/sms/providers/twilio"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("auth_token", (Object) "token"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetGuardianSnsFactorProvider() throws Exception {
        Request<GuardianSnsFactorProvider> request = api.guardian().getGuardianSnsFactorProvider();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_SNS_FACTOR_PROVIDER, 200);
        GuardianSnsFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/guardian/factors/push-notification/providers/sns"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateGuardianSnsFactorProviderWithNullData() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.guardian().updateGuardianSnsFactorProvider(null);
    }

    @Test
    public void shouldUpdateGuardianSnsFactorProvider() throws Exception {
        GuardianSnsFactorProvider provider = new GuardianSnsFactorProvider();
        provider.setAwsRegion("region");
        Request<GuardianSnsFactorProvider> request = api.guardian().updateGuardianSnsFactorProvider(provider);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_SNS_FACTOR_PROVIDER, 200);
        GuardianSnsFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PUT", "/api/v2/guardian/factors/push-notification/providers/sns"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("aws_region", (Object) "region"));

        assertThat(response, is(notNullValue()));
    }
}
