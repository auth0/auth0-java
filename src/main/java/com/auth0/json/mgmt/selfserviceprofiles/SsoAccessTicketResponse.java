package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SsoAccessTicketResponse {
    @JsonProperty("ticket")
    private String ticket;

    /**
     * Creates a new instance.
     */
    public SsoAccessTicketResponse() {
    }

    /**
     * Creates a new instance with the given ticket.
     * @param ticket the ticket.
     */
    @JsonCreator
    public SsoAccessTicketResponse(@JsonProperty("ticket") String ticket) {
        this.ticket = ticket;
    }

    /**
     * Getter for the ticket.
     * @return the ticket.
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * Setter for the ticket.
     * @param ticket the ticket to set.
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
