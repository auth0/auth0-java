# Reference
## Actions
<details><summary><code>client.actions.list() -> SyncPagingIterable&lt;Action&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all actions.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().list(
    ListActionsRequestParameters
        .builder()
        .triggerId(
            OptionalNullable.of("triggerId")
        )
        .actionName(
            OptionalNullable.of("actionName")
        )
        .deployed(
            OptionalNullable.of(true)
        )
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .installed(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**triggerId:** `Optional<String>` — An actions extensibility point.
    
</dd>
</dl>

<dl>
<dd>

**actionName:** `Optional<String>` — The name of the action to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**deployed:** `Optional<Boolean>` — Optional filter to only retrieve actions that are deployed.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Use this field to request a specific page of the list results.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — The maximum number of results to be returned by the server in single response. 20 by default
    
</dd>
</dl>

<dl>
<dd>

**installed:** `Optional<Boolean>` — Optional. When true, return only installed actions. When false, return only custom actions. Returns all actions by default.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.create(request) -> CreateActionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create an action. Once an action is created, it must be deployed, and then bound to a trigger before it will be executed as part of a flow.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().create(
    CreateActionRequestContent
        .builder()
        .name("name")
        .supportedTriggers(
            Arrays.asList(
                ActionTrigger
                    .builder()
                    .id("id")
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — The name of an action.
    
</dd>
</dl>

<dl>
<dd>

**supportedTriggers:** `List<ActionTrigger>` — The list of triggers that this action supports. At this time, an action can only target a single trigger at a time.
    
</dd>
</dl>

<dl>
<dd>

**code:** `Optional<String>` — The source code of the action.
    
</dd>
</dl>

<dl>
<dd>

**dependencies:** `Optional<List<ActionVersionDependency>>` — The list of third party npm modules, and their versions, that this action depends on.
    
</dd>
</dl>

<dl>
<dd>

**runtime:** `Optional<String>` — The Node runtime. For example: `node22`, defaults to `node22`
    
</dd>
</dl>

<dl>
<dd>

**secrets:** `Optional<List<ActionSecretRequest>>` — The list of secrets that are included in an action or a version of an action.
    
</dd>
</dl>

<dl>
<dd>

**modules:** `Optional<List<ActionModuleReference>>` — The list of action modules and their versions used by this action.
    
</dd>
</dl>

<dl>
<dd>

**deploy:** `Optional<Boolean>` — True if the action should be deployed after creation.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.get(id) -> GetActionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve an action by its ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the action to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Deletes an action and all of its associated versions. An action must be unbound from all triggers before it can be deleted.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().delete(
    "id",
    DeleteActionRequestParameters
        .builder()
        .force(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the action to delete.
    
</dd>
</dl>

<dl>
<dd>

**force:** `Optional<Boolean>` — Force action deletion detaching bindings
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.update(id, request) -> UpdateActionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update an existing action. If this action is currently bound to a trigger, updating it will <strong>not</strong> affect any user flows until the action is deployed.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().update(
    "id",
    UpdateActionRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the action to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — The name of an action.
    
</dd>
</dl>

<dl>
<dd>

**supportedTriggers:** `Optional<List<ActionTrigger>>` — The list of triggers that this action supports. At this time, an action can only target a single trigger at a time.
    
</dd>
</dl>

<dl>
<dd>

**code:** `Optional<String>` — The source code of the action.
    
</dd>
</dl>

<dl>
<dd>

**dependencies:** `Optional<List<ActionVersionDependency>>` — The list of third party npm modules, and their versions, that this action depends on.
    
</dd>
</dl>

<dl>
<dd>

**runtime:** `Optional<String>` — The Node runtime. For example: `node22`, defaults to `node22`
    
</dd>
</dl>

<dl>
<dd>

**secrets:** `Optional<List<ActionSecretRequest>>` — The list of secrets that are included in an action or a version of an action.
    
</dd>
</dl>

<dl>
<dd>

**modules:** `Optional<List<ActionModuleReference>>` — The list of action modules and their versions used by this action.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.deploy(id) -> DeployActionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Deploy an action. Deploying an action will create a new immutable version of the action. If the action is currently bound to a trigger, then the system will begin executing the newly deployed version of the action immediately. Otherwise, the action will only be executed as a part of a flow once it is bound to that flow.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().deploy("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of an action.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.test(id, request) -> TestActionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Test an action. After updating an action, it can be tested prior to being deployed to ensure it behaves as expected.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().test(
    "id",
    TestActionRequestContent
        .builder()
        .payload(
            new HashMap<String, Object>() {{
                put("key", "value");
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the action to test.
    
</dd>
</dl>

<dl>
<dd>

**payload:** `Map<String, Object>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Branding
<details><summary><code>client.branding.get() -> GetBrandingResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve branding settings.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.update(request) -> UpdateBrandingResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update branding settings.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().update(
    UpdateBrandingRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**colors:** `Optional<UpdateBrandingColors>` 
    
</dd>
</dl>

<dl>
<dd>

**faviconUrl:** `Optional<String>` — URL for the favicon. Must use HTTPS.
    
</dd>
</dl>

<dl>
<dd>

**logoUrl:** `Optional<String>` — URL for the logo. Must use HTTPS.
    
</dd>
</dl>

<dl>
<dd>

**font:** `Optional<UpdateBrandingFont>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## ClientGrants
<details><summary><code>client.clientGrants.list() -> SyncPagingIterable&lt;ClientGrantResponseContent&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a list of <a href="https://auth0.com/docs/get-started/applications/application-access-to-apis-client-grants">client grants</a>, including the scopes associated with the application/API pair.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clientGrants().list(
    ListClientGrantsRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .audience(
            OptionalNullable.of("audience")
        )
        .clientId(
            OptionalNullable.of("client_id")
        )
        .allowAnyOrganization(
            OptionalNullable.of(true)
        )
        .subjectType(
            OptionalNullable.of(ClientGrantSubjectTypeEnum.CLIENT)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**audience:** `Optional<String>` — Optional filter on audience.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — Optional filter on client_id.
    
</dd>
</dl>

<dl>
<dd>

**allowAnyOrganization:** `Optional<Boolean>` — Optional filter on allow_any_organization.
    
</dd>
</dl>

<dl>
<dd>

**subjectType:** `Optional<ClientGrantSubjectTypeEnum>` — The type of application access the client grant allows. Use of this field is subject to the applicable Free Trial terms in Okta’s <a href="https://www.okta.com/legal/"> Master Subscription Agreement.</a>
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clientGrants.create(request) -> CreateClientGrantResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a client grant for a machine-to-machine login flow. To learn more, read <a href="https://www.auth0.com/docs/get-started/authentication-and-authorization-flow/client-credentials-flow">Client Credential Flow</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clientGrants().create(
    CreateClientGrantRequestContent
        .builder()
        .clientId("client_id")
        .audience("audience")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**clientId:** `String` — ID of the client.
    
</dd>
</dl>

<dl>
<dd>

**audience:** `String` — The audience (API identifier) of this client grant
    
</dd>
</dl>

<dl>
<dd>

**organizationUsage:** `Optional<ClientGrantOrganizationUsageEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**allowAnyOrganization:** `Optional<Boolean>` — If enabled, any organization can be used with this grant. If disabled (default), the grant must be explicitly assigned to the desired organizations.
    
</dd>
</dl>

<dl>
<dd>

**scope:** `Optional<List<String>>` — Scopes allowed for this client grant.
    
</dd>
</dl>

<dl>
<dd>

**subjectType:** `Optional<ClientGrantSubjectTypeEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**authorizationDetailsTypes:** `Optional<List<String>>` — Types of authorization_details allowed for this client grant. Use of this field is subject to the applicable Free Trial terms in Okta’s <a href= "https://www.okta.com/legal/"> Master Subscription Agreement.</a>
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clientGrants.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete the <a href="https://www.auth0.com/docs/get-started/authentication-and-authorization-flow/client-credentials-flow">Client Credential Flow</a> from your machine-to-machine application.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clientGrants().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the client grant to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clientGrants.update(id, request) -> UpdateClientGrantResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a client grant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clientGrants().update(
    "id",
    UpdateClientGrantRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the client grant to update.
    
</dd>
</dl>

<dl>
<dd>

**scope:** `Optional<List<String>>` — Scopes allowed for this client grant.
    
</dd>
</dl>

<dl>
<dd>

**organizationUsage:** `Optional<ClientGrantOrganizationNullableUsageEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**allowAnyOrganization:** `Optional<Boolean>` — Controls allowing any organization to be used with this grant
    
</dd>
</dl>

<dl>
<dd>

**authorizationDetailsTypes:** `Optional<List<String>>` — Types of authorization_details allowed for this client grant. Use of this field is subject to the applicable Free Trial terms in Okta’s <a href= "https://www.okta.com/legal/"> Master Subscription Agreement.</a>
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Clients
<details><summary><code>client.clients.list() -> SyncPagingIterable&lt;Client&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve clients (applications and SSO integrations) matching provided filters. A list of fields to include or exclude may also be specified.
For more information, read <a href="https://www.auth0.com/docs/get-started/applications"> Applications in Auth0</a> and <a href="https://www.auth0.com/docs/authenticate/single-sign-on"> Single Sign-On</a>.

<ul>
  <li>
    The following can be retrieved with any scope:
    <code>client_id</code>, <code>app_type</code>, <code>name</code>, and <code>description</code>.
  </li>
  <li>
    The following properties can only be retrieved with the <code>read:clients</code> or
    <code>read:client_keys</code> scope:
    <code>callbacks</code>, <code>oidc_logout</code>, <code>allowed_origins</code>,
    <code>web_origins</code>, <code>tenant</code>, <code>global</code>, <code>config_route</code>,
    <code>callback_url_template</code>, <code>jwt_configuration</code>,
    <code>jwt_configuration.lifetime_in_seconds</code>, <code>jwt_configuration.secret_encoded</code>,
    <code>jwt_configuration.scopes</code>, <code>jwt_configuration.alg</code>, <code>api_type</code>,
    <code>logo_uri</code>, <code>allowed_clients</code>, <code>owners</code>, <code>custom_login_page</code>,
    <code>custom_login_page_off</code>, <code>sso</code>, <code>addons</code>, <code>form_template</code>,
    <code>custom_login_page_codeview</code>, <code>resource_servers</code>, <code>client_metadata</code>,
    <code>mobile</code>, <code>mobile.android</code>, <code>mobile.ios</code>, <code>allowed_logout_urls</code>,
    <code>token_endpoint_auth_method</code>, <code>is_first_party</code>, <code>oidc_conformant</code>,
    <code>is_token_endpoint_ip_header_trusted</code>, <code>initiate_login_uri</code>, <code>grant_types</code>,
    <code>refresh_token</code>, <code>refresh_token.rotation_type</code>, <code>refresh_token.expiration_type</code>,
    <code>refresh_token.leeway</code>, <code>refresh_token.token_lifetime</code>, <code>refresh_token.policies</code>, <code>organization_usage</code>,
    <code>organization_require_behavior</code>.
  </li>
  <li>
    The following properties can only be retrieved with the
    <code>read:client_keys</code> or <code>read:client_credentials</code> scope:
    <code>encryption_key</code>, <code>encryption_key.pub</code>, <code>encryption_key.cert</code>,
    <code>client_secret</code>, <code>client_authentication_methods</code> and <code>signing_key</code>.
  </li>
</ul>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().list(
    ListClientsRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .isGlobal(
            OptionalNullable.of(true)
        )
        .isFirstParty(
            OptionalNullable.of(true)
        )
        .appType(
            OptionalNullable.of("app_type")
        )
        .q(
            OptionalNullable.of("q")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Default value is 50, maximum value is 100
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**isGlobal:** `Optional<Boolean>` — Optional filter on the global client parameter.
    
</dd>
</dl>

<dl>
<dd>

**isFirstParty:** `Optional<Boolean>` — Optional filter on whether or not a client is a first-party client.
    
</dd>
</dl>

<dl>
<dd>

**appType:** `Optional<String>` — Optional filter by a comma-separated list of application types.
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Advanced Query in <a href="http://www.lucenetutorial.com/lucene-query-syntax.html">Lucene</a> syntax.<br /><b>Permitted Queries</b>:<br /><ul><li><i>client_grant.organization_id:{organization_id}</i></li><li><i>client_grant.allow_any_organization:true</i></li></ul><b>Additional Restrictions</b>:<br /><ul><li>Cannot be used in combination with other filters</li><li>Requires use of the <i>from</i> and <i>take</i> paging parameters (checkpoint paginatinon)</li><li>Reduced rate limits apply. See <a href="https://auth0.com/docs/troubleshoot/customer-support/operational-policies/rate-limit-policy/rate-limit-configurations/enterprise-public">Rate Limit Configurations</a></li></ul><i><b>Note</b>: Recent updates may not be immediately reflected in query results</i>
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.create(request) -> CreateClientResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new client (application or SSO integration). For more information, read <a href="https://www.auth0.com/docs/get-started/auth0-overview/create-applications">Create Applications</a>
<a href="https://www.auth0.com/docs/authenticate/single-sign-on/api-endpoints-for-single-sign-on>">API Endpoints for Single Sign-On</a>. 

Notes: 
- We recommend leaving the `client_secret` parameter unspecified to allow the generation of a safe secret.
- The <code>client_authentication_methods</code> and <code>token_endpoint_auth_method</code> properties are mutually exclusive. Use 
<code>client_authentication_methods</code> to configure the client with Private Key JWT authentication method. Otherwise, use <code>token_endpoint_auth_method</code>
to configure the client with client secret (basic or post) or with no authentication method (none).
- When using <code>client_authentication_methods</code> to configure the client with Private Key JWT authentication method, specify fully defined credentials. 
These credentials will be automatically enabled for Private Key JWT authentication on the client. 
- To configure <code>client_authentication_methods</code>, the <code>create:client_credentials</code> scope is required.
- To configure <code>client_authentication_methods</code>, the property <code>jwt_configuration.alg</code> must be set to RS256.

<div class="alert alert-warning">SSO Integrations created via this endpoint will accept login requests and share user profile information.</div>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().create(
    CreateClientRequestContent
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — Name of this client (min length: 1 character, does not allow `<` or `>`).
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` — Free text description of this client (max length: 140 characters).
    
</dd>
</dl>

<dl>
<dd>

**logoUri:** `Optional<String>` — URL of the logo to display for this client. Recommended size is 150x150 pixels.
    
</dd>
</dl>

<dl>
<dd>

**callbacks:** `Optional<List<String>>` — Comma-separated list of URLs whitelisted for Auth0 to use as a callback to the client after authentication.
    
</dd>
</dl>

<dl>
<dd>

**oidcLogout:** `Optional<ClientOidcBackchannelLogoutSettings>` 
    
</dd>
</dl>

<dl>
<dd>

**oidcBackchannelLogout:** `Optional<ClientOidcBackchannelLogoutSettings>` 
    
</dd>
</dl>

<dl>
<dd>

**sessionTransfer:** `Optional<ClientSessionTransferConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**allowedOrigins:** `Optional<List<String>>` — Comma-separated list of URLs allowed to make requests from JavaScript to Auth0 API (typically used with CORS). By default, all your callback URLs will be allowed. This field allows you to enter other origins if necessary. You can also use wildcards at the subdomain level (e.g., https://*.contoso.com). Query strings and hash information are not taken into account when validating these URLs.
    
</dd>
</dl>

<dl>
<dd>

**webOrigins:** `Optional<List<String>>` — Comma-separated list of allowed origins for use with <a href='https://auth0.com/docs/cross-origin-authentication'>Cross-Origin Authentication</a>, <a href='https://auth0.com/docs/flows/concepts/device-auth'>Device Flow</a>, and <a href='https://auth0.com/docs/protocols/oauth2#how-response-mode-works'>web message response mode</a>.
    
</dd>
</dl>

<dl>
<dd>

**clientAliases:** `Optional<List<String>>` — List of audiences/realms for SAML protocol. Used by the wsfed addon.
    
</dd>
</dl>

<dl>
<dd>

**allowedClients:** `Optional<List<String>>` — List of allow clients and API ids that are allowed to make delegation requests. Empty means all all your clients are allowed.
    
</dd>
</dl>

<dl>
<dd>

**allowedLogoutUrls:** `Optional<List<String>>` — Comma-separated list of URLs that are valid to redirect to after logout from Auth0. Wildcards are allowed for subdomains.
    
</dd>
</dl>

<dl>
<dd>

**grantTypes:** `Optional<List<String>>` — List of grant types supported for this application. Can include `authorization_code`, `implicit`, `refresh_token`, `client_credentials`, `password`, `http://auth0.com/oauth/grant-type/password-realm`, `http://auth0.com/oauth/grant-type/mfa-oob`, `http://auth0.com/oauth/grant-type/mfa-otp`, `http://auth0.com/oauth/grant-type/mfa-recovery-code`, `urn:openid:params:grant-type:ciba`, `urn:ietf:params:oauth:grant-type:device_code`, and `urn:auth0:params:oauth:grant-type:token-exchange:federated-connection-access-token`.
    
</dd>
</dl>

<dl>
<dd>

**tokenEndpointAuthMethod:** `Optional<ClientTokenEndpointAuthMethodEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**isTokenEndpointIpHeaderTrusted:** `Optional<Boolean>` — If true, trust that the IP specified in the `auth0-forwarded-for` header is the end-user's IP for brute-force-protection on token endpoint.
    
</dd>
</dl>

<dl>
<dd>

**appType:** `Optional<ClientAppTypeEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**isFirstParty:** `Optional<Boolean>` — Whether this client a first party client or not
    
</dd>
</dl>

<dl>
<dd>

**oidcConformant:** `Optional<Boolean>` — Whether this client conforms to <a href='https://auth0.com/docs/api-auth/tutorials/adoption'>strict OIDC specifications</a> (true) or uses legacy features (false).
    
</dd>
</dl>

<dl>
<dd>

**jwtConfiguration:** `Optional<ClientJwtConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**encryptionKey:** `Optional<ClientEncryptionKey>` 
    
</dd>
</dl>

<dl>
<dd>

**sso:** `Optional<Boolean>` — Applies only to SSO clients and determines whether Auth0 will handle Single Sign On (true) or whether the Identity Provider will (false).
    
</dd>
</dl>

<dl>
<dd>

**crossOriginAuthentication:** `Optional<Boolean>` — Whether this client can be used to make cross-origin authentication requests (true) or it is not allowed to make such requests (false).
    
</dd>
</dl>

<dl>
<dd>

**crossOriginLoc:** `Optional<String>` — URL of the location in your site where the cross origin verification takes place for the cross-origin auth flow when performing Auth in your own domain instead of Auth0 hosted login page.
    
</dd>
</dl>

<dl>
<dd>

**ssoDisabled:** `Optional<Boolean>` — <code>true</code> to disable Single Sign On, <code>false</code> otherwise (default: <code>false</code>)
    
</dd>
</dl>

<dl>
<dd>

**customLoginPageOn:** `Optional<Boolean>` — <code>true</code> if the custom login page is to be used, <code>false</code> otherwise. Defaults to <code>true</code>
    
</dd>
</dl>

<dl>
<dd>

**customLoginPage:** `Optional<String>` — The content (HTML, CSS, JS) of the custom login page.
    
</dd>
</dl>

<dl>
<dd>

**customLoginPagePreview:** `Optional<String>` — The content (HTML, CSS, JS) of the custom login page. (Used on Previews)
    
</dd>
</dl>

<dl>
<dd>

**formTemplate:** `Optional<String>` — HTML form template to be used for WS-Federation.
    
</dd>
</dl>

<dl>
<dd>

**addons:** `Optional<ClientAddons>` 
    
</dd>
</dl>

<dl>
<dd>

**clientMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**mobile:** `Optional<ClientMobile>` 
    
</dd>
</dl>

<dl>
<dd>

**initiateLoginUri:** `Optional<String>` — Initiate login uri, must be https
    
</dd>
</dl>

<dl>
<dd>

**nativeSocialLogin:** `Optional<NativeSocialLogin>` 
    
</dd>
</dl>

<dl>
<dd>

**refreshToken:** `Optional<ClientRefreshTokenConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultOrganization:** `Optional<ClientDefaultOrganization>` 
    
</dd>
</dl>

<dl>
<dd>

**organizationUsage:** `Optional<ClientOrganizationUsageEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**organizationRequireBehavior:** `Optional<ClientOrganizationRequireBehaviorEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**organizationDiscoveryMethods:** `Optional<List<ClientOrganizationDiscoveryEnum>>` — Defines the available methods for organization discovery during the `pre_login_prompt`. Users can discover their organization either by `email`, `organization_name` or both.
    
</dd>
</dl>

<dl>
<dd>

**clientAuthenticationMethods:** `Optional<ClientCreateAuthenticationMethod>` 
    
</dd>
</dl>

<dl>
<dd>

**requirePushedAuthorizationRequests:** `Optional<Boolean>` — Makes the use of Pushed Authorization Requests mandatory for this client
    
</dd>
</dl>

<dl>
<dd>

**requireProofOfPossession:** `Optional<Boolean>` — Makes the use of Proof-of-Possession mandatory for this client
    
</dd>
</dl>

<dl>
<dd>

**signedRequestObject:** `Optional<ClientSignedRequestObjectWithPublicKey>` 
    
</dd>
</dl>

<dl>
<dd>

**complianceLevel:** `Optional<ClientComplianceLevelEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**skipNonVerifiableCallbackUriConfirmationPrompt:** `Optional<Boolean>` 

Controls whether a confirmation prompt is shown during login flows when the redirect URI uses non-verifiable callback URIs (for example, a custom URI schema such as `myapp://`, or `localhost`).
If set to true, a confirmation prompt will not be shown. We recommend that this is set to false for improved protection from malicious apps.
See https://auth0.com/docs/secure/security-guidance/measures-against-app-impersonation for more information.
    
</dd>
</dl>

<dl>
<dd>

**tokenExchange:** `Optional<ClientTokenExchangeConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**parRequestExpiry:** `Optional<Integer>` — Specifies how long, in seconds, a Pushed Authorization Request URI remains valid
    
</dd>
</dl>

<dl>
<dd>

**tokenQuota:** `Optional<CreateTokenQuota>` 
    
</dd>
</dl>

<dl>
<dd>

**resourceServerIdentifier:** `Optional<String>` — The identifier of the resource server that this client is linked to.
    
</dd>
</dl>

<dl>
<dd>

**expressConfiguration:** `Optional<ExpressConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**asyncApprovalNotificationChannels:** `Optional<List<AsyncApprovalNotificationsChannelsEnum>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.get(id) -> GetClientResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve client details by ID. Clients are SSO connections or Applications linked with your Auth0 tenant. A list of fields to include or exclude may also be specified. 
For more information, read <a href="https://www.auth0.com/docs/get-started/applications"> Applications in Auth0</a> and <a href="https://www.auth0.com/docs/authenticate/single-sign-on"> Single Sign-On</a>.
<ul>
  <li>
    The following properties can be retrieved with any of the scopes:
    <code>client_id</code>, <code>app_type</code>, <code>name</code>, and <code>description</code>.
  </li>
  <li>
    The following properties can only be retrieved with the <code>read:clients</code> or
    <code>read:client_keys</code> scopes:
    <code>callbacks</code>, <code>oidc_logout</code>, <code>allowed_origins</code>,
    <code>web_origins</code>, <code>tenant</code>, <code>global</code>, <code>config_route</code>,
    <code>callback_url_template</code>, <code>jwt_configuration</code>,
    <code>jwt_configuration.lifetime_in_seconds</code>, <code>jwt_configuration.secret_encoded</code>,
    <code>jwt_configuration.scopes</code>, <code>jwt_configuration.alg</code>, <code>api_type</code>,
    <code>logo_uri</code>, <code>allowed_clients</code>, <code>owners</code>, <code>custom_login_page</code>,
    <code>custom_login_page_off</code>, <code>sso</code>, <code>addons</code>, <code>form_template</code>,
    <code>custom_login_page_codeview</code>, <code>resource_servers</code>, <code>client_metadata</code>,
    <code>mobile</code>, <code>mobile.android</code>, <code>mobile.ios</code>, <code>allowed_logout_urls</code>,
    <code>token_endpoint_auth_method</code>, <code>is_first_party</code>, <code>oidc_conformant</code>,
    <code>is_token_endpoint_ip_header_trusted</code>, <code>initiate_login_uri</code>, <code>grant_types</code>,
    <code>refresh_token</code>, <code>refresh_token.rotation_type</code>, <code>refresh_token.expiration_type</code>,
    <code>refresh_token.leeway</code>, <code>refresh_token.token_lifetime</code>, <code>refresh_token.policies</code>, <code>organization_usage</code>,
    <code>organization_require_behavior</code>.
  </li>
  <li>
    The following properties can only be retrieved with the <code>read:client_keys</code> or <code>read:client_credentials</code> scopes:
    <code>encryption_key</code>, <code>encryption_key.pub</code>, <code>encryption_key.cert</code>,
    <code>client_secret</code>, <code>client_authentication_methods</code> and <code>signing_key</code>.
  </li>
</ul>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().get(
    "id",
    GetClientRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the client to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a client and related configuration (rules, connections, etc).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the client to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.update(id, request) -> UpdateClientResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Updates a client's settings. For more information, read <a href="https://www.auth0.com/docs/get-started/applications"> Applications in Auth0</a> and <a href="https://www.auth0.com/docs/authenticate/single-sign-on"> Single Sign-On</a>.

Notes:
- The `client_secret` and `signing_key` attributes can only be updated with the `update:client_keys` scope.
- The <code>client_authentication_methods</code> and <code>token_endpoint_auth_method</code> properties are mutually exclusive. Use <code>client_authentication_methods</code> to configure the client with Private Key JWT authentication method. Otherwise, use <code>token_endpoint_auth_method</code> to configure the client with client secret (basic or post) or with no authentication method (none).
- When using <code>client_authentication_methods</code> to configure the client with Private Key JWT authentication method, only specify the credential IDs that were generated when creating the credentials on the client.
- To configure <code>client_authentication_methods</code>, the <code>update:client_credentials</code> scope is required.
- To configure <code>client_authentication_methods</code>, the property <code>jwt_configuration.alg</code> must be set to RS256.
- To change a client's <code>is_first_party</code> property to <code>false</code>, the <code>organization_usage</code> and <code>organization_require_behavior</code> properties must be unset.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().update(
    "id",
    UpdateClientRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the client to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — The name of the client. Must contain at least one character. Does not allow '<' or '>'.
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` — Free text description of the purpose of the Client. (Max character length: <code>140</code>)
    
</dd>
</dl>

<dl>
<dd>

**clientSecret:** `Optional<String>` — The secret used to sign tokens for the client
    
</dd>
</dl>

<dl>
<dd>

**logoUri:** `Optional<String>` — The URL of the client logo (recommended size: 150x150)
    
</dd>
</dl>

<dl>
<dd>

**callbacks:** `Optional<List<String>>` — A set of URLs that are valid to call back from Auth0 when authenticating users
    
</dd>
</dl>

<dl>
<dd>

**oidcLogout:** `Optional<ClientOidcBackchannelLogoutSettings>` 
    
</dd>
</dl>

<dl>
<dd>

**oidcBackchannelLogout:** `Optional<ClientOidcBackchannelLogoutSettings>` 
    
</dd>
</dl>

<dl>
<dd>

**sessionTransfer:** `Optional<ClientSessionTransferConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**allowedOrigins:** `Optional<List<String>>` — A set of URLs that represents valid origins for CORS
    
</dd>
</dl>

<dl>
<dd>

**webOrigins:** `Optional<List<String>>` — A set of URLs that represents valid web origins for use with web message response mode
    
</dd>
</dl>

<dl>
<dd>

**grantTypes:** `Optional<List<String>>` — A set of grant types that the client is authorized to use. Can include `authorization_code`, `implicit`, `refresh_token`, `client_credentials`, `password`, `http://auth0.com/oauth/grant-type/password-realm`, `http://auth0.com/oauth/grant-type/mfa-oob`, `http://auth0.com/oauth/grant-type/mfa-otp`, `http://auth0.com/oauth/grant-type/mfa-recovery-code`, `urn:openid:params:grant-type:ciba`, `urn:ietf:params:oauth:grant-type:device_code`, and `urn:auth0:params:oauth:grant-type:token-exchange:federated-connection-access-token`.
    
</dd>
</dl>

<dl>
<dd>

**clientAliases:** `Optional<List<String>>` — List of audiences for SAML protocol
    
</dd>
</dl>

<dl>
<dd>

**allowedClients:** `Optional<List<String>>` — Ids of clients that will be allowed to perform delegation requests. Clients that will be allowed to make delegation request. By default, all your clients will be allowed. This field allows you to specify specific clients
    
</dd>
</dl>

<dl>
<dd>

**allowedLogoutUrls:** `Optional<List<String>>` — URLs that are valid to redirect to after logout from Auth0.
    
</dd>
</dl>

<dl>
<dd>

**jwtConfiguration:** `Optional<ClientJwtConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**encryptionKey:** `Optional<ClientEncryptionKey>` 
    
</dd>
</dl>

<dl>
<dd>

**sso:** `Optional<Boolean>` — <code>true</code> to use Auth0 instead of the IdP to do Single Sign On, <code>false</code> otherwise (default: <code>false</code>)
    
</dd>
</dl>

<dl>
<dd>

**crossOriginAuthentication:** `Optional<Boolean>` — <code>true</code> if this client can be used to make cross-origin authentication requests, <code>false</code> otherwise if cross origin is disabled
    
</dd>
</dl>

<dl>
<dd>

**crossOriginLoc:** `Optional<String>` — URL for the location in your site where the cross origin verification takes place for the cross-origin auth flow when performing Auth in your own domain instead of Auth0 hosted login page.
    
</dd>
</dl>

<dl>
<dd>

**ssoDisabled:** `Optional<Boolean>` — <code>true</code> to disable Single Sign On, <code>false</code> otherwise (default: <code>false</code>)
    
</dd>
</dl>

<dl>
<dd>

**customLoginPageOn:** `Optional<Boolean>` — <code>true</code> if the custom login page is to be used, <code>false</code> otherwise.
    
</dd>
</dl>

<dl>
<dd>

**tokenEndpointAuthMethod:** `Optional<ClientTokenEndpointAuthMethodOrNullEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**isTokenEndpointIpHeaderTrusted:** `Optional<Boolean>` — If true, trust that the IP specified in the `auth0-forwarded-for` header is the end-user's IP for brute-force-protection on token endpoint.
    
</dd>
</dl>

<dl>
<dd>

**appType:** `Optional<ClientAppTypeEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**isFirstParty:** `Optional<Boolean>` — Whether this client a first party client or not
    
</dd>
</dl>

<dl>
<dd>

**oidcConformant:** `Optional<Boolean>` — Whether this client will conform to strict OIDC specifications
    
</dd>
</dl>

<dl>
<dd>

**customLoginPage:** `Optional<String>` — The content (HTML, CSS, JS) of the custom login page
    
</dd>
</dl>

<dl>
<dd>

**customLoginPagePreview:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**tokenQuota:** `Optional<UpdateTokenQuota>` 
    
</dd>
</dl>

<dl>
<dd>

**formTemplate:** `Optional<String>` — Form template for WS-Federation protocol
    
</dd>
</dl>

<dl>
<dd>

**addons:** `Optional<ClientAddons>` 
    
</dd>
</dl>

<dl>
<dd>

**clientMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**mobile:** `Optional<ClientMobile>` 
    
</dd>
</dl>

<dl>
<dd>

**initiateLoginUri:** `Optional<String>` — Initiate login uri, must be https
    
</dd>
</dl>

<dl>
<dd>

**nativeSocialLogin:** `Optional<NativeSocialLogin>` 
    
</dd>
</dl>

<dl>
<dd>

**refreshToken:** `Optional<ClientRefreshTokenConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultOrganization:** `Optional<ClientDefaultOrganization>` 
    
</dd>
</dl>

<dl>
<dd>

**organizationUsage:** `Optional<ClientOrganizationUsagePatchEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**organizationRequireBehavior:** `Optional<ClientOrganizationRequireBehaviorPatchEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**organizationDiscoveryMethods:** `Optional<List<ClientOrganizationDiscoveryEnum>>` — Defines the available methods for organization discovery during the `pre_login_prompt`. Users can discover their organization either by `email`, `organization_name` or both.
    
</dd>
</dl>

<dl>
<dd>

**clientAuthenticationMethods:** `Optional<ClientAuthenticationMethod>` 
    
</dd>
</dl>

<dl>
<dd>

**requirePushedAuthorizationRequests:** `Optional<Boolean>` — Makes the use of Pushed Authorization Requests mandatory for this client
    
</dd>
</dl>

<dl>
<dd>

**requireProofOfPossession:** `Optional<Boolean>` — Makes the use of Proof-of-Possession mandatory for this client
    
</dd>
</dl>

<dl>
<dd>

**signedRequestObject:** `Optional<ClientSignedRequestObjectWithCredentialId>` 
    
</dd>
</dl>

<dl>
<dd>

**complianceLevel:** `Optional<ClientComplianceLevelEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**skipNonVerifiableCallbackUriConfirmationPrompt:** `Optional<Boolean>` 

Controls whether a confirmation prompt is shown during login flows when the redirect URI uses non-verifiable callback URIs (for example, a custom URI schema such as `myapp://`, or `localhost`).
If set to true, a confirmation prompt will not be shown. We recommend that this is set to false for improved protection from malicious apps.
See https://auth0.com/docs/secure/security-guidance/measures-against-app-impersonation for more information.
    
</dd>
</dl>

<dl>
<dd>

**tokenExchange:** `Optional<ClientTokenExchangeConfigurationOrNull>` 
    
</dd>
</dl>

<dl>
<dd>

**parRequestExpiry:** `Optional<Integer>` — Specifies how long, in seconds, a Pushed Authorization Request URI remains valid
    
</dd>
</dl>

<dl>
<dd>

**expressConfiguration:** `Optional<ExpressConfigurationOrNull>` 
    
</dd>
</dl>

<dl>
<dd>

**asyncApprovalNotificationChannels:** `Optional<List<AsyncApprovalNotificationsChannelsEnum>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.rotateSecret(id) -> RotateClientSecretResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Rotate a client secret.

This endpoint cannot be used with clients configured with Private Key JWT authentication method (client_authentication_methods configured with private_key_jwt). The generated secret is NOT base64 encoded.

For more information, read <a href="https://www.auth0.com/docs/get-started/applications/rotate-client-secret">Rotate Client Secrets</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().rotateSecret("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the client that will rotate secrets.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## ConnectionProfiles
<details><summary><code>client.connectionProfiles.list() -> SyncPagingIterable&lt;ConnectionProfile&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a list of Connection Profiles. This endpoint supports Checkpoint pagination.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connectionProfiles().list(
    ListConnectionProfileRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 5.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connectionProfiles.create(request) -> CreateConnectionProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a Connection Profile.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connectionProfiles().create(
    CreateConnectionProfileRequestContent
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**organization:** `Optional<ConnectionProfileOrganization>` 
    
</dd>
</dl>

<dl>
<dd>

**connectionNamePrefixTemplate:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**enabledFeatures:** `Optional<List<EnabledFeaturesEnum>>` 
    
</dd>
</dl>

<dl>
<dd>

**connectionConfig:** `Optional<ConnectionProfileConfig>` 
    
</dd>
</dl>

<dl>
<dd>

**strategyOverrides:** `Optional<ConnectionProfileStrategyOverrides>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connectionProfiles.listTemplates() -> ListConnectionProfileTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a list of Connection Profile Templates.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connectionProfiles().listTemplates();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connectionProfiles.getTemplate(id) -> GetConnectionProfileTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a Connection Profile Template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connectionProfiles().getTemplate("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the connection-profile-template to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connectionProfiles.get(id) -> GetConnectionProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a single Connection Profile specified by ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connectionProfiles().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the connection-profile to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connectionProfiles.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a single Connection Profile specified by ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connectionProfiles().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the connection-profile to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connectionProfiles.update(id, request) -> UpdateConnectionProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the details of a specific Connection Profile.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connectionProfiles().update(
    "id",
    UpdateConnectionProfileRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the connection profile to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**organization:** `Optional<ConnectionProfileOrganization>` 
    
</dd>
</dl>

<dl>
<dd>

**connectionNamePrefixTemplate:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**enabledFeatures:** `Optional<List<EnabledFeaturesEnum>>` 
    
</dd>
</dl>

<dl>
<dd>

**connectionConfig:** `Optional<ConnectionProfileConfig>` 
    
</dd>
</dl>

<dl>
<dd>

**strategyOverrides:** `Optional<ConnectionProfileStrategyOverrides>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Connections
<details><summary><code>client.connections.list() -> SyncPagingIterable&lt;ConnectionForList&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves detailed list of all <a href="https://auth0.com/docs/authenticate/identity-providers">connections</a> that match the specified strategy. If no strategy is provided, all connections within your tenant are retrieved. This action can accept a list of fields to include or exclude from the resulting list of connections. 

This endpoint supports two types of pagination:
<ul>
<li>Offset pagination</li>
<li>Checkpoint pagination</li>
</ul>

Checkpoint pagination must be used if you need to retrieve more than 1000 connections.

<h2>Checkpoint Pagination</h2>

To search by checkpoint, use the following parameters:
<ul>
<li><code>from</code>: Optional id from which to start selection.</li>
<li><code>take</code>: The total amount of entries to retrieve when using the from parameter. Defaults to 50.</li>
</ul>

<b>Note</b>: The first time you call this endpoint using checkpoint pagination, omit the <code>from</code> parameter. If there are more results, a <code>next</code> value is included in the response. You can use this for subsequent API calls. When <code>next</code> is no longer included in the response, no pages are remaining.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().list(
    ListConnectionsQueryParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .name(
            OptionalNullable.of("name")
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**strategy:** `Optional<ConnectionStrategyEnum>` — Provide strategies to only retrieve connections with such strategies
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Provide the name of the connection to retrieve
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — A comma separated list of fields to include or exclude (depending on include_fields) from the result, empty to retrieve all fields
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — <code>true</code> if the fields specified are to be included in the result, <code>false</code> otherwise (defaults to <code>true</code>)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.create(request) -> CreateConnectionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Creates a new connection according to the JSON object received in <code>body</code>.<br/>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().create(
    CreateConnectionRequestContent
        .builder()
        .name("name")
        .strategy(ConnectionIdentityProviderEnum.AD)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — The name of the connection. Must start and end with an alphanumeric character and can only contain alphanumeric characters and '-'. Max length 128
    
</dd>
</dl>

<dl>
<dd>

**displayName:** `Optional<String>` — Connection name used in the new universal login experience
    
</dd>
</dl>

<dl>
<dd>

**strategy:** `ConnectionIdentityProviderEnum` 
    
</dd>
</dl>

<dl>
<dd>

**options:** `Optional<ConnectionPropertiesOptions>` 
    
</dd>
</dl>

<dl>
<dd>

**enabledClients:** `Optional<List<String>>` — DEPRECATED property. Use the PATCH /v2/connections/{id}/clients endpoint to enable the connection for a set of clients.
    
</dd>
</dl>

<dl>
<dd>

**isDomainConnection:** `Optional<Boolean>` — <code>true</code> promotes to a domain-level connection so that third-party applications can use it. <code>false</code> does not promote the connection, so only first-party applications with the connection enabled can use it. (Defaults to <code>false</code>.)
    
</dd>
</dl>

<dl>
<dd>

**showAsButton:** `Optional<Boolean>` — Enables showing a button for the connection in the login page (new experience only). If false, it will be usable only by HRD. (Defaults to <code>false</code>.)
    
</dd>
</dl>

<dl>
<dd>

**realms:** `Optional<List<String>>` — Defines the realms for which the connection will be used (ie: email domains). If the array is empty or the property is not specified, the connection name will be added as realm.
    
</dd>
</dl>

<dl>
<dd>

**metadata:** `Optional<Map<String, Optional<String>>>` 
    
</dd>
</dl>

<dl>
<dd>

**authentication:** `Optional<ConnectionAuthenticationPurpose>` 
    
</dd>
</dl>

<dl>
<dd>

**connectedAccounts:** `Optional<ConnectionConnectedAccountsPurpose>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.get(id) -> GetConnectionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details for a specified <a href="https://auth0.com/docs/authenticate/identity-providers">connection</a> along with options that can be used for identity provider configuration.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().get(
    "id",
    GetConnectionRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to retrieve
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — A comma separated list of fields to include or exclude (depending on include_fields) from the result, empty to retrieve all fields
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — <code>true</code> if the fields specified are to be included in the result, <code>false</code> otherwise (defaults to <code>true</code>)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Removes a specific <a href="https://auth0.com/docs/authenticate/identity-providers">connection</a> from your tenant. This action cannot be undone. Once removed, users can no longer use this connection to authenticate.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to delete
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.update(id, request) -> UpdateConnectionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update details for a specific <a href="https://auth0.com/docs/authenticate/identity-providers">connection</a>, including option properties for identity provider configuration.

<b>Note</b>: If you use the <code>options</code> parameter, the entire <code>options</code> object is overriden. To avoid partial data or other issues, ensure all parameters are present when using this option.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().update(
    "id",
    UpdateConnectionRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to update
    
</dd>
</dl>

<dl>
<dd>

**displayName:** `Optional<String>` — The connection name used in the new universal login experience. If display_name is not included in the request, the field will be overwritten with the name value.
    
</dd>
</dl>

<dl>
<dd>

**options:** `Optional<UpdateConnectionOptions>` 
    
</dd>
</dl>

<dl>
<dd>

**enabledClients:** `Optional<List<String>>` — DEPRECATED property. Use the PATCH /v2/connections/{id}/clients endpoint to enable or disable the connection for any clients.
    
</dd>
</dl>

<dl>
<dd>

**isDomainConnection:** `Optional<Boolean>` — <code>true</code> promotes to a domain-level connection so that third-party applications can use it. <code>false</code> does not promote the connection, so only first-party applications with the connection enabled can use it. (Defaults to <code>false</code>.)
    
</dd>
</dl>

<dl>
<dd>

**showAsButton:** `Optional<Boolean>` — Enables showing a button for the connection in the login page (new experience only). If false, it will be usable only by HRD. (Defaults to <code>false</code>.)
    
</dd>
</dl>

<dl>
<dd>

**realms:** `Optional<List<String>>` — Defines the realms for which the connection will be used (ie: email domains). If the array is empty or the property is not specified, the connection name will be added as realm.
    
</dd>
</dl>

<dl>
<dd>

**metadata:** `Optional<Map<String, Optional<String>>>` 
    
</dd>
</dl>

<dl>
<dd>

**authentication:** `Optional<ConnectionAuthenticationPurpose>` 
    
</dd>
</dl>

<dl>
<dd>

**connectedAccounts:** `Optional<ConnectionConnectedAccountsPurpose>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.checkStatus(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves the status of an ad/ldap connection referenced by its <code>ID</code>. <code>200 OK</code> http status code response is returned  when the connection is online, otherwise a <code>404</code> status code is returned along with an error message
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().checkStatus("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the connection to check
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## CustomDomains
<details><summary><code>client.customDomains.list() -> List&lt;CustomDomain&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details on <a href="https://auth0.com/docs/custom-domains">custom domains</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.customDomains().list(
    ListCustomDomainsRequestParameters
        .builder()
        .q(
            OptionalNullable.of("q")
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .sort(
            OptionalNullable.of("sort")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**q:** `Optional<String>` — Query in <a href ="http://www.lucenetutorial.com/lucene-query-syntax.html">Lucene query string syntax</a>.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>

<dl>
<dd>

**sort:** `Optional<String>` — Field to sort by. Only <code>domain:1</code> (ascending order by domain) is supported at this time.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.customDomains.create(request) -> CreateCustomDomainResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new custom domain.

Note: The custom domain will need to be verified before it will accept
requests.

Optional attributes that can be updated:

- custom_client_ip_header
- tls_policy


TLS Policies:

- recommended - for modern usage this includes TLS 1.2 only
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.customDomains().create(
    CreateCustomDomainRequestContent
        .builder()
        .domain("domain")
        .type(CustomDomainProvisioningTypeEnum.AUTH0MANAGED_CERTS)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**domain:** `String` — Domain name.
    
</dd>
</dl>

<dl>
<dd>

**type:** `CustomDomainProvisioningTypeEnum` 
    
</dd>
</dl>

<dl>
<dd>

**verificationMethod:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**tlsPolicy:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**customClientIpHeader:** `Optional<CustomDomainCustomClientIpHeaderEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**domainMetadata:** `Optional<Map<String, Optional<String>>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.customDomains.get(id) -> GetCustomDomainResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a custom domain configuration and status.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.customDomains().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the custom domain to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.customDomains.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a custom domain and stop serving requests for it.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.customDomains().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the custom domain to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.customDomains.update(id, request) -> UpdateCustomDomainResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a custom domain.

These are the attributes that can be updated:

- custom_client_ip_header
- tls_policy

<h5>Updating CUSTOM_CLIENT_IP_HEADER for a custom domain</h5>To update the <code>custom_client_ip_header</code> for a domain, the body to
send should be:
<pre><code>{ "custom_client_ip_header": "cf-connecting-ip" }</code></pre>

<h5>Updating TLS_POLICY for a custom domain</h5>To update the <code>tls_policy</code> for a domain, the body to send should be:
<pre><code>{ "tls_policy": "recommended" }</code></pre>


TLS Policies:

- recommended - for modern usage this includes TLS 1.2 only


Some considerations:

- The TLS ciphers and protocols available in each TLS policy follow industry recommendations, and may be updated occasionally.
- The <code>compatible</code> TLS policy is no longer supported.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.customDomains().update(
    "id",
    UpdateCustomDomainRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the custom domain to update
    
</dd>
</dl>

<dl>
<dd>

**tlsPolicy:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**customClientIpHeader:** `Optional<CustomDomainCustomClientIpHeaderEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**domainMetadata:** `Optional<Map<String, Optional<String>>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.customDomains.test(id) -> TestCustomDomainResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Run the test process on a custom domain.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.customDomains().test("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the custom domain to test.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.customDomains.verify(id) -> VerifyCustomDomainResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Run the verification process on a custom domain.

Note: Check the <code>status</code> field to see its verification status. Once verification is complete, it may take up to 10 minutes before the custom domain can start accepting requests.

For <code>self_managed_certs</code>, when the custom domain is verified for the first time, the response will also include the <code>cname_api_key</code> which you will need to configure your proxy. This key must be kept secret, and is used to validate the proxy requests.

<a href="https://auth0.com/docs/custom-domains#step-2-verify-ownership">Learn more</a> about verifying custom domains that use Auth0 Managed certificates.
<a href="https://auth0.com/docs/custom-domains/self-managed-certificates#step-2-verify-ownership">Learn more</a> about verifying custom domains that use Self Managed certificates.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.customDomains().verify("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the custom domain to verify.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## DeviceCredentials
<details><summary><code>client.deviceCredentials.list() -> SyncPagingIterable&lt;DeviceCredential&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve device credential information (<code>public_key</code>, <code>refresh_token</code>, or <code>rotating_refresh_token</code>) associated with a specific user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deviceCredentials().list(
    ListDeviceCredentialsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .userId(
            OptionalNullable.of("user_id")
        )
        .clientId(
            OptionalNullable.of("client_id")
        )
        .type(
            OptionalNullable.of(DeviceCredentialTypeEnum.PUBLIC_KEY)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page.  There is a maximum of 1000 results allowed from this endpoint.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<String>` — user_id of the devices to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — client_id of the devices to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**type:** `Optional<DeviceCredentialTypeEnum>` — Type of credentials to retrieve. Must be `public_key`, `refresh_token` or `rotating_refresh_token`. The property will default to `refresh_token` when paging is requested
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deviceCredentials.createPublicKey(request) -> CreatePublicKeyDeviceCredentialResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a device credential public key to manage refresh token rotation for a given <code>user_id</code>. Device Credentials APIs are designed for ad-hoc administrative use only and paging is by default enabled for GET requests.

When refresh token rotation is enabled, the endpoint becomes consistent. For more information, read <a href="https://auth0.com/docs/get-started/tenant-settings/signing-keys"> Signing Keys</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deviceCredentials().createPublicKey(
    CreatePublicKeyDeviceCredentialRequestContent
        .builder()
        .deviceName("device_name")
        .type("public_key")
        .value("value")
        .deviceId("device_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**deviceName:** `String` — Name for this device easily recognized by owner.
    
</dd>
</dl>

<dl>
<dd>

**type:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**value:** `String` — Base64 encoded string containing the credential.
    
</dd>
</dl>

<dl>
<dd>

**deviceId:** `String` — Unique identifier for the device. Recommend using <a href="http://developer.android.com/reference/android/provider/Settings.Secure.html#ANDROID_ID">Android_ID</a> on Android and <a href="https://developer.apple.com/library/ios/documentation/UIKit/Reference/UIDevice_Class/index.html#//apple_ref/occ/instp/UIDevice/identifierForVendor">identifierForVendor</a>.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — client_id of the client (application) this credential is for.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.deviceCredentials.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Permanently delete a device credential (such as a refresh token or public key) with the given ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.deviceCredentials().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the credential to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## EmailTemplates
<details><summary><code>client.emailTemplates.create(request) -> CreateEmailTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create an email template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.emailTemplates().create(
    CreateEmailTemplateRequestContent
        .builder()
        .template(EmailTemplateNameEnum.VERIFY_EMAIL)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**template:** `EmailTemplateNameEnum` 
    
</dd>
</dl>

<dl>
<dd>

**body:** `Optional<String>` — Body of the email template.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Senders `from` email address.
    
</dd>
</dl>

<dl>
<dd>

**resultUrl:** `Optional<String>` — URL to redirect the user to after a successful action.
    
</dd>
</dl>

<dl>
<dd>

**subject:** `Optional<String>` — Subject line of the email.
    
</dd>
</dl>

<dl>
<dd>

**syntax:** `Optional<String>` — Syntax of the template body.
    
</dd>
</dl>

<dl>
<dd>

**urlLifetimeInSeconds:** `Optional<Double>` — Lifetime in seconds that the link within the email will be valid for.
    
</dd>
</dl>

<dl>
<dd>

**includeEmailInRedirect:** `Optional<Boolean>` — Whether the `reset_email` and `verify_email` templates should include the user's email address as the `email` parameter in the returnUrl (true) or whether no email address should be included in the redirect (false). Defaults to true.
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether the template is enabled (true) or disabled (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.emailTemplates.get(templateName) -> GetEmailTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve an email template by pre-defined name. These names are `verify_email`, `verify_email_by_code`, `reset_email`, `reset_email_by_code`, `welcome_email`, `blocked_account`, `stolen_credentials`, `enrollment_email`, `mfa_oob_code`, `user_invitation`, and `async_approval`. The names `change_password`, and `password_reset` are also supported for legacy scenarios.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.emailTemplates().get(EmailTemplateNameEnum.VERIFY_EMAIL);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**templateName:** `EmailTemplateNameEnum` — Template name. Can be `verify_email`, `verify_email_by_code`, `reset_email`, `reset_email_by_code`, `welcome_email`, `blocked_account`, `stolen_credentials`, `enrollment_email`, `mfa_oob_code`, `user_invitation`, `async_approval`, `change_password` (legacy), or `password_reset` (legacy).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.emailTemplates.set(templateName, request) -> SetEmailTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update an email template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.emailTemplates().set(
    EmailTemplateNameEnum.VERIFY_EMAIL,
    SetEmailTemplateRequestContent
        .builder()
        .template(EmailTemplateNameEnum.VERIFY_EMAIL)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**templateName:** `EmailTemplateNameEnum` — Template name. Can be `verify_email`, `verify_email_by_code`, `reset_email`, `reset_email_by_code`, `welcome_email`, `blocked_account`, `stolen_credentials`, `enrollment_email`, `mfa_oob_code`, `user_invitation`, `async_approval`, `change_password` (legacy), or `password_reset` (legacy).
    
</dd>
</dl>

<dl>
<dd>

**template:** `EmailTemplateNameEnum` 
    
</dd>
</dl>

<dl>
<dd>

**body:** `Optional<String>` — Body of the email template.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Senders `from` email address.
    
</dd>
</dl>

<dl>
<dd>

**resultUrl:** `Optional<String>` — URL to redirect the user to after a successful action.
    
</dd>
</dl>

<dl>
<dd>

**subject:** `Optional<String>` — Subject line of the email.
    
</dd>
</dl>

<dl>
<dd>

**syntax:** `Optional<String>` — Syntax of the template body.
    
</dd>
</dl>

<dl>
<dd>

**urlLifetimeInSeconds:** `Optional<Double>` — Lifetime in seconds that the link within the email will be valid for.
    
</dd>
</dl>

<dl>
<dd>

**includeEmailInRedirect:** `Optional<Boolean>` — Whether the `reset_email` and `verify_email` templates should include the user's email address as the `email` parameter in the returnUrl (true) or whether no email address should be included in the redirect (false). Defaults to true.
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether the template is enabled (true) or disabled (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.emailTemplates.update(templateName, request) -> UpdateEmailTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify an email template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.emailTemplates().update(
    EmailTemplateNameEnum.VERIFY_EMAIL,
    UpdateEmailTemplateRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**templateName:** `EmailTemplateNameEnum` — Template name. Can be `verify_email`, `verify_email_by_code`, `reset_email`, `reset_email_by_code`, `welcome_email`, `blocked_account`, `stolen_credentials`, `enrollment_email`, `mfa_oob_code`, `user_invitation`, `async_approval`, `change_password` (legacy), or `password_reset` (legacy).
    
</dd>
</dl>

<dl>
<dd>

**template:** `Optional<EmailTemplateNameEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**body:** `Optional<String>` — Body of the email template.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Senders `from` email address.
    
</dd>
</dl>

<dl>
<dd>

**resultUrl:** `Optional<String>` — URL to redirect the user to after a successful action.
    
</dd>
</dl>

<dl>
<dd>

**subject:** `Optional<String>` — Subject line of the email.
    
</dd>
</dl>

<dl>
<dd>

**syntax:** `Optional<String>` — Syntax of the template body.
    
</dd>
</dl>

<dl>
<dd>

**urlLifetimeInSeconds:** `Optional<Double>` — Lifetime in seconds that the link within the email will be valid for.
    
</dd>
</dl>

<dl>
<dd>

**includeEmailInRedirect:** `Optional<Boolean>` — Whether the `reset_email` and `verify_email` templates should include the user's email address as the `email` parameter in the returnUrl (true) or whether no email address should be included in the redirect (false). Defaults to true.
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether the template is enabled (true) or disabled (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## EventStreams
<details><summary><code>client.eventStreams.list() -> ListEventStreamsResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().list(
    ListEventStreamsRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.eventStreams.create(request) -> CreateEventStreamResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().create(
    EventStreamsCreateRequest.of(
        CreateEventStreamWebHookRequestContent
            .builder()
            .destination(
                EventStreamWebhookDestination
                    .builder()
                    .type("webhook")
                    .configuration(
                        EventStreamWebhookConfiguration
                            .builder()
                            .webhookEndpoint("webhook_endpoint")
                            .webhookAuthorization(
                                EventStreamWebhookAuthorizationResponse.of(
                                    EventStreamWebhookBasicAuth
                                        .builder()
                                        .method("basic")
                                        .username("username")
                                        .build()
                                )
                            )
                            .build()
                    )
                    .build()
            )
            .build()
    )
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `EventStreamsCreateRequest` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.eventStreams.get(id) -> GetEventStreamResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Unique identifier for the event stream.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.eventStreams.delete(id)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Unique identifier for the event stream.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.eventStreams.update(id, request) -> UpdateEventStreamResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().update(
    "id",
    UpdateEventStreamRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Unique identifier for the event stream.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Name of the event stream.
    
</dd>
</dl>

<dl>
<dd>

**subscriptions:** `Optional<List<EventStreamSubscription>>` — List of event types subscribed to in this stream.
    
</dd>
</dl>

<dl>
<dd>

**destination:** `Optional<EventStreamDestinationPatch>` 
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<EventStreamStatusEnum>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.eventStreams.test(id, request) -> CreateEventStreamTestEventResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().test(
    "id",
    CreateEventStreamTestEventRequestContent
        .builder()
        .eventType(EventStreamTestEventTypeEnum.USER_CREATED)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Unique identifier for the event stream.
    
</dd>
</dl>

<dl>
<dd>

**eventType:** `EventStreamTestEventTypeEnum` 
    
</dd>
</dl>

<dl>
<dd>

**data:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Flows
<details><summary><code>client.flows.list() -> SyncPagingIterable&lt;FlowSummary&gt;</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().list(
    FlowsListRequest
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .synchronous(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**hydrate:** `Optional<String>` — hydration param
    
</dd>
</dl>

<dl>
<dd>

**synchronous:** `Optional<Boolean>` — flag to filter by sync/async flows
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.create(request) -> CreateFlowResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().create(
    CreateFlowRequestContent
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**actions:** `Optional<List<FlowAction>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.get(id) -> GetFlowResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().get(
    "id",
    GetFlowRequestParameters
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Flow identifier
    
</dd>
</dl>

<dl>
<dd>

**hydrate:** `Optional<GetFlowRequestParametersHydrateEnum>` — hydration param
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.delete(id)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Flow id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.update(id, request) -> UpdateFlowResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().update(
    "id",
    UpdateFlowRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Flow identifier
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**actions:** `Optional<List<FlowAction>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Forms
<details><summary><code>client.forms.list() -> SyncPagingIterable&lt;FormSummary&gt;</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.forms().list(
    ListFormsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**hydrate:** `Optional<FormsRequestParametersHydrateEnum>` — Query parameter to hydrate the response with additional data
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.forms.create(request) -> CreateFormResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.forms().create(
    CreateFormRequestContent
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**messages:** `Optional<FormMessages>` 
    
</dd>
</dl>

<dl>
<dd>

**languages:** `Optional<FormLanguages>` 
    
</dd>
</dl>

<dl>
<dd>

**translations:** `Optional<Map<String, Map<String, Object>>>` 
    
</dd>
</dl>

<dl>
<dd>

**nodes:** `Optional<List<FormNode>>` 
    
</dd>
</dl>

<dl>
<dd>

**start:** `Optional<FormStartNode>` 
    
</dd>
</dl>

<dl>
<dd>

**ending:** `Optional<FormEndingNode>` 
    
</dd>
</dl>

<dl>
<dd>

**style:** `Optional<FormStyle>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.forms.get(id) -> GetFormResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.forms().get(
    "id",
    GetFormRequestParameters
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the form to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**hydrate:** `Optional<FormsRequestParametersHydrateEnum>` — Query parameter to hydrate the response with additional data
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.forms.delete(id)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.forms().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the form to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.forms.update(id, request) -> UpdateFormResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.forms().update(
    "id",
    UpdateFormRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the form to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**messages:** `Optional<FormMessages>` 
    
</dd>
</dl>

<dl>
<dd>

**languages:** `Optional<FormLanguages>` 
    
</dd>
</dl>

<dl>
<dd>

**translations:** `Optional<Map<String, Map<String, Object>>>` 
    
</dd>
</dl>

<dl>
<dd>

**nodes:** `Optional<List<FormNode>>` 
    
</dd>
</dl>

<dl>
<dd>

**start:** `Optional<FormStartNode>` 
    
</dd>
</dl>

<dl>
<dd>

**ending:** `Optional<FormEndingNode>` 
    
</dd>
</dl>

<dl>
<dd>

**style:** `Optional<FormStyle>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## UserGrants
<details><summary><code>client.userGrants.list() -> SyncPagingIterable&lt;UserGrant&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the <a href="https://auth0.com/docs/api-auth/which-oauth-flow-to-use">grants</a> associated with your account. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userGrants().list(
    ListUserGrantsRequestParameters
        .builder()
        .perPage(
            OptionalNullable.of(1)
        )
        .page(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .userId(
            OptionalNullable.of("user_id")
        )
        .clientId(
            OptionalNullable.of("client_id")
        )
        .audience(
            OptionalNullable.of("audience")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<String>` — user_id of the grants to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — client_id of the grants to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**audience:** `Optional<String>` — audience of the grants to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userGrants.deleteByUserId()</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a grant associated with your account. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userGrants().deleteByUserId(
    DeleteUserGrantByUserIdRequestParameters
        .builder()
        .userId("user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — user_id of the grant to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userGrants.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a grant associated with your account. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userGrants().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the grant to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Hooks
<details><summary><code>client.hooks.list() -> SyncPagingIterable&lt;Hook&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all <a href="https://auth0.com/docs/hooks">hooks</a>. Accepts a list of fields to include or exclude in the result.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().list(
    ListHooksRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .enabled(
            OptionalNullable.of(true)
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .triggerId(
            OptionalNullable.of(HookTriggerIdEnum.CREDENTIALS_EXCHANGE)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Optional filter on whether a hook is enabled (true) or disabled (false).
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `Optional<HookTriggerIdEnum>` — Retrieves hooks that match the trigger
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.hooks.create(request) -> CreateHookResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new hook.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().create(
    CreateHookRequestContent
        .builder()
        .name("name")
        .script("script")
        .triggerId(HookTriggerIdEnum.CREDENTIALS_EXCHANGE)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — Name of this hook.
    
</dd>
</dl>

<dl>
<dd>

**script:** `String` — Code to be executed when this hook runs.
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether this hook will be executed (true) or ignored (false).
    
</dd>
</dl>

<dl>
<dd>

**dependencies:** `Optional<Map<String, String>>` 
    
</dd>
</dl>

<dl>
<dd>

**triggerId:** `HookTriggerIdEnum` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.hooks.get(id) -> GetHookResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve <a href="https://auth0.com/docs/hooks">a hook</a> by its ID. Accepts a list of fields to include in the result.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().get(
    "id",
    GetHookRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the hook to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.hooks.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a hook.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the hook to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.hooks.update(id, request) -> UpdateHookResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update an existing hook.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().update(
    "id",
    UpdateHookRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the hook to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Name of this hook.
    
</dd>
</dl>

<dl>
<dd>

**script:** `Optional<String>` — Code to be executed when this hook runs.
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether this hook will be executed (true) or ignored (false).
    
</dd>
</dl>

<dl>
<dd>

**dependencies:** `Optional<Map<String, String>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Jobs
<details><summary><code>client.jobs.get(id) -> GetJobResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves a job. Useful to check its status.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.jobs().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the job.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## LogStreams
<details><summary><code>client.logStreams.list() -> List&lt;LogStreamResponseSchema&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details on <a href="https://auth0.com/docs/logs/streams">log streams</a>.
<h5>Sample Response</h5><pre><code>[{
	"id": "string",
	"name": "string",
	"type": "eventbridge",
	"status": "active|paused|suspended",
	"sink": {
		"awsAccountId": "string",
		"awsRegion": "string",
		"awsPartnerEventSource": "string"
	}
}, {
	"id": "string",
	"name": "string",
	"type": "http",
	"status": "active|paused|suspended",
	"sink": {
		"httpContentFormat": "JSONLINES|JSONARRAY",
		"httpContentType": "string",
		"httpEndpoint": "string",
		"httpAuthorization": "string"
	}
},
{
	"id": "string",
	"name": "string",
	"type": "eventgrid",
	"status": "active|paused|suspended",
	"sink": {
		"azureSubscriptionId": "string",
		"azureResourceGroup": "string",
		"azureRegion": "string",
		"azurePartnerTopic": "string"
	}
},
{
	"id": "string",
	"name": "string",
	"type": "splunk",
	"status": "active|paused|suspended",
	"sink": {
		"splunkDomain": "string",
		"splunkToken": "string",
		"splunkPort": "string",
		"splunkSecure": "boolean"
	}
},
{
	"id": "string",
	"name": "string",
	"type": "sumo",
	"status": "active|paused|suspended",
	"sink": {
		"sumoSourceAddress": "string",
	}
},
{
	"id": "string",
	"name": "string",
	"type": "datadog",
	"status": "active|paused|suspended",
	"sink": {
		"datadogRegion": "string",
		"datadogApiKey": "string"
	}
}]</code></pre>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.logStreams().list();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.logStreams.create(request) -> CreateLogStreamResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a log stream.
<h5>Log Stream Types</h5> The <code>type</code> of log stream being created determines the properties required in the <code>sink</code> payload.
<h5>HTTP Stream</h5> For an <code>http</code> Stream, the <code>sink</code> properties are listed in the payload below
Request: <pre><code>{
	"name": "string",
	"type": "http",
	"sink": {
		"httpEndpoint": "string",
		"httpContentType": "string",
		"httpContentFormat": "JSONLINES|JSONARRAY",
		"httpAuthorization": "string"
	}
}</code></pre>
Response: <pre><code>{
	"id": "string",
	"name": "string",
	"type": "http",
	"status": "active",
	"sink": {
		"httpEndpoint": "string",
		"httpContentType": "string",
		"httpContentFormat": "JSONLINES|JSONARRAY",
		"httpAuthorization": "string"
	}
}</code></pre>
<h5>Amazon EventBridge Stream</h5> For an <code>eventbridge</code> Stream, the <code>sink</code> properties are listed in the payload below
Request: <pre><code>{
	"name": "string",
	"type": "eventbridge",
	"sink": {
		"awsRegion": "string",
		"awsAccountId": "string"
	}
}</code></pre>
The response will include an additional field <code>awsPartnerEventSource</code> in the <code>sink</code>: <pre><code>{
	"id": "string",
	"name": "string",
	"type": "eventbridge",
	"status": "active",
	"sink": {
		"awsAccountId": "string",
		"awsRegion": "string",
		"awsPartnerEventSource": "string"
	}
}</code></pre>
<h5>Azure Event Grid Stream</h5> For an <code>Azure Event Grid</code> Stream, the <code>sink</code> properties are listed in the payload below
Request: <pre><code>{
	"name": "string",
	"type": "eventgrid",
	"sink": {
		"azureSubscriptionId": "string",
		"azureResourceGroup": "string",
		"azureRegion": "string"
	}
}</code></pre>
Response: <pre><code>{
	"id": "string",
	"name": "string",
	"type": "http",
	"status": "active",
	"sink": {
		"azureSubscriptionId": "string",
		"azureResourceGroup": "string",
		"azureRegion": "string",
		"azurePartnerTopic": "string"
	}
}</code></pre>
<h5>Datadog Stream</h5> For a <code>Datadog</code> Stream, the <code>sink</code> properties are listed in the payload below
Request: <pre><code>{
	"name": "string",
	"type": "datadog",
	"sink": {
		"datadogRegion": "string",
		"datadogApiKey": "string"
	}
}</code></pre>
Response: <pre><code>{
	"id": "string",
	"name": "string",
	"type": "datadog",
	"status": "active",
	"sink": {
		"datadogRegion": "string",
		"datadogApiKey": "string"
	}
}</code></pre>
<h5>Splunk Stream</h5> For a <code>Splunk</code> Stream, the <code>sink</code> properties are listed in the payload below
Request: <pre><code>{
	"name": "string",
	"type": "splunk",
	"sink": {
		"splunkDomain": "string",
		"splunkToken": "string",
		"splunkPort": "string",
		"splunkSecure": "boolean"
	}
}</code></pre>
Response: <pre><code>{
	"id": "string",
	"name": "string",
	"type": "splunk",
	"status": "active",
	"sink": {
		"splunkDomain": "string",
		"splunkToken": "string",
		"splunkPort": "string",
		"splunkSecure": "boolean"
	}
}</code></pre>
<h5>Sumo Logic Stream</h5> For a <code>Sumo Logic</code> Stream, the <code>sink</code> properties are listed in the payload below
Request: <pre><code>{
	"name": "string",
	"type": "sumo",
	"sink": {
		"sumoSourceAddress": "string",
	}
}</code></pre>
Response: <pre><code>{
	"id": "string",
	"name": "string",
	"type": "sumo",
	"status": "active",
	"sink": {
		"sumoSourceAddress": "string",
	}
}</code></pre>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.logStreams().create(
    CreateLogStreamRequestContent.of(
        CreateLogStreamHttpRequestBody
            .builder()
            .type("http")
            .sink(
                LogStreamHttpSink
                    .builder()
                    .httpEndpoint("httpEndpoint")
                    .build()
            )
            .build()
    )
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `CreateLogStreamRequestContent` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.logStreams.get(id) -> GetLogStreamResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a log stream configuration and status.
<h5>Sample responses</h5><h5>Amazon EventBridge Log Stream</h5><pre><code>{
	"id": "string",
	"name": "string",
	"type": "eventbridge",
	"status": "active|paused|suspended",
	"sink": {
		"awsAccountId": "string",
		"awsRegion": "string",
		"awsPartnerEventSource": "string"
	}
}</code></pre> <h5>HTTP Log Stream</h5><pre><code>{
	"id": "string",
	"name": "string",
	"type": "http",
	"status": "active|paused|suspended",
	"sink": {
		"httpContentFormat": "JSONLINES|JSONARRAY",
		"httpContentType": "string",
		"httpEndpoint": "string",
		"httpAuthorization": "string"
	}
}</code></pre> <h5>Datadog Log Stream</h5><pre><code>{
	"id": "string",
	"name": "string",
	"type": "datadog",
	"status": "active|paused|suspended",
	"sink": {
		"datadogRegion": "string",
		"datadogApiKey": "string"
	}

}</code></pre><h5>Mixpanel</h5>
	
	Request: <pre><code>{
	  "name": "string",
	  "type": "mixpanel",
	  "sink": {
		"mixpanelRegion": "string", // "us" | "eu",
		"mixpanelProjectId": "string",
		"mixpanelServiceAccountUsername": "string",
		"mixpanelServiceAccountPassword": "string"
	  }
	} </code></pre>
	
	
	Response: <pre><code>{
		"id": "string",
		"name": "string",
		"type": "mixpanel",
		"status": "active",
		"sink": {
		  "mixpanelRegion": "string", // "us" | "eu",
		  "mixpanelProjectId": "string",
		  "mixpanelServiceAccountUsername": "string",
		  "mixpanelServiceAccountPassword": "string" // the following is redacted on return
		}
	  } </code></pre>

	<h5>Segment</h5>

	Request: <pre><code> {
	  "name": "string",
	  "type": "segment",
	  "sink": {
		"segmentWriteKey": "string"
	  }
	}</code></pre>
	
	Response: <pre><code>{
	  "id": "string",
	  "name": "string",
	  "type": "segment",
	  "status": "active",
	  "sink": {
		"segmentWriteKey": "string"
	  }
	} </code></pre>
	
<h5>Splunk Log Stream</h5><pre><code>{
	"id": "string",
	"name": "string",
	"type": "splunk",
	"status": "active|paused|suspended",
	"sink": {
		"splunkDomain": "string",
		"splunkToken": "string",
		"splunkPort": "string",
		"splunkSecure": "boolean"
	}
}</code></pre> <h5>Sumo Logic Log Stream</h5><pre><code>{
	"id": "string",
	"name": "string",
	"type": "sumo",
	"status": "active|paused|suspended",
	"sink": {
		"sumoSourceAddress": "string",
	}
}</code></pre> <h5>Status</h5> The <code>status</code> of a log stream maybe any of the following:
1. <code>active</code> - Stream is currently enabled.
2. <code>paused</code> - Stream is currently user disabled and will not attempt log delivery.
3. <code>suspended</code> - Stream is currently disabled because of errors and will not attempt log delivery.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.logStreams().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the log stream to get
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.logStreams.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a log stream.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.logStreams().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the log stream to delete
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.logStreams.update(id, request) -> UpdateLogStreamResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a log stream.
<h4>Examples of how to use the PATCH endpoint.</h4> The following fields may be updated in a PATCH operation: <ul><li>name</li><li>status</li><li>sink</li></ul> Note: For log streams of type <code>eventbridge</code> and <code>eventgrid</code>, updating the <code>sink</code> is not permitted.
<h5>Update the status of a log stream</h5><pre><code>{
	"status": "active|paused"
}</code></pre>
<h5>Update the name of a log stream</h5><pre><code>{
	"name": "string"
}</code></pre>
<h5>Update the sink properties of a stream of type <code>http</code></h5><pre><code>{
  "sink": {
    "httpEndpoint": "string",
    "httpContentType": "string",
    "httpContentFormat": "JSONARRAY|JSONLINES",
    "httpAuthorization": "string"
  }
}</code></pre>
<h5>Update the sink properties of a stream of type <code>datadog</code></h5><pre><code>{
  "sink": {
		"datadogRegion": "string",
		"datadogApiKey": "string"
  }
}</code></pre>
<h5>Update the sink properties of a stream of type <code>splunk</code></h5><pre><code>{
  "sink": {
    "splunkDomain": "string",
    "splunkToken": "string",
    "splunkPort": "string",
    "splunkSecure": "boolean"
  }
}</code></pre>
<h5>Update the sink properties of a stream of type <code>sumo</code></h5><pre><code>{
  "sink": {
    "sumoSourceAddress": "string"
  }
}</code></pre> 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.logStreams().update(
    "id",
    UpdateLogStreamRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the log stream to get
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — log stream name
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<LogStreamStatusEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**isPriority:** `Optional<Boolean>` — True for priority log streams, false for non-priority
    
</dd>
</dl>

<dl>
<dd>

**filters:** `Optional<List<LogStreamFilter>>` — Only logs events matching these filters will be delivered by the stream. If omitted or empty, all events will be delivered.
    
</dd>
</dl>

<dl>
<dd>

**piiConfig:** `Optional<LogStreamPiiConfig>` 
    
</dd>
</dl>

<dl>
<dd>

**sink:** `Optional<LogStreamSinkPatch>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Logs
<details><summary><code>client.logs.list() -> SyncPagingIterable&lt;Log&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve log entries that match the specified search criteria (or all log entries if no criteria specified).

Set custom search criteria using the <code>q</code> parameter, or search from a specific log ID (<i>"search from checkpoint"</i>).

For more information on all possible event types, their respective acronyms, and descriptions, see <a href="https://auth0.com/docs/logs/log-event-type-codes">Log Event Type Codes</a>.

<h5>To set custom search criteria, use the following parameters:</h5>

<ul>
    <li><b>q:</b> Search Criteria using <a href="https://auth0.com/docs/logs/log-search-query-syntax">Query String Syntax</a></li>
    <li><b>page:</b> Page index of the results to return. First page is 0.</li>
    <li><b>per_page:</b> Number of results per page.</li>
    <li><b>sort:</b> Field to use for sorting appended with `:1` for ascending and `:-1` for descending. e.g. `date:-1`</li>
    <li><b>fields:</b> Comma-separated list of fields to include or exclude (depending on include_fields) from the result, empty to retrieve all fields.</li>
    <li><b>include_fields:</b> Whether specified fields are to be included (true) or excluded (false).</li>
    <li><b>include_totals:</b> Return results inside an object that contains the total result count (true) or as a direct array of results (false, default). <b>Deprecated:</b> this field is deprecated and should be removed from use. See <a href="https://auth0.com/docs/product-lifecycle/deprecations-and-migrations/migrate-to-tenant-log-search-v3#pagination">Search Engine V3 Breaking Changes</a></li>
</ul>

For more information on the list of fields that can be used in <code>fields</code> and <code>sort</code>, see <a href="https://auth0.com/docs/logs/log-search-query-syntax#searchable-fields">Searchable Fields</a>.

Auth0 <a href="https://auth0.com/docs/logs/retrieve-log-events-using-mgmt-api#limitations">limits the number of logs</a> you can return by search criteria to 100 logs per request. Furthermore, you may paginate only through 1,000 search results. If you exceed this threshold, please redefine your search or use the <a href="https://auth0.com/docs/logs/retrieve-log-events-using-mgmt-api#retrieve-logs-by-checkpoint">get logs by checkpoint method</a>.

<h5>To search from a checkpoint log ID, use the following parameters:</h5>
<ul>
    <li><b>from:</b> Log Event ID from which to start retrieving logs. You can limit the number of logs returned using the <code>take</code> parameter. If you use <code>from</code> at the same time as <code>q</code>, <code>from</code> takes precedence and <code>q</code> is ignored.</li>
    <li><b>take:</b> Number of entries to retrieve when using the <code>from</code> parameter.</li>
</ul>

<strong>Important:</strong> When fetching logs from a checkpoint log ID, any parameter other than <code>from</code> and <code>take</code> will be ignored, and date ordering is not guaranteed.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.logs().list(
    ListLogsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .sort(
            OptionalNullable.of("sort")
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .search(
            OptionalNullable.of("search")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` —  Number of results per page. Paging is disabled if parameter not sent. Default: <code>50</code>. Max value: <code>100</code>
    
</dd>
</dl>

<dl>
<dd>

**sort:** `Optional<String>` — Field to use for sorting appended with <code>:1</code>  for ascending and <code>:-1</code> for descending. e.g. <code>date:-1</code>
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for <code>include_fields</code>) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (<code>true</code>) or excluded (<code>false</code>)
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results as an array when false (default). Return results inside an object that also contains a total result count when true.
    
</dd>
</dl>

<dl>
<dd>

**search:** `Optional<String>` 

Retrieves logs that match the specified search criteria. This parameter can be combined with all the others in the /api/logs endpoint but is specified separately for clarity.
If no fields are provided a case insensitive 'starts with' search is performed on all of the following fields: client_name, connection, user_name. Otherwise, you can specify multiple fields and specify the search using the %field%:%search%, for example: application:node user:"John@contoso.com".
Values specified without quotes are matched using a case insensitive 'starts with' search. If quotes are used a case insensitve exact search is used. If multiple fields are used, the AND operator is used to join the clauses.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.logs.get(id) -> GetLogResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve an individual log event.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.logs().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — log_id of the log to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## NetworkAcls
<details><summary><code>client.networkAcls.list() -> SyncPagingIterable&lt;NetworkAclsResponseContent&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get all access control list entries for your client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.networkAcls().list(
    ListNetworkAclsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Use this field to request a specific page of the list results.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — The amount of results per page.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.networkAcls.create(request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new access control list for your client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.networkAcls().create(
    CreateNetworkAclRequestContent
        .builder()
        .description("description")
        .active(true)
        .priority(1.1)
        .rule(
            NetworkAclRule
                .builder()
                .action(
                    NetworkAclAction
                        .builder()
                        .build()
                )
                .scope(NetworkAclRuleScopeEnum.MANAGEMENT)
                .build()
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**description:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**active:** `Boolean` — Indicates whether or not this access control list is actively being used
    
</dd>
</dl>

<dl>
<dd>

**priority:** `Double` — Indicates the order in which the ACL will be evaluated relative to other ACL rules.
    
</dd>
</dl>

<dl>
<dd>

**rule:** `NetworkAclRule` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.networkAcls.get(id) -> GetNetworkAclsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get a specific access control list entry for your client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.networkAcls().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the access control list to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.networkAcls.set(id, request) -> SetNetworkAclsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update existing access control list for your client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.networkAcls().set(
    "id",
    SetNetworkAclRequestContent
        .builder()
        .description("description")
        .active(true)
        .priority(1.1)
        .rule(
            NetworkAclRule
                .builder()
                .action(
                    NetworkAclAction
                        .builder()
                        .build()
                )
                .scope(NetworkAclRuleScopeEnum.MANAGEMENT)
                .build()
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the ACL to update.
    
</dd>
</dl>

<dl>
<dd>

**description:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**active:** `Boolean` — Indicates whether or not this access control list is actively being used
    
</dd>
</dl>

<dl>
<dd>

**priority:** `Double` — Indicates the order in which the ACL will be evaluated relative to other ACL rules.
    
</dd>
</dl>

<dl>
<dd>

**rule:** `NetworkAclRule` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.networkAcls.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete existing access control list for your client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.networkAcls().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the ACL to delete
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.networkAcls.update(id, request) -> UpdateNetworkAclResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update existing access control list for your client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.networkAcls().update(
    "id",
    UpdateNetworkAclRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the ACL to update.
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**active:** `Optional<Boolean>` — Indicates whether or not this access control list is actively being used
    
</dd>
</dl>

<dl>
<dd>

**priority:** `Optional<Double>` — Indicates the order in which the ACL will be evaluated relative to other ACL rules.
    
</dd>
</dl>

<dl>
<dd>

**rule:** `Optional<NetworkAclRule>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Organizations
<details><summary><code>client.organizations.list() -> SyncPagingIterable&lt;Organization&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve detailed list of all Organizations available in your tenant. For more information, see Auth0 Organizations.

This endpoint supports two types of pagination:
<ul>
<li>Offset pagination</li>
<li>Checkpoint pagination</li>
</ul>

Checkpoint pagination must be used if you need to retrieve more than 1000 organizations.

<h2>Checkpoint Pagination</h2>

To search by checkpoint, use the following parameters:
<ul>
<li><code>from</code>: Optional id from which to start selection.</li>
<li><code>take</code>: The total number of entries to retrieve when using the <code>from</code> parameter. Defaults to 50.</li>
</ul>

<b>Note</b>: The first time you call this endpoint using checkpoint pagination, omit the <code>from</code> parameter. If there are more results, a <code>next</code> value is included in the response. You can use this for subsequent API calls. When <code>next</code> is no longer included in the response, no pages are remaining.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().list(
    ListOrganizationsRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .sort(
            OptionalNullable.of("sort")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**sort:** `Optional<String>` — Field to sort by. Use <code>field:order</code> where order is <code>1</code> for ascending and <code>-1</code> for descending. e.g. <code>created_at:1</code>. We currently support sorting by the following fields: <code>name</code>, <code>display_name</code> and <code>created_at</code>.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.create(request) -> CreateOrganizationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new Organization within your tenant.  To learn more about Organization settings, behavior, and configuration options, review <a href="https://auth0.com/docs/manage-users/organizations/create-first-organization">Create Your First Organization</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().create(
    CreateOrganizationRequestContent
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — The name of this organization.
    
</dd>
</dl>

<dl>
<dd>

**displayName:** `Optional<String>` — Friendly name of this organization.
    
</dd>
</dl>

<dl>
<dd>

**branding:** `Optional<OrganizationBranding>` 
    
</dd>
</dl>

<dl>
<dd>

**metadata:** `Optional<Map<String, Optional<String>>>` 
    
</dd>
</dl>

<dl>
<dd>

**enabledConnections:** `Optional<List<ConnectionForOrganization>>` — Connections that will be enabled for this organization. See POST enabled_connections endpoint for the object format. (Max of 10 connections allowed)
    
</dd>
</dl>

<dl>
<dd>

**tokenQuota:** `Optional<CreateTokenQuota>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.getByName(name) -> GetOrganizationByNameResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a single Organization specified by name.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().getByName("name");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — name of the organization to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.get(id) -> GetOrganizationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a single Organization specified by ID. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the organization to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove an Organization from your tenant.  This action cannot be undone. 

<b>Note</b>: Members are automatically disassociated from an Organization when it is deleted. However, this action does <b>not</b> delete these users from your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.update(id, request) -> UpdateOrganizationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the details of a specific <a href="https://auth0.com/docs/manage-users/organizations/configure-organizations/create-organizations">Organization</a>, such as name and display name, branding options, and metadata.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().update(
    "id",
    UpdateOrganizationRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the organization to update.
    
</dd>
</dl>

<dl>
<dd>

**displayName:** `Optional<String>` — Friendly name of this organization.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — The name of this organization.
    
</dd>
</dl>

<dl>
<dd>

**branding:** `Optional<OrganizationBranding>` 
    
</dd>
</dl>

<dl>
<dd>

**metadata:** `Optional<Map<String, Optional<String>>>` 
    
</dd>
</dl>

<dl>
<dd>

**tokenQuota:** `Optional<UpdateTokenQuota>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Prompts
<details><summary><code>client.prompts.getSettings() -> GetSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the Universal Login configuration of your tenant. This includes the <a href="https://auth0.com/docs/authenticate/login/auth0-universal-login/identifier-first">Identifier First Authentication</a> and <a href="https://auth0.com/docs/secure/multi-factor-authentication/fido-authentication-with-webauthn/configure-webauthn-device-biometrics-for-mfa">WebAuthn with Device Biometrics for MFA</a> features.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().getSettings();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.prompts.updateSettings(request) -> UpdateSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the Universal Login configuration of your tenant. This includes the <a href="https://auth0.com/docs/authenticate/login/auth0-universal-login/identifier-first">Identifier First Authentication</a> and <a href="https://auth0.com/docs/secure/multi-factor-authentication/fido-authentication-with-webauthn/configure-webauthn-device-biometrics-for-mfa">WebAuthn with Device Biometrics for MFA</a> features.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().updateSettings(
    UpdateSettingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**universalLoginExperience:** `Optional<UniversalLoginExperienceEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**identifierFirst:** `Optional<Boolean>` — Whether identifier first is enabled or not
    
</dd>
</dl>

<dl>
<dd>

**webauthnPlatformFirstFactor:** `Optional<Boolean>` — Use WebAuthn with Device Biometrics as the first authentication factor
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## RefreshTokens
<details><summary><code>client.refreshTokens.get(id) -> GetRefreshTokenResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve refresh token information.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.refreshTokens().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID refresh token to retrieve
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.refreshTokens.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a refresh token by its ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.refreshTokens().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the refresh token to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.refreshTokens.update(id, request) -> UpdateRefreshTokenResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a refresh token by its ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.refreshTokens().update(
    "id",
    UpdateRefreshTokenRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the refresh token to update.
    
</dd>
</dl>

<dl>
<dd>

**refreshTokenMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## ResourceServers
<details><summary><code>client.resourceServers.list() -> SyncPagingIterable&lt;ResourceServer&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of all APIs associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.resourceServers().list(
    ListResourceServerRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**identifiers:** `Optional<String>` — An optional filter on the resource server identifier. Must be URL encoded and may be specified multiple times (max 10).<br /><b>e.g.</b> <i>../resource-servers?identifiers=id1&identifiers=id2</i>
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.resourceServers.create(request) -> CreateResourceServerResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new API associated with your tenant. Note that all new APIs must be registered with Auth0. For more information, read <a href="https://www.auth0.com/docs/get-started/apis"> APIs</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.resourceServers().create(
    CreateResourceServerRequestContent
        .builder()
        .identifier("identifier")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `Optional<String>` — Friendly name for this resource server. Can not contain `<` or `>` characters.
    
</dd>
</dl>

<dl>
<dd>

**identifier:** `String` — Unique identifier for the API used as the audience parameter on authorization calls. Can not be changed once set.
    
</dd>
</dl>

<dl>
<dd>

**scopes:** `Optional<List<ResourceServerScope>>` — List of permissions (scopes) that this API uses.
    
</dd>
</dl>

<dl>
<dd>

**signingAlg:** `Optional<SigningAlgorithmEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**signingSecret:** `Optional<String>` — Secret used to sign tokens when using symmetric algorithms (HS256).
    
</dd>
</dl>

<dl>
<dd>

**allowOfflineAccess:** `Optional<Boolean>` — Whether refresh tokens can be issued for this API (true) or not (false).
    
</dd>
</dl>

<dl>
<dd>

**tokenLifetime:** `Optional<Integer>` — Expiration value (in seconds) for access tokens issued for this API from the token endpoint.
    
</dd>
</dl>

<dl>
<dd>

**tokenDialect:** `Optional<ResourceServerTokenDialectSchemaEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**skipConsentForVerifiableFirstPartyClients:** `Optional<Boolean>` — Whether to skip user consent for applications flagged as first party (true) or not (false).
    
</dd>
</dl>

<dl>
<dd>

**enforcePolicies:** `Optional<Boolean>` — Whether to enforce authorization policies (true) or to ignore them (false).
    
</dd>
</dl>

<dl>
<dd>

**tokenEncryption:** `Optional<ResourceServerTokenEncryption>` 
    
</dd>
</dl>

<dl>
<dd>

**consentPolicy:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**authorizationDetails:** `Optional<List<Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**proofOfPossession:** `Optional<ResourceServerProofOfPossession>` 
    
</dd>
</dl>

<dl>
<dd>

**subjectTypeAuthorization:** `Optional<ResourceServerSubjectTypeAuthorization>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.resourceServers.get(id) -> GetResourceServerResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve <a href="https://auth0.com/docs/apis">API</a> details with the given ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.resourceServers().get(
    "id",
    GetResourceServerRequestParameters
        .builder()
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID or audience of the resource server to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.resourceServers.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete an existing API by ID. For more information, read <a href="https://www.auth0.com/docs/get-started/apis/api-settings">API Settings</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.resourceServers().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID or the audience of the resource server to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.resourceServers.update(id, request) -> UpdateResourceServerResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Change an existing API setting by resource server ID. For more information, read <a href="https://www.auth0.com/docs/get-started/apis/api-settings">API Settings</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.resourceServers().update(
    "id",
    UpdateResourceServerRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID or audience of the resource server to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Friendly name for this resource server. Can not contain `<` or `>` characters.
    
</dd>
</dl>

<dl>
<dd>

**scopes:** `Optional<List<ResourceServerScope>>` — List of permissions (scopes) that this API uses.
    
</dd>
</dl>

<dl>
<dd>

**signingAlg:** `Optional<SigningAlgorithmEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**signingSecret:** `Optional<String>` — Secret used to sign tokens when using symmetric algorithms (HS256).
    
</dd>
</dl>

<dl>
<dd>

**skipConsentForVerifiableFirstPartyClients:** `Optional<Boolean>` — Whether to skip user consent for applications flagged as first party (true) or not (false).
    
</dd>
</dl>

<dl>
<dd>

**allowOfflineAccess:** `Optional<Boolean>` — Whether refresh tokens can be issued for this API (true) or not (false).
    
</dd>
</dl>

<dl>
<dd>

**tokenLifetime:** `Optional<Integer>` — Expiration value (in seconds) for access tokens issued for this API from the token endpoint.
    
</dd>
</dl>

<dl>
<dd>

**tokenDialect:** `Optional<ResourceServerTokenDialectSchemaEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**enforcePolicies:** `Optional<Boolean>` — Whether authorization policies are enforced (true) or not enforced (false).
    
</dd>
</dl>

<dl>
<dd>

**tokenEncryption:** `Optional<ResourceServerTokenEncryption>` 
    
</dd>
</dl>

<dl>
<dd>

**consentPolicy:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**authorizationDetails:** `Optional<List<Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**proofOfPossession:** `Optional<ResourceServerProofOfPossession>` 
    
</dd>
</dl>

<dl>
<dd>

**subjectTypeAuthorization:** `Optional<ResourceServerSubjectTypeAuthorization>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Roles
<details><summary><code>client.roles.list() -> SyncPagingIterable&lt;Role&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve detailed list of user roles created in your tenant.

<b>Note</b>: The returned list does not include standard roles available for tenant members, such as Admin or Support Access.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().list(
    ListRolesRequestParameters
        .builder()
        .perPage(
            OptionalNullable.of(1)
        )
        .page(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .nameFilter(
            OptionalNullable.of("name_filter")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**nameFilter:** `Optional<String>` — Optional filter on name (case-insensitive).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.roles.create(request) -> CreateRoleResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a user role for <a href="https://auth0.com/docs/manage-users/access-control/rbac">Role-Based Access Control</a>.

<b>Note</b>: New roles are not associated with any permissions by default. To assign existing permissions to your role, review Associate Permissions with a Role. To create new permissions, review Add API Permissions.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().create(
    CreateRoleRequestContent
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — Name of the role.
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` — Description of the role.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.roles.get(id) -> GetRoleResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a specific <a href="https://auth0.com/docs/manage-users/access-control/rbac">user role</a> specified by ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the role to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.roles.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a specific <a href="https://auth0.com/docs/manage-users/access-control/rbac">user role</a> from your tenant. Once deleted, it is removed from any user who was previously assigned that role. This action cannot be undone.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the role to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.roles.update(id, request) -> UpdateRoleResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify the details of a specific <a href="https://auth0.com/docs/manage-users/access-control/rbac">user role</a> specified by ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().update(
    "id",
    UpdateRoleRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the role to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Name of this role.
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` — Description of this role.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Rules
<details><summary><code>client.rules.list() -> SyncPagingIterable&lt;Rule&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a filtered list of <a href="https://auth0.com/docs/rules">rules</a>. Accepts a list of fields to include or exclude.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.rules().list(
    ListRulesRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .enabled(
            OptionalNullable.of(true)
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Optional filter on whether a rule is enabled (true) or disabled (false).
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.rules.create(request) -> CreateRuleResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a <a href="https://auth0.com/docs/rules#create-a-new-rule-using-the-management-api">new rule</a>.

Note: Changing a rule's stage of execution from the default <code>login_success</code> can change the rule's function signature to have user omitted.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.rules().create(
    CreateRuleRequestContent
        .builder()
        .name("name")
        .script("script")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — Name of this rule.
    
</dd>
</dl>

<dl>
<dd>

**script:** `String` — Code to be executed when this rule runs.
    
</dd>
</dl>

<dl>
<dd>

**order:** `Optional<Double>` — Order that this rule should execute in relative to other rules. Lower-valued rules execute first.
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether the rule is enabled (true), or disabled (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.rules.get(id) -> GetRuleResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve <a href="https://auth0.com/docs/rules">rule</a> details. Accepts a list of fields to include or exclude in the result.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.rules().get(
    "id",
    GetRuleRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the rule to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.rules.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a rule.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.rules().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the rule to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.rules.update(id, request) -> UpdateRuleResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update an existing rule.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.rules().update(
    "id",
    UpdateRuleRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the rule to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**script:** `Optional<String>` — Code to be executed when this rule runs.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Name of this rule.
    
</dd>
</dl>

<dl>
<dd>

**order:** `Optional<Double>` — Order that this rule should execute in relative to other rules. Lower-valued rules execute first.
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether the rule is enabled (true), or disabled (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## RulesConfigs
<details><summary><code>client.rulesConfigs.list() -> List&lt;RulesConfig&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve rules config variable keys.

    Note: For security, config variable values cannot be retrieved outside rule execution.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.rulesConfigs().list();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.rulesConfigs.set(key, request) -> SetRulesConfigResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Sets a rules config variable.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.rulesConfigs().set(
    "key",
    SetRulesConfigRequestContent
        .builder()
        .value("value")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**key:** `String` — Key of the rules config variable to set (max length: 127 characters).
    
</dd>
</dl>

<dl>
<dd>

**value:** `String` — Value for a rules config variable.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.rulesConfigs.delete(key)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a rules config variable identified by its key.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.rulesConfigs().delete("key");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**key:** `String` — Key of the rules config variable to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## SelfServiceProfiles
<details><summary><code>client.selfServiceProfiles.list() -> SyncPagingIterable&lt;SelfServiceProfile&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves self-service profiles.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().list(
    ListSelfServiceProfilesRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.selfServiceProfiles.create(request) -> CreateSelfServiceProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Creates a self-service profile.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().create(
    CreateSelfServiceProfileRequestContent
        .builder()
        .name("name")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — The name of the self-service Profile.
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` — The description of the self-service Profile.
    
</dd>
</dl>

<dl>
<dd>

**branding:** `Optional<SelfServiceProfileBrandingProperties>` 
    
</dd>
</dl>

<dl>
<dd>

**allowedStrategies:** `Optional<List<SelfServiceProfileAllowedStrategyEnum>>` — List of IdP strategies that will be shown to users during the Self-Service SSO flow. Possible values: [`oidc`, `samlp`, `waad`, `google-apps`, `adfs`, `okta`, `keycloak-samlp`, `pingfederate`]
    
</dd>
</dl>

<dl>
<dd>

**userAttributes:** `Optional<List<SelfServiceProfileUserAttribute>>` — List of attributes to be mapped that will be shown to the user during the SS-SSO flow.
    
</dd>
</dl>

<dl>
<dd>

**userAttributeProfileId:** `Optional<String>` — ID of the user-attribute-profile to associate with this self-service profile.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.selfServiceProfiles.get(id) -> GetSelfServiceProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves a self-service profile by Id.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the self-service profile to retrieve
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.selfServiceProfiles.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Deletes a self-service profile by Id.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the self-service profile to delete
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.selfServiceProfiles.update(id, request) -> UpdateSelfServiceProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Updates a self-service profile.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().update(
    "id",
    UpdateSelfServiceProfileRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the self-service profile to update
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — The name of the self-service Profile.
    
</dd>
</dl>

<dl>
<dd>

**description:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**branding:** `Optional<SelfServiceProfileBrandingProperties>` 
    
</dd>
</dl>

<dl>
<dd>

**allowedStrategies:** `Optional<List<SelfServiceProfileAllowedStrategyEnum>>` — List of IdP strategies that will be shown to users during the Self-Service SSO flow. Possible values: [`oidc`, `samlp`, `waad`, `google-apps`, `adfs`, `okta`, `keycloak-samlp`, `pingfederate`]
    
</dd>
</dl>

<dl>
<dd>

**userAttributes:** `Optional<List<SelfServiceProfileUserAttribute>>` 
    
</dd>
</dl>

<dl>
<dd>

**userAttributeProfileId:** `Optional<String>` — ID of the user-attribute-profile to associate with this self-service profile.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Sessions
<details><summary><code>client.sessions.get(id) -> GetSessionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve session information.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.sessions().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of session to retrieve
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.sessions.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a session by ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.sessions().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the session to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.sessions.update(id, request) -> UpdateSessionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update session information.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.sessions().update(
    "id",
    UpdateSessionRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the session to update.
    
</dd>
</dl>

<dl>
<dd>

**sessionMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.sessions.revoke(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Revokes a session by ID and all associated refresh tokens.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.sessions().revoke("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the session to revoke.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Stats
<details><summary><code>client.stats.getActiveUsersCount() -> Double</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the number of active users that logged in during the last 30 days.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.stats().getActiveUsersCount();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.stats.getDaily() -> List&lt;DailyStats&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the number of logins, signups and breached-password detections (subscription required) that occurred each day within a specified date range.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.stats().getDaily(
    GetDailyStatsRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .to(
            OptionalNullable.of("to")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional first day of the date range (inclusive) in YYYYMMDD format.
    
</dd>
</dl>

<dl>
<dd>

**to:** `Optional<String>` — Optional last day of the date range (inclusive) in YYYYMMDD format.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## SupplementalSignals
<details><summary><code>client.supplementalSignals.get() -> GetSupplementalSignalsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get the supplemental signals configuration for a tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.supplementalSignals().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.supplementalSignals.patch(request) -> PatchSupplementalSignalsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the supplemental signals configuration for a tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.supplementalSignals().patch(
    UpdateSupplementalSignalsRequestContent
        .builder()
        .akamaiEnabled(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**akamaiEnabled:** `Boolean` — Indicates if incoming Akamai Headers should be processed
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Tickets
<details><summary><code>client.tickets.verifyEmail(request) -> VerifyEmailTicketResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create an email verification ticket for a given user. An email verification ticket is a generated URL that the user can consume to verify their email address.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tickets().verifyEmail(
    VerifyEmailTicketRequestContent
        .builder()
        .userId("user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**resultUrl:** `Optional<String>` — URL the user will be redirected to in the classic Universal Login experience once the ticket is used. Cannot be specified when using client_id or organization_id.
    
</dd>
</dl>

<dl>
<dd>

**userId:** `String` — user_id of for whom the ticket should be created.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — ID of the client (application). If provided for tenants using the New Universal Login experience, the email template and UI displays application details, and the user is prompted to redirect to the application's <a target='' href='https://auth0.com/docs/authenticate/login/auth0-universal-login/configure-default-login-routes#completing-the-password-reset-flow'>default login route</a> after the ticket is used. client_id is required to use the <a target='' href='https://auth0.com/docs/customize/actions/flows-and-triggers/post-change-password-flow'>Password Reset Post Challenge</a> trigger.
    
</dd>
</dl>

<dl>
<dd>

**organizationId:** `Optional<String>` — (Optional) Organization ID – the ID of the Organization. If provided, organization parameters will be made available to the email template and organization branding will be applied to the prompt. In addition, the redirect link in the prompt will include organization_id and organization_name query string parameters.
    
</dd>
</dl>

<dl>
<dd>

**ttlSec:** `Optional<Integer>` — Number of seconds for which the ticket is valid before expiration. If unspecified or set to 0, this value defaults to 432000 seconds (5 days).
    
</dd>
</dl>

<dl>
<dd>

**includeEmailInRedirect:** `Optional<Boolean>` — Whether to include the email address as part of the returnUrl in the reset_email (true), or not (false).
    
</dd>
</dl>

<dl>
<dd>

**identity:** `Optional<Identity>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.tickets.changePassword(request) -> ChangePasswordTicketResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a password change ticket for a given user. A password change ticket is a generated URL that the user can consume to start a reset password flow.

Note: This endpoint does not verify the given user’s identity. If you call this endpoint within your application, you must design your application to verify the user’s identity.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tickets().changePassword(
    ChangePasswordTicketRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**resultUrl:** `Optional<String>` — URL the user will be redirected to in the classic Universal Login experience once the ticket is used. Cannot be specified when using client_id or organization_id.
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<String>` — user_id of for whom the ticket should be created.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — ID of the client (application). If provided for tenants using the New Universal Login experience, the email template and UI displays application details, and the user is prompted to redirect to the application's <a target='' href='https://auth0.com/docs/authenticate/login/auth0-universal-login/configure-default-login-routes#completing-the-password-reset-flow'>default login route</a> after the ticket is used. client_id is required to use the <a target='' href='https://auth0.com/docs/customize/actions/flows-and-triggers/post-change-password-flow'>Password Reset Post Challenge</a> trigger.
    
</dd>
</dl>

<dl>
<dd>

**organizationId:** `Optional<String>` — (Optional) Organization ID – the ID of the Organization. If provided, organization parameters will be made available to the email template and organization branding will be applied to the prompt. In addition, the redirect link in the prompt will include organization_id and organization_name query string parameters.
    
</dd>
</dl>

<dl>
<dd>

**connectionId:** `Optional<String>` — ID of the connection. If provided, allows the user to be specified using email instead of user_id. If you set this value, you must also send the email parameter. You cannot send user_id when specifying a connection_id.
    
</dd>
</dl>

<dl>
<dd>

**email:** `Optional<String>` — Email address of the user for whom the tickets should be created. Requires the connection_id parameter. Cannot be specified when using user_id.
    
</dd>
</dl>

<dl>
<dd>

**ttlSec:** `Optional<Integer>` — Number of seconds for which the ticket is valid before expiration. If unspecified or set to 0, this value defaults to 432000 seconds (5 days).
    
</dd>
</dl>

<dl>
<dd>

**markEmailAsVerified:** `Optional<Boolean>` — Whether to set the email_verified attribute to true (true) or whether it should not be updated (false).
    
</dd>
</dl>

<dl>
<dd>

**includeEmailInRedirect:** `Optional<Boolean>` — Whether to include the email address as part of the returnUrl in the reset_email (true), or not (false).
    
</dd>
</dl>

<dl>
<dd>

**identity:** `Optional<ChangePasswordTicketIdentity>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## TokenExchangeProfiles
<details><summary><code>client.tokenExchangeProfiles.list() -> SyncPagingIterable&lt;TokenExchangeProfileResponseContent&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a list of all Token Exchange Profiles available in your tenant.

By using this feature, you agree to the applicable Free Trial terms in <a href="https://www.okta.com/legal/">Okta’s Master Subscription Agreement</a>. It is your responsibility to securely validate the user’s subject_token. See <a href="https://auth0.com/docs/authenticate/custom-token-exchange">User Guide</a> for more details.

This endpoint supports Checkpoint pagination. To search by checkpoint, use the following parameters:
<ul>
<li><code>from</code>: Optional id from which to start selection.</li>
<li><code>take</code>: The total amount of entries to retrieve when using the from parameter. Defaults to 50.</li>
</ul>

<b>Note</b>: The first time you call this endpoint using checkpoint pagination, omit the <code>from</code> parameter. If there are more results, a <code>next</code> value is included in the response. You can use this for subsequent API calls. When <code>next</code> is no longer included in the response, no pages are remaining.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokenExchangeProfiles().list(
    TokenExchangeProfilesListRequest
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.tokenExchangeProfiles.create(request) -> CreateTokenExchangeProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new Token Exchange Profile within your tenant.

By using this feature, you agree to the applicable Free Trial terms in <a href="https://www.okta.com/legal/">Okta’s Master Subscription Agreement</a>. It is your responsibility to securely validate the user’s subject_token. See <a href="https://auth0.com/docs/authenticate/custom-token-exchange">User Guide</a> for more details.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokenExchangeProfiles().create(
    CreateTokenExchangeProfileRequestContent
        .builder()
        .name("name")
        .subjectTokenType("subject_token_type")
        .actionId("action_id")
        .type("custom_authentication")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` — Friendly name of this profile.
    
</dd>
</dl>

<dl>
<dd>

**subjectTokenType:** `String` — Subject token type for this profile. When receiving a token exchange request on the Authentication API, the corresponding token exchange profile with a matching subject_token_type will be executed. This must be a URI.
    
</dd>
</dl>

<dl>
<dd>

**actionId:** `String` — The ID of the Custom Token Exchange action to execute for this profile, in order to validate the subject_token. The action must use the custom-token-exchange trigger.
    
</dd>
</dl>

<dl>
<dd>

**type:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.tokenExchangeProfiles.get(id) -> GetTokenExchangeProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a single Token Exchange Profile specified by ID.

By using this feature, you agree to the applicable Free Trial terms in <a href="https://www.okta.com/legal/">Okta’s Master Subscription Agreement</a>. It is your responsibility to securely validate the user’s subject_token. See <a href="https://auth0.com/docs/authenticate/custom-token-exchange">User Guide</a> for more details.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokenExchangeProfiles().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the Token Exchange Profile to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.tokenExchangeProfiles.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a Token Exchange Profile within your tenant.

By using this feature, you agree to the applicable Free Trial terms in <a href="https://www.okta.com/legal/">Okta's Master Subscription Agreement</a>. It is your responsibility to securely validate the user's subject_token. See <a href="https://auth0.com/docs/authenticate/custom-token-exchange">User Guide</a> for more details.

</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokenExchangeProfiles().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the Token Exchange Profile to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.tokenExchangeProfiles.update(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a Token Exchange Profile within your tenant.

By using this feature, you agree to the applicable Free Trial terms in <a href="https://www.okta.com/legal/">Okta's Master Subscription Agreement</a>. It is your responsibility to securely validate the user's subject_token. See <a href="https://auth0.com/docs/authenticate/custom-token-exchange">User Guide</a> for more details.

</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tokenExchangeProfiles().update(
    "id",
    UpdateTokenExchangeProfileRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the Token Exchange Profile to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Friendly name of this profile.
    
</dd>
</dl>

<dl>
<dd>

**subjectTokenType:** `Optional<String>` — Subject token type for this profile. When receiving a token exchange request on the Authentication API, the corresponding token exchange profile with a matching subject_token_type will be executed. This must be a URI.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## UserAttributeProfiles
<details><summary><code>client.userAttributeProfiles.list() -> SyncPagingIterable&lt;UserAttributeProfile&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a list of User Attribute Profiles. This endpoint supports Checkpoint pagination.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userAttributeProfiles().list(
    ListUserAttributeProfileRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 5.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userAttributeProfiles.create(request) -> CreateUserAttributeProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a single User Attribute Profile specified by ID. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userAttributeProfiles().create(
    CreateUserAttributeProfileRequestContent
        .builder()
        .name("name")
        .userAttributes(
            new HashMap<String, UserAttributeProfileUserAttributeAdditionalProperties>() {{
                put("key", UserAttributeProfileUserAttributeAdditionalProperties
                    .builder()
                    .description("description")
                    .label("label")
                    .profileRequired(true)
                    .auth0Mapping("auth0_mapping")
                    .build());
            }}
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<UserAttributeProfileUserId>` 
    
</dd>
</dl>

<dl>
<dd>

**userAttributes:** `Map<String, UserAttributeProfileUserAttributeAdditionalProperties>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userAttributeProfiles.listTemplates() -> ListUserAttributeProfileTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a list of User Attribute Profile Templates.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userAttributeProfiles().listTemplates();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userAttributeProfiles.getTemplate(id) -> GetUserAttributeProfileTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a User Attribute Profile Template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userAttributeProfiles().getTemplate("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user-attribute-profile-template to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userAttributeProfiles.get(id) -> GetUserAttributeProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a single User Attribute Profile specified by ID. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userAttributeProfiles().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user-attribute-profile to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userAttributeProfiles.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a single User Attribute Profile specified by ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userAttributeProfiles().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user-attribute-profile to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userAttributeProfiles.update(id, request) -> UpdateUserAttributeProfileResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the details of a specific User attribute profile, such as name, user_id and user_attributes.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userAttributeProfiles().update(
    "id",
    UpdateUserAttributeProfileRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user attribute profile to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<UserAttributeProfileUserId>` 
    
</dd>
</dl>

<dl>
<dd>

**userAttributes:** `Optional<Map<String, UserAttributeProfileUserAttributeAdditionalProperties>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## UserBlocks
<details><summary><code>client.userBlocks.listByIdentifier() -> ListUserBlocksByIdentifierResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of all <a href="https://auth0.com/docs/secure/attack-protection/brute-force-protection">Brute-force Protection</a> blocks for a user with the given identifier (username, phone number, or email).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userBlocks().listByIdentifier(
    ListUserBlocksByIdentifierRequestParameters
        .builder()
        .identifier("identifier")
        .considerBruteForceEnablement(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**identifier:** `String` — Should be any of a username, phone number, or email.
    
</dd>
</dl>

<dl>
<dd>

**considerBruteForceEnablement:** `Optional<Boolean>` 


          If true and Brute Force Protection is enabled and configured to block logins, will return a list of blocked IP addresses.
          If true and Brute Force Protection is disabled, will return an empty list.
        
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userBlocks.deleteByIdentifier()</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove all <a href="https://auth0.com/docs/secure/attack-protection/brute-force-protection">Brute-force Protection</a> blocks for the user with the given identifier (username, phone number, or email).

Note: This endpoint does not unblock users that were <a href="https://auth0.com/docs/user-profile#block-and-unblock-a-user">blocked by a tenant administrator</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userBlocks().deleteByIdentifier(
    DeleteUserBlocksByIdentifierRequestParameters
        .builder()
        .identifier("identifier")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**identifier:** `String` — Should be any of a username, phone number, or email.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userBlocks.list(id) -> ListUserBlocksResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of all <a href="https://auth0.com/docs/secure/attack-protection/brute-force-protection">Brute-force Protection</a> blocks for the user with the given ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userBlocks().list(
    "id",
    ListUserBlocksRequestParameters
        .builder()
        .considerBruteForceEnablement(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — user_id of the user blocks to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**considerBruteForceEnablement:** `Optional<Boolean>` 


          If true and Brute Force Protection is enabled and configured to block logins, will return a list of blocked IP addresses.
          If true and Brute Force Protection is disabled, will return an empty list.
        
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.userBlocks.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove all <a href="https://auth0.com/docs/secure/attack-protection/brute-force-protection">Brute-force Protection</a> blocks for the user with the given ID.

Note: This endpoint does not unblock users that were <a href="https://auth0.com/docs/user-profile#block-and-unblock-a-user">blocked by a tenant administrator</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.userBlocks().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The user_id of the user to update.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users
<details><summary><code>client.users.list() -> SyncPagingIterable&lt;UserResponseSchema&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of users. It is possible to:

- Specify a search criteria for users
- Sort the users to be returned
- Select the fields to be returned
- Specify the number of users to retrieve per page and the page index
 <!-- only v3 is available -->
The <code>q</code> query parameter can be used to get users that match the specified criteria <a href="https://auth0.com/docs/users/search/v3/query-syntax">using query string syntax.</a>

<a href="https://auth0.com/docs/users/search/v3">Learn more about searching for users.</a>

Read about <a href="https://auth0.com/docs/users/search/best-practices">best practices</a> when working with the API endpoints for retrieving users.

Auth0 limits the number of users you can return. If you exceed this threshold, please redefine your search, use the <a href="https://auth0.com/docs/api/management/v2#!/Jobs/post_users_exports">export job</a>, or the <a href="https://auth0.com/docs/extensions/user-import-export">User Import / Export</a> extension.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().list(
    ListUsersRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .sort(
            OptionalNullable.of("sort")
        )
        .connection(
            OptionalNullable.of("connection")
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .q(
            OptionalNullable.of("q")
        )
        .searchEngine(
            OptionalNullable.of(SearchEngineVersionsEnum.V1)
        )
        .primaryOrder(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**sort:** `Optional<String>` — Field to sort by. Use <code>field:order</code> where order is <code>1</code> for ascending and <code>-1</code> for descending. e.g. <code>created_at:1</code>
    
</dd>
</dl>

<dl>
<dd>

**connection:** `Optional<String>` — Connection filter. Only applies when using <code>search_engine=v1</code>. To filter by connection with <code>search_engine=v2|v3</code>, use <code>q=identities.connection:"connection_name"</code>
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>

<dl>
<dd>

**q:** `Optional<String>` — Query in <a target='_new' href ='http://www.lucenetutorial.com/lucene-query-syntax.html'>Lucene query string syntax</a>. Some query types cannot be used on metadata fields, for details see <a href='https://auth0.com/docs/users/search/v3/query-syntax#searchable-fields'>Searchable Fields</a>.
    
</dd>
</dl>

<dl>
<dd>

**searchEngine:** `Optional<SearchEngineVersionsEnum>` — The version of the search engine
    
</dd>
</dl>

<dl>
<dd>

**primaryOrder:** `Optional<Boolean>` — If true (default), results are returned in a deterministic order. If false, results may be returned in a non-deterministic order, which can enhance performance for complex queries targeting a small number of users. Set to false only when consistent ordering and pagination is not required.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.create(request) -> CreateUserResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a new user for a given <a href="https://auth0.com/docs/connections/database">database</a> or <a href="https://auth0.com/docs/connections/passwordless">passwordless</a> connection.

Note: <code>connection</code> is required but other parameters such as <code>email</code> and <code>password</code> are dependent upon the type of connection.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().create(
    CreateUserRequestContent
        .builder()
        .connection("connection")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**email:** `Optional<String>` — The user's email.
    
</dd>
</dl>

<dl>
<dd>

**phoneNumber:** `Optional<String>` — The user's phone number (following the E.164 recommendation).
    
</dd>
</dl>

<dl>
<dd>

**userMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**blocked:** `Optional<Boolean>` — Whether this user was blocked by an administrator (true) or not (false).
    
</dd>
</dl>

<dl>
<dd>

**emailVerified:** `Optional<Boolean>` — Whether this email address is verified (true) or unverified (false). User will receive a verification email after creation if `email_verified` is false or not specified
    
</dd>
</dl>

<dl>
<dd>

**phoneVerified:** `Optional<Boolean>` — Whether this phone number has been verified (true) or not (false).
    
</dd>
</dl>

<dl>
<dd>

**appMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**givenName:** `Optional<String>` — The user's given name(s).
    
</dd>
</dl>

<dl>
<dd>

**familyName:** `Optional<String>` — The user's family name(s).
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — The user's full name.
    
</dd>
</dl>

<dl>
<dd>

**nickname:** `Optional<String>` — The user's nickname.
    
</dd>
</dl>

<dl>
<dd>

**picture:** `Optional<String>` — A URI pointing to the user's picture.
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<String>` — The external user's id provided by the identity provider.
    
</dd>
</dl>

<dl>
<dd>

**connection:** `String` — Name of the connection this user should be created in.
    
</dd>
</dl>

<dl>
<dd>

**password:** `Optional<String>` — Initial password for this user. Only valid for auth0 connection strategy.
    
</dd>
</dl>

<dl>
<dd>

**verifyEmail:** `Optional<Boolean>` — Whether the user will receive a verification email after creation (true) or no email (false). Overrides behavior of `email_verified` parameter.
    
</dd>
</dl>

<dl>
<dd>

**username:** `Optional<String>` — The user's username. Only valid if the connection requires a username.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.listUsersByEmail() -> List&lt;UserResponseSchema&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Find users by email. If Auth0 is the identity provider (idP), the email address associated with a user is saved in lower case, regardless of how you initially provided it. 

For example, if you register a user as JohnSmith@example.com, Auth0 saves the user's email as johnsmith@example.com. 

Therefore, when using this endpoint, make sure that you are searching for users via email addresses using the correct case.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().listUsersByEmail(
    ListUsersByEmailRequestParameters
        .builder()
        .email("email")
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false). Defaults to true.
    
</dd>
</dl>

<dl>
<dd>

**email:** `String` — Email address to search for (case-sensitive).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.get(id) -> GetUserResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve user details. A list of fields to include or exclude may also be specified. For more information, see <a href="https://auth0.com/docs/manage-users/user-search/retrieve-users-with-get-users-endpoint">Retrieve Users with the Get Users Endpoint</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().get(
    "id",
    GetUserRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a user by user ID. This action cannot be undone. For Auth0 Dashboard instructions, see <a href="https://auth0.com/docs/manage-users/user-accounts/delete-users">Delete Users</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.update(id, request) -> UpdateUserResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a user.

These are the attributes that can be updated at the root level:

<ul>
    <li>app_metadata</li>
    <li>blocked</li>
    <li>email</li>
    <li>email_verified</li>
    <li>family_name</li>
    <li>given_name</li>
    <li>name</li>
    <li>nickname</li>
    <li>password</li>
    <li>phone_number</li>
    <li>phone_verified</li>
    <li>picture</li>
    <li>username</li>
    <li>user_metadata</li>
    <li>verify_email</li>
</ul>

Some considerations:
<ul>
    <li>The properties of the new object will replace the old ones.</li>
    <li>The metadata fields are an exception to this rule (<code>user_metadata</code> and <code>app_metadata</code>). These properties are merged instead of being replaced but be careful, the merge only occurs on the first level.</li>
    <li>If you are updating <code>email</code>, <code>email_verified</code>, <code>phone_number</code>, <code>phone_verified</code>, <code>username</code> or <code>password</code> of a secondary identity, you need to specify the <code>connection</code> property too.</li>
    <li>If you are updating <code>email</code> or <code>phone_number</code> you can specify, optionally, the <code>client_id</code> property.</li>
    <li>Updating <code>email_verified</code> is not supported for enterprise and passwordless sms connections.</li>
    <li>Updating the <code>blocked</code> to <code>false</code> does not affect the user's blocked state from an excessive amount of incorrectly provided credentials. Use the "Unblock a user" endpoint from the "User Blocks" API to change the user's state.</li>
    <li>Supported attributes can be unset by supplying <code>null</code> as the value.</li>
</ul>

<h5>Updating a field (non-metadata property)</h5>
To mark the email address of a user as verified, the body to send should be:
<pre><code>{ "email_verified": true }</code></pre>

<h5>Updating a user metadata root property</h5>Let's assume that our test user has the following <code>user_metadata</code>:
<pre><code>{ "user_metadata" : { "profileCode": 1479 } }</code></pre>

To add the field <code>addresses</code> the body to send should be:
<pre><code>{ "user_metadata" : { "addresses": {"work_address": "100 Industrial Way"} }}</code></pre>

The modified object ends up with the following <code>user_metadata</code> property:<pre><code>{
  "user_metadata": {
    "profileCode": 1479,
    "addresses": { "work_address": "100 Industrial Way" }
  }
}</code></pre>

<h5>Updating an inner user metadata property</h5>If there's existing user metadata to which we want to add  <code>"home_address": "742 Evergreen Terrace"</code> (using the <code>addresses</code> property) we should send the whole <code>addresses</code> object. Since this is a first-level object, the object will be merged in, but its own properties will not be. The body to send should be:
<pre><code>{
  "user_metadata": {
    "addresses": {
      "work_address": "100 Industrial Way",
      "home_address": "742 Evergreen Terrace"
    }
  }
}</code></pre>

The modified object ends up with the following <code>user_metadata</code> property:
<pre><code>{
  "user_metadata": {
    "profileCode": 1479,
    "addresses": {
      "work_address": "100 Industrial Way",
      "home_address": "742 Evergreen Terrace"
    }
  }
}</code></pre>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().update(
    "id",
    UpdateUserRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to update.
    
</dd>
</dl>

<dl>
<dd>

**blocked:** `Optional<Boolean>` — Whether this user was blocked by an administrator (true) or not (false).
    
</dd>
</dl>

<dl>
<dd>

**emailVerified:** `Optional<Boolean>` — Whether this email address is verified (true) or unverified (false). If set to false the user will not receive a verification email unless `verify_email` is set to true.
    
</dd>
</dl>

<dl>
<dd>

**email:** `Optional<String>` — Email address of this user.
    
</dd>
</dl>

<dl>
<dd>

**phoneNumber:** `Optional<String>` — The user's phone number (following the E.164 recommendation).
    
</dd>
</dl>

<dl>
<dd>

**phoneVerified:** `Optional<Boolean>` — Whether this phone number has been verified (true) or not (false).
    
</dd>
</dl>

<dl>
<dd>

**userMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**appMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**givenName:** `Optional<String>` — Given name/first name/forename of this user.
    
</dd>
</dl>

<dl>
<dd>

**familyName:** `Optional<String>` — Family name/last name/surname of this user.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Name of this user.
    
</dd>
</dl>

<dl>
<dd>

**nickname:** `Optional<String>` — Preferred nickname or alias of this user.
    
</dd>
</dl>

<dl>
<dd>

**picture:** `Optional<String>` — URL to picture, photo, or avatar of this user.
    
</dd>
</dl>

<dl>
<dd>

**verifyEmail:** `Optional<Boolean>` — Whether this user will receive a verification email after creation (true) or no email (false). Overrides behavior of `email_verified` parameter.
    
</dd>
</dl>

<dl>
<dd>

**verifyPhoneNumber:** `Optional<Boolean>` — Whether this user will receive a text after changing the phone number (true) or no text (false). Only valid when changing phone number for SMS connections.
    
</dd>
</dl>

<dl>
<dd>

**password:** `Optional<String>` — New password for this user. Only valid for database connections.
    
</dd>
</dl>

<dl>
<dd>

**connection:** `Optional<String>` — Name of the connection to target for this user update.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — Auth0 client ID. Only valid when updating email address.
    
</dd>
</dl>

<dl>
<dd>

**username:** `Optional<String>` — The user's username. Only valid if the connection requires a username.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.regenerateRecoveryCode(id) -> RegenerateUsersRecoveryCodeResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove an existing multi-factor authentication (MFA) <a href="https://auth0.com/docs/secure/multi-factor-authentication/reset-user-mfa">recovery code</a> and generate a new one. If a user cannot access the original device or account used for MFA enrollment, they can use a recovery code to authenticate. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().regenerateRecoveryCode("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to regenerate a multi-factor authentication recovery code for.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.revokeAccess(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Revokes selected resources related to a user (sessions, refresh tokens, ...).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().revokeAccess(
    "id",
    RevokeUserAccessRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user.
    
</dd>
</dl>

<dl>
<dd>

**sessionId:** `Optional<String>` — ID of the session to revoke.
    
</dd>
</dl>

<dl>
<dd>

**preserveRefreshTokens:** `Optional<Boolean>` — Whether to preserve the refresh tokens associated with the session.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Actions Versions
<details><summary><code>client.actions.versions.list(actionId) -> SyncPagingIterable&lt;ActionVersion&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all of an action's versions. An action version is created whenever an action is deployed. An action version is immutable, once created.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().versions().list(
    "actionId",
    ListActionVersionsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**actionId:** `String` — The ID of the action.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Use this field to request a specific page of the list results.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — This field specify the maximum number of results to be returned by the server. 20 by default
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.versions.get(actionId, id) -> GetActionVersionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a specific version of an action. An action version is created whenever an action is deployed. An action version is immutable, once created.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().versions().get("actionId", "id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**actionId:** `String` — The ID of the action.
    
</dd>
</dl>

<dl>
<dd>

**id:** `String` — The ID of the action version.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.versions.deploy(actionId, id, request) -> DeployActionVersionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Performs the equivalent of a roll-back of an action to an earlier, specified version. Creates a new, deployed action version that is identical to the specified version. If this action is currently bound to a trigger, the system will begin executing the newly-created version immediately.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().versions().deploy(
    "actionId",
    "id",
    OptionalNullable.absent()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**actionId:** `String` — The ID of an action.
    
</dd>
</dl>

<dl>
<dd>

**id:** `String` — The ID of an action version.
    
</dd>
</dl>

<dl>
<dd>

**request:** `Optional<DeployActionVersionRequestBodyParams>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Actions Executions
<details><summary><code>client.actions.executions.get(id) -> GetActionExecutionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve information about a specific execution of a trigger. Relevant execution IDs will be included in tenant logs generated as part of that authentication flow. Executions will only be stored for 10 days after their creation.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().executions().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the execution to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Actions Triggers
<details><summary><code>client.actions.triggers.list() -> ListActionTriggersResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the set of triggers currently available within actions. A trigger is an extensibility point to which actions can be bound.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().triggers().list();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Actions Triggers Bindings
<details><summary><code>client.actions.triggers.bindings.list(triggerId) -> SyncPagingIterable&lt;ActionBinding&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the actions that are bound to a trigger. Once an action is created and deployed, it must be attached (i.e. bound) to a trigger so that it will be executed as part of a flow. The list of actions returned reflects the order in which they will be executed during the appropriate flow.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().triggers().bindings().list(
    "triggerId",
    ListActionTriggerBindingsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**triggerId:** `String` — An actions extensibility point.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Use this field to request a specific page of the list results.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — The maximum number of results to be returned in a single request. 20 by default
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.actions.triggers.bindings.updateMany(triggerId, request) -> UpdateActionBindingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the actions that are bound (i.e. attached) to a trigger. Once an action is created and deployed, it must be attached (i.e. bound) to a trigger so that it will be executed as part of a flow. The order in which the actions are provided will determine the order in which they are executed.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.actions().triggers().bindings().updateMany(
    "triggerId",
    UpdateActionBindingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**triggerId:** `String` — An actions extensibility point.
    
</dd>
</dl>

<dl>
<dd>

**bindings:** `Optional<List<ActionBindingWithRef>>` — The actions that will be bound to this trigger. The order in which they are included will be the order in which they are executed.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Anomaly Blocks
<details><summary><code>client.anomaly.blocks.checkIp(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Check if the given IP address is blocked via the <a href="https://auth0.com/docs/configure/attack-protection/suspicious-ip-throttling">Suspicious IP Throttling</a> due to multiple suspicious attempts.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.anomaly().blocks().checkIp("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — IP address to check.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.anomaly.blocks.unblockIp(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove a block imposed by <a href="https://auth0.com/docs/configure/attack-protection/suspicious-ip-throttling">Suspicious IP Throttling</a> for the given IP address.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.anomaly().blocks().unblockIp("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — IP address to unblock.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## AttackProtection BotDetection
<details><summary><code>client.attackProtection.botDetection.get() -> GetBotDetectionSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get the Bot Detection configuration of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().botDetection().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.attackProtection.botDetection.update(request) -> UpdateBotDetectionSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the Bot Detection configuration of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().botDetection().update(
    UpdateBotDetectionSettingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**botDetectionLevel:** `Optional<BotDetectionLevelEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**challengePasswordPolicy:** `Optional<BotDetectionChallengePolicyPasswordFlowEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**challengePasswordlessPolicy:** `Optional<BotDetectionChallengePolicyPasswordlessFlowEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**challengePasswordResetPolicy:** `Optional<BotDetectionChallengePolicyPasswordResetFlowEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**allowlist:** `Optional<List<String>>` 
    
</dd>
</dl>

<dl>
<dd>

**monitoringModeEnabled:** `Optional<Boolean>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## AttackProtection BreachedPasswordDetection
<details><summary><code>client.attackProtection.breachedPasswordDetection.get() -> GetBreachedPasswordDetectionSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the Breached Password Detection configuration of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().breachedPasswordDetection().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.attackProtection.breachedPasswordDetection.update(request) -> UpdateBreachedPasswordDetectionSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update details of the Breached Password Detection configuration of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().breachedPasswordDetection().update(
    UpdateBreachedPasswordDetectionSettingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether or not breached password detection is active.
    
</dd>
</dl>

<dl>
<dd>

**shields:** `Optional<List<BreachedPasswordDetectionShieldsEnum>>` 

Action to take when a breached password is detected during a login.
      Possible values: <code>block</code>, <code>user_notification</code>, <code>admin_notification</code>.
    
</dd>
</dl>

<dl>
<dd>

**adminNotificationFrequency:** `Optional<List<BreachedPasswordDetectionAdminNotificationFrequencyEnum>>` 

When "admin_notification" is enabled, determines how often email notifications are sent.
        Possible values: <code>immediately</code>, <code>daily</code>, <code>weekly</code>, <code>monthly</code>.
    
</dd>
</dl>

<dl>
<dd>

**method:** `Optional<BreachedPasswordDetectionMethodEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**stage:** `Optional<BreachedPasswordDetectionStage>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## AttackProtection BruteForceProtection
<details><summary><code>client.attackProtection.bruteForceProtection.get() -> GetBruteForceSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the Brute-force Protection configuration of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().bruteForceProtection().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.attackProtection.bruteForceProtection.update(request) -> UpdateBruteForceSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the Brute-force Protection configuration of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().bruteForceProtection().update(
    UpdateBruteForceSettingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether or not brute force attack protections are active.
    
</dd>
</dl>

<dl>
<dd>

**shields:** `Optional<List<UpdateBruteForceSettingsRequestContentShieldsItem>>` 

Action to take when a brute force protection threshold is violated.
        Possible values: <code>block</code>, <code>user_notification</code>.
    
</dd>
</dl>

<dl>
<dd>

**allowlist:** `Optional<List<String>>` — List of trusted IP addresses that will not have attack protection enforced against them.
    
</dd>
</dl>

<dl>
<dd>

**mode:** `Optional<UpdateBruteForceSettingsRequestContentMode>` 

Account Lockout: Determines whether or not IP address is used when counting failed attempts.
          Possible values: <code>count_per_identifier_and_ip</code>, <code>count_per_identifier</code>.
    
</dd>
</dl>

<dl>
<dd>

**maxAttempts:** `Optional<Integer>` — Maximum number of unsuccessful attempts.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## AttackProtection Captcha
<details><summary><code>client.attackProtection.captcha.get() -> GetAttackProtectionCaptchaResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get the CAPTCHA configuration for your client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().captcha().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.attackProtection.captcha.update(request) -> UpdateAttackProtectionCaptchaResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update existing CAPTCHA configuration for your client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().captcha().update(
    UpdateAttackProtectionCaptchaRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**activeProviderId:** `Optional<AttackProtectionCaptchaProviderId>` 
    
</dd>
</dl>

<dl>
<dd>

**arkose:** `Optional<AttackProtectionUpdateCaptchaArkose>` 
    
</dd>
</dl>

<dl>
<dd>

**authChallenge:** `Optional<AttackProtectionCaptchaAuthChallengeRequest>` 
    
</dd>
</dl>

<dl>
<dd>

**hcaptcha:** `Optional<AttackProtectionUpdateCaptchaHcaptcha>` 
    
</dd>
</dl>

<dl>
<dd>

**friendlyCaptcha:** `Optional<AttackProtectionUpdateCaptchaFriendlyCaptcha>` 
    
</dd>
</dl>

<dl>
<dd>

**recaptchaEnterprise:** `Optional<AttackProtectionUpdateCaptchaRecaptchaEnterprise>` 
    
</dd>
</dl>

<dl>
<dd>

**recaptchaV2:** `Optional<AttackProtectionUpdateCaptchaRecaptchaV2>` 
    
</dd>
</dl>

<dl>
<dd>

**simpleCaptcha:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## AttackProtection SuspiciousIpThrottling
<details><summary><code>client.attackProtection.suspiciousIpThrottling.get() -> GetSuspiciousIpThrottlingSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the Suspicious IP Throttling configuration of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().suspiciousIpThrottling().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.attackProtection.suspiciousIpThrottling.update(request) -> UpdateSuspiciousIpThrottlingSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the details of the Suspicious IP Throttling configuration of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.attackProtection().suspiciousIpThrottling().update(
    UpdateSuspiciousIpThrottlingSettingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether or not suspicious IP throttling attack protections are active.
    
</dd>
</dl>

<dl>
<dd>

**shields:** `Optional<List<SuspiciousIpThrottlingShieldsEnum>>` 

Action to take when a suspicious IP throttling threshold is violated.
          Possible values: <code>block</code>, <code>admin_notification</code>.
    
</dd>
</dl>

<dl>
<dd>

**allowlist:** `Optional<List<String>>` 
    
</dd>
</dl>

<dl>
<dd>

**stage:** `Optional<SuspiciousIpThrottlingStage>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Branding Templates
<details><summary><code>client.branding.templates.getUniversalLogin() -> GetUniversalLoginTemplateResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().templates().getUniversalLogin();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.templates.updateUniversalLogin(request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the Universal Login branding template.

<p>When <code>content-type</code> header is set to <code>application/json</code>:</p>
<pre>
{
  "template": "&lt;!DOCTYPE html&gt;{% assign resolved_dir = dir | default: "auto" %}&lt;html lang="{{locale}}" dir="{{resolved_dir}}"&gt;&lt;head&gt;{%- auth0:head -%}&lt;/head&gt;&lt;body class="_widget-auto-layout"&gt;{%- auth0:widget -%}&lt;/body&gt;&lt;/html&gt;"
}
</pre>

<p>
  When <code>content-type</code> header is set to <code>text/html</code>:
</p>
<pre>
&lt!DOCTYPE html&gt;
{% assign resolved_dir = dir | default: "auto" %}
&lt;html lang="{{locale}}" dir="{{resolved_dir}}"&gt;
  &lt;head&gt;
    {%- auth0:head -%}
  &lt;/head&gt;
  &lt;body class="_widget-auto-layout"&gt;
    {%- auth0:widget -%}
  &lt;/body&gt;
&lt;/html&gt;
</pre>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().templates().updateUniversalLogin(
    UpdateUniversalLoginTemplateRequestContent.of("string")
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `UpdateUniversalLoginTemplateRequestContent` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.templates.deleteUniversalLogin()</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().templates().deleteUniversalLogin();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Branding Themes
<details><summary><code>client.branding.themes.create(request) -> CreateBrandingThemeResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create branding theme.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().themes().create(
    CreateBrandingThemeRequestContent
        .builder()
        .borders(
            BrandingThemeBorders
                .builder()
                .buttonBorderRadius(1.1)
                .buttonBorderWeight(1.1)
                .buttonsStyle(BrandingThemeBordersButtonsStyleEnum.PILL)
                .inputBorderRadius(1.1)
                .inputBorderWeight(1.1)
                .inputsStyle(BrandingThemeBordersInputsStyleEnum.PILL)
                .showWidgetShadow(true)
                .widgetBorderWeight(1.1)
                .widgetCornerRadius(1.1)
                .build()
        )
        .colors(
            BrandingThemeColors
                .builder()
                .bodyText("body_text")
                .error("error")
                .header("header")
                .icons("icons")
                .inputBackground("input_background")
                .inputBorder("input_border")
                .inputFilledText("input_filled_text")
                .inputLabelsPlaceholders("input_labels_placeholders")
                .linksFocusedComponents("links_focused_components")
                .primaryButton("primary_button")
                .primaryButtonLabel("primary_button_label")
                .secondaryButtonBorder("secondary_button_border")
                .secondaryButtonLabel("secondary_button_label")
                .success("success")
                .widgetBackground("widget_background")
                .widgetBorder("widget_border")
                .build()
        )
        .fonts(
            BrandingThemeFonts
                .builder()
                .bodyText(
                    BrandingThemeFontBodyText
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .buttonsText(
                    BrandingThemeFontButtonsText
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .fontUrl("font_url")
                .inputLabels(
                    BrandingThemeFontInputLabels
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .links(
                    BrandingThemeFontLinks
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .linksStyle(BrandingThemeFontLinksStyleEnum.NORMAL)
                .referenceTextSize(1.1)
                .subtitle(
                    BrandingThemeFontSubtitle
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .title(
                    BrandingThemeFontTitle
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .build()
        )
        .pageBackground(
            BrandingThemePageBackground
                .builder()
                .backgroundColor("background_color")
                .backgroundImageUrl("background_image_url")
                .pageLayout(BrandingThemePageBackgroundPageLayoutEnum.CENTER)
                .build()
        )
        .widget(
            BrandingThemeWidget
                .builder()
                .headerTextAlignment(BrandingThemeWidgetHeaderTextAlignmentEnum.CENTER)
                .logoHeight(1.1)
                .logoPosition(BrandingThemeWidgetLogoPositionEnum.CENTER)
                .logoUrl("logo_url")
                .socialButtonsLayout(BrandingThemeWidgetSocialButtonsLayoutEnum.BOTTOM)
                .build()
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**borders:** `BrandingThemeBorders` 
    
</dd>
</dl>

<dl>
<dd>

**colors:** `BrandingThemeColors` 
    
</dd>
</dl>

<dl>
<dd>

**displayName:** `Optional<String>` — Display Name
    
</dd>
</dl>

<dl>
<dd>

**fonts:** `BrandingThemeFonts` 
    
</dd>
</dl>

<dl>
<dd>

**pageBackground:** `BrandingThemePageBackground` 
    
</dd>
</dl>

<dl>
<dd>

**widget:** `BrandingThemeWidget` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.themes.getDefault() -> GetBrandingDefaultThemeResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve default branding theme.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().themes().getDefault();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.themes.get(themeId) -> GetBrandingThemeResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve branding theme.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().themes().get("themeId");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**themeId:** `String` — The ID of the theme
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.themes.delete(themeId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete branding theme.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().themes().delete("themeId");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**themeId:** `String` — The ID of the theme
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.themes.update(themeId, request) -> UpdateBrandingThemeResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update branding theme.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().themes().update(
    "themeId",
    UpdateBrandingThemeRequestContent
        .builder()
        .borders(
            BrandingThemeBorders
                .builder()
                .buttonBorderRadius(1.1)
                .buttonBorderWeight(1.1)
                .buttonsStyle(BrandingThemeBordersButtonsStyleEnum.PILL)
                .inputBorderRadius(1.1)
                .inputBorderWeight(1.1)
                .inputsStyle(BrandingThemeBordersInputsStyleEnum.PILL)
                .showWidgetShadow(true)
                .widgetBorderWeight(1.1)
                .widgetCornerRadius(1.1)
                .build()
        )
        .colors(
            BrandingThemeColors
                .builder()
                .bodyText("body_text")
                .error("error")
                .header("header")
                .icons("icons")
                .inputBackground("input_background")
                .inputBorder("input_border")
                .inputFilledText("input_filled_text")
                .inputLabelsPlaceholders("input_labels_placeholders")
                .linksFocusedComponents("links_focused_components")
                .primaryButton("primary_button")
                .primaryButtonLabel("primary_button_label")
                .secondaryButtonBorder("secondary_button_border")
                .secondaryButtonLabel("secondary_button_label")
                .success("success")
                .widgetBackground("widget_background")
                .widgetBorder("widget_border")
                .build()
        )
        .fonts(
            BrandingThemeFonts
                .builder()
                .bodyText(
                    BrandingThemeFontBodyText
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .buttonsText(
                    BrandingThemeFontButtonsText
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .fontUrl("font_url")
                .inputLabels(
                    BrandingThemeFontInputLabels
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .links(
                    BrandingThemeFontLinks
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .linksStyle(BrandingThemeFontLinksStyleEnum.NORMAL)
                .referenceTextSize(1.1)
                .subtitle(
                    BrandingThemeFontSubtitle
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .title(
                    BrandingThemeFontTitle
                        .builder()
                        .bold(true)
                        .size(1.1)
                        .build()
                )
                .build()
        )
        .pageBackground(
            BrandingThemePageBackground
                .builder()
                .backgroundColor("background_color")
                .backgroundImageUrl("background_image_url")
                .pageLayout(BrandingThemePageBackgroundPageLayoutEnum.CENTER)
                .build()
        )
        .widget(
            BrandingThemeWidget
                .builder()
                .headerTextAlignment(BrandingThemeWidgetHeaderTextAlignmentEnum.CENTER)
                .logoHeight(1.1)
                .logoPosition(BrandingThemeWidgetLogoPositionEnum.CENTER)
                .logoUrl("logo_url")
                .socialButtonsLayout(BrandingThemeWidgetSocialButtonsLayoutEnum.BOTTOM)
                .build()
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**themeId:** `String` — The ID of the theme
    
</dd>
</dl>

<dl>
<dd>

**borders:** `BrandingThemeBorders` 
    
</dd>
</dl>

<dl>
<dd>

**colors:** `BrandingThemeColors` 
    
</dd>
</dl>

<dl>
<dd>

**displayName:** `Optional<String>` — Display Name
    
</dd>
</dl>

<dl>
<dd>

**fonts:** `BrandingThemeFonts` 
    
</dd>
</dl>

<dl>
<dd>

**pageBackground:** `BrandingThemePageBackground` 
    
</dd>
</dl>

<dl>
<dd>

**widget:** `BrandingThemeWidget` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Branding Phone Providers
<details><summary><code>client.branding.phone.providers.list() -> ListBrandingPhoneProvidersResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a list of <a href="https://auth0.com/docs/customize/phone-messages/configure-phone-messaging-providers">phone providers</a> details set for a Tenant. A list of fields to include or exclude may also be specified.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().providers().list(
    ListBrandingPhoneProvidersRequestParameters
        .builder()
        .disabled(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**disabled:** `Optional<Boolean>` — Whether the provider is enabled (false) or disabled (true).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.providers.create(request) -> CreateBrandingPhoneProviderResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a <a href="https://auth0.com/docs/customize/phone-messages/configure-phone-messaging-providers">phone provider</a>.
The <code>credentials</code> object requires different properties depending on the phone provider (which is specified using the <code>name</code> property).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().providers().create(
    CreateBrandingPhoneProviderRequestContent
        .builder()
        .name(PhoneProviderNameEnum.TWILIO)
        .credentials(
            PhoneProviderCredentials.of(
                TwilioProviderCredentials
                    .builder()
                    .authToken("auth_token")
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `PhoneProviderNameEnum` 
    
</dd>
</dl>

<dl>
<dd>

**disabled:** `Optional<Boolean>` — Whether the provider is enabled (false) or disabled (true).
    
</dd>
</dl>

<dl>
<dd>

**configuration:** `Optional<PhoneProviderConfiguration>` 
    
</dd>
</dl>

<dl>
<dd>

**credentials:** `PhoneProviderCredentials` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.providers.get(id) -> GetBrandingPhoneProviderResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve <a href="https://auth0.com/docs/customize/phone-messages/configure-phone-messaging-providers">phone provider</a> details. A list of fields to include or exclude may also be specified.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().providers().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.providers.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete the configured phone provider.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().providers().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.providers.update(id, request) -> UpdateBrandingPhoneProviderResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a <a href="https://auth0.com/docs/customize/phone-messages/configure-phone-messaging-providers">phone provider</a>.
The <code>credentials</code> object requires different properties depending on the phone provider (which is specified using the <code>name</code> property).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().providers().update(
    "id",
    UpdateBrandingPhoneProviderRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<PhoneProviderNameEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**disabled:** `Optional<Boolean>` — Whether the provider is enabled (false) or disabled (true).
    
</dd>
</dl>

<dl>
<dd>

**credentials:** `Optional<PhoneProviderCredentials>` 
    
</dd>
</dl>

<dl>
<dd>

**configuration:** `Optional<PhoneProviderConfiguration>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.providers.test(id, request) -> CreatePhoneProviderSendTestResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().providers().test(
    "id",
    CreatePhoneProviderSendTestRequestContent
        .builder()
        .to("to")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**to:** `String` — The recipient phone number to receive a given notification.
    
</dd>
</dl>

<dl>
<dd>

**deliveryMethod:** `Optional<PhoneProviderDeliveryMethodEnum>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Branding Phone Templates
<details><summary><code>client.branding.phone.templates.list() -> ListPhoneTemplatesResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().templates().list(
    ListPhoneTemplatesRequestParameters
        .builder()
        .disabled(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**disabled:** `Optional<Boolean>` — Whether the template is enabled (false) or disabled (true).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.templates.create(request) -> CreatePhoneTemplateResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().templates().create(
    CreatePhoneTemplateRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**type:** `Optional<PhoneTemplateNotificationTypeEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**disabled:** `Optional<Boolean>` — Whether the template is enabled (false) or disabled (true).
    
</dd>
</dl>

<dl>
<dd>

**content:** `Optional<PhoneTemplateContent>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.templates.get(id) -> GetPhoneTemplateResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().templates().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.templates.delete(id)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().templates().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.templates.update(id, request) -> UpdatePhoneTemplateResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().templates().update(
    "id",
    UpdatePhoneTemplateRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**content:** `Optional<PartialPhoneTemplateContent>` 
    
</dd>
</dl>

<dl>
<dd>

**disabled:** `Optional<Boolean>` — Whether the template is enabled (false) or disabled (true).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.templates.reset(id, request) -> ResetPhoneTemplateResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().templates().reset("id", new 
HashMap<String, Object>() {{put("key", "value");
}});
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**request:** `Object` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.branding.phone.templates.test(id, request) -> CreatePhoneTemplateTestNotificationResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.branding().phone().templates().test(
    "id",
    CreatePhoneTemplateTestNotificationRequestContent
        .builder()
        .to("to")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**to:** `String` — Destination of the testing phone notification
    
</dd>
</dl>

<dl>
<dd>

**deliveryMethod:** `Optional<PhoneProviderDeliveryMethodEnum>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## ClientGrants Organizations
<details><summary><code>client.clientGrants.organizations.list(id) -> SyncPagingIterable&lt;Organization&gt;</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clientGrants().organizations().list(
    "id",
    ListClientGrantOrganizationsRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the client grant
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Clients Credentials
<details><summary><code>client.clients.credentials.list(clientId) -> List&lt;ClientCredential&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get the details of a client credential.

<b>Important</b>: To enable credentials to be used for a client authentication method, set the <code>client_authentication_methods</code> property on the client. To enable credentials to be used for JWT-Secured Authorization requests set the <code>signed_request_object</code> property on the client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().credentials().list("client_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**clientId:** `String` — ID of the client.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.credentials.create(clientId, request) -> PostClientCredentialResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a client credential associated to your application. Credentials can be used to configure Private Key JWT and mTLS authentication methods, as well as for JWT-secured Authorization requests.

<h5>Public Key</h5>Public Key credentials can be used to set up Private Key JWT client authentication and JWT-secured Authorization requests.

Sample: <pre><code>{
  "credential_type": "public_key",
  "name": "string",
  "pem": "string",
  "alg": "RS256",
  "parse_expiry_from_cert": false,
  "expires_at": "2022-12-31T23:59:59Z"
}</code></pre>
<h5>Certificate (CA-signed & self-signed)</h5>Certificate credentials can be used to set up mTLS client authentication. CA-signed certificates can be configured either with a signed certificate or with just the certificate Subject DN.

CA-signed Certificate Sample (pem): <pre><code>{
  "credential_type": "x509_cert",
  "name": "string",
  "pem": "string"
}</code></pre>CA-signed Certificate Sample (subject_dn): <pre><code>{
  "credential_type": "cert_subject_dn",
  "name": "string",
  "subject_dn": "string"
}</code></pre>Self-signed Certificate Sample: <pre><code>{
  "credential_type": "cert_subject_dn",
  "name": "string",
  "pem": "string"
}</code></pre>

The credential will be created but not yet enabled for use until you set the corresponding properties in the client:
<ul>
  <li>To enable the credential for Private Key JWT or mTLS authentication methods, set the <code>client_authentication_methods</code> property on the client. For more information, read <a href="https://auth0.com/docs/get-started/applications/configure-private-key-jwt">Configure Private Key JWT Authentication</a> and <a href="https://auth0.com/docs/get-started/applications/configure-mtls">Configure mTLS Authentication</a></li>
  <li>To enable the credential for JWT-secured Authorization requests, set the <code>signed_request_object</code>property on the client. For more information, read <a href="https://auth0.com/docs/get-started/applications/configure-jar">Configure JWT-secured Authorization Requests (JAR)</a></li>
</ul>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().credentials().create(
    "client_id",
    PostClientCredentialRequestContent
        .builder()
        .credentialType(ClientCredentialTypeEnum.PUBLIC_KEY)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**clientId:** `String` — ID of the client.
    
</dd>
</dl>

<dl>
<dd>

**credentialType:** `ClientCredentialTypeEnum` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Friendly name for a credential.
    
</dd>
</dl>

<dl>
<dd>

**subjectDn:** `Optional<String>` — Subject Distinguished Name. Mutually exclusive with `pem` property. Applies to `cert_subject_dn` credential type.
    
</dd>
</dl>

<dl>
<dd>

**pem:** `Optional<String>` — PEM-formatted public key (SPKI and PKCS1) or X509 certificate. Must be JSON escaped.
    
</dd>
</dl>

<dl>
<dd>

**alg:** `Optional<PublicKeyCredentialAlgorithmEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**parseExpiryFromCert:** `Optional<Boolean>` — Parse expiry from x509 certificate. If true, attempts to parse the expiry date from the provided PEM. Applies to `public_key` credential type.
    
</dd>
</dl>

<dl>
<dd>

**expiresAt:** `Optional<OffsetDateTime>` — The ISO 8601 formatted date representing the expiration of the credential. If not specified (not recommended), the credential never expires. Applies to `public_key` credential type.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.credentials.get(clientId, credentialId) -> GetClientCredentialResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get the details of a client credential.

<b>Important</b>: To enable credentials to be used for a client authentication method, set the <code>client_authentication_methods</code> property on the client. To enable credentials to be used for JWT-Secured Authorization requests set the <code>signed_request_object</code> property on the client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().credentials().get("client_id", "credential_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**clientId:** `String` — ID of the client.
    
</dd>
</dl>

<dl>
<dd>

**credentialId:** `String` — ID of the credential.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.credentials.delete(clientId, credentialId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a client credential you previously created. May be enabled or disabled. For more information, read <a href="https://www.auth0.com/docs/get-started/authentication-and-authorization-flow/client-credentials-flow">Client Credential Flow</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().credentials().delete("client_id", "credential_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**clientId:** `String` — ID of the client.
    
</dd>
</dl>

<dl>
<dd>

**credentialId:** `String` — ID of the credential to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.clients.credentials.update(clientId, credentialId, request) -> PatchClientCredentialResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Change a client credential you previously created. May be enabled or disabled. For more information, read <a href="https://www.auth0.com/docs/get-started/authentication-and-authorization-flow/client-credentials-flow">Client Credential Flow</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().credentials().update(
    "client_id",
    "credential_id",
    PatchClientCredentialRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**clientId:** `String` — ID of the client.
    
</dd>
</dl>

<dl>
<dd>

**credentialId:** `String` — ID of the credential.
    
</dd>
</dl>

<dl>
<dd>

**expiresAt:** `Optional<OffsetDateTime>` — The ISO 8601 formatted date representing the expiration of the credential.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Clients Connections
<details><summary><code>client.clients.connections.get(id) -> SyncPagingIterable&lt;ConnectionForList&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all connections that are enabled for the specified <a href="https://www.auth0.com/docs/get-started/applications"> Application</a>, using checkpoint pagination. A list of fields to include or exclude for each connection may also be specified.
<ul>
  <li>
    This endpoint requires the <code>read:connections</code> scope and any one of <code>read:clients</code> or <code>read:client_summary</code>.
  </li>
  <li>
    <b>Note</b>: The first time you call this endpoint, omit the <code>from</code> parameter. If there are more results, a <code>next</code> value is included in the response. You can use this for subsequent API calls. When <code>next</code> is no longer included in the response, no further results are remaining.
  </li>
</ul>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.clients().connections().get(
    "id",
    ConnectionsGetRequest
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the client for which to retrieve enabled connections.
    
</dd>
</dl>

<dl>
<dd>

**strategy:** `Optional<ConnectionStrategyEnum>` — Provide strategies to only retrieve connections with such strategies
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — A comma separated list of fields to include or exclude (depending on include_fields) from the result, empty to retrieve all fields
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — <code>true</code> if the fields specified are to be included in the result, <code>false</code> otherwise (defaults to <code>true</code>)
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Connections Clients
<details><summary><code>client.connections.clients.get(id) -> SyncPagingIterable&lt;ConnectionEnabledClient&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all clients that have the specified <a href="https://auth0.com/docs/authenticate/identity-providers">connection</a> enabled.

<b>Note</b>: The first time you call this endpoint, omit the <code>from</code> parameter. If there are more results, a <code>next</code> value is included in the response. You can use this for subsequent API calls. When <code>next</code> is no longer included in the response, no further results are remaining.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().clients().get(
    "id",
    GetConnectionEnabledClientsRequestParameters
        .builder()
        .take(
            OptionalNullable.of(1)
        )
        .from(
            OptionalNullable.of("from")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection for which enabled clients are to be retrieved
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.clients.update(id, request)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().clients().update(
    "id",
    Arrays.asList(
        UpdateEnabledClientConnectionsRequestContentItem
            .builder()
            .clientId("client_id")
            .status(true)
            .build()
    )
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to modify
    
</dd>
</dl>

<dl>
<dd>

**request:** `List<UpdateEnabledClientConnectionsRequestContentItem>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Connections DirectoryProvisioning
<details><summary><code>client.connections.directoryProvisioning.get(id) -> GetDirectoryProvisioningResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the directory provisioning configuration of a connection.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().directoryProvisioning().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to retrieve its directory provisioning configuration
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.directoryProvisioning.create(id, request) -> CreateDirectoryProvisioningResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a directory provisioning configuration for a connection.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().directoryProvisioning().create(
    "id",
    OptionalNullable.absent()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to create its directory provisioning configuration
    
</dd>
</dl>

<dl>
<dd>

**request:** `Optional<CreateDirectoryProvisioningRequestContent>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.directoryProvisioning.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete the directory provisioning configuration of a connection.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().directoryProvisioning().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to delete its directory provisioning configuration
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.directoryProvisioning.update(id, request) -> UpdateDirectoryProvisioningResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the directory provisioning configuration of a connection.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().directoryProvisioning().update(
    "id",
    OptionalNullable.absent()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to create its directory provisioning configuration
    
</dd>
</dl>

<dl>
<dd>

**request:** `Optional<UpdateDirectoryProvisioningRequestContent>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.directoryProvisioning.getDefaultMapping(id) -> GetDirectoryProvisioningDefaultMappingResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the directory provisioning default attribute mapping of a connection.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().directoryProvisioning().getDefaultMapping("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to retrieve its directory provisioning configuration
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Connections Keys
<details><summary><code>client.connections.keys.get(id) -> List&lt;ConnectionKey&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Gets the connection keys for the Okta or OIDC connection strategy.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().keys().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the connection
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.keys.rotate(id, request) -> RotateConnectionsKeysResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Rotates the connection keys for the Okta or OIDC connection strategies.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().keys().rotate(
    "id",
    OptionalNullable.absent()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the connection
    
</dd>
</dl>

<dl>
<dd>

**request:** `Optional<RotateConnectionKeysRequestContent>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Connections ScimConfiguration
<details><summary><code>client.connections.scimConfiguration.get(id) -> GetScimConfigurationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves a scim configuration by its <code>connectionId</code>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().scimConfiguration().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to retrieve its SCIM configuration
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.scimConfiguration.create(id, request) -> CreateScimConfigurationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a scim configuration for a connection.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().scimConfiguration().create(
    "id",
    OptionalNullable.absent()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to create its SCIM configuration
    
</dd>
</dl>

<dl>
<dd>

**request:** `Optional<CreateScimConfigurationRequestContent>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.scimConfiguration.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Deletes a scim configuration by its <code>connectionId</code>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().scimConfiguration().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to delete its SCIM configuration
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.scimConfiguration.update(id, request) -> UpdateScimConfigurationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a scim configuration by its <code>connectionId</code>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().scimConfiguration().update(
    "id",
    UpdateScimConfigurationRequestContent
        .builder()
        .userIdAttribute("user_id_attribute")
        .mapping(
            Arrays.asList(
                ScimMappingItem
                    .builder()
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to update its SCIM configuration
    
</dd>
</dl>

<dl>
<dd>

**userIdAttribute:** `String` — User ID attribute for generating unique user ids
    
</dd>
</dl>

<dl>
<dd>

**mapping:** `List<ScimMappingItem>` — The mapping between auth0 and SCIM
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.scimConfiguration.getDefaultMapping(id) -> GetScimConfigurationDefaultMappingResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves a scim configuration's default mapping by its <code>connectionId</code>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().scimConfiguration().getDefaultMapping("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to retrieve its default SCIM mapping
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Connections Users
<details><summary><code>client.connections.users.deleteByEmail(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Deletes a specified connection user by its email (you cannot delete all users from specific connection). Currently, only Database Connections are supported.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().users().deleteByEmail(
    "id",
    DeleteConnectionUsersByEmailQueryParameters
        .builder()
        .email("email")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection (currently only database connections are supported)
    
</dd>
</dl>

<dl>
<dd>

**email:** `String` — The email of the user to delete
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Connections DirectoryProvisioning Synchronizations
<details><summary><code>client.connections.directoryProvisioning.synchronizations.create(id) -> CreateDirectorySynchronizationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Request an on-demand synchronization of the directory.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().directoryProvisioning().synchronizations().create("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to trigger synchronization for
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Connections ScimConfiguration Tokens
<details><summary><code>client.connections.scimConfiguration.tokens.get(id) -> List&lt;ScimTokenItem&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves all scim tokens by its connection <code>id</code>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().scimConfiguration().tokens().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to retrieve its SCIM configuration
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.scimConfiguration.tokens.create(id, request) -> CreateScimTokenResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a scim token for a scim client.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().scimConfiguration().tokens().create(
    "id",
    CreateScimTokenRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the connection to create its SCIM token
    
</dd>
</dl>

<dl>
<dd>

**scopes:** `Optional<List<String>>` — The scopes of the scim token
    
</dd>
</dl>

<dl>
<dd>

**tokenLifetime:** `Optional<Integer>` — Lifetime of the token in seconds. Must be greater than 900
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.connections.scimConfiguration.tokens.delete(id, tokenId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Deletes a scim token by its connection <code>id</code> and <code>tokenId</code>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.connections().scimConfiguration().tokens().delete("id", "tokenId");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The connection id that owns the SCIM token to delete
    
</dd>
</dl>

<dl>
<dd>

**tokenId:** `String` — The id of the scim token to delete
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Emails Provider
<details><summary><code>client.emails.provider.get() -> GetEmailProviderResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the <a href="https://auth0.com/docs/customize/email/smtp-email-providers">email provider configuration</a> in your tenant. A list of fields to include or exclude may also be specified.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.emails().provider().get(
    GetEmailProviderRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (dependent upon include_fields) from the result. Leave empty to retrieve `name` and `enabled`. Additional fields available include `credentials`, `default_from_address`, and `settings`.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.emails.provider.create(request) -> CreateEmailProviderResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create an <a href="https://auth0.com/docs/email/providers">email provider</a>. The <code>credentials</code> object
requires different properties depending on the email provider (which is specified using the <code>name</code> property):
<ul>
  <li><code>mandrill</code> requires <code>api_key</code></li>
  <li><code>sendgrid</code> requires <code>api_key</code></li>
  <li>
    <code>sparkpost</code> requires <code>api_key</code>. Optionally, set <code>region</code> to <code>eu</code> to use
    the SparkPost service hosted in Western Europe; set to <code>null</code> to use the SparkPost service hosted in
    North America. <code>eu</code> or <code>null</code> are the only valid values for <code>region</code>.
  </li>
  <li>
    <code>mailgun</code> requires <code>api_key</code> and <code>domain</code>. Optionally, set <code>region</code> to
    <code>eu</code> to use the Mailgun service hosted in Europe; set to <code>null</code> otherwise. <code>eu</code> or
    <code>null</code> are the only valid values for <code>region</code>.
  </li>
  <li><code>ses</code> requires <code>accessKeyId</code>, <code>secretAccessKey</code>, and <code>region</code></li>
  <li>
    <code>smtp</code> requires <code>smtp_host</code>, <code>smtp_port</code>, <code>smtp_user</code>, and
    <code>smtp_pass</code>
  </li>
</ul>
Depending on the type of provider it is possible to specify <code>settings</code> object with different configuration
options, which will be used when sending an email:
<ul>
  <li>
    <code>smtp</code> provider, <code>settings</code> may contain <code>headers</code> object.
    <ul>
      <li>
        When using AWS SES SMTP host, you may provide a name of configuration set in
        <code>X-SES-Configuration-Set</code> header. Value must be a string.
      </li>
      <li>
        When using Sparkpost host, you may provide value for
        <code>X-MSYS_API</code> header. Value must be an object.
      </li>
    </ul>
  </li>
  <li>
    for <code>ses</code> provider, <code>settings</code> may contain <code>message</code> object, where you can provide
    a name of configuration set in <code>configuration_set_name</code> property. Value must be a string.
  </li>
</ul>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.emails().provider().create(
    CreateEmailProviderRequestContent
        .builder()
        .name(EmailProviderNameEnum.MAILGUN)
        .credentials(
            EmailProviderCredentialsSchema.of(
                EmailProviderCredentialsSchemaZero
                    .builder()
                    .apiKey("api_key")
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `EmailProviderNameEnum` 
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether the provider is enabled (true) or disabled (false).
    
</dd>
</dl>

<dl>
<dd>

**defaultFromAddress:** `Optional<String>` — Email address to use as "from" when no other address specified.
    
</dd>
</dl>

<dl>
<dd>

**credentials:** `EmailProviderCredentialsSchema` 
    
</dd>
</dl>

<dl>
<dd>

**settings:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.emails.provider.delete()</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete the email provider.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.emails().provider().delete();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.emails.provider.update(request) -> UpdateEmailProviderResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update an <a href="https://auth0.com/docs/email/providers">email provider</a>. The <code>credentials</code> object
requires different properties depending on the email provider (which is specified using the <code>name</code> property):
<ul>
  <li><code>mandrill</code> requires <code>api_key</code></li>
  <li><code>sendgrid</code> requires <code>api_key</code></li>
  <li>
    <code>sparkpost</code> requires <code>api_key</code>. Optionally, set <code>region</code> to <code>eu</code> to use
    the SparkPost service hosted in Western Europe; set to <code>null</code> to use the SparkPost service hosted in
    North America. <code>eu</code> or <code>null</code> are the only valid values for <code>region</code>.
  </li>
  <li>
    <code>mailgun</code> requires <code>api_key</code> and <code>domain</code>. Optionally, set <code>region</code> to
    <code>eu</code> to use the Mailgun service hosted in Europe; set to <code>null</code> otherwise. <code>eu</code> or
    <code>null</code> are the only valid values for <code>region</code>.
  </li>
  <li><code>ses</code> requires <code>accessKeyId</code>, <code>secretAccessKey</code>, and <code>region</code></li>
  <li>
    <code>smtp</code> requires <code>smtp_host</code>, <code>smtp_port</code>, <code>smtp_user</code>, and
    <code>smtp_pass</code>
  </li>
</ul>
Depending on the type of provider it is possible to specify <code>settings</code> object with different configuration
options, which will be used when sending an email:
<ul>
  <li>
    <code>smtp</code> provider, <code>settings</code> may contain <code>headers</code> object.
    <ul>
      <li>
        When using AWS SES SMTP host, you may provide a name of configuration set in
        <code>X-SES-Configuration-Set</code> header. Value must be a string.
      </li>
      <li>
        When using Sparkpost host, you may provide value for
        <code>X-MSYS_API</code> header. Value must be an object.
      </li>
    </ul>
    for <code>ses</code> provider, <code>settings</code> may contain <code>message</code> object, where you can provide
    a name of configuration set in <code>configuration_set_name</code> property. Value must be a string.
  </li>
</ul>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.emails().provider().update(
    UpdateEmailProviderRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `Optional<EmailProviderNameEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Optional<Boolean>` — Whether the provider is enabled (true) or disabled (false).
    
</dd>
</dl>

<dl>
<dd>

**defaultFromAddress:** `Optional<String>` — Email address to use as "from" when no other address specified.
    
</dd>
</dl>

<dl>
<dd>

**credentials:** `Optional<EmailProviderCredentialsSchema>` 
    
</dd>
</dl>

<dl>
<dd>

**settings:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## EventStreams Deliveries
<details><summary><code>client.eventStreams.deliveries.list(id) -> List&lt;EventStreamDelivery&gt;</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().deliveries().list(
    "id",
    ListEventStreamDeliveriesRequestParameters
        .builder()
        .statuses(
            OptionalNullable.of("statuses")
        )
        .eventTypes(
            OptionalNullable.of("event_types")
        )
        .dateFrom(
            OptionalNullable.of("date_from")
        )
        .dateTo(
            OptionalNullable.of("date_to")
        )
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Unique identifier for the event stream.
    
</dd>
</dl>

<dl>
<dd>

**statuses:** `Optional<String>` — Comma-separated list of statuses by which to filter
    
</dd>
</dl>

<dl>
<dd>

**eventTypes:** `Optional<String>` — Comma-separated list of event types by which to filter
    
</dd>
</dl>

<dl>
<dd>

**dateFrom:** `Optional<String>` — An RFC-3339 date-time for redelivery start, inclusive. Does not allow sub-second precision.
    
</dd>
</dl>

<dl>
<dd>

**dateTo:** `Optional<String>` — An RFC-3339 date-time for redelivery end, exclusive. Does not allow sub-second precision.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.eventStreams.deliveries.getHistory(id, eventId) -> GetEventStreamDeliveryHistoryResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().deliveries().getHistory("id", "event_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Unique identifier for the event stream.
    
</dd>
</dl>

<dl>
<dd>

**eventId:** `String` — Unique identifier for the event
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## EventStreams Redeliveries
<details><summary><code>client.eventStreams.redeliveries.create(id, request) -> CreateEventStreamRedeliveryResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().redeliveries().create(
    "id",
    CreateEventStreamRedeliveryRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Unique identifier for the event stream.
    
</dd>
</dl>

<dl>
<dd>

**dateFrom:** `Optional<OffsetDateTime>` — An RFC-3339 date-time for redelivery start, inclusive. Does not allow sub-second precision.
    
</dd>
</dl>

<dl>
<dd>

**dateTo:** `Optional<OffsetDateTime>` — An RFC-3339 date-time for redelivery end, exclusive. Does not allow sub-second precision.
    
</dd>
</dl>

<dl>
<dd>

**statuses:** `Optional<List<String>>` — Filter by status
    
</dd>
</dl>

<dl>
<dd>

**eventTypes:** `Optional<List<EventStreamEventTypeEnum>>` — Filter by event type
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.eventStreams.redeliveries.createById(id, eventId)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.eventStreams().redeliveries().createById("id", "event_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Unique identifier for the event stream.
    
</dd>
</dl>

<dl>
<dd>

**eventId:** `String` — Unique identifier for the event
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Flows Executions
<details><summary><code>client.flows.executions.list(flowId) -> SyncPagingIterable&lt;FlowExecutionSummary&gt;</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().executions().list(
    "flow_id",
    ExecutionsListRequest
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**flowId:** `String` — Flow id
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.executions.get(flowId, executionId) -> GetFlowExecutionResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().executions().get(
    "flow_id",
    "execution_id",
    ExecutionsGetRequest
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**flowId:** `String` — Flow id
    
</dd>
</dl>

<dl>
<dd>

**executionId:** `String` — Flow execution id
    
</dd>
</dl>

<dl>
<dd>

**hydrate:** `Optional<String>` — Hydration param
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.executions.delete(flowId, executionId)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().executions().delete("flow_id", "execution_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**flowId:** `String` — Flows id
    
</dd>
</dl>

<dl>
<dd>

**executionId:** `String` — Flow execution identifier
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Flows Vault Connections
<details><summary><code>client.flows.vault.connections.list() -> SyncPagingIterable&lt;FlowsVaultConnectionSummary&gt;</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().vault().connections().list(
    ListFlowsVaultConnectionsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.vault.connections.create(request) -> CreateFlowsVaultConnectionResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().vault().connections().create(
    CreateFlowsVaultConnectionRequestContent.of(
        CreateFlowsVaultConnectionActivecampaign.of(
            CreateFlowsVaultConnectionActivecampaignApiKey
                .builder()
                .name("name")
                .appId("ACTIVECAMPAIGN")
                .setup(
                    FlowsVaultConnectioSetupApiKeyWithBaseUrl
                        .builder()
                        .type("API_KEY")
                        .apiKey("api_key")
                        .baseUrl("base_url")
                        .build()
                )
                .build()
        )
    )
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `CreateFlowsVaultConnectionRequestContent` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.vault.connections.get(id) -> GetFlowsVaultConnectionResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().vault().connections().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Flows Vault connection ID
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.vault.connections.delete(id)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().vault().connections().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Vault connection id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.flows.vault.connections.update(id, request) -> UpdateFlowsVaultConnectionResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.flows().vault().connections().update(
    "id",
    UpdateFlowsVaultConnectionRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Flows Vault connection ID
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — Flows Vault Connection name.
    
</dd>
</dl>

<dl>
<dd>

**setup:** `Optional<UpdateFlowsVaultConnectionSetup>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Guardian Enrollments
<details><summary><code>client.guardian.enrollments.createTicket(request) -> CreateGuardianEnrollmentTicketResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a <a href="https://auth0.com/docs/secure/multi-factor-authentication/auth0-guardian/create-custom-enrollment-tickets">multi-factor authentication (MFA) enrollment ticket</a>, and optionally send an email with the created ticket, to a given user.
Create a <a href="https://auth0.com/docs/secure/multi-factor-authentication/auth0-guardian/create-custom-enrollment-tickets">multi-factor authentication (MFA) enrollment ticket</a>, and optionally send an email with the created ticket to a given user. Enrollment tickets can specify which factor users must enroll with or allow existing MFA users to enroll in additional factors.<br/> 

Note: Users cannot enroll in Email as a factor through custom enrollment tickets. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().enrollments().createTicket(
    CreateGuardianEnrollmentTicketRequestContent
        .builder()
        .userId("user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — user_id for the enrollment ticket
    
</dd>
</dl>

<dl>
<dd>

**email:** `Optional<String>` — alternate email to which the enrollment email will be sent. Optional - by default, the email will be sent to the user's default address
    
</dd>
</dl>

<dl>
<dd>

**sendMail:** `Optional<Boolean>` — Send an email to the user to start the enrollment
    
</dd>
</dl>

<dl>
<dd>

**emailLocale:** `Optional<String>` — Optional. Specify the locale of the enrollment email. Used with send_email.
    
</dd>
</dl>

<dl>
<dd>

**factor:** `Optional<GuardianEnrollmentFactorEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**allowMultipleEnrollments:** `Optional<Boolean>` — Optional. Allows a user who has previously enrolled in MFA to enroll with additional factors.<br />Note: Parameter can only be used with Universal Login; it cannot be used with Classic Login or custom MFA pages.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.enrollments.get(id) -> GetGuardianEnrollmentResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details, such as status and type, for a specific multi-factor authentication enrollment registered to a user account.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().enrollments().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the enrollment to be retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.enrollments.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove a specific multi-factor authentication (MFA) enrollment from a user's account. This allows the user to re-enroll with MFA. For more information, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/reset-user-mfa">Reset User Multi-Factor Authentication and Recovery Codes</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().enrollments().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the enrollment to be deleted.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Guardian Factors
<details><summary><code>client.guardian.factors.list() -> List&lt;GuardianFactor&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of all <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors">multi-factor authentication factors</a> associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().list();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.set(name, request) -> SetGuardianFactorResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the status (i.e., enabled or disabled) of a specific multi-factor authentication factor.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().set(
    GuardianFactorNameEnum.PUSH_NOTIFICATION,
    SetGuardianFactorRequestContent
        .builder()
        .enabled(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `GuardianFactorNameEnum` — Factor name. Can be `sms`, `push-notification`, `email`, `duo` `otp` `webauthn-roaming`, `webauthn-platform`, or `recovery-code`.
    
</dd>
</dl>

<dl>
<dd>

**enabled:** `Boolean` — Whether this factor is enabled (true) or disabled (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Guardian Policies
<details><summary><code>client.guardian.policies.list() -> List&lt;MfaPolicyEnum&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the <a href="https://auth0.com/docs/secure/multi-factor-authentication/enable-mfa">multi-factor authentication (MFA) policies</a> configured for your tenant.

The following policies are supported:
<ul>
<li><code>all-applications</code> policy prompts with MFA for all logins.</li>
<li><code>confidence-score</code> policy prompts with MFA only for low confidence logins.</li>
</ul>

<b>Note</b>: The <code>confidence-score</code> policy is part of the <a href="https://auth0.com/docs/secure/multi-factor-authentication/adaptive-mfa">Adaptive MFA feature</a>. Adaptive MFA requires an add-on for the Enterprise plan; review <a href="https://auth0.com/pricing">Auth0 Pricing</a> for more details.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().policies().list();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.policies.set(request) -> List&lt;MfaPolicyEnum&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Set <a href="https://auth0.com/docs/secure/multi-factor-authentication/enable-mfa">multi-factor authentication (MFA) policies</a> for your tenant.

The following policies are supported:
<ul>
<li><code>all-applications</code> policy prompts with MFA for all logins.</li>
<li><code>confidence-score</code> policy prompts with MFA only for low confidence logins.</li>
</ul>

<b>Note</b>: The <code>confidence-score</code> policy is part of the <a href="https://auth0.com/docs/secure/multi-factor-authentication/adaptive-mfa">Adaptive MFA feature</a>. Adaptive MFA requires an add-on for the Enterprise plan; review <a href="https://auth0.com/pricing">Auth0 Pricing</a> for more details.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().policies().set(
    Arrays.asList(MfaPolicyEnum.ALL_APPLICATIONS)
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**request:** `List<MfaPolicyEnum>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Guardian Factors Phone
<details><summary><code>client.guardian.factors.phone.getMessageTypes() -> GetGuardianFactorPhoneMessageTypesResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve list of <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors/configure-sms-voice-notifications-mfa">phone-type MFA factors</a> (i.e., sms and voice) that are enabled for your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().phone().getMessageTypes();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.phone.setMessageTypes(request) -> SetGuardianFactorPhoneMessageTypesResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Replace the list of <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors/configure-sms-voice-notifications-mfa">phone-type MFA factors</a> (i.e., sms and voice) that are enabled for your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().phone().setMessageTypes(
    SetGuardianFactorPhoneMessageTypesRequestContent
        .builder()
        .messageTypes(
            Arrays.asList(GuardianFactorPhoneFactorMessageTypeEnum.SMS)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**messageTypes:** `List<GuardianFactorPhoneFactorMessageTypeEnum>` — The list of phone factors to enable on the tenant. Can include `sms` and `voice`.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.phone.getTwilioProvider() -> GetGuardianFactorsProviderPhoneTwilioResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve configuration details for a Twilio phone provider that has been set up in your tenant. To learn more, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors/configure-sms-voice-notifications-mfa">Configure SMS and Voice Notifications for MFA</a>. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().phone().getTwilioProvider();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.phone.setTwilioProvider(request) -> SetGuardianFactorsProviderPhoneTwilioResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the configuration of a Twilio phone provider that has been set up in your tenant. To learn more, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors/configure-sms-voice-notifications-mfa">Configure SMS and Voice Notifications for MFA</a>. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().phone().setTwilioProvider(
    SetGuardianFactorsProviderPhoneTwilioRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — From number
    
</dd>
</dl>

<dl>
<dd>

**messagingServiceSid:** `Optional<String>` — Copilot SID
    
</dd>
</dl>

<dl>
<dd>

**authToken:** `Optional<String>` — Twilio Authentication token
    
</dd>
</dl>

<dl>
<dd>

**sid:** `Optional<String>` — Twilio SID
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.phone.getSelectedProvider() -> GetGuardianFactorsProviderPhoneResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the multi-factor authentication phone provider configured for your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().phone().getSelectedProvider();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.phone.setProvider(request) -> SetGuardianFactorsProviderPhoneResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().phone().setProvider(
    SetGuardianFactorsProviderPhoneRequestContent
        .builder()
        .provider(GuardianFactorsProviderSmsProviderEnum.AUTH0)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**provider:** `GuardianFactorsProviderSmsProviderEnum` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.phone.getTemplates() -> GetGuardianFactorPhoneTemplatesResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the multi-factor authentication enrollment and verification templates for phone-type factors available in your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().phone().getTemplates();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.phone.setTemplates(request) -> SetGuardianFactorPhoneTemplatesResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Customize the messages sent to complete phone enrollment and verification (subscription required).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().phone().setTemplates(
    SetGuardianFactorPhoneTemplatesRequestContent
        .builder()
        .enrollmentMessage("enrollment_message")
        .verificationMessage("verification_message")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**enrollmentMessage:** `String` — Message sent to the user when they are invited to enroll with a phone number.
    
</dd>
</dl>

<dl>
<dd>

**verificationMessage:** `String` — Message sent to the user when they are prompted to verify their account.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Guardian Factors PushNotification
<details><summary><code>client.guardian.factors.pushNotification.getApnsProvider() -> GetGuardianFactorsProviderApnsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve configuration details for the multi-factor authentication APNS provider associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().getApnsProvider();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.setApnsProvider(request) -> SetGuardianFactorsProviderPushNotificationApnsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Overwrite all configuration details of the multi-factor authentication APNS provider associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().setApnsProvider(
    SetGuardianFactorsProviderPushNotificationApnsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**sandbox:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**bundleId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**p12:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.updateApnsProvider(request) -> UpdateGuardianFactorsProviderPushNotificationApnsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify configuration details of the multi-factor authentication APNS provider associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().updateApnsProvider(
    UpdateGuardianFactorsProviderPushNotificationApnsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**sandbox:** `Optional<Boolean>` 
    
</dd>
</dl>

<dl>
<dd>

**bundleId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**p12:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.setFcmProvider(request) -> Map&lt;String, Object&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Overwrite all configuration details of the multi-factor authentication FCM provider associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().setFcmProvider(
    SetGuardianFactorsProviderPushNotificationFcmRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**serverKey:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.updateFcmProvider(request) -> Map&lt;String, Object&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify configuration details of the multi-factor authentication FCM provider associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().updateFcmProvider(
    UpdateGuardianFactorsProviderPushNotificationFcmRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**serverKey:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.setFcmv1Provider(request) -> Map&lt;String, Object&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Overwrite all configuration details of the multi-factor authentication FCMV1 provider associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().setFcmv1Provider(
    SetGuardianFactorsProviderPushNotificationFcmv1RequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**serverCredentials:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.updateFcmv1Provider(request) -> Map&lt;String, Object&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify configuration details of the multi-factor authentication FCMV1 provider associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().updateFcmv1Provider(
    UpdateGuardianFactorsProviderPushNotificationFcmv1RequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**serverCredentials:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.getSnsProvider() -> GetGuardianFactorsProviderSnsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve configuration details for an AWS SNS push notification provider that has been enabled for MFA. To learn more, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors/configure-push-notifications-for-mfa">Configure Push Notifications for MFA</a>. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().getSnsProvider();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.setSnsProvider(request) -> SetGuardianFactorsProviderPushNotificationSnsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Configure the <a href="https://auth0.com/docs/multifactor-authentication/developer/sns-configuration">AWS SNS push notification provider configuration</a> (subscription required).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().setSnsProvider(
    SetGuardianFactorsProviderPushNotificationSnsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**awsAccessKeyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**awsSecretAccessKey:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**awsRegion:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**snsApnsPlatformApplicationArn:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**snsGcmPlatformApplicationArn:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.updateSnsProvider(request) -> UpdateGuardianFactorsProviderPushNotificationSnsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Configure the <a href="https://auth0.com/docs/multifactor-authentication/developer/sns-configuration">AWS SNS push notification provider configuration</a> (subscription required).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().updateSnsProvider(
    UpdateGuardianFactorsProviderPushNotificationSnsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**awsAccessKeyId:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**awsSecretAccessKey:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**awsRegion:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**snsApnsPlatformApplicationArn:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**snsGcmPlatformApplicationArn:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.getSelectedProvider() -> GetGuardianFactorsProviderPushNotificationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify the push notification provider configured for your tenant. For more information, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors/configure-push-notifications-for-mfa">Configure Push Notifications for MFA</a>. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().getSelectedProvider();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.pushNotification.setProvider(request) -> SetGuardianFactorsProviderPushNotificationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify the push notification provider configured for your tenant. For more information, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors/configure-push-notifications-for-mfa">Configure Push Notifications for MFA</a>. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().pushNotification().setProvider(
    SetGuardianFactorsProviderPushNotificationRequestContent
        .builder()
        .provider(GuardianFactorsProviderPushNotificationProviderDataEnum.GUARDIAN)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**provider:** `GuardianFactorsProviderPushNotificationProviderDataEnum` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Guardian Factors Sms
<details><summary><code>client.guardian.factors.sms.getTwilioProvider() -> GetGuardianFactorsProviderSmsTwilioResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the <a href="https://auth0.com/docs/multifactor-authentication/twilio-configuration">Twilio SMS provider configuration</a> (subscription required).

    A new endpoint is available to retrieve the Twilio configuration related to phone factors (<a href='https://auth0.com/docs/api/management/v2/#!/Guardian/get_twilio'>phone Twilio configuration</a>). It has the same payload as this one. Please use it instead.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().sms().getTwilioProvider();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.sms.setTwilioProvider(request) -> SetGuardianFactorsProviderSmsTwilioResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

This endpoint has been deprecated. To complete this action, use the <a href="https://auth0.com/docs/api/management/v2/guardian/put-twilio">Update Twilio phone configuration</a> endpoint.

    <b>Previous functionality</b>: Update the Twilio SMS provider configuration.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().sms().setTwilioProvider(
    SetGuardianFactorsProviderSmsTwilioRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — From number
    
</dd>
</dl>

<dl>
<dd>

**messagingServiceSid:** `Optional<String>` — Copilot SID
    
</dd>
</dl>

<dl>
<dd>

**authToken:** `Optional<String>` — Twilio Authentication token
    
</dd>
</dl>

<dl>
<dd>

**sid:** `Optional<String>` — Twilio SID
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.sms.getSelectedProvider() -> GetGuardianFactorsProviderSmsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

This endpoint has been deprecated. To complete this action, use the <a href="https://auth0.com/docs/api/management/v2/guardian/get-phone-providers">Retrieve phone configuration</a> endpoint instead.

    <b>Previous functionality</b>: Retrieve details for the multi-factor authentication SMS provider configured for your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().sms().getSelectedProvider();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.sms.setProvider(request) -> SetGuardianFactorsProviderSmsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

This endpoint has been deprecated. To complete this action, use the <a href="https://auth0.com/docs/api/management/v2/guardian/put-phone-providers">Update phone configuration</a> endpoint instead.

    <b>Previous functionality</b>: Update the multi-factor authentication SMS provider configuration in your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().sms().setProvider(
    SetGuardianFactorsProviderSmsRequestContent
        .builder()
        .provider(GuardianFactorsProviderSmsProviderEnum.AUTH0)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**provider:** `GuardianFactorsProviderSmsProviderEnum` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.sms.getTemplates() -> GetGuardianFactorSmsTemplatesResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

This endpoint has been deprecated. To complete this action, use the <a href="https://auth0.com/docs/api/management/v2/guardian/get-factor-phone-templates">Retrieve enrollment and verification phone templates</a> endpoint instead.

    <b>Previous function</b>: Retrieve details of SMS enrollment and verification templates configured for your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().sms().getTemplates();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.sms.setTemplates(request) -> SetGuardianFactorSmsTemplatesResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

This endpoint has been deprecated. To complete this action, use the <a href="https://auth0.com/docs/api/management/v2/guardian/put-factor-phone-templates">Update enrollment and verification phone templates</a> endpoint instead.

    <b>Previous functionality</b>: Customize the messages sent to complete SMS enrollment and verification.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().sms().setTemplates(
    SetGuardianFactorSmsTemplatesRequestContent
        .builder()
        .enrollmentMessage("enrollment_message")
        .verificationMessage("verification_message")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**enrollmentMessage:** `String` — Message sent to the user when they are invited to enroll with a phone number.
    
</dd>
</dl>

<dl>
<dd>

**verificationMessage:** `String` — Message sent to the user when they are prompted to verify their account.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Guardian Factors Duo Settings
<details><summary><code>client.guardian.factors.duo.settings.get() -> GetGuardianFactorDuoSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves the DUO account and factor configuration.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().duo().settings().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.duo.settings.set(request) -> SetGuardianFactorDuoSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Set the DUO account configuration and other properties specific to this factor.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().duo().settings().set(
    SetGuardianFactorDuoSettingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ikey:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**skey:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**host:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.guardian.factors.duo.settings.update(request) -> UpdateGuardianFactorDuoSettingsResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.guardian().factors().duo().settings().update(
    UpdateGuardianFactorDuoSettingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**ikey:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**skey:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**host:** `Optional<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Hooks Secrets
<details><summary><code>client.hooks.secrets.get(id) -> Map&lt;String, String&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a hook's secrets by the ID of the hook. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().secrets().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the hook to retrieve secrets from.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.hooks.secrets.create(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Add one or more secrets to an existing hook. Accepts an object of key-value pairs, where the key is the name of the secret. A hook can have a maximum of 20 secrets. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().secrets().create(
    "id",
    new HashMap<String, String>() {{
        put("key", "value");
    }}
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the hook to retrieve
    
</dd>
</dl>

<dl>
<dd>

**request:** `Map<String, String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.hooks.secrets.delete(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete one or more existing secrets for a given hook. Accepts an array of secret names to delete. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().secrets().delete(
    "id",
    Arrays.asList("string")
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the hook whose secrets to delete.
    
</dd>
</dl>

<dl>
<dd>

**request:** `List<String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.hooks.secrets.update(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update one or more existing secrets for an existing hook. Accepts an object of key-value pairs, where the key is the name of the existing secret. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.hooks().secrets().update(
    "id",
    new HashMap<String, String>() {{
        put("key", "value");
    }}
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the hook whose secrets to update.
    
</dd>
</dl>

<dl>
<dd>

**request:** `Map<String, String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Jobs UsersExports
<details><summary><code>client.jobs.usersExports.create(request) -> CreateExportUsersResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Export all users to a file via a long-running job.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.jobs().usersExports().create(
    CreateExportUsersRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**connectionId:** `Optional<String>` — connection_id of the connection from which users will be exported.
    
</dd>
</dl>

<dl>
<dd>

**format:** `Optional<JobFileFormatEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**limit:** `Optional<Integer>` — Limit the number of records.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<List<CreateExportUsersFields>>` — List of fields to be included in the CSV. Defaults to a predefined set of fields.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Jobs UsersImports
<details><summary><code>client.jobs.usersImports.create(request) -> CreateImportUsersResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Import users from a <a href="https://auth0.com/docs/users/references/bulk-import-database-schema-examples">formatted file</a> into a connection via a long-running job. When importing users, with or without upsert, the `email_verified` is set to `false` when the email address is added or updated. Users must verify their email address. To avoid this behavior, set `email_verified` to `true` in the imported data.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.jobs().usersImports().create(
    CreateImportUsersRequestContent
        .builder()
        .connectionId("connection_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Jobs VerificationEmail
<details><summary><code>client.jobs.verificationEmail.create(request) -> CreateVerificationEmailResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Send an email to the specified user that asks them to click a link to <a href="https://auth0.com/docs/email/custom#verification-email">verify their email address</a>.

Note: You must have the `Status` toggle enabled for the verification email template for the email to be sent.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.jobs().verificationEmail().create(
    CreateVerificationEmailRequestContent
        .builder()
        .userId("user_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — user_id of the user to send the verification email to.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — client_id of the client (application). If no value provided, the global Client ID will be used.
    
</dd>
</dl>

<dl>
<dd>

**identity:** `Optional<Identity>` 
    
</dd>
</dl>

<dl>
<dd>

**organizationId:** `Optional<String>` — (Optional) Organization ID – the ID of the Organization. If provided, organization parameters will be made available to the email template and organization branding will be applied to the prompt. In addition, the redirect link in the prompt will include organization_id and organization_name query string parameters.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Jobs Errors
<details><summary><code>client.jobs.errors.get(id) -> ErrorsGetResponse</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve error details of a failed job.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.jobs().errors().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the job.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Keys CustomSigning
<details><summary><code>client.keys.customSigning.get() -> GetCustomSigningKeysResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get entire jwks representation of custom signing keys.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().customSigning().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.customSigning.set(request) -> SetCustomSigningKeysResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create or replace entire jwks representation of custom signing keys.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().customSigning().set(
    SetCustomSigningKeysRequestContent
        .builder()
        .keys(
            Arrays.asList(
                CustomSigningKeyJwk
                    .builder()
                    .kty(CustomSigningKeyTypeEnum.EC)
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**keys:** `List<CustomSigningKeyJwk>` — An array of custom public signing keys.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.customSigning.delete()</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete entire jwks representation of custom signing keys.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().customSigning().delete();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Keys Encryption
<details><summary><code>client.keys.encryption.list() -> SyncPagingIterable&lt;EncryptionKey&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of all the encryption keys associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().encryption().list(
    ListEncryptionKeysRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Default value is 50, maximum value is 100.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.encryption.create(request) -> CreateEncryptionKeyResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create the new, pre-activated encryption key, without the key material.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().encryption().create(
    CreateEncryptionKeyRequestContent
        .builder()
        .type(CreateEncryptionKeyType.CUSTOMER_PROVIDED_ROOT_KEY)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**type:** `CreateEncryptionKeyType` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.encryption.rekey()</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Perform rekeying operation on the key hierarchy.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().encryption().rekey();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.encryption.get(kid) -> GetEncryptionKeyResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the encryption key with the given ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().encryption().get("kid");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**kid:** `String` — Encryption key ID
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.encryption.import_(kid, request) -> ImportEncryptionKeyResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Import wrapped key material and activate encryption key.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().encryption().import_(
    "kid",
    ImportEncryptionKeyRequestContent
        .builder()
        .wrappedKey("wrapped_key")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**kid:** `String` — Encryption key ID
    
</dd>
</dl>

<dl>
<dd>

**wrappedKey:** `String` — Base64 encoded ciphertext of key material wrapped by public wrapping key.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.encryption.delete(kid)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete the custom provided encryption key with the given ID and move back to using native encryption key.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().encryption().delete("kid");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**kid:** `String` — Encryption key ID
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.encryption.createPublicWrappingKey(kid) -> CreateEncryptionKeyPublicWrappingResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create the public wrapping key to wrap your own encryption key material.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().encryption().createPublicWrappingKey("kid");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**kid:** `String` — Encryption key ID
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Keys Signing
<details><summary><code>client.keys.signing.list() -> List&lt;SigningKeys&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of all the application signing keys associated with your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().signing().list();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.signing.rotate() -> RotateSigningKeysResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Rotate the application signing key of your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().signing().rotate();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.signing.get(kid) -> GetSigningKeysResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details of the application signing key with the given ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().signing().get("kid");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**kid:** `String` — Key id of the key to retrieve
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.keys.signing.revoke(kid) -> RevokedSigningKeysResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Revoke the application signing key with the given ID.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.keys().signing().revoke("kid");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**kid:** `String` — Key id of the key to revoke
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Organizations ClientGrants
<details><summary><code>client.organizations.clientGrants.list(id) -> SyncPagingIterable&lt;OrganizationClientGrant&gt;</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().clientGrants().list(
    "id",
    ListOrganizationClientGrantsRequestParameters
        .builder()
        .audience(
            OptionalNullable.of("audience")
        )
        .clientId(
            OptionalNullable.of("client_id")
        )
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**audience:** `Optional<String>` — Optional filter on audience of the client grant.
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `Optional<String>` — Optional filter on client_id of the client grant.
    
</dd>
</dl>

<dl>
<dd>

**grantIds:** `Optional<String>` — Optional filter on the ID of the client grant. Must be URL encoded and may be specified multiple times (max 10).<br /><b>e.g.</b> <i>../client-grants?grant_ids=id1&grant_ids=id2</i>
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.clientGrants.create(id, request) -> AssociateOrganizationClientGrantResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().clientGrants().create(
    "id",
    AssociateOrganizationClientGrantRequestContent
        .builder()
        .grantId("grant_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**grantId:** `String` — A Client Grant ID to add to the organization.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.clientGrants.delete(id, grantId)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().clientGrants().delete("id", "grant_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**grantId:** `String` — The Client Grant ID to remove from the organization
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Organizations DiscoveryDomains
<details><summary><code>client.organizations.discoveryDomains.list(id) -> SyncPagingIterable&lt;OrganizationDiscoveryDomain&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve list of all organization discovery domains associated with the specified organization.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().discoveryDomains().list(
    "id",
    ListOrganizationDiscoveryDomainsRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the organization.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.discoveryDomains.create(id, request) -> CreateOrganizationDiscoveryDomainResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the verification status and/or use_for_organization_discovery for an organization discovery domain. The <code>status</code> field must be either <code>pending</code> or <code>verified</code>. The <code>use_for_organization_discovery</code> field can be <code>true</code> or <code>false</code> (default: <code>true</code>).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().discoveryDomains().create(
    "id",
    CreateOrganizationDiscoveryDomainRequestContent
        .builder()
        .domain("domain")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the organization.
    
</dd>
</dl>

<dl>
<dd>

**domain:** `String` — The domain name to associate with the organization e.g. acme.com.
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<OrganizationDiscoveryDomainStatus>` 
    
</dd>
</dl>

<dl>
<dd>

**useForOrganizationDiscovery:** `Optional<Boolean>` — Indicates whether this discovery domain should be used for organization discovery.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.discoveryDomains.getByName(id, discoveryDomain) -> GetOrganizationDiscoveryDomainByNameResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a single organization discovery domain specified by domain name.

</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().discoveryDomains().getByName("id", "discovery_domain");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the organization.
    
</dd>
</dl>

<dl>
<dd>

**discoveryDomain:** `String` — Domain name of the discovery domain.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.discoveryDomains.get(id, discoveryDomainId) -> GetOrganizationDiscoveryDomainResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a single organization discovery domain specified by ID. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().discoveryDomains().get("id", "discovery_domain_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the organization.
    
</dd>
</dl>

<dl>
<dd>

**discoveryDomainId:** `String` — ID of the discovery domain.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.discoveryDomains.delete(id, discoveryDomainId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove a discovery domain from an organization. This action cannot be undone. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().discoveryDomains().delete("id", "discovery_domain_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the organization.
    
</dd>
</dl>

<dl>
<dd>

**discoveryDomainId:** `String` — ID of the discovery domain.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.discoveryDomains.update(id, discoveryDomainId, request) -> UpdateOrganizationDiscoveryDomainResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update the verification status and/or use_for_organization_discovery for an organization discovery domain. The <code>status</code> field must be either <code>pending</code> or <code>verified</code>. The <code>use_for_organization_discovery</code> field can be <code>true</code> or <code>false</code> (default: <code>true</code>).
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().discoveryDomains().update(
    "id",
    "discovery_domain_id",
    UpdateOrganizationDiscoveryDomainRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the organization.
    
</dd>
</dl>

<dl>
<dd>

**discoveryDomainId:** `String` — ID of the discovery domain to update.
    
</dd>
</dl>

<dl>
<dd>

**status:** `Optional<OrganizationDiscoveryDomainStatus>` 
    
</dd>
</dl>

<dl>
<dd>

**useForOrganizationDiscovery:** `Optional<Boolean>` — Indicates whether this discovery domain should be used for organization discovery.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Organizations EnabledConnections
<details><summary><code>client.organizations.enabledConnections.list(id) -> SyncPagingIterable&lt;OrganizationConnection&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a specific connection currently enabled for an Organization. Information returned includes details such as connection ID, name, strategy, and whether the connection automatically grants membership upon login.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().enabledConnections().list(
    "id",
    ListOrganizationConnectionsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.enabledConnections.add(id, request) -> AddOrganizationConnectionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Enable a specific connection for a given Organization. To enable a connection, it must already exist within your tenant; connections cannot be created through this action.

<a href="https://auth0.com/docs/authenticate/identity-providers">Connections</a> represent the relationship between Auth0 and a source of users. Available types of connections include database, enterprise, and social.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().enabledConnections().add(
    "id",
    AddOrganizationConnectionRequestContent
        .builder()
        .connectionId("connection_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**connectionId:** `String` — Single connection ID to add to the organization.
    
</dd>
</dl>

<dl>
<dd>

**assignMembershipOnLogin:** `Optional<Boolean>` — When true, all users that log in with this connection will be automatically granted membership in the organization. When false, users must be granted membership in the organization before logging in with this connection.
    
</dd>
</dl>

<dl>
<dd>

**isSignupEnabled:** `Optional<Boolean>` — Determines whether organization signup should be enabled for this organization connection. Only applicable for database connections. Default: false.
    
</dd>
</dl>

<dl>
<dd>

**showAsButton:** `Optional<Boolean>` — Determines whether a connection should be displayed on this organization’s login prompt. Only applicable for enterprise connections. Default: true.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.enabledConnections.get(id, connectionId) -> GetOrganizationConnectionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details about a specific connection currently enabled for an Organization. Information returned includes details such as connection ID, name, strategy, and whether the connection automatically grants membership upon login.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().enabledConnections().get("id", "connectionId");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**connectionId:** `String` — Connection identifier.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.enabledConnections.delete(id, connectionId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Disable a specific connection for an Organization. Once disabled, Organization members can no longer use that connection to authenticate. 

<b>Note</b>: This action does not remove the connection from your tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().enabledConnections().delete("id", "connectionId");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**connectionId:** `String` — Connection identifier.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.enabledConnections.update(id, connectionId, request) -> UpdateOrganizationConnectionResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify the details of a specific connection currently enabled for an Organization.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().enabledConnections().update(
    "id",
    "connectionId",
    UpdateOrganizationConnectionRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**connectionId:** `String` — Connection identifier.
    
</dd>
</dl>

<dl>
<dd>

**assignMembershipOnLogin:** `Optional<Boolean>` — When true, all users that log in with this connection will be automatically granted membership in the organization. When false, users must be granted membership in the organization before logging in with this connection.
    
</dd>
</dl>

<dl>
<dd>

**isSignupEnabled:** `Optional<Boolean>` — Determines whether organization signup should be enabled for this organization connection. Only applicable for database connections. Default: false.
    
</dd>
</dl>

<dl>
<dd>

**showAsButton:** `Optional<Boolean>` — Determines whether a connection should be displayed on this organization’s login prompt. Only applicable for enterprise connections. Default: true.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Organizations Invitations
<details><summary><code>client.organizations.invitations.list(id) -> SyncPagingIterable&lt;OrganizationInvitation&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve a detailed list of invitations sent to users for a specific Organization. The list includes details such as inviter and invitee information, invitation URLs, and dates of creation and expiration. To learn more about Organization invitations, review <a href="https://auth0.com/docs/manage-users/organizations/configure-organizations/invite-members">Invite Organization Members</a>. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().invitations().list(
    "id",
    ListOrganizationInvitationsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .sort(
            OptionalNullable.of("sort")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — When true, return results inside an object that also contains the start and limit.  When false (default), a direct array of results is returned.  We do not yet support returning the total invitations count.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false). Defaults to true.
    
</dd>
</dl>

<dl>
<dd>

**sort:** `Optional<String>` — Field to sort by. Use field:order where order is 1 for ascending and -1 for descending Defaults to created_at:-1.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.invitations.create(id, request) -> CreateOrganizationInvitationResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a user invitation for a specific Organization. Upon creation, the listed user receives an email inviting them to join the Organization. To learn more about Organization invitations, review <a href="https://auth0.com/docs/manage-users/organizations/configure-organizations/invite-members">Invite Organization Members</a>. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().invitations().create(
    "id",
    CreateOrganizationInvitationRequestContent
        .builder()
        .inviter(
            OrganizationInvitationInviter
                .builder()
                .name("name")
                .build()
        )
        .invitee(
            OrganizationInvitationInvitee
                .builder()
                .email("email")
                .build()
        )
        .clientId("client_id")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**inviter:** `OrganizationInvitationInviter` 
    
</dd>
</dl>

<dl>
<dd>

**invitee:** `OrganizationInvitationInvitee` 
    
</dd>
</dl>

<dl>
<dd>

**clientId:** `String` — Auth0 client ID. Used to resolve the application's login initiation endpoint.
    
</dd>
</dl>

<dl>
<dd>

**connectionId:** `Optional<String>` — The id of the connection to force invitee to authenticate with.
    
</dd>
</dl>

<dl>
<dd>

**appMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**userMetadata:** `Optional<Map<String, Object>>` 
    
</dd>
</dl>

<dl>
<dd>

**ttlSec:** `Optional<Integer>` — Number of seconds for which the invitation is valid before expiration. If unspecified or set to 0, this value defaults to 604800 seconds (7 days). Max value: 2592000 seconds (30 days).
    
</dd>
</dl>

<dl>
<dd>

**roles:** `Optional<List<String>>` — List of roles IDs to associated with the user.
    
</dd>
</dl>

<dl>
<dd>

**sendInvitationEmail:** `Optional<Boolean>` — Whether the user will receive an invitation email (true) or no email (false), true by default
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.invitations.get(id, invitationId) -> GetOrganizationInvitationResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().invitations().get(
    "id",
    "invitation_id",
    GetOrganizationInvitationRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**invitationId:** `String` — The id of the user invitation.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false). Defaults to true.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.invitations.delete(id, invitationId)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().invitations().delete("id", "invitation_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**invitationId:** `String` — The id of the user invitation.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Organizations Members
<details><summary><code>client.organizations.members.list(id) -> SyncPagingIterable&lt;OrganizationMember&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

List organization members.
This endpoint is subject to eventual consistency. New users may not be immediately included in the response and deleted users may not be immediately removed from it.

<ul>
  <li>
    Use the <code>fields</code> parameter to optionally define the specific member details retrieved. If <code>fields</code> is left blank, all fields (except roles) are returned.
  </li>
  <li>
    Member roles are not sent by default. Use <code>fields=roles</code> to retrieve the roles assigned to each listed member. To use this parameter, you must include the <code>read:organization_member_roles</code> scope in the token.
  </li>
</ul>

This endpoint supports two types of pagination:

- Offset pagination
- Checkpoint pagination

Checkpoint pagination must be used if you need to retrieve more than 1000 organization members.

<h2>Checkpoint Pagination</h2>

To search by checkpoint, use the following parameters: - from: Optional id from which to start selection. - take: The total amount of entries to retrieve when using the from parameter. Defaults to 50. Note: The first time you call this endpoint using Checkpoint Pagination, you should omit the <code>from</code> parameter. If there are more results, a <code>next</code> value will be included in the response. You can use this for subsequent API calls. When <code>next</code> is no longer included in the response, this indicates there are no more pages remaining.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().members().list(
    "id",
    ListOrganizationMembersRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.members.create(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Set one or more existing users as members of a specific <a href="https://auth0.com/docs/manage-users/organizations">Organization</a>.

To add a user to an Organization through this action, the user must already exist in your tenant. If a user does not yet exist, you can <a href="https://auth0.com/docs/manage-users/organizations/configure-organizations/invite-members">invite them to create an account</a>, manually create them through the Auth0 Dashboard, or use the Management API.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().members().create(
    "id",
    CreateOrganizationMemberRequestContent
        .builder()
        .members(
            Arrays.asList("members")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**members:** `List<String>` — List of user IDs to add to the organization as members.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.members.delete(id, request)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().members().delete(
    "id",
    DeleteOrganizationMembersRequestContent
        .builder()
        .members(
            Arrays.asList("members")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**members:** `List<String>` — List of user IDs to remove from the organization.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Organizations Members Roles
<details><summary><code>client.organizations.members.roles.list(id, userId) -> SyncPagingIterable&lt;Role&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve detailed list of roles assigned to a given user within the context of a specific Organization. 

Users can be members of multiple Organizations with unique roles assigned for each membership. This action only returns the roles associated with the specified Organization; any roles assigned to the user within other Organizations are not included.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().members().roles().list(
    "id",
    "user_id",
    ListOrganizationMemberRolesRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**userId:** `String` — ID of the user to associate roles with.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.members.roles.assign(id, userId, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Assign one or more <a href="https://auth0.com/docs/manage-users/access-control/rbac">roles</a> to a user to determine their access for a specific Organization.

Users can be members of multiple Organizations with unique roles assigned for each membership. This action assigns roles to a user only for the specified Organization. Roles cannot be assigned to a user across multiple Organizations in the same call.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().members().roles().assign(
    "id",
    "user_id",
    AssignOrganizationMemberRolesRequestContent
        .builder()
        .roles(
            Arrays.asList("roles")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**userId:** `String` — ID of the user to associate roles with.
    
</dd>
</dl>

<dl>
<dd>

**roles:** `List<String>` — List of roles IDs to associated with the user.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.organizations.members.roles.delete(id, userId, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove one or more Organization-specific <a href="https://auth0.com/docs/manage-users/access-control/rbac">roles</a> from a given user.

Users can be members of multiple Organizations with unique roles assigned for each membership. This action removes roles from a user in relation to the specified Organization. Roles assigned to the user within a different Organization cannot be managed in the same call.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.organizations().members().roles().delete(
    "id",
    "user_id",
    DeleteOrganizationMemberRolesRequestContent
        .builder()
        .roles(
            Arrays.asList("roles")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Organization identifier.
    
</dd>
</dl>

<dl>
<dd>

**userId:** `String` — User ID of the organization member to remove roles from.
    
</dd>
</dl>

<dl>
<dd>

**roles:** `List<String>` — List of roles IDs associated with the organization member to remove.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Prompts Rendering
<details><summary><code>client.prompts.rendering.list() -> SyncPagingIterable&lt;AculResponseContent&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get render setting configurations for all screens.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().rendering().list(
    ListAculsRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .prompt(
            OptionalNullable.of("prompt")
        )
        .screen(
            OptionalNullable.of("screen")
        )
        .renderingMode(
            OptionalNullable.of(AculRenderingModeEnum.ADVANCED)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (default: true) or excluded (false).
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Maximum value is 100, default value is 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total configuration count (true) or as a direct array of results (false, default).
    
</dd>
</dl>

<dl>
<dd>

**prompt:** `Optional<String>` — Name of the prompt to filter by
    
</dd>
</dl>

<dl>
<dd>

**screen:** `Optional<String>` — Name of the screen to filter by
    
</dd>
</dl>

<dl>
<dd>

**renderingMode:** `Optional<AculRenderingModeEnum>` — Rendering mode to filter by
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.prompts.rendering.bulkUpdate(request) -> BulkUpdateAculResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Learn more about <a href='https://auth0.com/docs/customize/login-pages/advanced-customizations/getting-started/configure-acul-screens'>configuring render settings</a> for advanced customization.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().rendering().bulkUpdate(
    BulkUpdateAculRequestContent
        .builder()
        .configs(
            Arrays.asList(
                AculConfigsItem
                    .builder()
                    .prompt(PromptGroupNameEnum.LOGIN)
                    .screen(ScreenGroupNameEnum.LOGIN)
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**configs:** `List<AculConfigsItem>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.prompts.rendering.get(prompt, screen) -> GetAculResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get render settings for a screen.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().rendering().get(PromptGroupNameEnum.LOGIN, ScreenGroupNameEnum.LOGIN);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**prompt:** `PromptGroupNameEnum` — Name of the prompt
    
</dd>
</dl>

<dl>
<dd>

**screen:** `ScreenGroupNameEnum` — Name of the screen
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.prompts.rendering.update(prompt, screen, request) -> UpdateAculResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Learn more about <a href='https://auth0.com/docs/customize/login-pages/advanced-customizations/getting-started/configure-acul-screens'>configuring render settings</a> for advanced customization.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().rendering().update(
    PromptGroupNameEnum.LOGIN,
    ScreenGroupNameEnum.LOGIN,
    UpdateAculRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**prompt:** `PromptGroupNameEnum` — Name of the prompt
    
</dd>
</dl>

<dl>
<dd>

**screen:** `ScreenGroupNameEnum` — Name of the screen
    
</dd>
</dl>

<dl>
<dd>

**renderingMode:** `Optional<AculRenderingModeEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**contextConfiguration:** `Optional<List<AculContextConfigurationItem>>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultHeadTagsDisabled:** `Optional<Boolean>` — Override Universal Login default head tags
    
</dd>
</dl>

<dl>
<dd>

**usePageTemplate:** `Optional<Boolean>` — Use page template with ACUL
    
</dd>
</dl>

<dl>
<dd>

**headTags:** `Optional<List<AculHeadTag>>` — An array of head tags
    
</dd>
</dl>

<dl>
<dd>

**filters:** `Optional<AculFilters>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Prompts CustomText
<details><summary><code>client.prompts.customText.get(prompt, language) -> Map&lt;String, Object&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve custom text for a specific prompt and language.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().customText().get(PromptGroupNameEnum.LOGIN, PromptLanguageEnum.AM);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**prompt:** `PromptGroupNameEnum` — Name of the prompt.
    
</dd>
</dl>

<dl>
<dd>

**language:** `PromptLanguageEnum` — Language to update.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.prompts.customText.set(prompt, language, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Set custom text for a specific prompt. Existing texts will be overwritten.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().customText().set(
    PromptGroupNameEnum.LOGIN,
    PromptLanguageEnum.AM,
    new HashMap<String, Object>() {{
        put("key", "value");
    }}
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**prompt:** `PromptGroupNameEnum` — Name of the prompt.
    
</dd>
</dl>

<dl>
<dd>

**language:** `PromptLanguageEnum` — Language to update.
    
</dd>
</dl>

<dl>
<dd>

**request:** `Map<String, Object>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Prompts Partials
<details><summary><code>client.prompts.partials.get(prompt) -> Map&lt;String, Object&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get template partials for a prompt
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().partials().get(PartialGroupsEnum.LOGIN);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**prompt:** `PartialGroupsEnum` — Name of the prompt.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.prompts.partials.set(prompt, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Set template partials for a prompt
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.prompts().partials().set(
    PartialGroupsEnum.LOGIN,
    new HashMap<String, Object>() {{
        put("key", "value");
    }}
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**prompt:** `PartialGroupsEnum` — Name of the prompt.
    
</dd>
</dl>

<dl>
<dd>

**request:** `Map<String, Object>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## RiskAssessments Settings
<details><summary><code>client.riskAssessments.settings.get() -> GetRiskAssessmentsSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Gets the tenant settings for risk assessments
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.riskAssessments().settings().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.riskAssessments.settings.update(request) -> UpdateRiskAssessmentsSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Updates the tenant settings for risk assessments
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.riskAssessments().settings().update(
    UpdateRiskAssessmentsSettingsRequestContent
        .builder()
        .enabled(true)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**enabled:** `Boolean` — Whether or not risk assessment is enabled.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## RiskAssessments Settings NewDevice
<details><summary><code>client.riskAssessments.settings.newDevice.get() -> GetRiskAssessmentsSettingsNewDeviceResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Gets the risk assessment settings for the new device assessor
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.riskAssessments().settings().newDevice().get();
```
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.riskAssessments.settings.newDevice.update(request) -> UpdateRiskAssessmentsSettingsNewDeviceResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Updates the risk assessment settings for the new device assessor
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.riskAssessments().settings().newDevice().update(
    UpdateRiskAssessmentsSettingsNewDeviceRequestContent
        .builder()
        .rememberFor(1)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**rememberFor:** `Integer` — Length of time to remember devices for, in days.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Roles Permissions
<details><summary><code>client.roles.permissions.list(id) -> SyncPagingIterable&lt;PermissionsResponsePayload&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve detailed list (name, description, resource server) of permissions granted by a specified user role.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().permissions().list(
    "id",
    ListRolePermissionsRequestParameters
        .builder()
        .perPage(
            OptionalNullable.of(1)
        )
        .page(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the role to list granted permissions.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.roles.permissions.add(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Add one or more <a href="https://auth0.com/docs/manage-users/access-control/configure-core-rbac/manage-permissions">permissions</a> to a specified user role.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().permissions().add(
    "id",
    AddRolePermissionsRequestContent
        .builder()
        .permissions(
            Arrays.asList(
                PermissionRequestPayload
                    .builder()
                    .resourceServerIdentifier("resource_server_identifier")
                    .permissionName("permission_name")
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the role to add permissions to.
    
</dd>
</dl>

<dl>
<dd>

**permissions:** `List<PermissionRequestPayload>` — array of resource_server_identifier, permission_name pairs.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.roles.permissions.delete(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove one or more <a href="https://auth0.com/docs/manage-users/access-control/configure-core-rbac/manage-permissions">permissions</a> from a specified user role.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().permissions().delete(
    "id",
    DeleteRolePermissionsRequestContent
        .builder()
        .permissions(
            Arrays.asList(
                PermissionRequestPayload
                    .builder()
                    .resourceServerIdentifier("resource_server_identifier")
                    .permissionName("permission_name")
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the role to remove permissions from.
    
</dd>
</dl>

<dl>
<dd>

**permissions:** `List<PermissionRequestPayload>` — array of resource_server_identifier, permission_name pairs.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Roles Users
<details><summary><code>client.roles.users.list(id) -> SyncPagingIterable&lt;RoleUser&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve list of users associated with a specific role. For Dashboard instructions, review <a href="https://auth0.com/docs/manage-users/access-control/configure-core-rbac/roles/view-users-assigned-to-roles">View Users Assigned to Roles</a>.

This endpoint supports two types of pagination:
<ul>
<li>Offset pagination</li>
<li>Checkpoint pagination</li>
</ul>

Checkpoint pagination must be used if you need to retrieve more than 1000 organization members.

<h2>Checkpoint Pagination</h2>

To search by checkpoint, use the following parameters:
<ul>
<li><code>from</code>: Optional id from which to start selection.</li>
<li><code>take</code>: The total amount of entries to retrieve when using the from parameter. Defaults to 50.</li>
</ul>

<b>Note</b>: The first time you call this endpoint using checkpoint pagination, omit the <code>from</code> parameter. If there are more results, a <code>next</code> value is included in the response. You can use this for subsequent API calls. When <code>next</code> is no longer included in the response, no pages are remaining.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().users().list(
    "id",
    ListRoleUsersRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the role to retrieve a list of users associated with.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.roles.users.assign(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Assign one or more users to an existing user role. To learn more, review <a href="https://auth0.com/docs/manage-users/access-control/rbac">Role-Based Access Control</a>.

<b>Note</b>: New roles cannot be created through this action.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.roles().users().assign(
    "id",
    AssignRoleUsersRequestContent
        .builder()
        .users(
            Arrays.asList("users")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the role to assign users to.
    
</dd>
</dl>

<dl>
<dd>

**users:** `List<String>` — user_id's of the users to assign the role to.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## SelfServiceProfiles CustomText
<details><summary><code>client.selfServiceProfiles.customText.list(id, language, page) -> Map&lt;String, String&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieves text customizations for a given self-service profile, language and Self Service SSO Flow page.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().customText().list("id", "en", "get-started");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the self-service profile.
    
</dd>
</dl>

<dl>
<dd>

**language:** `String` — The language of the custom text.
    
</dd>
</dl>

<dl>
<dd>

**page:** `String` — The page where the custom text is shown.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.selfServiceProfiles.customText.set(id, language, page, request) -> Map&lt;String, String&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Updates text customizations for a given self-service profile, language and Self Service SSO Flow page.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().customText().set(
    "id",
    "en",
    "get-started",
    new HashMap<String, String>() {{
        put("key", "value");
    }}
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the self-service profile.
    
</dd>
</dl>

<dl>
<dd>

**language:** `String` — The language of the custom text.
    
</dd>
</dl>

<dl>
<dd>

**page:** `String` — The page where the custom text is shown.
    
</dd>
</dl>

<dl>
<dd>

**request:** `Map<String, String>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## SelfServiceProfiles SsoTicket
<details><summary><code>client.selfServiceProfiles.ssoTicket.create(id, request) -> CreateSelfServiceProfileSsoTicketResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Creates an SSO access ticket to initiate the Self Service SSO Flow using a self-service profile.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().ssoTicket().create(
    "id",
    CreateSelfServiceProfileSsoTicketRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The id of the self-service profile to retrieve
    
</dd>
</dl>

<dl>
<dd>

**connectionId:** `Optional<String>` — If provided, this will allow editing of the provided connection during the SSO Flow
    
</dd>
</dl>

<dl>
<dd>

**connectionConfig:** `Optional<SelfServiceProfileSsoTicketConnectionConfig>` 
    
</dd>
</dl>

<dl>
<dd>

**enabledClients:** `Optional<List<String>>` — List of client_ids that the connection will be enabled for.
    
</dd>
</dl>

<dl>
<dd>

**enabledOrganizations:** `Optional<List<SelfServiceProfileSsoTicketEnabledOrganization>>` — List of organizations that the connection will be enabled for.
    
</dd>
</dl>

<dl>
<dd>

**ttlSec:** `Optional<Integer>` — Number of seconds for which the ticket is valid before expiration. If unspecified or set to 0, this value defaults to 432000 seconds (5 days).
    
</dd>
</dl>

<dl>
<dd>

**domainAliasesConfig:** `Optional<SelfServiceProfileSsoTicketDomainAliasesConfig>` 
    
</dd>
</dl>

<dl>
<dd>

**provisioningConfig:** `Optional<SelfServiceProfileSsoTicketProvisioningConfig>` 
    
</dd>
</dl>

<dl>
<dd>

**useForOrganizationDiscovery:** `Optional<Boolean>` — Indicates whether a verified domain should be used for organization discovery during authentication.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.selfServiceProfiles.ssoTicket.revoke(profileId, id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Revokes an SSO access ticket and invalidates associated sessions. The ticket will no longer be accepted to initiate a Self-Service SSO session. If any users have already started a session through this ticket, their session will be terminated. Clients should expect a `202 Accepted` response upon successful processing, indicating that the request has been acknowledged and that the revocation is underway but may not be fully completed at the time of response. If the specified ticket does not exist, a `202 Accepted` response is also returned, signaling that no further action is required.
Clients should treat these `202` responses as an acknowledgment that the request has been accepted and is in progress, even if the ticket was not found.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.selfServiceProfiles().ssoTicket().revoke("profileId", "id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**profileId:** `String` — The id of the self-service profile
    
</dd>
</dl>

<dl>
<dd>

**id:** `String` — The id of the ticket to revoke
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Tenants Settings
<details><summary><code>client.tenants.settings.get() -> GetTenantSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve tenant settings. A list of fields to include or exclude may also be specified.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tenants().settings().get(
    GetTenantSettingsRequestParameters
        .builder()
        .fields(
            OptionalNullable.of("fields")
        )
        .includeFields(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**fields:** `Optional<String>` — Comma-separated list of fields to include or exclude (based on value provided for include_fields) in the result. Leave empty to retrieve all fields.
    
</dd>
</dl>

<dl>
<dd>

**includeFields:** `Optional<Boolean>` — Whether specified fields are to be included (true) or excluded (false).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.tenants.settings.update(request) -> UpdateTenantSettingsResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update settings for a tenant.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.tenants().settings().update(
    UpdateTenantSettingsRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**changePassword:** `Optional<TenantSettingsPasswordPage>` 
    
</dd>
</dl>

<dl>
<dd>

**deviceFlow:** `Optional<TenantSettingsDeviceFlow>` 
    
</dd>
</dl>

<dl>
<dd>

**guardianMfaPage:** `Optional<TenantSettingsGuardianPage>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultAudience:** `Optional<String>` — Default audience for API Authorization.
    
</dd>
</dl>

<dl>
<dd>

**defaultDirectory:** `Optional<String>` — Name of connection used for password grants at the `/token` endpoint. The following connection types are supported: LDAP, AD, Database Connections, Passwordless, Windows Azure Active Directory, ADFS.
    
</dd>
</dl>

<dl>
<dd>

**errorPage:** `Optional<TenantSettingsErrorPage>` 
    
</dd>
</dl>

<dl>
<dd>

**defaultTokenQuota:** `Optional<DefaultTokenQuota>` 
    
</dd>
</dl>

<dl>
<dd>

**flags:** `Optional<TenantSettingsFlags>` 
    
</dd>
</dl>

<dl>
<dd>

**friendlyName:** `Optional<String>` — Friendly name for this tenant.
    
</dd>
</dl>

<dl>
<dd>

**pictureUrl:** `Optional<String>` — URL of logo to be shown for this tenant (recommended size: 150x150)
    
</dd>
</dl>

<dl>
<dd>

**supportEmail:** `Optional<String>` — End-user support email.
    
</dd>
</dl>

<dl>
<dd>

**supportUrl:** `Optional<String>` — End-user support url.
    
</dd>
</dl>

<dl>
<dd>

**allowedLogoutUrls:** `Optional<List<String>>` — URLs that are valid to redirect to after logout from Auth0.
    
</dd>
</dl>

<dl>
<dd>

**sessionLifetime:** `Optional<Integer>` — Number of hours a session will stay valid.
    
</dd>
</dl>

<dl>
<dd>

**idleSessionLifetime:** `Optional<Integer>` — Number of hours for which a session can be inactive before the user must log in again.
    
</dd>
</dl>

<dl>
<dd>

**ephemeralSessionLifetime:** `Optional<Integer>` — Number of hours an ephemeral (non-persistent) session will stay valid.
    
</dd>
</dl>

<dl>
<dd>

**idleEphemeralSessionLifetime:** `Optional<Integer>` — Number of hours for which an ephemeral (non-persistent) session can be inactive before the user must log in again.
    
</dd>
</dl>

<dl>
<dd>

**sandboxVersion:** `Optional<String>` — Selected sandbox version for the extensibility environment
    
</dd>
</dl>

<dl>
<dd>

**legacySandboxVersion:** `Optional<String>` — Selected legacy sandbox version for the extensibility environment
    
</dd>
</dl>

<dl>
<dd>

**defaultRedirectionUri:** `Optional<String>` — The default absolute redirection uri, must be https
    
</dd>
</dl>

<dl>
<dd>

**enabledLocales:** `Optional<List<UpdateTenantSettingsRequestContentEnabledLocalesItem>>` — Supported locales for the user interface
    
</dd>
</dl>

<dl>
<dd>

**sessionCookie:** `Optional<SessionCookieSchema>` 
    
</dd>
</dl>

<dl>
<dd>

**sessions:** `Optional<TenantSettingsSessions>` 
    
</dd>
</dl>

<dl>
<dd>

**oidcLogout:** `Optional<TenantOidcLogoutSettings>` 
    
</dd>
</dl>

<dl>
<dd>

**customizeMfaInPostloginAction:** `Optional<Boolean>` — Whether to enable flexible factors for MFA in the PostLogin action
    
</dd>
</dl>

<dl>
<dd>

**allowOrganizationNameInAuthenticationApi:** `Optional<Boolean>` — Whether to accept an organization name instead of an ID on auth endpoints
    
</dd>
</dl>

<dl>
<dd>

**acrValuesSupported:** `Optional<List<String>>` — Supported ACR values
    
</dd>
</dl>

<dl>
<dd>

**mtls:** `Optional<TenantSettingsMtls>` 
    
</dd>
</dl>

<dl>
<dd>

**pushedAuthorizationRequestsSupported:** `Optional<Boolean>` — Enables the use of Pushed Authorization Requests
    
</dd>
</dl>

<dl>
<dd>

**authorizationResponseIssParameterSupported:** `Optional<Boolean>` — Supports iss parameter in authorization responses
    
</dd>
</dl>

<dl>
<dd>

**skipNonVerifiableCallbackUriConfirmationPrompt:** `Optional<Boolean>` 

Controls whether a confirmation prompt is shown during login flows when the redirect URI uses non-verifiable callback URIs (for example, a custom URI schema such as `myapp://`, or `localhost`).
If set to true, a confirmation prompt will not be shown. We recommend that this is set to false for improved protection from malicious apps.
See https://auth0.com/docs/secure/security-guidance/measures-against-app-impersonation for more information.
    
</dd>
</dl>

<dl>
<dd>

**resourceParameterProfile:** `Optional<TenantSettingsResourceParameterProfile>` 
    
</dd>
</dl>

<dl>
<dd>

**enableAiGuide:** `Optional<Boolean>` — Whether Auth0 Guide (AI-powered assistance) is enabled for this tenant.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users AuthenticationMethods
<details><summary><code>client.users.authenticationMethods.list(id) -> SyncPagingIterable&lt;UserAuthenticationMethod&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve detailed list of authentication methods associated with a specified user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().authenticationMethods().list(
    "id",
    ListUserAuthenticationMethodsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the user in question.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0. Default is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Default is 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.authenticationMethods.create(id, request) -> CreateUserAuthenticationMethodResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create an authentication method. Authentication methods created via this endpoint will be auto confirmed and should already have verification completed.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().authenticationMethods().create(
    "id",
    CreateUserAuthenticationMethodRequestContent
        .builder()
        .type(CreatedUserAuthenticationMethodTypeEnum.PHONE)
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the user to whom the new authentication method will be assigned.
    
</dd>
</dl>

<dl>
<dd>

**type:** `CreatedUserAuthenticationMethodTypeEnum` 
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — A human-readable label to identify the authentication method.
    
</dd>
</dl>

<dl>
<dd>

**totpSecret:** `Optional<String>` — Base32 encoded secret for TOTP generation.
    
</dd>
</dl>

<dl>
<dd>

**phoneNumber:** `Optional<String>` — Applies to phone authentication methods only. The destination phone number used to send verification codes via text and voice.
    
</dd>
</dl>

<dl>
<dd>

**email:** `Optional<String>` — Applies to email authentication methods only. The email address used to send verification messages.
    
</dd>
</dl>

<dl>
<dd>

**preferredAuthenticationMethod:** `Optional<PreferredAuthenticationMethodEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**keyId:** `Optional<String>` — Applies to webauthn authentication methods only. The id of the credential.
    
</dd>
</dl>

<dl>
<dd>

**publicKey:** `Optional<String>` — Applies to webauthn authentication methods only. The public key, which is encoded as base64.
    
</dd>
</dl>

<dl>
<dd>

**relyingPartyIdentifier:** `Optional<String>` — Applies to webauthn authentication methods only. The relying party identifier.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.authenticationMethods.set(id, request) -> List&lt;SetUserAuthenticationMethodResponseContent&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Replace the specified user <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors"> authentication methods</a> with supplied values.

    <b>Note</b>: Authentication methods supplied through this action do not iterate on existing methods. Instead, any methods passed will overwrite the user&#8217s existing settings.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().authenticationMethods().set(
    "id",
    Arrays.asList(
        SetUserAuthenticationMethods
            .builder()
            .type(AuthenticationTypeEnum.PHONE)
            .build()
    )
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the user in question.
    
</dd>
</dl>

<dl>
<dd>

**request:** `List<SetUserAuthenticationMethods>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.authenticationMethods.deleteAll(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove all authentication methods (i.e., enrolled MFA factors) from the specified user account. This action cannot be undone. 
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().authenticationMethods().deleteAll("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the user in question.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.authenticationMethods.get(id, authenticationMethodId) -> GetUserAuthenticationMethodResponseContent</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().authenticationMethods().get("id", "authentication_method_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the user in question.
    
</dd>
</dl>

<dl>
<dd>

**authenticationMethodId:** `String` — The ID of the authentication methods in question.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.authenticationMethods.delete(id, authenticationMethodId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove the authentication method with the given ID from the specified user. For more information, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/manage-mfa-auth0-apis/manage-authentication-methods-with-management-api">Manage Authentication Methods with Management API</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().authenticationMethods().delete("id", "authentication_method_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the user in question.
    
</dd>
</dl>

<dl>
<dd>

**authenticationMethodId:** `String` — The ID of the authentication method to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.authenticationMethods.update(id, authenticationMethodId, request) -> UpdateUserAuthenticationMethodResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Modify the authentication method with the given ID from the specified user. For more information, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/manage-mfa-auth0-apis/manage-authentication-methods-with-management-api">Manage Authentication Methods with Management API</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().authenticationMethods().update(
    "id",
    "authentication_method_id",
    UpdateUserAuthenticationMethodRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — The ID of the user in question.
    
</dd>
</dl>

<dl>
<dd>

**authenticationMethodId:** `String` — The ID of the authentication method to update.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` — A human-readable label to identify the authentication method.
    
</dd>
</dl>

<dl>
<dd>

**preferredAuthenticationMethod:** `Optional<PreferredAuthenticationMethodEnum>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Authenticators
<details><summary><code>client.users.authenticators.deleteAll(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove all authenticators registered to a given user ID, such as OTP, email, phone, and push-notification. This action cannot be undone. For more information, review <a href="https://auth0.com/docs/secure/multi-factor-authentication/manage-mfa-auth0-apis/manage-authentication-methods-with-management-api">Manage Authentication Methods with Management API</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().authenticators().deleteAll("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to delete.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users ConnectedAccounts
<details><summary><code>client.users.connectedAccounts.list(id) -> SyncPagingIterable&lt;ConnectedAccount&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all connected accounts associated with the user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().connectedAccounts().list(
    "id",
    GetUserConnectedAccountsRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to list connected accounts for.
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results to return.  Defaults to 10 with a maximum of 20
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Enrollments
<details><summary><code>client.users.enrollments.get(id) -> List&lt;UsersEnrollment&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve the first <a href="https://auth0.com/docs/secure/multi-factor-authentication/multi-factor-authentication-factors">multi-factor authentication</a> enrollment that a specific user has confirmed.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().enrollments().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to list enrollments for.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users FederatedConnectionsTokensets
<details><summary><code>client.users.federatedConnectionsTokensets.list(id) -> List&lt;FederatedConnectionTokenSet&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

List active federated connections tokensets for a provided user
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().federatedConnectionsTokensets().list("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — User identifier
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.federatedConnectionsTokensets.delete(id, tokensetId)</code></summary>
<dl>
<dd>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().federatedConnectionsTokensets().delete("id", "tokenset_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — Id of the user that owns the tokenset
    
</dd>
</dl>

<dl>
<dd>

**tokensetId:** `String` — The tokenset id
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Identities
<details><summary><code>client.users.identities.link(id, request) -> List&lt;UserIdentity&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Link two user accounts together forming a primary and secondary relationship. On successful linking, the endpoint returns the new array of the primary account identities.

Note: There are two ways of invoking the endpoint:

<ul>
  <li>With the authenticated primary account's JWT in the Authorization header, which has the <code>update:current_user_identities</code> scope:
    <pre>
      POST /api/v2/users/PRIMARY_ACCOUNT_USER_ID/identities
      Authorization: "Bearer PRIMARY_ACCOUNT_JWT"
      {
        "link_with": "SECONDARY_ACCOUNT_JWT"
      }
    </pre>
    In this case, only the <code>link_with</code> param is required in the body, which also contains the JWT obtained upon the secondary account's authentication.
  </li>
  <li>With a token generated by the API V2 containing the <code>update:users</code> scope:
    <pre>
    POST /api/v2/users/PRIMARY_ACCOUNT_USER_ID/identities
    Authorization: "Bearer YOUR_API_V2_TOKEN"
    {
      "provider": "SECONDARY_ACCOUNT_PROVIDER",
      "connection_id": "SECONDARY_ACCOUNT_CONNECTION_ID(OPTIONAL)",
      "user_id": "SECONDARY_ACCOUNT_USER_ID"
    }
    </pre>
    In this case you need to send <code>provider</code> and <code>user_id</code> in the body. Optionally you can also send the <code>connection_id</code> param which is suitable for identifying a particular database connection for the 'auth0' provider.
  </li>
</ul>
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().identities().link(
    "id",
    LinkUserIdentityRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the primary user account to link a second user account to.
    
</dd>
</dl>

<dl>
<dd>

**provider:** `Optional<UserIdentityProviderEnum>` 
    
</dd>
</dl>

<dl>
<dd>

**connectionId:** `Optional<String>` — connection_id of the secondary user account being linked when more than one `auth0` database provider exists.
    
</dd>
</dl>

<dl>
<dd>

**userId:** `Optional<UserId>` 
    
</dd>
</dl>

<dl>
<dd>

**linkWith:** `Optional<String>` — JWT for the secondary account being linked. If sending this parameter, `provider`, `user_id`, and `connection_id` must not be sent.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.identities.delete(id, provider, userId) -> List&lt;DeleteUserIdentityResponseContentItem&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Unlink a specific secondary account from a target user. This action requires the ID of both the target user and the secondary account. 

Unlinking the secondary account removes it from the identities array of the target user and creates a new standalone profile for the secondary account. To learn more, review <a href="https://auth0.com/docs/manage-users/user-accounts/user-account-linking/unlink-user-accounts">Unlink User Accounts</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().identities().delete("id", UserIdentityProviderEnum.AD, "user_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the primary user account.
    
</dd>
</dl>

<dl>
<dd>

**provider:** `UserIdentityProviderEnum` — Identity provider name of the secondary linked account (e.g. `google-oauth2`).
    
</dd>
</dl>

<dl>
<dd>

**userId:** `String` — ID of the secondary linked account (e.g. `123456789081523216417` part after the `|` in `google-oauth2|123456789081523216417`).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Logs
<details><summary><code>client.users.logs.list(id) -> SyncPagingIterable&lt;Log&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve log events for a specific user.

Note: For more information on all possible event types, their respective acronyms and descriptions, see <a href="https://auth0.com/docs/logs/log-event-type-codes">Log Event Type Codes</a>.

For more information on the list of fields that can be used in `sort`, see <a href="https://auth0.com/docs/logs/log-search-query-syntax#searchable-fields">Searchable Fields</a>.

Auth0 <a href="https://auth0.com/docs/logs/retrieve-log-events-using-mgmt-api#limitations">limits the number of logs</a> you can return by search criteria to 100 logs per request. Furthermore, you may only paginate through up to 1,000 search results. If you exceed this threshold, please redefine your search.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().logs().list(
    "id",
    ListUserLogsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .sort(
            OptionalNullable.of("sort")
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user of the logs to retrieve
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Paging is disabled if parameter not sent.
    
</dd>
</dl>

<dl>
<dd>

**sort:** `Optional<String>` — Field to sort by. Use `fieldname:1` for ascending order and `fieldname:-1` for descending.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Multifactor
<details><summary><code>client.users.multifactor.invalidateRememberBrowser(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Invalidate all remembered browsers across all <a href="https://auth0.com/docs/multifactor-authentication">authentication factors</a> for a user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().multifactor().invalidateRememberBrowser("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to invalidate all remembered browsers and authentication factors for.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.multifactor.deleteProvider(id, provider)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove a <a href="https://auth0.com/docs/multifactor-authentication">multifactor</a> authentication configuration from a user's account. This forces the user to manually reconfigure the multi-factor provider.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().multifactor().deleteProvider("id", UserMultifactorProviderEnum.DUO);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to remove a multifactor configuration from.
    
</dd>
</dl>

<dl>
<dd>

**provider:** `UserMultifactorProviderEnum` — The multi-factor provider. Supported values 'duo' or 'google-authenticator'
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Organizations
<details><summary><code>client.users.organizations.list(id) -> SyncPagingIterable&lt;Organization&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve list of the specified user's current Organization memberships. User must be specified by user ID. For more information, review <a href="https://auth0.com/docs/manage-users/organizations">Auth0 Organizations</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().organizations().list(
    "id",
    ListUserOrganizationsRequestParameters
        .builder()
        .page(
            OptionalNullable.of(1)
        )
        .perPage(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to retrieve the organizations for.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Permissions
<details><summary><code>client.users.permissions.list(id) -> SyncPagingIterable&lt;UserPermissionSchema&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve all permissions associated with the user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().permissions().list(
    "id",
    ListUserPermissionsRequestParameters
        .builder()
        .perPage(
            OptionalNullable.of(1)
        )
        .page(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to retrieve the permissions for.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.permissions.create(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Assign permissions to a user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().permissions().create(
    "id",
    CreateUserPermissionsRequestContent
        .builder()
        .permissions(
            Arrays.asList(
                PermissionRequestPayload
                    .builder()
                    .resourceServerIdentifier("resource_server_identifier")
                    .permissionName("permission_name")
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to assign permissions to.
    
</dd>
</dl>

<dl>
<dd>

**permissions:** `List<PermissionRequestPayload>` — List of permissions to add to this user.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.permissions.delete(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove permissions from a user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().permissions().delete(
    "id",
    DeleteUserPermissionsRequestContent
        .builder()
        .permissions(
            Arrays.asList(
                PermissionRequestPayload
                    .builder()
                    .resourceServerIdentifier("resource_server_identifier")
                    .permissionName("permission_name")
                    .build()
            )
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to remove permissions from.
    
</dd>
</dl>

<dl>
<dd>

**permissions:** `List<PermissionRequestPayload>` — List of permissions to remove from this user.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users RiskAssessments
<details><summary><code>client.users.riskAssessments.clear(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Clear risk assessment assessors for a specific user
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().riskAssessments().clear(
    "id",
    ClearAssessorsRequestContent
        .builder()
        .connection("connection")
        .assessors(
            Arrays.asList("new-device")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to clear assessors for.
    
</dd>
</dl>

<dl>
<dd>

**connection:** `String` — The name of the connection containing the user whose assessors should be cleared.
    
</dd>
</dl>

<dl>
<dd>

**assessors:** `List<String>` — List of assessors to clear.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Roles
<details><summary><code>client.users.roles.list(id) -> SyncPagingIterable&lt;Role&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve detailed list of all user roles currently assigned to a user.

<b>Note</b>: This action retrieves all roles assigned to a user in the context of your whole tenant. To retrieve Organization-specific roles, use the following endpoint: <a href="https://auth0.com/docs/api/management/v2/organizations/get-organization-member-roles">Get user roles assigned to an Organization member</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().roles().list(
    "id",
    ListUserRolesRequestParameters
        .builder()
        .perPage(
            OptionalNullable.of(1)
        )
        .page(
            OptionalNullable.of(1)
        )
        .includeTotals(
            OptionalNullable.of(true)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to list roles for.
    
</dd>
</dl>

<dl>
<dd>

**perPage:** `Optional<Integer>` — Number of results per page.
    
</dd>
</dl>

<dl>
<dd>

**page:** `Optional<Integer>` — Page index of the results to return. First page is 0.
    
</dd>
</dl>

<dl>
<dd>

**includeTotals:** `Optional<Boolean>` — Return results inside an object that contains the total result count (true) or as a direct array of results (false, default).
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.roles.assign(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Assign one or more existing user roles to a user. For more information, review <a href="https://auth0.com/docs/manage-users/access-control/rbac">Role-Based Access Control</a>.

<b>Note</b>: New roles cannot be created through this action. Additionally, this action is used to assign roles to a user in the context of your whole tenant. To assign roles in the context of a specific Organization, use the following endpoint: <a href="https://auth0.com/docs/api/management/v2/organizations/post-organization-member-roles">Assign user roles to an Organization member</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().roles().assign(
    "id",
    AssignUserRolesRequestContent
        .builder()
        .roles(
            Arrays.asList("roles")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to associate roles with.
    
</dd>
</dl>

<dl>
<dd>

**roles:** `List<String>` — List of roles IDs to associated with the user.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.roles.delete(id, request)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Remove one or more specified user roles assigned to a user.

<b>Note</b>: This action removes a role from a user in the context of your whole tenant. If you want to unassign a role from a user in the context of a specific Organization, use the following endpoint: <a href="https://auth0.com/docs/api/management/v2/organizations/delete-organization-member-roles">Delete user roles from an Organization member</a>.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().roles().delete(
    "id",
    DeleteUserRolesRequestContent
        .builder()
        .roles(
            Arrays.asList("roles")
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the user to remove roles from.
    
</dd>
</dl>

<dl>
<dd>

**roles:** `List<String>` — List of roles IDs to remove from the user.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users RefreshToken
<details><summary><code>client.users.refreshToken.list(userId) -> SyncPagingIterable&lt;RefreshTokenResponseContent&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details for a user's refresh tokens.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().refreshToken().list(
    "user_id",
    ListRefreshTokensRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — ID of the user to get refresh tokens for
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — An optional cursor from which to start the selection (exclusive).
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.refreshToken.delete(userId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete all refresh tokens for a user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().refreshToken().delete("user_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — ID of the user to get remove refresh tokens for
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## Users Sessions
<details><summary><code>client.users.sessions.list(userId) -> SyncPagingIterable&lt;SessionResponseContent&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Retrieve details for a user's sessions.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().sessions().list(
    "user_id",
    ListUserSessionsRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — ID of the user to get sessions for
    
</dd>
</dl>

<dl>
<dd>

**from:** `Optional<String>` — An optional cursor from which to start the selection (exclusive).
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.users.sessions.delete(userId)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete all sessions for a user.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.users().sessions().delete("user_id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**userId:** `String` — ID of the user to get sessions for
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

## VerifiableCredentials Verification Templates
<details><summary><code>client.verifiableCredentials.verification.templates.list() -> SyncPagingIterable&lt;VerifiableCredentialTemplateResponse&gt;</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

List a verifiable credential templates.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.verifiableCredentials().verification().templates().list(
    ListVerifiableCredentialTemplatesRequestParameters
        .builder()
        .from(
            OptionalNullable.of("from")
        )
        .take(
            OptionalNullable.of(1)
        )
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**from:** `Optional<String>` — Optional Id from which to start selection.
    
</dd>
</dl>

<dl>
<dd>

**take:** `Optional<Integer>` — Number of results per page. Defaults to 50.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.verifiableCredentials.verification.templates.create(request) -> CreateVerifiableCredentialTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Create a verifiable credential template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.verifiableCredentials().verification().templates().create(
    CreateVerifiableCredentialTemplateRequestContent
        .builder()
        .name("name")
        .type("type")
        .dialect("dialect")
        .presentation(
            MdlPresentationRequest
                .builder()
                .orgIso1801351MDl(
                    MdlPresentationRequestProperties
                        .builder()
                        .orgIso1801351(
                            MdlPresentationProperties
                                .builder()
                                .build()
                        )
                        .build()
                )
                .build()
        )
        .wellKnownTrustedIssuers("well_known_trusted_issuers")
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**name:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**type:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**dialect:** `String` 
    
</dd>
</dl>

<dl>
<dd>

**presentation:** `MdlPresentationRequest` 
    
</dd>
</dl>

<dl>
<dd>

**customCertificateAuthority:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**wellKnownTrustedIssuers:** `String` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.verifiableCredentials.verification.templates.get(id) -> GetVerifiableCredentialTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Get a verifiable credential template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.verifiableCredentials().verification().templates().get("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the template to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.verifiableCredentials.verification.templates.delete(id)</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Delete a verifiable credential template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.verifiableCredentials().verification().templates().delete("id");
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the template to retrieve.
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>

<details><summary><code>client.verifiableCredentials.verification.templates.update(id, request) -> UpdateVerifiableCredentialTemplateResponseContent</code></summary>
<dl>
<dd>

#### 📝 Description

<dl>
<dd>

<dl>
<dd>

Update a verifiable credential template.
</dd>
</dl>
</dd>
</dl>

#### 🔌 Usage

<dl>
<dd>

<dl>
<dd>

```java
client.verifiableCredentials().verification().templates().update(
    "id",
    UpdateVerifiableCredentialTemplateRequestContent
        .builder()
        .build()
);
```
</dd>
</dl>
</dd>
</dl>

#### ⚙️ Parameters

<dl>
<dd>

<dl>
<dd>

**id:** `String` — ID of the template to retrieve.
    
</dd>
</dl>

<dl>
<dd>

**name:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**type:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**dialect:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**presentation:** `Optional<MdlPresentationRequest>` 
    
</dd>
</dl>

<dl>
<dd>

**wellKnownTrustedIssuers:** `Optional<String>` 
    
</dd>
</dl>

<dl>
<dd>

**version:** `Optional<Double>` 
    
</dd>
</dl>
</dd>
</dl>


</dd>
</dl>
</details>
