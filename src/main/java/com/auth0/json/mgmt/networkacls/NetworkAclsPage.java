package com.auth0.json.mgmt.networkacls;

import com.auth0.json.mgmt.Page;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = NetworkAclsPageDeserializer.class)
public class NetworkAclsPage extends Page<NetworkAcls> {

    public NetworkAclsPage(List<NetworkAcls> items) {
        super(items);
    }

    public NetworkAclsPage(Integer start, Integer length, Integer total, Integer limit, List<NetworkAcls> items) {
        super(start, length, total, limit, items);
    }
}
