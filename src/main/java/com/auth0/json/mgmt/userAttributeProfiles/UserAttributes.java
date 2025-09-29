package com.auth0.json.mgmt.userAttributeProfiles;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAttributes {
    @JsonProperty("description")
    private String description;
    @JsonProperty("label")
    private String label;
    @JsonProperty("profile_required")
    private boolean profileRequired;
    @JsonProperty("auth0_mapping")
    private String auth0Mapping;
    @JsonProperty("oidc_mapping")
    private OidcMapping oidcMapping;
    @JsonProperty("saml_mapping")
    private List<String> samlMapping;
    @JsonProperty("scim_mapping")
    private String scimMapping;
    @JsonProperty("strategy_overrides")
    private Map<String, StrategyOverridesUserAttributes> strategyOverrides;

    /**
     * Description of the user attribute.
     * @return the description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the user attribute.
     * @param description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Label of the user attribute.
     * @return the label
     */
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * Sets the label of the user attribute.
     * @param label the label
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Indicates if the user attribute is required in the profile.
     * @return true if the user attribute is required in the profile, false otherwise
     */
    @JsonProperty("profile_required")
    public boolean isProfileRequired() {
        return profileRequired;
    }

    /**
     * Sets if the user attribute is required in the profile.
     * @param profileRequired true if the user attribute is required in the profile, false otherwise
     */
    @JsonProperty("profile_required")
    public void setProfileRequired(boolean profileRequired) {
        this.profileRequired = profileRequired;
    }

    /**
     * Auth0 mapping.
     * @return the Auth0 mapping
     */
    @JsonProperty("auth0_mapping")
    public String getAuth0Mapping() {
        return auth0Mapping;
    }

    /**
     * Sets the Auth0 mapping.
     * @param auth0Mapping the Auth0 mapping
     */
    @JsonProperty("auth0_mapping")
    public void setAuth0Mapping(String auth0Mapping) {
        this.auth0Mapping = auth0Mapping;
    }

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

    /**
     * Strategy overrides mapping.
     * @return the strategy overrides mapping
     */
    @JsonProperty("strategy_overrides")
    public Map<String, StrategyOverridesUserAttributes> getStrategyOverrides() {
        return strategyOverrides;
    }

    /**
     * Sets the strategy overrides mapping.
     * @param strategyOverrides the strategy overrides mapping
     */
    @JsonProperty("strategy_overrides")
    public void setStrategyOverrides(Map<String, StrategyOverridesUserAttributes> strategyOverrides) {
        this.strategyOverrides = strategyOverrides;
    }
}

