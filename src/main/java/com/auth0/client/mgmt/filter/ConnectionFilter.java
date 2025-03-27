package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Connections endpoint. Related to the {@link com.auth0.client.mgmt.ConnectionsEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class ConnectionFilter extends FieldsFilter {

    /**
     * Filter by strategy
     *
     * @param strategy only retrieve items with this strategy.
     * @return this filter instance
     */
    public ConnectionFilter withStrategy(String strategy) {
        parameters.put("strategy", strategy);
        return this;
    }

    /**
     * Filter by name
     *
     * @param name only retrieve the item with this name.
     * @return this filter instance
     */
    public ConnectionFilter withName(String name) {
        parameters.put("name", name);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public ConnectionFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("per_page", amountPerPage);
        return this;
    }

    /**
     * Include the query summary.
     * Warning: Can only be used with {@link com.auth0.client.mgmt.ConnectionsEntity#listAll(ConnectionFilter)}
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public ConnectionFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    @Override
    public ConnectionFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }

    /**
     * Include the {@code from} parameter to specify where to start the page selection. Only applicable for endpoints that
     * support checkpoint pagination.
     * @param from the ID from which to start selection. This can be obtained from the {@code next} field returned from
     *             a checkpoint-paginated result.
     * @return this filter instance.
     */
    public ConnectionFilter withFrom(String from) {
        parameters.put("from", from);
        return this;
    }

    /**
     * Include the {@code take} parameter to specify the amount of results to return per page. Only applicable for endpoints that
     * support checkpoint pagination.
     * @param take the amount of entries to retrieve per page.
     * @return this filter instance.
     */
    public ConnectionFilter withTake(int take) {
        parameters.put("take", take);
        return this;
    }
}
