package com.auth0.net;

public class ConnectionFilter extends BaseFilter<ConnectionFilter> {

    /**
     * Filter by strategy
     *
     * @param strategy only retrieve items with this strategy.
     * @return this filter instance
     */
    public ConnectionFilter withStrategy(String strategy) {
        filters.put("strategy", strategy);
        return this;
    }

    /**
     * Filter by name
     *
     * @param name only retrieve the item with this name.
     * @return this filter instance
     */
    public ConnectionFilter withName(String name) {
        filters.put("name", name);
        return this;
    }

}
