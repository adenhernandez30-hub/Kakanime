# KakaAnime Checkpoint — Dantotsu Foundation Preservation

Date: 2026-09-17
Repository: `adenhernandez30-hub/Kakanime`
Branch: `main`
Baseline commit at checkpoint: `e091178beee2b981b5f131903bd5a6bf9a21453c`

## Direction

KakaAnime is based on the current Dantotsu-derived Android source, but Dantotsu services/backend are **not** the target architecture.

The goal is to remove/replace Dantotsu-owned backend/service dependencies while preserving the useful application foundation already present in the source.

## Preserve — do not remove during migration

- Player and playback architecture
- Existing UI/UX architecture and screen structure
- Navigation behavior
- Gesture and interaction behavior
- Transitions and animations
- Icon system and icon resources
- Typography/font setup
- Theme/design system
- Reusable UI components
- Episode/detail/player interaction flow
- Responsive/layout behavior
- Android foundation required by the above features

A Dantotsu-origin package/class is not automatically a deletion target. It must be audited by function and dependency first.

## Replace / audit

Audit all Dantotsu-owned or Dantotsu-specific service dependencies, including:

- `api.dantotsu.app`
- Dantotsu authentication/session dependencies
- Dantotsu comments backend
- Dantotsu account/sync services
- Dantotsu-specific remote endpoints
- Dantotsu-related configuration and deep links
- Dantotsu telemetry/analytics where applicable
- Any other runtime dependency that requires Dantotsu infrastructure

Replacement target is KakaAnime-owned backend/services or local Android functionality where appropriate.

## Remove

- Dantotsu branding/identity shown to users
- Dantotsu service URLs that remain runtime dependencies
- Dantotsu-specific service configuration that is no longer required
- Non-required Dantotsu identity remnants, subject to license/NOTICE requirements

## Architecture target

```text
KakaAnime Android
  UI/UX + Player + Client
          |
          v
KakaAnime Backend
  Auth / Premium / Entitlement / API / Routing
          |
          v
Provider Layer
  Search / Anime / Episodes / Sources
          |
          v
Extractor / Stream Layer
```

The backend/provider migration must remain decoupled from the preserved UI/player foundation so provider and backend implementations can evolve without rebuilding the UI architecture from scratch.

## Rules for next work

1. Audit first.
2. Do not perform broad refactors unless required.
3. Preserve player/UI foundation unless an audit proves it is Dantotsu-service-specific.
4. Replace service dependencies incrementally.
5. Validate after every meaningful change.
6. Only mark 🟢 after actual build/test validation succeeds.

## Current status

- 🟢 Direction/checkpoint recorded.
- 🟡 Full Dantotsu dependency audit remains in progress.
- 🟡 Backend replacement has not yet been implemented.
- 🟡 Dantotsu branding/service cleanup has not yet been implemented.
