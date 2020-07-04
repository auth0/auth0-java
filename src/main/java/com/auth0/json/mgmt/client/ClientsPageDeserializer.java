package com.auth0.json.mgmt.client;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

/**
 * Parses a given paged response into their {@link ClientsPage} representation.
 * <p>
 * This class is thread-safe.
 *
 * @see PageDeserializer
 * @see com.auth0.client.mgmt.ClientsEntity
 */
@SuppressWarnings({"unused", "WeakerAccess"})
class ClientsPageDeserializer extends PageDeserializer<ClientsPage, Client> {

    ClientsPageDeserializer() {
        super(Client.class, "clients");
    }

    @Override
    protected ClientsPage createPage(List<Client> items) {
        return new ClientsPage(items);
    }

    @Override
    protected ClientsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Client> items) {
        return new ClientsPage(start, length, total, limit, items);
    }

}
