package yuresko.skifme.repository

import io.reactivex.Single
import yuresko.skifme.Resource
import yuresko.skifme.network.SkiffMeApi
import yuresko.skifme.network.model.RegBody
import yuresko.skifme.network.model.RegistrationResponse
import yuresko.skifme.network.model.RegistrationRequest
import yuresko.skifme.network.model.Token

interface IRepository {

    fun verifyNumber(phone: String): Single<Resource<RegistrationResponse>>

    fun authentication(regBody: RegBody): Single<Token>

    fun getUserPhone(): String
}

class RepositoryImpl(private val skiffMeApi: SkiffMeApi) : IRepository {

    private var actualPhone: String = ""

    override fun verifyNumber(phone: String): Single<Resource<RegistrationResponse>> {
        actualPhone = phone
        return skiffMeApi
            .registration(RegistrationRequest(phone = phone))
            .map<Resource<RegistrationResponse>> {
                Resource.Data(it)
            }
            .onErrorReturn {
                Resource.Error(it)
            }
    }

    override fun authentication(regBody: RegBody): Single<Token> {
        return skiffMeApi.authentication(regBody)
    }

    override fun getUserPhone(): String {
        return actualPhone
    }
}