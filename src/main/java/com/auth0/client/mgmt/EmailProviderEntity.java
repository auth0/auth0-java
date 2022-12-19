package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.json.mgmt.emailproviders.EmailProvider;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

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
    EmailProviderEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
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
        BaseRequest<EmailProvider> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<EmailProvider>() {
        });
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
        BaseRequest<EmailProvider> request = new BaseRequest<>(this.client, tokenProvider, url,  HttpMethod.POST, new TypeReference<EmailProvider>() {
        });
        request.setBody(emailProvider);
        return request;
    }

    /**
     * Delete the existing Email Provider. A token with scope delete:email_provider is needed.
     * See https://auth0.com/docs/api/management/v2#!/Emails/delete_provider
     *
     * @return a Request to execute.
     */
    public Request<Void> delete() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/emails/provider")
                .build()
                .toString();
        VoidRequest request =  new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
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
        BaseRequest<EmailProvider> request = new BaseRequest<>(this.client, tokenProvider, url,  HttpMethod.PATCH, new TypeReference<EmailProvider>() {
        });
        request.setBody(emailProvider);
        return request;
    }
}
