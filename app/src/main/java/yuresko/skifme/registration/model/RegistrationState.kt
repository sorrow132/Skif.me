package yuresko.skifme.registration.model

sealed class RegistrationState {

    object Loading : RegistrationState()

    object Default : RegistrationState()

    data class Error(
        val error: Throwable
    ) : RegistrationState()
}