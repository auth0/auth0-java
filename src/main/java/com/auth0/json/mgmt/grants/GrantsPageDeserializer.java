package com.auth0.json.mgmt.grants;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a given paged response into their {@link GrantsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see com.auth0.client.mgmt.GrantsEntity
 */
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
