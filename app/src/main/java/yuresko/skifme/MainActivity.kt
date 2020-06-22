package yuresko.skifme

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import yuresko.skifme.registration.ActivityTwo

class MainActivity : AppCompatActivity() {
    private lateinit var textViewFirst: TextView
    private lateinit var textViewSecond: TextView
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textViewFirst = findViewById(R.id.yourNumberEditText)
        textViewSecond = findViewById(R.id.yourNumberSeven)
        imageView = findViewById(R.id.imageView)

        supportActionBar?.hide()

        textViewFirst.setOnClickListener {
            val intent = Intent(this, ActivityTwo::class.java)
            startActivity(intent)
        }

        imageView.setOnClickListener {
            val intent = Intent(this, ActivityTwo::class.java)
            startActivity(intent)
        }

        textViewSecond.setOnClickListener {
            val intent = Intent(this, ActivityTwo::class.java)
            startActivity(intent)
        }
    }
}