package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Represents the value of the {@code oidc_backchannel_logout} property on an Auth0 application.\
 * @see Client
 * @see com.auth0.client.mgmt.ClientsEntity
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OIDCBackchannelLogout {

    @JsonProperty("backchannel_logout_urls")
    private List<String> backchannelLogoutUrls;

    /**
     * Create a new instance with the given list of Logout URIs that will receive a {@code logout_token} when selected Back-Channel Logout Initiators occur.
     * @param backchannelLogoutUrls the list of allowed backchannel logout URLs.
     */
    public OIDCBackchannelLogout(@JsonProperty("backchannel_logout_urls") List<String> backchannelLogoutUrls) {
        this.backchannelLogoutUrls = backchannelLogoutUrls;
    }

    /**
     * @return the list of Logout URIs that will receive a {@code logout_token} when selected Back-Channel Logout Initiators occur.
     */
    public List<String> getBackchannelLogoutUrls() {
        return this.backchannelLogoutUrls;
    }
}
