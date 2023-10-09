package com.rmf.imagecollection.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.rmf.imagecollection.data.parser.toPhoto
import com.rmf.imagecollection.data.remote.UnsplashAPI
import com.rmf.imagecollection.domain.model.Photo

class PhotoPagingSource(
    private val api: UnsplashAPI,
    private val searchQuery: String
) : PagingSource<Int, Photo>() {

    companion object {
        const val ITEMS_PER_PAGE = 10
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>) =
        state.anchorPosition


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val nextPage = params.key ?: 1

        return try {
            val response =
                api.searchPhotos(query = searchQuery, page = nextPage, perPage = ITEMS_PER_PAGE)

            val photos = response.results
            LoadResult.Page(
                data = photos.map { it.toPhoto() },
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = if (photos.isEmpty()) null else nextPage.plus(1)
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}