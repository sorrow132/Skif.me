package yuresko.skifme.registration

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.sapereaude.maskedEditText.MaskedEditText
import kotlinx.android.synthetic.main.activity_two.*
import yuresko.skifme.R
import yuresko.skifme.SkiffApplication
import yuresko.skifme.authentication.ActivityThree
import yuresko.skifme.network.model.RequestBody
import yuresko.skifme.registration.model.RegistrationState
import yuresko.skifme.repository.IRepository
import javax.inject.Inject

class ActivityTwo : AppCompatActivity() {
    private lateinit var maskedEditText: MaskedEditText
    private lateinit var buttonNext: ImageView
    private lateinit var progressBar: ProgressBar

    @Inject
    lateinit var repository: IRepository

    private lateinit var viewModel: IRegViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as SkiffApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
        maskedEditText = findViewById(R.id.phoneInput)
        buttonNext = findViewById(R.id.buttonActivityTwo)
        progressBar = findViewById(R.id.progressBarActivityTwo)

        supportActionBar?.title = "Авторизация"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonNext.isEnabled = false
        buttonNext.alpha = 0.2f

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RegViewModel(repository) as T
            }
        }).get(RegViewModel::class.java)

        maskedEditText.addTextChangedListener(RegTextWatcher {
            invalidateFrom()
        })

        observeLiveData()

    }

    private fun invalidateFrom() {
        if (maskedEditText.text?.length == 16) {
            buttonNext.isEnabled = true
            buttonNext.alpha = 1f
        } else {
            buttonNext.isEnabled = false
            buttonNext.alpha = 0.2f
        }
    }

    private fun observeLiveData() {
        viewModel.state.observe(this, Observer { state ->
            when (state) {
                RegistrationState.Loading -> {
                    progressBar.visibility = View.GONE
                    textViewNumber.visibility = View.GONE
                    phoneInput.visibility = View.GONE
                    buttonActivityTwo.visibility = View.GONE
                    textViewActivityTwo.visibility = View.GONE
                }
                RegistrationState.Default -> {
                    progressBar.visibility = View.GONE
                    textViewNumber.visibility = View.VISIBLE
                    phoneInput.visibility = View.VISIBLE
                    buttonActivityTwo.visibility = View.VISIBLE
                    textViewActivityTwo.visibility = View.VISIBLE

                    buttonNext.setOnClickListener {
                        viewModel.fetchState(RequestBody(maskedEditText.rawText))
                        val intent = Intent(this, ActivityThree::class.java)
                        startActivity(intent)
                    }
                }
                is RegistrationState.Error -> {
                    progressBar.visibility = View.GONE
                    textViewNumber.visibility = View.GONE
                    phoneInput.visibility = View.GONE
                    buttonActivityTwo.visibility = View.GONE
                    textViewActivityTwo.visibility = View.GONE
                }
            }
        })
    }
}