package fr.uge.tp2.component

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import fr.uge.tp2.Countries
import fr.uge.tp2.Country
import fr.uge.tp2.countries

@Preview(showBackground = true)
@Composable
fun FlagDisplayerPreview() {
    Flags.FlagDisplayer(Countries.FRANCE)
}

@Preview(showBackground = true)
@Composable
fun FlagListPreview() {
    Flags.FlagsDisplayer(countries)
}

object Flags {

    @Composable
    fun FlagDisplayer(country: Country, onClick: (Country) -> Unit = {}) {
        Column(
            Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { onClick(country) }
                }) {
            country.flag()
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = country.name),
                textAlign = TextAlign.Center
            )
        }
    }

    @Composable
    fun FlagsDisplayer(countries: List<Country>, onClick: (Country) -> Unit = {}) {
        LazyColumn {
            items(countries.size) { index ->
                FlagDisplayer(countries[index], onClick)
            }
        }
    }

}