package com.edorin.litunyi.helpers

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Remote web service helper
 */
object WebService {

    /**
     * Create the retrofit web service
     * @param baseUrl base url of web service
     * @param defaultHeaders default headers for requests
     */
    inline fun <reified T> create(
        baseUrl: String,
        vararg defaultHeaders: Pair<String, String>
    ): T {
        // OkHttpClient builder
        val okHttpClientBuilder: OkHttpClient.Builder = OkHttpClient.Builder()
            .connectTimeout(60L, TimeUnit.SECONDS)
            .readTimeout(60L, TimeUnit.SECONDS)
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })

        // add headers
        for (header in defaultHeaders) {
            val (headerName, headerValue) = header
            okHttpClientBuilder.addInterceptor { chain ->
                chain.proceed(
                    chain.request().newBuilder().addHeader(
                        headerName,
                        headerValue
                    ).build()
                )
            }
        }

        // retrofit
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(T::class.java)
    }

}