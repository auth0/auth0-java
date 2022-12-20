package com.auth0.client.mgmt;

import com.auth0.json.mgmt.stats.DailyStats;
import com.auth0.net.Request;
import com.auth0.net.client.HttpMethod;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StatsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldGetActiveUsersCount() throws Exception {
        Request<Integer> request = api.stats().getActiveUsersCount();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_ACTIVE_USERS_COUNT, 200);
        Integer response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/stats/active-users"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldThrowOnGetDailyStatsWithNullDateFrom() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'date from' cannot be null!");
        api.stats().getDailyStats(null, new Date());
    }

    @Test
    public void shouldThrowOnGetDailyStatsWithNullDateTo() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'date to' cannot be null!");
        api.stats().getDailyStats(new Date(), null);
    }

    @SuppressWarnings("deprecation")
    @Test
    public void shouldGetDailyStats() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2016);
        calendar.set(Calendar.MONTH, Calendar.OCTOBER);
        calendar.set(Calendar.DATE, 11);
        Date dateFrom = calendar.getTime();
        calendar.set(Calendar.DATE, 12);
        Date dateTo = calendar.getTime();
        Request<List<DailyStats>> request = api.stats().getDailyStats(dateFrom, dateTo);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_DAILY_STATS_LIST, 200);
        List<DailyStats> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath(HttpMethod.GET, "/api/v2/stats/daily"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
        assertThat(recordedRequest, hasQueryParameter("from", "20161011"));
        assertThat(recordedRequest, hasQueryParameter("to", "20161012"));

        assertThat(response, is(notNullValue()));
    }

    @Test
    public void shouldReturnEmptyDailyStats() throws Exception {
        Request<List<DailyStats>> request = api.stats().getDailyStats(new Date(), new Date());
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<DailyStats> response = request.execute().getBody();

        assertThat(response, is(notNullValue()));
        assertThat(response, is(emptyCollectionOf(DailyStats.class)));
    }

    @Test
    public void shouldFormatDateToYYYYMMDD() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2010);
        //Calendar.MONTH starts at 0 being January
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DATE, 22);

        assertThat(api.stats().formatDate(calendar.getTime()), is("20100122"));

    }
}
