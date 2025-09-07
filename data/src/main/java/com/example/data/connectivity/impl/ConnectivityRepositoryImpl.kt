package com.example.data.connectivity.impl


import com.example.data.connectivity.ConnectivityNetworkManager
import com.example.data.connectivity.ConnectivityRepository
import com.example.data.utils.ConnectionStatus
import com.example.data.utils.NetworkStatus
import kotlinx.coroutines.flow.Flow

class ConnectivityRepositoryImpl(
    private val connectivityManager: ConnectivityNetworkManager,
) : ConnectivityRepository {

    override val networkStatus: Flow<NetworkStatus>
        get() = connectivityManager.networkStatus

    override val connectionStatus: Flow<ConnectionStatus>
        get() = connectivityManager.connectionStatus
}
