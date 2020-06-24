package yuresko.skifme.authentication

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_three.*
import yuresko.skifme.R
import yuresko.skifme.SkiffApplication
import yuresko.skifme.core.base.BaseActivity
import yuresko.skifme.mainmenu.ActivityMenu
import yuresko.skifme.repository.IRepository
import yuresko.skifme.utils.visibleOrGone
import javax.inject.Inject

class ActivityThree : BaseActivity() {
    private lateinit var buttonNext: ImageView
    private lateinit var editText: EditText
    private lateinit var sendAgain: TextView

    private lateinit var mCountDownTimer: CountDownTimer
    private var isRunning: Boolean = true
    private var mTimeLeftInMillis = 30000L
    var timeInMilliSeconds = 1000L

    @Inject
    lateinit var repository: IRepository

    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as SkiffApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_three)
        buttonNext = findViewById(R.id.act3ButtonActivityThree)
        editText = findViewById(R.id.phoneInputActivityThree)
        sendAgain = findViewById(R.id.sendAgainActivityThree)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return AuthViewModel(repository) as T
            }
        }).get(AuthViewModel::class.java)

        supportActionBar?.title = "Авторизация"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sendAgain.setOnClickListener {
            countDownTimer()
            resetTimer()
            viewModel.sendMessageAgain()
        }

        buttonNext.setOnClickListener {
            viewModel.authentication(editText.text.toString())
        }

        editText.doAfterTextChanged {
            viewModel.onTextChanged(it?.toString() ?: "")
        }

        viewModel
            .isLoading
            .observe(this, Observer { isLoading ->
                progressBarActivityThree.visibleOrGone(isLoading)
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
                val intent = Intent(this, ActivityMenu::class.java)
                startActivity(intent)
            })

        viewModel
            .error
            .observe(this, Observer(::displayError))

        countDownTimer()
        showHideUI()
    }

    private fun showHideUI() {
        if (isRunning) {
            sendAgainActivityThree.visibility = View.GONE
            textView2ActivityThree.visibility = View.GONE
            textViewSendAgain.visibility = View.VISIBLE
            textViewTimer.visibility = View.VISIBLE

        } else {
            textViewSendAgain.visibility = View.GONE
            textViewTimer.visibility = View.GONE
            sendAgainActivityThree.visibility = View.VISIBLE
            textView2ActivityThree.visibility = View.VISIBLE
        }
    }

    private fun resetTimer() {
        isRunning = true
        updateTextUI()
        showHideUI()
    }

    private fun updateTextUI() {
        val seconds = (timeInMilliSeconds / 1000) % 60
        textViewTimer.text = seconds.toString()
    }

    private fun countDownTimer() {
        mCountDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onFinish() {
                textViewSendAgain.visibility = View.GONE
                textViewTimer.visibility = View.GONE
                sendAgainActivityThree.visibility = View.VISIBLE
                textView2ActivityThree.visibility = View.VISIBLE
                isRunning = false
            }

            override fun onTick(millisUntilFinished: Long) {
                timeInMilliSeconds = millisUntilFinished
                textViewTimer.text = "${millisUntilFinished / 1000}"
            }
        }
        mCountDownTimer.start()
    }
}