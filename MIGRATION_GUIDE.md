# Migrating from v3 to v4

`v4` is a compatible evolution of `v3`. The Authentication API is unchanged, and the Management API keeps the same client structure, builder patterns, and pagination introduced in `v3`. The breaking changes are narrow and limited to a few generated Management API types where field types were tightened for correctness or split for type-safety.

- [Overview](#overview)
- [Breaking changes](#breaking-changes)
    - [1. Role pagination fields are now non-optional primitives](#1-role-pagination-fields-are-now-non-optional-primitives)
    - [2. Connection attribute `identifier` is split by attribute type](#2-connection-attribute-identifier-is-split-by-attribute-type)
    - [3. Phone provider protection backoff strategy enum value changed](#3-phone-provider-protection-backoff-strategy-enum-value-changed)
- [Migration steps](#migration-steps)

## Overview

Most `v3` code compiles and runs unchanged on `v4`. You only need to act if your code touches one of the following:

| Area | What changed | Impact |
|------|--------------|--------|
| `ListRolesOffsetPaginatedResponseContent` | `start` / `limit` / `total` changed from `Optional<Double>` to primitive `double`, and are now required builder stages | Callers reading these getters or constructing the type |
| Connection attributes | The shared `ConnectionAttributeIdentifier` type is removed and replaced by dedicated `EmailAttributeIdentifier`, `PhoneAttributeIdentifier`, and `UsernameAttributeIdentifier` types | Callers reading/setting `identifier` on `EmailAttribute`, `PhoneAttribute`, `UsernameAttribute` |
| `PhoneProviderProtectionBackoffStrategyEnum` | `NONE` (`"none"`) removed, replaced by `DEFAULT` (`"default"`) | Callers referencing the `NONE` constant or visitor |

Everything else in `v4` is additive.

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
