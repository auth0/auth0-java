package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Clients endpoint. Related to the {@link com.auth0.client.mgmt.ClientsEntity} entity.
 */
public class ClientFilter extends FieldsFilter {

    /**
     * Include the query summary
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public ClientFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public ClientFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("per_page", amountPerPage);
        return this;
    }

    /**
     * Filter by global clients
     *
     * @param isGlobal whether the client should or not be global
     * @return this filter instance
     */
    public ClientFilter withIsGlobal(boolean isGlobal) {
        parameters.put("is_global", isGlobal);
        return this;
    }

    /**
     * Filter by first party clients
     *
     * @param isFirstParty whether the client should or not be first party
     * @return this filter instance
     */
    public ClientFilter withIsFirstParty(boolean isFirstParty) {
        parameters.put("is_first_party", isFirstParty);
        return this;
    }

    /**
     * Filter by application type
     *
     * @param appType A comma separated list of application types used to filter the returned clients (native, spa, regular_web, non_interactive)
     * @return this filter instance
     */
    public ClientFilter withAppType(String appType) {
        parameters.put("app_type", appType);
        return this;
    }

    @Override
    public ClientFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }
}
