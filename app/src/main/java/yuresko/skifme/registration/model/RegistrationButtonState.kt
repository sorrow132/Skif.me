package yuresko.skifme.registration.model

sealed class RegistrationButtonState {

    data class IsEnabled(
        val button: Boolean
    ) : RegistrationButtonState()

    data class Error(
        val error: Throwable
    ) : RegistrationButtonState()
}