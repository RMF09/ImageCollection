package com.rmf.imagecollection.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Photo(
    val description: String?=null,
    val id: String,
    val urls: Urls,
    val user: User
) : Parcelable
