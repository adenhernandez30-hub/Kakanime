# AniLab Migration Checkpoint — Phase 2

Date: 2026-09-17
Branch: main

## Change
Removed the legacy Dantotsu comment-notification worker from `TaskScheduler`.

## Preserved
- AniList notification scheduling
- Subscription notification scheduling
- WorkManager / AlarmManager scheduler infrastructure

## Intentionally not removed yet
- Comment UI and `CommentsAPI`
- Legacy comment worker source file
- Other unrelated notification infrastructure

## Validation
Source change committed directly to `main`. Android build/test validation is still pending and must not be considered green until executed.
