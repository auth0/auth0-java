package com.auth0.json.mgmt;

import java.util.List;

@SuppressWarnings({"unused", "WeakerAccess"})
class ResourceServersPageDeserializer extends PageDeserializer<ResourceServersPage, ResourceServer> {

    ResourceServersPageDeserializer() {
        super(ResourceServer.class, "resource_servers");
    }

    @Override
    protected ResourceServersPage createPage(List<ResourceServer> items) {
        return new ResourceServersPage(items);
    }

    @Override
    protected ResourceServersPage createPage(Integer start, Integer length, Integer total, Integer limit, List<ResourceServer> items) {
        return new ResourceServersPage(start, length, total, limit, items);
    }

}
