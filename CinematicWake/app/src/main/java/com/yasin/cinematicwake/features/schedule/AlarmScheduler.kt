package com.yasin.cinematicwake.features.schedule

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.Calendar

class AlarmScheduler(
    private val context: Context,
    private val repository: ScheduleRepository
) {

    fun scheduleAll() {
        val alarms = repository.getAll().filter { it.enabled }
        if (alarms.isEmpty()) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Respect exact alarm capability on Android 12+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val canScheduleExact = alarmManager.canScheduleExactAlarms()
            if (!canScheduleExact) {
                Log.w("CinematicWake", "Cannot schedule exact alarms: permission not granted")
                return
            }
        }

        // Read the selected calendar date from the store
        val (year, month, day) = ScheduleSelectionStore.getCurrentDate(context)
        val nowMillis = System.currentTimeMillis()

        alarms.forEach { alarm ->
            val triggerTimeMillis = nextTriggerTimeMillis(
                year = year,
                month = month,
                day = day,
                hour = alarm.hour,
                minute = alarm.minute
            )

            if (triggerTimeMillis <= nowMillis) {
                // One-off semantics: if the selected date/time is in the past,
                // we do NOT schedule anything automatically.
                Log.w(
                    "CinematicWake",
                    "Selected date/time is in the past, not scheduling (id=${alarm.id})"
                )
                return@forEach
            }

            val pendingIntent = createPendingIntent(context, alarm.id)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(
                    AlarmManager.RTC_WAKEUP,
                    triggerTimeMillis,
                    pendingIntent
                )
            }
        }
    }

    private fun nextTriggerTimeMillis(
        year: Int,
        month: Int,  // 1..12 (as stored in ScheduleSelectionStore)
        day: Int,
        hour: Int,
        minute: Int
    ): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            // Calendar months are 0-based, store is 1-based
            set(Calendar.MONTH, month - 1)
            set(Calendar.DAY_OF_MONTH, day)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        return calendar.timeInMillis
    }

    companion object {
        fun createPendingIntent(context: Context, alarmId: Int): PendingIntent {
            val intent = Intent(context, AlarmReceiver::class.java)

            val flags = PendingIntent.FLAG_UPDATE_CURRENT or
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        PendingIntent.FLAG_IMMUTABLE
                    } else {
                        0
                    }

            return PendingIntent.getBroadcast(
                context,
                alarmId,
                intent,
                flags
            )
        }
    }
}