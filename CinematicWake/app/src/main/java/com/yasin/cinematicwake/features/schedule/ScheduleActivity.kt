package com.yasin.cinematicwake.features.schedule

import android.app.TimePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.yasin.cinematicwake.ui.theme.CinematicWakeTheme
import java.util.Locale

class ScheduleActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CinematicWakeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ScheduleScreen(
                        modifier = Modifier.padding(innerPadding),
                        onTimeChanged = { hour, minute ->
                            // Save new time
                            ScheduleSelectionStore.setCurrentTime(this, hour, minute)
                            // Reschedule alarm with new time
                            val repo = InMemoryScheduleRepository(this)
                            val scheduler = AlarmScheduler(this, repo)
                            scheduler.scheduleAll()
                            // Go back to home
                            finish()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onTimeChanged: (Int, Int) -> Unit
) {
    val context = LocalContext.current
    val (initialHour, initialMinute) = remember {
        ScheduleSelectionStore.getCurrentTime(context)
    }

    var hour by remember { mutableStateOf(initialHour) }
    var minute by remember { mutableStateOf(initialMinute) }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "Alarm time", modifier = Modifier.padding(bottom = 8.dp))

        Text(
            text = String.format(Locale.getDefault(), "%02d:%02d", hour, minute),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(
            onClick = {
                TimePickerDialog(
                    context,
                    { _, h, m ->
                        hour = h
                        minute = m
                        onTimeChanged(h, m)
                    },
                    hour,
                    minute,
                    true
                ).show()
            }
        ) {
            Text("Change time")
        }

        Button(
            onClick = { (context as? ScheduleActivity)?.finish() },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back")
        }
    }
}