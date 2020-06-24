package yuresko.skifme.network

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import yuresko.skifme.network.model.*

interface SkiffMeApi {

    @POST("/api/users/")
    fun registration(@Body registrationRequest: RegistrationRequest): Single<RegistrationResponse>

    @POST("/api/users/login/")
    fun authentication(
        @Body authenticationBody: AuthenticationBody
    ): Single<Token>
}