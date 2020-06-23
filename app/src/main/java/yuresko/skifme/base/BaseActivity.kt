package yuresko.skifme.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.net.UnknownHostException

open class BaseActivity : AppCompatActivity() {

    protected fun displayError(throwable: Throwable) {
        when (throwable) {
            is UnknownHostException -> {
                Toast.makeText(this, "Проверьте соединение с интернетом", Toast.LENGTH_SHORT).show()
            }
            else -> {
                Toast.makeText(
                    this,
                    throwable.message ?: "Произошла не предвиденная ошибка, попробуйте позже",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}