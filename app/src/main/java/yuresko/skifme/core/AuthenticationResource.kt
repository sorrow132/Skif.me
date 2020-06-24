package yuresko.skifme.core

sealed class AuthenticationResource<T> {

    class Loading<T> : AuthenticationResource<T>()

    data class Data<T>(val data: T) : AuthenticationResource<T>()

    data class Error<T>(val error: Throwable) : AuthenticationResource<T>()
}