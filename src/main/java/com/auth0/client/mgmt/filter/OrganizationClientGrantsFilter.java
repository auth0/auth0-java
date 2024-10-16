package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when listing the client grants associated with an organization.
 * Related to the {@link com.auth0.client.mgmt.OrganizationsEntity} entity.
 */
public class OrganizationClientGrantsFilter extends BaseFilter {

    /**
     * Filter by client id
     *
     * @param clientId only retrieve items with this client id.
     * @return this filter instance
     */
    public OrganizationClientGrantsFilter withClientId(String clientId) {
        parameters.put("client_id", clientId);
        return this;
    }

    /**
     * Filter by audience
     *
     * @param audience only retrieve the item with this audience.
     * @return this filter instance
     */
    public OrganizationClientGrantsFilter withAudience(String audience) {
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
    public OrganizationClientGrantsFilter withPage(int pageNumber, int amountPerPage) {
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
    public OrganizationClientGrantsFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    /**
     * Filter by grant IDs
     *
     * @param grantIds comma-separated list of grant IDs to filter results on.
     * @return this filter instance
     */
    public OrganizationClientGrantsFilter withGrantIds(String grantIds) {
        parameters.put("grant_ids", grantIds);
        return this;
    }
}
