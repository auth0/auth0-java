package com.auth0.client.mgmt;

import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.client.mgmt.filter.RulesFilter;
import com.auth0.json.mgmt.Rule;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Rules methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Rules
 */
@SuppressWarnings("WeakerAccess")
public class RulesEntity {
    private final RequestBuilder requestBuilder;
    RulesEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request all the Rules. A token with scope read:rules is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules/get_rules
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<List<Rule>> list(RulesFilter filter) {

        return requestBuilder.get("api/v2/rules")
                             .queryParameters(filter)
                             .request(new TypeReference<List<Rule>>() {
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

        return requestBuilder.get("api/v2/rules", ruleId)
                             .queryParameters(filter)
                             .request(new TypeReference<Rule>() {
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

        return requestBuilder.post("api/v2/rules")
                             .body(rule)
                             .request(new TypeReference<Rule>() {
                             });
    }

    /**
     * Delete an existing Rule. A token with scope delete:rules is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules/delete_rules_by_id
     *
     * @param ruleId the rule id
     * @return a Request to execute.
     */
    public Request delete(String ruleId) {
        Asserts.assertNotNull(ruleId, "rule id");

        return requestBuilder.delete("api/v2/rules", ruleId)
                             .request();
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

        return requestBuilder.patch("api/v2/rules", ruleId)
                             .body(rule)
                             .request(new TypeReference<Rule>() {
                             });
    }
}
