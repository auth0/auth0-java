package com.auth0.client.mgmt.filter;

/**
 * Class used to filter the results received when calling the Rules endpoint. Related to the {@link com.auth0.client.mgmt.RulesEntity()} entity.
 */
public class RulesFilter extends FieldsFilter {

    /**
     * Filter by enabled value
     *
     * @param enabled only retrieve items that are enabled or disabled.
     */
    public RulesFilter withEnabled(boolean enabled) {
        parameters.put("enabled", enabled);
        return this;
    }

    @Override
    public RulesFilter withFields(String fields, boolean includeFields) {
        super.withFields(fields, includeFields);
        return this;
    }
}
