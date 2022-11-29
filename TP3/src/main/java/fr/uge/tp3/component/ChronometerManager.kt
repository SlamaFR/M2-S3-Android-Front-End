package fr.uge.tp3.component

import android.os.SystemClock
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun ChronometerManagerPreview() {
    ChronometerManager()
}

@Composable
fun ChronometerManager() {
    var startTime by rememberSaveable { mutableStateOf(0L) }
    var endTime by rememberSaveable { mutableStateOf(0L) }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Chronometer(startTime = startTime, endTime = endTime)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                startTime = SystemClock.elapsedRealtime()
                endTime = -1
            }) {
                Text(text = "START", style = MaterialTheme.typography.button)
            }
            Button(onClick = {
                endTime = SystemClock.elapsedRealtime()
            }, enabled = endTime == -1L) {
                Text(text = "STOP", style = MaterialTheme.typography.button)
            }
        }
    }
}