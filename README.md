# MAD Lab Exam Repository (Android Studio Ready)

This repository now contains a complete Android Studio project configuration.

## Project Structure

- Root Gradle files: `settings.gradle.kts`, `build.gradle.kts`, `gradle.properties`
- Android module: `app/`
- Kotlin source: `app/src/main/java/com/example/madlabexam/...`
- Resources: `app/src/main/res/...`
- Manifest: `app/src/main/AndroidManifest.xml`

The original question-wise folders (`01_...` to `11_...`) are kept as reference notes.

## What Runs

All implemented modules are wired into a single launcher screen:
- `HomeActivity` (launcher)
- Exercise 1 and Exercise 2
- PS2, PS3, PS4, PS5, PS6, PS7
- Mock Test 1 (menu app + music service + theme toggle)

## Requirement Mapping

- Exercise 1: Converter with LinearLayout, TextView result, Toast result
- Exercise 2: Hungry to Happy character state change
- PS2 Program 1: Visiting card with logo, centered company name, divider line, full contact details
- PS2 Program 2: Basic calculator using EditText, Button, TextView
- PS3 Feedback: RatingBar on-change messages, max 100 chars, required message, next-screen display with color/indent
- PS3 Calculator: Equals button shows Toast result with divide-by-zero validation
- PS3 Layout variants: Linear and Relative layout XML samples are included
- PS4: Widget/adapter form with validations and intent-based result screen
- PS5: Student registration with EditText, Spinner, RadioButton, CheckBox, DatePicker, AutoCompleteTextView, and validations
- PS6: Hospital doctor search directory with one-tap calling and runtime permission handling
- PS7: OTP listener starter and settings flows for internet, USB debugging, keyboard, and location
- Mock Test 1: Menu-driven WhatsApp message flow, background music service, and dark/light theme toggle

## UI Consistency

- Unified app theme and colors are applied across modules for a clean, professional look.
- Shared input/button styles are defined in `app/src/main/res/values/styles.xml`.
- Reusable visual surfaces are in `app/src/main/res/drawable/bg_input.xml` and `app/src/main/res/drawable/bg_card.xml`.

## Open In Android Studio

1. Open this folder as a project.
2. Let Gradle sync.
3. Run app on emulator/device.
4. From home screen, tap any module to test that question flow.

## Notes

- Required permissions and OTP dependency are already added.
- Placeholder drawables for logo/happy/unhappy are included.
- Validation and intent flows are included for all form-based questions.
