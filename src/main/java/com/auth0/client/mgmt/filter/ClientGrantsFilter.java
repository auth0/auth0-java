package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Client Grants endpoint. Related to the {@link com.auth0.client.mgmt.ClientGrantsEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class ClientGrantsFilter extends BaseFilter {

    /**
     * Filter by client id
     *
     * @param clientId only retrieve items with this client id.
     * @return this filter instance
     */
    public ClientGrantsFilter withClientId(String clientId) {
        parameters.put("client_id", clientId);
        return this;
    }

    /**
     * Filter by audience
     *
     * @param audience only retrieve the item with this audience.
     * @return this filter instance
     */
    public ClientGrantsFilter withAudience(String audience) {
        parameters.put("audience", audience);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public ClientGrantsFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("per_page", amountPerPage);
        return this;
    }

    /**
     * Include the query summary
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public ClientGrantsFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

}
