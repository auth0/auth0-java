# Command Reference

Full command list, extracted from `.github/workflows/build-and-test.yml`, `CONTRIBUTING.md`, and `build.gradle`. See [CLAUDE.md](../CLAUDE.md) for the always-loaded quick set. Always use the committed Gradle wrapper (`./gradlew`), never a system `gradle`.

## Build

```bash
# Full build: compile, run tests, assemble artifacts
./gradlew build

# Assemble artifacts only (no tests)
./gradlew assemble

# What CI runs (build-and-test.yml) — assemble + all verification tasks, keep going on failure
./gradlew assemble check --continue --console=plain

# Clean
./gradlew clean
```

## Test

```bash
# Run the full JUnit 5 suite
./gradlew test

# Run a single test class
./gradlew test --tests 'com.auth0.client.mgmt.ManagementApiBuilderTest'

# Run a single test method
./gradlew test --tests 'com.auth0.client.mgmt.OAuthTokenSupplierTest.someMethod'

# Standard streams are shown during tests (configured in build.gradle)
```

## Format

```bash
# Apply formatting (palantir-java-format via Spotless) — run before committing
./gradlew spotlessApply

# Check formatting without modifying (part of `check`)
./gradlew spotlessCheck
```

## Sample app (manual verification / issue repros)

`sample-app/` is a separate Gradle module that depends on the root project (`implementation rootProject`). Use it to reproduce reported issues against local SDK changes.

```bash
# Compile the sample app against the local SDK
./gradlew :sample-app:build
```

## Coverage

Coverage reports are produced during `check` and uploaded to Codecov under the `unittests` flag in CI (`.codecov.yml`). Build reports land in `build/reports/`.

## Release (CI only)

Releases run via `.github/workflows/release.yml` → `java-release.yml`, triggered by merging a `release/*` branch. The version is read from `.version` by `gradle/versioning.gradle`; publishing uses the `maven-publish` action with signing keys injected as GitHub secrets. Do not run publish tasks locally.
