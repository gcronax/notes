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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextOverflow
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

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    topBar = {
                        topAppbarNotes(scrollBehavior)
                        },
                    floatingActionButton = {
                        Box(){
                            circleInfinite()
                            FloatingActionButton(
                                onClick = { /* TODO */ },
                                containerColor = Color(0xFF5A4599),
                                contentColor = Color.White,
                                shape = CircleShape
                            ) {
                                Icon(Icons.Default.Create, "Create")
                            }}
                    }
                    ) { innerPadding ->
                    ScrollContent(Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun topAppbarNotes(scrollBehavior: TopAppBarScrollBehavior) {
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

            infiniteBelt()

            IconButton(onClick = { /* do something */ }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description",
                    tint = Color(color = 0xFFFDEBB8)
                )
            }
        },

        scrollBehavior = scrollBehavior
    )
}


@Composable
fun ScrollContent( modifier: Modifier = Modifier) {

    val listaCosas = listOf(
        "Lorem Ipsum is simply dummy text of the printing and",
        "typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever ",
        "since the 1500s, when an unknown printer took a galley of type and scrambled it to make a ",
        "type specimen book. It has survived not only five centuries, but also the leap into electronic ",
        "typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of ",
        "Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software ",
        "like Aldus PageMaker including versions of Lorem Ipsum.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum ",
        "Lorem Ipsum is simply dummy text of the printing and",
        "typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever ",
        "since the 1500s, when an unknown printer took a galley of type and scrambled it to make a ",
        "type specimen book. It has survived not only five centuries, but also the leap into electronic ",
        "typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of ",
        "Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software ",
        "like Aldus PageMaker including versions of Lorem Ipsum.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum ",
        "Lorem Ipsum is simply dummy text of the printing and",
        "typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever ",
        "since the 1500s, when an unknown printer took a galley of type and scrambled it to make a ",
        "type specimen book. It has survived not only five centuries, but also the leap into electronic ",
        "typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of ",
        "Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software ",
        "like Aldus PageMaker including versions of Lorem Ipsum.",
        "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum ",
    )
    LazyColumn (modifier.fillMaxSize()){
        items (listaCosas.size){ item->

            var isSelected by rememberSaveable { mutableStateOf(false) }
            val sizeChangeH by animateDpAsState(
                targetValue = if (isSelected) 10.dp else 30.dp
            )
            val sizeChangeV by animateDpAsState(
                targetValue = if (isSelected) 5.dp else 15.dp
            )
            val colorChange by animateColorAsState(
                targetValue = if (isSelected) Color(0xFFD8BFD8) else Color(0xFFFFFACD)
            )

            Card(
                modifier = Modifier
                    .padding(horizontal = sizeChangeH, vertical = sizeChangeV)
                    .fillMaxWidth()
                    .clickable { isSelected = !isSelected },
                shape = RoundedCornerShape(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .background(colorChange)
                        .padding(5.dp)
                        .fillMaxWidth()
                ) {
                    Text(listaCosas[item])
                }
            }
        }
    }
}

@Composable
fun circleInfinite(){

    val value by rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    )

    val colors = listOf(
        Color(0xFF405DE6),
        Color(0xFFC13584),
        Color(0xFFFD1D1D),
        Color(0xFFFFDC80)
    )

    var gradientBrush by remember{
        mutableStateOf(
            Brush.horizontalGradient(
                colors=colors,
                startX=-10.0f,
                endX=400.0f,
                tileMode= TileMode.Repeated
            )
        )
    }
    Box(modifier = Modifier
        .drawBehind {
            rotate(value) {
                drawCircle(
                    gradientBrush, style = Stroke(width = 20.dp.value)
                )
            }
        }
        .size(57.dp)
    )
}

@Composable
fun infiniteBelt(){
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
    val infiniteTransition = rememberInfiniteTransition()

    val color by
    infiniteTransition.animateColor(
        initialValue = Color(0xFF5E0FF1),
        targetValue = Color(0xff800000),
        animationSpec =
            infiniteRepeatable(
                animation = tween(1000, easing = LinearEasing),
                repeatMode = RepeatMode.Reverse,
            ),
    )
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
}