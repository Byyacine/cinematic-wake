package com.yasin.cinematicwake

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.yasin.cinematicwake.features.animation.AnimationSelectionStore
import com.yasin.cinematicwake.features.animation.AnimationStoreActivity
import com.yasin.cinematicwake.features.schedule.AlarmScheduler
import com.yasin.cinematicwake.features.schedule.InMemoryScheduleRepository
import com.yasin.cinematicwake.features.schedule.ScheduleActivity
import com.yasin.cinematicwake.ui.home.HomeScreen
import com.yasin.cinematicwake.ui.theme.CinematicWakeTheme

class MainActivity : ComponentActivity() {

    companion object {
        private const val REQ_POST_NOTIFICATIONS = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestNotificationPermissionIfNeeded()

        // Schedule alarms once at app start (now uses stored time)
        val repository = InMemoryScheduleRepository(this)
        val scheduler = AlarmScheduler(this, repository)
        scheduler.scheduleAll()

        enableEdgeToEdge()
        setContent {
            CinematicWakeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    val current = AnimationSelectionStore.getCurrent(this)

                    HomeScreen(
                        modifier = Modifier.padding(innerPadding),
                        currentAnimation = current,
                        onOpenAnimationStore = {
                            startActivity(Intent(this, AnimationStoreActivity::class.java))
                        },
                        onOpenSchedule = {
                            startActivity(Intent(this, ScheduleActivity::class.java))
                        }
                    )
                }
            }
        }
    }

    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions(
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    REQ_POST_NOTIFICATIONS
                )
            }
        }
    }
}