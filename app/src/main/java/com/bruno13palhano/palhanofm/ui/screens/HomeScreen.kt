package com.bruno13palhano.palhanofm.ui.screens

import android.content.ComponentName
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.media3.common.Player
import androidx.media3.session.MediaController
import androidx.media3.session.SessionToken
import androidx.media3.ui.PlayerView
import coil.compose.rememberAsyncImagePainter
import com.bruno13palhano.palhanofm.R
import com.bruno13palhano.palhanofm.mediaplayer.PlaybackService
import com.bruno13palhano.palhanofm.ui.components.Carousel
import com.bruno13palhano.palhanofm.ui.screens.viewmodels.HomeViewModel
import com.google.common.util.concurrent.ListenableFuture
import com.google.common.util.concurrent.MoreExecutors

private lateinit var controllerFuture: ListenableFuture<MediaController>
private val controller: MediaController?
    get() = if (controllerFuture.isDone) controllerFuture.get() else null

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) { viewModel.synchronize() }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var isVisible by remember { mutableStateOf(false) }

    val sponsorsUrlImages by viewModel.sponsorsImagesUrl.collectAsStateWithLifecycle()

    DisposableEffect(key1 = lifecycleOwner) {
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

    Scaffold(
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {}) {
                Icon(
                    imageVector = Icons.Filled.Whatsapp,
                    contentDescription = stringResource(id = R.string.request_song_description)
                )
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = stringResource(id = R.string.hello_label)
                )
            }
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            if (isVisible) {
                PlayerContent(context = context)
                if (sponsorsUrlImages.isNotEmpty()) {
                    Carousel(
                        modifier = Modifier
                            .weight(1F)
                            .fillMaxWidth(),
                        pageCount = sponsorsUrlImages.size
                    ) { page ->
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 8.dp, top = 16.dp, end = 8.dp, bottom = 8.dp)
                        ) {
                            Image(
                                painter = rememberAsyncImagePainter(model = sponsorsUrlImages[page]),
                                contentDescription = stringResource(id = R.string.current_sponsor_description),
                                contentScale = ContentScale.Inside,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(5))
                            )
                        }
                    }
                }
            }
        }
    }
}

@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
private fun PlayerContent(
    context: Context
) {
    var title by remember { mutableStateOf("") }
    val arts = listOf(
        context.getDrawable(R.drawable.player_image_1),
        context.getDrawable(R.drawable.player_image_2),
        context.getDrawable(R.drawable.player_image_3)
    )
    var currentId = 0

    AndroidView(
        modifier = Modifier.fillMaxWidth().sizeIn(maxHeight = 408.dp),
        factory = {
            PlayerView(context).apply {
                showController()
                useController = true
                player = controller
                layoutParams = FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
        },
        update = { playerView ->
            playerView.player?.addListener(object : Player.Listener {
                override fun onEvents(player: Player, events: Player.Events) {
                    val newTitle = player.mediaMetadata.title.toString()
                    if(title != newTitle) {
                        val nextId = (currentId + 1) % arts.size
                        playerView.defaultArtwork = arts[nextId]
                        currentId = nextId
                    }

                    title = newTitle
                    playerView.artworkDisplayMode = PlayerView.ARTWORK_DISPLAY_MODE_FILL

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
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        text = title,
        fontWeight = FontWeight.Bold,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        style = MaterialTheme.typography.titleMedium
    )
}

private fun releaseController() = MediaController.releaseFuture(controllerFuture)

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