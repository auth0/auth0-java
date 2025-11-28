# V3 Migration Guide

A guide to migrating the Auth0 Java SDK from `v2` to `v3`.

- [Overall changes](#overall-changes)
    - [Java versions](#java-versions)
    - [Authentication API](#authentication-api)
    - [Management API](#management-api)
- [Specific changes to the Management API](#specific-changes-to-the-management-api)
    - [Client initialization](#client-initialization)
    - [Sub-client organization](#sub-client-organization)
    - [Request and response patterns](#request-and-response-patterns)
    - [Pagination](#pagination)
    - [Exception handling](#exception-handling)
    - [Accessing raw HTTP responses](#accessing-raw-http-responses)
    - [Request-level configuration](#request-level-configuration)
    - [Type changes](#type-changes)

## Overall changes

### Java versions

Both v2 and v3 require Java 8 or above.

### Authentication API

This major version change does not affect the Authentication API. The `AuthAPI` class has been ported directly from v2 to v3. Any code written for the Authentication API in the v2 version should work in the v3 version.

```java
// Works in both v2 and v3
AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
```

### Management API

V3 introduces significant improvements to the Management API SDK by migrating to [Fern](https://github.com/fern-api/fern) as the code generation tool. This provides:

- Better resource grouping with sub-client organization
- Type-safe request and response objects using builder patterns
- Automatic pagination with `SyncPagingIterable<T>`
- Simplified access to HTTP response metadata via `withRawResponse()`
- Consistent method naming (`list`, `create`, `get`, `update`, `delete`)

## Specific changes to the Management API

### Client initialization

The Management API client initialization has changed from `ManagementAPI` to `ManagementApi`, and uses a different builder pattern.

**v2:**
```java
import com.auth0.client.mgmt.ManagementAPI;

// Using domain and token
ManagementAPI mgmt = ManagementAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_API_TOKEN}").build();

// Using TokenProvider
TokenProvider tokenProvider = SimpleTokenProvider.create("{YOUR_API_TOKEN}");
ManagementAPI mgmt = ManagementAPI.newBuilder("{YOUR_DOMAIN}", tokenProvider).build();
```

**v3:**
1st Approach : Standard Token-Based
```java
import com.auth0.client.mgmt.ManagementApi;

ManagementApi client = ManagementApi
    .builder()
    .url("https://{YOUR_DOMAIN}/api/v2")
    .token("{YOUR_API_TOKEN}")
    .build();
```

or 

2nd Approach : OAuth client credentials flow 

```java
OAuthTokenSupplier tokenSupplier = new OAuthTokenSupplier(
"{CLIENT_ID}",
"{CLIENT_SECRET}",
"https://{YOUR_DOMAIN}",
"{YOUR_AUDIENCE}"
);

ClientOptions clientOptions = ClientOptions.builder()
.environment(Environment.custom("https://{YOUR_AUDIENCE}"))
.addHeader("Authorization", () -> "Bearer " + tokenSupplier.get())
.build();

ManagementApi client = new ManagementApi(clientOptions);

```

#### Builder options comparison

| Option | v2 | v3 |
|--------|----|----|
| Domain/URL | `newBuilder(domain, token)` | `.url("https://domain/api/v2")` |
| Token | Constructor parameter | `.token(token)` |
| Timeout | Via `HttpOptions` | `.timeout(seconds)` |
| Max retries | Via `HttpOptions` | `.maxRetries(count)` |
| Custom HTTP client | `.withHttpClient(Auth0HttpClient)` | `.httpClient(OkHttpClient)` |
| Custom headers | Not directly supported | `.addHeader(name, value)` |

### Sub-client organization

V3 introduces a hierarchical sub-client structure. Operations on related resources are now accessed through nested clients instead of methods on a flat entity class.

**v2:**
```java
// All user operations on UsersEntity
Request<User> userRequest = mgmt.users().get("user_id", new UserFilter());
Request<List<Permission>> permissionsRequest = mgmt.users().getPermissions("user_id", new PermissionsFilter());
Request<List<Role>> rolesRequest = mgmt.users().getRoles("user_id", new RolesFilter());
Request<LogEventsPage> logsRequest = mgmt.users().getLogEvents("user_id", new LogEventFilter());
```

**v3:**
```java
// Operations organized into sub-clients
GetUserResponseContent user = client.users().get("user_id");
SyncPagingIterable<Permission> permissions = client.users().permissions().list("user_id");
SyncPagingIterable<Role> roles = client.users().roles().list("user_id");
SyncPagingIterable<LogEvent> logs = client.users().logs().list("user_id");
```

#### Common sub-client mappings

| v2 Method | v3 Sub-client |
|-----------|---------------|
| `mgmt.users().getPermissions()` | `client.users().permissions().list()` |
| `mgmt.users().getRoles()` | `client.users().roles().list()` |
| `mgmt.users().getLogEvents()` | `client.users().logs().list()` |
| `mgmt.users().getOrganizations()` | `client.users().organizations().list()` |
| `mgmt.users().link()` | `client.users().identities().link()` |
| `mgmt.users().unlink()` | `client.users().identities().delete()` |
| `mgmt.users().deleteMultifactorProvider()` | `client.users().multifactor().deleteProvider()` |
| `mgmt.organizations().getMembers()` | `client.organizations().members().list()` |
| `mgmt.organizations().getInvitations()` | `client.organizations().invitations().list()` |
| `mgmt.organizations().getEnabledConnections()` | `client.organizations().enabledConnections().list()` |
| `mgmt.actions().getVersions()` | `client.actions().versions().list()` |
| `mgmt.actions().getTriggerBindings()` | `client.actions().triggers().bindings().list()` |
| `mgmt.guardian().getFactors()` | `client.guardian().factors().list()` |
| `mgmt.branding().getUniversalLoginTemplate()` | `client.branding().templates().getUniversalLogin()` |
| `mgmt.connections().getScimConfiguration()` | `client.connections().scimConfiguration().get()` |

### Request and response patterns

V3 uses type-safe request content objects with builders instead of domain objects or filter parameters.

**v2:**
```java
import com.auth0.json.mgmt.users.User;
import com.auth0.net.Request;

// Creating a user
User user = new User("Username-Password-Authentication");
user.setEmail("test@example.com");
user.setPassword("password123".toCharArray());

Request<User> request = mgmt.users().create(user);
User createdUser = request.execute().getBody();
```

**v3:**
```java
import com.auth0.client.mgmt.types.CreateUserRequestContent;
import com.auth0.client.mgmt.types.CreateUserResponseContent;

// Creating a user
CreateUserResponseContent user = client.users().create(
    CreateUserRequestContent
        .builder()
        .connection("Username-Password-Authentication")
        .email("test@example.com")
        .password("password123")
        .build()
);
```

#### Key differences

| Aspect | v2 | v3 |
|--------|----|----|
| Request building | Domain objects with setters | Builder pattern with `*RequestContent` types |
| Response type | `Request<T>` requiring `.execute().getBody()` | Direct return of response object |
| Filtering | Filter classes (e.g., `UserFilter`) | `*RequestParameters` builder classes |
| Execution | Explicit `.execute()` call | Implicit execution on method call |

### Pagination

V3 introduces `SyncPagingIterable<T>` for automatic pagination, replacing the manual `Request<Page>` pattern.

**v2:**
```java
import com.auth0.json.mgmt.users.UsersPage;
import com.auth0.client.mgmt.filter.UserFilter;

Request<UsersPage> request = mgmt.users().list(new UserFilter().withPage(0, 50));
UsersPage page = request.execute().getBody();

for (User user : page.getItems()) {
    System.out.println(user.getEmail());
}

// Manual pagination
while (page.getNext() != null) {
    request = mgmt.users().list(new UserFilter().withPage(page.getNext(), 50));
    page = request.execute().getBody();
    for (User user : page.getItems()) {
        System.out.println(user.getEmail());
    }
}
```

**v3:**
```java
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.UserResponseSchema;
import com.auth0.client.mgmt.types.ListUsersRequestParameters;

// Automatic iteration through all pages
SyncPagingIterable<UserResponseSchema> users = client.users().list(
    ListUsersRequestParameters
        .builder()
        .perPage(50)
        .build()
);

for (UserResponseSchema user : users) {
    System.out.println(user.getEmail());
}

// Or manual page control
List<UserResponseSchema> pageItems = users.getItems();
while (users.hasNext()) {
    pageItems = users.nextPage().getItems();
    // process page
}
```

### Exception handling

V3 uses a unified `ManagementApiException` class instead of the v2 exception hierarchy.

**v2:**
```java
import com.auth0.exception.Auth0Exception;
import com.auth0.exception.APIException;
import com.auth0.exception.RateLimitException;

try {
    User user = mgmt.users().get("user_id", null).execute().getBody();
} catch (RateLimitException e) {
    // Rate limited
    long retryAfter = e.getLimit();
} catch (APIException e) {
    int statusCode = e.getStatusCode();
    String error = e.getError();
    String description = e.getDescription();
} catch (Auth0Exception e) {
    // Network or other errors
}
```

**v3:**
```java
import com.auth0.client.mgmt.core.ManagementApiException;

try {
    GetUserResponseContent user = client.users().get("user_id");
} catch (ManagementApiException e) {
    int statusCode = e.statusCode();
    Object body = e.body();
    Map<String, List<String>> headers = e.headers();
    String message = e.getMessage();
}
```

### Accessing raw HTTP responses

V3 provides access to full HTTP response metadata via `withRawResponse()`.

**v2:**
```java
// Response wrapper provided status code
Response<User> response = mgmt.users().get("user_id", null).execute();
int statusCode = response.getStatusCode();
User user = response.getBody();
```

**v3:**
```java
import com.auth0.client.mgmt.core.ManagementApiHttpResponse;

// Use withRawResponse() to access headers and metadata
ManagementApiHttpResponse<GetUserResponseContent> response = client.users()
    .withRawResponse()
    .get("user_id");

GetUserResponseContent user = response.body();
Map<String, List<String>> headers = response.headers();
```

### Request-level configuration

V3 allows per-request configuration through `RequestOptions`.

**v2:**
```java
// Most configuration was at client level only
// Request-level headers required creating a new request manually
Request<User> request = mgmt.users().get("user_id", null);
request.addHeader("X-Custom-Header", "value");
User user = request.execute().getBody();
```

**v3:**
```java
import com.auth0.client.mgmt.core.RequestOptions;

GetUserResponseContent user = client.users().get(
    "user_id",
    GetUserRequestParameters.builder().build(),
    RequestOptions.builder()
        .timeout(10)
        .maxRetries(1)
        .addHeader("X-Custom-Header", "value")
        .build()
);
```

### Type changes

V3 uses generated type classes located in `com.auth0.client.mgmt.types` instead of the hand-written POJOs in `com.auth0.json.mgmt`.

**v2:**
```java
import com.auth0.json.mgmt.users.User;
import com.auth0.json.mgmt.roles.Role;
import com.auth0.json.mgmt.organizations.Organization;
```

**v3:**
```java
import com.auth0.client.mgmt.types.UserResponseSchema;
import com.auth0.client.mgmt.types.CreateUserRequestContent;
import com.auth0.client.mgmt.types.CreateUserResponseContent;
import com.auth0.client.mgmt.types.Role;
import com.auth0.client.mgmt.types.Organization;
```

Type naming conventions in v3:
- Request body types: `*RequestContent` (e.g., `CreateUserRequestContent`)
- Response types: `*ResponseContent` or `*ResponseSchema` (e.g., `GetUserResponseContent`, `UserResponseSchema`)
- Query parameters: `*RequestParameters` (e.g., `ListUsersRequestParameters`)

All types use immutable builders:

```java
// v3 type construction
CreateUserRequestContent request = CreateUserRequestContent
    .builder()
    .email("test@example.com")
    .connection("Username-Password-Authentication")
    .password("secure-password")
    .build();
```
