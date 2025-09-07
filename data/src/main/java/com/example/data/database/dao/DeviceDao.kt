package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.data.database.entity.DeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {
    @Insert(onConflict = REPLACE)
    suspend fun insert(device: DeviceEntity)

    @Delete
    suspend fun delete(device: DeviceEntity)

    @Query("SELECT * FROM device")
    fun getAll(): Flow<List<DeviceEntity>>
}
