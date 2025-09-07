package com.example.data.database.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "machine")
data class MachineEntity(
    @PrimaryKey
    @ColumnInfo(name = "modelId")
    val modelId: String,
    @ColumnInfo(name = "address")
    val address: String,
    @ColumnInfo(name = "last_connected")
    val lastConnected: Long,
)