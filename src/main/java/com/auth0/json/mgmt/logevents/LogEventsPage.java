package com.auth0.json.mgmt.logevents;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a Page of Auth0 Events objects. Related to the {@link com.auth0.client.mgmt.LogEventsEntity()} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = LogEventsPageDeserializer.class)
public class LogEventsPage extends Page<LogEvent> {

    public LogEventsPage(List<LogEvent> items) {
        super(items);
    }

    public LogEventsPage(Integer start, Integer length, Integer total, Integer limit, List<LogEvent> items) {
        super(start, length, total, limit, items);
    }

}