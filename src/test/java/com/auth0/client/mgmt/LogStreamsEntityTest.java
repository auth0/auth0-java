package com.auth0.client.mgmt;

import com.auth0.client.MockServer;
import com.auth0.json.mgmt.logstreams.LogStream;
import com.auth0.net.Request;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.auth0.client.MockServer.*;
import static com.auth0.client.RecordedRequestMatcher.hasHeader;
import static com.auth0.client.RecordedRequestMatcher.hasMethodAndPath;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class LogStreamsEntityTest extends BaseMgmtEntityTest {

    @Test
    public void shouldListLogStreams() throws Exception {
        Request<List<LogStream>> request = api.logStreams().list();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_LOG_STREAMS_LIST, 200);
        List<LogStream> response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/log-streams"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasSize(2));
        assertThat(response, everyItem(hasProperty("id", is(notNullValue()))));
        assertThat(response, everyItem(hasProperty("name", is(notNullValue()))));
        assertThat(response, everyItem(hasProperty("type", is(notNullValue()))));
        assertThat(response, everyItem(hasProperty("status", is(notNullValue()))));
        assertThat(response, everyItem(hasProperty("sink", is(notNullValue()))));
    }

    @Test
    public void shouldReturnEmptyLogStreams() throws Exception {
        Request<List<LogStream>> request = api.logStreams().list();
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_EMPTY_LIST, 200);
        List<LogStream> response = request.execute().getBody();

        assertThat(response, is(notNullValue()));
        assertThat(response, empty());
    }

    @Test
    public void shouldGetLogStream() throws Exception {
        Request<LogStream> request = api.logStreams().get("123");
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MockServer.MGMT_LOG_STREAM, 200);
        LogStream response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("GET", "/api/v2/log-streams/123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasProperty("id", is(notNullValue())));
        assertThat(response, hasProperty("name", is(notNullValue())));
        assertThat(response, hasProperty("type", is(notNullValue())));
        assertThat(response, hasProperty("status", is(notNullValue())));
        assertThat(response, hasProperty("sink", is(notNullValue())));
    }

    @Test
    public void shouldCreateLogStream() throws Exception {
        LogStream logStream = getLogStream("log stream", "http");

        Request<LogStream> request = api.logStreams().create(logStream);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_STREAM, 200);
        LogStream response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("POST", "/api/v2/log-streams"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("name", "log stream"));
        assertThat(body, hasEntry("type", "http"));
        assertThat(body, hasEntry("sink", logStream.getSink()));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasProperty("id", is(notNullValue())));
        assertThat(response, hasProperty("name", is(notNullValue())));
        assertThat(response, hasProperty("type", is(notNullValue())));
        assertThat(response, hasProperty("status", is(notNullValue())));
        assertThat(response, hasProperty("sink", is(notNullValue())));

    }

    @Test
    public void shouldUpdateLogStream() throws Exception {
        LogStream logStream = getLogStream("log stream", null);
        logStream.setStatus("paused");

        Request<LogStream> request = api.logStreams().update("123", logStream);
        assertThat(request, is(notNullValue()));

        server.jsonResponse(MGMT_LOG_STREAM, 200);
        LogStream response = request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("PATCH", "/api/v2/log-streams/123"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));

        Map<String, Object> body = bodyFromRequest(recordedRequest);
        assertThat(body.size(), is(3));
        assertThat(body, hasEntry("name", "log stream"));
        assertThat(body, hasEntry("status", "paused"));
        assertThat(body, hasEntry("sink", logStream.getSink()));

        assertThat(response, is(notNullValue()));
        assertThat(response, hasProperty("id", is(notNullValue())));
        assertThat(response, hasProperty("name", is(notNullValue())));
        assertThat(response, hasProperty("type", is(notNullValue())));
        assertThat(response, hasProperty("status", is(notNullValue())));
        assertThat(response, hasProperty("sink", is(notNullValue())));
    }

    @Test
    public void shouldDeleteLogStream() throws Exception {
        Request<Void> request = api.logStreams().delete("1");
        assertThat(request, is(notNullValue()));

        server.emptyResponse(204);
        request.execute().getBody();
        RecordedRequest recordedRequest = server.takeRequest();

        assertThat(recordedRequest, hasMethodAndPath("DELETE", "/api/v2/log-streams/1"));
        assertThat(recordedRequest, hasHeader("Content-Type", "application/json"));
        assertThat(recordedRequest, hasHeader("Authorization", "Bearer apiToken"));
    }

    @Test
    public void shouldThrowOnGetLogStreamWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'log stream id' cannot be null!");
        api.logStreams().get(null);
    }

    @Test
    public void shouldThrowOnDeleteLogStreamWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'log stream id' cannot be null!");
        api.logStreams().delete(null);
    }

    @Test
    public void shouldThrowOnUpdateLogStreamWithNullId() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'log stream id' cannot be null!");
        api.logStreams().update(null, new LogStream());
    }

    @Test
    public void shouldThrowOnUpdateLogStreamWithNullLogStream() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'log stream' cannot be null!");
        api.logStreams().update("123", null);
    }

    @Test
    public void shouldThrowOnCreateLogStreamWithNullLogStream() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage("'log stream' cannot be null!");
        api.logStreams().create(null);
    }

    private LogStream getLogStream(String name, String type) {
        LogStream logStream = new LogStream(type);
        logStream.setName(name);

        Map<String, Object> sink = new HashMap<>();
        sink.put("httpEndpoint", "https://me.org");
        sink.put("httpAuthorization", "abc123");
        sink.put("httpContentFormat", "JSONLINES");
        sink.put("httpContentType", "application/json");
        logStream.setSink(sink);

        return logStream;
    }
}
