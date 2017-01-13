package com.auth0.net;

public class LogEventFilter extends BaseFilter<LogEventFilter> {

    /**
     * Filter by a query
     *
     * @param query         the query expression using the following syntax https://auth0.com/docs/api/management/v2/query-string-syntax.
     * @param sort          the field to use for sorting. Use 'field:order' where order is 1 for ascending and -1 for descending.
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public LogEventFilter withQuery(String query, String sort, boolean includeTotals) {
        filters.put("q", query);
        filters.put("sort", sort);
        filters.put("include_totals", includeTotals);
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
        filters.put("from", from);
        filters.put("take", take);
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
        filters.put("page", pageNumber);
        filters.put("per_page", amountPerPage);
        return this;
    }

}
