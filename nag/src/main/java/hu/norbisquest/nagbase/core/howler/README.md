# Howler audio backend

The engine uses the vendored **Howler.js 2.2.4 core** distribution:
https://github.com/goldfire/howler.js/tree/v2.2.4

- `howler.core.min.js` is the upstream distribution, unchanged.
- `LICENSE.howler.txt` contains its MIT license.
- `HowlerLibrary` injects it once through a GWT `TextResource`, into the host window.
  Host HTML pages need no extra script tag or CDN access.
- `Howl` contains the native JsInterop bindings; game code uses `IAudioChannel`.

Each channel owns one Howl and controls one sound ID. Pause resumes that ID;
stop resets playback; destroy/reload unloads the old Howl. Natural completion
keeps the channel reusable. Loop boundaries do not notify `onEnded`.

Playback blocked by browser autoplay policy retries once Howler signals `unlock`
from a user gesture. Other load failures and a failed unlock retry report
`onError`; speech falls back to its timed animation/text path and waiting sound
effects finish. Stopping or destroying a channel cancels its pending playback.

The backend buffers audio using Web Audio where supported, with Howler's HTML5
fallback. Music streaming and spatial audio are not enabled.

Update the pinned distribution and license together. Verify Java tests and GWT
compilation, then exercise initial playback before/after a click, overlapping
channels, pause/resume, loops, missing files, and screen cleanup in a browser.
