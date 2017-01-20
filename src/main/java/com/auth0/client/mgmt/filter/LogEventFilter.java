package com.auth0.client.mgmt.filter;

public class LogEventFilter extends QueryFilter<LogEventFilter> {

    /**
     * Filter by checkpoint
     *
     * @param from the log event id to start retrieving logs from.
     * @param take the limit of items to retrieve.
     * @return this filter instance
     */
    public LogEventFilter withCheckpoint(String from, int take) {
        parameters.put("from", from);
        parameters.put("take", take);
        return this;
    }

}
