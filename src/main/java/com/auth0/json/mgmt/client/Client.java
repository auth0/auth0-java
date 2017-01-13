package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    @JsonProperty("name")
    private String name;
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("client_secret")
    private String clientSecret;
    @JsonProperty("app_type")
    private String appType;
    @JsonProperty("logo_uri")
    private String logoUri;
    @JsonProperty("is_first_party")
    private Boolean isFirstParty;
    @JsonProperty("oidc_conformant")
    private Boolean oidcConformant;
    @JsonProperty("callbacks")
    private List<String> callbacks;
    @JsonProperty("allowed_origins")
    private List<String> allowedOrigins;
    @JsonProperty("client_aliases")
    private List<String> clientAliases;
    @JsonProperty("allowed_clients")
    private List<String> allowedClients;
    @JsonProperty("allowed_logout_urls")
    private List<String> allowedLogoutUrls;
    @JsonProperty("jwt_configuration")
    private JwtConfiguration jwtConfiguration;
    @JsonProperty("signing_keys")
    private List<SigningKey> signingKeys;
    @JsonProperty("encryption_key")
    private EncryptionKey encryptionKey;
    @JsonProperty("sso")
    private Boolean sso;
    @JsonProperty("sso_disabled")
    private Boolean ssoDisabled;
    @JsonProperty("custom_login_page_on")
    private Boolean customLoginPageOn;
    @JsonProperty("is_heroku_app")
    private Boolean isHerokuApp;
    @JsonProperty("custom_login_page")
    private String customLoginPage;
    @JsonProperty("custom_login_page_preview")
    private String customLoginPagePreview;
    @JsonProperty("form_template")
    private String formTemplate;
    @JsonProperty("addons")
    private Addons addons;
    @JsonProperty("token_endpoint_auth_method")
    private String tokenEndpointAuthMethod;
    @JsonProperty("resource_servers")
    private List<ResourceServer> resourceServers;
    @JsonProperty("client_metadata")
    private Map<String, Object> clientMetadata;
    @JsonProperty("mobile")
    private Mobile mobile;

    @JsonCreator
    public Client(@JsonProperty("name") String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getLogoUri() {
        return logoUri;
    }

    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    public Boolean getFirstParty() {
        return isFirstParty;
    }

    public Boolean getOidcConformant() {
        return oidcConformant;
    }

    public void setOidcConformant(Boolean oidcConformant) {
        this.oidcConformant = oidcConformant;
    }

    public List<String> getCallbacks() {
        return callbacks;
    }

    public void setCallbacks(List<String> callbacks) {
        this.callbacks = callbacks;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    public List<String> getClientAliases() {
        return clientAliases;
    }

    public void setClientAliases(List<String> clientAliases) {
        this.clientAliases = clientAliases;
    }

    public List<String> getAllowedClients() {
        return allowedClients;
    }

    public void setAllowedClients(List<String> allowedClients) {
        this.allowedClients = allowedClients;
    }

    public List<String> getAllowedLogoutUrls() {
        return allowedLogoutUrls;
    }

    public void setAllowedLogoutUrls(List<String> allowedLogoutUrls) {
        this.allowedLogoutUrls = allowedLogoutUrls;
    }

    public JwtConfiguration getJwtConfiguration() {
        return jwtConfiguration;
    }

    public void setJwtConfiguration(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    public List<SigningKey> getSigningKeys() {
        return signingKeys;
    }

    public EncryptionKey getEncryptionKey() {
        return encryptionKey;
    }

    public void setEncryptionKey(EncryptionKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    public Boolean getSso() {
        return sso;
    }

    public void setSso(Boolean sso) {
        this.sso = sso;
    }

    public Boolean getSsoDisabled() {
        return ssoDisabled;
    }

    public void setSsoDisabled(Boolean ssoDisabled) {
        this.ssoDisabled = ssoDisabled;
    }

    public Boolean getCustomLoginPageOn() {
        return customLoginPageOn;
    }

    public void setCustomLoginPageOn(Boolean customLoginPageOn) {
        this.customLoginPageOn = customLoginPageOn;
    }

    public Boolean getHerokuApp() {
        return isHerokuApp;
    }

    public String getCustomLoginPage() {
        return customLoginPage;
    }

    public void setCustomLoginPage(String customLoginPage) {
        this.customLoginPage = customLoginPage;
    }

    public String getCustomLoginPagePreview() {
        return customLoginPagePreview;
    }

    public void setCustomLoginPagePreview(String customLoginPagePreview) {
        this.customLoginPagePreview = customLoginPagePreview;
    }

    public String getFormTemplate() {
        return formTemplate;
    }

    public void setFormTemplate(String formTemplate) {
        this.formTemplate = formTemplate;
    }

    public Addons getAddons() {
        return addons;
    }

    public void setAddons(Addons addons) {
        this.addons = addons;
    }

    public String getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    public void setTokenEndpointAuthMethod(String tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
    }

    public List<ResourceServer> getResourceServers() {
        return resourceServers;
    }

    public void setResourceServers(List<ResourceServer> resourceServers) {
        this.resourceServers = resourceServers;
    }

    public Map<String, Object> getClientMetadata() {
        return clientMetadata;
    }

    public void setClientMetadata(Map<String, Object> clientMetadata) {
        this.clientMetadata = clientMetadata;
    }

    public Mobile getMobile() {
        return mobile;
    }

    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }
}

