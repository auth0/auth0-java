package com.auth0.json.mgmt;

import java.util.List;

/**
 * Parses a given paged response into their {@link RulesPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see com.auth0.client.mgmt.RulesEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class RulesPageDeserializer extends PageDeserializer<RulesPage, Rule> {

    RulesPageDeserializer() {
        super(Rule.class, "rules");
    }

    @Override
    protected RulesPage createPage(List<Rule> items) {
        return new RulesPage(items);
    }

    @Override
    protected RulesPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Rule> items) {
        return new RulesPage(start, length, total, limit, items);
    }

}
