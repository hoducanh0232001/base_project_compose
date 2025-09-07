package com.example.data.http

import com.example.data.connectivity.ConnectivityNetworkManager
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlin.time.Duration.Companion.minutes
import kotlin.time.toJavaDuration

private val loggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

fun createExampleAPI(
    url: String,
    token: String,
    json: Json,
    connectivityManager: ConnectivityNetworkManager,
): ExampleApiService {
    return Retrofit.Builder()
        .baseUrl(url)
        .client(
            OkHttpClient.Builder()
                .addInterceptor(AuthorizationInterceptor(token))
                .addInterceptor(connectivityManager)
                .addNetworkInterceptor(loggingInterceptor)
                .readTimeout(5.minutes.toJavaDuration())
                .connectTimeout(5.minutes.toJavaDuration())
                .writeTimeout(5.minutes.toJavaDuration())
                .build(),
        )
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()
        .create(ExampleApiService::class.java)
}
