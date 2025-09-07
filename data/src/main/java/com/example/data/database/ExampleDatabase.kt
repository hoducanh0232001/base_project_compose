package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.data.database.dao.DeviceDao
import com.example.data.database.dao.MachineDao
import com.example.data.database.entity.DeviceEntity
import com.example.data.database.entity.DownloadFileDao
import com.example.data.database.entity.DownloadFileEntity
import com.example.data.database.entity.MachineEntity


@Database(
    entities = [
        DeviceEntity::class,
        DownloadFileEntity::class,
        MachineEntity::class
    ],
    version = 1,
    exportSchema = false,
)
abstract class ExampleDatabase : RoomDatabase() {

    abstract fun deviceDao(): DeviceDao
    abstract fun downloadFileDao(): DownloadFileDao
    abstract fun machineDao(): MachineDao
}

fun providerExampleDatabase(context: Context): ExampleDatabase {
    return Room.databaseBuilder(context, ExampleDatabase::class.java, "example.db").apply {
//        if (BuildConfig.DEBUG)
//            fallbackToDestructiveMigration()
    }.build()
}
