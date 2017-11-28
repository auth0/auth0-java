package com.auth0.net;

import com.auth0.json.auth.UserInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;

import java.util.Map;

public class CreateUserRequest extends CustomRequest<UserInfo> implements SignUpRequest {

    public CreateUserRequest(OkHttpClient client, String url) {
        super(client, url, "POST", new TypeReference<UserInfo>() {
        });
    }

    @Override
    public SignUpRequest setCustomFields(Map<String, String> customFields) {
        super.addParameter("user_metadata", customFields);
        return this;
    }
}
