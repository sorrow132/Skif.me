package yuresko.skifme.authentication

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import yuresko.skifme.R
import yuresko.skifme.SkiffApplication
import yuresko.skifme.authentication.model.AuthState
import yuresko.skifme.registration.RegViewModel
import yuresko.skifme.repository.IRepository
import javax.inject.Inject

class ActivityThree : AppCompatActivity() {
    private lateinit var buttonNext: ImageView
    private lateinit var editText: EditText

    @Inject
    lateinit var repository: IRepository

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as SkiffApplication).component.inject(this)
        setContentView(R.layout.activity_three)
        buttonNext = findViewById(R.id.act3Button)
        editText = findViewById(R.id.phone_input)

        supportActionBar?.title = "Авторизация"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RegViewModel(repository) as T
            }
        }).get(AuthViewModel::class.java)

        viewModel.state.observe(this, Observer { state ->
            when (state){
                AuthState.Loading -> TODO()
                AuthState.Default -> TODO()
                AuthState.DefaultWithTimer -> TODO()
                is AuthState.Error -> TODO()
            }
        })

    }
}