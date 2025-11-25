package com.yasin.cinematicwake.features.schedule

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.util.Date

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        Log.d("CinematicWake", "Alarm triggered at ${Date()}")
    }
}
