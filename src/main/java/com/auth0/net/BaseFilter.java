package com.auth0.net;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseFilter<T extends BaseFilter> {
    protected Map<String, Object> parameters;

    protected BaseFilter() {
        parameters = new HashMap<>();
    }

    /**
     * Only retrieve certain fields from the item.
     *
     * @param fields        a list of comma separated fields to retrieve.
     * @param includeFields whether to include or exclude in the response the fields that were given.
     * @return this filter instance
     */
    public T withFields(String fields, boolean includeFields) {
        parameters.put("fields", fields);
        parameters.put("include_fields", includeFields);
        return (T) this;
    }

    public Map<String, Object> getAsMap() {
        return parameters;
    }
}
