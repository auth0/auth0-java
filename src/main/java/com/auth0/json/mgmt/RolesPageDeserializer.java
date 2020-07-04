package com.auth0.json.mgmt;

import java.util.List;

/**
 * Parses a given paged response into their {@link RolesPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see com.auth0.client.mgmt.RolesEntity
 */
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
