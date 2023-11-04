package com.bruno13palhano.palhanofm.ui.screens

import android.content.ComponentName
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import com.bruno13palhano.palhanofm.R
import com.bruno13palhano.palhanofm.mediaplayer.PlaybackService
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

private lateinit var controllerFuture: ListenableFuture<MediaController>
private val controller: MediaController?
    get() = if (controllerFuture.isDone) controllerFuture.get() else null

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isVisible by remember { mutableStateOf(false) }

    DisposableEffect(key1 = lifecycleOwner, key2 = context) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                initializeController(context) {
                    isVisible = true
                }
            }
            else if (event == Lifecycle.Event.ON_STOP){
                releaseController()
                isVisible = false
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    if (isVisible) {
        PlayerContent(context = context)
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
private fun PlayerContent(
    context: Context
) {
    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {  }) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.hello_label)
                )
                Icon(
                    imageVector = Icons.Filled.Whatsapp,
                    contentDescription = stringResource(id = R.string.request_song_description)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            var title by remember { mutableStateOf("") }

            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .sizeIn(maxHeight = 304.dp),
                factory = {
                    PlayerView(context).apply {
                        showController()
                        useController = true
                        player = controller
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        defaultArtwork = context.getDrawable(R.drawable.palhano_fm_logo)
                    }
                },
                update = { playerView ->
                    playerView.player?.addListener(object : Player.Listener {
                        override fun onEvents(player: Player, events: Player.Events) {
                            title = player.mediaMetadata.title.toString()

                            if (events.contains(Player.EVENT_IS_PLAYING_CHANGED)) {
                                if (!player.isPlaying) {
                                    player.seekToDefaultPosition()
                                }
                            }
                        }
                    })
                }
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

private fun releaseController() {
    MediaController.releaseFuture(controllerFuture)
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
private fun initializeController(context: Context, launched: () -> Unit) {
    controllerFuture = MediaController.Builder(
        context,
        SessionToken(context, ComponentName(context, PlaybackService::class.java))
    )
        .buildAsync()

    controllerFuture.addListener({
        launched()
    }, MoreExecutors.directExecutor())
}