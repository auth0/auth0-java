package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.RulesFilter;
import com.auth0.json.mgmt.rules.Rule;
import com.auth0.json.mgmt.rules.RulesPage;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.List;
import java.util.Map;

/**
 * Class that provides an implementation of the Rules methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Rules
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class RulesEntity extends BaseManagementEntity {

    RulesEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all the Rules. A token with scope read:rules is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules/get_rules
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<RulesPage> listAll(RulesFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/rules");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<RulesPage>() {
        });
    }

    /**
     * Request a Rule. A token with scope read:rules is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules/get_rules_by_id
     *
     * @param ruleId the id of the rule to retrieve.
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<Rule> get(String ruleId, RulesFilter filter) {
        Asserts.assertNotNull(ruleId, "rule id");

        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/rules")
                .addPathSegment(ruleId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Rule>() {
        });
    }

    /**
     * Create a Rule. A token with scope create:rules is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules/post_rules
     *
     * @param rule the rule data to set
     * @return a Request to execute.
     */
    public Request<Rule> create(Rule rule) {
        Asserts.assertNotNull(rule, "rule");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/rules")
                .build()
                .toString();
        BaseRequest<Rule> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.POST, new TypeReference<Rule>() {
        });
        request.setBody(rule);
        return request;
    }

    /**
     * Delete an existing Rule. A token with scope delete:rules is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules/delete_rules_by_id
     *
     * @param ruleId the rule id
     * @return a Request to execute.
     */
    public Request<Void> delete(String ruleId) {
        Asserts.assertNotNull(ruleId, "rule id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/rules")
                .addPathSegment(ruleId)
                .build()
                .toString();
        return new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
    }

    /**
     * Update an existing Rule. A token with scope update:rules is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules/patch_rules_by_id
     *
     * @param ruleId the rule id
     * @param rule   the rule data to set. It can't include id.
     * @return a Request to execute.
     */
    public Request<Rule> update(String ruleId, Rule rule) {
        Asserts.assertNotNull(ruleId, "rule id");
        Asserts.assertNotNull(rule, "rule");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/rules")
                .addPathSegment(ruleId)
                .build()
                .toString();
        BaseRequest<Rule> request = new BaseRequest<>(this.client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<Rule>() {
        });
        request.setBody(rule);
        return request;
    }
}
