package com.auth0.client.mgmt;

import com.auth0.json.mgmt.prompts.Prompt;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

public class PromptsEntity extends BaseManagementEntity {

    private final static String ORGS_PATH = "api/v2/prompts";

    PromptsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Get the prompt.
     * A token with {@code read:prompts} scope is required.
     * @return a Request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/prompts/get-prompts">https://auth0.com/docs/api/management/v2#!/prompts/get-prompts</a>
     */
    public Request<Prompt> getPrompt() {
        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH);
        String url = builder.build().toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Prompt>() {
        });
    }

    /**
     * Get the prompt.
     * A token with {@code update:prompts} scope is required.
     * @return a Request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/prompts/patch-prompts">https://auth0.com/docs/api/management/v2#!/prompts/patch-prompts</a>
     */
    public Request<Prompt> updatePrompt(Prompt prompt) {
        Asserts.assertNotNull(prompt, "prompt");

        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH);
        String url = builder.build().toString();

        BaseRequest<Prompt> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.PATCH, new TypeReference<Prompt>() {
        });

        request.setBody(prompt);
        return request;
    }

    /**
     * Get the custom text for specific prompt and language.
     * A token with {@code read:prompts} scope is required.
     * @param prompt the prompt name.
     * @param language the language.
     * @return a Request to execute.
     *
     *  @see <a href="https://auth0.com/docs/api/management/v2#!/prompts/get-custom-text-by-language">https://auth0.com/docs/api/management/v2#!/prompts/get-custom-text-by-language</a>
     */
    public Request<Object> getCustomText(String prompt, String language) {
        Asserts.assertNotNull(prompt, "prompt");
        Asserts.assertNotNull(language, "language");

        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .addPathSegment(prompt)
                .addPathSegment("custom-text")
                .addPathSegments(language);

        String url = builder.build().toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Object>() {
        });
    }

    /**
     * Set the custom text for specific prompt and language.
     * A token with {@code update:prompts} scope is required.
     * @param prompt the prompt name.
     * @param language the language.
     * @param customText the custom text.
     * @return a Request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/prompts/put-custom-text-by-language">https://auth0.com/docs/api/management/v2#!/prompts/put-custom-text-by-language</a>
     */
    public Request<Void> setCustomText(String prompt, String language, Object customText) {
        Asserts.assertNotNull(prompt, "prompt");
        Asserts.assertNotNull(language, "language");
        Asserts.assertNotNull(customText, "customText");

        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .addPathSegment(prompt)
                .addPathSegment("custom-text")
                .addPathSegments(language);

        String url = builder.build().toString();

        BaseRequest<Void> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.PUT, new TypeReference<Void>() {
        });

        request.setBody(customText);
        return request;
    }

    /**
     * Get the partials for specific prompt.
     * A token with {@code read:prompts} scope is required.
     * @param prompt the prompt name.
     * @return a Request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/prompts/get-partialse">https://auth0.com/docs/api/management/v2#!/prompts/get-partials</a>
     */
    public Request<Object> getPartialsPrompt(String prompt) {
        Asserts.assertNotNull(prompt, "prompt");

        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .addPathSegment(prompt)
                .addPathSegment("partials");

        String url = builder.build().toString();

        return new BaseRequest<>(client, tokenProvider, url, HttpMethod.GET, new TypeReference<Object>() {
        });
    }

    /**
     * Set the partials for specific prompt.
     * A token with {@code read:prompts} scope is required.
     * @param prompt the prompt name.
     * @param partials the partials.
     * @return a Request to execute.
     *
     * @see <a href="https://auth0.com/docs/api/management/v2#!/prompts/put-partials">https://auth0.com/docs/api/management/v2#!/prompts/put-partials</a>
     */
    public Request<Void> setPartialsPrompt(String prompt, Object partials) {
        Asserts.assertNotNull(prompt, "prompt");
        Asserts.assertNotNull(partials, "partials");

        HttpUrl.Builder builder = baseUrl.newBuilder()
                .addPathSegments(ORGS_PATH)
                .addPathSegment(prompt)
                .addPathSegment("partials");

        String url = builder.build().toString();

        BaseRequest<Void> request = new BaseRequest<>(client, tokenProvider, url, HttpMethod.PUT, new TypeReference<Void>() {
        });

        request.setBody(partials);
        return request;
    }

}
