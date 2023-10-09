package com.rmf.imagecollection.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.rmf.imagecollection.data.local.PhotoDao
import com.rmf.imagecollection.data.paging.PhotoPagingSource
import com.rmf.imagecollection.data.parser.toEntity
import com.rmf.imagecollection.data.parser.toPhoto
import com.rmf.imagecollection.data.remote.UnsplashAPI
import com.rmf.imagecollection.domain.model.Photo
import com.rmf.imagecollection.domain.repository.UnsplashRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UnsplashRepositoryImpl @Inject constructor(
    private val api: UnsplashAPI,
    private val photoDao: PhotoDao
) : UnsplashRepository {
    override suspend fun getPhotos(searchQuery: String): Flow<PagingData<Photo>> =
        Pager(
            config = PagingConfig(pageSize = 10, prefetchDistance = 2)
        ) {
            PhotoPagingSource(api = api, searchQuery = searchQuery)
        }.flow

    override fun getFavoritePhotos(): Flow<List<Photo>> {
        return photoDao.getFavoritePhotos().map { res -> res.map { it.toPhoto() } }
    }

    override fun getFavoritePhoto(id: String): Flow<Photo?> {
        return photoDao.getFavoritePhoto(id).map { res -> res?.toPhoto() }
    }

    override suspend fun addToFavorite(photo: Photo) {
        photoDao.addToFavorite(photo.toEntity(date = System.currentTimeMillis()))
    }

    override suspend fun removeFromFavorite(photo: Photo) {
        photoDao.removeFromFavorite(photo.toEntity())

    }
}