package com.rmf.imagecollection.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Query("SELECT * FROM photoentity ORDER BY date DESC")
    fun getFavoritePhotos(): Flow<List<PhotoEntity>>

    @Query("SELECT * FROM photoentity WHERE id = :id")
    fun getFavoritePhoto(id: String): Flow<PhotoEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorite(photoEntity: PhotoEntity)

    @Delete
    suspend fun removeFromFavorite(photoEntity: PhotoEntity)
}