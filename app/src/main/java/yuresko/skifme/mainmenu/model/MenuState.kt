package yuresko.skifme.mainmenu.model

sealed class MenuState {
    object Loading : MenuState()

    object EmptyList : MenuState()

    data class Exist(
        val phoneTime: PhoneTime
    ) : MenuState()

    data class Error(
        val error: Throwable
    ) : MenuState()
}