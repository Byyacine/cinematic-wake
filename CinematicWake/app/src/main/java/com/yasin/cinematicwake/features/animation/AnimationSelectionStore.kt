package com.yasin.cinematicwake.features.animation

import android.content.Context
import com.yasin.cinematicwake.R

enum class AnimationChoice(val rawResId: Int, val label: String) {
    ANIMATION_1(R.raw.animation_1, "Animation 1"),
    ANIMATION_2(R.raw.animation_2, "Animation 2"),
    ANIMATION_3(R.raw.animation_3, "Animation 3"),
    ANIMATION_4(R.raw.animation_4, "Animation 4"); // keep your current one
}

object AnimationSelectionStore {

    private const val PREFS_NAME = "cinematic_wake_prefs"
    private const val KEY_ANIMATION = "current_animation"

    fun getCurrent(context: Context): AnimationChoice {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val name = prefs.getString(KEY_ANIMATION, AnimationChoice.ANIMATION_4.name)
        return AnimationChoice.values().find { it.name == name } ?: AnimationChoice.ANIMATION_4
    }

    fun setCurrent(context: Context, choice: AnimationChoice) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_ANIMATION, choice.name).apply()
    }
}