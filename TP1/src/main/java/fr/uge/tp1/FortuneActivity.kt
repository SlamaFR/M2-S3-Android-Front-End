package fr.uge.tp1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONTokener

fun String.unescapeJSONString(): String {
    return JSONTokener(this).nextValue().toString()
}

class FortuneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fortune)

        val textView = findViewById<TextView>(R.id.fortune)
        val button = findViewById<Button>(R.id.quit)

        val queue = Volley.newRequestQueue(this)
        val url = "https://fortuneapi.herokuapp.com/"

        val stringRequest = StringRequest(Request.Method.GET, url, { response ->
            textView.text = response.unescapeJSONString()
        }, { error ->
            textView.text = getString(R.string.error)
        })
        queue.add(stringRequest)

        button.setOnClickListener {
            finish()
        }
    }
}