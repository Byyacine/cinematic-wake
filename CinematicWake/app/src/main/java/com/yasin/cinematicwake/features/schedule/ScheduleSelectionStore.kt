package com.yasin.cinematicwake.features.schedule

import android.content.Context
import java.util.Calendar

object ScheduleSelectionStore {

    private const val PREFS_NAME = "cinematic_wake_prefs"
    private const val KEY_HOUR = "alarm_hour"
    private const val KEY_MINUTE = "alarm_minute"

    fun getCurrentTime(context: Context): Pair<Int, Int> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        return if (!prefs.contains(KEY_HOUR) || !prefs.contains(KEY_MINUTE)) {
            // Default: now + 1 minute (your current behaviour)
            val cal = Calendar.getInstance().apply {
                add(Calendar.MINUTE, 1)
            }
            val h = cal.get(Calendar.HOUR_OF_DAY)
            val m = cal.get(Calendar.MINUTE)
            setCurrentTime(context, h, m)
            h to m
        } else {
            val h = prefs.getInt(KEY_HOUR, 7)
            val m = prefs.getInt(KEY_MINUTE, 0)
            h to m
        }
    }

    fun setCurrentTime(context: Context, hour: Int, minute: Int) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putInt(KEY_HOUR, hour)
            .putInt(KEY_MINUTE, minute)
            .apply()
    }
}