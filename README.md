# Auth0 Java

[![Build][travis-ci-badge]][travis-ci-url]
[![MIT][mit-badge]][mit-url]
[![Maven][maven-badge]][maven-url]
[![JCenter][jcenter-badge]][jcenter-url]

Java client library for the [Auth0](https://auth0.com) platform.

## Download

Get Auth0 Java via Maven:

```xml
<dependency>
  <groupId>com.auth0</groupId>
  <artifactId>auth0</artifactId>
  <version>1.0.0</version>
</dependency>
```

or Gradle:

```gradle
compile 'com.auth0:auth0:1.0.0'
```

Make sure your client type is set to `Non Interactive Client`.


## Authentication API

The implementation is based on the [Authentication API Docs](https://auth0.com/docs/api/authentication).

Create a new `AuthAPI` instance by providing the client data from the [dashboard](https://manage.auth0.com/#/clients).

```java
AuthAPI auth = new AuthAPI("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}");
```

### Authorize - /authorize

Creates an `AuthorizeUrlBuilder` to authenticate the user with an OAuth provider. The `redirectUri` must be white-listed in the "Allowed Callback URLs" section of the Client Settings. Parameters can be added to the final url by using the builder methods. When ready, call `build()` and obtain the Url.

`AuthorizeUrlBuilder authorize("{CONNECTION}", "{REDIRECT_URI}")`

Example:
```java
AuthorizeUrlBuilder builder = auth.authorize("facebook", "https://me.auth0.com/callback");
builder.withAudience("https://api.me.auth0.com/users");
builder.withScope("openid contacts");
builder.withState("state123");

String url = builder.build();
// https://me.auth0.com/authorize?redirect_uri=https://me.auth0.com/callback&response_type=code&client_id=iaih2D7AC56kksdkPtWAsjI3li&audience=https://api.me.auth0.com/users&scope=openid%20contacts&connection=facebook&state=state123
```

### Logout - /v2/logout
Creates a `LogoutUrlBuilder` to log out the user. The `returnToUrl` must be white-listed in the "Allowed Logout URLs" section of the Client Settings. Parameters can be added to the final url by using the builder methods. When ready, call `build()` and obtain the Url.

`LogoutUrlBuilder logout("{RETURN_TO_URL}", "{SEND_CLIENT_ID}")`

Example:
```java
LogoutUrlBuilder builder = auth.logout("https://me.auth0.com/home", true);
builder.useFederated(true);
builder.withAccessToken("aToKen");

String url = builder.build();
// https://me.auth0.com/v2/logout?returnTo=https://me.auth0.com/home&client_id=iaih2D7AC56kksdkPtWAsjI3li&access_token=aToKen&federated=
```

### UserInfo - /userinfo
Creates a new request to get the user information associated to a given access token. This will only work if the token has been granted the `openid` scope.

`Request<UserInfo> userInfo("{ACCESS_TOKEN}")`

Example:
```java
Request<UserInfo> request = auth.userInfo("nisd1h9dk.....s1doWJOsaf");
try {
    UserInfo info = request.execute();
    // info.getValues();
} catch (RequestFailedException exception) {
    // request error
} catch (AuthenticationException exception) {
    // api error
}
```

### Reset Password - /dbconnections/change_password
Creates a new request to reset the user's password. This will only work for db connections.

`Request resetPassword("{EMAIL}", "{CONNECTION}")`

Example:
```java
Request request = auth.resetPassword("user@domain.com", "Username-Password-Authentication");
try {
    request.execute();
} catch (RequestFailedException exception) {
    // request error
} catch (AuthenticationException exception) {
    // api error
}
```


### Sign Up - /dbconnections/signup
Creates a new request to create a new user. Up to 10 additional Sign Up fields can be added to the request. This will only work for db connections.

`SignUpRequest signUp("{EMAIL}", "{USERNAME}", "{PASSWORD}", "{CONNECTION}")`

`SignUpRequest signUp("{EMAIL}", "{PASSWORD}", "{CONNECTION}")`

Example:
```java
SignUpRequest request = auth.signUp("user@domain.com", "username", "password123", "Username-Password-Authentication");
Map<String, String> fields = new HashMap<>();
fields.put("location", "Buenos Aires");
fields.put("age", "25");
request.setCustomFields(fields);
try {
    request.execute();
} catch (RequestFailedException exception) {
    // request error
} catch (AuthenticationException exception) {
    // api error
}
```

### Log In with Authorization Code - /oauth/token

Creates a new request to log in the user with a `code` previously obtained by calling the /authorize endpoint. The redirect uri must be the one sent in the /authorize call.

`AuthRequest loginWithAuthorizationCode("{CODE}", "{REDIRECT_URI}")`

Example:
```java
AuthRequest request = loginWithAuthorizationCode("asdfgh", "https://me.auth0.com/callback");
request.setAudience("https://api.me.auth0.com/users");
request.setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
    // holder.getAccessToken();
} catch (RequestFailedException exception) {
    // request error
} catch (AuthenticationException exception) {
    // api error
}
```

### Log In with Password - /oauth/token

Creates a new request to log in the user with `username` and `password`. The connection used is the one defined as "Default Directory" in the account settings.

`AuthRequest loginWithPassword("{USERNAME_OR_EMAIL}", "{PASSWORD}")`

Example:
```java
AuthRequest request = loginWithPassword("me@domain.com", "password123");
request.setAudience("https://api.me.auth0.com/users");
request.setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
    // holder.getAccessToken();
} catch (RequestFailedException exception) {
    // request error
} catch (AuthenticationException exception) {
    // api error
}
```

### Log In with Password Realm - /oauth/token

Creates a new request to log in the user with `username` and `password` using the Password Realm.

`AuthRequest loginWithPasswordRealm("{USERNAME_OR_EMAIL}", "{PASSWORD}", "{REALM}")`

Example:
```java
AuthRequest request = loginWithPasswordRealm("me@domain.com", "password123", "Username-Password-Authentication");
request.setAudience("https://api.me.auth0.com/users");
request.setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
    // holder.getAccessToken();
} catch (RequestFailedException exception) {
    // request error
} catch (AuthenticationException exception) {
    // api error
}
```

### Log In with Client Credentials - /oauth/token

Creates a new request to log in the user using the Client Credentials.

`AuthRequest loginWithClientCredentials("{AUDIENCE}")`

Example:
```java
AuthRequest request = loginWithClientCredentials("https://api.me.auth0.com/users");
request.setScope("openid contacts");
try {
    TokenHolder holder = request.execute();
    // holder.getAccessToken();
} catch (RequestFailedException exception) {
    // request error
} catch (AuthenticationException exception) {
    // api error
}
```

## Documentation

For more information about [auth0](http://auth0.com) check our [documentation page](http://docs.auth0.com/).

## What is Auth0?

Auth0 helps you to:

* Add authentication with [multiple authentication sources](https://docs.auth0.com/identityproviders), either social like **Google, Facebook, Microsoft Account, LinkedIn, GitHub, Twitter, Box, Salesforce, amont others**, or enterprise identity systems like **Windows Azure AD, Google Apps, Active Directory, ADFS or any SAML Identity Provider**.
* Add authentication through more traditional **[username/password databases](https://docs.auth0.com/mysql-connection-tutorial)**.
* Add support for **[linking different user accounts](https://docs.auth0.com/link-accounts)** with the same user.
* Support for generating signed [Json Web Tokens](https://docs.auth0.com/jwt) to call your APIs and **flow the user identity** securely.
* Analytics of how, when and where users are logging in.
* Pull data from other sources and add it to the user profile, through [JavaScript rules](https://docs.auth0.com/rules).

## Create a free Auth0 Account

1. Go to [Auth0](https://auth0.com) and click Sign Up.
2. Use Google, GitHub or Microsoft Account to login.

## Issue Reporting

If you have found a bug or if you have a feature request, please report them at this repository issues section. Please do not report security vulnerabilities on the public GitHub issue tracker. The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

## Author

[Auth0](auth0.com)

## License

This project is licensed under the MIT license. See the [LICENSE](LICENSE) file for more info.


<!-- Vars -->

[travis-ci-badge]: https://travis-ci.org/auth0/auth0-java.svg?branch=master
[travis-ci-url]: https://travis-ci.org/auth0/auth0-java
[mit-badge]: http://img.shields.io/:license-mit-blue.svg?style=flat
[mit-url]: https://raw.githubusercontent.com/auth0/auth0-java/master/LICENSE
[maven-badge]: https://img.shields.io/maven-central/v/com.auth0/auth0.svg
[maven-url]: http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.auth0%22%20AND%20a%3A%22auth0%22
[jcenter-badge]: https://api.bintray.com/packages/auth0/lock-android/auth0/images/download.svg
[jcenter-url]: https://bintray.com/auth0/lock-android/auth0/_latestVersion