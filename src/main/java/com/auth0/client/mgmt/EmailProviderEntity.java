package com.auth0.client.mgmt;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.client.mgmt.filter.FieldsFilter;
import com.auth0.json.mgmt.emailproviders.EmailProvider;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Emails methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Emails
 */
@SuppressWarnings("WeakerAccess")
public class EmailProviderEntity {
    private final RequestBuilder requestBuilder;
    EmailProviderEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request the Email Provider. A token with scope read:email_provider is needed.
     * See https://auth0.com/docs/api/management/v2#!/Emails/get_provider
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<EmailProvider> get(FieldsFilter filter) {
        return requestBuilder.get("api/v2/emails/provider")
                             .queryParameters(filter)
                             .request(new TypeReference<EmailProvider>() {
                             });
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

        return requestBuilder.post("api/v2/emails/provider")
                             .body(emailProvider)
                             .request(new TypeReference<EmailProvider>() {
                             });
    }

    /**
     * Delete the existing Email Provider. A token with scope delete:email_provider is needed.
     * See https://auth0.com/docs/api/management/v2#!/Emails/delete_provider
     *
     * @return a Request to execute.
     */
    public Request delete() {
        return requestBuilder.delete("api/v2/emails/provider")
                             .request();
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

        return requestBuilder.patch("api/v2/emails/provider")
                             .body(emailProvider)
                             .request(new TypeReference<EmailProvider>() {
                             });
    }
}
