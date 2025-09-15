package com.auth0.json.mgmt.networkacls;

import com.auth0.json.mgmt.PageDeserializer;

import java.util.List;

public class NetworkAclsPageDeserializer extends PageDeserializer<NetworkAclsPage, NetworkAcls> {
    protected NetworkAclsPageDeserializer() {
        super(NetworkAcls.class, "network_acls");
    }

    @Override
    protected NetworkAclsPage createPage(List<NetworkAcls> items) {
        return new NetworkAclsPage(items);
    }

    @Override
    protected NetworkAclsPage createPage(Integer start, Integer length, Integer total, Integer limit, List<NetworkAcls> items) {
        return new NetworkAclsPage(start, length, total, limit, items);
    }
}
