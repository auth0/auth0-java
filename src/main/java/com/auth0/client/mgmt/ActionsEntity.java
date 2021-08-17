package com.auth0.client.mgmt;

import com.auth0.json.mgmt.actions.Action;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class ActionsEntity extends BaseManagementEntity {

    private final static String ACTIONS_BASE_PATH = "api/v2/actions";
    private final static String ACTIONS_PATH = "actions";
    private final static String AUTHORIZATION_HEADER = "Authorization";

    ActionsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Create an action
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

        CustomRequest<Action> request = new CustomRequest<>(client, url, "POST", new TypeReference<Action>() {
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

        CustomRequest<Action> request = new CustomRequest<>(client, url, "GET", new TypeReference<Action>() {
        });

        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }
}
