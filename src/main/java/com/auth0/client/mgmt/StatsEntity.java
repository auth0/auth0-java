package com.auth0.client.mgmt;

import com.auth0.utils.Asserts;
import com.auth0.json.mgmt.DailyStats;
import com.auth0.net.CustomRequest;
import com.auth0.net.Request;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

import java.util.List;

public class StatsEntity extends BaseManagementEntity {

    StatsEntity(OkHttpClient client, String baseUrl, String apiToken) {
        super(client, baseUrl, apiToken);
    }

    /**
     * Request the Active Users Count (logged in during the last 30 days). A token with scope read:stats is needed.
     *
     * @return a Request to execute.
     */
    public Request<Integer> getActiveUsersCount() {
        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("stats")
                .addPathSegment("active-users")
                .build()
                .toString();

        CustomRequest<Integer> request = new CustomRequest<>(client, url, "GET", new TypeReference<Integer>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

    /**
     * Request the Daily Stats for a given period. A token with scope read:stats is needed.
     *
     * @param dateFrom the first day of the period (inclusive). Must have YYYYMMDD format.
     * @param dateTo   the last day of the period (inclusive). Must have YYYYMMDD format.
     * @return a Request to execute.
     */
    public Request<List<DailyStats>> getDailyStats(String dateFrom, String dateTo) {
        Asserts.assertNotNull(dateFrom, "date from");
        Asserts.assertNotNull(dateTo, "date to");

        String url = HttpUrl.parse(baseUrl)
                .newBuilder()
                .addPathSegment("api")
                .addPathSegment("v2")
                .addPathSegment("stats")
                .addPathSegment("daily")
                .build()
                .toString();

        CustomRequest<List<DailyStats>> request = new CustomRequest<>(client, url, "GET", new TypeReference<List<DailyStats>>() {
        });
        request.addHeader("Authorization", "Bearer " + apiToken);
        return request;
    }

}
