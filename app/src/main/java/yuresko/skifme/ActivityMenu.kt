package yuresko.skifme

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet.*
import yuresko.skifme.R

class ActivityMenu : AppCompatActivity() {
    private lateinit var button: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        button = findViewById(R.id.imageButton)

        button.setOnClickListener {
            val bottomSheetDialog = BottomSheetDialog(this)
            val view = LayoutInflater.from(applicationContext)
                .inflate(R.layout.bottom_sheet, findViewById(R.id.bottom_sheet_container))
            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }
    }

}