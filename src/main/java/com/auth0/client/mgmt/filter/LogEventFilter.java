package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Logs endpoint. Related to the {@link com.auth0.client.mgmt.LogEventsEntity()} entity.
 */
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
