# AniLab Phase 3D — Comments Fragment Boundary

Date: 2026-09-17

## Scope

Migrate `CommentsFragment` UI operations behind `CommentsRepositoryProvider` without changing user-facing comment behavior.

## Completed

- Comment authentication checks route through `CommentsRepository`.
- Comment list/pagination routes through `getCommentsForId`.
- Single-comment loading routes through `getSingleComment`.
- Replies route through `getRepliesFromId`.
- Create/reply routes through `createComment`.
- Edit routes through `editComment`.
- Existing banned/session checks route through repository boundary.
- `InteractionState` compatibility type was restored after build validation exposed the missing type.

## Validation

GitHub Actions run `35226878285` on commit `51316f8b23683bcba2e8ce469c60f79c2ed625a1` completed successfully.

- Build F-Droid debug APK: success
- Upload APK: success
- Artifact: `KakaAnime-debug-apk`

## Remaining legacy boundary

`LegacyCommentsRepository` still delegates to the existing `CommentsAPI`. This is intentional; transport replacement is a later phase.

## Notification audit result

The legacy comment-notification subsystem is NOT yet safe to delete. Current references include:

- `SettingsNotificationActivity` → `CommentNotificationWorker` and `CommentNotificationInterval`
- `NotificationFragment` → `CommentStore` / `CommentNotificationStore`
- `NotificationItem` → `CommentNotificationStore`
- `CommentNotificationTask` → `CommentNotificationStore`
- `CommentStore` → `CommentNotificationWorker.NotificationType`
- `AndroidManifest.xml` → `CommentNotificationReceiver`
- `Preferences.kt` → comment notification preferences

Therefore no destructive notification cleanup is included in this checkpoint. Scheduler infrastructure remains intact.
