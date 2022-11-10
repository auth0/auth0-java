package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.ActionsFilter;
import com.auth0.client.mgmt.filter.BaseFilter;
import com.auth0.client.mgmt.filter.PageFilter;
import com.auth0.json.mgmt.actions.*;
import com.auth0.net.CustomRequest;
import com.auth0.net.EmptyBodyRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.net.client.HttpRequestBody;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

/**
 * Class that provides an implementation of the Actions methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Actions
 *
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
public class ActionsEntity extends BaseManagementEntity {

    private final static String ACTIONS_BASE_PATH = "api/v2/actions";
    private final static String ACTIONS_PATH = "actions";
    private final static String TRIGGERS_PATH = "triggers";
    private final static String DEPLOY_PATH = "deploy";
    private final static String VERSIONS_PATH = "versions";
    private final static String EXECUTIONS_PATH = "executions";
    private final static String BINDINGS_PATH = "bindings";

    private final static String AUTHORIZATION_HEADER = "Authorization";

    ActionsEntity(Auth0HttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Create an action. A token with {@code create:actions} scope is required.
     *
     * @param action the action to create
     * @return a request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/post_action">https://auth0.com/docs/api/management/v2#!/Actions/post_action</a>
     */
    public Request<Action> create(Action action) {
        Asserts.assertNotNull(action, "action");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH);

        String url = builder.build().toString();

        CustomRequest<Action> request = new CustomRequest<>(client, url, HttpMethod.POST, new TypeReference<Action>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(action);
        return request;
    }

    /**
     * Get an action. A token with {@code read:actions} scope is required.
     *
     * @param actionId the ID of the action to retrieve
     * @return a Request to execute
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/get_action">https://auth0.com/docs/api/management/v2#!/Actions/get_action</a>
     */
    public Request<Action> get(String actionId) {
        Asserts.assertNotNull(actionId, "action ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH)
            .addPathSegment(actionId)
            .build()
            .toString();

        CustomRequest<Action> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<Action>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Delete an action and all of its associated versions. An action must be unbound from all triggers before it can
     * be deleted. A token with {@code delete:action} scope is required.
     *
     * @param actionId the ID of the action to delete.
     * @return a request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/delete_action">https://auth0.com/docs/api/management/v2#!/Actions/delete_action</a>
     */
    public Request<Void> delete(String actionId) {
        return delete(actionId, false);
    }

    /**
     * Delete an action and all of its associated versions. A token with {@code delete:action} scope is required.
     *
     * @param actionId the ID of the action to delete.
     * @param force whether to force the action deletion even if it is bound to triggers.
     * @return a request to execute.
     *
     * <a href="https://auth0.com/docs/api/management/v2#!/Actions/get_triggers">https://auth0.com/docs/api/management/v2#!/Actions/get_triggers</a>
     */
    public Request<Void> delete(String actionId, boolean force) {
        Asserts.assertNotNull(actionId, "action ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH)
            .addPathSegment(actionId)
            .addQueryParameter("force", String.valueOf(force))
            .build()
            .toString();

        VoidRequest voidRequest = new VoidRequest(client, url, HttpMethod.DELETE);
        voidRequest.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return voidRequest;
    }

    /**
     * Get the set of triggers currently available. A trigger is an extensibility point to which actions can be bound.
     * A token with {@code read:actions} scope is required.
     *
     * @return a request to execute.
     */
    public Request<Triggers> getTriggers() {
        String url = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(TRIGGERS_PATH)
            .build()
            .toString();

        CustomRequest<Triggers> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<Triggers>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing action. If this action is currently bound to a trigger, updating it will not affect any user
     * flows until the action is deployed. Requires a token with {@code update:actions} scope.
     *
     * @param actionId the ID of the action to update.
     * @param action the updated action.
     * @return a request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/patch_action">https://auth0.com/docs/api/management/v2#!/Actions/patch_action</a>
     */
    public Request<Action> update(String actionId, Action action) {
        Asserts.assertNotNull(actionId, "action ID");
        Asserts.assertNotNull(action, "action");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH)
            .addPathSegment(actionId)
            .build()
            .toString();

        CustomRequest<Action> request = new CustomRequest<>(client, url, HttpMethod.PATCH, new TypeReference<Action>() {
        });

        request.setBody(action);
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Deploy an action. Deploying an action will create a new immutable version of the action. If the action is
     * currently bound to a trigger, then the system will begin executing the newly deployed version of the action
     * immediately. Otherwise, the action will only be executed as a part of a flow once it is bound to that flow.
     * Requires a token with {@code create:actions}.
     *
     * @param actionId the ID of the action to deploy.
     * @return a request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/post_deploy_action">https://auth0.com/docs/api/management/v2#!/Actions/post_deploy_action</a>
     */
    public Request<Version> deploy(String actionId) {
        Asserts.assertNotNull(actionId, "action ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH)
            .addPathSegment(actionId)
            .addPathSegment(DEPLOY_PATH)
            .build()
            .toString();

        EmptyBodyRequest<Version> request = new EmptyBodyRequest<>(client, url, HttpMethod.POST, new TypeReference<Version>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Retrieve a specific version of an action. An action version is created whenever
     * an action is deployed. An action version is immutable, once created. Requires a token with {@code read:actions} scope.
     *
     * @param actionId the ID of the action for which to retrieve the version.
     * @param actionVersionId the ID of the specific version to retrieve.
     * @return a request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/get_action_version">https://auth0.com/docs/api/management/v2#!/Actions/get_action_version</a>
     */
    public Request<Version> getVersion(String actionId, String actionVersionId) {
        Asserts.assertNotNull(actionId, "action ID");
        Asserts.assertNotNull(actionVersionId, "action version ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH)
            .addPathSegment(actionId)
            .addPathSegment(VERSIONS_PATH)
            .addPathSegment(actionVersionId)
            .build()
            .toString();

        CustomRequest<Version> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<Version>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Performs the equivalent of a roll-back of an action to an earlier, specified version. Creates a new, deployed
     * action version that is identical to the specified version. If this action is currently bound to a trigger, the
     * system will begin executing the newly-created version immediately.
     *
     * @param actionId the ID of the action
     * @param actionVersionId the ID of the action version to roll-back to
     * @return a request to be executed
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/post_deploy_draft_version">https://auth0.com/docs/api/management/v2#!/Actions/post_deploy_draft_version</a>
     */
    public Request<Version> rollBackToVersion(String actionId, String actionVersionId) {
        Asserts.assertNotNull(actionId, "action ID");
        Asserts.assertNotNull(actionVersionId, "action version ID");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH)
            .addPathSegment(actionId)
            .addPathSegment(VERSIONS_PATH)
            .addPathSegment(actionVersionId)
            .addPathSegment(DEPLOY_PATH)
            .build()
            .toString();

        // Needed to successfully call the roll-back endpoint until DXEX-1738 is resolved.
        EmptyObjectRequest<Version> request = new EmptyObjectRequest<>(client, url, HttpMethod.POST, new TypeReference<Version>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Retrieve information about a specific execution of an action. Relevant execution IDs will be included in tenant logs
     * generated as part of that authentication flow. Executions will only be stored for 10 days after their creation.
     * Requires a token with {@code read:actions} scope.
     *
     * @param executionId The ID of the execution to retrieve.
     * @return a request to be executed.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/get_execution">https://auth0.com/docs/api/management/v2#!/Actions/get_execution</a>
     */
    public Request<Execution> getExecution(String executionId) {
        Asserts.assertNotNull(executionId, "execution ID");

        String url =  baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(EXECUTIONS_PATH)
            .addPathSegment(executionId)
            .build()
            .toString();

        CustomRequest<Execution> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<Execution>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Get all actions. Requires a token with {@code read:actions} scope.
     *
     * @param filter an optional filter to apply to the request.
     * @return a request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/get_actions">https://auth0.com/docs/api/management/v2#!/Actions/get_actions</a>
     */
    public Request<ActionsPage> list(ActionsFilter filter) {
        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH);

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<ActionsPage> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<ActionsPage>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Retrieve all of an action's versions. An action version is created whenever
     * an action is deployed. An action version is immutable, once created.
     * Requires a token with {@code read:actions} scope.
     *
     * @param actionId the ID of the action to retrieve versions for.
     * @param filter an optional pagination filter. Note that all available pagination parameters may be available for
     *               this endpoint. See the referenced API documentation for supported parameters.
     * @return a request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/get_action_versions">https://auth0.com/docs/api/management/v2#!/Actions/get_action_versions</a>
     */
    public Request<VersionsPage> getVersions(String actionId, PageFilter filter) {
        Asserts.assertNotNull(actionId, "action ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(ACTIONS_PATH)
            .addPathSegment(actionId)
            .addPathSegment(VERSIONS_PATH);

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<VersionsPage> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<VersionsPage>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Retrieve the actions that are bound to a trigger. Once an action is created and deployed, it must be
     * attached (i.e. bound) to a trigger so that it will be executed as part of a flow. The list of actions returned
     * reflects the order in which they will be executed during the appropriate flow.
     * Requires a token with {@code read:actions}.
     *
     * @param triggerId The trigger ID for which to get its action bindings.
     * @param filter an optional pagination filter.  Note that all available pagination parameters may be available for
     *               this endpoint. See the referenced API documentation for supported parameters.
     * @return a request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/get_bindings">https://auth0.com/docs/api/management/v2#!/Actions/get_bindings</a>
     */
    public Request<BindingsPage> getTriggerBindings(String triggerId, PageFilter filter) {
        Asserts.assertNotNull(triggerId, "trigger ID");

        HttpUrl.Builder builder = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(TRIGGERS_PATH)
            .addPathSegment(triggerId)
            .addPathSegment(BINDINGS_PATH);

        applyFilter(filter, builder);

        String url = builder.build().toString();
        CustomRequest<BindingsPage> request = new CustomRequest<>(client, url, HttpMethod.GET, new TypeReference<BindingsPage>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Update the actions that are bound (i.e. attached) to a trigger. Once an action is created and deployed, it must be
     * attached (i.e. bound) to a trigger so that it will be executed as part of a flow. The order in which the actions are
     * provided will determine the order in which they are executed.
     * Requires a token with {@code update:actions} scope.
     *
     * @param triggerId the ID of the trigger for which to update its bindings.
     * @param bindingsUpdateRequest the bindings update request body.
     * @return a request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/Actions/patch_bindings">https://auth0.com/docs/api/management/v2#!/Actions/patch_bindings</a>
     */
    public Request<BindingsPage> updateTriggerBindings(String triggerId, BindingsUpdateRequest bindingsUpdateRequest) {
        Asserts.assertNotNull(triggerId, "trigger ID");
        Asserts.assertNotNull(bindingsUpdateRequest, "request body");

        String url = baseUrl
            .newBuilder()
            .addPathSegments(ACTIONS_BASE_PATH)
            .addPathSegment(TRIGGERS_PATH)
            .addPathSegment(triggerId)
            .addPathSegment(BINDINGS_PATH)
            .build()
            .toString();

        CustomRequest<BindingsPage> request = new CustomRequest<>(client, url, HttpMethod.PATCH, new TypeReference<BindingsPage>() {
        });

        request.setBody(bindingsUpdateRequest);
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    private void applyFilter(BaseFilter filter, HttpUrl.Builder builder) {
        if (filter != null) {
            filter.getAsMap().forEach((k, v) -> builder.addQueryParameter(k, String.valueOf(v)));
        }
    }

    // Temporary request implementation to send an empty json object on the request body.
    private static class EmptyObjectRequest<T> extends EmptyBodyRequest<T> {
        EmptyObjectRequest(Auth0HttpClient client, String url, HttpMethod method, TypeReference<T> tType) {
            super(client, url, method, tType);
        }

//        @Override
        @SuppressWarnings("deprecation")
//        protected byte[] createRequestBody() {
//            // Use OkHttp v3 signature to ensure binary compatibility between v3 and v4
//            // https://github.com/auth0/auth0-java/issues/324
//            // TODO this right?
//            return "{}".getBytes();
////            return RequestBody.create(MediaType.parse("application/json"), "{}".getBytes());
//        }

        @Override
        protected HttpRequestBody createRequestBody() {
            // Use OkHttp v3 signature to ensure binary compatibility between v3 and v4
            // https://github.com/auth0/auth0-java/issues/324
            // TODO this right?
            return HttpRequestBody.newBuilder().withContent("{}".getBytes()).build();
//            return "{}".getBytes();
//            return RequestBody.create(MediaType.parse("application/json"), "{}".getBytes());
        }
    }
}
