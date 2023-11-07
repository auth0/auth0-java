> **Note**
> As part of our ongoing commitment to best security practices, we have rotated the signing keys used to sign previous releases of this SDK. As a result, new patch builds have been released using the new signing key. Please upgrade at your earliest convenience.
>
> While this change won't affect most developers, if you have implemented a dependency signature validation step in your build process, you may notice a warning that past releases can't be verified. This is expected, and a result of the key rotation process. Updating to the latest version will resolve this for you.

![A Java client library for the Auth0 Authentication and Management APIs.](https://cdn.auth0.com/website/sdks/banners/auth0-java-banner.png)

![Build Status](https://img.shields.io/github/checks-status/auth0/auth0-java/master)
[![Coverage Status](https://codecov.io/gh/auth0/auth0-java/branch/master/graph/badge.svg?style=flat-square)](https://codecov.io/github/auth0/auth0-java)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](https://doge.mit-license.org/)
[![Maven Central](https://img.shields.io/maven-central/v/com.auth0/auth0.svg?style=flat-square)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.auth0%22%20AND%20a%3A%22auth0%22)
[![javadoc](https://javadoc.io/badge2/com.auth0/auth0/javadoc.svg)](https://javadoc.io/doc/com.auth0/auth0)

:books: [Documentation](#documentation) - :rocket: [Getting Started](#getting-started) - :computer: [API Reference](#api-reference) :speech_balloon: [Feedback](#feedback)

## Documentation
- [Examples](./EXAMPLES.md) - code samples for common auth0-java scenarios.
- [Migration Guide](./MIGRATION_GUIDE.md) - guidance for updating your application to use version 2 of auth0-java.
- [Docs site](https://www.auth0.com/docs) - explore our docs site and learn more about Auth0.

## Getting Started

### Requirements

Java 8 or above.

> `auth0-java` is intended for server-side JVM applications. Android applications should use the [Auth0.Android SDK](https://github.com/auth0/auth0.android).

### Installation

Add the dependency via Maven:

```xml
<dependency>
  <groupId>com.auth0</groupId>
  <artifactId>auth0</artifactId>
  <version>2.8.0</version>
</dependency>
```

or Gradle:

```gradle
implementation 'com.auth0:auth0:2.8.0'
```

### Configure the SDK

#### Authentication API Client

The Authentication API client is based on the [Auth0 Authentication API](https://auth0.com/docs/api/authentication).

Create an `AuthAPI` instance by providing the Application details from the [dashboard](https://manage.auth0.com/#/applications).

```java
AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
```

#### Management API Client

The Management API client is based on the [Management API Docs](https://auth0.com/docs/api/management/v2).

Create a `ManagementAPI` instance by providing the domain from the [Application dashboard](https://manage.auth0.com/#/applications) and a valid API Token.

```java
ManagementAPI mgmt = ManagementAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_API_TOKEN}").build();
```

The Management API is organized by entities represented by the Auth0 Management API objects.

```java
User user = mgmt.users().get("auth0|user-id", new UserFilter()).execute().getBody();
Role role = mgmt.roles().get("role-id").execute().getBody();
```

You can use the Authentication API to obtain a token for a previously authorized Application:

```java
AuthAPI authAPI = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
TokenRequest tokenRequest = authAPI.requestToken("https://{YOUR_DOMAIN}/api/v2/");
TokenHolder holder = tokenRequest.execute().getBody();
String accessToken = holder.getAccessToken();
ManagementAPI mgmt = ManagementAPI.newBuilder("{YOUR_DOMAIN}", accessToken).build();
```

An expired token for an existing `ManagementAPI` instance can be replaced by calling the `setApiToken` method with the new token.

See the [Auth0 Management API documentation](https://auth0.com/docs/api/management/v2/tokens) for more information on how to obtain API Tokens.

## API Reference

- [AuthAPI](https://javadoc.io/doc/com.auth0/auth0/latest/com/auth0/client/auth/AuthAPI.html)
- [ManagementAPI](https://javadoc.io/doc/com.auth0/auth0/latest/com/auth0/client/mgmt/ManagementAPI.html)

## Feedback

### Contributing

We appreciate feedback and contribution to this repo! Before you get started, please see the following:

- [Auth0's general contribution guidelines](https://github.com/auth0/open-source-template/blob/master/GENERAL-CONTRIBUTING.md)
- [Auth0's code of conduct guidelines](https://github.com/auth0/open-source-template/blob/master/CODE-OF-CONDUCT.md)

### Raise an issue
To provide feedback or report a bug, [please raise an issue on our issue tracker](https://github.com/auth0/auth0-java/issues).

### Vulnerability Reporting
Please do not report security vulnerabilities on the public Github issue tracker. The [Responsible Disclosure Program](https://auth0.com/whitehat) details the procedure for disclosing security issues.

---

<p align="center">
  <picture>
    <source media="(prefers-color-scheme: light)" srcset="https://cdn.auth0.com/website/sdks/logos/auth0_light_mode.png"   width="150">
    <source media="(prefers-color-scheme: dark)" srcset="https://cdn.auth0.com/website/sdks/logos/auth0_dark_mode.png" width="150">
    <img alt="Auth0 Logo" src="./auth0_light_mode.png" width="150">
  </picture>
</p>
<p align="center">Auth0 is an easy to implement, adaptable authentication and authorization platform. To learn more checkout <a href="https://auth0.com/why-auth0">Why Auth0?</a></p>
<p align="center">
This project is licensed under the MIT license. See the <a href="./LICENSE"> LICENSE</a> file for more info.</p>
