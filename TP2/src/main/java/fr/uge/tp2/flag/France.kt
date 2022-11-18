package fr.uge.tp2.flag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun FranceFlagPreview() {
    France.FranceFlag()
}

object France {

    private val blue = Color(0xFF0055A4)
    private val red = Color(0xFFEF4135)

    @Composable
    fun FranceFlag() {
        Box(Modifier.fillMaxWidth().aspectRatio(3f/2f).clip(RoundedCornerShape(5.dp))) {
            Row() {
                Box(Modifier.fillMaxHeight().weight(1f/3f, true).background(color = blue))
                Box(Modifier.fillMaxHeight().weight(1f/3f, true).background(color = Color.White))
                Box(Modifier.fillMaxHeight().weight(1f/3f, true).background(color = red))
            }
        }
    }

}