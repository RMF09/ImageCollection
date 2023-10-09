package com.rmf.imagecollection.presentation.favorite_list

import com.rmf.imagecollection.domain.model.Photo

sealed class FavoriteEvent{
    data class NavigatePhotoDetail(val photo: Photo): FavoriteEvent()
}
