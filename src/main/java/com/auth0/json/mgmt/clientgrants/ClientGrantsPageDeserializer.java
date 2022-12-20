package com.auth0.json.mgmt.clientgrants;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a given paged response into their {@link ClientGrantsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see com.auth0.client.mgmt.ClientGrantsEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class ClientGrantsPageDeserializer extends PageDeserializer<ClientGrantsPage, ClientGrant> {

    ClientGrantsPageDeserializer() {
        super(ClientGrant.class, "client_grants");
    }

    @Override
    protected ClientGrantsPage createPage(List<ClientGrant> items) {
        return new ClientGrantsPage(items);
    }

    @Override
    protected ClientGrantsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<ClientGrant> items) {
        return new ClientGrantsPage(start, length, total, limit, items);
    }

}
