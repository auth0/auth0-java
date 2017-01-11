package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_ABSENT)
public class Client {

    private String name;
    private String clientId;
    private String clientSecret;
    private String appType;
    private String logoUri;
    private Boolean isFirstParty;
    private Boolean oidcConformant;
    private List<String> callbacks;
    private List<String> allowedOrigins;
    private List<String> clientAliases;
    private List<String> allowedClients;
    private List<String> allowedLogoutUrls;
    private JwtConfiguration jwtConfiguration;
    private List<SigningKey> signingKeys;
    private EncryptionKey encryptionKey;
    private Boolean sso;
    private Boolean ssoDisabled;
    private Boolean customLoginPageOn;
    private Boolean isHerokuApp;
    private String customLoginPage;
    private String customLoginPagePreview;
    private String formTemplate;
    private Map<String, Object> addons;
    private String tokenEndpointAuthMethod;
    private List<ResourceServer> resourceServers;
    private Map<String, Object> clientMetadata;
    private Mobile mobile;

    @JsonCreator
    public Client(@JsonProperty("name") String name) {
        this.name = name;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty(value = "client_id", access = JsonProperty.Access.WRITE_ONLY)
    public String getClientId() {
        return clientId;
    }

    @JsonProperty(value = "client_id", access = JsonProperty.Access.WRITE_ONLY)
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @JsonProperty("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }

    @JsonProperty("client_secret")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @JsonProperty("app_type")
    public String getAppType() {
        return appType;
    }

    @JsonProperty("app_type")
    public void setAppType(String appType) {
        this.appType = appType;
    }

    @JsonProperty("logo_uri")
    public String getLogoUri() {
        return logoUri;
    }

    @JsonProperty("logo_uri")
    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    @JsonProperty(value = "is_first_party", access = JsonProperty.Access.WRITE_ONLY)
    public Boolean getFirstParty() {
        return isFirstParty;
    }

    @JsonProperty(value = "is_first_party", access = JsonProperty.Access.WRITE_ONLY)
    public void setFirstParty(Boolean firstParty) {
        isFirstParty = firstParty;
    }

    @JsonProperty("oidc_conformant")
    public Boolean getOidcConformant() {
        return oidcConformant;
    }

    @JsonProperty("oidc_conformant")
    public void setOidcConformant(Boolean oidcConformant) {
        this.oidcConformant = oidcConformant;
    }

    @JsonProperty("callbacks")
    public List<String> getCallbacks() {
        return callbacks;
    }

    @JsonProperty("callbacks")
    public void setCallbacks(List<String> callbacks) {
        this.callbacks = callbacks;
    }

    @JsonProperty("allowed_origins")
    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    @JsonProperty("allowed_origins")
    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    @JsonProperty("client_aliases")
    public List<String> getClientAliases() {
        return clientAliases;
    }

    @JsonProperty("client_aliases")
    public void setClientAliases(List<String> clientAliases) {
        this.clientAliases = clientAliases;
    }

    @JsonProperty("allowed_clients")
    public List<String> getAllowedClients() {
        return allowedClients;
    }

    @JsonProperty("allowed_clients")
    public void setAllowedClients(List<String> allowedClients) {
        this.allowedClients = allowedClients;
    }

    @JsonProperty("allowed_logout_urls")
    public List<String> getAllowedLogoutUrls() {
        return allowedLogoutUrls;
    }

    @JsonProperty("allowed_logout_urls")
    public void setAllowedLogoutUrls(List<String> allowedLogoutUrls) {
        this.allowedLogoutUrls = allowedLogoutUrls;
    }

    @JsonProperty("jwt_configuration")
    public JwtConfiguration getJwtConfiguration() {
        return jwtConfiguration;
    }

    @JsonProperty("jwt_configuration")
    public void setJwtConfiguration(JwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @JsonProperty(value = "signing_keys", access = JsonProperty.Access.WRITE_ONLY)
    public List<SigningKey> getSigningKeys() {
        return signingKeys;
    }

    @JsonProperty(value = "signing_keys", access = JsonProperty.Access.WRITE_ONLY)
    public void setSigningKeys(List<SigningKey> signingKeys) {
        this.signingKeys = signingKeys;
    }

    @JsonProperty("encryption_key")
    public EncryptionKey getEncryptionKey() {
        return encryptionKey;
    }

    @JsonProperty("encryption_key")
    public void setEncryptionKey(EncryptionKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    @JsonProperty("sso")
    public Boolean getSso() {
        return sso;
    }

    @JsonProperty("sso")
    public void setSso(Boolean sso) {
        this.sso = sso;
    }

    @JsonProperty("sso_disabled")
    public Boolean getSsoDisabled() {
        return ssoDisabled;
    }

    @JsonProperty("sso_disabled")
    public void setSsoDisabled(Boolean ssoDisabled) {
        this.ssoDisabled = ssoDisabled;
    }

    @JsonProperty("custom_login_page_on")
    public Boolean getCustomLoginPageOn() {
        return customLoginPageOn;
    }

    @JsonProperty("custom_login_page_on")
    public void setCustomLoginPageOn(Boolean customLoginPageOn) {
        this.customLoginPageOn = customLoginPageOn;
    }

    @JsonProperty(value = "is_heroku_app", access = JsonProperty.Access.WRITE_ONLY)
    public Boolean getHerokuApp() {
        return isHerokuApp;
    }

    @JsonProperty(value = "is_heroku_app", access = JsonProperty.Access.WRITE_ONLY)
    public void setHerokuApp(Boolean herokuApp) {
        isHerokuApp = herokuApp;
    }

    @JsonProperty("custom_login_page")
    public String getCustomLoginPage() {
        return customLoginPage;
    }

    @JsonProperty("custom_login_page")
    public void setCustomLoginPage(String customLoginPage) {
        this.customLoginPage = customLoginPage;
    }

    @JsonProperty("custom_login_page_preview")
    public String getCustomLoginPagePreview() {
        return customLoginPagePreview;
    }

    @JsonProperty("custom_login_page_preview")
    public void setCustomLoginPagePreview(String customLoginPagePreview) {
        this.customLoginPagePreview = customLoginPagePreview;
    }

    @JsonProperty("form_template")
    public String getFormTemplate() {
        return formTemplate;
    }

    @JsonProperty("form_template")
    public void setFormTemplate(String formTemplate) {
        this.formTemplate = formTemplate;
    }

    @JsonProperty("addons")
    public Map<String, Object> getAddons() {
        return addons;
    }

    @JsonProperty("addons")
    public void setAddons(Map<String, Object> addons) {
        this.addons = addons;
    }

    @JsonProperty("token_endpoint_auth_method")
    public String getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    @JsonProperty("token_endpoint_auth_method")
    public void setTokenEndpointAuthMethod(String tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
    }

    @JsonProperty("resource_servers")
    public List<ResourceServer> getResourceServers() {
        return resourceServers;
    }

    @JsonProperty("resource_servers")
    public void setResourceServers(List<ResourceServer> resourceServers) {
        this.resourceServers = resourceServers;
    }

    @JsonProperty("client_metadata")
    public Map<String, Object> getClientMetadata() {
        return clientMetadata;
    }

    @JsonProperty("client_metadata")
    public void setClientMetadata(Map<String, Object> clientMetadata) {
        this.clientMetadata = clientMetadata;
    }

    @JsonProperty("mobile")
    public Mobile getMobile() {
        return mobile;
    }

    @JsonProperty("mobile")
    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }
}

