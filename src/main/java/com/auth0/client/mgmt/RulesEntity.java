package com.auth0.client.mgmt;

import com.auth0.utils.Asserts;
import com.auth0.client.mgmt.filter.RulesFilter;
import com.auth0.json.mgmt.Rule;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;
import java.util.Map;

public class RulesEntity extends BaseManagementEntity {

    RulesEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the Rules. A token with scope read:rules is needed.
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<Rule>> list(RulesFilter filter) {
        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<List<Rule>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<Rule>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request a Rule. A token with scope read:rules is needed.
     *
     * @param ruleId the id of the rule to retrieve.
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<Rule> get(String ruleId, RulesFilter filter) {
        Asserts.assertNotNull(ruleId, "rule id");

        HttpUrl.Builder builder = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules")
                .addPathSegment(ruleId);
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<Rule> request = new CustomRequest<>(client, url, "GET", new TypeReference<Rule>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create a new Rule. A token with scope create:rules is needed.
     *
     * @param rule the rule data to set
     * @return a Request to execute.
     */
    public Request<Rule> create(Rule rule) {
        Asserts.assertNotNull(rule, "rule");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules")
                .build()
                .toString();
        CustomRequest<Rule> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<Rule>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(rule);
        return request;
    }

    /**
     * Delete an existing Rule. A token with scope delete:rules is needed.
     *
     * @param ruleId the rule id
     * @return a Request to execute.
     */
    public Request delete(String ruleId) {
        Asserts.assertNotNull(ruleId, "rule id");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules")
                .addPathSegment(ruleId)
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Update an existing Rule. A token with scope update:rules is needed.
     *
     * @param ruleId the rule id
     * @param rule   the rule data to set. It can't include id.
     * @return a Request to execute.
     */
    public Request<Rule> update(String ruleId, Rule rule) {
        Asserts.assertNotNull(ruleId, "rule id");
        Asserts.assertNotNull(rule, "rule");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("rules")
                .addPathSegment(ruleId)
                .build()
                .toString();
        CustomRequest<Rule> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<Rule>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(rule);
        return request;
    }


}
