package yuresko.skifme.authentication.model

sealed class AuthState {

    object Loading : AuthState()

    object Default : AuthState()

    object DefaultWithTimer : AuthState()

    data class Error(
        val error: Throwable
    ) : AuthState()
}