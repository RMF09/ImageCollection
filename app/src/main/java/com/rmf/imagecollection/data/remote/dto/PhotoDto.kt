package com.rmf.imagecollection.data.remote.dto

data class PhotoDto(
    val description: String?=null,
    val id: String,
    val urls: UrlsDto,
    val user: UserDto
)
