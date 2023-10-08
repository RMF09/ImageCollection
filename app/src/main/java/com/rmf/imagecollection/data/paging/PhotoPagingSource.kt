package com.rmf.imagecollection.data.paging

import android.util.Log
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
        const val ITEM_PER_PAGE = 30
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>) =
        state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }


    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        val nextPage = params.key ?: 1

        return try {
            val response =
                api.searchPhotos(query = searchQuery, page = nextPage, perPage = ITEM_PER_PAGE)

            Log.e("TAG", "load: ${response.code}", )

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