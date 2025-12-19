package com.yasin.cinematicwake.features.schedule

import android.content.Context
import java.time.LocalDate
import java.time.LocalTime

object ScheduleSelectionStore {

    private const val PREFS_NAME = "cinematic_wake_prefs"

    private const val KEY_HOUR = "alarm_hour"
    private const val KEY_MINUTE = "alarm_minute"
    private const val KEY_YEAR = "alarm_year"
    private const val KEY_MONTH = "alarm_month"
    private const val KEY_DAY = "alarm_day"

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Time of day used by the alarm engine (hour, minute).
     */
    fun getCurrentTime(context: Context): Pair<Int, Int> {
        val p = prefs(context)

        if (!p.contains(KEY_HOUR) || !p.contains(KEY_MINUTE)) {
            // Default once if not set: current time (rounded down to current minute)
            val now = LocalTime.now()
            p.edit()
                .putInt(KEY_HOUR, now.hour)
                .putInt(KEY_MINUTE, now.minute)
                .apply()
        }

        val hour = p.getInt(KEY_HOUR, 7)
        val minute = p.getInt(KEY_MINUTE, 0)
        return hour to minute
    }

    fun setCurrentTime(context: Context, hour: Int, minute: Int) {
        prefs(context).edit()
            .putInt(KEY_HOUR, hour)
            .putInt(KEY_MINUTE, minute)
            .apply()
    }

    /**
     * Calendar date chosen in the schedule screen (year, month, day).
     * Used for UI / UX; the engine still uses hour/minute for now.
     */
    fun getCurrentDate(context: Context): Triple<Int, Int, Int> {
        val p = prefs(context)

        if (!p.contains(KEY_YEAR) || !p.contains(KEY_MONTH) || !p.contains(KEY_DAY)) {
            val today = LocalDate.now()
            p.edit()
                .putInt(KEY_YEAR, today.year)
                .putInt(KEY_MONTH, today.monthValue)      // 1..12
                .putInt(KEY_DAY, today.dayOfMonth)
                .apply()
        }

        val today = LocalDate.now()
        val year = p.getInt(KEY_YEAR, today.year)
        val month = p.getInt(KEY_MONTH, today.monthValue)
        val day = p.getInt(KEY_DAY, today.dayOfMonth)

        return Triple(year, month, day)
    }

    fun setCurrentDate(context: Context, year: Int, month: Int, day: Int) {
        prefs(context).edit()
            .putInt(KEY_YEAR, year)
            .putInt(KEY_MONTH, month)  // store 1..12
            .putInt(KEY_DAY, day)
            .apply()
    }
}