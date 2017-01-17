package com.auth0.net;

public class UserFilter extends QueryFilter<UserFilter> {

    public UserFilter() {
        parameters.put("search_engine", "v2");
    }
}
