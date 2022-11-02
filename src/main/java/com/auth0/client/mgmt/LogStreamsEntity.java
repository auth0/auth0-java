package com.auth0.client.mgmt;

import com.auth0.json.mgmt.logstreams.LogStream;
import com.auth0.net.*;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;

import java.util.List;

/**
 * Class that provides an implementation of the Log Streams methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Log_Streams
 * <p>
 * This class is not thread-safe.
 *
 * @see ManagementAPI
 */
public class LogStreamsEntity extends BaseManagementEntity {

    private final static String LOG_STREAMS_PATH = "api/v2/log-streams";
    private final static String AUTHORIZATION_HEADER = "Authorization";

    LogStreamsEntity(HttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Creates a request to get all Log Streams.
     * See the <a href="https://auth0.com/docs/api/management/v2#!/Log_Streams/get_log_streams">API Documentation for more information.</a>
     *
     * @return the request to execute.
     */
    public Request<List<LogStream>> list() {
        String url = baseUrl
                .newBuilder()
                .addPathSegments(LOG_STREAMS_PATH)
                .build()
                .toString();

        CustomRequest<List<LogStream>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<LogStream>>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Creates a request to get a single Log Stream by its ID.
     * See the <a href="https://auth0.com/docs/api/management/v2#!/Log_Streams/get_log_streams_by_id">API Documentation for more information.</a>
     *
     * @param logStreamId The ID of the Log Stream to retrieve.
     * @return the request to execute.
     */
    public Request<LogStream> get(String logStreamId) {
        Asserts.assertNotNull(logStreamId, "log stream id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments(LOG_STREAMS_PATH)
                .addPathSegment(logStreamId)
                .build()
                .toString();

        CustomRequest<LogStream> request = new CustomRequest<>(client, url, "GET", new TypeReference<LogStream>() {
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }

    /**
     * Creates a request to create a Log Stream.
     * See the <a href="https://auth0.com/docs/api/management/v2#!/Log_Streams/post_log_streams">API Documentation for more information.</a>
     *
     * @param logStream The {@linkplain LogStream} to create.
     * @return the request to execute.
     */
    public Request<LogStream> create(LogStream logStream) {
        Asserts.assertNotNull(logStream, "log stream");

        String url = baseUrl
                .newBuilder()
                .addPathSegments(LOG_STREAMS_PATH)
                .build()
                .toString();

        CustomRequest<LogStream> request = new CustomRequest<>(client, url, "POST", new TypeReference<LogStream>(){});
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(logStream);
        return request;
    }

    /**
     * Creates a request to update a Log Stream.
     * See the <a href="https://auth0.com/docs/api/management/v2#!/Log_Streams/patch_log_streams_by_id">API Documentation for more information.</a>
     *
     * @param logStreamId The ID of the Log Stream to update.
     * @param logStream   The {@linkplain LogStream} to update.
     * @return the request to execute.
     */
    public Request<LogStream> update(String logStreamId, LogStream logStream) {
        Asserts.assertNotNull(logStreamId, "log stream id");
        Asserts.assertNotNull(logStream, "log stream");

        String url = baseUrl
                .newBuilder()
                .addPathSegments(LOG_STREAMS_PATH)
                .addPathSegment(logStreamId)
                .build()
                .toString();

        CustomRequest<LogStream> request = new CustomRequest<>(client, url, "PATCH", new TypeReference<LogStream>(){
        });
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        request.setBody(logStream);
        return request;
    }

    /**
     * Creates a request to delete a Log Stream.
     * See the <a href="https://auth0.com/docs/api/management/v2#!/Log_Streams/delete_log_streams_by_id">API Documentation for more information.</a>
     *
     * @param logStreamId The ID of the Log Stream to delete.
     * @return the request to execute.
     */
    public Request<Void> delete(String logStreamId) {
        Asserts.assertNotNull(logStreamId, "log stream id");

        String url = baseUrl
                .newBuilder()
                .addPathSegments(LOG_STREAMS_PATH)
                .addPathSegment(logStreamId)
                .build()
                .toString();

        VoidRequest request = new VoidRequest(client, url, "DELETE");
        request.addHeader(AUTHORIZATION_HEADER, "Bearer " + apiToken);
        return request;
    }
}
