package yuresko.skifme.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.sapereaude.maskedEditText.MaskedEditText
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import yuresko.skifme.R
import yuresko.skifme.SkiffApplication
import yuresko.skifme.authentication.ActivityThree
import yuresko.skifme.authentication.EditTextWatcher
import yuresko.skifme.network.SkiffMeApi
import yuresko.skifme.repository.IRepository
import javax.inject.Inject

class ActivityTwo : AppCompatActivity() {
    private lateinit var maskedEditText: MaskedEditText
    private lateinit var buttonNext: ImageView

    @Inject
    lateinit var repository: IRepository

    private lateinit var viewModel: IRegViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as SkiffApplication).component.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return modelClass.getConstructor(
                    IRepository::class.java
                )
                    .newInstance(repository)
            }
        }).get(RegViewModel::class.java)

        maskedEditText = findViewById(R.id.phone_input)
        buttonNext = findViewById(R.id.imageView2)

        viewModel.sendPhone("79013532604")

        buttonNext.setOnClickListener {
            val intent = Intent(this, ActivityThree::class.java)
            startActivity(intent)
        }
        supportActionBar?.title = "Авторизация"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        maskedEditText.addTextChangedListener(EditTextWatcher {
            invalidate()
        })


        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://www.skiff.sol.bz/api/users/")

        val ttt = retrofit.build().create(SkiffMeApi::class.java)

        val call = ttt.registration("79013532604")

        call.enqueue(object: Callback<Any>{
            override fun onFailure(call: Call<Any>, t: Throwable) {

            }

            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                Log.d("1","1")
            }
        })
    }

    private fun invalidate() {
        buttonNext.imageAlpha = 0
    }
}