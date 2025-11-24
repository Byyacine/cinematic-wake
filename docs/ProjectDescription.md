# Project Description: Cinematic Wake App

## 1. Project Name (working title)

**Cinematic Wake**\
*(placeholder -- can be updated later)*

## 2. Project Overview

Cinematic Wake is an Android application designed to deliver beautiful,
immersive animations at meaningful moments of the day. Instead of
overwhelming the user with constant notifications or intrusive pop-ups,
the app creates a subtle cinematic ritual that appears naturally when
the user wakes their phone.

The app combines scheduled triggers (specific times chosen by the user)
with user presence detection (screen-on events) to ensure each animation
is seen and experienced. This creates an emotionally engaging,
personalized "welcome moment" whenever the user turns on their device.

## 3. Core Concept

At its heart, Cinematic Wake is a hybrid-trigger animation experience:

### • Scheduled animations

The user selects specific times of the day when they want a cinematic
animation to "occur."

### • Presence-based playback

If the scheduled moment passes while the user is not looking at their
phone, the animation will be stored as pending and will play
automatically the next time the user turns on their screen, even if the
device is still locked.

**Goal:**\
Ensure every cinematic moment is experienced intentionally, without
being missed or intrusive.

## 4. Why This App Is Unique

Most apps trigger content either at a specific time (notifications) or
only during user interaction (screen events).\
Cinematic Wake combines both:

-   The meaning and rhythm of specific times\
-   The guaranteed visibility of screen-on triggers

This hybrid system produces: - higher engagement\
- more emotional impact\
- a ritualistic user experience\
- stronger retention\
- unique branding potential

The app becomes a kind of personal ambient companion, greeting the user
with a visual experience that evolves through time, day, seasons, or
moods.

## 5. Key Features

### 5.1 Cinematic Animations

-   High-quality looping videos or visual scenes\
-   Fullscreen, immersive mode\
-   Hides system UI and status bar\
-   Smooth fade-in from black for a premium feel

### 5.2 Scheduled Moments

-   Users choose daily times (e.g., 7:00 AM, 1:00 PM, 8:30 PM)\
-   App records these times using Android's AlarmManager\
-   If the user is idle during the animation time, it becomes "pending"

### 5.3 Hybrid Trigger System

-   If a scheduled animation time is missed, the app will show the
    animation the next time the user wakes the screen
-   Triggered by:
    -   `SCREEN_ON`
    -   `USER_PRESENT`\
-   Ensures the user always sees the cinematic moment

### 5.4 Lock-Screen Compatible

Animations can play: - over the lock screen\
- immediately after the screen wakes\
- without requiring the user to unlock first\
- while still keeping the device secure

### 5.5 User Preferences

-   Enable/disable scheduled animations\
-   Choose which animation to show for each time\
-   Adjust volume or mute\
-   Optional: random daily animations\
-   Optional: seasonal/ambient packs

## 6. User Experience Vision

### 6.1 Emotional Goal

The user should feel: - welcomed\
- calm\
- aesthetically pleased\
- surprised in a positive way\
- connected to the rhythm of their day

### 6.2 Example Scenario

At 20:00, the user had their phone in their pocket. The scheduled
animation passes.\
At 20:15, they press the power button to check a notification.\
The screen lights up... and without clutter, the app gracefully fades in
a beautiful sunset animation.

A moment of cinematic calm before they unlock their phone.

## 7. Target Users

-   Aesthetic-focused smartphone users\
-   People who enjoy ambient visuals\
-   Wellness / mindfulness enthusiasts\
-   Productivity-oriented users who like structured daily moments\
-   Users who enjoy personalization and ritual-like experiences

## 8. Monetization Possibilities

(Not mandatory for MVP -- can be evaluated later.)

-   Premium animation packs\
-   Monthly subscription for weekly new animations\
-   One-time "lifetime unlock"\
-   Seasonal theme bundles\
-   Partnership with animation artists for curated collections

## 9. Technical Summary

### Main Components

-   AlarmManager → schedules animation triggers\
-   Foreground service → listens for screen events\
-   BroadcastReceiver → detects `SCREEN_ON` or `USER_PRESENT`\
-   Pending flag system → ensures missed animations are shown later\
-   Fullscreen Activity → displays the cinematic animation\
-   SharedPreferences → lightweight state management

### Android Permissions Required

-   Wake lock\
-   Draw over lock screen (via window flags)\
-   Foreground service\
-   Exact alarm permission (Android 12+)

## 10. MVP Milestones

### Milestone 1 --- Core Logic

-   Create scheduled triggers\
-   Detect screen wake\
-   Play animation on screen-on if pending

### Milestone 2 --- Animation Player

-   Fullscreen immersive player\
-   Fade-in effect\
-   Lock-screen compatible activity

### Milestone 3 --- Basic UI

-   Time selection\
-   Activate/deactivate schedule\
-   Animation preview

### Milestone 4 --- Stability & Battery Optimization

-   Optimize background services\
-   Ensure system compatibility across devices

## 11. Long-Term Vision

Over time, Cinematic Wake could evolve into: - a platform for ambient
digital art\
- a daily ritualization tool\
- a biometrics-aware greeting system\
- a time and mood-based visual companion\
- a premium aesthetic experience for smartphones
