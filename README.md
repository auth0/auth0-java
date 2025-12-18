# Auth0 Java Library

[![fern shield](https://img.shields.io/badge/%F0%9F%8C%BF-Built%20with%20Fern-brightgreen)](https://buildwithfern.com?utm_source=github&utm_medium=github&utm_campaign=readme&utm_source=https%3A%2F%2Fgithub.com%2Fauth0%2Fauth0-java)

The Auth0 Java library provides convenient access to the Auth0 APIs from Java.

## Table of Contents

- [Documentation](#documentation)
- [Getting Started](#getting-started)
- [Api Reference](#api-reference)
- [Feedback](#feedback)
- [Reference](#reference)
- [Usage](#usage)
- [Environments](#environments)
- [Base Url](#base-url)
- [Exception Handling](#exception-handling)
- [Advanced](#advanced)
  - [Custom Client](#custom-client)
  - [Retries](#retries)
  - [Timeouts](#timeouts)
  - [Custom Headers](#custom-headers)
  - [Access Raw Response Data](#access-raw-response-data)
- [Contributing](#contributing)

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
  <version>2.27.0</version>
</dependency>
```

or Gradle:

```gradle
implementation 'com.auth0:auth0:2.27.0'
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

OR

Create a `ManagementAPI` instance by providing the domain from the [Application dashboard](https://manage.auth0.com/#/applications) and Token Provider.

```java
TokenProvider tokenProvider = SimpleTokenProvider.create("{YOUR_API_TOKEN}");
ManagementAPI mgmt = ManagementAPI.newBuilder("{YOUR_DOMAIN}", TokenProvider).build();
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
## Reference

A full reference for this library is available [here](https://github.com/auth0/auth0-java/blob/HEAD/./reference.md).

## Usage

Instantiate and use the client with the following:

```java
package com.example.usage;

import com.auth0.client.mgmt.ManagementApi;
import com.auth0.client.mgmt.types.ActionTrigger;
import com.auth0.client.mgmt.types.CreateActionRequestContent;
import java.util.Arrays;

public class Example {
    public static void main(String[] args) {
        ManagementApi client = ManagementApi
            .builder()
            .token("<token>")
            .build();

        client.actions().create(
            CreateActionRequestContent
                .builder()
                .name("my-action")
                .supportedTriggers(
                    Arrays.asList(
                        ActionTrigger
                            .builder()
                            .id("id")
                            .build(),
                        ActionTrigger
                            .builder()
                            .id("id")
                            .build()
                    )
                )
                .build()
        );
    }
}
```
## OptionalNullable for PATCH Requests

For PATCH requests, the SDK uses `OptionalNullable<T>` to handle three-state nullable semantics:

- **ABSENT**: Field not provided (omitted from JSON)
- **NULL**: Field explicitly set to null (included as `null` in JSON)
- **PRESENT**: Field has a non-null value

```java
import com.seed.api.core.OptionalNullable;

UpdateRequest request = UpdateRequest.builder()
    .fieldName(OptionalNullable.absent())    // Skip field
    .anotherField(OptionalNullable.ofNull()) // Clear field
    .yetAnotherField(OptionalNullable.of("value")) // Set value
    .build();
```

### Important Notes

- **Required fields**: For required fields, you cannot use `absent()`. Required fields must always be present with either a non-null value or explicitly set to null using `ofNull()`.
- **Type safety**: `OptionalNullable<T>` is not fully type-safe since all three states use the same type, but it provides a cleaner API than nested `Optional<Optional<T>>` for handling three-state nullable semantics.

## Environments

This SDK allows you to configure different environments for API requests.

```java
import com.auth0.client.mgmt.ManagementApi;
import com.auth0.client.mgmt.core.Environment;

ManagementApi client = ManagementApi
    .builder()
    .environment(Environment.Default)
    .build();
```

## Base Url

You can set a custom base URL when constructing the client.

```java
import com.auth0.client.mgmt.ManagementApi;

ManagementApi client = ManagementApi
    .builder()
    .url("https://example.com")
    .build();
```

## Exception Handling

When the API returns a non-success status code (4xx or 5xx response), an API exception will be thrown.

```java
import com.auth0.client.mgmt.core.ManagementApiException;

try{
    client.actions().create(...);
} catch (ManagementApiException e){
    // Do something with the API exception...
}
```

## Advanced

### Custom Client

This SDK is built to work with any instance of `OkHttpClient`. By default, if no client is provided, the SDK will construct one.
However, you can pass your own client like so:

```java
import com.auth0.client.mgmt.ManagementApi;
import okhttp3.OkHttpClient;

OkHttpClient customClient = ...;

ManagementApi client = ManagementApi
    .builder()
    .httpClient(customClient)
    .build();
```

### Retries

The SDK is instrumented with automatic retries with exponential backoff. A request will be retried as long
as the request is deemed retryable and the number of retry attempts has not grown larger than the configured
retry limit (default: 2). Before defaulting to exponential backoff, the SDK will first attempt to respect
the `Retry-After` header (as either in seconds or as an HTTP date), and then the `X-RateLimit-Reset` header
(as a Unix timestamp in epoch seconds); failing both of those, it will fall back to exponential backoff.

A request is deemed retryable when any of the following HTTP status codes is returned:

- [408](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/408) (Timeout)
- [429](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/429) (Too Many Requests)
- [5XX](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/500) (Internal Server Errors)

Use the `maxRetries` client option to configure this behavior.

```java
import com.auth0.client.mgmt.ManagementApi;

ManagementApi client = ManagementApi
    .builder()
    .maxRetries(1)
    .build();
```

### Timeouts

The SDK defaults to a 60 second timeout. You can configure this with a timeout option at the client or request level.
```java
import com.auth0.client.mgmt.ManagementApi;
import com.auth0.client.mgmt.core.RequestOptions;

// Client level
ManagementApi client = ManagementApi
    .builder()
    .timeout(60)
    .build();

// Request level
client.actions().create(
    ...,
    RequestOptions
        .builder()
        .timeout(60)
        .build()
);
```

### Custom Headers

The SDK allows you to add custom headers to requests. You can configure headers at the client level or at the request level.

```java
import com.auth0.client.mgmt.ManagementApi;
import com.auth0.client.mgmt.core.RequestOptions;

// Client level
ManagementApi client = ManagementApi
    .builder()
    .addHeader("X-Custom-Header", "custom-value")
    .addHeader("X-Request-Id", "abc-123")
    .build();
;

// Request level
client.actions().create(
    ...,
    RequestOptions
        .builder()
        .addHeader("X-Request-Header", "request-value")
        .build()
);
```

### Access Raw Response Data

The SDK provides access to raw response data, including headers, through the `withRawResponse()` method.
The `withRawResponse()` method returns a raw client that wraps all responses with `body()` and `headers()` methods.
(A normal client's `response` is identical to a raw client's `response.body()`.)

```java
CreateHttpResponse response = client.actions().withRawResponse().create(...);

System.out.println(response.body());
System.out.println(response.headers().get("X-My-Header"));
```

## Contributing

While we value open-source contributions to this SDK, this library is generated programmatically.
Additions made directly to this library would have to be moved over to our generation code,
otherwise they would be overwritten upon the next generated release. Feel free to open a PR as
a proof of concept, but know that we will not be able to merge it as-is. We suggest opening
an issue first to discuss with us!

On the other hand, contributions to the README are always very welcome!