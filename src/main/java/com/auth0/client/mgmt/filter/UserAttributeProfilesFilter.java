package com.auth0.client.mgmt.filter;

public class UserAttributeProfilesFilter extends BaseFilter {

    /**
     * Filter by checkpoint pagination support
     *
     * @param from the starting index identifier
     * @param take the number of items to retrieve
     * @return this filter instance
     */
    public UserAttributeProfilesFilter withCheckpointPagination(String from, int take) {
        parameters.put("from", from);
        parameters.put("take", take);
        return this;
    }
}
