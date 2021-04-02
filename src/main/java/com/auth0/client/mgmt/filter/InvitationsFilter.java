package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when fetching invitations. Related to the {@linkplain com.auth0.client.mgmt.OrganizationsEntity}.
 * <p>
 * This class is not thread-safe.
 */
public class InvitationsFilter extends BaseFilter {

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public InvitationsFilter withPage(int pageNumber, int amountPerPage) {
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
    public InvitationsFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    /**
     * Only retrieve certain fields from the item.
     *
     * @param fields        a list of comma separated fields to retrieve.
     * @param includeFields whether to include or exclude in the response the fields that were given.
     * @return this filter instance
     */
    public InvitationsFilter withFields(String fields, boolean includeFields) {
        parameters.put("fields", fields);
        parameters.put("include_fields", includeFields);
        return this;
    }

    /**
     * Sort the query
     *
     * @param sort the field to use for sorting. Use 'field:order' where order is 1 for ascending and -1 for descending.
     * @return this filter instance
     */
    public InvitationsFilter withSort(String sort) {
        parameters.put("sort", sort);
        return this;
    }

}
