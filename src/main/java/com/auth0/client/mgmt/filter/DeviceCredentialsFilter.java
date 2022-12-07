package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Device Credentials endpoint. Related to the {@link com.auth0.client.mgmt.DeviceCredentialsEntity} entity.
 * <p>
 * This class is not thread-safe.
 *
 * @see BaseFilter
 */
public class DeviceCredentialsFilter extends FieldsFilter {

    /**
     * Filter by user id
     *
     * @param userId only retrieve items with this user id.
     * @return this filter instance.
     */
    public DeviceCredentialsFilter withUserId(String userId) {
        parameters.put("user_id", userId);
        return this;
    }

    /**
     * Filter by application's client id
     *
     * @param clientId only retrieve items with this client id.
     * @return this filter instance.
     */
    public DeviceCredentialsFilter withClientId(String clientId) {
        parameters.put("client_id", clientId);
        return this;
    }

    /**
     * Filter by type
     *
     * @param type only retrieve items with this type.
     * @return this filter instance.
     */
    public DeviceCredentialsFilter withType(String type) {
        parameters.put("type", type);
        return this;
    }

    @Override
    public DeviceCredentialsFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }

    /**
     * Filter by page
     *
     * @param pageNumber    the page number to retrieve.
     * @param amountPerPage the amount of items per page to retrieve.
     * @return this filter instance
     */
    public DeviceCredentialsFilter withPage(int pageNumber, int amountPerPage) {
        parameters.put("page", pageNumber);
        parameters.put("per_page", amountPerPage);
        return this;
    }

    /**
     * Include the query summary
     *
     * @param includeTotals whether to include or not the query summary.
     * @return this filter instance
     */
    public DeviceCredentialsFilter withTotals(boolean includeTotals) {
        parameters.put("include_totals", includeTotals);
        return this;
    }
}
