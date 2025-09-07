package com.example.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.REPLACE
import androidx.room.Query
import com.example.data.database.entity.MachineEntity

@Dao
interface MachineDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(machine: MachineEntity)

    @Delete
    suspend fun delete(machine: MachineEntity)

    @Query("SELECT * FROM machine")
    fun getAll(): List<MachineEntity>

}