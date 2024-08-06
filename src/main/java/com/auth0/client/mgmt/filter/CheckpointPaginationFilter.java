package com.auth0.client.mgmt.filter;

public class CheckpointPaginationFilter extends BaseFilter {

    /**
     * Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
     *
     * @param includeTotals whether to include or not total result count.
     * @return this filter instance
     */
    public CheckpointPaginationFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    /**
     * Optional ID from which to start selection (exclusive).
     *
     * @param from the ID from which to start selection. This can be obtained from the {@code next} field returned from
     *             a checkpoint-paginated result.
     * @return this filter instance.
     */
    public CheckpointPaginationFilter withFrom(String from) {
        parameters.put("from", from);
        return this;
    }

    /**
     * Number of results per page. Defaults to 50.
     *
     * @param take the amount of entries to retrieve per page.
     * @return this filter instance.
     */
    public CheckpointPaginationFilter withTake(int take) {
        parameters.put("take", take);
        return this;
    }
}
