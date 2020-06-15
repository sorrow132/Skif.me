package yuresko.skifme

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import br.com.sapereaude.maskedEditText.MaskedEditText

class ActivityTwo : AppCompatActivity() {
    private lateinit var maskedEditText: MaskedEditText
    private lateinit var buttonNext: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
        maskedEditText = findViewById(R.id.phone_input)
        buttonNext = findViewById(R.id.imageView2)

        buttonNext.setOnClickListener {
            val intent = Intent(this, ActivityThree::class.java)
            startActivity(intent)
        }
        supportActionBar?.title = "Авторизация"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        maskedEditText.addTextChangedListener(EditTextWatcher {
            invalidate()
        })
    }

    private fun invalidate() {
        buttonNext.imageAlpha =0
    }
}