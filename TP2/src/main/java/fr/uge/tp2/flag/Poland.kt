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
fun PolandFlagPreview() {
    Poland.PolandFlag()
}

object Poland {

    private val red = Color(0xFFCB2E3F)

    @Composable
    fun PolandFlag() {
        Box(Modifier.fillMaxWidth().aspectRatio(8f/5f).clip(RoundedCornerShape(5.dp))) {
            Column() {
                Box(Modifier.fillMaxWidth().weight(1f/2f, true).background(color = Color.White))
                Box(Modifier.fillMaxWidth().weight(1f/2f, true).background(color = red))
            }
        }
    }

}