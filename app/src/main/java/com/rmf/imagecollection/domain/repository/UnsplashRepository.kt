package com.rmf.imagecollection.domain.repository

import androidx.paging.PagingData
import com.rmf.imagecollection.domain.model.Photo
import kotlinx.coroutines.flow.Flow

interface UnsplashRepository {

    suspend fun getPhotos(searchQuery: String): Flow<PagingData<Photo>>

    fun getFavoritePhotos(): Flow<List<Photo>>

    suspend fun addToFavorite(photo: Photo)

    suspend fun removeFromFavorite(photo: Photo)
}