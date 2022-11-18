package fr.uge.tp2.flag

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp

@Preview(showBackground = true)
@Composable
fun SouthAfricaFlagPreview() {
    SouthAfrica.SouthAfricaFlag()
}

object SouthAfrica {

    private val green = Color(0xFF007A4D)
    private val gold = Color(0xFFFFB612)
    private val red = Color(0xFFDE3831)
    private val blue = Color(0xFF002395)

    @Composable
    fun SouthAfricaFlag() {
        val offsetTriangleShape: (xOffsetRatio: Float) -> GenericShape = {
            GenericShape { size, _ ->
                moveTo(0f - it * size.width * 2, 0f)
                lineTo(size.width - it * size.width * 2, size.height / 2)
                lineTo(0f - it * size.width * 2, size.height)
                close()
            }
        }
        val triangleShape = offsetTriangleShape(0f)

        Box(
            Modifier
                .fillMaxWidth()
                .aspectRatio(3f / 2f)
                .clip(RoundedCornerShape(5.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Column(Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f / 3f, true)
                        .background(color = red)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f / 3f, true)
                        .background(color = Color.White)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f / 3f, true)
                        .background(color = blue)
                )
            }
            Row(Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(10f / 50f, true)
                        .background(color = Color.White)
                )
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(25f / 50f, true)
                        .background(color = Color.White, shape = triangleShape)
                )
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(15f / 50f, true)
                        .background(color = Color.Transparent)
                )
            }
            Row(Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(6f / 50f, true)
                        .background(color = green)
                )
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(25f / 50f, true)
                        .background(color = green, shape = triangleShape)
                )
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(19f / 50f, true)
                        .background(color = Color.Transparent)
                )
            }
            Column(Modifier.fillMaxSize()) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(2f / 5f, true)
                        .background(color = Color.Transparent)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f / 5f, true)
                        .background(color = green)
                )
                Box(
                    Modifier
                        .fillMaxWidth()
                        .weight(2f / 5f, true)
                        .background(color = Color.Transparent)
                )
            }
            Row(
                Modifier
                    .fillMaxSize()
            ) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f / 2f, true)
                        .background(color = gold, shape = offsetTriangleShape(3f / 25f))
                )
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f / 2f, true)
                        .background(color = Color.Transparent)
                )
            }
            Row(
                Modifier
                    .fillMaxSize()
            ) {
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f / 2f, true)
                        .background(color = Color.Black, shape = offsetTriangleShape(1f / 5f))
                )
                Box(
                    Modifier
                        .fillMaxHeight()
                        .weight(1f / 2f, true)
                        .background(color = Color.Transparent)
                )
            }
        }
    }

}