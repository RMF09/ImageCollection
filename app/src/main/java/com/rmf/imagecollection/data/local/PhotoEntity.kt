package com.rmf.imagecollection.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PhotoEntity(
    @PrimaryKey(autoGenerate = false) val id: String,
    val description: String,
    val username: String,
    val name: String,
    val full: String,
    val raw: String,
    val regular: String,
    val small: String,
    val thumb: String
)
