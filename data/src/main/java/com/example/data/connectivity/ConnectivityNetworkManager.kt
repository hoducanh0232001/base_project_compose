package com.example.data.connectivity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.data.utils.ConnectionStatus
import com.example.data.utils.NetworkStatus

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.receiveAsFlow
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ConnectivityNetworkManager(
    private val context: Context,
): Interceptor {
    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val connectionChannel = Channel<ConnectionStatus>(capacity = Channel.BUFFERED)

    val networkStatus: Flow<NetworkStatus>
        @SuppressLint("MissingPermission")
        get() = callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    super.onAvailable(network)
                    trySend(NetworkStatus.Available)
                }

                override fun onUnavailable() {
                    super.onUnavailable()
                    trySend(NetworkStatus.Unavailable)
                }

                override fun onLost(network: Network) {
                    super.onLost(network)
                    trySend(NetworkStatus.Lost)
                }
            }
            trySend(getCurrentNetworkStatus())
            val request = NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build()
            connectivityManager.registerNetworkCallback(request, callback)
            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }

    @SuppressLint("MissingPermission")
    private fun getCurrentNetworkStatus(): NetworkStatus {
        val network = connectivityManager.activeNetwork ?: return NetworkStatus.Unavailable
        val capabilities = connectivityManager.getNetworkCapabilities(network)
            ?: return NetworkStatus.Unavailable
        return if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
            NetworkStatus.Available
        } else {
            NetworkStatus.Unavailable
        }
    }

    val connectionStatus: Flow<ConnectionStatus>
        get() = connectionChannel.receiveAsFlow()

    override fun intercept(chain: Interceptor.Chain): Response {
        return try {
            chain.proceed(chain.request())
        } catch (e: IOException) {
            when (e) {
                is SocketException,
                is SocketTimeoutException,
                is UnknownHostException,
                is ConnectionShutdownException,
                    -> {
                    connectionChannel.trySend(ConnectionStatus.Timeout)
                }
            }
            throw e
        }
    }
}