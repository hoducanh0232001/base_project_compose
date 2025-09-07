package com.example.data.database.datasource.machine

import com.example.data.database.ExampleDatabase
import com.example.data.database.entity.MachineEntity


internal class MachineLocalDataSource(
    private val database: ExampleDatabase,
) : MachineDataSource {
    override suspend fun insertMachineConnected(modelId: String, address: String) {
        database.machineDao().insert(
            MachineEntity(
                modelId = modelId,
                address = address,
                lastConnected = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun getConnectedMachines(): List<String> {
        return database.machineDao().getAll().map { it.modelId }
    }
}