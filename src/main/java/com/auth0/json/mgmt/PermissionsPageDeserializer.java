package com.auth0.json.mgmt;

import java.util.List;

/**
 * Parses a given paged response into their {@link PermissionsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see com.auth0.client.mgmt.RolesEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class PermissionsPageDeserializer extends PageDeserializer<PermissionsPage, Permission> {

    PermissionsPageDeserializer() {
        super(Permission.class, "permissions");
    }

    @Override
    protected PermissionsPage createPage(List<Permission> items) {
        return new PermissionsPage(items);
    }

    @Override
    protected PermissionsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Permission> items) {
        return new PermissionsPage(start, length, total, limit, items);
    }

}
