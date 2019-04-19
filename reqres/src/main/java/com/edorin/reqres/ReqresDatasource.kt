package com.edorin.reqres

import android.net.Uri
import com.edorin.reqres.model.getcolor.GetResourceResponse
import com.edorin.reqres.model.getcolors.GetResourcesResponse
import com.edorin.reqres.model.login.LoginBody
import com.edorin.reqres.model.login.LoginResponse
import com.edorin.reqres.model.register.RegisterBody
import com.edorin.reqres.model.register.RegisterResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

/**
 * Datasource - Retrofit tagged
 */
interface ReqresDatasource {

	// BASE URL =========

	companion object {
		private const val URI_SCHEME = "https"

		private const val URI_AUTHORITY_DEBUG = "reqres.in"
		private const val URI_AUTHORITY_STAGING = "reqres.in"
		private const val URI_AUTHORITY_RELEASE = "reqres.in"

		val baseUrl
			get() = Uri.Builder().apply {
				scheme(URI_SCHEME)
				authority(
					when (BuildConfig.BUILD_TYPE) {
						"debug" -> URI_AUTHORITY_DEBUG
						"staging" -> URI_AUTHORITY_STAGING
						"release" -> URI_AUTHORITY_RELEASE
						else -> URI_AUTHORITY_DEBUG
					}
				)
				appendPath("api")
				appendPath("")
			}.build().toString()
	}

	// ENDPOINTS =========

	@POST("register")
	fun register(
		@Body registerBody: RegisterBody,
		@Query("delay") delay: Int? = 3)
			: Single<Response<RegisterResponse>>

	@POST("login")
	fun login(
		@Body loginBody: LoginBody,
		@Query("delay") delay: Int? = 3)
			: Single<Response<LoginResponse>>

	@GET("unknown")
	fun getResources(@Query("delay") delay: Int? = 3)
			: Single<Response<GetResourcesResponse>>

	@GET("unknown/{id}")
	fun getResource(
		@Path("id") id: Int,
		@Query("delay") delay: Int? = 3)
			: Single<Response<GetResourceResponse>>

}
