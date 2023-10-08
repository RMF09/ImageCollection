package com.rmf.imagecollection.domain.model

data class Photo(
    val description: String?=null,
    val id: String,
    val urls: Urls,
    val user: User
)
