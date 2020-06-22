package yuresko.skifme.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yuresko.skifme.authentication.model.AuthState
import yuresko.skifme.base.BaseViewModel
import yuresko.skifme.repository.IRepository
import yuresko.skifme.utils.addTo

interface IAuthViewModel {

    val state: LiveData<AuthState>

    fun fetchState(code: String)
}

class AuthViewModel(private val repository: IRepository) : BaseViewModel(), IAuthViewModel {

    override val state: MutableLiveData<AuthState> = MutableLiveData()

    override fun fetchState(code: String) {
        repository
            .authentication(code)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                state.value = AuthState.Loading
            }.subscribe({

            }, {
                state.value = AuthState.Error(it)
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}