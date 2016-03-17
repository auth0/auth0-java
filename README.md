# Auth0 Java

[![Build status][travis-ci-badge]](travis-ci-url)

Java client library for the [Auth0](https://auth0.com) platform.

## Download

Get Auth0 Java via Maven:

```xml
<dependency>
  <groupId>com.auth0</groupId>
  <artifactId>auth0-java</artifactId>
  <version>0.0.1</version>
</dependency>
```

or Gradle:

```gradle
compile 'com.auth0:auth0-java:0.0.1'
```

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

### Android

You need to add the following to your `build.gradle` file:

``` gradle
android {

    // Other config of your application
    
    packagingOptions {
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/NOTICE'
    }
}
```

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

[travis-ci-badge]: https://travis-ci.org/auth0/auth0-api-java.svg?branch=master
[tavis-ci-url]: https://travis-ci.org/auth0/auth0-api-java