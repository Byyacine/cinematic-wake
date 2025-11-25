package com.yasin.cinematicwake.features.schedule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.yasin.cinematicwake.features.animation.FullscreenAlarmActivity
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val now = Date()

        Log.d("CinematicWake", "Alarm triggered at $now")

        Toast.makeText(
            context,
            "CinematicWake alarm triggered at $now",
            Toast.LENGTH_LONG
        ).show()

        val activityIntent = Intent(context, FullscreenAlarmActivity::class.java).apply {
            addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
            )
        }

        context.startActivity(activityIntent)
    }
}
