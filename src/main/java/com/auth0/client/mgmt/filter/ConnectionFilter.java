package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Connections endpoint. Related to the {@link com.auth0.client.mgmt.ConnectionsEntity()} entity.
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

    @Override
    public ConnectionFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }
}
