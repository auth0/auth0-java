package com.auth0.client.mgmt;

import com.auth0.json.mgmt.EmailTemplate;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Email Templates methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Email_Templates
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class EmailTemplatesEntity extends BaseManagementEntity {

    public static final String TEMPLATE_VERIFY_EMAIL = "verify_email";
    public static final String TEMPLATE_RESET_EMAIL = "reset_email";
    public static final String TEMPLATE_WELCOME_EMAIL = "welcome_email";
    public static final String TEMPLATE_BLOCKED_ACCOUNT = "blocked_account";
    public static final String TEMPLATE_STOLEN_CREDENTIALS = "stolen_credentials";
    public static final String TEMPLATE_ENROLLMENT_EMAIL = "enrollment_email";
    public static final String TEMPLATE_CHANGE_PASSWORD = "change_password";
    public static final String TEMPLATE_PASSWORD_RESET = "password_reset";
    public static final String TEMPLATE_MFA_OOB_CODE = "mfa_oob_code";

    EmailTemplatesEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request the Email Templates. A token with scope read:email_templates is needed.
     * See https://auth0.com/docs/api/management/v2#!/Email_Templates/get_email_templates_by_templateName
     *
     * @param templateName the template name to request. You can use any of the constants defined in {@link EmailTemplatesEntity}
     * @return a Request to execute.
     */
    public Request<EmailTemplate> get(String templateName) {
        Asserts.assertNotNull(templateName, "template name");
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/email-templates")
                .addPathSegment(templateName);
        String url = builder.build().toString();
        CustomRequest<EmailTemplate> request = new CustomRequest<>(client, url, "GET", new TypeReference<EmailTemplate>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Create an Email Template. A token with scope create:email_templates is needed.
     * See https://auth0.com/docs/api/management/v2#!/Email_Templates/post_email_templates
     *
     * @param template the template data to set
     * @return a Request to execute.
     */
    public Request<EmailTemplate> create(EmailTemplate template) {
        Asserts.assertNotNull(template, "template");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/email-templates")
                .build()
                .toString();
        CustomRequest<EmailTemplate> request = new CustomRequest<>(this.client, url, "POST", new TypeReference<EmailTemplate>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(template);
        return request;
    }

    /**
     * Patches the existing Email Template. A token with scope update:email_templates is needed.
     * See https://auth0.com/docs/api/management/v2#!/Email_Templates/patch_email_templates_by_templateName
     *
     * @param templateName the name of the template to update. You can use any of the constants defined in {@link EmailTemplatesEntity}
     * @param template     the email template data to set.
     * @return a Request to execute.
     */
    public Request<EmailTemplate> update(String templateName, EmailTemplate template) {
        Asserts.assertNotNull(templateName, "template name");
        Asserts.assertNotNull(template, "template");

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/email-templates")
                .addPathSegment(templateName)
                .build()
                .toString();
        CustomRequest<EmailTemplate> request = new CustomRequest<>(this.client, url, "PATCH", new TypeReference<EmailTemplate>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        request.setBody(template);
        return request;
    }
}
