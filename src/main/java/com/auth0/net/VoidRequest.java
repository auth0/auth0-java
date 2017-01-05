package com.auth0.net;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Response;

import java.util.Map;

public class VoidRequest extends CustomRequest<Void> implements SignUpRequest {
    private static final MediaType JSON = MediaType.parse("application/json");

    public VoidRequest(OkHttpClient client, String url, String method) {
        super(client, url, method, Void.class);
    }

    @Override
    protected Void parseResponse(Response response) throws RequestFailedException {
        if (!response.isSuccessful()) {
            super.parseResponseError(response);
        }
        return null;
    }

    @Override
    public void setCustomFields(Map<String, String> customFields) {
        super.addParameter("user_metadata", customFields);
    }
}
