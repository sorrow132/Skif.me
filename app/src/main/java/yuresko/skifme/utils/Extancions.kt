package yuresko.skifme.utils

import android.view.View
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

fun View.visibleOrGone(isVisible: Boolean) {
    if(isVisible) {
        visibility = View.VISIBLE
    } else {
        visibility = View.GONE
    }
}