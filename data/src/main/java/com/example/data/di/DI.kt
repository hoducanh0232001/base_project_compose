package com.example.data.di
import com.example.data.connectivity.ConnectivityNetworkManager
import com.example.data.connectivity.ConnectivityRepository
import com.example.data.connectivity.impl.ConnectivityRepositoryImpl
import com.example.data.database.datasource.machine.MachineLocalDataSource
import com.example.data.database.providerExampleDatabase
import com.example.data.settings.ConfigSettings
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single { Json { prettyPrint = true } }
    singleOf(::ConnectivityNetworkManager)
    singleOf(::providerExampleDatabase)
    singleOf(::ConfigSettings)
    singleOf(::ConnectivityRepositoryImpl) bind ConnectivityRepository::class
    singleOf(::MachineLocalDataSource) bind MachineLocalDataSource::class
}
