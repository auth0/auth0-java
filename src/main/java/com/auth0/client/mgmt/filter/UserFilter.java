package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Users endpoint. Related to the {@link com.auth0.client.mgmt.UsersEntity()} entity.
 */
public class UserFilter extends QueryFilter<UserFilter> {

    /**
     * Creates a new instance using the search engine 'v2'.
     */
    public UserFilter() {
        parameters.put("search_engine", "v2");
    }
}
