# AniLab Phase 3D — AniList Logout Boundary

Date: 2026-09-17

## Scope
Detach AniList account logout from the legacy comments transport without changing AniList authentication itself.

## Changes
- Expanded `CommentsRepository` with `logout()`.
- Kept `LegacyCommentsRepository` as the compatibility adapter and delegated `logout()` to the existing legacy comments implementation.
- Removed the direct `CommentsAPI` import from `Anilist.kt`.
- Removed the direct legacy comments logout call from `Anilist.removeSavedToken()`.

## Result
`Anilist.removeSavedToken()` now clears AniList-local account state only. The comments subsystem exposes its logout operation through the repository boundary for the later UI/backend migration.

## Validation
Source changes committed through GitHub. Android build/test has not been run in this checkpoint.

## Status
- Repository boundary: 🟢
- AniList logout decoupling: 🟢
- Legacy comments transport removal: 🔴 pending remaining consumers
- Build/test: 🟡 pending

## Next
Audit and migrate the remaining direct `CommentsAPI` consumers, prioritizing the comments UI surface (`CommentsFragment` / `CommentItem`) before replacing the legacy adapter with the AniLab backend implementation.
