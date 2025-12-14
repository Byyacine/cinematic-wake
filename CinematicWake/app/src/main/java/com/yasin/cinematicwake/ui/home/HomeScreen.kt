package com.yasin.cinematicwake.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.yasin.cinematicwake.R
import com.yasin.cinematicwake.features.animation.AnimationChoice
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    currentAnimation: AnimationChoice,
    onOpenAnimationStore: () -> Unit,
    onOpenSchedule: () -> Unit
) {
    var now by remember { mutableStateOf(LocalDateTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            now = LocalDateTime.now()
            delay(1_000L)
        }
    }

    val dateFormatter = remember {
        DateTimeFormatter.ofPattern("EEE d MMM") // Sat 14 Dec
    }
    val timeFormatter = remember {
        DateTimeFormatter.ofPattern("HH:mm")     // 07:30
    }

    val dayOfYear = now.dayOfYear
    val yearLength = now.toLocalDate().lengthOfYear()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        // Background Atlas image (put atlas.jpeg in res/drawable as atlas)
        Image(
            painter = painterResource(id = R.drawable.atlas),
            contentDescription = "Atlas background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Dark overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(alpha = 0.45f))
        )

        // Top buttons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = onOpenAnimationStore) {
                Text(text = "Animations")
            }
            Button(onClick = onOpenSchedule) {
                Text(text = "Schedule")
            }
        }

        // Bottom text (tagline + date/time + day-of-year)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 32.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Fix The Door Of Your Imagination",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "${now.format(dateFormatter)}  •  ${now.format(timeFormatter)}",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black
            )

            Text(
                text = "Day of the year : $dayOfYear / $yearLength",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.Black.copy(alpha = 1f)
            )

            // (optional) current animation info:
//            Text(
//                text = "Current scene: ${currentAnimation.label}",
//                style = MaterialTheme.typography.bodySmall,
//                color = Color.White.copy(alpha = 0.7f)
//            )
        }
    }
}