package com.auth0.json.mgmt;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@SuppressWarnings({"WeakerAccess", "unused"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceServer {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("scopes")
    private List<Scope> scopes;
    @JsonProperty("signing_alg")
    private String signingAlgorithm;
    @JsonProperty("signing_secret")
    private String signingSecret;
    @JsonProperty("allow_offline_access")
    private Boolean allowOfflineAccess;
    @JsonProperty("skip_consent_for_verifiable_first_party_clients")
    private Boolean skipConsentForVerifiableFirstPartyClients;
    @JsonProperty("token_lifetime")
    private Integer tokenLifetime;
    @JsonProperty("token_lifetime_for_web")
    private Integer tokenLifetimeForWeb;
    @JsonProperty("verification_location")
    private String verificationLocation;
    @JsonProperty("is_system")
    private Boolean isSystem;

    @JsonCreator
    public ResourceServer(@JsonProperty("identifier") String identifier) {
        this.identifier = identifier;
    }

    public ResourceServer() {
    }

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("is_system")
    public Boolean isSystem() {
        return isSystem;
    }

    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    @JsonProperty("scopes")
    public List<Scope> getScopes() {
        return scopes;
    }

    @JsonProperty("scopes")
    public void setScopes(List<Scope> scopes) {
        this.scopes = scopes;
    }

    @JsonProperty("signing_alg")
    public String getSigningAlgorithm() {
        return signingAlgorithm;
    }

    @JsonProperty("signing_alg")
    public void setSigningAlgorithm(String signingAlgorithm) {
        this.signingAlgorithm = signingAlgorithm;
    }

    @JsonProperty("signing_secret")
    public String getSigningSecret() {
        return signingSecret;
    }

    @JsonProperty("signing_secret")
    public void setSigningSecret(String signingSecret) {
        this.signingSecret = signingSecret;
    }

    @JsonProperty("allow_offline_access")
    public Boolean getAllowOfflineAccess() {
        return allowOfflineAccess;
    }

    @JsonProperty("allow_offline_access")
    public void setAllowOfflineAccess(Boolean allowOfflineAccess) {
        this.allowOfflineAccess = allowOfflineAccess;
    }

    @JsonProperty("skip_consent_for_verifiable_first_party_clients")
    public Boolean getSkipConsentForVerifiableFirstPartyClients() {
        return skipConsentForVerifiableFirstPartyClients;
    }

    @JsonProperty("skip_consent_for_verifiable_first_party_clients")
    public void setSkipConsentForVerifiableFirstPartyClients(Boolean skipConsentForVerifiableFirstPartyClients) {
        this.skipConsentForVerifiableFirstPartyClients = skipConsentForVerifiableFirstPartyClients;
    }

    @JsonProperty("token_lifetime")
    public Integer getTokenLifetime() {
        return tokenLifetime;
    }

    @JsonProperty("token_lifetime")
    public void setTokenLifetime(Integer tokenLifetime) {
        this.tokenLifetime = tokenLifetime;
    }

    @JsonProperty("verification_location")
    public String getVerificationLocation() {
        return verificationLocation;
    }

    @JsonProperty("verification_location")
    public void setVerificationLocation(String verificationLocation) {
        this.verificationLocation = verificationLocation;
    }

    @JsonProperty("token_lifetime_for_web")
    public Integer getTokenLifetimeForWeb() {
        return tokenLifetimeForWeb;
    }

    @JsonProperty("token_lifetime_for_web")
    public void setTokenLifetimeForWeb(Integer tokenLifetimeForWeb) {
        this.tokenLifetimeForWeb = tokenLifetimeForWeb;
    }

}
