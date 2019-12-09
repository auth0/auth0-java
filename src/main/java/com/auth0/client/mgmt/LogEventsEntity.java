package com.auth0.client.mgmt;

import com.auth0.client.mgmt.filter.LogEventFilter;
import com.auth0.json.mgmt.logevents.LogEvent;
import com.auth0.json.mgmt.logevents.LogEventsPage;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.Map;

import static com.auth0.client.mgmt.filter.QueryFilter.KEY_QUERY;

/**
 * Class that provides an implementation of the Events methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Logs
 */
@SuppressWarnings("WeakerAccess")
public class LogEventsEntity extends BaseManagementEntity {

    LogEventsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request all the Log Events. A token with scope read:logs is needed.
     * See https://auth0.com/docs/api/management/v2#!/Logs/get_logs
     *
     * @param filter the filter to use. Can be null.
     * @return a Request to execute.
     */
    public Request<LogEventsPage> list(LogEventFilter filter) {
        HttpUrl.Builder builder = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/logs");
        if (filter != null) {
            for (Map.Entry<String, Object> e : filter.getAsMap().entrySet()) {
                if (KEY_QUERY.equals(e.getKey())) {
                    builder.addEncodedQueryParameter(e.getKey(), String.valueOf(e.getValue()));
                } else {
                    builder.addEncodedQueryParameter(e.getKey(), String.valueOf(e.getValue()));
                }
            }
        }
        String url = builder.build().toString();
        CustomRequest<LogEventsPage> request = new CustomRequest<>(client, url, "GET", new TypeReference<LogEventsPage>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
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

        String url = baseUrl
                .newBuilder()
                .addPathSegments("api/v2/logs")
                .addPathSegment(logEventId)
                .build()
                .toString();
        CustomRequest<LogEvent> request = new CustomRequest<>(client, url, "GET", new TypeReference<LogEvent>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }
}
