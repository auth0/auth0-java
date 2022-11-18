package com.auth0.net;

import com.auth0.json.auth.CreatedUser;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.Map;

public class CreateUserRequest extends CustomRequest<CreatedUser> implements SignUpRequest {

    public CreateUserRequest(Auth0HttpClient client, String url) {
        super(client, null, url, HttpMethod.POST, new TypeReference<CreatedUser>() {
        });
    }

    @Override
    public SignUpRequest setCustomFields(Map<String, String> customFields) {
        super.addParameter("user_metadata", customFields);
        return this;
    }
}
