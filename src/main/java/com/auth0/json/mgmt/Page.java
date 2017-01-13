package com.auth0.json.mgmt;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public abstract class Page<T> {

    @JsonProperty("start")
    private Integer start;
    @JsonProperty("length")
    private Integer length;
    @JsonProperty("total")
    private Integer total;
    @JsonProperty("limit")
    private Integer limit;
    private List<T> items;

    public Page(List<T> items) {
        this.items = items;
    }

    public Page(Integer start, Integer length, Integer total, Integer limit, List<T> items) {
        this.start = start;
        this.length = length;
        this.total = total;
        this.limit = limit;
        this.items = items;
    }

    public Integer getStart() {
        return start;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getTotal() {
        return total;
    }

    public Integer getLimit() {
        return limit;
    }

    public List<T> getItems() {
        return items;
    }
}