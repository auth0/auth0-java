package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@SuppressWarnings("unused")
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