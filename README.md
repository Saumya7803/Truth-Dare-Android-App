# Truth Dare Android App

Truth Dare is a simple Android party game where players sit in a circle and spin for turns.  
The selected player gets a random truth or dare prompt.

## What Changed (v1.1.0)

- Single-question gameplay: one Truth or Dare prompt at a time
- `NEXT` now returns players to the Truth/Dare selection screen
- Improved randomization to reduce quick repeats in a session
- Upgraded question bank with office-friendly adult group content
- Refreshed screenshots and gameplay demo video

## Demo Video

- Watch full gameplay demo: [Play MP4 (Raw)](https://raw.githubusercontent.com/Saumya7803/Truth-Dare-Android-App/main/demo-video/truth-dare-gameplay-demo.mp4)
- Alternate link: [Repository file](demo-video/truth-dare-gameplay-demo.mp4)

## Demo Preview

![Truth Dare Demo](src/video.gif)

## Features

- Spin-based player selection
- Single question gameplay (one truth/dare at a time)
- NEXT button returns to Truth/Dare selection screen
- Improved randomization with reduced repeats per session
- Office-friendly, adult group question set
- Add custom truths and dares
- Lightweight offline experience

## Tech Stack

- Java
- Android SDK (compile SDK 29)
- AndroidX
- Gradle wrapper (`gradle-5.6.4`, AGP `3.6.3`)

## Project Structure

- `app/src/main/java/com/farizma/truthdare` - app source code
- `app/src/main/res` - layouts, drawables, strings, assets
- `demo-video/truth-dare-gameplay-demo.mp4` - full demo video

## Getting Started

### Requirements

- Android Studio (recommended)
- JDK 8
- Android SDK Platform 29

### Run Locally

```bash
git clone https://github.com/Saumya7803/Truth-Dare-Android-App.git
cd Truth-Dare-Android-App
./gradlew assembleDebug
```

On Windows:

```bat
gradlew.bat assembleDebug
```

Generated APK path:

- `app/build/outputs/apk/debug/app-debug.apk`

## CI/CD

GitHub Actions workflows are included:

- `.github/workflows/android-build.yml` - builds debug APK on push/PR
- `.github/workflows/android-release.yml` - builds release APK on `v*` tags and publishes a GitHub release

## Create a Release

```bash
git tag v1.0.0
git push origin v1.0.0
```

This triggers the release workflow and uploads the release APK to GitHub Releases.

## License

This project is licensed under the MIT License. See `LICENSE` for details.

