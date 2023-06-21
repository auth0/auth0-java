package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.json.mgmt.emailproviders.EmailProvider;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.MGMT_EMAIL_PROVIDER;
import static com.auth0.client.MockServer.bodyFromRequest;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class EmailProviderEntityTest extends BaseMgmtEntityTest {
    @Test
    public void shouldGetEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().get(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetEmailProviderWithFields() throws Exception {
        FieldsFilter filter = new FieldsFilter().withFields("some,random,fields", true);
        Request<EmailProvider> request = api.emailProvider().get(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnSetupEmailProviderWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.emailProvider().setup(null),
            "'email provider' cannot be null!");
    }

    @Test
    public void shouldSetupEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().setup(new EmailProvider("provider"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "provider"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldDeleteEmailProvider() throws Exception {
        Request<Void> request = api.emailProvider().delete();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateEmailProviderWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.emailProvider().update(null),
            "'email provider' cannot be null!");
    }

    @Test
    public void shouldUpdateEmailProvider() throws Exception {
        Request<EmailProvider> request = api.emailProvider().update(new EmailProvider("name"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMAIL_PROVIDER, 200);
        EmailProvider response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/emails/provider"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("name", "name"));

        assertThat(response, is(notNullValue()));
    }
}
