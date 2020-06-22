package yuresko.skifme.registration

import android.text.Editable
import android.text.TextWatcher

class RegTextWatcher(private val callback: () -> Unit): TextWatcher {
    override fun afterTextChanged(s: Editable?) {
        callback()
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }
}