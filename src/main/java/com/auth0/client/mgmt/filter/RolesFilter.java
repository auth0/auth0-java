package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Grants endpoint. Related to the {@link com.auth0.client.mgmt.RolesEntity} entity.
 */
public class RolesFilter extends BaseFilter {

    /**
     * Filter by name
     *
     * @param name only retrieve items with this name.
     * @return this filter instance
     */
    public RolesFilter withName(String name) {
        parameters.put("name_filter", name);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public RolesFilter withPage(int pageNumber, int amountPerPage) {
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
    public RolesFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

}
