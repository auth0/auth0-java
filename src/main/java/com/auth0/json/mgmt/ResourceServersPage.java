package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a given page of Resource Servers. Related to the {@link com.auth0.client.mgmt.ResourceServerEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = ResourceServersPageDeserializer.class)
public class ResourceServersPage extends Page<ResourceServer> {

    public ResourceServersPage(List<ResourceServer> items) {
        super(items);
    }

    public ResourceServersPage(Integer start, Integer length, Integer total, Integer limit, List<ResourceServer> items) {
        super(start, length, total, limit, items);
    }

}