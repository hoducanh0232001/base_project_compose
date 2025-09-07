package com.example.basecomposeproject.di


import com.example.basecomposeproject.BuildConfig
import com.example.data.connectivity.ConnectivityNetworkManager
import com.example.data.http.ExampleApiService
import com.example.data.http.createExampleAPI
import kotlinx.serialization.json.Json
import org.koin.core.module.Module

import org.koin.dsl.module

val appModule = module {
    viewModelModule()
}

val configModule = module { single { providerExampleAPI(get(), get()) } }



private fun Module.viewModelModule() {


}

fun providerExampleAPI(
    json: Json,
    connectivityManager: ConnectivityNetworkManager,
): ExampleApiService {
    return createExampleAPI(BuildConfig.BASE_URL, BuildConfig.TOKEN, json, connectivityManager)
}

