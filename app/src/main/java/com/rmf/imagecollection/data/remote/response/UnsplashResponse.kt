package com.rmf.imagecollection.data.remote.response

import com.rmf.imagecollection.data.remote.dto.PhotoDto

data class UnsplashResponse(
    val code: Int,
    val results: List<PhotoDto>
)