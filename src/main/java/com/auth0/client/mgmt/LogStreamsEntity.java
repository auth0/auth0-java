package com.auth0.client.mgmt;

import com.auth0.json.mgmt.logstreams.LogStream;
import com.auth0.net.BaseRequest;
import com.auth0.net.Request;
import com.auth0.net.VoidRequest;
import com.auth0.net.client.Auth0HttpClient;
import com.auth0.net.client.HttpMethod;
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

    LogStreamsEntity(Auth0HttpClient client, HttpUrl baseUrl, TokenProvider tokenProvider) {
        super(client, baseUrl, tokenProvider);
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

        return new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<List<LogStream>>() {
        });
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

        return new BaseRequest<>(client, tokenProvider, url,  HttpMethod.GET, new TypeReference<LogStream>() {
        });
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

        BaseRequest<LogStream> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.POST, new TypeReference<LogStream>(){});
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

        BaseRequest<LogStream> request =  new BaseRequest<>(client, tokenProvider, url,  HttpMethod.PATCH, new TypeReference<LogStream>(){
        });
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

        return new VoidRequest(client, tokenProvider,  url, HttpMethod.DELETE);
    }
}
