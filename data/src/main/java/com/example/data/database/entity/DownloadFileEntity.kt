package com.example.data.database.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "check_download_file")
data class DownloadFileEntity(
    @PrimaryKey @ColumnInfo(name = "id") val id: String = "",
    @ColumnInfo(name = "url") val url: String,
    @ColumnInfo(name = "time") val time: Long,
)
