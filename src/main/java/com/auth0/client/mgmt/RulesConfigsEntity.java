package com.auth0.client.mgmt;

import com.auth0.json.mgmt.RulesConfig;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.List;

/**
 * Class that provides an implementation of the Rules Configs methods of the Management API as defined in See https://auth0.com/docs/api/management/v2#!/Rules_Configs/
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class RulesConfigsEntity extends BaseManagementEntity {

    RulesConfigsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request all the Rules Configs. A token with scope read:rules_configs is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules_Configs/get_rules_configs
     * <p>
     * Note: For security, config variable values cannot be retrieved outside rule execution.
     *
     * @return a Request to execute.
     */
    public Request<List<RulesConfig>> list() {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/rules-configs");
        String url = builder.build().toString();
        BaseRequest<List<RulesConfig>> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<List<RulesConfig>>() {
        });
        return request;
    }

    /**
     * Delete an existing Rules Config. A token with scope delete:rules_configs is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules_Configs/delete_rules_configs_by_key
     *
     * @param rulesConfigKey the rules config key
     * @return a Request to execute.
     */
    public Request<Void> delete(String rulesConfigKey) {
        Asserts.assertNotNull(rulesConfigKey, "rules config key");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/rules-configs")
                .addPathSegment(rulesConfigKey)
                .build()
                .toString();
        VoidRequest request =  new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
        return request;
    }

    /**
     * Update an existing Rules Config. A token with scope update:rules_configs is needed.
     * See https://auth0.com/docs/api/management/v2#!/Rules_Configs/put_rules_configs_by_key
     *
     * @param rulesConfigKey the rules config key
     * @param rulesConfig    the rules config data to set. It can't include key.
     * @return a Request to execute.
     */
    public Request<RulesConfig> update(String rulesConfigKey, RulesConfig rulesConfig) {
        Asserts.assertNotNull(rulesConfigKey, "rules config key");
        Asserts.assertNotNull(rulesConfig, "rules config");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/rules-configs")
                .addPathSegment(rulesConfigKey)
                .build()
                .toString();
        BaseRequest<RulesConfig> request = new BaseRequest<>(this.client, tokenProvider, url,  HttpMethod.PUT, new TypeReference<RulesConfig>() {
        });
        request.setBody(rulesConfig);
        return request;
    }
}
