package com.example.basecomposeproject

import androidx.lifecycle.viewModelScope
import com.example.core.viewmodel.ViewModel
import com.example.data.connectivity.ConnectivityRepository
import com.example.data.utils.ConnectionStatus
import kotlinx.coroutines.launch

class MainViewModel(
    private val connectivityRepo: ConnectivityRepository,
) : ViewModel() {
    val connectionEvent = EventFlow<ConnectionEvent>()

    init {

        observerNetworkTimeout()
    }



    private fun observerNetworkTimeout() {
        viewModelScope.launch {
            connectivityRepo.connectionStatus.collect {
                when (it) {
                    ConnectionStatus.Timeout -> {
                        connectionEvent.send(ConnectionEvent.ConnectionTimeout)
                    }
                }
            }
        }
    }

}



data class UpdateFirmwareUiState(
    val hasNewFirmware: Boolean = false,
    val isUpgrading: Boolean = false,
)

sealed interface ConnectionEvent {
    data object NoConnection : ConnectionEvent
    data object ConnectionTimeout : ConnectionEvent
}


