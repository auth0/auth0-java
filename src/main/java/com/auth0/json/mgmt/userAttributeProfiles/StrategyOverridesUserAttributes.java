package com.auth0.json.mgmt.userAttributeProfiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StrategyOverridesUserAttributes {

    @JsonProperty("oidc_mapping")
    private OidcMapping oidcMapping;
    @JsonProperty("saml_mapping")
    private List<String> samlMapping;
    @JsonProperty("scim_mapping")
    private String scimMapping;

    /**
     * OIDC mapping.
     * @return the OIDC mapping
     */
    @JsonProperty("oidc_mapping")
    public OidcMapping getOidcMapping() {
        return oidcMapping;
    }

    /**
     * Sets the OIDC mapping.
     * @param oidcMapping the OIDC mapping
     */
    @JsonProperty("oidc_mapping")
    public void setOidcMapping(OidcMapping oidcMapping) {
        this.oidcMapping = oidcMapping;
    }

    /**
     * SAML mapping.
     * @return the SAML mapping
     */
    @JsonProperty("saml_mapping")
    public List<String> getSamlMapping() {
        return samlMapping;
    }

    /**
     * Sets the SAML mapping.
     * @param samlMapping the SAML mapping
     */
    @JsonProperty("saml_mapping")
    public void setSamlMapping(List<String> samlMapping) {
        this.samlMapping = samlMapping;
    }

    /**
     * SCIM mapping.
     * @return the SCIM mapping
     */
    @JsonProperty("scim_mapping")
    public String getScimMapping() {
        return scimMapping;
    }

    /**
     * Sets the SCIM mapping.
     * @param scimMapping the SCIM mapping
     */
    @JsonProperty("scim_mapping")
    public void setScimMapping(String scimMapping) {
        this.scimMapping = scimMapping;
    }
}
