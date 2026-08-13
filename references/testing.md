# Testing Conventions

Read this when writing or modifying tests. See [CLAUDE.md](../CLAUDE.md) for the always-loaded safe command.

## Framework & layout

| Aspect | Detail |
|--------|--------|
| Test framework | JUnit Jupiter 5 (`useJUnitPlatform()` in `build.gradle`) |
| Mocking | Mockito 4 (`mockito-core`) |
| HTTP | OkHttp `MockWebServer` — real HTTP against a local mock server, not stubbed clients |
| Assertions | Hamcrest matchers |
| Location | `src/test/java/com/auth0/` mirrors the `src/main` package layout |
| Fixtures | `src/test/resources/` — `wire-tests/`, `auth/` |

## Generated vs. hand-maintained tests

- Most Management API tests are **Fern-generated** (they carry the auto-generated header and live under the generated `mgmt` tree). Do not hand-edit them; behavioral test changes come from the spec/generator.
- Hand-maintained tests are the ones listed in `.fernignore`, e.g.:
  - `src/test/java/com/auth0/client/mgmt/ManagementApiBuilderTest.java`
  - `src/test/java/com/auth0/client/mgmt/DynamicTokenManagementTest.java`
  - `src/test/java/com/auth0/client/mgmt/OAuthTokenSupplierTest.java`
  - `src/test/java/com/auth0/client/mgmt/CustomDomainInterceptorTest.java`
  - `src/test/java/com/auth0/client/mgmt/CustomDomainHeaderIntegrationTest.java`
  - the entire `src/test/java/com/auth0/client/auth/` tree (Authentication API)
- Shared test infrastructure — also hand-maintained (`.fernignore`): `MockServer`, `RecordedRequestMatcher`, `UrlMatcher` (under `com/auth0/client/`) and `AssertsUtil`. Reuse these rather than rolling new HTTP-assertion helpers.

## Conventions

- New functionality in hand-maintained code (Authentication API, builders, token providers, interceptors) **must** ship with tests in the corresponding `.fernignore`-listed location so Fern regeneration doesn't clobber them.
- Tests must run with no network access and no real Auth0 tenant — use `MockWebServer` / `MockServer` and fixture files under `src/test/resources/`.
- Never put a real tenant domain, client secret, or token into a test or fixture.

## Coverage

Coverage is uploaded to Codecov under the `unittests` flag (`.codecov.yml`); HTML/XML reports are written to `build/reports/` and uploaded as the `Reports` artifact in CI.
