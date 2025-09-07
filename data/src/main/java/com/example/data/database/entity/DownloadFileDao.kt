package com.example.data.database.entity
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DownloadFileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(checkDownloadFileEntity: DownloadFileEntity)

    @Query("SELECT * FROM check_download_file WHERE url IN (:url)")
    suspend fun getByUrl(url: List<String>): List<DownloadFileEntity>
}
