package fr.uge.tp4

import android.graphics.BitmapFactory
import android.graphics.RectF
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.scale
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import java.lang.Integer.min

@Preview(showBackground = true)
@Composable
fun TownDiplayerPreview() = Box(modifier = Modifier.fillMaxSize()) {
    Components.TownDisplayer(
        towns = setOf(Town("Paris", 48.8566f, 2.3522f, "75000")),
    )
}

@Preview(showBackground = true)
@Composable
fun TownManagerPreview() = Box(modifier = Modifier.fillMaxSize()) {
    Components.TownManager(
        towns = listOf(Town("Paris", 48.8566f, 2.3522f, "75000")),
    )
}


object Components {

    val MAP_BOUNDS = RectF(
        -5.6126f /* min longitude */,
        51.8073f /* max latitude */,
        8.3117f /* max longitude */,
        41.3509f /* min latitude */
    )

    @OptIn(ExperimentalTextApi::class)
    @Composable
    fun TownDisplayer(
        towns: Set<Town>,
        journey: List<Town> = emptyList(),
        journeyLength: Double = 0.0,
    ) {
        val textMeasure = rememberTextMeasurer()
        val context = LocalContext.current
        val displayMetrics = context.resources.displayMetrics
        val scale = min(displayMetrics.widthPixels, displayMetrics.heightPixels)
        val map = remember {
            BitmapFactory.decodeResource(
                context.resources,
                R.drawable.france_map
            ).scale(scale, scale).asImageBitmap()
        }

        Canvas(
            Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
        ) {
            drawImage(map)
            drawText(
                textMeasure.measure(
                    AnnotatedString("Journey length: ${journeyLength.toInt()} km")
                ),
                color = Color.Black,
                topLeft = Offset(20f, size.height - 70f)
            )

            towns.forEach {
                val x = (it.longitude - MAP_BOUNDS.left) / MAP_BOUNDS.width() * scale
                val y = scale - ((it.latitude - MAP_BOUNDS.bottom) / -MAP_BOUNDS.height() * scale)
                drawCircle(
                    color = Color.Red,
                    radius = 5f,
                    center = Offset(x, y)
                )
                val text = textMeasure.measure(AnnotatedString(it.name))
                drawText(text, topLeft = Offset(x, y))
            }

            if (journey.isNotEmpty()) {
                val path = journey.map {
                    val x = (it.longitude - MAP_BOUNDS.left) / MAP_BOUNDS.width() * scale
                    val y =
                        scale - ((it.latitude - MAP_BOUNDS.bottom) / -MAP_BOUNDS.height() * scale)
                    Offset(x, y)
                }
                path.zipWithNext().forEach { (current, next) -> drawPath(current, next) }
                drawPath(path.first(), path.last())
            }
        }

    }

    private fun DrawScope.drawPath(current: Offset, next: Offset) {
        drawLine(
            color = Color.Blue,
            strokeWidth = 5f,
            start = current,
            end = next,
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
    }

    @Composable
    fun TownManager(towns: List<Town>) {
        var townNumber by remember { mutableStateOf(0f) }
        var temperatureDecreaseRatio by remember { mutableStateOf(0.5) }
        val townList = remember { mutableStateListOf<Town>() }
        val journey = remember { mutableStateListOf<Town>() }
        var journeyLength by remember { mutableStateOf(0.0) }

        LaunchedEffect(townNumber, temperatureDecreaseRatio) {
            journey.clear()
            journeyLength = 0.0
            while (townNumber.toInt() != townList.size) {
                if (townNumber.toInt() > townList.size) {
                    townList.addRandomTown(towns)
                } else {
                    townList.removeLast()
                }
            }

            if (townList.isNotEmpty()) {
                computeWithSimulatedAnnealing(
                    townList,
                    1_000_000,
                    SimulatedAnnealingParams(1.0, temperatureDecreaseRatio)
                ).flowOn(Dispatchers.Default)
                    .conflate()
                    .onEach { delay(100) }
                    .collect {
                        journey.clear()
                        journey.addAll(it)
                        journeyLength = journey.pathLength
                    }
            }
        }

        Column(Modifier.fillMaxSize()) {
            Text(text = "Number of towns: ${townNumber.toInt()}")
            Slider(
                value = townNumber,
                onValueChange = { townNumber = it },
                valueRange = 0f..25f,
                steps = 24
            )
            Text(text = "Temperature decrease ratio: $temperatureDecreaseRatio")
            Slider(
                value = temperatureDecreaseRatio.toFloat(),
                onValueChange = { temperatureDecreaseRatio = it.toDouble() },
                valueRange = 0f..1f
            )

            TownDisplayer(townList.toSet(), journey, journeyLength)

            Row(Modifier.fillMaxWidth()) {
                Button(onClick = {
                    townNumber = 0f
                    temperatureDecreaseRatio = 0.5
                    townList.clear()
                    journey.clear()
                }) {
                    Text("RESET")
                }
            }
        }
    }

    @Composable
    fun ReactiveText(content: String) {
        Box(modifier = Modifier
            .height(IntrinsicSize.Min)
            .fillMaxWidth()
            /* I need this shit otherwise the text won't update *sigh* */
            .border(0.dp, Color.Transparent),
            contentAlignment = Alignment.Center
        ){
            Text(text = content)
        }
    }
}