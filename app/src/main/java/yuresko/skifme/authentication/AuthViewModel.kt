package yuresko.skifme.authentication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import yuresko.skifme.authentication.model.TimerState
import yuresko.skifme.core.AuthenticationResource
import yuresko.skifme.core.SingleLiveEvent
import yuresko.skifme.core.base.BaseViewModel
import yuresko.skifme.repository.IRepository
import yuresko.skifme.utils.addTo

interface IAuthViewModel {

    val isLoading: LiveData<Boolean>

    val isSendable: LiveData<Boolean>

    val error: LiveData<Throwable>

    val timerState: LiveData<TimerState>

    val openNextScreen: LiveData<String>

    fun onTextChanged(text: String)

    fun authentication(code: String)

    fun sendMessageAgain()
}

class AuthViewModel(private val repository: IRepository) : BaseViewModel(), IAuthViewModel {

    override val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    override val isSendable: MutableLiveData<Boolean> = MutableLiveData(false)

    override val error: SingleLiveEvent<Throwable> = SingleLiveEvent()

    override val timerState: MutableLiveData<TimerState> = MutableLiveData()

    override val openNextScreen: SingleLiveEvent<String> = SingleLiveEvent()

    override fun onTextChanged(text: String) {
        isSendable.value = text.trim().length > 4
    }

    override fun authentication(code: String) {
        repository
            .authentication(code)
            .toObservable()
            .startWith(AuthenticationResource.Loading())
            .subscribeOn(Schedulers.io())
            .subscribe { resource ->
                when (resource) {
                    is AuthenticationResource.Loading -> {
                        isLoading.postValue(true)
                        isSendable.postValue(false)
                    }
                    is AuthenticationResource.Data -> {
                        isLoading.postValue(false)
                        isSendable.postValue(true)
                        openNextScreen.postValue(repository.getUserCode())
                    }
                    is AuthenticationResource.Error -> {
                        isSendable.postValue(true)
                        isLoading.postValue(false)
                        error.postValue(resource.error)
                    }
                }
            }.addTo(compositeDisposable)
    }

    override fun sendMessageAgain() {
        repository
            .verifyNumber(
                repository
                    .getUserPhone()
            )
            .subscribeOn(Schedulers.io())
            .subscribe()
            .addTo(compositeDisposable)
    }
}