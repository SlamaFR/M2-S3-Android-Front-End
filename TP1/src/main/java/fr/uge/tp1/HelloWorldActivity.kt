package fr.uge.tp1

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class HelloWorldActivity : AppCompatActivity() {

    private lateinit var cities: List<City>

    private var counter = 0
    private lateinit var targetCity: City

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hello_world)
        val targetCityText = findViewById<TextView>(R.id.targetCity)
        val distance = findViewById<TextView>(R.id.distance)
        val gamesPlayed = findViewById<TextView>(R.id.gamesPlayed)

        cities = City.loadFromResources(this, R.raw.topcities)
        gamesPlayed.text = getString(R.string.game_played, counter)
        pickRandomCity(targetCityText)

        findViewById<Button>(R.id.quitButton).setOnClickListener {
            Toast.makeText(this, R.string.quit_message, Toast.LENGTH_SHORT).show()
            val intent = Intent(this, FortuneActivity::class.java)
            startActivity(intent)
            finish()
        }

        val worldMap = findViewById<ImageView>(R.id.worldMap)
        worldMap.setOnTouchListener { _, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    val latitude = 90 - event.y / worldMap.height * 180
                    val longitude = event.x / worldMap.width * 360 - 180

                    val distances = floatArrayOf(0f, 0f, 0f)
                    Location.distanceBetween(
                        latitude.toDouble(),
                        longitude.toDouble(),
                        targetCity.latitude.toDouble(),
                        targetCity.longitude.toDouble(),
                        distances
                    )

                    Log.i("HelloWorldActivity", "latitude: $latitude, longitude: $longitude")
                    Log.i("HelloWorldActivity", "city => latitude: ${targetCity.latitude}, longitude: ${targetCity.longitude}")

                    counter++
                    distance.text = "${distances[0] / 1000} km"
                    gamesPlayed.text = getString(R.string.game_played, counter)
                    pickRandomCity(targetCityText)
                }
                else -> { /* do nothing */ }
            }
            true
        }

        //worldMap.setOnClickListener {
        //    counter++
        //    textView = getString(R.string.counter, counter)
        //    //textView.setBackgroundColor(textColor(counter))
        //}
    }

    private fun pickRandomCity(targetCityText: TextView) {
        targetCity = cities.random(kotlin.random.Random)
        targetCityText.text = targetCity.name
    }

    private fun textColor(counter: Int): Int = when {
        counter <= 10 -> Color.BLACK
        counter <= 20 -> Color.BLUE
        else -> Color.RED
    }
}