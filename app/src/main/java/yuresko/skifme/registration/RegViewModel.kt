package yuresko.skifme.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.schedulers.Schedulers
import yuresko.skifme.core.RegistrationResource
import yuresko.skifme.core.SingleLiveEvent
import yuresko.skifme.core.base.BaseViewModel
import yuresko.skifme.repository.IRepository
import yuresko.skifme.utils.addTo

interface IRegViewModel {

    val isLoading: LiveData<Boolean>

    val isSendable: LiveData<Boolean>

    val error: LiveData<Throwable>

    val openNextScreen: LiveData<String>

    fun onTextChanged(text: String)

    fun sendPhoneAuthorization(phone: String)
}

class RegViewModel(private val repository: IRepository) : IRegViewModel, BaseViewModel() {

    override val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)

    override val isSendable: MutableLiveData<Boolean> = MutableLiveData(false)

    override val openNextScreen: SingleLiveEvent<String> =
        SingleLiveEvent()

    override val error: SingleLiveEvent<Throwable> =
        SingleLiveEvent()

    override fun onTextChanged(text: String) {
        isSendable.value = text.trim().length > 15
    }

    override fun sendPhoneAuthorization(phone: String) {
        repository
            .verifyNumber("7$phone")
            .toObservable()
            .startWith(RegistrationResource.Loading())
            .subscribeOn(Schedulers.io())
            .subscribe { resource ->
                when (resource) {
                    is RegistrationResource.Loading -> {
                        isLoading.postValue(true)
                        isSendable.postValue(false)
                    }
                    is RegistrationResource.Data -> {
                        isLoading.postValue(false)
                        isSendable.postValue(true)
                        openNextScreen.postValue(repository.getUserPhone())
                    }
                    is RegistrationResource.Error -> {
                        isSendable.postValue(true)
                        isLoading.postValue(false)
                        error.postValue(resource.error)
                    }
                }
            }
            .addTo(compositeDisposable)
    }
}