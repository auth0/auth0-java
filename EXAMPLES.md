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
    UsersPage usersPage = request.execute();
} catch(APIException apiException) {
    apiException.getStatusCode(); // 400
    apiException.getError(); // "operation_not_supported"
    apiException.getDescription(); // "You are not allowed to use search_engine=v1."
}
```

## HTTP Client configuration

Both the Authentication and Management API clients use the OkHttp networking library. Certain configurations of the client are available via the `HttpOptions` object, which can passed to both API client constructors.

```java
HttpOptions options = new HttpOptions();

// configure timeouts; default is ten seconds for both connect and read timeouts:
options.setConnectTimeout(5);
options.setReadTimeout(15);

// configure proxy:
Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("{IP-ADDRESS}", {PORT}));
ProxyOptions proxyOptions = new ProxyOptions(proxy);
options.setProxyOptions(proxyOptions);

// create client
AuthAPI authAPI = new AuthAPI("{CLIENT_ID}", "{CLIENT_SECRET}", options);
```

## Verifying an ID token

This library also provides the ability to validate an OIDC-compliant ID Token, according to the [OIDC Specification](https://openid.net/specs/openid-connect-core-1_0-final.html#IDTokenValidation).

### Verifying an ID Token signed with the RS256 signing algorithm

To verify an ID Token that is signed using the RS256 signing algorithm, you will need to provide an implementation of
`PublicKeyProvider` that will return the public key used to verify the token's signature. The example below demonstrates how to use the `JwkProvider` from the [jwks-rsa-java](https://github.com/auth0/jwks-rsa-java) library:

```java
JwkProvider provider = new JwkProviderBuilder("https://your-domain.auth0.com").build();
SignatureVerifier sigVerifier = SignatureVerifier.forRS256(new PublicKeyProvider() {
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

Using Organizations, you can:

- Represent teams, business customers, partner companies, or any logical grouping of users that should have different ways of accessing your applications, as organizations.
- Manage their membership in a variety of ways, including user invitation.
- Configure branded, federated login flows for each organization.
- Implement role-based access control, such that users can have different roles when authenticating in the context of different organizations.
- Build administration capabilities into your products, using Organizations APIs, so that those businesses can manage their own organizations.

Note that Organizations is currently only available to customers on our Enterprise and Startup subscription plans.

### Log in to an organization

Log in to an organization by using `withOrganization()` when building the Authorization URL:

```java
AuthAPI auth = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
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
AuthAPI auth = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
String url = auth.authorizeUrl("https://me.auth0.com/callback")
    .withOrganization("{YOUR_ORGANIZATION_ID")
    .withInvitation("{YOUR_INVITATION_ID}")
    .build();
```

## Asynchronous operations

Requests can be executed asynchronously, using the `executeAsync()` method, which returns a `CompletableFuture<T>`.

```java
CompletableFuture<User> userFuture = mgmt.users().getUser("auth0|123", new UserFilter()).executeAsync();
User user = userFuture.get();
```
