package com.yasin.cinematicwake.features.schedule

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.yasin.cinematicwake.R
import com.yasin.cinematicwake.features.animation.FullscreenAlarmActivity
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val CHANNEL_ID = "cinematic_wake_alarm_channel"
        private const val CHANNEL_NAME = "Cinematic Wake Alarms"
        private const val NOTIFICATION_ID = 1001
    }

    override fun onReceive(context: Context, intent: Intent) {
        val now = Date()
        Log.d("CinematicWake", "AlarmReceiver.onReceive at $now")

        showFullScreenAlarmNotification(context)
    }

    private fun showFullScreenAlarmNotification(context: Context) {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        ensureChannel(nm)

        // Intent used for full-screen.
        val fullScreenIntent = Intent(context, FullscreenAlarmActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                    Intent.FLAG_ACTIVITY_CLEAR_TOP
        }

        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            0,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher) // replace later with a proper alarm icon
            .setContentTitle("Cinematic Wake")
            .setContentText("Alarm is ringing")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setOngoing(true)               // behaves more like a real alarm
            .setAutoCancel(true)
            // This is the key: request full-screen UI
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .build()

        nm.notify(NOTIFICATION_ID, notification)
    }

    private fun ensureChannel(nm: NotificationManager) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH   // high importance for alarms
            ).apply {
                description = "Alarm notifications for Cinematic Wake"
                lockscreenVisibility = android.app.Notification.VISIBILITY_PUBLIC
            }
            nm.createNotificationChannel(channel)
        }
    }
}