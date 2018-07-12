package com.auth0.json.mgmt;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
class ConnectionsPageDeserializer extends PageDeserializer<ConnectionsPage, Connection> {

    ConnectionsPageDeserializer() {
        super(Connection.class, "connections");
    }

    @Override
    protected ConnectionsPage createPage(List<Connection> items) {
        return new ConnectionsPage(items);
    }

    @Override
    protected ConnectionsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<Connection> items) {
        return new ConnectionsPage(start, length, total, limit, items);
    }

}
