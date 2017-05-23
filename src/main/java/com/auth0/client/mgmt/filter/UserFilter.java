package com.auth0.client.mgmt.filter;

import java.io.UnsupportedEncodingException;

/**
 * Class used to filter the results received when calling the Users endpoint. Related to the {@link com.auth0.client.mgmt.UsersEntity} entity.
 */
public class UserFilter extends QueryFilter {

    /**
     * Creates a new instance using the search engine 'v2'.
     */
    public UserFilter() {
        parameters.put("search_engine", "v2");
    }

    @Override
    public UserFilter withTotals(boolean includeTotals) {
        super.withTotals(includeTotals);
        return this;
    }

    @Override
    public UserFilter withQuery(String query) throws UnsupportedEncodingException {
        super.withQuery(query);
        return this;
    }

    @Override
    public UserFilter withSort(String sort) {
        super.withSort(sort);
        return this;
    }

    @Override
    public UserFilter withPage(int pageNumber, int amountPerPage) {
        super.withPage(pageNumber, amountPerPage);
        return this;
    }

    @Override
    public UserFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }
}
