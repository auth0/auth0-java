package com.auth0.json.mgmt;

import java.util.List;

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
