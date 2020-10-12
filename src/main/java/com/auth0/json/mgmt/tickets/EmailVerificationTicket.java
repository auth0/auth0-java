
package com.auth0.json.mgmt.tickets;

import com.auth0.json.mgmt.EmailVerificationIdentity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Class that represents an Auth0 Email Verification Ticket object. Related to the {@link com.auth0.client.mgmt.TicketsEntity} entity.
 */
@SuppressWarnings({"unused", "WeakerAccess", "FieldCanBeLocal"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EmailVerificationTicket {

    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("result_url")
    private String resultUrl;
    @JsonProperty("ttl_sec")
    private Integer ttlSec;
    @JsonProperty("ticket")
    private String ticket;
    @JsonProperty("includeEmailInRedirect")
    private Boolean includeEmailInRedirect;
    @JsonProperty("identity")
    private EmailVerificationIdentity identity;

    @JsonCreator
    public EmailVerificationTicket(@JsonProperty("user_id") String userId) {
        this.userId = userId;
    }

    /**
     * Setter for the id of the user this ticket is meant to.
     *
     * @param userId the user id to set.
     */
    @JsonProperty("user_id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * Setter for the url the user will be redirected to after using the ticket.
     *
     * @param resultUrl the result url.
     */
    @JsonProperty("result_url")
    public void setResultUrl(String resultUrl) {
        this.resultUrl = resultUrl;
    }

    /**
     * The ticket's lifetime in seconds starting from the moment of creation. After expiration the ticket can not be used to verify the users's email. If not specified or if you send 0 the Auth0 default lifetime will be applied.
     *
     * @param seconds the lifetime in seconds to set.
     */
    @JsonProperty("ttl_sec")
    public void setTTLSeconds(Integer seconds) {
        this.ttlSec = seconds;
    }

    /**
     * Whether to include the email address as part of the returnUrl in the reset_email (true), or not (false).
     *
     * @param includeEmailInRedirect whether to include the email address as part of the returnUrl in the reset email.
     */
    public void setIncludeEmailInRedirect(Boolean includeEmailInRedirect) {
        this.includeEmailInRedirect = includeEmailInRedirect;
    }

    /**
     * Sets the identity. Needed to verify primary identities when using social, enterprise, or passwordless connections.
     * It is also required to verify secondary identities.
     *
     * @param identity the identity.
     */
    @JsonProperty("identity")
    public void setIdentity(EmailVerificationIdentity identity) {
        this.identity = identity;
    }

    /**
     * Getter for the ticket url.
     *
     * @return the ticket url.
     */
    @JsonProperty("ticket")
    public String getTicket() {
        return ticket;
    }
}
