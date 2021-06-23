package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.client.mgmt.tokens.TokenProvider;
import com.auth0.json.mgmt.emailproviders.EmailProvider;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.Map;

/**
 * Class that provides an implementation of the Emails methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Emails
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
@SuppressWarnings("WeakerAccess")
public class EmailProviderEntity extends BaseManagementEntity {
    EmailProviderEntity(OkHttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
    }

    /**
     * Request the Email Provider. A token with scope read:email_provider is needed.
     * See https://auth0.com/docs/api/management/v2#!/Emails/get_provider
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<EmailProvider> get(FieldsFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/emails/provider");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                builder.addQueryParameter(e.getKey(), String.valueOf(e.getValue()));
            }
        }
        String url = builder.build().toString();
        CustomRequest<EmailProvider> request = new CustomRequest<>(client, url, "GET", new TypeReference<EmailProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
    }

    /**
     * Setup the Email Provider. A token with scope create:email_provider is needed.
     * See https://auth0.com/docs/api/management/v2#!/Emails/post_provider
     *
     * @param emailProvider the email provider data to set
     * @return a Request to execute.
     */
    public Request<EmailProvider> setup(EmailProvider emailProvider) {
        Asserts.assertNotNull(emailProvider, "email provider");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/emails/provider")
                .build()
                .toString();
        CustomRequest<EmailProvider> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<EmailProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        request.setBody(emailProvider);
        return request;
    }

    /**
     * Delete the existing Email Provider. A token with scope delete:email_provider is needed.
     * See https://auth0.com/docs/api/management/v2#!/Emails/delete_provider
     *
     * @return a Request to execute.
     */
    public Request delete() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/emails/provider")
                .build()
                .toString();
        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        return request;
    }

    /**
     * Update the existing Email Provider. A token with scope update:email_provider is needed.
     * See https://auth0.com/docs/api/management/v2#!/Emails/patch_provider
     *
     * @param emailProvider the email provider data to set.
     * @return a Request to execute.
     */
    public Request<EmailProvider> update(EmailProvider emailProvider) {
        Asserts.assertNotNull(emailProvider, "email provider");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/emails/provider")
                .build()
                .toString();
        CustomRequest<EmailProvider> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<EmailProvider>() {
        });
        request.addHeader("Authorization", "Bearer " + tokenProvider.getToken());
        request.setBody(emailProvider);
        return request;
    }
}
