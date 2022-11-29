package fr.uge.tp3.component

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import kotlin.math.abs

@Preview(showBackground = false)
@Composable
fun PositivePreview() {
    DeltaTimeDisplayer(1234567)
}

@Preview(showBackground = false)
@Composable
fun NegativePreview() {
    DeltaTimeDisplayer(-34567)
}

@Composable
fun DeltaTimeDisplayer(deltaTime: Long) {
    val absDelta = abs(deltaTime)
    val minutes = absDelta / 60000
    val seconds = (absDelta % 60000) / 1000
    val centiseconds = (absDelta % 1000) / 10
    if (deltaTime < 0) {
        Text(
            text = "-%d:%02d.%02d".format(minutes, seconds, centiseconds),
            fontSize = 64.sp
        )
    } else {
        Text(
            text = "%d:%02d.%02d".format(minutes, seconds, centiseconds),
            fontSize = 64.sp
        )
    }
}
