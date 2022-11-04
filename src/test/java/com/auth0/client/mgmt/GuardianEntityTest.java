package com.auth0.client.mgmt;

import com.auth0.json.mgmt.guardian.*;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class GuardianEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnDeleteGuardianEnrollmentWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'enrollment id' cannot be null!");
        api.guardian().deleteEnrollment(null);
    }

    @Test
    public void shouldDeleteGuardianEnrollment() throws Exception {
        Request<Void> request = api.guardian().deleteEnrollment("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_ENROLLMENT, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/guardian/enrollments/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnCreateGuardianEnrollmentTicketWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'enrollment ticket' cannot be null!");
        api.guardian().createEnrollmentTicket(null);
    }

    @Test
    public void shouldCreateGuardianEnrollmentTicket() throws Exception {
        Request<EnrollmentTicket> request = api.guardian().createEnrollmentTicket(new EnrollmentTicket("1"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_ENROLLMENT_TICKET, 200);
        EnrollmentTicket response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/guardian/enrollments/ticket"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("user_id", "1"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetGuardianTemplates() throws Exception {
        Request<GuardianTemplates> request = api.guardian().getTemplates();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TEMPLATES, 200);
        GuardianTemplates response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/guardian/factors/sms/templates"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateGuardianTemplatesWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'guardian templates' cannot be null!");
        api.guardian().updateTemplates(null);
    }

    @Test
    public void shouldUpdateGuardianTemplates() throws Exception {
        Request<GuardianTemplates> request = api.guardian().updateTemplates(new GuardianTemplates("msg", "msg"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TEMPLATES, 200);
        GuardianTemplates response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/guardian/factors/sms/templates"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldListGuardianFactors() throws Exception {
        Request<List<Factor>> request = api.guardian().listFactors();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_FACTORS_LIST, 200);
        List<Factor> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/guardian/factors"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyGuardianFactors() throws Exception {
        Request<List<Factor>> request = api.guardian().listFactors();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<Factor> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(Factor.class)));
    }

    @Test
    public void shouldThrowOnUpdateGuardianFactorWithNullName() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'name' cannot be null!");
        api.guardian().updateFactor(null, true);
    }

    @Test
    public void shouldThrowOnUpdateGuardianFactorWithNullEnabled() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'enabled' cannot be null!");
        api.guardian().updateFactor("my-factor", null);
    }

    @Test
    public void shouldUpdateGuardianFactor() throws Exception {
        Request<Factor> request = api.guardian().updateFactor("my-factor", true);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_FACTOR, 200);
        Factor response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/guardian/factors/my-factor"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("enabled", true));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetGuardianTwilioFactorProviderWithMSSID() throws Exception {
        Request<TwilioFactorProvider> request = api.guardian().getTwilioFactorProvider();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TWILIO_FACTOR_PROVIDER_WITH_MSSID, 200);
        TwilioFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/guardian/factors/sms/providers/twilio"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetGuardianTwilioFactorProviderWithFrom() throws Exception {
        Request<TwilioFactorProvider> request = api.guardian().getTwilioFactorProvider();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TWILIO_FACTOR_PROVIDER_WITH_FROM, 200);
        TwilioFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/guardian/factors/sms/providers/twilio"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateGuardianTwilioFactorProviderWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.guardian().updateTwilioFactorProvider(null);
    }

    @Test
    public void shouldUpdateGuardianTwilioFactorProviderWithFrom() throws Exception {
        TwilioFactorProvider provider = new TwilioFactorProvider("+156789", null, "aToKen", "3123");
        Request<TwilioFactorProvider> request = api.guardian().updateTwilioFactorProvider(provider);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TWILIO_FACTOR_PROVIDER_WITH_FROM, 200);
        TwilioFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/guardian/factors/sms/providers/twilio"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("from", "+156789"));
        assertThat(body, hasEntry("auth_token", "aToKen"));
        assertThat(body, hasEntry("sid", "3123"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getFrom(), is(equalTo("+156789")));
        assertThat(response.getMessagingServiceSID(), is(nullValue()));
        assertThat(response.getAuthToken(), is(equalTo("aToKen")));
        assertThat(response.getSID(), is(equalTo("3123")));
    }

    @Test
    public void shouldUpdateGuardianTwilioFactorProviderWithMSSID() throws Exception {
        TwilioFactorProvider provider = new TwilioFactorProvider(null, "aac", "aToKen", "3123");
        Request<TwilioFactorProvider> request = api.guardian().updateTwilioFactorProvider(provider);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TWILIO_FACTOR_PROVIDER_WITH_MSSID, 200);
        TwilioFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/guardian/factors/sms/providers/twilio"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("messaging_service_sid", "aac"));
        assertThat(body, hasEntry("auth_token", "aToKen"));
        assertThat(body, hasEntry("sid", "3123"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getFrom(), is(nullValue()));
        assertThat(response.getMessagingServiceSID(), is(equalTo("aac")));
        assertThat(response.getAuthToken(), is(equalTo("aToKen")));
        assertThat(response.getSID(), is(equalTo("3123")));
    }

    @Test
    public void shouldResetGuardianTwilioFactorProvider() throws Exception {
        Request<TwilioFactorProvider> request = api.guardian().resetTwilioFactorProvider();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_TWILIO_FACTOR_PROVIDER_EMPTY, 200);
        TwilioFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/guardian/factors/sms/providers/twilio"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(0));

        assertThat(response, is(notNullValue()));
        assertThat(response.getFrom(), is(nullValue()));
        assertThat(response.getMessagingServiceSID(), is(nullValue()));
        assertThat(response.getAuthToken(), is(nullValue()));
        assertThat(response.getSID(), is(nullValue()));
    }

    @Test
    public void shouldGetGuardianSnsFactorProvider() throws Exception {
        Request<SNSFactorProvider> request = api.guardian().getSNSFactorProvider();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_SNS_FACTOR_PROVIDER, 200);
        SNSFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/guardian/factors/push-notification/providers/sns"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdateGuardianSnsFactorProviderWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'provider' cannot be null!");
        api.guardian().updateSNSFactorProvider(null);
    }

    @Test
    public void shouldUpdateGuardianSnsFactorProvider() throws Exception {
        SNSFactorProvider provider = new SNSFactorProvider("awsAccessKeyId", "awsSecretAccessKey", "us-west-2", "APNS:platform:arn", "GCM:platform:arn");
        Request<SNSFactorProvider> request = api.guardian().updateSNSFactorProvider(provider);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_SNS_FACTOR_PROVIDER, 200);
        SNSFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/guardian/factors/push-notification/providers/sns"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(5));
        assertThat(body, hasEntry("aws_access_key_id", "awsAccessKeyId"));
        assertThat(body, hasEntry("aws_secret_access_key", "awsSecretAccessKey"));
        assertThat(body, hasEntry("aws_region", "us-west-2"));
        assertThat(body, hasEntry("sns_apns_platform_application_arn", "APNS:platform:arn"));
        assertThat(body, hasEntry("sns_gcm_platform_application_arn", "GCM:platform:arn"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldResetGuardianSnsFactorProvider() throws Exception {
        Request<SNSFactorProvider> request = api.guardian().resetSNSFactorProvider();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_SNS_FACTOR_PROVIDER_EMPTY, 200);
        SNSFactorProvider response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/guardian/factors/push-notification/providers/sns"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(0));

        assertThat(response, is(notNullValue()));
        assertThat(response.getAWSAccessKeyId(), is(nullValue()));
        assertThat(response.getAWSRegion(), is(nullValue()));
        assertThat(response.getAWSSecretAccessKey(), is(nullValue()));
        assertThat(response.getSNSAPNSPlatformApplicationARN(), is(nullValue()));
        assertThat(response.getSNSGCMPlatformApplicationARN(), is(nullValue()));
    }

    @Test
    public void shouldGetAuthenticationPolicies() throws Exception {
        Request<List<String>> request = api.guardian().getAuthenticationPolicies();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_AUTHENTICATION_POLICIES_LIST, 200);
        List<String> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/guardian/policies"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, hasSize(1));
    }

    @Test
    public void shouldThrowOnUpdateAuthenticationPoliciesWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'policies' cannot be null!");
        api.guardian().updateAuthenticationPolicies(null);
    }

    @Test
    public void shouldUpdateAuthenticationPolicies() throws Exception {
        List<String> policies = Collections.singletonList("all-applications");
        Request<List<String>> request = api.guardian().updateAuthenticationPolicies(policies);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_GUARDIAN_AUTHENTICATION_POLICIES_LIST, 200);
        List<String> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/guardian/policies"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        List<Object> body = bodyListFromRequest(recordedRequest);
        assertThat(body, is(policies));

        assertThat(response, hasSize(1));
    }
}
