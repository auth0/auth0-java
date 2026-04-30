> **Note**
> As part of our ongoing commitment to best security practices, we have rotated the signing keys used to sign previous releases of this SDK. As a result, new patch builds have been released using the new signing key. Please upgrade at your earliest convenience.
>
> While this change won't affect most developers, if you have implemented a dependency signature validation step in your build process, you may notice a warning that past releases can't be verified. This is expected, and a result of the key rotation process. Updating to the latest version will resolve this for you.
> 
> We are improving our API specs which introduces minor breaking changes.

![A Java client library for the Auth0 Authentication and Management APIs.](https://cdn.auth0.com/website/sdks/banners/auth0-java-banner.png)

![Build Status](https://img.shields.io/github/checks-status/auth0/auth0-java/master)
[![Coverage Status](https://codecov.io/gh/auth0/auth0-java/branch/master/graph/badge.svg?style=flat-square)](https://codecov.io/github/auth0/auth0-java)
[![License](http://img.shields.io/:license-mit-blue.svg?style=flat)](https://doge.mit-license.org/)
[![Maven Central](https://img.shields.io/maven-central/v/com.auth0/auth0.svg?style=flat-square)](https://search.maven.org/#search%7Cga%7C1%7Cg%3A%22com.auth0%22%20AND%20a%3A%22auth0%22)
[![javadoc](https://javadoc.io/badge2/com.auth0/auth0/javadoc.svg)](https://javadoc.io/doc/com.auth0/auth0)
[![Ask DeepWiki](https://deepwiki.com/badge.svg)](https://deepwiki.com/auth0/auth0-java)
[![fern shield](https://img.shields.io/badge/%F0%9F%8C%BF-Built%20with%20Fern-brightgreen)](https://buildwithfern.com?utm_source=github&utm_medium=github&utm_campaign=readme&utm_source=https%3A%2F%2Fgithub.com%2Fauth0%2Fauth0-java)

:books: [Documentation](#documentation) - :rocket: [Getting Started](#getting-started) - :computer: [API Reference](#api-reference) :speech_balloon: [Feedback](#feedback)

## Documentation
- [Reference](./reference.md) - code samples for Management APIs.
- [Examples](./EXAMPLES.md) - code samples for common auth0-java scenarios.
- [Migration Guide](./MIGRATION_GUIDE.md) - guidance for updating your application to use version 3 of auth0-java.
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
  <version>3.5.0</version>
</dependency>
```

or Gradle:

```gradle
implementation 'com.auth0:auth0:3.5.0'
```

### Configure the SDK

#### Authentication API Client

The Authentication API client is based on the [Auth0 Authentication API](https://auth0.com/docs/api/authentication).

Create an `AuthAPI` instance by providing the Application details from the [dashboard](https://manage.auth0.com/#/applications).

```java
AuthAPI auth = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
```

#### Management API Client

The Management API client is based on the [Management API Docs](https://auth0.com/docs/api/management/v2). In v3, the Management API is Fern-generated and uses `ManagementApi` as the entry point.

Create a `ManagementApi` instance with a static token:

```java
ManagementApi mgmt = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .token("{YOUR_API_TOKEN}")
        .build();
```

Or use client credentials for automatic token management (recommended for server-to-server applications):

```java
ManagementApi mgmt = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .clientCredentials("{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}")
        .build();
```

The Management API is organized into resource clients. Methods return typed response objects directly:

```java
GetUserResponseContent user = mgmt.users().get("user_id");
GetRoleResponseContent role = mgmt.roles().get("role_id");
```

You can also obtain a token via the Authentication API and use it with the Management API client:

```java
AuthAPI authAPI = AuthAPI.newBuilder("{YOUR_DOMAIN}", "{YOUR_CLIENT_ID}", "{YOUR_CLIENT_SECRET}").build();
TokenRequest tokenRequest = authAPI.requestToken("https://{YOUR_DOMAIN}/api/v2/");
TokenHolder holder = tokenRequest.execute().getBody();
String accessToken = holder.getAccessToken();
ManagementApi mgmt = ManagementApi.builder()
        .domain("{YOUR_DOMAIN}")
        .token(accessToken)
        .build();
```

See the [Auth0 Management API documentation](https://auth0.com/docs/api/management/v2/tokens) for more information on how to obtain API Tokens.

## API Reference

- [AuthAPI](https://javadoc.io/doc/com.auth0/auth0/latest/com/auth0/client/auth/AuthAPI.html)
- [ManagementApi](https://javadoc.io/doc/com.auth0/auth0/latest/com/auth0/client/mgmt/ManagementApi.html)

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
