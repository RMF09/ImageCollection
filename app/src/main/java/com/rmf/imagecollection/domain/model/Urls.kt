package com.rmf.imagecollection.domain.model

import android.os.Parcelable
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.parcelize.Parcelize

@Parcelize
data class Urls(
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
): Parcelable
