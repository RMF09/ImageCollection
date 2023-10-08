package com.rmf.imagecollection.data.parser

import com.rmf.imagecollection.data.remote.dto.PhotoDto
import com.rmf.imagecollection.domain.model.Photo

fun PhotoDto.toPhoto() =
    Photo(
        id = id,
        description = description,
        urls = urls.toUrls(),
        user = user.toUser()
    )