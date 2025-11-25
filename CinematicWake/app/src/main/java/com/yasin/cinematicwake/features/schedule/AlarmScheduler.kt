package com.yasin.cinematicwake.features.schedule

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

class AlarmScheduler(
    private val context: Context,
    private val repository: ScheduleRepository
) {

    @SuppressLint("ScheduleExactAlarm")
    fun scheduleAll() {
        val alarms = repository.getAll().filter { it.enabled }
        if (alarms.isEmpty()) return

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        for (alarm in alarms) {
            val triggerTimeMillis = nextTriggerTimeMillis(alarm.hour, alarm.minute)
            val pendingIntent = createPendingIntent(context, alarm.id)

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                triggerTimeMillis,
                pendingIntent
            )
        }
    }

    private fun nextTriggerTimeMillis(hour: Int, minute: Int): Long {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)

            // If time already passed today, schedule for tomorrow
            if (timeInMillis <= System.currentTimeMillis()) {
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        return calendar.timeInMillis
    }

    companion object {
        fun createPendingIntent(context: Context, alarmId: Int): PendingIntent {
            val intent = Intent(context, AlarmReceiver::class.java)

            val flags = PendingIntent.FLAG_UPDATE_CURRENT or
                    PendingIntent.FLAG_IMMUTABLE

            return PendingIntent.getBroadcast(
                context,
                alarmId,
                intent,
                flags
            )
        }
    }
}
