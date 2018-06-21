package com.auth0.json.mgmt;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
class GrantsPageDeserializer extends PageDeserializer<GrantsPage, Grant> {

    GrantsPageDeserializer() {
        super(Grant.class, "grants");
    }

    @Override
    protected GrantsPage createPage(List<Grant> items) {
        return new GrantsPage(items);
    }

    @Override
    protected GrantsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Grant> items) {
        return new GrantsPage(start, length, total, limit, items);
    }

}
