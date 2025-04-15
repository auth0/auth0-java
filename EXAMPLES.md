# Examples using auth0-java

- [Error handling](#error-handling)
- [HTTP Client configuration](#http-client-configuration)
- [Verifying an ID token](#verifying-an-id-token)
- [Organizations](#organizations)
- [Asynchronous operations](#asynchronous-operations)

## Error handling

The API Clients throw an `Auth0Exception` when an unexpected error happens on a request execution, for example a connection or timeout error.

An `APIException` will be thrown if the network request succeeded, but another error occurred.

```java
Request<UsersPage> request = api.users().list(new UserFilter().withSearchEngine("v1"));
try {
    UsersPage usersPage = request.execute().getBody();
} catch(APIException apiException) {
    apiException.getStatusCode(); // 400
    apiException.getError(); // "operation_not_supported"
    apiException.getDescription(); // "You are not allowed to use search_engine=v1."
}
```

## HTTP Client configuration

By default, both the Authentication and Management API clients use the OkHttp networking library to make HTTP requests.
The client can be configured by building a `DefaultHttpClient` and providing it to the API clients.
If using both the Management and Authentication API clients, it is recommended to create one `Auth0HttpClient` to be used by both API clients to minimize resource usage.

```java
Auth0HttpClient client = DefaultHttpClient.newBuilder()
        // configure as needed
        .build();

AuthAPI auth = AuthAPI.newBuilder("DOMAIN", "CLIENT-ID", "CLIENT-SECRET")
        .withHttpClient(client)
        .build();

ManagementAPI mgmt = ManagementAPI.newBuilder("DOMAIN", "API-TOKEN")
        .withHttpClient(client)
        .build();
```

If the `DefaultHttpClient` does not support your required networking client configuration, you may choose to implement
your own client by implementing the `Auth0HttpClient` interface and providing it to the API clients. This is an advanced
use case and should be used only when necessary.

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
    .withOrganization("{YOUR_ORGANIZATION_ID")
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

```
AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
String url = auth.authorizeUrl("https://me.auth0.com/callback")
    .withOrganization("{YOUR_ORGANIZATION_ID")
    .withInvitation("{YOUR_INVITATION_ID}")
    .build();
```

## Asynchronous operations

Requests can be executed asynchronously, using the `executeAsync()` method, which returns a `CompletableFuture<T>`.

```java
CompletableFuture<Response<User>> userFuture = mgmt.users().getUser("auth0|123", new UserFilter()).executeAsync();
User user = userFuture.get().getBody();
```
