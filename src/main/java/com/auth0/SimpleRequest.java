package com.auth0;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

public class SimpleRequest<T> implements Request<T> {

    private final OkHttpClient client;
    private final String url;
    private final String method;
    private final Class<T> tClazz;

    public SimpleRequest(OkHttpClient client, String url, String method, Class<T> tClazz) {
        this.client = client;
        this.url = url;
        this.method = method;
        this.tClazz = tClazz;
    }

    private okhttp3.Request createRequest() {
        return new okhttp3.Request.Builder()
                .url(url)
                .method(method, createBody())
                .build();
    }

    private RequestBody createBody() {
        return null;
    }

    /**
     * Executes this request.
     *
     * @return the response body JSON decoded as T
     * @throws RequestFailedException if the request execution fails.
     */
    public T execute() throws RequestFailedException {
        okhttp3.Request request = createRequest();
        Response response;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RequestFailedException(String.format("Failed to execute %s request on %s", method, url), e);
        }

        ResponseBody body = response.body();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(body.charStream(), tClazz);
        } catch (IOException e) {
            throw new RequestFailedException("Failed to parse JSON body", e);
        } finally {
            body.close();
        }
    }
}
