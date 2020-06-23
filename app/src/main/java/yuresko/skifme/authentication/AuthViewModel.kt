package yuresko.skifme.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yuresko.skifme.authentication.model.AuthState
import yuresko.skifme.authentication.model.TokenModel
import yuresko.skifme.base.BaseViewModel
import yuresko.skifme.network.model.RegBody
import yuresko.skifme.network.model.RegistrationRequest
import yuresko.skifme.repository.IRepository
import yuresko.skifme.utils.addTo

interface IAuthViewModel {

    val state: LiveData<AuthState>

    fun fetchState(regBody: RegBody)

    fun sendMessageAgain(registrationRequest: RegistrationRequest)
}

class AuthViewModel(private val repository: IRepository) : BaseViewModel(), IAuthViewModel {

    override val state: MutableLiveData<AuthState> = MutableLiveData()

    override fun fetchState(regBody: RegBody) {
        repository
            .authentication(regBody)
            .map { response ->
                TokenModel(response.token)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                state.value = AuthState.Loading
            }.subscribe({
                state.value = AuthState.Default(it)
            }, {
                state.value = AuthState.Error(it)
            })
            .addTo(compositeDisposable)
    }

    override fun sendMessageAgain(registrationRequest: RegistrationRequest) {
//        repository
//            .verifyNumber(registrationRequest)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe()
//            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}