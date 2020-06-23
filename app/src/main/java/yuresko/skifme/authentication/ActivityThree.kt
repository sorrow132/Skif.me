package yuresko.skifme.authentication

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_three.*
import yuresko.skifme.R
import yuresko.skifme.SkiffApplication
import yuresko.skifme.authentication.model.AuthState
import yuresko.skifme.network.model.RegBody
import yuresko.skifme.network.model.RegistrationRequest
import yuresko.skifme.repository.IRepository
import javax.inject.Inject

class ActivityThree : AppCompatActivity() {
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

        buttonNext.alpha = 0.2f
        buttonNext.isEnabled = false

        sendAgain.setOnClickListener {
            countDownTimer()
            resetTimer()
            viewModel.sendMessageAgain(RegistrationRequest((application as SkiffApplication).userNum))
        }

        editText.addTextChangedListener(AuthTextWatcher {
            invalidateForm()
        })

        observeLiveData()
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

    private fun invalidateForm() {
        if (editText.text.length == 5) {
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
                AuthState.Loading -> {
                    progressBarActivityThree.visibility = View.GONE
                    textViewNumberActivityThree.visibility = View.GONE
                    phoneInputActivityThree.visibility = View.GONE
                    act3ButtonActivityThree.visibility = View.GONE
                }
                is AuthState.Default -> {
                    progressBarActivityThree.visibility = View.GONE
                    textViewNumberActivityThree.visibility = View.VISIBLE
                    phoneInputActivityThree.visibility = View.VISIBLE
                    act3ButtonActivityThree.visibility = View.VISIBLE

                    buttonNext.setOnClickListener {
                        viewModel.fetchState(
                            RegBody(
                                (application as SkiffApplication).userNum, editText.text.toString()
                            )
                        )
                    }
                    Log.d("XUY", state.token.toString())

                }
                is AuthState.Error -> {
                    progressBarActivityThree.visibility = View.GONE
                    textViewNumberActivityThree.visibility = View.GONE
                    phoneInputActivityThree.visibility = View.GONE
                    act3ButtonActivityThree.visibility = View.GONE
                    val toast = Toast.makeText(this, "Error", Toast.LENGTH_SHORT)
                    toast.show()
                }
            }
        })
    }
}