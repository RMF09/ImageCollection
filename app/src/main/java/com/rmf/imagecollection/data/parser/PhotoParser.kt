package com.rmf.imagecollection.data.parser

import com.rmf.imagecollection.data.local.PhotoEntity
import com.rmf.imagecollection.data.remote.dto.PhotoDto
import com.rmf.imagecollection.domain.model.Photo
import com.rmf.imagecollection.domain.model.Urls
import com.rmf.imagecollection.domain.model.User

fun PhotoDto.toPhoto() =
    Photo(
        id = id,
        description = description,
        urls = urls.toUrls(),
        user = user.toUser()
    )

fun PhotoEntity.toPhoto() =
    Photo(
        id = id,
        description = description,
        urls = Urls(
            full = full,
            raw = raw,
            regular = regular,
            small = small,
            thumb = thumb
        ),
        user = User(
            name = name,
            username = username
        )
    )

fun Photo.toEntity() =
    PhotoEntity(
        id = id,
        description = description ?: "",
        full = urls.full,
        raw = urls.raw,
        regular = urls.regular,
        small = urls.small,
        thumb = urls.thumb,
        name = user.name,
        username = user.username,
    )