package yuresko.skifme.repository

import io.reactivex.Single
import yuresko.skifme.core.AuthenticationResource
import yuresko.skifme.core.RegistrationResource
import yuresko.skifme.network.SkiffMeApi
import yuresko.skifme.network.model.*

interface IRepository {

    fun verifyNumber(phone: String): Single<RegistrationResource<RegistrationResponse>>

    fun authentication(code: String): Single<AuthenticationResource<Token>>

    fun getUserPhone(): String

    fun getUserCode(): String
}

class RepositoryImpl(private val skiffMeApi: SkiffMeApi) : IRepository {

    private var actualPhone: String = ""
    private var actualCode: String = ""

    override fun verifyNumber(phone: String): Single<RegistrationResource<RegistrationResponse>> {
        actualPhone = phone
        return skiffMeApi
            .registration(RegistrationRequest(phone = phone))
            .map<RegistrationResource<RegistrationResponse>> {
                RegistrationResource.Data(it)
            }
            .onErrorReturn {
                RegistrationResource.Error(it)
            }
    }

    override fun authentication(code: String): Single<AuthenticationResource<Token>> {
        actualCode = code
        return skiffMeApi
            .authentication(
                AuthenticationBody(username = getUserPhone(), password = code)
            )
            .map<AuthenticationResource<Token>> {
                AuthenticationResource.Data(it)
            }
            .onErrorReturn {
                AuthenticationResource.Error(it)
            }
    }

    override fun getUserPhone(): String {
        return actualPhone
    }

    override fun getUserCode(): String {
        return actualCode
    }
}