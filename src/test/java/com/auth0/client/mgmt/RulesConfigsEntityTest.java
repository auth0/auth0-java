package com.auth0.client.mgmt;

import com.auth0.json.mgmt.rules.RulesConfig;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static com.auth0.AssertsUtil.verifyThrows;
import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RulesConfigsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListRules() throws Exception {
        Request<List<RulesConfig>> request = api.rulesConfigs().list();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULES_CONFIGS_LIST, 200);
        List<RulesConfig> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/rules-configs"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
    }

    @Test
    public void shouldReturnEmptyRulesConfigs() throws Exception {
        Request<List<RulesConfig>> request = api.rulesConfigs().list();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<RulesConfig> response = request.execute().getBody();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(RulesConfig.class)));
    }

    @Test
    public void shouldThrowOnDeleteRulesConfigWithNullKey() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.rulesConfigs().delete(null),
            "'rules config key' cannot be null!");
    }

    @Test
    public void shouldDeleteRulesConfig() throws Exception {
        Request<Void> request = api.rulesConfigs().delete("1");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_RULE, 200);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/rules-configs/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnUpdateRulesConfigWithNullKey() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.rulesConfigs().update(null, new RulesConfig("my-value")),
            "'rules config key' cannot be null!");
    }

    @Test
    public void shouldThrowOnUpdateRulesConfigWithNullData() {
        verifyThrows(IllegalArgumentException.class,
            () -> api.rulesConfigs().update("1", null),
            "'rules config' cannot be null!");
    }

    @Test
    public void shouldUpdateRulesConfig() throws Exception {
        Request<RulesConfig> request = api.rulesConfigs().update("1", new RulesConfig("my-value"));
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_CONNECTION, 200);
        RulesConfig response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/rules-configs/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(1));
        assertThat(body, hasEntry("value", "my-value"));

        assertThat(response, is(notNullValue()));
    }
}
