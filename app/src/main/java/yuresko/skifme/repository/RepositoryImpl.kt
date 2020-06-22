package yuresko.skifme.repository

import io.reactivex.Single
import yuresko.skifme.network.SkiffMeApi
import yuresko.skifme.network.model.RegBody
import yuresko.skifme.network.model.RegistrationResponse
import yuresko.skifme.network.model.RequestBody
import yuresko.skifme.network.model.Token

interface IRepository {

    fun verifyNumber(requestBody: RequestBody): Single<RegistrationResponse>

    fun authentication(code: String): Single<Token>
}

class RepositoryImpl(private val skiffMeApi: SkiffMeApi) : IRepository {

    override fun verifyNumber(requestBody: RequestBody): Single<RegistrationResponse> {
        return skiffMeApi.registration(requestBody)
    }

    override fun authentication(code: String): Single<Token> {
        return skiffMeApi.authentication(RegBody("79013532604", "61751"))
    }
}