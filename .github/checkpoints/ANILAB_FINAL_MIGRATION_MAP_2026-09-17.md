# AniLab Final Migration Map — 2026-09-17

## Decision
AniLab is the final product brand. Migration is incremental; do not perform a blind global KakaAnime/Dantotsu replacement.

## Preserve
- Android/UI foundation
- Media3/ExoPlayer player and playback infrastructure
- Offline/download/local storage
- AniList and MyAnimeList integrations
- Addon/extension architecture
- WorkManager/AlarmManager infrastructure
- Notification/widget infrastructure where not tied to Dantotsu services
- Network stack (OkHttp/serialization/Jsoup) as implementation infrastructure

## Replace or remove
- Dantotsu backend service endpoints, especially `api.dantotsu.app`
- Dantotsu Comments API authentication/session and comment service coupling
- Comment-specific workers/notifications when the old backend is removed
- Dantotsu-owned Discord deep links and service configuration
- Dantotsu-specific remote/service configuration
- Dantotsu branding in resources and user-facing metadata

## Migrate later, in controlled batches
- `ani.dantotsu.*` namespace
- Android `applicationId` / package identity
- Deep-link scheme currently using `dantotsu`
- Theme/resource identifiers containing Dantotsu
- Release/update metadata and repository references
- Discord RPC only after its player/reader lifecycle coupling is isolated

## AniLab target architecture
Android UI + Player + Client -> AniLab Backend (Auth/Premium/Entitlement/API/Routing) -> Provider -> Extractor/Stream

Backend must remain independently deployable so Provider/backend changes do not require a full Android rewrite.

## Workflow rule
Every meaningful source/config change gets its own checkpoint. Build/test is only marked green after actual validation. Codespaces is reserved for work that requires an execution environment (build, tests, server runtime, E2E); repository audit/edit work should be done directly when possible.

## Current status
- 🟢 Audit batches completed: dependencies, Crashlytics wiring, runtime Dantotsu services, local state/storage, background tasks, manifest/release footprint.
- 🟡 Implementation not started from this map.
- 🟡 Build/test validation pending for future source changes.
