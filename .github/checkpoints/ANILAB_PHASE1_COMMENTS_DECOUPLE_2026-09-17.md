# AniLab Phase 1 — Legacy Comments Decoupling

Date: 2026-09-17

## Change
Detached application startup from the legacy Dantotsu Comments API authentication flow.

- Removed the `CommentsAPI` import from `App.kt`.
- Removed automatic `CommentsAPI.fetchAuthToken(...)` execution during application startup.
- Kept the existing comments subsystem in place for a later dedicated migration/removal step; no destructive deletion was performed in this phase.
- AniList remains independent from this startup path.

## Validation status
Source change committed to `main`.
Android build/test has **not** been run for this checkpoint yet.

## Next
Audit and migrate remaining direct CommentsAPI consumers, then validate the Android build before marking build/test green.
