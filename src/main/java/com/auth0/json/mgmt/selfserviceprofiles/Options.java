package com.auth0.json.mgmt.selfserviceprofiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Options {
    @JsonProperty("icon_url")
    private String iconUrl;
    @JsonProperty("domain_aliases")
    private List<String> domainAliases;
    @JsonProperty("idpinitiated")
    private Idpinitiated idpinitiated;

    /**
     * Getter for the icon URL.
     * @return the icon URL.
     */
    public String getIconUrl() {
        return iconUrl;
    }

    /**
     * Setter for the icon URL.
     * @param iconUrl the icon URL to set.
     */
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    /**
     * Getter for the domain aliases.
     * @return the domain aliases.
     */
    public List<String> getDomainAliases() {
        return domainAliases;
    }

    /**
     * Setter for the domain aliases.
     * @param domainAliases the domain aliases to set.
     */
    public void setDomainAliases(List<String> domainAliases) {
        this.domainAliases = domainAliases;
    }

    /**
     * Getter for the Idpinitiated.
     * @return the Idpinitiated.
     */
    public Idpinitiated getIdpinitiated() {
        return idpinitiated;
    }

    /**
     * Setter for the Idpinitiated.
     * @param idpinitiated the Idpinitiated to set.
     */
    public void setIdpinitiated(Idpinitiated idpinitiated) {
        this.idpinitiated = idpinitiated;
    }
}
