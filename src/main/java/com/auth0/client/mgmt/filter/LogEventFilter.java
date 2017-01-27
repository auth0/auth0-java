package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Logs endpoint. Related to the {@link com.auth0.client.mgmt.LogEventsEntity()} entity.
 */
public class LogEventFilter extends QueryFilter {

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

    @Override
    public LogEventFilter withTotals(boolean includeTotals) {
        super.withTotals(includeTotals);
        return this;
    }

    @Override
    public LogEventFilter withQuery(String query) {
        super.withQuery(query);
        return this;
    }

    @Override
    public LogEventFilter withSort(String sort) {
        super.withSort(sort);
        return this;
    }

    @Override
    public LogEventFilter withPage(int pageNumber, int amountPerPage) {
        super.withPage(pageNumber, amountPerPage);
        return this;
    }

    @Override
    public LogEventFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }
}
