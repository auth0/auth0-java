package com.auth0.client.mgmt.filter;

public class EnabledClientsFilter extends BaseFilter {
    /**
     * Include the {@code from} parameter to specify where to start the page selection. Only applicable for endpoints that
     * support checkpoint pagination.
     *
     * @param from the ID from which to start selection. This can be obtained from the {@code next} field returned from
     *             a checkpoint-paginated result.
     * @return this filter instance.
     */
    public EnabledClientsFilter withFrom(String from) {
        parameters.put("from", from);
        return this;
    }

    /**
     * Include the {@code take} parameter to specify the amount of results to return per page. Only applicable for endpoints that
     * support checkpoint pagination.
     *
     * @param take the amount of entries to retrieve per page.
     * @return this filter instance.
     */
    public EnabledClientsFilter withTake(int take) {
        parameters.put("take", take);
        return this;
    }
}
