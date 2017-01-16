package com.auth0.net;

public class LogEventFilter extends BaseFilter<LogEventFilter> {

    /**
     * Filter by a query
     *
     * @param query the query expression using the following syntax https://auth0.com/docs/api/management/v2/query-string-syntax.
     * @return this filter instance
     */
    public LogEventFilter withQuery(String query) {
        parameters.put("q", query);
        return this;
    }

    /**
     * Include the query summary
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public LogEventFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    /**
     * Sort the query
     *
     * @param sort the field to use for sorting. Use 'field:order' where order is 1 for ascending and -1 for descending.
     * @return this filter instance
     */
    public LogEventFilter withSort(String sort) {
        parameters.put("sort", sort);
        return this;
    }

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

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public LogEventFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("per_page", amountPerPage);
        return this;
    }

}
