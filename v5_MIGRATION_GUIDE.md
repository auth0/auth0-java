# Migrating from v4 to v5

`v5` is a compatible evolution of `v4`. The Authentication API is unchanged, and the Management API keeps the same client structure, builder patterns, and pagination. There is a single breaking change: response fields that the API declares as nullable now use `OptionalNullable<T>` instead of `Optional<T>`, so the SDK reflects the actual API contract.

- [Overview](#overview)
- [Breaking changes](#breaking-changes)
    - [1. Nullable response fields use `OptionalNullable<T>`](#1-nullable-response-fields-use-optionalnullablet)
- [Migration steps](#migration-steps)

For everything else added in `v5` — the Keys Network ACLs client, third-party client access, and Cross-App Access connection profiles — see the [changelog](CHANGELOG.md). Those changes are additive and require no action.

## Overview

Most `v4` code compiles and runs unchanged on `v5`. You only need to act if your code reads one of the affected date/nullable fields:

| Area | What changed | Impact |
|------|--------------|--------|
| Session response types | Timestamp getters return `OptionalNullable<SessionDate>` instead of `Optional<SessionDate>` | Callers reading `created_at`, `updated_at`, `authenticated_at`, `idle_expires_at`, `expires_at`, `last_interacted_at` |
| Refresh token response types | Timestamp getters return `OptionalNullable<RefreshTokenDate>` instead of `Optional<RefreshTokenDate>` | Callers reading `created_at`, `idle_expires_at`, `expires_at`, `last_exchanged_at` |
| `SessionAuthenticationSignal` | `getTimestamp()` returns `OptionalNullable<SessionDate>` | Callers reading `timestamp` |
| `FlowActionFlowMapValueParams` | `getFallback()` returns `OptionalNullable<FlowActionFlowMapValueParamsFallback>` | Callers reading `fallback` |

Everything else in `v5` is additive or internal. Builder setters keep their `Optional<T>` and raw-value overloads, so code that *writes* these types is unaffected — only read paths change.

## Breaking changes

### 1. Nullable response fields use `OptionalNullable<T>`

These fields are declared nullable in the Auth0 API definition. Earlier SDK versions dropped that nullability and generated plain `Optional<T>`; `v5` preserves it, which allows the SDK to represent a field that the API returned as an explicit `null` distinctly from one it omitted entirely.

The following 32 getters are affected:

| Type | Getters |
|------|---------|
| `GetSessionResponseContent`, `SessionResponseContent`, `UpdateSessionResponseContent` | `getCreatedAt()`, `getUpdatedAt()`, `getAuthenticatedAt()`, `getIdleExpiresAt()`, `getExpiresAt()`, `getLastInteractedAt()` |
| `GetRefreshTokenResponseContent`, `RefreshTokenResponseContent`, `UpdateRefreshTokenResponseContent` | `getCreatedAt()`, `getIdleExpiresAt()`, `getExpiresAt()`, `getLastExchangedAt()` |
| `SessionAuthenticationSignal` | `getTimestamp()` |
| `FlowActionFlowMapValueParams` | `getFallback()` |

There are three separate things to check. Only the first is caught by the compiler.

#### a. Signature change

`OptionalNullable<T>` is not a drop-in replacement for `Optional<T>`. It provides `isPresent()`, `isAbsent()`, `isNull()`, `wasSpecified()`, `get()`, `getValueOrNull()`, `orElse()`, `map()`, and `toOptional()` — but **not** `ifPresent()`, `orElseThrow()`, `filter()`, or `stream()`.

The simplest migration is `.toOptional()`, which returns an empty `Optional` for both the absent and explicit-null cases:

```java
// v4
Optional<SessionDate> created = session.getCreatedAt();
session.getCreatedAt().ifPresent(this::audit);

// v5
Optional<SessionDate> created = session.getCreatedAt().toOptional();
session.getCreatedAt().toOptional().ifPresent(this::audit);
```

If you want to distinguish the two states, use the richer API directly:

```java
// v5
OptionalNullable<SessionDate> expires = session.getExpiresAt();
if (expires.isPresent()) {
    handle(expires.get());
} else if (expires.isNull()) {
    // the API explicitly returned null for this field
} else {
    // the API omitted the field
}
```

#### b. `get()` throws a different exception

`Optional.get()` throws `NoSuchElementException`. `OptionalNullable.get()` throws `IllegalStateException`, and does so for both the absent and explicit-null states.

```java
// v4 — catches successfully
try {
    SessionDate created = session.getCreatedAt().get();
} catch (NoSuchElementException e) { ... }

// v5 — the catch no longer matches; the exception propagates
try {
    SessionDate created = session.getCreatedAt().get();
} catch (IllegalStateException e) { ... }
```

This compiles unchanged in `v5`, so the compiler will not flag it. Search for `NoSuchElementException` around these getters.

#### c. `orElse()` can return `null`

`Optional.orElse(fallback)` never returns `null`. `OptionalNullable.orElse(fallback)` returns `null` when the field was explicitly null, and returns `fallback` only when the field was absent:

```java
SessionDate created = session.getCreatedAt().orElse(DEFAULT);
// v5: returns DEFAULT if absent, but null if the API returned an explicit null
```

If you rely on a non-null result, use `.toOptional().orElse(fallback)` instead, which treats both states as empty:

```java
SessionDate created = session.getCreatedAt().toOptional().orElse(DEFAULT);
```

Like (b), this compiles unchanged, so it is worth an explicit grep.

## Migration steps

1. Update the dependency to the `v5` release.
2. Compile. Any getter listed above that you assign to an `Optional<T>`, or call `ifPresent()` / `orElseThrow()` / `filter()` / `stream()` on, will fail — append `.toOptional()`.
3. Grep for `NoSuchElementException` near those getters and change it to `IllegalStateException`, or switch to `.toOptional().orElseThrow(...)`.
4. Grep for `.orElse(` on those getters. If a non-null result is required, use `.toOptional().orElse(...)`.
5. Run your build and test suite.
