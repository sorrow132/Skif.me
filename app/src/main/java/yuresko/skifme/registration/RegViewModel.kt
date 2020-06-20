package yuresko.skifme.registration

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import yuresko.skifme.repository.IRepository

interface IRegViewModel {
    val phoneNumber: LiveData<String>

    fun sendPhone(phoneNUmber: String)
}

class RegViewModel(private val IRepository: IRepository) : ViewModel(), IRegViewModel {

    override val phoneNumber: MutableLiveData<String> = MutableLiveData()

    override fun sendPhone(phoneNUmber: String) {
        Completable.fromAction {
            IRepository
                .verifyNumber(phoneNUmber)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}