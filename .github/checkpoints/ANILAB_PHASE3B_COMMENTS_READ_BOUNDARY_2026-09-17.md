# AniLab Phase 3B — Comments Read Boundary

Date: 2026-09-17

## Change
Expanded `CommentsRepository` to cover the comments read surface used by the Android comments UI:
- paged comments with optional episode/chapter tag and sort order
- single comment lookup
- comment replies

## Compatibility
`LegacyCommentsRepository` delegates these operations to the existing `CommentsAPI`.

## Safety
No comments UI behavior was intentionally removed. The legacy API remains available as the compatibility transport.

## Validation
No Android build/test has been run for this change yet. Do not mark build/test green.

## Next
Move `CommentsFragment` read calls behind the repository, then define write/moderation/voting boundaries before replacing the legacy transport with AniLab Backend.
