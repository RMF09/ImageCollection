package com.rmf.imagecollection.domain.model

data class User(
    val name: String,
    val username: String
){
    val attributionUrl get() = "https://unsplash.com/$username?utm_source=ImageSearch&utm_medium=referral"
}
