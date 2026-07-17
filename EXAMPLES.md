# Examples using auth0-java

- [Error handling](#error-handling)
- [HTTP Client configuration](#http-client-configuration)
- [Management API usage](#management-api-usage)
- [Verifying an ID token](#verifying-an-id-token)
- [Organizations](#organizations)
- [Token Vault](#token-vault)
- [Logging](#logging)
- [Asynchronous operations](#asynchronous-operations)

## Error handling

### Management API errors

The Management API uses a unified `ManagementApiException` class, which provides access to the HTTP status code, response body, headers, and message of the error response.

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

### Authentication API errors

The Authentication API uses `APIException` for error handling.

```java
import com.auth0.exception.APIException;

try {
    TokenHolder holder = authAPI.requestToken("https://{YOUR_DOMAIN}/api/v2/").execute().getBody();
} catch (APIException e) {
    int statusCode = e.getStatusCode();
    String description = e.getDescription();
    String error = e.getError();
}
```

## HTTP Client configuration

### Authentication API

The Authentication API client uses the OkHttp networking library to make HTTP requests.
The client can be configured by building a `DefaultHttpClient` and providing it to the API client.

```java
Auth0HttpClient httpClient = DefaultHttpClient.newBuilder()
        // configure as needed
        .build();

AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}")
        .withHttpClient(httpClient)
        .build();
```

If the `DefaultHttpClient` does not support your required networking client configuration, you may choose to implement
your own client by implementing the `Auth0HttpClient` interface and providing it to the API client. This is an advanced
use case and should be used only when necessary.

### Management API

The Management API client uses `ManagementApi` as the main entry point.
You can configure a custom `OkHttpClient` via the builder:

```java
import com.auth0.client.mgmt.ManagementApi;

OkHttpClient okHttpClient = new OkHttpClient.Builder()
        // configure as needed
        .build();

ManagementApi client = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .token("{YOUR_API_TOKEN}")
        .httpClient(okHttpClient)
        .build();
```

## Management API usage

### Creating a client with a static token

```java
import com.auth0.client.mgmt.ManagementApi;

ManagementApi client = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .token("{YOUR_API_TOKEN}")
        .build();
```

### Creating a client with client credentials (recommended)

When using client credentials, the SDK automatically fetches and caches access tokens, refreshing them before expiry.

```java
import com.auth0.client.mgmt.ManagementApi;

ManagementApi client = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .clientCredentials("{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}")
        .build();
```

You can also specify a custom audience:

```java
ManagementApi client = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .clientCredentials("{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}")
        .audience("{YOUR_AUDIENCE}")
        .build();
```

### Creating a client with advanced options

```java
ManagementApi client = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .clientCredentials("{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}")
        .timeout(30)        // timeout in seconds (default: 60)
        .maxRetries(3)      // max retries (default: 2)
        .addHeader("X-Custom-Header", "value")
        .build();
```

### Accessing resources

The Management API is organized into resource clients:

```java
// Get a user
GetUserResponseContent user = client.users().get("user_id");

// Get a role
GetRoleResponseContent role = client.roles().get("role_id");

// Create a user
CreateUserResponseContent newUser = client.users().create(
        CreateUserRequestContent.builder()
                .email("user@example.com")
                .connection("Username-Password-Authentication")
                .password("securePassword123!")
                .build()
);

// Update a user
UpdateUserResponseContent updatedUser = client.users().update(
        "user_id",
        UpdateUserRequestContent.builder()
                .name("Updated Name")
                .build()
);

// Delete a user
client.users().delete("user_id");
```

### Pagination

List operations return a `SyncPagingIterable` that supports automatic pagination:

```java
import com.auth0.client.mgmt.core.SyncPagingIterable;
import com.auth0.client.mgmt.types.ListUsersRequestParameters;
import com.auth0.client.mgmt.types.UserResponseSchema;

SyncPagingIterable<UserResponseSchema> users = client.users().list(
        ListUsersRequestParameters.builder()
                .perPage(50)
                .build()
);

// Iterate through all pages automatically
for (UserResponseSchema user : users) {
    System.out.println(user.getEmail());
}

// Or access pages manually
List<UserResponseSchema> pageItems = users.getItems();
while (users.hasNext()) {
    pageItems = users.nextPage().getItems();
}
```

### Accessing pagination metadata

List responses are wrapped in a metadata envelope (`total`, `start`, `limit`, `length`, etc.).
The items come from the iterable as usual; the envelope is read via `getResponse()`, typed to
the endpoint's response class (for users, `ListUsersOffsetPaginatedResponseContent`):

```java
SyncPagingIterable<UserResponseSchema> page = client.users().list(
        ListUsersRequestParameters.builder().perPage(50).build()
);

Optional<ListUsersOffsetPaginatedResponseContent> listUsersOffsetPaginatedResponseContentOptional = resp.getResponse();
listUsersOffsetPaginatedResponseContentOptional.ifPresent(m -> {
        m.getTotal().ifPresent(total   -> System.out.println("total: "  + total));
        m.getStart().ifPresent(start   -> System.out.println("start: "  + start));
        m.getLimit().ifPresent(limit   -> System.out.println("limit: "  + limit));
        m.getLength().ifPresent(length -> System.out.println("length: " + length));
});

```

### Accessing raw HTTP responses

Use `withRawResponse()` to access HTTP response metadata:

```java
import com.auth0.client.mgmt.core.ManagementApiHttpResponse;

ManagementApiHttpResponse<GetUserResponseContent> response = client.users()
        .withRawResponse()
        .get("user_id");

GetUserResponseContent user = response.body();
Map<String, List<String>> headers = response.headers();
```

### Nested sub-resources

Some resources have nested sub-clients:

```java
// List a user's roles
SyncPagingIterable<Role> roles = client.users().roles().list("user_id");

// List a user's permissions
SyncPagingIterable<Permission> permissions = client.users().permissions().list("user_id");

// List organization members
SyncPagingIterable<OrganizationMember> members = client.organizations().members().list("org_id");
```

## Verifying an ID token

This library also provides the ability to validate an OIDC-compliant ID Token, according to the [OIDC Specification](https://openid.net/specs/openid-connect-core-1_0-final.html#IDTokenValidation).

### Verifying an ID Token signed with the RS256 signing algorithm

To verify an ID Token that is signed using the RS256 signing algorithm, you will need to provide an implementation of
`PublicKeyProvider` that will return the public key used to verify the token's signature. The example below demonstrates how to use the `JwkProvider` from the [jwks-rsa-java](https://github.com/auth0/jwks-rsa-java) library:

```java
JwkProvider provider = new JwkProviderBuilder("https://your-domain.auth0.com").build();
SignatureVerifier signatureVerifier = SignatureVerifier.forRS256(new PublicKeyProvider() {
    @Override
    public RSAPublicKey getPublicKeyById(String keyId) throws PublicKeyProviderException {
       try {
            return (RSAPublicKey) provider.get(keyId).getPublicKey();
        } catch (JwkException jwke) {
            throw new PublicKeyProviderException("Error obtaining public key", jwke);
        }
    }
}

IdTokenVerifier idTokenVerifier = IdTokenVerifier.init("https://your-domain.auth0.com/","your-client-id", signatureVerifier).build();

try {
    idTokenVerifier.verify("token", "expected-nonce");
} catch(IdTokenValidationException idtve) {
    // Handle invalid token exception
}
```

### Verifying an ID Token signed with the HS256 signing algorithm

To verify an ID Token that is signed using the HS256 signing algorithm:

```java
SignatureVerifier signatureVerifier = SignatureVerifier.forHS256("your-client-secret");
IdTokenVerifier idTokenVerifier = IdTokenVerifier.init("https://your-domain.auth0.com/","your-client-id", signatureVerifier).build();

try {
    idTokenVerifier.verify("token", "expected-nonce");
} catch(IdTokenValidationException idtve) {
    // Handle invalid token exception
}
```

## Organizations

[Organizations](https://auth0.com/docs/organizations) is a set of features that provide better support for developers who build and maintain SaaS and Business-to-Business (B2B) applications.

Note that Organizations is currently only available to customers on our Enterprise and Startup subscription plans.

### Log in to an organization

Log in to an organization by using `withOrganization()` when building the Authorization URL:

```java
AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
String url = auth.authorizeUrl("https://me.auth0.com/callback")
    .withOrganization("{YOUR_ORGANIZATION_ID}")
    .build();
```

**Important!** When logging into an organization, it is important to ensure the `org_id` claim of the ID Token matches the expected organization value. The `IdTokenVerifier` can be configured with an expected `org_id` claim value, as the example below demonstrates.
For more information, please read [Work with Tokens and Organizations](https://auth0.com/docs/organizations/using-tokens) on Auth0 Docs.
```java
IdTokenVerifier.init("{ISSUER}", "{AUDIENCE}", signatureVerifier)
    .withOrganization("{ORG_ID}")
    .build()
    .verify(jwt);
```

### Accept user invitations

Accept a user invitation by using `withInvitation()` when building the Authorization URL:

```java
AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
String url = auth.authorizeUrl("https://me.auth0.com/callback")
    .withOrganization("{YOUR_ORGANIZATION_ID}")
    .withInvitation("{YOUR_INVITATION_ID}")
    .build();
```

## Token Vault

[Token Vault](https://auth0.com/docs/secure/tokens/token-vault) lets you exchange an Auth0 token for a federated identity provider's access token, so your application can call the provider's APIs on the user's behalf. The connection must have Token Vault enabled, and because this exchange requires a private client, the `AuthAPI` instance must be configured with a client secret or client assertion.

### Exchange a refresh token

Use `getTokenForConnectionWithRefreshToken()` to exchange an Auth0 refresh token for the federated provider's access token:

```java
AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
TokenHolder result = auth.getTokenForConnectionWithRefreshToken("google-oauth2", "{REFRESH_TOKEN}", null)
    .execute()
    .getBody();
String federatedAccessToken = result.getAccessToken();
```

### Exchange an access token

Use `getTokenForConnectionWithAccessToken()` to exchange an Auth0 access token instead:

```java
TokenHolder result = auth.getTokenForConnectionWithAccessToken("google-oauth2", "{ACCESS_TOKEN}", null)
    .execute()
    .getBody();
String federatedAccessToken = result.getAccessToken();
```

### Specifying a login hint

When a user has multiple accounts on the same connection, pass their ID within the identity provider as the `loginHint` (the final argument). Pass `null` to omit it:

```java
TokenHolder result = auth.getTokenForConnectionWithRefreshToken("google-oauth2", "{REFRESH_TOKEN}", "{GOOGLE_USER_ID}")
    .execute()
    .getBody();
```

### Exchanging other token types

For full control over the subject token type, use the generic `getTokenForConnection()` and supply the `subject_token_type` URN yourself:

```java
TokenHolder result = auth.getTokenForConnection(
        "google-oauth2",
        "{SUBJECT_TOKEN}",
        "urn:ietf:params:oauth:token-type:refresh_token",
        "{GOOGLE_USER_ID}")
    .execute()
    .getBody();
```

## Logging

The SDK is silent by default. You can enable logging to see HTTP requests and responses, which is useful for debugging.

### Enable logging with default settings

```java
import com.auth0.client.mgmt.ManagementApi;
import com.auth0.client.mgmt.core.LogConfig;

ManagementApi client = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .token("{YOUR_API_TOKEN}")
        .logging(LogConfig.builder()
                .silent(false)
                .build())
        .build();
```

### Enable debug-level logging

```java
import com.auth0.client.mgmt.core.LogConfig;
import com.auth0.client.mgmt.core.LogLevel;

ManagementApi client = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .token("{YOUR_API_TOKEN}")
        .logging(LogConfig.builder()
                .level(LogLevel.DEBUG)
                .silent(false)
                .build())
        .build();
```

### Using a custom logger

```java
import com.auth0.client.mgmt.core.ILogger;
import com.auth0.client.mgmt.core.LogConfig;
import com.auth0.client.mgmt.core.LogLevel;

ManagementApi client = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .token("{YOUR_API_TOKEN}")
        .logging(LogConfig.builder()
                .level(LogLevel.DEBUG)
                .logger(new MyCustomLogger()) // implements ILogger
                .silent(false)
                .build())
        .build();
```

Logging is also available on the async client:

```java
AsyncManagementApi asyncClient = AsyncManagementApi.builder()
        .token("{YOUR_API_TOKEN}")
        .tenantDomain("{YOUR_DOMAIN}")
        .logging(LogConfig.builder()
                .silent(false)
                .build())
        .build();
```

## Asynchronous operations

### Management API

The Management API provides an async client that returns `CompletableFuture` for all operations:

```java
import com.auth0.client.mgmt.AsyncManagementApi;
import com.auth0.client.mgmt.core.ClientOptions;

AsyncManagementApi asyncClient = new AsyncManagementApi(clientOptions);

// Async user retrieval
CompletableFuture<GetUserResponseContent> future = asyncClient.users().get("user_id");
future.thenAccept(user -> {
    System.out.println(user.getEmail());
});
```

### Authentication API

The Authentication API supports async operations via the `executeAsync()` method:

```java
AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
CompletableFuture<TokenHolder> future = auth.requestToken("https://{YOUR_DOMAIN}/api/v2/").executeAsync();
future.thenAccept(holder -> {
    String accessToken = holder.getBody().getAccessToken();
});
```
