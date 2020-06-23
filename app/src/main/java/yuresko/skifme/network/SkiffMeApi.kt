package yuresko.skifme.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import yuresko.skifme.network.model.RegBody
import yuresko.skifme.network.model.RegistrationResponse
import yuresko.skifme.network.model.RegistrationRequest
import yuresko.skifme.network.model.Token

interface SkiffMeApi {

    @POST("/api/users/")
    fun registration(@Body registrationRequest: RegistrationRequest): Single<RegistrationResponse>

    @POST("/api/users/login")
    fun authentication(@Body regBody: RegBody): Single<Token>
}