package com.yasin.cinematicwake.features.animation

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.yasin.cinematicwake.ui.theme.CinematicWakeTheme

class AnimationStoreActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CinematicWakeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AnimationStoreScreen(
                        modifier = Modifier.padding(innerPadding),
                        current = AnimationSelectionStore.getCurrent(this),
                        onSelect = { choice ->
                            AnimationSelectionStore.setCurrent(this, choice)
                        },
                        onBack = { finish() }   // ⬅ back to home
                    )
                }
            }
        }
    }
}

@Composable
private fun AnimationStoreScreen(
    modifier: Modifier = Modifier,
    current: AnimationChoice,
    onSelect: (AnimationChoice) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = modifier.padding(16.dp)
    ) {
        // Back button at the top
        Button(
            onClick = onBack,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Text("Back")
        }

        Text(text = "Select your cinematic wake-up scene")

        Text(
            text = "Current: ${current.label}",
            modifier = Modifier.padding(top = 8.dp, bottom = 16.dp)
        )

        AnimationChoice.values().forEach { choice ->
            Button(
                onClick = { onSelect(choice) },
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(choice.label)
            }
        }
    }
}