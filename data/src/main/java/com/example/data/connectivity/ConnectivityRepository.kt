package com.example.data.connectivity


import com.example.data.utils.ConnectionStatus
import com.example.data.utils.NetworkStatus
import kotlinx.coroutines.flow.Flow

interface ConnectivityRepository {
    val networkStatus: Flow<NetworkStatus>

    val connectionStatus: Flow<ConnectionStatus>
}
