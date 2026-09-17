# AniLab Identity Checkpoint

Date: 2026-09-17
Repository: `adenhernandez30-hub/Kakanime`
Branch: `main`
Baseline: current main at checkpoint creation

## Naming decision

The final product/brand name is **AniLab**.

`KakaAnime` is no longer the target final product name. Do not continue introducing new KakaAnime branding, package/resource identity, API naming, or user-facing labels where a new name is required.

## Migration rule

Do NOT perform a blind global rename immediately.

The existing Android source contains Dantotsu-derived architecture, including player/UI foundation. Package/class names and internal identifiers must be migrated carefully so the following remain intact:

- Player and playback architecture
- UI/UX architecture
- Navigation
- Gestures
- Transitions and animations
- Icons
- Fonts/typography
- Theme/design system
- Reusable components
- Episode/detail/player flow

## Target identity

User-facing identity:
- Product: AniLab
- App/brand naming: AniLab
- New backend/service naming: AniLab-owned

Technical migration may be staged. Existing identifiers may remain temporarily when changing them would risk breaking the preserved foundation, but no new work should deepen dependency on the old KakaAnime/Dantotsu identity.

## Architecture direction

AniLab Android
  UI/UX + Player + Client
          |
          v
AniLab Backend
  Auth / Premium / Entitlement / API / Routing
          |
          v
Provider Layer
  Search / Anime / Episodes / Sources
          |
          v
Extractor / Stream Layer

## Status

- 🟢 Final product name decided: AniLab.
- 🟡 Existing KakaAnime/Dantotsu identifiers still require staged migration.
- 🟡 No blind global rename performed.
- 🟡 Full identity/config/package audit remains in progress.
