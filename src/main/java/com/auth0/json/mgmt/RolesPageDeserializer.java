package com.auth0.json.mgmt;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
class RolesPageDeserializer extends PageDeserializer<RolesPage, Role> {

    RolesPageDeserializer() {
        super(Role.class, "roles");
    }

    @Override
    protected RolesPage createPage(List<Role> items) {
        return new RolesPage(items);
    }

    @Override
    protected RolesPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Role> items) {
        return new RolesPage(start, length, total, limit, items);
    }

}
