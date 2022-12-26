package fr.uge.tp4

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import fr.uge.tp4.ui.theme.FrontEndTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setContent {
            FrontEndTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    App(context)
                }
            }
        }
    }
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun App(context: Context) {
    Column(Modifier.fillMaxSize()) {
        val textMeasure = rememberTextMeasurer()
        var loading by remember { mutableStateOf(true) }
        var loadedTowns by rememberSaveable { mutableStateOf(0) }
        val townList = remember { mutableListOf<Town>() }

        LaunchedEffect(Unit) {
            loading = true
            Town.parseFileAsync(context, "laposte_hexasmal.csv")
                .flowOn(Dispatchers.IO)
                .conflate()
                .onEach { delay(10) }
                .collect() {
                    when (it) {
                        is TownListProgress -> {
                            loadedTowns = it.townNumber
                        }
                        is TownListResult -> {
                            townList.addAll(it.townList)
                            loading = false
                        }
                    }
                }
        }

        if (loading) {
            LoadingScreen(loadedTowns = loadedTowns)
        } else {
            Components.TownManager(townList)
        }
    }
}

@Composable
fun LoadingScreen(loadedTowns: Int) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Loading...",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Components.ReactiveText("$loadedTowns towns loaded")
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FrontEndTheme {
        Greeting("Android")
    }
}