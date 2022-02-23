package com.example.pocketfridge.model.repsitory

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * API client manager.
 */
class ApiClientManager {

    companion object {
        /** 通信タイムアウト. */
        const val TIME_OUT: Long = 120
    }

    /** Apiクライアント. */
    fun getApiClient(endpoint: String): ApiClient =
        Retrofit.Builder()
            .client(getClient())
            .baseUrl(endpoint)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiClient::class.java)

    /** OkHttpクライアント */
    private fun getClient() =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)
            .build()
}