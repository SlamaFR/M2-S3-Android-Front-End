package fr.uge.tp2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import fr.uge.tp2.component.CountryDisplayer
import fr.uge.tp2.component.Flags.FlagsDisplayer
import fr.uge.tp2.ui.theme.FrontEndTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FrontEndTheme {
                MainLayout()
            }
        }
    }
}

sealed class MainComponent

class CountryComponent(val country: Country) : MainComponent()

object FlagsComponent : MainComponent()


@Composable
fun MainLayout() {
    val scaffoldState = rememberScaffoldState()
    var mainComponent by remember { mutableStateOf<MainComponent>(FlagsComponent) }
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("Country facts") },
                navigationIcon = {
                    if (scaffoldState.drawerState.isClosed) {
                        IconButton(onClick = {
                            scope.launch { scaffoldState.drawerState.open() }
                        }) { Icon(Icons.Default.Menu, "Open") }
                    } else {
                        IconButton(onClick = {
                            scope.launch { scaffoldState.drawerState.close() }
                        }) { Icon(Icons.Default.Menu, "Close") }
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                mainComponent = CountryComponent(countries.random())
            }) {
                Icon(Icons.Default.Refresh, contentDescription = "Random country")
            }
        },
        drawerContent = {
            FlagsDisplayer(countries = countries) { country ->
                mainComponent = CountryComponent(country)
                scope.launch { scaffoldState.drawerState.close() }
            }
        }
    ) {
        Box(Modifier.fillMaxSize()) {
            when (val component = mainComponent) {
                is FlagsComponent -> FlagsDisplayer(countries) {
                    mainComponent = CountryComponent(it)
                }
                is CountryComponent -> CountryDisplayer.CountryDisplayer(component.country)
            }
        }
    }
}

@Composable
fun HelloWorld(name: String) {
    var counter by remember { mutableStateOf(0) }
    Column {
        HelloWorldMessage(name, counter)
        Map({ counter++ }, { counter += 4 })
    }
}

@Composable
fun HelloWorldMessage(name: String, counter: Int) {
    Text(text = "Hello $name! Counter: $counter")
}

@Composable
fun Map(mapClick: () -> Unit, mapDoubleClick: () -> Unit) {
    Image(
        painter = painterResource(R.drawable.equirectangular_world_map),
        contentDescription = "Map",
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = { mapDoubleClick() },
                    onTap = { mapClick() }
                )
            }
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FrontEndTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HelloWorld("Irwin")
        }
    }
}