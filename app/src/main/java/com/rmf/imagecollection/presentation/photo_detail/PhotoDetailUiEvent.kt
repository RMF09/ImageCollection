package com.rmf.imagecollection.presentation.photo_detail

sealed class PhotoDetailUiEvent {
    data object OnClickLoveIcon : PhotoDetailUiEvent()
}
