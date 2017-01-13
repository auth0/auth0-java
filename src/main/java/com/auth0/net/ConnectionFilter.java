package com.auth0.net;

import java.util.HashMap;
import java.util.Map;

public class ConnectionFilter {

    private Map<String, Object> filters;

    public ConnectionFilter() {
        filters = new HashMap<>();
    }

    /**
     * Filter by strategy name
     *
     * @param strategy only retrieve connections with this strategy.
     * @return this filter instance
     */
    public ConnectionFilter withStrategy(String strategy) {
        filters.put("strategy", strategy);
        return this;
    }

    /**
     * Filter by connection name
     *
     * @param name only retrieve the connection with this name.
     * @return this filter instance
     */
    public ConnectionFilter withName(String name) {
        filters.put("name", name);
        return this;
    }

    /**
     * Only retrieve certain fields.
     *
     * @param fields        a list of comma separated fields to retrieve from the connection. The following values are allowed: name,strategy,options,enabled_clients,id,provisioning_ticket_url.
     * @param includeFields whether to include or exclude in the response the fields that were given.
     * @return this filter instance
     */
    public ConnectionFilter withFields(String fields, boolean includeFields) {
        filters.put("fields", fields);
        filters.put("include_fields", includeFields);
        return this;
    }

    public Map<String, Object> getAsMap() {
        return filters;
    }
}
