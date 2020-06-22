package yuresko.skifme.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yuresko.skifme.base.BaseViewModel
import yuresko.skifme.network.model.RequestBody
import yuresko.skifme.registration.model.RegistrationState
import yuresko.skifme.repository.IRepository
import yuresko.skifme.utils.addTo

interface IRegViewModel {

    val isEnabled: LiveData<Boolean>

    val state: LiveData<RegistrationState>

    fun fetchState(requestBody: RequestBody)
}

class RegViewModel(private val repository: IRepository) : IRegViewModel, BaseViewModel() {

    override val isEnabled: MutableLiveData<Boolean> = MutableLiveData()

    override val state: MutableLiveData<RegistrationState> = MutableLiveData()

    init {
        state.value = RegistrationState.Default
    }

    override fun fetchState(requestBody: RequestBody) {
        repository
            .verifyNumber(requestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                state.value = RegistrationState.Loading
                isEnabled.value = false
            }
            .subscribe({
                state.value = RegistrationState.Default
                isEnabled.value = true
            }, {
                state.value = RegistrationState.Error(it)
                isEnabled.value = false
            })
            .addTo(compositeDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}