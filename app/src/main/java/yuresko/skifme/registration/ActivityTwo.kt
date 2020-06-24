package yuresko.skifme.registration

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.doOnNextLayout
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.sapereaude.maskedEditText.MaskedEditText
import yuresko.skifme.R
import yuresko.skifme.SkiffApplication
import yuresko.skifme.authentication.ActivityThree
import yuresko.skifme.core.base.BaseActivity
import yuresko.skifme.repository.IRepository
import yuresko.skifme.utils.visibleOrGone
import javax.inject.Inject

class ActivityTwo : BaseActivity() {
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

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return RegViewModel(repository) as T
            }
        }).get(RegViewModel::class.java)

        supportActionBar?.title = "Авторизация"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        buttonNext.setOnClickListener {
            viewModel.sendPhoneAuthorization(maskedEditText.rawText)
        }

        maskedEditText.doAfterTextChanged {
            viewModel.onTextChanged(it?.toString() ?: "")
        }

        viewModel
            .isLoading
            .observe(this, Observer { isLoading ->
                progressBar.visibleOrGone(isLoading)
            })

        viewModel
            .isSendable
            .observe(this, Observer { isSendable ->
                buttonNext.isEnabled = isSendable
                buttonNext.alpha = if (isSendable) {
                    1f
                } else {
                    0.2f
                }
            })

        viewModel
            .openNextScreen
            .observe(this, Observer { _ ->
                val intent = Intent(this, ActivityThree::class.java)
                startActivity(intent)
            })

        viewModel
            .error
            .observe(this, Observer(::displayError))
    }

}