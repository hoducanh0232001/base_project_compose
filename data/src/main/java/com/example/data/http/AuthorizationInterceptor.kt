package com.example.data.http

import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(val token: String) : Interceptor {

    companion object {
        private const val AUTHORIZATION = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .header(
                    AUTHORIZATION,
                    token,
                )
                .build(),
        )
    }
}
