package com.yasin.cinematicwake.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yasin.cinematicwake.R
import com.yasin.cinematicwake.features.animation.AnimationChoice
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.text.font.FontWeight

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

    val dateFormatter = remember { DateTimeFormatter.ofPattern("EEE d MMM") } // Sat 14 Dec
    val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }     // 07:30
    val dayOfYear = now.dayOfYear
    val yearLength = now.toLocalDate().lengthOfYear()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.background_1),
            contentDescription = "Cute alarm clock background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Animated hands aligned to the centre of the screen
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            AnimatedClockHands(
                modifier = Modifier.offset(
                    x = 8.dp,   // move a bit to the right
                    y = 12.dp   // move a bit down
                ),
                clockRadius = 160.dp
            )
        }

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

        // Bottom text (tagline + date/time + J-XXX)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 70.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Fix The Door Of Your Imagination",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "${now.format(dateFormatter)}  •  J-$dayOfYear",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun AnimatedClockHands(
    modifier: Modifier = Modifier,
    clockRadius: Dp = 80.dp  // tweak radius so it matches the face size
) {
    var now by remember { mutableStateOf(LocalTime.now()) }

    LaunchedEffect(Unit) {
        while (true) {
            now = LocalTime.now()
            delay(1_000L)
        }
    }

    val seconds = now.second.toFloat()
    val minutes = now.minute + seconds / 60f
    val hours = (now.hour % 12) + minutes / 60f

    val hourAngle = hours * 30f       // 360 / 12
    val minuteAngle = minutes * 6f    // 360 / 60
    val secondAngle = seconds * 6f    // 360 / 60

    Canvas(
        modifier = modifier.size(clockRadius * 2)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radius = size.minDimension / 2f

        // Hour hand
        val hourLength = radius * 0.55f
        val hourEnd = Offset(
            x = center.x + hourLength * cos(Math.toRadians(hourAngle - 90.0)).toFloat(),
            y = center.y + hourLength * sin(Math.toRadians(hourAngle - 90.0)).toFloat()
        )
        drawLine(
            color = Color(0xFF4A4036),
            start = center,
            end = hourEnd,
            strokeWidth = radius * 0.06f,
            cap = StrokeCap.Round
        )

        // Minute hand
        val minuteLength = radius * 0.7f
        val minuteEnd = Offset(
            x = center.x + minuteLength * cos(Math.toRadians(minuteAngle - 90.0)).toFloat(),
            y = center.y + minuteLength * sin(Math.toRadians(minuteAngle - 90.0)).toFloat()
        )
        drawLine(
            color = Color(0xFF4A4036),
            start = center,
            end = minuteEnd,
            strokeWidth = radius * 0.045f,
            cap = StrokeCap.Round
        )

        // Seconds hand (thin, a bit longer)
        val secondLength = radius * 0.75f
        val secondEnd = Offset(
            x = center.x + secondLength * cos(Math.toRadians(secondAngle - 90.0)).toFloat(),
            y = center.y + secondLength * sin(Math.toRadians(secondAngle - 90.0)).toFloat()
        )
        drawLine(
            color = Color(0xFFF4C56B),       // accent colour
            start = center,
            end = secondEnd,
            strokeWidth = radius * 0.025f,
            cap = StrokeCap.Round
        )

        // Centre dot
        drawCircle(
            color = Color(0xFFF4C56B),
            radius = radius * 0.08f,
            center = center
        )
    }
}