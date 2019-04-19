package com.edorin.mvvmtemplate.domain

import com.edorin.mvvmtemplate.common.prefs.ColorsCache
import com.edorin.mvvmtemplate.domain.entity.Color
import com.edorin.mvvmtemplate.domain.ext.toColorEntity
import com.edorin.reqres.ReqresDatasource
import com.edorin.reqres.model.login.LoginBody
import com.edorin.reqres.model.register.RegisterBody
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Repository
 */
interface ColorsRepository {
    val isAuthenticated: Single<Boolean>
    fun register(email: String, password: String): Completable
    fun login(email: String, password: String): Completable
    fun getColors(): Single<List<Color>>
    fun getColor(id: Int): Single<Color>
}

/**
 * Repository implementation
 * Make use of ReqresDatasource & add some cache
 */
class ColorsRepositoryImpl(
    private val reqresDatasource: ReqresDatasource
) : ColorsRepository {

    private val cachedColors get() = ColorsCache.colors

    override val isAuthenticated: Single<Boolean> get() = Single.just(ColorsCache.token.isNotBlank())

    override fun register(email: String, password: String): Completable {
        return reqresDatasource.register(RegisterBody(email, password))
            .map { response ->
                val responseBody = response.body()
                if (response.isSuccessful) responseBody?.token ?: throw Exception(responseBody?.error ?: "No token")
                else throw Exception(responseBody?.error ?: "Registration Failed")
            }
            .ignoreElement()
    }

    override fun login(email: String, password: String): Completable {
        return reqresDatasource.login(LoginBody(email, password))
            .map { response ->
                val responseBody = response.body()
                if (response.isSuccessful) responseBody?.token ?: throw Exception(responseBody?.error ?: "No token")
                else throw Exception(responseBody?.error ?: "Login Failed")
            }
            .doOnSuccess { token -> ColorsCache.token = token }
            .ignoreElement()
    }

    override fun getColors(): Single<List<Color>> {
        return reqresDatasource.getResources()
            .map { response -> response.body()?.data }
            .map { resources -> resources.map { it.toColorEntity() } }
            .doOnSuccess { colors ->
                cachedColors.clear()
                colors.forEach { color -> cachedColors.add(color) }
            }
            .doOnError { cachedColors }
    }

    override fun getColor(id: Int): Single<Color> {
        return reqresDatasource.getResource(id)
            .map { response -> response.body()?.data ?: throw Exception("No data") }
            .map { resource -> resource.toColorEntity() }
    }

}
