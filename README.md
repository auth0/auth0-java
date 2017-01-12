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
  <version>0.4.0</version>
</dependency>
```

or Gradle:

```gradle
compile 'com.auth0:auth0:0.4.0'
```

### Android

The Auth0 Authentication API and User's Management API are available for Android in the `auth0.android` library. Check https://github.com/auth0/auth0.android for more information.

## Usage

First with your Auth0 account information

```java
Auth0 auth0 = new Auth0("{YOUR_CLIENT_ID}", "{YOUR_DOMAIN}");
```

and then ask `Auth0` object for the Authentication API Client

```java
AuthenticationAPIClient client = auth0.newAuthenticationAPIClient();
```

> Currently we only have a Authentication API Client, in future version we'll start adding Management API Client and methods.

### Authentication API

#### Making a request

Asynchronously

```java
Auth0 auth0 = new Auth0("{YOUR_CLIENT_ID}", "{YOUR_DOMAIN}");
AuthenticationAPIClient client = auth0.newAuthenticationAPIClient();

client.login("{username or email}", "{password}")
        .setConnection("{database connection name}")
        .start(new BaseCallback<Credentials>() {
            Override
            public void onSuccess(Credentials payload) { }
        
            Override
            public void onFailure(Auth0Exception error) { }
        });
```

Synchronously

```java
Auth0 auth0 = new Auth0("{YOUR_CLIENT_ID}", "{YOUR_DOMAIN}");
AuthenticationAPIClient client = auth0.newAuthenticationAPIClient();

Credentials credentials = client.login("{username or email}", "{password}")
                                    .setConnection("{database connection name}")
                                    .execute();
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
