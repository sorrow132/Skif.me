package yuresko.skifme

sealed class Resource<T> {
    class Loading<T> : Resource<T>()

    data class Data<T>(val data: T) : Resource<T>()

    data class Error<T>(val error: Throwable) : Resource<T>()
}