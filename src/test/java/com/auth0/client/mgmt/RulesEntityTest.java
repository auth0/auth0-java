package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.RulesFilter;
import com.auth0.json.mgmt.rules.Rule;
import com.auth0.json.mgmt.rules.RulesPage;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RulesEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListRulesWithoutFilter() throws Exception {
        Request<RulesPage> request = api.rules().listAll(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULES_LIST, 200);
        RulesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/rules"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListRulesWithPage() throws Exception {
        RulesFilter filter = new RulesFilter().withPage(23, 5);
        Request<RulesPage> request = api.rules().listAll(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULES_LIST, 200);
        RulesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/rules"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("page", "23"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "5"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
    }

    @Test
    public void shouldListRulesWithTotals() throws Exception {
        RulesFilter filter = new RulesFilter().withTotals(true);
        Request<RulesPage> request = api.rules().listAll(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULES_PAGED_LIST, 200);
        RulesPage response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/rules"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("include_totals", "true"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getItems(), hasSize(2));
        assertThat(response.getStart(), is(0));
        assertThat(response.getLength(), is(14));
        assertThat(response.getTotal(), is(14));
        assertThat(response.getLimit(), is(50));
    }

    @Test
    public void shouldThrowOnGetRuleWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'rule id' cannot be null!");
        api.rules().get(null, null);
    }

    @Test
    public void shouldGetRule() throws Exception {
        Request<Rule> request = api.rules().get("1", null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULE, 200);
        Rule response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/rules/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetRuleWithFields() throws Exception {
        RulesFilter filter = new RulesFilter().withFields("some,random,fields", true);
        Request<Rule> request = api.rules().get("1", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULE, 200);
        Rule response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/rules/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("fields", "some,random,fields"));
        assertThat(recordedRequest, hasQueryParameter("include_fields", "true"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnCreateRuleWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'rule' cannot be null!");
        api.rules().create(null);
    }

    @Test
    public void shouldCreateRule() throws Exception {
        Request<Rule> request = api.rules().create(new Rule("my-rule", "function(){}"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULE, 200);
        Rule response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/rules"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("name", "my-rule"));
        assertThat(body, hasEntry("script", "function(){}"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnDeleteRuleWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'rule id' cannot be null!");
        api.rules().delete(null);
    }

    @Test
    public void shouldDeleteRule() throws Exception {
        Request<Void> request = api.rules().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULE, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/rules/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateRuleWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'rule id' cannot be null!");
        api.rules().update(null, new Rule("my-rule", "function(){}"));
    }

    @Test
    public void shouldThrowOnUpdateRuleWithNullData() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'rule' cannot be null!");
        api.rules().update("1", null);
    }

    @Test
    public void shouldUpdateRule() throws Exception {
        Request<Rule> request = api.rules().update("1", new Rule("my-rule", "function(){}"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        Rule response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/rules/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(2));
        assertThat(body, hasEntry("name", "my-rule"));
        assertThat(body, hasEntry("script", "function(){}"));

        assertThat(response, is(notNullValue()));
    }
}
