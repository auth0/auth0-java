package com.auth0.json.mgmt;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResourceServer {
    @JsonProperty
    private String id;
    @JsonProperty
    private String name;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("scopes")
    private List<Scope> scopes;
    @JsonProperty("signing_alg")
    private String signingAlg;
    @JsonProperty("signing_secret")
    private String signingSecret;
    @JsonProperty("allow_offline_access")
    private Boolean allowOfflineAccess;
    @JsonProperty("skip_consent_for_verifiable_first_party_clients")
    private Boolean skipConsentForVerifiableFirstPartyClients;
    @JsonProperty("token_lifetime")
    private Integer tokenLifetime;
    @JsonProperty("verificationKey")
    private String verificationKey;
    @JsonProperty("verificationLocation")
    private String verificationLocation;
    @JsonProperty("is_system")
    private Boolean isSystem;

    public ResourceServer(String identifier) {
        this.identifier = identifier;
    }

    @JsonCreator
    public ResourceServer(@JsonProperty("id") String id,
                          @JsonProperty("name") String name,
                          @JsonProperty("identifier") String identifier,
                          @JsonProperty("scopes") List<Scope> scopes,
                          @JsonProperty("signing_alg") String signingAlg,
                          @JsonProperty("signing_secret") String signingSecret,
                          @JsonProperty("allow_offline_access") Boolean allowOfflineAccess,
                          @JsonProperty("skip_consent_for_verifiable_first_party_clients")
                                  Boolean skipConsentForVerifiableFirstPartyClients,
                          @JsonProperty("token_lifetime") Integer tokenLifetime,
                          @JsonProperty("verificationKey") String verificationKey,
                          @JsonProperty("verificationLocation") String verificationLocation,
                          @JsonProperty("is_system") Boolean isSystem) {
        this.id = id;
        this.name = name;
        this.identifier = identifier;
        this.scopes = scopes;
        this.signingAlg = signingAlg;
        this.signingSecret = signingSecret;
        this.allowOfflineAccess = allowOfflineAccess;
        this.skipConsentForVerifiableFirstPartyClients = skipConsentForVerifiableFirstPartyClients;
        this.tokenLifetime = tokenLifetime;
        this.verificationKey = verificationKey;
        this.verificationLocation = verificationLocation;
        this.isSystem = isSystem;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsSystem() {
        return isSystem;
    }

    public void setIsSystem(Boolean system) {
        isSystem = system;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public List<Scope> getScopes() {
        return scopes;
    }

    public void setScopes(List<Scope> scopes) {
        this.scopes = scopes;
    }

    public String getSigningAlg() {
        return signingAlg;
    }

    public void setSigningAlg(String signingAlg) {
        this.signingAlg = signingAlg;
    }

    public String getSigningSecret() {
        return signingSecret;
    }

    public void setSigningSecret(String signingSecret) {
        this.signingSecret = signingSecret;
    }

    public Boolean getAllowOfflineAccess() {
        return allowOfflineAccess;
    }

    public void setAllowOfflineAccess(Boolean allowOfflineAccess) {
        this.allowOfflineAccess = allowOfflineAccess;
    }

    public Boolean getSkipConsentForVerifiableFirstPartyClients() {
        return skipConsentForVerifiableFirstPartyClients;
    }

    public void setSkipConsentForVerifiableFirstPartyClients(Boolean skipConsentForVerifiableFirstPartyClients) {
        this.skipConsentForVerifiableFirstPartyClients = skipConsentForVerifiableFirstPartyClients;
    }

    public Integer getTokenLifetime() {
        return tokenLifetime;
    }

    public void setTokenLifetime(Integer tokenLifetime) {
        this.tokenLifetime = tokenLifetime;
    }

    public String getVerificationKey() {
        return verificationKey;
    }

    public void setVerificationKey(String verificationKey) {
        this.verificationKey = verificationKey;
    }

    public String getVerificationLocation() {
        return verificationLocation;
    }

    public void setVerificationLocation(String verificationLocation) {
        this.verificationLocation = verificationLocation;
    }
}
