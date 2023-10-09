package com.rmf.imagecollection.presentation.favorite_list

import com.rmf.imagecollection.domain.model.Photo

sealed class FavoriteUiEvent{
    data class OnClickPhoto(val photo: Photo): FavoriteUiEvent()
}
