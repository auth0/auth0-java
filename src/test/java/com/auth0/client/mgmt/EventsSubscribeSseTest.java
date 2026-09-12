package com.auth0.client.mgmt;

import com.auth0.client.mgmt.types.EventStreamCloudEventUserUpdatedObjectIdentitiesItem;
import com.auth0.client.mgmt.types.EventStreamCloudEventUserUpdatedObjectIdentitiesItemCustom;
import com.auth0.client.mgmt.types.EventStreamCloudEventUserUpdatedObjectIdentitiesItemDatabase;
import com.auth0.client.mgmt.types.EventStreamCloudEventUserUpdatedObjectIdentitiesItemEnterprise;
import com.auth0.client.mgmt.types.EventStreamCloudEventUserUpdatedObjectIdentitiesItemPasswordless;
import com.auth0.client.mgmt.types.EventStreamCloudEventUserUpdatedObjectIdentitiesItemSocial;
import com.auth0.client.mgmt.types.EventStreamSubscribeEventsEventTypeEnum;
import com.auth0.client.mgmt.types.EventStreamSubscribeEventsResponseContent;
import com.auth0.client.mgmt.types.SubscribeEventsRequestParameters;
import java.util.Arrays;
import java.util.Iterator;
import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Regression coverage for the Events API SSE subscription: the event must resolve to its typed variant
 */
public class EventsSubscribeSseTest {

    private static final String USER_UPDATED_SSE_BODY = "event: user.updated\n" + "id: evt-anonymized-1\n"
            + "data: {\"offset\":\"off-anonymized-1\",\"event\":{\"specversion\":\"1.0\",\"type\":\"user.updated\","
            + "\"source\":\"urn:auth0:example-test.eu.auth0.com\",\"id\":\"evt_test_1\","
            + "\"time\":\"2026-07-10T01:01:42.873Z\",\"data\":{\"object\":{"
            + "\"user_id\":\"auth0|00000000-0000-0000-0000-000000000001\","
            + "\"email\":\"user@example.com\",\"email_verified\":true,"
            + "\"identities\":[{\"connection\":\"Example-Database\",\"isSocial\":false,\"provider\":\"auth0\","
            + "\"user_id\":\"00000000-0000-0000-0000-000000000001\"}],"
            + "\"created_at\":\"2026-05-08T09:33:01.503Z\",\"updated_at\":\"2026-07-10T01:01:42.868Z\"}},"
            + "\"a0tenant\":\"example-test\",\"a0stream\":\"test-stream\"}}\n\n";

    private MockWebServer server;
    private ManagementApi client;

    @BeforeEach
    public void setup() throws Exception {
        server = new MockWebServer();
        server.start();
        client = ManagementApi.builder()
                .url(server.url("/").toString())
                .token("test-token")
                .build();
    }

    @AfterEach
    public void teardown() throws Exception {
        server.shutdown();
    }

    @Test
    public void subscribeResolvesUserUpdatedEventToTypedVariant() throws Exception {
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "text/event-stream")
                .setBody(USER_UPDATED_SSE_BODY));

        Iterable<EventStreamSubscribeEventsResponseContent> response = client.events()
                .subscribe(SubscribeEventsRequestParameters.builder()
                        .eventType(Arrays.asList(EventStreamSubscribeEventsEventTypeEnum.USER_UPDATED))
                        .build());

        RecordedRequest request = server.takeRequest();
        Assertions.assertEquals("GET", request.getMethod());
        HttpUrl requestUrl = request.getRequestUrl();
        Assertions.assertNotNull(requestUrl);
        Assertions.assertEquals("user.updated", requestUrl.queryParameter("event_type"));

        Iterator<EventStreamSubscribeEventsResponseContent> iterator = response.iterator();
        Assertions.assertTrue(iterator.hasNext());
        EventStreamSubscribeEventsResponseContent event = iterator.next();
        Assertions.assertFalse(iterator.hasNext(), "expected exactly one event");

        Assertions.assertFalse(event._isUnknown());
        Assertions.assertTrue(event.isUserUpdated());
        Assertions.assertTrue(event.getUserUpdated().isPresent());

        Assertions.assertEquals("off-anonymized-1", event.getUserUpdated().get().getOffset());
        Assertions.assertEquals(
                "auth0|00000000-0000-0000-0000-000000000001",
                event.getUserUpdated().get().getEvent().getData().getObject().getUserId());

        EventStreamCloudEventUserUpdatedObjectIdentitiesItem identity = event.getUserUpdated()
                .get()
                .getEvent()
                .getData()
                .getObject()
                .getIdentities()
                .get(0);
        Object resolvedIdentity =
                identity.visit(new EventStreamCloudEventUserUpdatedObjectIdentitiesItem.Visitor<Object>() {
                    @Override
                    public Object visit(EventStreamCloudEventUserUpdatedObjectIdentitiesItemCustom value) {
                        return value;
                    }

                    @Override
                    public Object visit(EventStreamCloudEventUserUpdatedObjectIdentitiesItemDatabase value) {
                        return value;
                    }

                    @Override
                    public Object visit(EventStreamCloudEventUserUpdatedObjectIdentitiesItemEnterprise value) {
                        return value;
                    }

                    @Override
                    public Object visit(EventStreamCloudEventUserUpdatedObjectIdentitiesItemPasswordless value) {
                        return value;
                    }

                    @Override
                    public Object visit(EventStreamCloudEventUserUpdatedObjectIdentitiesItemSocial value) {
                        return value;
                    }
                });

        Assertions.assertInstanceOf(
                EventStreamCloudEventUserUpdatedObjectIdentitiesItemDatabase.class, resolvedIdentity);
        EventStreamCloudEventUserUpdatedObjectIdentitiesItemDatabase database =
                (EventStreamCloudEventUserUpdatedObjectIdentitiesItemDatabase) resolvedIdentity;
        Assertions.assertEquals("Example-Database", database.getConnection());
        Assertions.assertEquals("auth0", database.getProvider().toString());
    }

    @Test
    public void unrecognizedEventTypeFallsBackToUnknownWithoutThrowing() throws Exception {
        String body = "event: user.teleported\n" + "data: {\"foo\":\"bar\"}\n\n";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setHeader("Content-Type", "text/event-stream")
                .setBody(body));

        Iterable<EventStreamSubscribeEventsResponseContent> response = client.events()
                .subscribe(SubscribeEventsRequestParameters.builder().build());

        Iterator<EventStreamSubscribeEventsResponseContent> iterator = response.iterator();
        Assertions.assertTrue(iterator.hasNext());
        EventStreamSubscribeEventsResponseContent event = Assertions.assertDoesNotThrow(iterator::next);
        Assertions.assertTrue(event._isUnknown());
    }
}
