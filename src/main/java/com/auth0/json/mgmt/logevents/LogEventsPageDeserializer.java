package com.auth0.json.mgmt.logevents;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

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
