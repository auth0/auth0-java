package com.auth0.net;

import com.auth0.exception.APIException;
import com.auth0.exception.Auth0Exception;
import com.auth0.json.mgmt.jobs.JobError;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class StatusCodeRequest extends EmptyBodyRequest<JobError> {

    public StatusCodeRequest(final OkHttpClient client, final String url) {
        super(client, url, "GET", new TypeReference<JobError>() {});
    }

    @Override
    protected RequestBody createBody() {
        return null;
    }

    @Override
    protected JobError parseResponse(final Response response) throws Auth0Exception {
        if (!response.isSuccessful()) {
            throw createResponseException(response);
        }

        try (final ResponseBody body = response.body()) {
            final String payload = body.string();
            return new JobError(response.code(), payload);
        } catch (final IOException e) {
            throw new APIException("Failed to parse response", response.code(), e);
        }
    }
}
