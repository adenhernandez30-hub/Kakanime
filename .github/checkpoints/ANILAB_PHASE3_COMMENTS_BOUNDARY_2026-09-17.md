# AniLab Phase 3 — Comments Boundary

Date: 2026-09-17

## Change
Added `CommentsRepository` as the app-facing boundary for the comments subsystem.

## Current adapter
`LegacyCommentsRepository` temporarily delegates to the existing `CommentsAPI` implementation.

## Purpose
Keep Android UI decoupled from the legacy Dantotsu comments transport so the backend can later be replaced by AniLab without redesigning the UI layer.

## Scope
- No deletion of `CommentsAPI`.
- No deletion of comments UI.
- No backend endpoint introduced yet.
- No claim of build/test success; validation is still pending.

## Next
Migrate `CommentsFragment` and related consumers to the repository boundary, then replace the legacy adapter with an AniLab backend implementation.
