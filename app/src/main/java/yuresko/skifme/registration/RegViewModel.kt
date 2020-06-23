package yuresko.skifme.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import yuresko.skifme.Resource
import yuresko.skifme.SingleLiveEvent
import yuresko.skifme.base.BaseViewModel
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

    override val openNextScreen: SingleLiveEvent<String> = SingleLiveEvent()

    override val error: SingleLiveEvent<Throwable> = SingleLiveEvent()

    override fun onTextChanged(text: String) {
        isSendable.value = text.trim().length > 15
    }

    override fun sendPhoneAuthorization(phone: String) {
        repository
            .verifyNumber("7$phone")
            .toObservable()
            .startWith(Resource.Loading())
            .subscribeOn(Schedulers.io())
            .subscribe { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        isLoading.postValue(true)
                        isSendable.postValue(false)
                    }
                    is Resource.Data -> {
                        isLoading.postValue(false)
                        isSendable.postValue(true)
                        openNextScreen.postValue(repository.getUserPhone())
                    }
                    is Resource.Error -> {
                        isSendable.postValue(true)
                        isLoading.postValue(false)
                        error.postValue(resource.error)
                    }
                }
            }
            .addTo(compositeDisposable)
//        repository
//            .verifyNumber(requestBody)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .doOnSubscribe {
//                state.value = RegistrationState.Loading
//            }
//            .subscribe({
//                state.value = RegistrationState.Default
//            }, {
//                state.value = RegistrationState.Error(it)
//            })
//            .addTo(compositeDisposable)
    }

}