# PSX Intelligence Android App

This Android project is pre-connected to the deployed PSX Streamlit backend:

`https://psxbackendmobile-jquev7jajg6fgfmiwvexhq.streamlit.app`

## Main Android navigation

- Alerts → `?page=alerts`
- Decision → `?page=decision`
- Scenario → `?page=scenario`
- Divergence → `?page=divergence`
- Portfolio → `?page=portfolio`

The app also adds `embed=true` for a cleaner Streamlit view inside Android.

## Important: Streamlit visibility

The backend currently redirects to Streamlit authentication. For the smoothest APK experience, make the Streamlit app public before distributing the APK. If it remains private, users will need Streamlit access/authentication.

## Upload to GitHub

Create an empty GitHub repository. Extract this ZIP, open CMD inside the extracted folder, then run:

```bat
git init
git add .
git commit -m "PSX Android app connected to live backend"
git branch -M main
git remote add origin https://github.com/YOUR-USERNAME/PSX-Android-App.git
git push -u origin main
```

## Build APK on GitHub

The repository includes:

`.github/workflows/build-apk.yml`

After pushing to `main`, GitHub Actions will build the debug APK automatically.

Open your GitHub repository → **Actions** → **Build PSX Android APK** → latest successful run → download artifact **PSX-Intelligence-APK**.

The APK inside the artifact is `app-debug.apk`.

## Android build configuration

- Application ID: `com.digitalkarachi.psx`
- minSdk: 24
- targetSdk: 36
- compileSdk: 36
- Version: 1.0.1 (code 2)
- Android Gradle Plugin: 8.13.2
- Gradle used by GitHub Actions: 8.13
- Java: 17
