package yuresko.skifme.core

sealed class RegistrationResource<T> {
    class Loading<T> : RegistrationResource<T>()

    data class Data<T>(val data: T) : RegistrationResource<T>()

    data class Error<T>(val error: Throwable) : RegistrationResource<T>()
}