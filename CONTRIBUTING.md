# Contributing

Thanks for your interest in improving iRetard.

## Development Setup

1. Clone the repository.
2. Open Chrome and go to chrome://extensions.
3. Enable Developer mode.
4. Click Load unpacked and select this project folder.
5. Make your changes and click Reload on the extension card to test updates.

## Pull Request Guidelines

- Keep changes focused and minimal.
- Preserve strict behavior for feed blocking and usage enforcement.
- Test on both hosts:
  - https://instagram.com
  - https://www.instagram.com
- Verify all of these before submitting:
  - Home feed remains blocked.
  - Reels routes redirect to Direct.
  - Timer counts correctly.
  - 5-minute challenge appears and pauses timer until solved.

## Commit Style

Use clear, short commit messages describing behavior changes.

Examples:

- fix: restore challenge checkpoint sync on resume
- feat: improve challenge modal accessibility and contrast
