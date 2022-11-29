package fr.uge.tp3.component

import android.os.SystemClock
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Preview(showBackground = true)
@Composable
fun ChronometerPreview() {
    val time = SystemClock.elapsedRealtime()
    Chronometer(time)
}

@Composable
fun Chronometer(startTime: Long, endTime: Long? = null, onUpdate: (Long) -> Unit = {}) {
    val delta = if (endTime == null) {
        var currentTime by rememberSaveable { mutableStateOf(SystemClock.elapsedRealtime()) }
        LaunchedEffect(Unit) {
            while (isActive) {
                currentTime = SystemClock.elapsedRealtime()
                delay(10)
            }
        }
        currentTime - startTime
    } else {
        endTime - startTime
    }
    onUpdate(delta)
    DeltaTimeDisplayer(deltaTime = delta)
}