# User Interface Mockup

````markdown
# Cinematic Wake – User Interface Mockup

This document describes the user interface mockup for the Cinematic Wake Android application.  
It is structured into five main screens:

1. Home / Schedule list  
2. Add / Edit schedule  
3. Animation gallery  
4. Settings  
5. Playback (cinematic screen)

---

## 1. Home / Schedule list

**Purpose**: main hub to see and manage all scheduled animations.

### Layout (portrait)

```text
------------------------------------------------
Cinematic Wake                      [☼ Global ON]
------------------------------------------------
Today’s ambience
[ ] Morning  07:00   • Sunrise glow        [ON>]
[ ] Noon     12:30   • Soft sky           [OFF>]
[ ] Evening  20:15   • Golden hour        [ON>]

[ + Add schedule ]

Bottom bar (optional):
[Home]   [Animations]   [Settings]
------------------------------------------------
````

### Key elements

* App bar

  * Title: `Cinematic Wake`
  * Global toggle: master on/off switch for all schedules.

* Schedule list

  * Each row:

    * Checkbox or small icon to indicate state (optional).
    * Label for moment (for example “Morning”).
    * Time (for example “07:00”).
    * Small subtitle with animation name.
    * Per schedule toggle: ON / OFF.
    * Tap row to open “Edit schedule”.

* Primary action

  * “+ Add schedule” button (floating or at bottom).

The screen should feel quiet and minimal: neutral background, simple typography, subtle section title “Today’s ambience”.

---

## 2. Add / Edit schedule screen

**Purpose**: define when an animation is associated with a time of day.

### Layout

```text
------------------------------------------------
[←] New schedule                       [Save]
------------------------------------------------

Label
[ Morning wake-up          ]

Time
[ 07 : 00  ▾ ]

Animation
[ Sunrise glow             ▸ ]

Behaviour
[✓] Play next time screen turns on 
    after this time
[ ] Try to wake screen exactly at this time

Status
[ Toggle: Enabled  ◉]

[ Delete schedule ]   (only in edit mode)
------------------------------------------------
```

### Key elements

* Label field

  * Simple text field (for example “Morning wake-up”, “Focus break”).

* Time picker

  * Hour and minute, 24 h format.
  * Standard Android time picker when pressed.

* Animation selector

  * Shows the currently selected animation name and a chevron.
  * Opens the Animation gallery screen.

* Behaviour section (hybrid logic)

  Two options:

  * “Play next time screen turns on after this time” (default enabled).
  * “Try to wake screen exactly at this time” (optional, more advanced).

* Enabled toggle

  * Switch to enable or disable this schedule without deleting it.

* Save / Delete

  * Save button in header.
  * Delete button only visible when editing an existing schedule.

---

## 3. Animation gallery screen

**Purpose**: choose which cinematic animation is linked to a schedule.

### Layout

```text
------------------------------------------------
[←] Choose animation
------------------------------------------------

[ ▢ Preview thumbnail ]  Sunrise glow
    Soft warm gradient, slow moving

[ ▢ Preview thumbnail ]  Ocean calm
    Gentle waves and blue tones

[ ▢ Preview thumbnail ]  Forest mist
    Green, soft fog drifting

[ ▢ Preview thumbnail ]  Night city
    Lights, slow pan

------------------------------------------------
[ Preview ]  [ Select ]
------------------------------------------------
```

### Key elements

* List of animations

  * Each item:

    * Thumbnail (static image or short loop).
    * Title (for example “Sunrise glow”).
    * One line description (colour / mood).
  * Tap to select one item (highlight with border or radio icon).

* Bottom bar

  * `Preview` button: opens full-screen playback of the chosen animation.
  * `Select` button: confirms the choice and returns to the schedule editor.

Optional: small tags such as “Calm”, “Energetic”, “Night”, “Nature” to help categorisation.

---

## 4. Settings screen

**Purpose**: global behaviour and app-level configuration.

### Layout

```text
------------------------------------------------
[←] Settings
------------------------------------------------

General
[ Global switch ] Cinematic Wake enabled

Playback behaviour
[✓] Only play when screen is manually turned on
[ ] Allow auto wake at scheduled time

Sound
[ Volume slider:  ─────●──── ]
[✓] Mute by default

Battery & system
[▶] Battery optimisation tips
[▶] Permissions status

About
[▶] Version, credits, privacy
------------------------------------------------
```

### Key elements

* General

  * Master toggle for Cinematic Wake, consistent with Home screen.

* Playback behaviour

  * “Only play when screen is manually turned on” as safe default.
  * “Allow auto wake at scheduled time” as an optional advanced setting, possibly with explanatory text.

* Sound

  * Volume slider.
  * Mute by default checkbox.

* Battery & system

  * Info section explaining:

    * Foreground service behaviour.
    * Exact alarm permission.
    * Possible instructions if the system kills background activity.

* About

  * Version, credits, privacy policy and similar information.

---

## 5. Playback / cinematic screen

**Purpose**: the “wow” moment when the animation actually shows.

### Layout

Full-screen minimal interface.

```text
+----------------------------------------------+
|                                              |
|         [   ANIMATION PLAYING HERE   ]       |
|       Fullscreen video / visual loop         |
|                                              |
|   (no status bar, no nav bar, dark edges)    |
|                                              |
|                    ◁  0:18 / 0:30   ✕       |
+----------------------------------------------+
```

### Behaviour and controls

* Default view

  * No text.
  * No visible controls at first.
  * Fades in from black in around 0.5–1.0 seconds.

* On tap

  * Show minimal overlay:

    * Small progress indicator or dot.
    * Close button `✕` in a corner.
  * Overlay auto hides after a short delay.

* End of animation

  * Either:

    * Loop once and then fade to black, or
    * For short loops, play for a fixed duration (for example 10–20 seconds), then fade to black.
  * After fade out, the system returns to the previous lock screen or unlocked state as it was before.

---

```
```
