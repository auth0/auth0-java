package com.auth0.json.mgmt.client;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

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
