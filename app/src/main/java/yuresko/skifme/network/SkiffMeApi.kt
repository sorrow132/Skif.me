package yuresko.skifme.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import yuresko.skifme.registration.model.RegBody
import yuresko.skifme.registration.model.Token

interface SkiffMeApi {

    @POST("/api/users/")
    fun registration(@Body phone: String): Single<Any>

    @POST("/api/users/login")
    fun authentication(@Body regBody: RegBody): Single<Token>
}