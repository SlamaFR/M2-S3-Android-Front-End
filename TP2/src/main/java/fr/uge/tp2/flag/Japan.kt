package fr.uge.tp2.flag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun JapanFlagPreview() {
    Japan.JapanFlag()
}

object Japan {

    private val red = Color(0xFFBC002D)

    @Composable
    fun JapanFlag() {
        Box(
            Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
                .clip(RoundedCornerShape(5.dp)),
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier
                    .fillMaxSize()
                    .background(color = Color.White))
            Box(
                Modifier
                    .fillMaxHeight(3f / 5f)
                    .aspectRatio(1f)
                    .background(color = red, shape = CircleShape)
            )
        }
    }

}