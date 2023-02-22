package com.auth0.net;

import com.auth0.json.auth.CreatedUser;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

/**
 * Class that represents a Create User call.
 */
public class SignUpRequest extends BaseRequest<CreatedUser> {

    public SignUpRequest(Auth0HttpClient client, String url) {
        super(client, null, url, HttpMethod.POST, new TypeReference<CreatedUser>() {
        });
    }

    /**
     * Setter for the additional fields to set when creating a user.
     *
     * @param customFields the list of custom fields.
     * @return this request instance.
     */
    public SignUpRequest setCustomFields(Map<String, String> customFields) {
        super.addParameter("user_metadata", customFields);
        return this;
    }
}
