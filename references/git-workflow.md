# Git Workflow

## Branch naming

No enforced convention detected. Release branches use the `release/*` prefix (the release workflow only runs for merged PRs whose head branch starts with `release/`).

## Commit messages

Conventional Commits, matching recent `git log`: `feat:`, `fix:`, `chore:`, `ci:`, `docs:`. Release commits are titled `Release X.Y.Z` (e.g. `Release 4.2.0`). SDK-regeneration PRs are commonly titled `SDK regeneration`.

## Pull requests

- **Body:** follow `.github/pull_request_template.md` — fill in **Changes**, **References**, **Testing**, and complete the **Checklist** (contribution guidelines read, tests added, existing + new tests pass).
- **Tests must pass:** CI (`build-and-test.yml`) runs `./gradlew assemble check --continue` on Java 8; formatting (`spotlessCheck`) is part of `check`, so run `./gradlew spotlessApply` before pushing.
- **Generated code:** if a PR changes Management API behavior, note whether it originates from an SDK regeneration (spec/generator change) rather than a hand-edit — reviewers need to know a local edit to generated code won't survive.

## Releases

Releases are automated: bump `.version`, cut a `release/*` branch, and merging it triggers `release.yml` → `java-release.yml` (RL security scan, then Maven Central publish with signing secrets). Do not publish locally.
