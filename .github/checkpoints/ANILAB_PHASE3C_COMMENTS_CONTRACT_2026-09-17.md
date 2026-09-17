# AniLab Phase 3C — Comments Contract

Date: 2026-09-17

## Change
Expanded `CommentsRepository` from read-only operations to the full app-facing comments capability contract.

## Contract areas
- Read: comments, single comment, replies
- Write: create and edit
- Moderation: delete, report, ban
- Voting: vote
- Session/role state: banned, admin, moderator, current user id

## Compatibility
`LegacyCommentsRepository` delegates to the existing `CommentsAPI` implementation. No legacy transport has been removed in this checkpoint.

## Validation
Build/test validation is pending. This checkpoint records source architecture only.

## Next
Migrate UI consumers to `CommentsRepository`, then replace the legacy adapter with an AniLab backend implementation.
