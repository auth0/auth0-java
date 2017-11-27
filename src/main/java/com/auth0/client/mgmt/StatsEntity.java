package com.auth0.client.mgmt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.auth0.client.mgmt.builder.RequestBuilder;
import com.auth0.json.mgmt.DailyStats;
import com.auth0.net.Request;
import com.auth0.utils.Asserts;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Class that provides an implementation of the Stats methods of the Management API as defined in https://auth0.com/docs/api/management/v2#!/Stats
 */
@SuppressWarnings("WeakerAccess")
public class StatsEntity {
    private final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private final RequestBuilder requestBuilder;

    StatsEntity(OkHttpClient client, HttpUrl baseUrl, String apiToken) {
        requestBuilder = new RequestBuilder(client, baseUrl, apiToken);
    }

    /**
     * Request the Active Users Count (logged in during the last 30 days). A token with scope read:stats is needed.
     * See https://auth0.com/docs/api/management/v2#!/Stats/get_active_users
     *
     * @return a Request to execute.
     */
    public Request<Integer> getActiveUsersCount() {
        return requestBuilder.get("api/v2/stats/active-users")
                             .request(new TypeReference<Integer>() {
                             });
    }

    /**
     * Request the Daily Stats for a given period. A token with scope read:stats is needed.
     * See https://auth0.com/docs/api/management/v2#!/Stats/get_daily
     *
     * @param from the first day of the period (inclusive). Time is not taken into account.
     * @param to   the last day of the period (inclusive). Time is not taken into account.
     * @return a Request to execute.
     */
    public Request<List<DailyStats>> getDailyStats(Date from, Date to) {
        Asserts.assertNotNull(from, "date from");
        Asserts.assertNotNull(to, "date to");

        return requestBuilder.get("api/v2/stats/daily")
                             .queryParameter("from", formatDate(from))
                             .queryParameter("to", formatDate(to))
                             .request(new TypeReference<List<DailyStats>>() {
                             });
    }

    protected String formatDate(Date date) {
        return SIMPLE_DATE_FORMAT.format(date);
    }
}
