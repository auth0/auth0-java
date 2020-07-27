package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling an Auth0 endpoint.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class FieldsFilter extends BaseFilter {

    /**
     * Only retrieve certain fields from the item.
     *
     * @param fields        a list of comma separated fields to retrieve.
     * @param includeFields whether to include or exclude in the response the fields that were given.
     * @return this filter instance
     */
    public FieldsFilter withFields(String fields, boolean includeFields) {
        parameters.put("fields", fields);
        parameters.put("include_fields", includeFields);
        return this;
    }
}
