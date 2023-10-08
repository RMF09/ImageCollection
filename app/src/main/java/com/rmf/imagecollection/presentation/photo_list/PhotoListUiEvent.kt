package com.rmf.imagecollection.presentation.photo_list

import com.rmf.imagecollection.domain.model.Photo

sealed class PhotoListUiEvent {

    data class OnSearchQueryChange(val value: String): PhotoListUiEvent()
    data class OnClickPhoto(val photo: Photo) : PhotoListUiEvent()
    data object OnClickSearch : PhotoListUiEvent()
}
