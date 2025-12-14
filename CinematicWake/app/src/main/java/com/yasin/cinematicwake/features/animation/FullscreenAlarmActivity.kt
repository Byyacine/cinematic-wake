package com.yasin.cinematicwake.features.animation

import android.net.Uri
import com.yasin.cinematicwake.features.animation.AnimationSelectionStore
import com.yasin.cinematicwake.features.animation.AnimationChoice
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.AndroidView
import com.yasin.cinematicwake.R
import com.yasin.cinematicwake.ui.theme.CinematicWakeTheme
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class FullscreenAlarmActivity : ComponentActivity() {

    private var player: ExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        configureLockScreenBehaviour()
        initPlayer()
        enableEdgeToEdge()

        setContent {
            CinematicWakeTheme {
                FullscreenVideo(
                    player = player,
                    onSingleTap = { togglePlayPause() },
                    onDoubleTap = { stopAndFinish() }
                )
            }
        }
    }

    private fun togglePlayPause() {
        val p = player ?: return
        if (p.isPlaying) {
            p.pause()
        } else {
            p.play()
        }
    }

    private fun stopAndFinish() {
        player?.pause()
        player?.seekTo(0)
        finish()
    }

    private fun configureLockScreenBehaviour() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            // Show over lockscreen and wake the screen
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }

        // Force a solid black window background
        window.setBackgroundDrawableResource(android.R.color.black)
    }

//    private fun initPlayer() {
//        val exo = ExoPlayer.Builder(this).build()
//
//        // Option 1: stable android.resource:// URI
//        val uri = Uri.parse("android.resource://$packageName/${R.raw.animation_4}")
//
//        exo.setMediaItem(MediaItem.fromUri(uri))
//        exo.repeatMode = ExoPlayer.REPEAT_MODE_ALL
//        exo.playWhenReady = true
//        exo.prepare()
//
//        player = exo
//    }

    private fun initPlayer() {
        val exo = ExoPlayer.Builder(this).build()

        val choice = AnimationSelectionStore.getCurrent(this)
        val uri = Uri.parse("android.resource://$packageName/${choice.rawResId}")

        exo.setMediaItem(MediaItem.fromUri(uri))
        exo.repeatMode = ExoPlayer.REPEAT_MODE_ALL
        exo.playWhenReady = true
        exo.prepare()

        player = exo
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.release()
        player = null
    }
}

@Composable
private fun FullscreenVideo(
    player: ExoPlayer?,
    onSingleTap: () -> Unit,
    onDoubleTap: () -> Unit
) {
    if (player == null) {
        // Safety: if player not ready, still render pure black
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black)
        )
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { onSingleTap() },
                    onDoubleTap = { onDoubleTap() }
                )
            },
        contentAlignment = Alignment.Center
    ) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                PlayerView(context).apply {
                    useController = false
                    this.player = player
                    // Keep any gap before first frame black
                    setBackgroundColor(android.graphics.Color.BLACK)
                }
            },
            update = { view ->
                view.player = player
            }
        )
    }
}