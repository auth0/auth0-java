package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.filter.ActionsFilter;
import com.auth0.json.mgmt.actions.*;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.*;

import static com.auth0.client.MockServer.bodyFromRequest;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@SuppressWarnings("unchecked")
public class ActionsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void getActionShouldThrowOnNullActionId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action ID");
        api.actions().get(null);
    }

    @Test
    public void shouldGetAction() throws Exception {
        Request<Action> request = api.actions().get("action-id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION, 200);
        Action response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/actions/actions/action-id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void createActionShouldThrowOnNullAction() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action");
        api.actions().create(null);
    }

    @Test
    public void shouldCreateAction() throws Exception {
        Trigger trigger = new Trigger();
        trigger.setId("post-login");
        trigger.setVersion("v2");

        Dependency dependency = new Dependency();
        dependency.setVersion("v2");
        dependency.setName("some-dep");
        dependency.setRegistryUrl("some-registry-url");

        Secret secret = new Secret("secret-name", "secret-value");
        Action action = new Action("my action", Collections.singletonList(trigger));
        action.setCode("some code");
        action.setRuntime("node16");
        action.setDependencies(Collections.singletonList(dependency));
        action.setSecrets(Collections.singletonList(secret));

        Request<Action> request = api.actions().create(action);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION, 200);
        Action response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/actions/actions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(6));
        assertThat(body, hasEntry("name", "my action"));
        assertThat(body, hasEntry("code", "some code"));
        assertThat(body, hasEntry("runtime", "node16"));

        assertThat(body, hasEntry(is("supported_triggers"), is(notNullValue())));
        List<Map<String, Object>> triggersOnRequest = (ArrayList<Map<String, Object>>) body.get("supported_triggers");
        assertThat(triggersOnRequest, hasSize(1));
        assertThat(triggersOnRequest.get(0), is(aMapWithSize(2)));
        assertThat(triggersOnRequest.get(0), hasEntry("version", trigger.getVersion()));
        assertThat(triggersOnRequest.get(0), hasEntry("id", trigger.getId()));

        assertThat(body, hasEntry(is("dependencies"), is(notNullValue())));
        List<Map<String, Object>> dependenciesOnRequest = (ArrayList<Map<String, Object>>) body.get("dependencies");
        assertThat(dependenciesOnRequest, hasSize(1));
        assertThat(dependenciesOnRequest.get(0), is(aMapWithSize(3)));
        assertThat(dependenciesOnRequest.get(0), hasEntry("version", dependency.getVersion()));
        assertThat(dependenciesOnRequest.get(0), hasEntry("name", dependency.getName()));
        assertThat(dependenciesOnRequest.get(0), hasEntry("registry_url", dependency.getRegistryUrl()));

        assertThat(body, hasEntry(is("secrets"), is(notNullValue())));
        List<Map<String, Object>> secretsOnRequest = (ArrayList<Map<String, Object>>) body.get("secrets");
        assertThat(secretsOnRequest, hasSize(1));
        assertThat(secretsOnRequest.get(0), is(aMapWithSize(2)));
        assertThat(secretsOnRequest.get(0), hasEntry("name", secret.getName()));
        assertThat(secretsOnRequest.get(0), hasEntry("value", secret.getValue()));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void deleteActionShouldThrowWhenActionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action ID");
        api.actions().delete(null);
    }

    @Test
    public void shouldDeleteAction() throws Exception {
        Request request = api.actions().delete("action-id");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/actions/actions/action-id"));
        assertThat(recordedRequest, hasQueryParameter("force", "false"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldForceDeleteAction() throws Exception {
        Request request = api.actions().delete("action-id", true);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/actions/actions/action-id"));
        assertThat(recordedRequest, hasQueryParameter("force", "true"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldGetActionTriggers() throws Exception {
        Request<Triggers> request = api.actions().getTriggers();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_TRIGGERS, 200);
        Triggers triggers = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/actions/triggers"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(triggers, is(notNullValue()));
        assertThat(triggers.getTriggers(), hasSize(12));
    }

    @Test
    public void updateActionShouldThrowWhenActionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action ID");
        api.actions().update(null, new Action());
    }

    @Test
    public void updateActionShouldThrowWhenActionIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action");
        api.actions().update("action-id", null);
    }

    @Test
    public void shouldUpdateAction() throws Exception {
        Dependency dependency = new Dependency("lodash", "1.5.5");
        Secret secret = new Secret("secret-key", "secret-val");

        Action action = new Action();
        action.setName("action name");
        action.setCode("some code");
        action.setRuntime("node16");
        action.setDependencies(Collections.singletonList(dependency));
        action.setSecrets(Collections.singletonList(secret));

        Request<Action> request = api.actions().update("action-id", action);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION, 200);
        Action response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/actions/actions/action-id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(5));
        assertThat(body, hasEntry("name", "action name"));
        assertThat(body, hasEntry("code", "some code"));
        assertThat(body, hasEntry("runtime", "node16"));

        assertThat(body, hasEntry(is("dependencies"), is(notNullValue())));
        List<Map<String, Object>> dependenciesOnRequest = (ArrayList<Map<String, Object>>) body.get("dependencies");
        assertThat(dependenciesOnRequest, hasSize(1));
        assertThat(dependenciesOnRequest.get(0), is(aMapWithSize(2)));
        assertThat(dependenciesOnRequest.get(0), hasEntry("version", dependency.getVersion()));
        assertThat(dependenciesOnRequest.get(0), hasEntry("name", dependency.getName()));

        assertThat(body, hasEntry(is("secrets"), is(notNullValue())));
        List<Map<String, Object>> secretsOnRequest = (ArrayList<Map<String, Object>>) body.get("secrets");
        assertThat(secretsOnRequest, hasSize(1));
        assertThat(secretsOnRequest.get(0), is(aMapWithSize(2)));
        assertThat(secretsOnRequest.get(0), hasEntry("name", secret.getName()));
        assertThat(secretsOnRequest.get(0), hasEntry("value", secret.getValue()));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void deployActionShouldThrowWhenActionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action ID");
        api.actions().deploy(null);
    }

    @Test
    public void shouldDeployAction() throws Exception {
        Request<Version> request = api.actions().deploy("action-id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_VERSION, 200);
        Version response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/actions/actions/action-id/deploy"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest.getBody(), is(notNullValue()));
        assertThat(recordedRequest.getBody().size(), is(0L));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getVersionShouldThrowWhenActionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action ID");
        api.actions().getVersion(null, "version-id");
    }

    @Test
    public void getVersionShouldThrowWhenVersionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action version ID");
        api.actions().getVersion("action-id", null);
    }

    @Test
    public void shouldGetActionVersion() throws Exception {
        Request<Version> request = api.actions().getVersion("action-id", "version-id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_VERSION, 200);
        Version response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/actions/actions/action-id/versions/version-id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void rollBackToVersionShouldThrowWhenActionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action ID");
        api.actions().rollBackToVersion(null, "version-id");
    }

    @Test
    public void rollbackToVersionShouldThrowWhenVersionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action version ID");
        api.actions().rollBackToVersion("action-id", null);
    }

    @Test
    public void shouldRollBackActionVersion() throws Exception {
        Request<Version> request = api.actions().rollBackToVersion("action-id", "version-id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_VERSION, 200);
        Version response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/actions/actions/action-id/versions/version-id/deploy"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest.getBody(), is(notNullValue()));
        assertThat(recordedRequest.getBody().size(), is(0L));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetServiceStatus() throws Exception {
        Request<ServiceStatus> request = api.actions().getServiceStatus();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_SERVICE_STATUS, 200);
        ServiceStatus response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/actions/status"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response.getStatus(), is("OK"));
    }

    @Test
    public void getExecutionShouldThrowWhenExecutionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("execution ID");
        api.actions().getExecution( null);
    }

    @Test
    public void shouldGetExecution() throws Exception {
        Request<Execution> request = api.actions().getExecution("execution-id");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_EXECUTION, 200);
        Execution response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/actions/executions/execution-id"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldListActionsWithNoFilter() throws Exception {
        Request<ActionsPage> request = api.actions().list(null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTIONS_LIST, 200);
        ActionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/actions/actions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldListActionsWithFilter() throws Exception {
        ActionsFilter filter = new ActionsFilter()
            .withActionName("action-name")
            .withDeployed(true)
            .withInstalled(false)
            .withTriggerId("post-login")
            .withPage(1, 10);

        Request<ActionsPage> request = api.actions().list(filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTIONS_LIST, 200);
        ActionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/actions/actions"));
        assertThat(recordedRequest, hasQueryParameter("actionName", "action-name"));
        assertThat(recordedRequest, hasQueryParameter("deployed", "true"));
        assertThat(recordedRequest, hasQueryParameter("installed", "false"));
        assertThat(recordedRequest, hasQueryParameter("triggerId", "post-login"));
        assertThat(recordedRequest, hasQueryParameter("page", "1"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "10"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }
}
