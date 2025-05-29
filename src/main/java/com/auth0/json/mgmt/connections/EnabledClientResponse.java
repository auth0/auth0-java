package com.auth0.json.mgmt.connections;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EnabledClientResponse {
    @JsonProperty("clients")
    private List<Clients> clients;
    @JsonProperty("next")
    private String next;

    /**
     * Getter for the list of clients.
     *
     * @return the list of clients.
     */
    public List<Clients> getClients() {
        return clients;
    }

    /**
     * Setter for the list of clients.
     *
     * @param clients the list of clients to set.
     */
    public void setClients(List<Clients> clients) {
        this.clients = clients;
    }

    /**
     * Getter for the next page URL.
     *
     * @return the next page URL.
     */
    public String getNext() {
        return next;
    }

    /**
     * Setter for the next page URL.
     *
     * @param next the next page URL to set.
     */
    public void setNext(String next) {
        this.next = next;
    }
}
