package com.auth0.json.mgmt;

import java.util.List;

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
