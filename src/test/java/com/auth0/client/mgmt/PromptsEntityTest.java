package com.auth0.client.mgmt;

import com.auth0.json.mgmt.prompts.Prompt;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static com.auth0.client.MockServer.MGMT_PARTIALS_PROMPT;
import static com.auth0.client.MockServer.MGMT_PROMPT;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static com.auth0.AssertsUtil.verifyThrows;

public class PromptsEntityTest extends BaseMgmtEntityTest{

    @Test
    public void shouldGetPrompts() throws Exception {
        Request<Prompt> request = api.prompts().getPrompt();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_PROMPT, 200);
        Prompt response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/prompts"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnUpdatePromptsWhenPromptIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().updatePrompt(null),
            "'prompt' cannot be null!");
    }

    @Test
    public void shouldUpdatePrompts() throws Exception {
        Prompt prompt = new Prompt();
        prompt.setIdentifierFirst(true);
        prompt.setUniversalLoginExperience("new");
        prompt.setWebauthnPlatformFirstFactor(true);

        Request<Prompt> request = api.prompts().updatePrompt(prompt);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_PROMPT, 200);
        Prompt response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/prompts"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.isIdentifierFirst(), is(true));
        assertThat(response.getUniversalLoginExperience(), is("new"));
        assertThat(response.isWebauthnPlatformFirstFactor(), is(true));
    }

    @Test
    public void shouldThrowOnGetCustomTextWhenPromptIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().getCustomText(null, "en"),
            "'prompt' cannot be null!");
    }

    @Test
    public void shouldThrowOnGetCustomTextWhenLanguageIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().getCustomText("login", null),
            "'language' cannot be null!");
    }

    @Test
    public void shouldGetCustomText() throws Exception {
        Request<Object> request = api.prompts().getCustomText("login", "en");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_PARTIALS_PROMPT, 200);
        Object response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/prompts/login/custom-text/en"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnSetCustomTextWhenPromptIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().getCustomText(null, "en"),
            "'prompt' cannot be null!");
    }

    @Test
    public void shouldThrowOnSetCustomTextWhenLanguageIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().getCustomText("login", null),
            "'language' cannot be null!");
    }

    @Test
    public void shouldThrowOnSetCustomTextWhenCustomTextIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().setCustomText("login", "en", null),
            "'customText' cannot be null!");
    }

    @Test
    public void shouldSetCustomText() throws Exception {
        Map<String, Object> customText = new HashMap<>();
        Request<Void> request = api.prompts().setCustomText("login", "en", customText);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_PARTIALS_PROMPT, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/prompts/login/custom-text/en"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnGetPartialsWhenPromptIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().getPartialsPrompt(null),
            "'prompt' cannot be null!");
    }

    @Test
    public void shouldGetPartials() throws Exception {
        Request<Object> request = api.prompts().getPartialsPrompt("login");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_PARTIALS_PROMPT, 200);
        Object response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/prompts/login/partials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnSetPartialsWhenPromptIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().setPartialsPrompt(null, new Object()),
            "'prompt' cannot be null!");
    }

    @Test
    public void shouldThrowOnSetPartialsWhenPartialsIsNull() throws Exception {
        verifyThrows(IllegalArgumentException.class,
            () -> api.prompts().setPartialsPrompt("login", null),
            "'partials' cannot be null!");
    }

    @Test
    public void shouldSetPartials() throws Exception {
        Map<String, Object> partials = new HashMap<>();
        Request<Void> request = api.prompts().setPartialsPrompt("login", partials);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_PARTIALS_PROMPT, 200);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PUT, "/api/v2/prompts/login/partials"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }
}
