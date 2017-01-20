package com.auth0.client.mgmt;

import com.auth0.json.mgmt.DailyStats;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.List;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class StatsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldGetActiveUsersCount() throws Exception {
        Request<Integer> request = api.stats().getActiveUsersCount();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_ACTIVE_USERS_COUNT, 200);
        Integer response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/stats/active-users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetDailyStatsWithNullDateFrom() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'date from' cannot be null!");
        api.stats().getDailyStats(null, "20161011");
    }

    @Test
    public void shouldThrowOnGetDailyStatsWithNullDateTo() throws Exception {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'date to' cannot be null!");
        api.stats().getDailyStats("20161011", null);
    }

    @Test
    public void shouldGetDailyStats() throws Exception {
        Request<List<DailyStats>> request = api.stats().getDailyStats("20161011", "20161011");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DAILY_STATS_LIST, 200);
        List<DailyStats> response = request.execute();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/stats/daily"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldReturnEmptyDailyStats() throws Exception {
        Request<List<DailyStats>> request = api.stats().getDailyStats("20161011", "20161011");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<DailyStats> response = request.execute();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(DailyStats.class)));
    }
}
