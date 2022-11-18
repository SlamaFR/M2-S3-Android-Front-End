package fr.uge.tp2.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import fr.uge.tp2.Country
import fr.uge.tp2.QuantitativeFact
import fr.uge.tp2.Rank

@Preview(showBackground = true)
@Composable
fun RankedValueDisplayerPreview() {
    RankedValue.RankedValueDisplayer(value = 4f, unit = "km^2", rank = Rank(4, 10))
}

object RankedValue {

    @Composable
    fun FactDisplayer(fact: QuantitativeFact) {
        RankedValueDisplayer(value = fact.value, unit = fact.unit, rank = fact.rank)
    }

    @Composable
    fun RankedValueDisplayer(value: Float, unit: String, rank: Rank) {
        Box(
            Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .border(1.dp, Color.Black, RoundedCornerShape(5.dp))
        ) {
            Box(
                Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(rank.ratio)
                    .background(color = Color(0xFF2ECC71), shape = RoundedCornerShape(5.dp))
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "$value $unit")
                Text(text = "${rank.position} / ${rank.maxPosition}")
            }
        }
    }

}