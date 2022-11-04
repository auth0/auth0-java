package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import com.auth0.client.mgmt.filter.ActionsFilter;
import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.json.mgmt.actions.*;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.*;

import static com.auth0.client.MockServer.bodyFromRequest;
import static com.auth0.client.MockServer.readFromRequest;
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/actions/action-id"));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/actions/actions"));
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
        Request<Void> request = api.actions().delete("action-id");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/actions/actions/action-id"));
        assertThat(recordedRequest, hasQueryParameter("force", "false"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldForceDeleteAction() throws Exception {
        Request<Void> request = api.actions().delete("action-id", true);
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.DELETE, "/api/v2/actions/actions/action-id"));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/triggers"));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/actions/actions/action-id"));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/actions/actions/action-id/deploy"));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/actions/action-id/versions/version-id"));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.POST, "/api/v2/actions/actions/action-id/versions/version-id/deploy"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest.getBody(), is(notNullValue()));
        assertThat(readFromRequest(recordedRequest), is("{}"));

        assertThat(response, is(notNullValue()));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/executions/execution-id"));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/actions"));
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

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/actions"));
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

    @Test
    public void getActionVetsionsShouldThrowWhenActionIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("action ID");
        api.actions().getVersions(null, null);
    }

    @Test
    public void shouldGetActionVersionsWithNoFilter() throws Exception {
        Request<VersionsPage> request = api.actions().getVersions("action-id",null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_VERSIONS_LIST, 200);
        VersionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/actions/action-id/versions"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetActionVersionsWithFilter() throws Exception {
        PageFilter filter = new PageFilter()
            .withPage(1, 10);

        Request<VersionsPage> request = api.actions().getVersions("action-id", filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_VERSIONS_LIST, 200);
        VersionsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/actions/action-id/versions"));
        assertThat(recordedRequest, hasQueryParameter("page", "1"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "10"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void getTriggerBindingsShouldThrowWhenTriggerIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("trigger ID");
        api.actions().getTriggerBindings(null, null);
    }

    @Test
    public void shouldGetTriggerBindingsWithNoFilter() throws Exception {
        Request<BindingsPage> request = api.actions().getTriggerBindings("trigger-id",null);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_TRIGGER_BINDINGS, 200);
        BindingsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/triggers/trigger-id/bindings"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldGetTriggerBindingsWithFilter() throws Exception {
        PageFilter filter = new PageFilter()
            .withPage(1, 10);

        Request<BindingsPage> request = api.actions().getTriggerBindings("trigger-id",filter);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_TRIGGER_BINDINGS, 200);
        BindingsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/actions/triggers/trigger-id/bindings"));
        assertThat(recordedRequest, hasQueryParameter("page", "1"));
        assertThat(recordedRequest, hasQueryParameter("per_page", "10"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void updateTriggerBindingsShouldThrowWhenTriggerIdIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("trigger ID");
        api.actions().updateTriggerBindings(null, null);
    }

    @Test
    public void updateTriggerBindingsShouldThrowWhenRequestBodyIsNull() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("request body");
        api.actions().updateTriggerBindings("triggeId", null);
    }

    @Test
    public void shouldUpdateTriggerBinding() throws Exception {
        BindingActionReference bindingReference1 = new BindingActionReference("action_name", "action name");
        BindingActionReference bindingReference2 = new BindingActionReference("action_id", "action-id");
        BindingUpdate update1 = new BindingUpdate(bindingReference1);
        update1.setDisplayName("display name");
        BindingUpdate update2 = new BindingUpdate(bindingReference2);
        update2.setDisplayName("another display name");

        BindingsUpdateRequest requestBody = new BindingsUpdateRequest(Arrays.asList(update1, update2));

        Request<BindingsPage> request = api.actions().updateTriggerBindings("trigger-id", requestBody);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.ACTION_TRIGGER_BINDINGS, 200);
        BindingsPage response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.PATCH, "/api/v2/actions/triggers/trigger-id/bindings"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body, aMapWithSize(1));

        assertThat(body, hasEntry(is("bindings"), is(notNullValue())));
        List<Map<String, Object>> bindingsOnRequest = (ArrayList<Map<String, Object>>) body.get("bindings");
        assertThat(bindingsOnRequest, hasSize(2));

        Map<String, Object> binding1 = bindingsOnRequest.get(0);
        assertThat(binding1, is(notNullValue()));
        assertThat(binding1.get("display_name"), is("display name"));
        Map<String, Object> binding1Ref = (Map<String, Object>) binding1.get("ref");
        assertThat(binding1Ref, is(aMapWithSize(2)));
        assertThat(binding1Ref, hasEntry("type", "action_name"));
        assertThat(binding1Ref, hasEntry("value",  "action name"));

        Map<String, Object> binding2 = bindingsOnRequest.get(1);
        assertThat(binding2, is(notNullValue()));
        assertThat(binding2.get("display_name"), is("another display name"));
        Map<String, Object> binding2Ref = (Map<String, Object>) binding2.get("ref");
        assertThat(binding2Ref, is(aMapWithSize(2)));
        assertThat(binding2Ref, hasEntry("type", "action_id"));
        assertThat(binding2Ref, hasEntry("value",  "action-id"));

        assertThat(response, is(notNullValue()));
    }
}
