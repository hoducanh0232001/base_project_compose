package com.example.data.database.datasource.machine

interface MachineDataSource {

    suspend fun insertMachineConnected(
        modelId: String,
        address: String,
    )

    suspend fun getConnectedMachines(): List<String>
}