package com.auth0.net;

import com.auth0.exception.Auth0Exception;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.util.Map;

public class VoidRequest extends CustomRequest<Void> implements SignUpRequest {

    public VoidRequest(OkHttpClient client, String url, String method) {
        super(client, url, method, Void.class);
    }

    @Override
    protected Void parseResponse(Response response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw super.createResponseException(response);
        }
        return null;
    }

    @Override
    public void setCustomFields(Map<String, String> customFields) {
        super.addParameter("user_metadata", customFields);
    }
}
