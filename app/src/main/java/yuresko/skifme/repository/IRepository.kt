package yuresko.skifme.repository

interface IRepository {

    fun verifyNumber(phoneNumber: String)

    fun authentication(code: String)

}