package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.util.List;

/**
 * Class that represents a given page of Rules. Related to the {@link com.auth0.client.mgmt.RulesEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonDeserialize(using = RulesPageDeserializer.class)
public class RulesPage extends Page<Rule> {

    public RulesPage(List<Rule> items) {
        super(items);
    }

    public RulesPage(Integer start, Integer length, Integer total, Integer limit, List<Rule> items) {
        super(start, length, total, limit, items);
    }

}