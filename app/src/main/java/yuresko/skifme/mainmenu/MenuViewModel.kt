package yuresko.skifme.mainmenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import yuresko.skifme.base.BaseViewModel
import yuresko.skifme.mainmenu.model.MenuState
import yuresko.skifme.repository.IRepository

interface IMenuViewModel {
    val state: LiveData<MenuState>

    fun fetchState()
}

class MenuViewModel(private val repository: IRepository) : BaseViewModel(), IMenuViewModel {

    override val state: MutableLiveData<MenuState> = MutableLiveData()


    override fun fetchState() {

    }
}