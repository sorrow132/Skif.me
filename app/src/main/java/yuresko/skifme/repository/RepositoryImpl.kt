package yuresko.skifme.repository

import yuresko.skifme.network.SkiffMeApi
import yuresko.skifme.registration.model.RegBody

class RepositoryImpl(private val skiffMeApi: SkiffMeApi) : IRepository {

    override fun verifyNumber(phoneNumber: String) {
        skiffMeApi.registration("79013532604")
    }

    override fun authentication(code: String) {
        skiffMeApi.authentication(
            RegBody(
                "79013532604",
                "61751"
            )
        )
    }
}