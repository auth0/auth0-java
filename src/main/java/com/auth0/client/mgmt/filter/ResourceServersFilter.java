package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Resource Servers endpoint. Related to the {@link com.auth0.client.mgmt.ResourceServerEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class ResourceServersFilter extends BaseFilter {

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public ResourceServersFilter withPage(int pageNumber, int amountPerPage) {
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
    public ResourceServersFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    /**
     * Filter by specific identifier IDs (i.e. audience)
     *
     * @param identifiers the identifier IDs to filter by
     * @return this filter instance
     */
    public ResourceServersFilter withIdentifiers(String identifiers) {
        parameters.put("identifiers", identifiers);
        return this;
    }

    /**
     * Filter by checkpoint pagination support
     *
     * @param from the starting index identifier
     * @param take the number of items to retrieve
     * @return this filter instance
     */
    public ResourceServersFilter withCheckpointPagination(String from, int take) {
        parameters.put("from", from);
        parameters.put("take", take);
        return this;
    }

}
