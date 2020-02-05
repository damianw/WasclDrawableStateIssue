MaterialButton OnAttachStateChangeListener + Drawable State Issue
===

This project demonstrates a bug in `MaterialButton` (a widget in [Material Components for Android](https://github.com/material-components/material-components-android)).

Issue: [material-components/material-components-android#982](https://github.com/material-components/material-components-android/issues/982)

## Symptom

Calling `View.getDrawableState` on a `MaterialButton` within an `OnAttachStateChangeListener`
computes a new drawable state without notifying `drawableStateChanged`. This results in the
`MaterialButton` failing to update the drawable state of the background tint until the next time it
changes (by e.g. touching the button, etc). `getDrawableState` is used by many APIs and something as
simple as `setTextColor` will call `getDrawableState` and expose the problem.

## Demo

In the following demo, we attach a fragment (defined in [`MainFragment`](app/src/main/java/wtf/log/wascldrawablestateissue/MainFragment))
at some point after the activity (defined in [`MainActivity`](app/src/main/java/wtf/log/wascldrawablestateissue/MainActivity)) is created, and
while the window has focus. The background tint of the button (defined in [`button_tint_selector`](app/src/main/res/color/button_tint_selector.xml))
defines the disabled state as red with a default state of green.

<img src="demo.gif" width="480"/>

The background tint incorrectly displays the disabled state even though the button
is in the enabled state. Touching the button (or un-focusing and re-focusing the window, etc)
synchronizes the state to the background tint and the issue resolves itself. 
