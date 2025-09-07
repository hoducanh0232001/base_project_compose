package com.example.data.utils


sealed interface NetworkStatus {
    data object Available : NetworkStatus
    data object Unavailable : NetworkStatus
    data object Lost : NetworkStatus
}

sealed interface ConnectionStatus {
    data object Timeout : ConnectionStatus
}