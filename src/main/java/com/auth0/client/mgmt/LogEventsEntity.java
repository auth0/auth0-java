package com.auth0.client.mgmt;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.client.mgmt.filter.LogEventFilter;
import com.auth0.json.mgmt.logevents.LogEvent;
import com.auth0.json.mgmt.logevents.LogEventsPage;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Events methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Logs
 */
@SuppressWarnings("WeakerAccess")
public class LogEventsEntity {
    private final RequestBuilder requestBuilder;
    LogEventsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request all the Log Events. A token with scope read:logs is needed.
     * See https://auth0.com/docs/api/management/v2#!/Logs/get_logs
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<LogEventsPage> list(LogEventFilter filter) {

        return requestBuilder.get("api/v2/logs")
                             .queryParameters(filter)
                             .request(new TypeReference<LogEventsPage>() {
                             });
    }

    /**
     * Request a Log Event. A token with scope read:logs is needed.
     * See https://auth0.com/docs/api/management/v2#!/Logs/get_logs_by_id
     *
     * @param logEventId the id of the connection to retrieve.
     * @return a Request to execute.
     */
    public Request<LogEvent> get(String logEventId) {
        Asserts.assertNotNull(logEventId, "log event id");

        return requestBuilder.get("api/v2/logs", logEventId)
                             .request(new TypeReference<LogEvent>() {
                             });
    }
}
