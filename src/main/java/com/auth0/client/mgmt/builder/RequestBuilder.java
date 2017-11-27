package com.auth0.client.mgmt.builder;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class RequestBuilder {
    private final HttpUrl baseUrl;
    private final String apiToken;
    private final OkHttpClient client;

    public RequestBuilder(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        this.baseUrl = baseUrl;
        this.apiToken = apiToken;
        this.client = client;
    }

    /**
     * Constructs POST request
     *
     * @param path     "some/path" will be "http://some/path"
     * @param segments each segment will be added to path delimited by "/"
     * @return builder for further construction
     */
    public RequestUnderConstruction post(String path, String... segments) {
        return new RequestUnderConstruction(baseUrl, apiToken, client,
                                            "POST",
                                            path,
                                            segments);
    }

    /**
     * Constructs GET request
     * @param path "some/path" will be "http://some/path"
     * @param segments each segment will be added to path delimited by "/"
     * @return builder for further construction
     */
    public RequestUnderConstruction get(String path, String... segments) {
        return new RequestUnderConstruction(baseUrl, apiToken, client,
                                            "GET",
                                            path,
                                            segments);
    }

    /**
     * Constructs DELETE request
     * @param path "some/path" will be "http://some/path"
     * @param segments each segment will be added to path delimited by "/"
     * @return builder for further construction
     */
    public RequestUnderConstruction delete(String path, String... segments) {
        return new RequestUnderConstruction(baseUrl, apiToken, client,
                                            "DELETE",
                                            path,
                                            segments);
    }

    /**
     * Constructs PATCH request
     * @param path "some/path" will be "http://some/path"
     * @param segments each segment will be added to path delimited by "/"
     * @return builder for further construction
     */
    public RequestUnderConstruction patch(String path, String... segments) {
        return new RequestUnderConstruction(baseUrl, apiToken, client,
                                            "PATCH",
                                            path,
                                            segments);
    }

    /**
     * Constructs PUT request
     * @param path "some/path" will be "http://some/path"
     * @param segments each segment will be added to path delimited by "/"
     * @return builder for further construction
     */
    public RequestUnderConstruction put(String path, String... segments) {
        return new RequestUnderConstruction(baseUrl, apiToken, client,
                                            "PUT",
                                            path,
                                            segments);
    }
}
