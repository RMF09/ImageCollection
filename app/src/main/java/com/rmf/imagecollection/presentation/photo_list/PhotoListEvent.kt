package com.rmf.imagecollection.presentation.photo_list

import com.rmf.imagecollection.domain.model.Photo

sealed class PhotoListEvent {

    data class NavigateToPhotoDetailScreen(val photo: Photo) : PhotoListEvent()

}
