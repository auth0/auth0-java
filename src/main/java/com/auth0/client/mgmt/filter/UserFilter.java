package com.auth0.client.mgmt.filter;

public class UserFilter extends QueryFilter<UserFilter> {

    public UserFilter() {
        parameters.put("search_engine", "v2");
    }
}
