package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Users endpoint. Related to the {@link com.auth0.client.mgmt.UsersEntity} entity.
 */
public class UserFilter extends QueryFilter {

    /**
     * Creates a new instance using the search engine 'v2'.
     * <p>
     * This version of the search engine is now deprecated and will stop working on November 13th 2018.
     * Please, migrate as soon as possible and use the {@link #withSearchEngine(String)} method to specify version 'v3'.
     * See the migration guide at https://auth0.com/docs/users/search/v3#migrate-from-search-engine-v2-to-v3
     */
    public UserFilter() {
        withSearchEngine("v2");
    }

    @Override
    public UserFilter withTotals(boolean includeTotals) {
        super.withTotals(includeTotals);
        return this;
    }

    /**
     * Selects which Search Engine version to use.
     * <p>
     * Version 2 is now deprecated and will stop working on November 13th 2018. Please, migrate as soon as possible to 'v3'.
     * See the migration guide at https://auth0.com/docs/users/search/v3#migrate-from-search-engine-v2-to-v3
     *
     * @param searchEngineVersion the search engine version to use on queries.
     * @return this filter instance
     */
    public UserFilter withSearchEngine(String searchEngineVersion) {
        parameters.put("search_engine", searchEngineVersion);
        return this;
    }

    /**
     * Filter by a query
     *
     * @param query the query expression to use following the syntax defined at https://auth0.com/docs/users/search/v3/query-syntax
     * @return this filter instance
     */
    @Override
    public UserFilter withQuery(String query) {
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
