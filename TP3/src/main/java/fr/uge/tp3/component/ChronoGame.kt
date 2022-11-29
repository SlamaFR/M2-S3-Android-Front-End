package fr.uge.tp3.component

import android.os.SystemClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun ChronoGamePreview() {
    ChronoGame(10000) {}
}

@Composable
fun ChronoGame(expectedDuration: Long, onVerdict: (Long) -> Unit) {
    var startTime: Long by rememberSaveable { mutableStateOf(0L) }
    var endTime: Long? by rememberSaveable { mutableStateOf(0L) }
    var hideTimer: Boolean by rememberSaveable { mutableStateOf(false) }
    var finished: Boolean by rememberSaveable { mutableStateOf(false) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
        ) {
            Chronometer(startTime = startTime, endTime = endTime) {
                if (endTime == null && it >= expectedDuration shr 1) {
                    hideTimer = true
                }
            }
            if (hideTimer && endTime == null) {
                Box(
                    Modifier
                        .fillMaxSize()
                        .background(color = Color.LightGray)
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                startTime = SystemClock.elapsedRealtime()
                endTime = null
            }, enabled = endTime != null) {
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = "START",
                    style = MaterialTheme.typography.button,
                    fontSize = 24.sp
                )
            }
            Button(onClick = {
                endTime = SystemClock.elapsedRealtime()
                hideTimer = false
                finished = true
                onVerdict(endTime!! - startTime)
            }, enabled = endTime == null && !finished) {
                Text(
                    modifier = Modifier.padding(24.dp),
                    text = "STOP",
                    style = MaterialTheme.typography.button,
                    fontSize = 24.sp
                )
            }
        }
    }
}