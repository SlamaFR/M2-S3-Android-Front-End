package fr.uge.tp2.component

import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.tp2.Countries
import fr.uge.tp2.Country
import fr.uge.tp2.R
import kotlinx.coroutines.delay
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

@Preview(showBackground = true)
@Composable
fun CountryDisplayerPreview() {
    CountryDisplayer.CountryDisplayer(Countries.JAPAN)
}

object CountryDisplayer {

    @Composable
    fun CountryDisplayer(country: Country) {
        Column(Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                text = stringResource(country.name),
                textAlign = TextAlign.Center,
                fontSize = 40.sp,
                fontWeight = FontWeight.Black
            )
            CountryHeaderImage(country)
            CountryFactsDisplayer(country)
            WorldMap(country)
        }
    }

    @Composable
    fun CountryHeaderImage(country: Country) {
        Box(
            Modifier
                .wrapContentSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .drawWithCache {
                        val gradient = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color(0x9A000000)),
                            startY = size.width / 3f,
                        )
                        onDrawWithContent {
                            drawContent()
                            drawRect(gradient, blendMode = BlendMode.Multiply)
                        }
                    },
                painter = painterResource(id = country.resource),
                contentDescription = "",
                contentScale = ContentScale.FillWidth
            )
            //add text on bottom left corner
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    Modifier
                        .weight(4f / 5f)
                        .fillMaxWidth()
                ) {
                    LocalClock(timeZone = country.timeZone)
                }
                Box(
                    Modifier
                        .weight(1f / 5f)
                        .wrapContentSize()
                ) { country.flag() }
            }
        }
    }

    @Composable
    fun LocalClock(timeZone: TimeZone) {
        val locale = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            LocalConfiguration.current.locales[0]
        } else {
            // noinspection deprecation
            LocalConfiguration.current.locale
        }

        val timeFormat = SimpleDateFormat("HH:mm:ss", locale)
            .apply { this.timeZone = timeZone }
        val dateFormat = DateFormat.getDateInstance(DateFormat.FULL, locale)
            .apply { this.timeZone = timeZone }

        var time by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }

        LaunchedEffect(timeZone) {
            while (true) {
                val now = System.currentTimeMillis()
                time = timeFormat.format(now)
                date = dateFormat.format(now)
                delay(1000)
            }
        }
        Column() {
            Text(
                text = time,
                fontSize = 15.sp,
                color = Color.White,
            )
            Text(
                text = date,
                fontSize = 20.sp,
                color = Color.White,
            )
        }
    }

    @Composable
    fun CountryFactsDisplayer(country: Country) {
        Box(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Column() {
                Row(
                    Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f / 4f)) {
                        Text(text = stringResource(R.string.area))
                    }
                    Box(Modifier.weight(3f / 4f)) {
                        RankedValue.FactDisplayer(fact = country.areaFact)
                    }
                }
                Row(
                    Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f / 4f)) {
                        Text(text = stringResource(R.string.population))
                    }
                    Box(Modifier.weight(3f / 4f)) {
                        RankedValue.FactDisplayer(fact = country.populationFact)
                    }
                }
                Row(
                    Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f / 4f)) {
                        Text(text = stringResource(R.string.density))
                    }
                    Box(Modifier.weight(3f / 4f)) {
                        RankedValue.FactDisplayer(fact = country.densityFact)
                    }
                }
                Row(
                    Modifier.padding(vertical = 2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(Modifier.weight(1f / 4f)) {
                        Text(text = stringResource(R.string.gdp))
                    }
                    Box(Modifier.weight(3f / 4f)) {
                        RankedValue.FactDisplayer(fact = country.gdpFact)
                    }
                }
            }
        }
    }

    @Composable
    fun WorldMap(country: Country) {
        val (latitude, longitude) = country.coordinates
        val map = createMapWithLocation(latitude.toFloat(), longitude.toFloat())

        Box(Modifier.wrapContentSize()) {
            map.value?.let {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    bitmap = it,
                    contentDescription = "",
                    contentScale = ContentScale.FillWidth
                )
            }
        }
    }

    @Composable
    fun createMapWithLocation(latitude: Float, longitude: Float): State<ImageBitmap?> {
        val context = LocalContext.current
        return produceState<ImageBitmap?>(initialValue = null, latitude, longitude) {
            val originalImageBitmap = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.equirectangular_world_map
            ).asImageBitmap()
            val mapBitmap = BitmapFactory.decodeResource(
                context.resources,
                R.drawable.equirectangular_world_map,
                BitmapFactory.Options().also { it.inMutable = true })
            val canvas = Canvas(mapBitmap)
            val radius = minOf(canvas.width, canvas.height) / 50f
            val x = (longitude + 180f) / 360f * canvas.width
            val y = (1f - (latitude + 90f) / 180f) * canvas.height
            val paint = Paint()
            paint.style = Paint.Style.FILL_AND_STROKE
            paint.color = android.graphics.Color.BLUE
            canvas.drawCircle(x, y, radius, paint)
            val mapImageBitmap = mapBitmap.asImageBitmap()
            while (true) {
                value = originalImageBitmap
                delay(1000L)
                value = mapImageBitmap
                delay(1000L)
            }
        }
    }

}