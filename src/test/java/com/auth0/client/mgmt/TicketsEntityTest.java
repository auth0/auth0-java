package com.auth0.client.mgmt;

import com.auth0.json.mgmt.tickets.EmailVerificationTicket;
import com.auth0.json.mgmt.tickets.PasswordChangeTicket;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TicketsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldThrowOnCreateEmailVerificationTicketWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'email verification ticket' cannot be null!");
        api.tickets().requestEmailVerification(null);
    }

    @Test
    public void shouldCreateEmailVerificationTicket() throws Exception {
        EmailVerificationTicket ticket = new EmailVerificationTicket("uid123");
        ticket.setIncludeEmailInRedirect(true);
        ticket.setTTLSeconds(42);

        Request<EmailVerificationTicket> request = api.tickets().requestEmailVerification(ticket);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_VERIFICATION_TICKET, 200);
        EmailVerificationTicket response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/tickets/email-verification"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("user_id", "uid123"));
        assertThat(body, hasEntry("includeEmailInRedirect", true));
        assertThat(body, hasEntry("ttl_sec", 42));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreatePasswordChangeTicketWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'password change ticket' cannot be null!");
        api.tickets().requestPasswordChange(null);
    }

    @Test
    public void shouldCreatePasswordChangeTicket() throws Exception {
        Request<PasswordChangeTicket> request = api.tickets().requestPasswordChange(new PasswordChangeTicket("uid123"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_PASSWORD_CHANGE_TICKET, 200);
        PasswordChangeTicket response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/tickets/password-change"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("user_id", "uid123"));

        assertThat(response, is(notNullValue()));
    }
}
