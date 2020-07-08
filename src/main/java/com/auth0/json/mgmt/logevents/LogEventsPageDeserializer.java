package com.auth0.json.mgmt.logevents;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a given paged response into their {@link LogEventsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see com.auth0.client.mgmt.LogEventsEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class LogEventsPageDeserializer extends PageDeserializer<LogEventsPage, LogEvent> {

    protected LogEventsPageDeserializer() {
        super(LogEvent.class, "logs");
    }

    @Override
    protected LogEventsPage createPage(List<LogEvent> items) {
        return new LogEventsPage(items);
    }

    @Override
    protected LogEventsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<LogEvent> items) {
        return new LogEventsPage(start, length, total, limit, items);
    }
}
