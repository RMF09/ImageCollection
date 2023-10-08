package com.rmf.imagecollection.data.parser

import com.rmf.imagecollection.data.remote.dto.UrlsDto
import com.rmf.imagecollection.domain.model.Urls

fun UrlsDto.toUrls() =
    Urls(
        full = full,
        raw = raw,
        regular = regular,
        small = small,
        thumb = thumb
    )