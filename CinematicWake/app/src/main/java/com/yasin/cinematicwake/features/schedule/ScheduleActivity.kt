package com.yasin.cinematicwake.features.schedule

import android.app.DatePickerDialog
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
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
                        onDateTimeChanged = { year, month, day, hour, minute ->
                            // Persist new date + time
                            ScheduleSelectionStore.setCurrentDate(this, year, month, day)
                            ScheduleSelectionStore.setCurrentTime(this, hour, minute)

                            // Reschedule alarm using the stored time
                            val repo = InMemoryScheduleRepository(this)
                            val scheduler = AlarmScheduler(this, repo)
                            scheduler.scheduleAll()

                            // Go back to home
                            finish()
                        },
                        onBack = { finish() }
                    )
                }
            }
        }
    }
}

@Composable
private fun ScheduleScreen(
    modifier: Modifier = Modifier,
    onDateTimeChanged: (Int, Int, Int, Int, Int) -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    // Initial values from store
    val (initialHour, initialMinute) = remember {
        ScheduleSelectionStore.getCurrentTime(context)
    }
    val (initialYear, initialMonth, initialDay) = remember {
        ScheduleSelectionStore.getCurrentDate(context)
    }

    var year by remember { mutableStateOf(initialYear) }
    var month by remember { mutableStateOf(initialMonth) } // 1..12
    var day by remember { mutableStateOf(initialDay) }
    var hour by remember { mutableStateOf(initialHour) }
    var minute by remember { mutableStateOf(initialMinute) }

    val dateFormatter = remember {
        DateTimeFormatter.ofPattern("EEE d MMM yyyy", Locale.getDefault())
    }

    val selectedDateText = remember(year, month, day) {
        LocalDate.of(year, month, day).format(dateFormatter)
    }

    Column(
        modifier = modifier.padding(16.dp)
    ) {
        Text(text = "Alarm schedule", modifier = Modifier.padding(bottom = 8.dp))

        // Show selected date
        Text(
            text = "Date: $selectedDateText",
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Show selected time
        Text(
            text = String.format(Locale.getDefault(), "Time: %02d:%02d", hour, minute),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Change date button
        Button(
            onClick = {
                DatePickerDialog(
                    context,
                    { _, y, m, d ->
                        // DatePickerDialog gives month 0..11, store as 1..12
                        year = y
                        month = m + 1
                        day = d
                        onDateTimeChanged(year, month, day, hour, minute)
                    },
                    year,
                    month - 1, // convert back to 0-based
                    day
                ).show()
            },
            modifier = Modifier.padding(bottom = 8.dp)
        ) {
            Text("Change date")
        }

        // Change time button
        Button(
            onClick = {
                TimePickerDialog(
                    context,
                    { _, h, min ->
                        hour = h
                        minute = min
                        onDateTimeChanged(year, month, day, hour, minute)
                    },
                    hour,
                    minute,
                    true
                ).show()
            }
        ) {
            Text("Change time")
        }

        // Back button
        Button(
            onClick = onBack,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back")
        }
    }
}