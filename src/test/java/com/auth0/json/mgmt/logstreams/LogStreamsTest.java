package com.auth0.json.mgmt.logstreams;

import com.auth0.json.JsonMatcher;
import com.auth0.json.JsonTest;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class LogStreamsTest extends JsonTest<LogStream> {

    @Test
    public void shouldDeserialize() throws Exception {
        String httpSinkJson = "{\"id\": \"123\",\"name\": \"my http log stream\",\"type\": \"http\",\"status\":\"active\",\"sink\": {\"httpContentFormat\": \"JSONLINES\",\"httpContentType\": \"application/json\",\"httpEndpoint\": \"https://me.org\",\"httpAuthorization\": \"abc123\"}}";
        LogStream logStream = fromJSON(httpSinkJson, LogStream.class);

        assertThat(logStream, is(notNullValue()));
        assertThat(logStream.getId(), is("123"));
        assertThat(logStream.getName(), is("my http log stream"));
        assertThat(logStream.getType(), is("http"));
        assertThat(logStream.getStatus(), is("active"));
        assertThat(logStream.getSink(), is(notNullValue()));
        assertThat(logStream.getSink().size(), is(4));
        assertThat(logStream.getSink(), hasEntry("httpContentFormat", "JSONLINES"));
        assertThat(logStream.getSink(), hasEntry("httpContentType", "application/json"));
        assertThat(logStream.getSink(), hasEntry("httpEndpoint", "https://me.org"));
        assertThat(logStream.getSink(), hasEntry("httpAuthorization", "abc123"));
    }

    @Test
    public void shouldDeserializeWithDifferentObjectTypes() throws Exception {
        String json = "{\"id\": \"123\",\"name\": \"my http log stream\",\"type\": \"http\",\"status\":\"active\",\"sink\": {\"boolean\": true,\"array\": [\"one\",\"two\"],\"object\": {\"key1\":\"val1\",\"key2\":\"val2\"}}}";
        LogStream logStream = fromJSON(json, LogStream.class);

        assertThat(logStream, is(notNullValue()));
        assertThat(logStream.getSink(), is(notNullValue()));
        assertThat(logStream.getSink().size(), is(3));
        assertThat(logStream.getSink(), hasEntry("boolean", true));
        assertThat(logStream.getSink(), hasEntry("array", Arrays.asList("one","two")));

        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("key1", "val1");
        objectMap.put("key2", "val2");
        assertThat(logStream.getSink(), hasEntry("object", objectMap));
    }

    @Test
    public void shouldSerialize() throws Exception {
        LogStream logStream = new LogStream("http");
        logStream.setName("My Http Log Stream");

        Map<String, Object> sink = new HashMap<>();
        sink.put("httpContentFormat", "JSONLINES");
        sink.put("httpContentType", "application/json");
        sink.put("httpEndpoint", "https://me.org");
        sink.put("httpAuthorization", "abc123");
        logStream.setSink(sink);

        String serialized = toJSON(logStream);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("name", "My Http Log Stream"));
        assertThat(serialized, JsonMatcher.hasEntry("type", "http"));
        assertThat(serialized, JsonMatcher.hasEntry("sink", sink));
    }

    @Test
    public void shouldSerializeWithDifferentObjectTypes() throws Exception {
        LogStream logStream = new LogStream("http");
        logStream.setName("My Log Stream");

        Map<String, Object> sink = new HashMap<>();
        sink.put("boolean", true);
        sink.put("array", Arrays.asList("one", 2));
        sink.put("object", Collections.singletonMap("key", "val"));
        logStream.setSink(sink);

        String serialized = toJSON(logStream);
        assertThat(serialized, is(notNullValue()));
        assertThat(serialized, JsonMatcher.hasEntry("name", "My Log Stream"));
        assertThat(serialized, JsonMatcher.hasEntry("type", "http"));
        assertThat(serialized, JsonMatcher.hasEntry("sink", sink));
    }

    @Test
    public void shouldDeserializeReadOnlyData() throws Exception {
        String readOnlyJson = "{\"id\":\"logStreamId\",\"type\":\"http\"}";

        LogStream logStream = fromJSON(readOnlyJson, LogStream.class);
        assertThat(logStream, is(notNullValue()));
        assertThat(logStream.getId(), is("logStreamId"));
        assertThat(logStream.getType(), is("http"));
    }
}
