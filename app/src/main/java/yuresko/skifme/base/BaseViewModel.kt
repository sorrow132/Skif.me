package yuresko.skifme.base

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseViewModel : ViewModel(), LifecycleObserver {
    protected val compositeDisposable: CompositeDisposable = CompositeDisposable()

    protected var fetchCurrencyDisposable: Disposable? = null

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}