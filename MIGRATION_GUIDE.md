# Migration Guide

## Migrating from v1 to v2

The version 2 release includes several notable improvements, including:

* Requests can now be configured with additional parameters and headers, without needing to downcast to `CustomRequest`.
* Responses are now wrapped in a new `com.auth0.net.Response` type, which provides information about the HTTP response such as headers and status code.
* The `AuthAPI` and `ManagementAPI` clients can now share the same HTTP client.
* The `AuthAPI` client no longer requires a client secret, enabling support for APIs and scenarios where a secret is not required.

Version 2 includes breaking changes. Please read this guide to learn how to update your application for v2.

### Configuring `auth0-java` v2

To create the API clients, use the new builders, and specify any HTTP-related configurations with the new `DefaultHttpClient`:

```java
Auth0HttpClient http = DefaultHttpClient.newBuilder()
        .withConnectTimeout(10)
        .withReadTimeout(10)
        // additional configurations as needed
        .build();

AuthAPI auth = AuthAPI.newBuilder("{DOMAIN}", "{CLIENT-ID}", "{OPTIONAL-CLIENT-SECRET}")
        .withHttpClient(http)
        .build();

ManagementAPI mgmt = ManagementAPI.newBuilder("{DOMAIN}", "{API-TOKEN}")
        .withHttpClient(http)
        .build();
```

### Response information

Version 2 returns HTTP response information such as status code and headers in a new `com.auth0.net.Response` type.
Instead of simply returning the parsed JSON response body from requests, all API methods now return a `Response<T>`.
If you have no need for the response information, replace any calls to `execute()` with `execute().getBody()` to get the returned response body as before:

```java
// Get response info
Response<User> userResponse = api.users().get("{USER-ID}", null);
int code = userResponse.getStatusCode();
Map<String, String> headers = userResponse.getHeaders();

// Just get the response body
User user = api.users().get("{USER-ID}", null).execute().getBody();
```

### Request configuration

Previously, only requests that returned a `CustomizableRequest` (or its implementation, `CustomRequest`) allowed for a request to be configured with additional parameters or headers.
In v2, the `com.auth0.net.Request` interface defines the new methods:

- `Request<T> addHeader(String name, String value)`
- `Request<T> addParameter(String name, Object value)`
- `Request<T> setBody(Object body)`

This enables all requests to be configured, without the need to downcast to `CustomizableRequest` or `CustomRequest`.
If you were down-casting to these types, you will need to remove the cast and instead configure the request directly:

```java
Request<User> userRequest = api.users().get("{USER-ID}", null);
userRequest.addHeader("some-header", "some-value");
Response<User> userResponse = userRequest.execute();
```

### Detailed changes

The following summarizes details of the changes in version 2, including types and methods removed, added, or deprecated.

#### Removed classes

- `AuthRequest` has been removed. Use `TokenRequest` instead.
- `CustomizableRequest` and `CustomRequest` have been removed. The `Request` interface now supports request customization directly without the need to downcast.
- `FormDataRequest` has been removed. Use `MultipartRequest` instead.
- `CreateUserRequest` has been removed. Use `SignUpRequest` instead.

#### Moved classes

- `com.auth0.json.mgmt.Token` moved to `com.auth0.json.mgmt.blacklists.Token`
- `com.auth0.json.mgmt.ClientGrant` moved to `com.auth0.json.mgmt.clientgrants.ClientGrant`
- `com.auth0.json.mgmt.ClientGrantsPage` moved to `com.auth0.json.mgmt.clientgrants.ClientGrantsPage`
- `com.auth0.json.mgmt.Connection` moved to `com.auth0.json.mgmt.connections.Connection`
- `com.auth0.json.mgmt.ConnectionsPage` moved to `com.auth0.json.mgmt.connections.ConnectionsPage`
- `com.auth0.json.mgmt.DeviceCredentials` moved to `com.auth0.json.mgmt.devicecredentials.DeviceCredentials`
- `com.auth0.json.mgmt.EmailTemplate` moved to `com.auth0.json.mgmt.emailtemplates.EmailTemplate`
- `com.auth0.json.mgmt.Grant` moved to `com.auth0.json.mgmt.grants.Grant`
- `com.auth0.json.mgmt.GrantsPage` moved to `com.auth0.json.mgmt.grants.GrantsPage`
- `com.auth0.json.mgmt.EmailVerificationIdentity` moved to `com.auth0.json.mgmt.tickets.EmailVerificationIdentity`
- `com.auth0.json.mgmt.Key;` moved to `com.auth0.json.mgmt.keys.Key`
- `com.auth0.json.mgmt.RolesPage` moved to `com.auth0.json.mgmt.roles.RolesPage`
- `com.auth0.json.mgmt.ResourceServer` moved to `com.auth0.json.mgmt.resourceserver.ResourceServer`
- `com.auth0.json.mgmt.ResourceServersPage` moved to `com.auth0.json.mgmt.resourceserver.ResourceServersPage`
- `com.auth0.json.mgmt.Permission` moved to `com.auth0.json.mgmt.permissions.Permission`
- `com.auth0.json.mgmt.PermissionsPage` moved to `com.auth0.json.mgmt.permissions.PermissionsPage`
- `com.auth0.json.mgmt.Role` moved to `com.auth0.json.mgmt.roles.Role`
- `com.auth0.json.mgmt.RolesPage` moved to `com.auth0.json.mgmt.roles.RolesPage`
- `com.auth0.json.mgmt.RulesConfig` moved to `com.auth0.json.mgmt.rules.RulesConfig`
- `com.auth0.json.mgmt.Rule` moved to `com.auth0.json.mgmt.rules.Rule`
- `com.auth0.json.mgmt.RulesPage` moved to `com.auth0.json.mgmt.rules.RulesPage`
- `com.auth0.json.mgmt.DailyStats` moved to `com.auth0.json.mgmt.stats.DailyStats`
- `com.auth0.json.mgmt.Permission` moved to `com.auth0.json.mgmt.permissions.Permission`
- `com.auth0.json.mgmt.PermissionsPage` moved to `com.auth0.json.mgmt.permissions.PermissionsPage`
- `com.auth0.json.mgmt.RolesPage` moved to `com.auth0.json.mgmt.roles.RolesPage`

#### Removed methods

- `void com.auth0.client.mgmt.ManagementAPI#doNotSendTelemetry()` has been removed. Telemetry configuration can be done using the `DefaultHttpClient#Builder`
- `void com.auth0.client.auth.AuthAPI#doNotSendTelemetry()` has been removed. Telemetry configuration can be done using the `DefaultHttpClient#Builder`
- `void com.auth0.client.mgmt.ManagementAPI#setTelemetry(Telemetry telemetry)` has been removed. Telemetry configuration can be done using the `DefaultHttpClient#Builder`
- `void com.auth0.client.auth.AuthAP#setTelemetry(Telemetry telemetry)` has been removed. Telemetry configuration can be done using the `DefaultHttpClient#Builder`
- Deprecated `void com.auth0.client.mgmt.ManagementAPI#setLoggingEnabled(boolean enabled)`  has been removed. Telemetry configuration can be done using the `DefaultHttpClient#Builder`
- Deprecated `void com.auth0.client.auth.AuthAPI#setLoggingEnabled(boolean enabled)`  has been removed. Telemetry configuration can be done using the `DefaultHttpClient#Builder`
- Deprecated `Request<List<ClientGrant>> com.auth0.client.mgmt.ClientGrantsEntity#list()` has been removed. Use `Request<ClientGrantsPage> list(ClientGrantsFilter filter) com.auth0.client.mgmt.ClientGrantsEntity#list(ClientGrantsFilter filter)` instead.
- Deprecated `Request<List<Client>> com.auth0.client.mgmt.ClientsEntity#list()` has been removed. Use `Request<ClientsPage> com.auth0.client.mgmt.ClientsEntity#list(ClientFilter filter)` instead.
- Deprecated `Request<List<Connection>> com.auth0.client.mgmt.ClientsEntity#list(ConnectionFilter filter)` has been removed. Use `Request<ConnectionsPage> com.auth0.client.mgmt.ClientsEntity#listAll(ConnectionFilter filter)` instead.
- Deprecated `Request<List<Grant>> com.auth0.client.mgmt.GrantsEntity#list(String userId)` has been removed. Use `Request<GrantsPage> com.auth0.client.mgmt.GrantsEntity#list(String userId, GrantsFilter filter)` instead.
- Deprecated `Request<List<ResourceServer>> com.auth0.client.mgmt.ResourceServerEntity#list()` has been removed. Use `Request<ResourceServersPage> com.auth0.client.mgmt.ResourceServersEntity#list(ResourceServersFilter)` instead.
- Deprecated `Request<List<Rule>> com.auth0.client.mgmt.RulesEntity#list(RulesFilter filter)` has been removed. Use `Request<RulesPage> com.auth0.client.mgmt.RulesEntity#listAll(RulesFilter filter)` instead.
- Deprecated `void com.auth0.json.mgmt.guardian.EnrollmentTicket#setUserId(String id)` has been removed. Use the constructor instead.
- Deprecated `com.auth0.json.mgmt.guardian.SNSFactorProvider` no-arg constructor has been removed. Use the full constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.SNSFactorProvider#setAWSAccessKeyId(String awsAccessKeyId)` has been removed. Use the constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.SNSFactorProvider#setAWSSecretAccessKey(String awsSecretAccessKey)` has been removed. Use the constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.SNSFactorProvider#setAWSRegion(String awsRegion)` has been removed. Use the constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.SNSFactorProvider#setSNSAPNSPlatformApplicationARN(String apnARN)` has been removed. Use the constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.SNSFactorProvider#setSNSGCMPlatformApplicationARN(String gcmARN)` has been removed. Use the constructor instead.
- Deprecated `com.auth0.json.mgmt.guardian.TwilioFactorProvider` no-arg constructor has been removed. Use the full constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.TwilioFactorProvider#setFrom(String from)` has been removed. Use the constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.TwilioFactorProvider#setMessagingServiceSID(String messagingServiceSID)` has been removed. Use the constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.TwilioFactorProvider#setAuthToken(String authToken)` has been removed. Use the constructor instead.
- Deprecated `void com.auth0.json.mgmt.guardian.TwilioFactorProvider#setSID(String SID)` has been removed. Use the constructor instead.
- The default implementation of `com.auth0.net.Request#executeAsync()` has been removed; implementations must provide an implementation of `executeAsync`.

### New classes and methods

#### Refactored HTTP layer types

Version 2 introduces a new abstraction, `com.auth0.net.client.Auth0HttpClient`, to handle the core HTTP responsibilities of sending HTTP requests.
An implementation is provided in `DefaultHttpClient`, which supports all the configurations available in the now-deprecated `HttpOptions`.
In addition to these configurations, it is also possible to implement the `Auth0HttpClient` for advanced use-cases where the default implementation or its configurations are not sufficient.
Several new types have been added to support this:

- `com.auth0.net.client.Auth0HttpClient` has been added to define the HTTP client interface.
- `com.auth0.net.client.DefaultHttpClient` is the default HTTP implementation that should be used in the majority of cases. It supports the same configurations as `HttpOptions`, but can be reused across API clients. It uses `OkHttp` as the networking client internally.
- `com.auth0.net.client.Auth0HttpRequest` is a lightweight representation of an HTTP request to execute. Internal API implementations will form the request.
- `com.auth0.net.client.Auth0HttpResponse` is a lightweight representation of an HTTP response. Internal API implementations will parse the response.
- `com.auth0.net.client.HttpMethod` is an `enum` representing the HTTP methods.

### New deprecations

- `com.auth0.client.HttpOptions` has been deprecated, in favor of configuring the `DefaultHttpClient` directly.
- `com.auth0.client.mgmt.ManagementAPI` constructors have been deprecated in favor of `ManagementAPI#newBuilder(String domain, String apiToken)`.
- `com.auth0.client.auth.AuthAPI` constructors have been deprecated in favor of `AuthAPI.newBuilder(String domain, String clientId)` and `AuthAPI.newBuilder(String domain, String clientId, String clientSecret)`.
