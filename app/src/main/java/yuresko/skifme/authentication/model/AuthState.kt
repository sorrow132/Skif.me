package yuresko.skifme.authentication.model

sealed class AuthState {

    object Loading : AuthState()

    data class Default(
        val token: TokenModel
    ) : AuthState()

    data class Error(
        val error: Throwable
    ) : AuthState()
}