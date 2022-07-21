package com.auth0.json.mgmt.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Class that represents an Auth0 Application object. Related to the {@link com.auth0.client.mgmt.ClientsEntity} entity.
 */
@SuppressWarnings({"WeakerAccess", "unused"})
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Client {

    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
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
    @JsonProperty("web_origins")
    private List<String> webOrigins;
    @JsonProperty("grant_types")
    private List<String> grantTypes;
    @JsonProperty("client_aliases")
    private List<String> clientAliases;
    @JsonProperty("allowed_clients")
    private List<String> allowedClients;
    @JsonProperty("allowed_logout_urls")
    private List<String> allowedLogoutUrls;
    @JsonProperty("jwt_configuration")
    private JWTConfiguration jwtConfiguration;
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
    @JsonProperty("initiate_login_uri")
    private String initiateLoginUri;
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
    @JsonProperty("client_metadata")
    private Map<String, Object> clientMetadata;
    @JsonProperty("mobile")
    private Mobile mobile;
    @JsonProperty("refresh_token")
    private RefreshToken refreshToken;
    @JsonProperty("organization_usage")
    private String organizationUsage;
    @JsonProperty("organization_require_behavior")
    private String organizationRequireBehavior;
    @JsonProperty("tenant")
    private String tenant;
    @JsonProperty("global")
    private Boolean global;
    @JsonProperty("cross_origin_auth")
    private Boolean crossOriginAuth;
    @JsonProperty("cross_origin_loc")
    private String crossOriginLoc;

    /**
     * Getter for the name of the tenant this client belongs to.
     * @return the tenant name
     */
    @JsonProperty("tenant")
    public String getTenant() {
        return tenant;
    }

    /**
     * Setter for the name of the tenant this client belongs to.
     * @param tenant the name of the tenant
     */
    @JsonProperty("tenant")
    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    /**
     * Setter whether this is a global 'All Applications' client representing legacy tenant settings (true) or a regular client (false).
     * @param global whether legacy tenant or regular client
     */
    @JsonProperty("global")
    public void setGlobal(Boolean global) {
        this.global = global;
    }

    /**
     * Whether this is a global 'All Applications' client representing legacy tenant settings (true) or a regular client (false).
     * @return client representing legacy tenant settings (true) or a regular client (false).
     */
    @JsonProperty("global")
    public Boolean getGlobal() {
        return global;
    }

    /**
     * Creates a new Application instance setting the name property.
     *
     * @param name of the application.
     */
    @JsonCreator
    public Client(@JsonProperty("name") String name) {
        this.name = name;
    }

    /**
     * Getter for the name of the application.
     *
     * @return the name.
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * Setter for the application name.
     *
     * @param name the name to use.
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the description of the application.
     *
     * @return the description.
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * Setter for the description of the application.
     *
     * @param description the description to use.
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Getter for the application's client id.
     *
     * @return the application's client id.
     */
    @JsonProperty("client_id")
    public String getClientId() {
        return clientId;
    }

    /**
     * Getter for the application's client secret.
     *
     * @return the application's client secret.
     */
    @JsonProperty("client_secret")
    public String getClientSecret() {
        return clientSecret;
    }

    /**
     * Setter for the application's client secret. If no secret is provided, it will be generated by the Auth0 Server upon Application creation.
     *
     * @param clientSecret the secret to use.
     */
    @JsonProperty("client_secret")
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    /**
     * Getter for the type that this application represents.
     *
     * @return the application's type.
     */
    @JsonProperty("app_type")
    public String getAppType() {
        return appType;
    }

    /**
     * Setter for the type that this application represents.
     *
     * @param appType the application type to set.
     */
    @JsonProperty("app_type")
    public void setAppType(String appType) {
        this.appType = appType;
    }

    /**
     * Getter for the URI of the application logo.
     *
     * @return the application's logo URI.
     */
    @JsonProperty("logo_uri")
    public String getLogoUri() {
        return logoUri;
    }

    /**
     * Setter for the application logo URI. An image with size 150x150 is recommended.
     *
     * @param logoUri the logo URI to set.
     */
    @JsonProperty("logo_uri")
    public void setLogoUri(String logoUri) {
        this.logoUri = logoUri;
    }

    /**
     * Whether this application is a first party client or not.
     *
     * @return true if the application is first party, false otherwise.
     */
    @JsonProperty("is_first_party")
    public Boolean isFirstParty() {
        return isFirstParty;
    }

    /**
     * Setter for whether this application is a first party client or not.
     *
     * @param isFirstParty whether the application is a first party client or not.
     */
    @JsonProperty("is_first_party")
    public void setIsFirstParty(Boolean isFirstParty) {
        this.isFirstParty = isFirstParty;
    }

    /**
     * Whether this application will conform to strict Open ID Connect specifications or not.
     *
     * @return true if the application will conform to strict OIDC specifications, false otherwise.
     */
    @JsonProperty("oidc_conformant")
    public Boolean isOIDCConformant() {
        return oidcConformant;
    }

    /**
     * Setter for the strict conform to the Open ID Connect specifications.
     *
     * @param oidcConformant whether the application will conform to strict OIDC specifications or not.
     */
    @JsonProperty("oidc_conformant")
    public void setOIDCConformant(Boolean oidcConformant) {
        this.oidcConformant = oidcConformant;
    }

    /**
     * Getter for the list of allowed callback urls for the application.
     *
     * @return the list of callback urls.
     */
    @JsonProperty("callbacks")
    public List<String> getCallbacks() {
        return callbacks;
    }

    /**
     * Setter for the list of allowed callback urls for the application.
     *
     * @param callbacks the allowed callback urls to set.
     */
    @JsonProperty("callbacks")
    public void setCallbacks(List<String> callbacks) {
        this.callbacks = callbacks;
    }

    /**
     * Getter for the list of allowed origins for the application.
     *
     * @return the list of allowed origins.
     */
    @JsonProperty("allowed_origins")
    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    /**
     * Setter for the list of allowed origins for the application.
     *
     * @param allowedOrigins the allowed callback urls to set.
     */
    @JsonProperty("allowed_origins")
    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }

    /**
     * Setter for the list of web origins for the application.
     *
     * @param webOrigins the web origins to set.
     */
    @JsonProperty("web_origins")
    public void setWebOrigins(List<String> webOrigins) {
        this.webOrigins = webOrigins;
    }

    /**
     * Getter for the list of web origins for the application.
     *
     * @return the list of web origins.
     */
    @JsonProperty("web_origins")
    public List<String> getWebOrigins() {
        return webOrigins;
    }

    /**
     * Setter for the list of grant types for the application.
     * See allowed values at https://auth0.com/docs/applications/application-grant-types.
     *
     * @param grantTypes the list of grant types to set.
     */
    @JsonProperty("grant_types")
    public void setGrantTypes(List<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    /**
     * Getter for the list of grant types for the application.
     *
     * @return the list of grant types.
     */
    @JsonProperty("grant_types")
    public List<String> getGrantTypes() {
        return grantTypes;
    }

    /**
     * Getter for the list of application aliases.
     *
     * @return the list of application aliases.
     */
    @JsonProperty("client_aliases")
    public List<String> getClientAliases() {
        return clientAliases;
    }

    /**
     * Setter for the list of application aliases.
     *
     * @param clientAliases the application aliases to set.
     */
    @JsonProperty("client_aliases")
    public void setClientAliases(List<String> clientAliases) {
        this.clientAliases = clientAliases;
    }

    /**
     * Getter for the list of applications that will be allowed to make a delegation request.
     *
     * @return the list of allowed applications.
     */
    @JsonProperty("allowed_clients")
    public List<String> getAllowedClients() {
        return allowedClients;
    }

    /**
     * Setter for the list of applications that will be allowed to make a delegation request.
     *
     * @param allowedClients the list of allowed application.
     */
    @JsonProperty("allowed_clients")
    public void setAllowedClients(List<String> allowedClients) {
        this.allowedClients = allowedClients;
    }

    /**
     * Getter for the application list of URLs that are valid to redirect to after logout from Auth0.
     *
     * @return the list of logout urls.
     */
    @JsonProperty("allowed_logout_urls")
    public List<String> getAllowedLogoutUrls() {
        return allowedLogoutUrls;
    }

    /**
     * Setter for the application list of URLs that are valid to redirect to after logout from Auth0.
     *
     * @param allowedLogoutUrls the allowed logout urls to set.
     */
    @JsonProperty("allowed_logout_urls")
    public void setAllowedLogoutUrls(List<String> allowedLogoutUrls) {
        this.allowedLogoutUrls = allowedLogoutUrls;
    }

    /**
     * Getter for the JWT configuration object.
     *
     * @return the JWT Configuration.
     */
    @JsonProperty("jwt_configuration")
    public JWTConfiguration getJWTConfiguration() {
        return jwtConfiguration;
    }

    /**
     * Setter for the JWT configuration object.
     *
     * @param jwtConfiguration the JWT configuration to set.
     */
    @JsonProperty("jwt_configuration")
    public void setJWTConfiguration(JWTConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    /**
     * Getter for the application signing keys.
     *
     * @return the application signing keys.
     */
    @JsonProperty("signing_keys")
    public List<SigningKey> getSigningKeys() {
        return signingKeys;
    }

    /**
     * Getter for the encryption Key.
     *
     * @return the encryption key.
     */
    @JsonProperty("encryption_key")
    public EncryptionKey getEncryptionKey() {
        return encryptionKey;
    }

    /**
     * Setter for the encryption Key.
     *
     * @param encryptionKey the encryption key.
     */
    @JsonProperty("encryption_key")
    public void setEncryptionKey(EncryptionKey encryptionKey) {
        this.encryptionKey = encryptionKey;
    }

    /**
     * Whether to use Auth0 instead of the Identity Provider to do Single Sign On or not.
     *
     * @return true if this application will use Auth0 for SSO instead of the Identity Provider or not.
     */
    @JsonProperty("sso")
    public Boolean useAuth0SSO() {
        return sso;
    }

    /**
     * Sets if Auth0 will do Single Sign On instead of the Identity Provider.
     *
     * @param useAuth0SSO whether to use Auth0 instead of the Identity Provider to do Single Sign On or not.
     */
    @JsonProperty("sso")
    public void setUseAuth0SSO(Boolean useAuth0SSO) {
        this.sso = useAuth0SSO;
    }

    /**
     * Whether Single Sign On is disabled or not for this application.
     *
     * @return true is SSO is disabled for this application, false otherwise.
     */
    @JsonProperty("sso_disabled")
    public Boolean isSSODisabled() {
        return ssoDisabled;
    }

    /**
     * Sets if Single Sign On is disabled for this application or not.
     *
     * @param ssoDisabled whether SSO is disabled for this application or not.
     */
    @JsonProperty("sso_disabled")
    public void setSSODisabled(Boolean ssoDisabled) {
        this.ssoDisabled = ssoDisabled;
    }

    /**
     * Whether to use a custom login page or the default one.
     *
     * @return true if this application uses a custom login page, false otherwise.
     */
    @JsonProperty("custom_login_page_on")
    public Boolean useCustomLoginPage() {
        return customLoginPageOn;
    }

    /**
     * Sets if this application should use a custom login page or the default one.
     *
     * @param useCustomLoginPage true if this application uses a custom login page, false otherwise.
     */
    @JsonProperty("custom_login_page_on")
    public void setUseCustomLoginPage(Boolean useCustomLoginPage) {
        this.customLoginPageOn = useCustomLoginPage;
    }

    /**
     * Getter for the initiate login URI.
     *
     * @return the initiate login URI.
     */
    @JsonProperty("initiate_login_uri")
    public String getInitiateLoginUri() {
        return initiateLoginUri;
    }

    /**
     * Setter for the initiate login URI.
     *
     * @param initiateLoginUri the initiate login URI to set.
     */
    @JsonProperty("initiate_login_uri")
    public void setInitiateLoginUri(String initiateLoginUri) {
        this.initiateLoginUri = initiateLoginUri;
    }

    /**
     * Whether this application is a Heroku application or not.
     *
     * @return true if this application is a Heroku application, false otherwise.
     */
    @JsonProperty("is_heroku_app")
    public Boolean isHerokuApp() {
        return isHerokuApp;
    }

    /**
     * Getter for the custom login page HTML code.
     *
     * @return the custom login page HTML code.
     */
    @JsonProperty("custom_login_page")
    public String getCustomLoginPage() {
        return customLoginPage;
    }

    /**
     * Setter for the custom login page HTML code.
     *
     * @param customLoginPage the custom login page HTML code.
     */
    @JsonProperty("custom_login_page")
    public void setCustomLoginPage(String customLoginPage) {
        this.customLoginPage = customLoginPage;
    }

    /**
     * Getter for the custom login page preview HTML code.
     *
     * @return the custom login page preview HTML code.
     */
    @JsonProperty("custom_login_page_preview")
    public String getCustomLoginPagePreview() {
        return customLoginPagePreview;
    }

    /**
     * Setter for the custom login page preview HTML code.
     *
     * @param customLoginPagePreview the custom login page preview HTML code.
     */
    @JsonProperty("custom_login_page_preview")
    public void setCustomLoginPagePreview(String customLoginPagePreview) {
        this.customLoginPagePreview = customLoginPagePreview;
    }

    /**
     * Getter for the WS federation form template.
     *
     * @return the form template
     */
    @JsonProperty("form_template")
    public String getFormTemplate() {
        return formTemplate;
    }

    /**
     * Setter for the WS federation form template.
     *
     * @param formTemplate the form template to set.
     */
    @JsonProperty("form_template")
    public void setFormTemplate(String formTemplate) {
        this.formTemplate = formTemplate;
    }

    /**
     * Getter for the addons or plugins associated with an application in Auth0.
     *
     * @return the addons for this application.
     */
    @JsonProperty("addons")
    public Addons getAddons() {
        return addons;
    }

    /**
     * Setter for the addons or plugins associated with an application in Auth0.
     *
     * @param addons the addons to set for this application.
     */
    @JsonProperty("addons")
    public void setAddons(Addons addons) {
        this.addons = addons;
    }

    /**
     * Getter for the requested authentication method for the token endpoint.
     *
     * @return the requested authentication method.
     */
    @JsonProperty("token_endpoint_auth_method")
    public String getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    /**
     * Setter for the requested authentication method for the token endpoint. Possible values are 'none' (public application without a client secret), 'client_secret_post' (application uses HTTP POST parameters) or 'client_secret_basic' (application uses HTTP Basic).
     *
     * @param authMethod the authentication method to set.
     */
    @JsonProperty("token_endpoint_auth_method")
    public void setTokenEndpointAuthMethod(String authMethod) {
        this.tokenEndpointAuthMethod = authMethod;
    }

    /**
     * Getter for the metadata associated with the application.
     *
     * @return the application metadata.
     */
    @JsonProperty("client_metadata")
    public Map<String, Object> getClientMetadata() {
        return clientMetadata;
    }

    /**
     * Setter for the metadata associated with the application, in the form of an object with string values (max 255 chars).  Maximum of 10 metadata properties allowed.
     *
     * @param clientMetadata the application metadata to set.
     */
    @JsonProperty("client_metadata")
    public void setClientMetadata(Map<String, Object> clientMetadata) {
        this.clientMetadata = clientMetadata;
    }

    /**
     * Getter for the configuration related to native mobile apps.
     *
     * @return the mobile configuration.
     */
    @JsonProperty("mobile")
    public Mobile getMobile() {
        return mobile;
    }

    /**
     * Setter for the configuration related to native mobile apps.
     *
     * @param mobile the mobile configuration to set.
     */
    @JsonProperty("mobile")
    public void setMobile(Mobile mobile) {
        this.mobile = mobile;
    }

    /**
     * Getter for the configuration related to refresh tokens.
     *
     * @return the refresh token configuration.
     */
    public RefreshToken getRefreshToken() {
        return refreshToken;
    }

    /**
     * Setter for the configuration related to refresh tokens.
     *
     * @param refreshToken the refresh token configuration to set.
     */
    public void setRefreshToken(RefreshToken refreshToken) {
        this.refreshToken = refreshToken;
    }

    /**
     * @return the organization usage value
     */
    public String getOrganizationUsage() {
        return organizationUsage;
    }

    /**
     * Sets the value of the organization_usage field
     *
     * @param organizationUsage the organization_usage value
     */
    public void setOrganizationUsage(String organizationUsage) {
        this.organizationUsage = organizationUsage;
    }

    /**
     * @return the organization require behavior value
     */
    public String getOrganizationRequireBehavior() {
        return organizationRequireBehavior;
    }

    /**
     * Sets the value of the organization_require_behavior field
     * @param organizationRequireBehavior the organization_require_behavior value
     */
    public void setOrganizationRequireBehavior(String organizationRequireBehavior) {
        this.organizationRequireBehavior = organizationRequireBehavior;
    }


    /**
     * Setter whether this client can be used to make cross-origin authentication requests (true) or it is not allowed to make such requests (false).
     * @param crossOriginAuth whether an application can make cross-origin authentication requests or not
     */
    @JsonProperty("cross_origin_auth")
    public void setCrossOriginAuth(Boolean crossOriginAuth) {
        this.crossOriginAuth = crossOriginAuth;
    }

    /**
     * Whether this client can be used to make cross-origin authentication requests (true) or it is not allowed to make such requests (false).
     * @return true if application can make cross-origin authentication requests, false otherwise
     */
    @JsonProperty("cross_origin_auth")
    public Boolean getCrossOriginAuth() {
        return crossOriginAuth;
    }

    /**
     * URL of the location in your site where the cross-origin verification takes place for the cross-origin auth flow when performing Auth in your own domain instead of Auth0 hosted login page.
     * @param crossOriginLoc url location for cross-origin verification
     */
    @JsonProperty("cross_origin_loc")
    public void setCrossOriginLoc(String crossOriginLoc) {
        this.crossOriginLoc = crossOriginLoc;
    }

    /**
     * Getter for the URL of the location in your site where the cross-origin verification takes place for the cross-origin auth flow when performing Auth in your own domain instead of Auth0 hosted login page.
     * @return URL of the location in your site where the cross-origin verification takes place
     */
    @JsonProperty("cross_origin_loc")
    public String getCrossOriginLoc() {
        return crossOriginLoc;
    }
}

