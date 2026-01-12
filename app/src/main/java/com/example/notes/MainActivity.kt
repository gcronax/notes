package com.example.notes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.notes.ui.theme.NotesTheme



class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NotesTheme {
                val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
                val value by rememberInfiniteTransition().animateFloat(
                    initialValue = 25f,
                    targetValue = -25f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 600,
                            easing = LinearEasing
                        ),
                        repeatMode = RepeatMode.Reverse
                    )
                )
                val valueColor by rememberInfiniteTransition().animateFloat(
                    initialValue = 0f,
                    targetValue = 360f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(
                            durationMillis = 1000,
                            easing = LinearEasing
                        )
                    )
                )
                val infiniteTransition = rememberInfiniteTransition()

                // Creates a child animation of float type as a part of the [InfiniteTransition].
                val scale by
                infiniteTransition.animateFloat(
                    initialValue = 3f,
                    targetValue = 6f,
                    animationSpec =
                        infiniteRepeatable(
                            // Infinitely repeating a 1000ms tween animation using default easing curve.
                            animation = tween(1000),
                            // After each iteration of the animation (i.e. every 1000ms), the animation
                            // will
                            // start again from the [initialValue] defined above.
                            // This is the default [RepeatMode]. See [RepeatMode.Reverse] below for an
                            // alternative.
                            repeatMode = RepeatMode.Restart,
                        ),
                )

                // Creates a Color animation as a part of the [InfiniteTransition].
                val color by
                infiniteTransition.animateColor(
                    initialValue = Color.Red,
                    targetValue = Color(0xff800000), // Dark Red
                    animationSpec =
                        infiniteRepeatable(
                            // Linearly interpolate between initialValue and targetValue every 1000ms.
                            animation = tween(1000, easing = LinearEasing),
                            // Once [TargetValue] is reached, starts the next iteration in reverse (i.e.
                            // from
                            // TargetValue to InitialValue). Then again from InitialValue to
                            // TargetValue. This
                            // [RepeatMode] ensures that the animation value is *always continuous*.
                            repeatMode = RepeatMode.Reverse,
                        ),
                )
                val colors = listOf(
                    Color(0xFF405DE6),
                    Color(0xFFC13584),
                    Color(0xFFFD1D1D),
                    Color(0xFFFFDC80)
                )
                var gradientBrush by remember {
                    mutableStateOf(
                        Brush.horizontalGradient(
                            colors = colors,
                            startX = -10.0f,
                            endX = 400.0f,
                            tileMode = TileMode.Repeated
                        )
                    )
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize().nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        LargeTopAppBar(
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = Color(color = 0xFFFDBF08),
                                titleContentColor = Color(color = 0xFFFDEBB8),
                                scrolledContainerColor= Color(0xFFFD9F01),
                            ),
                            title = {
                                Text(
                                    "KeepNotes",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            navigationIcon = {
                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                        contentDescription = "Localized description",
                                        tint = Color(color = 0xFFFDEBB8)

                                    )
                                }
                            },
                            actions = {

                                Icon(
                                    imageVector = Icons.Default.Notifications,
                                    contentDescription = null,
                                modifier = Modifier
                                    .graphicsLayer(
                                        transformOrigin = TransformOrigin(
                                            pivotFractionX = 0.5f,
                                            pivotFractionY = 0.0f,
                                        ),
                                        rotationZ = value
                                    ),
                                    tint = color

                                )
                                IconButton(onClick = { /* do something */ }) {
                                    Icon(
                                        imageVector = Icons.Filled.Menu,
                                        contentDescription = "Localized description",
                                        tint = Color(color = 0xFFFDEBB8)
                                    )
                                }
                            },
                            scrollBehavior = scrollBehavior
                        )},

                    ) { innerPadding ->
                    ScrollContent(Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun ScrollContent( modifier: Modifier = Modifier) {
    var colorPer by rememberSaveable {
        mutableStateOf(false)
    }
    var realColor = if (colorPer) Color.Red else Color.Blue
    var colorPer2 by rememberSaveable {
        mutableStateOf(false)
    }
    val sizeChange by animateDpAsState(
        targetValue = if (isSelected) 10.dp else 12.dp
    )
    val listaCosas = listOf("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.")
    LazyColumn (modifier.fillMaxSize()){
        items (listaCosas.size){ item->
            Card(Modifier.padding(12.dp).clickable(
                onClick = {


                }
            )){
                Text(listaCosas[item])
            }
        }
    }
}


