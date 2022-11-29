package fr.uge.tp3.component

import android.media.MediaPlayer
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.uge.tp3.R
import kotlin.math.absoluteValue

@Preview(showBackground = true)
@Composable
fun GameManagerPreview() {
    GameManager()
}

enum class GameState {
    SET, PLAY, FINISH
}

@Composable
fun GameManager() {
    var gameState by rememberSaveable { mutableStateOf(GameState.SET) }
    var targetTime by rememberSaveable { mutableStateOf(10f) }
    var delta by rememberSaveable { mutableStateOf(0f) }

    val errorPercent = {
        if (delta == 0f) 0f
        else if (targetTime == 0f) Float.NaN
        else (delta / (targetTime * 1000)).absoluteValue * 100
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "ChronoGame", style = MaterialTheme.typography.h2)
            Spacer(Modifier.height(16.dp))
            when (gameState) {
                GameState.SET -> {
                    Text(text = "Set target time to guess")
                    Slider(
                        value = targetTime,
                        onValueChange = { targetTime = it },
                        valueRange = 1f..30f,
                        steps = 28
                    )
                    Text(text = "Target time: ${targetTime.toLong()}s")
                    Button(onClick = {
                        gameState = GameState.PLAY
                        delta = 0f
                    }) {
                        Text(text = "CONFIRM")
                    }
                }
                GameState.PLAY -> {
                    ChronoGame(expectedDuration = targetTime.toLong() * 1000) {
                        delta = it - (targetTime * 1000)
                        gameState = GameState.FINISH
                    }
                }
                GameState.FINISH -> {
                    val percent = errorPercent()
                    Text(text = "You are ${if (delta > 0) "late" else "early"} by ${delta.absoluteValue / 1000}s")
                    Text(text = "Error: $percent%")
                    Button(onClick = {
                        gameState = GameState.SET
                    }) {
                        Text(text = "RESTART")
                    }
                    MediaPlayer.create(LocalContext.current, when (percent) {
                        in 0f..1f -> R.raw.victory
                        in 1f..5f -> R.raw.ok
                        in 5f..10f -> R.raw.bad
                        else -> R.raw.very_bad
                    }).start()
                }
            }
        }
    }
}