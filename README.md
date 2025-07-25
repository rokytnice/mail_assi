# Mail Assistant Android App

This is a minimal example of an Android app that fetches emails, analyzes them using an AI model, and creates events or tasks based on the email content. When a task is created, a push notification is displayed.

The implementation is simplified with placeholders for Gmail, Google Calendar, and Google Tasks API integrations, as well as AI analysis (e.g., using Google Gemini). These parts need to be implemented with actual API calls and authentication logic.

## Structure
- `MainActivity.kt` – entry point of the app
- `GmailHelper.kt` – fetches emails (placeholder)
- `AiHelper.kt` – performs AI analysis of emails
- `CalendarHelper.kt` – adds events to Google Calendar
- `TasksHelper.kt` – adds tasks to Google Tasks and triggers notifications
- `NotificationHelper.kt` – handles push notifications

## Building
This project uses Gradle. Open it in Android Studio or run `./gradlew assembleDebug` to build the APK. Ensure you have the Android SDK installed.
