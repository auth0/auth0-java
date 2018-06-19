package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a given page of Clients. Related to the {@link com.auth0.client.mgmt.ClientsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ClientGrantsPageDeserializer.class)
public class ClientGrantsPage extends Page<ClientGrant> {

    public ClientGrantsPage(List<ClientGrant> items) {
        super(items);
    }

    public ClientGrantsPage(Integer start, Integer length, Integer total, Integer limit, List<ClientGrant> items) {
        super(start, length, total, limit, items);
    }

}