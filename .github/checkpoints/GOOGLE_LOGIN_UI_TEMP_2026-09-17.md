# KakaAnime — Google Login UI Temporary Flow Checkpoint

Date: 2026-09-17
Repository: `adenhernandez30-hub/Kakanime`
Branch: `main`

## Status

- 🟢 Google OAuth Android client created in Google Cloud for debug package `ani.dantotsu.beta` using the debug SHA-1.
- 🟢 Login screen changed to a Google-only presentation.
- 🟢 Discord/GitHub/Telegram login entry points hidden from the login UI.
- 🟢 Google button currently uses a temporary UI-only flow: tapping it enters the existing Home/MainActivity directly.
- 🟢 No Google credential, token, or client secret is stored in the repository.
- 🟡 Real Google authentication is not implemented yet.
- 🟡 KakaAnime backend/session authentication is not implemented yet.
- 🟡 AniList linking after KakaAnime authentication is not implemented yet.

## Important preservation rules

- AniList remains the anime metadata, poster, calendar, and tracking layer.
- Provider remains streaming-only and is out of scope for this UI work.
- Player/Media3/ExoPlayer must remain intact.
- Preserve existing transitions, gestures, animations, and Dantotsu-derived foundation.
- Do not perform a blind package/global rename.
- Real authentication must replace the temporary direct-to-Home behavior only after the backend/session contract exists.

## Files changed in this feature

- `app/src/main/res/drawable/ic_google_g.xml`
- `app/src/main/res/layout/fragment_login.xml`
- `app/src/main/java/ani/dantotsu/home/LoginFragment.kt`

## Validation

- Repository changes were committed directly through GitHub.
- Android build validation is pending in GitHub Actions after the latest commits.
- Do not mark the feature FINAL/LOCKED until the generated APK is installed and tested by Shin.

## Next

Build the APK through GitHub Actions, install/test the login UI, then continue with the next UI/UX feature. Replace the temporary Google-to-Home action with real authentication only after the KakaAnime auth backend/session API is available.
