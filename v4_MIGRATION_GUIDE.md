# Migrating from v3 to v4

`v4` is a compatible evolution of `v3`. The Authentication API is unchanged, and the Management API keeps the same client structure, builder patterns, and pagination introduced in `v3`. The breaking changes are narrow: a few generated Management API types where field types were tightened or split for type-safety, and the removal of the Federated Connections Tokensets API.

- [Overview](#overview)
- [Breaking changes](#breaking-changes)
    - [1. Role pagination fields are now non-optional primitives](#1-role-pagination-fields-are-now-non-optional-primitives)
    - [2. Connection attribute `identifier` is split by attribute type](#2-connection-attribute-identifier-is-split-by-attribute-type)
    - [3. Phone provider protection backoff strategy enum value changed](#3-phone-provider-protection-backoff-strategy-enum-value-changed)
    - [4. Federated Connections Tokensets API removed](#4-federated-connections-tokensets-api-removed)
- [Other changes](#other-changes)
    - [Per-request retry configuration](#per-request-retry-configuration)
    - [Query parameter serialization](#query-parameter-serialization)
- [Migration steps](#migration-steps)

## Overview

Most `v3` code compiles and runs unchanged on `v4`. You only need to act if your code touches one of the following:

| Area | What changed | Impact |
|------|--------------|--------|
| `ListRolesOffsetPaginatedResponseContent` | `start` / `limit` / `total` changed from `Optional<Double>` to primitive `double`, and are now required builder stages | Callers reading these getters or constructing the type |
| Connection attributes | The shared `ConnectionAttributeIdentifier` type is removed and replaced by dedicated `EmailAttributeIdentifier`, `PhoneAttributeIdentifier`, and `UsernameAttributeIdentifier` types | Callers reading/setting `identifier` on `EmailAttribute`, `PhoneAttribute`, `UsernameAttribute` |
| `PhoneProviderProtectionBackoffStrategyEnum` | `NONE` (`"none"`) removed, replaced by `DEFAULT` (`"default"`) | Callers referencing the `NONE` constant or visitor |
| Federated Connections Tokensets | `client.users().federatedConnectionsTokensets()` and its types removed | Callers of that API |

Everything else in `v4` is additive or internal.

## Breaking changes

### 1. Role pagination fields are now non-optional primitives

In `ListRolesOffsetPaginatedResponseContent`, the `start`, `limit`, and `total` fields change from `Optional<Double>` to primitive `double`, and the builder now requires them as mandatory, staged arguments.

**v3:**
```java
private final Optional<Double> start;
public Optional<Double> getStart() { ... }
```

**v4:**
```java
private final double start;
public double getStart() { ... }
```

If you read these getters, drop the `Optional` handling:

```java
// v3
double start = response.getStart().orElse(0d);

// v4
double start = response.getStart();
```

If you construct the type, the builder is now staged and requires `start`, `limit`, and `total` in order:

```java
// v4
ListRolesOffsetPaginatedResponseContent content = ListRolesOffsetPaginatedResponseContent.builder()
    .start(0)
    .limit(50)
    .total(200)
    .roles(roles)
    .build();
```

### 2. Connection attribute `identifier` is split by attribute type

The shared `ConnectionAttributeIdentifier` type is removed and replaced with dedicated types per attribute. The `identifier` field on `EmailAttribute`, `PhoneAttribute`, and `UsernameAttribute` now uses the matching type, so their `getIdentifier()` and builder `identifier(...)` signatures change.

| Attribute | v3 identifier type | v4 identifier type |
|-----------|--------------------|--------------------|
| `EmailAttribute` | `ConnectionAttributeIdentifier` | `EmailAttributeIdentifier` |
| `PhoneAttribute` | `ConnectionAttributeIdentifier` | `PhoneAttributeIdentifier` |
| `UsernameAttribute` | `ConnectionAttributeIdentifier` | `UsernameAttributeIdentifier` |

**v3:**
```java
public Optional<ConnectionAttributeIdentifier> getIdentifier() { ... }
```

**v4 (`EmailAttribute`):**
```java
public Optional<EmailAttributeIdentifier> getIdentifier() { ... }
```

Update your imports and any variables holding the identifier. The new types expose `active` (`Optional<Boolean>`); `EmailAttributeIdentifier` and `PhoneAttributeIdentifier` additionally expose a `defaultMethod` (`DefaultMethodEmailIdentifierEnum` / `DefaultMethodPhoneNumberIdentifierEnum`).

```java
// v4
EmailAttribute email = EmailAttribute.builder()
    .identifier(EmailAttributeIdentifier.builder()
        .active(true)
        .build())
    .build();
```

### 3. Phone provider protection backoff strategy enum value changed

`PhoneProviderProtectionBackoffStrategyEnum.NONE` (`"none"`, `visitNone()`) is removed and replaced by `PhoneProviderProtectionBackoffStrategyEnum.DEFAULT` (`"default"`, `visitDefault()`).

**v3:**
```java
PhoneProviderProtectionBackoffStrategyEnum strategy = PhoneProviderProtectionBackoffStrategyEnum.NONE;
```

**v4:**
```java
PhoneProviderProtectionBackoffStrategyEnum strategy = PhoneProviderProtectionBackoffStrategyEnum.DEFAULT;
```

If you implement the visitor interface, rename `visitNone()` to `visitDefault()`.

### 4. Federated Connections Tokensets API removed

The Federated Connections Tokensets API is removed. The following are no longer available:

- Client accessor: `client.users().federatedConnectionsTokensets()` (and its async/raw variants)
- Types: `FederatedConnectionTokenSet`, `ConnectionFederatedConnectionsAccessTokens`

**v3:**
```java
List<FederatedConnectionTokenSet> tokensets =
    client.users().federatedConnectionsTokensets().list("user_id");

client.users().federatedConnectionsTokensets().delete("user_id", "tokenset_id");
```

**v4:**

No replacement is generated in the SDK. Remove these calls; if you still need this functionality, call the corresponding Management API endpoint directly.

## Other changes

These are backward-compatible but worth noting.

### Per-request retry configuration

Requests now honor a per-request `maxRetries` value via `RequestOptions`, alongside the existing `timeout`:

```java
GetUserResponseContent user = client.users().get(
    "user_id",
    GetUserRequestParameters.builder().build(),
    RequestOptions.builder()
        .timeout(10)
        .maxRetries(2)
        .build()
);
```

### Query parameter serialization

List/query parameter types (e.g. `ListUsersRequestParameters`, `ListClientsRequestParameters`, `ListLogsRequestParameters`, `ListConnectionsQueryParameters`) now serialize their optional/nullable query parameters through a nullable-nonempty filter instead of being marked `@JsonIgnore`. Builder usage is unchanged; this only affects how parameters are emitted on the wire.

## Migration steps

1. Update the dependency to the `v4` release.
2. Replace any `ConnectionAttributeIdentifier` imports/usages with the matching `EmailAttributeIdentifier`, `PhoneAttributeIdentifier`, or `UsernameAttributeIdentifier`.
3. Remove `Optional` handling around `ListRolesOffsetPaginatedResponseContent#getStart/getLimit/getTotal`, and update any builder usage to the staged `start` → `limit` → `total` form.
4. Replace `PhoneProviderProtectionBackoffStrategyEnum.NONE` with `DEFAULT` (and `visitNone()` with `visitDefault()` in any visitor implementations).
5. Remove any usage of `client.users().federatedConnectionsTokensets()` and the `FederatedConnectionTokenSet` / `ConnectionFederatedConnectionsAccessTokens` types.
6. Run `mvn verify` (or your build) and fix any remaining compilation errors surfaced by the above.
