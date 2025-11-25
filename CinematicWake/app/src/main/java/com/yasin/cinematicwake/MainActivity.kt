package com.yasin.cinematicwake

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.yasin.cinematicwake.ui.theme.CinematicWakeTheme

import com.yasin.cinematicwake.features.schedule.AlarmScheduler
import com.yasin.cinematicwake.features.schedule.InMemoryScheduleRepository

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1) Schedule alarms once at app start
        val repository = InMemoryScheduleRepository()
        val scheduler = AlarmScheduler(this, repository)
        scheduler.scheduleAll()

        // 2) Existing UI
        enableEdgeToEdge()
        setContent {
            CinematicWakeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CinematicWakeTheme {
        Greeting("Android")
    }
}
