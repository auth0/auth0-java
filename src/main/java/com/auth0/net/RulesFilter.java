package com.auth0.net;

public class RulesFilter extends BaseFilter<RulesFilter> {

    /**
     * Filter by enabled value
     *
     * @param enabled only retrieve items that are enabled or disabled.
     */
    public RulesFilter withEnabled(boolean enabled) {
        parameters.put("enabled", enabled);
        return this;
    }

}
