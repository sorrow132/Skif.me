package yuresko.skifme.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import yuresko.skifme.R
import yuresko.skifme.mainmenu.ActivityMenu

class ActivityThree : AppCompatActivity() {
    private lateinit var buttonNext: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three)
        buttonNext = findViewById(R.id.act3Button)

        buttonNext.setOnClickListener {
            val intent = Intent(this, ActivityMenu::class.java)
            startActivity(intent)
        }

        supportActionBar?.title = "Авторизация"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }
}