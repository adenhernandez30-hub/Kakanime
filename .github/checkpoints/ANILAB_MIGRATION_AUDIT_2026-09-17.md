# AniLab Migration Checkpoint — Dependency Audit

Date: 2026-09-17
Repository: `adenhernandez30-hub/Kakanime`
Branch: `main`

## Locked direction

- Final product brand: **AniLab**.
- Do not perform a blind `KakaAnime -> AniLab` global replacement.
- Migrate the Dantotsu-derived foundation incrementally while preserving useful UI/player architecture.
- Codespaces is for environment-dependent validation only; repository audit and applicable edits can be handled directly through GitHub.

## Audit completed

- `app/build.gradle` audited.
- Media3/ExoPlayer playback stack identified as preserve/keep.
- OkHttp/Okio/serialization/networking stack identified as preserve/keep pending API integration audit.
- No direct Firebase dependency declaration was found in `app/build.gradle`.
- F-Droid flavor explicitly targets a build without Google/Firebase services.
- Legacy Firebase Crashlytics source-set references remain indexed and require source/config verification before removal.
- Billing dependency was not found in `app/build.gradle`; Premium/Diamond backend authority remains a future implementation concern.
- `applicationId` and `namespace` remain `ani.dantotsu`; do not change yet because package/deep-link/runtime dependency audit is incomplete.

## Status

- 🟢 Dependency audit batch recorded.
- 🟢 Player/network foundation preserved by decision.
- 🟡 Crashlytics source/config verification pending.
- 🟡 Root Gradle/source-set configuration audit pending.
- 🟡 Dantotsu service replacement not implemented.
- 🟡 AniLab package/application identity migration not implemented.
- 🟡 Build/test validation pending for future code changes.

## Next

Audit root Gradle files, source-set configuration, Crashlytics interface/factory wiring, app resources/label, and remaining Dantotsu runtime configuration before implementation.
