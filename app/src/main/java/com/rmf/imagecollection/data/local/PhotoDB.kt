package com.rmf.imagecollection.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [PhotoEntity::class],
    version = 1
)
abstract class PhotoDB : RoomDatabase() {

    abstract fun photoDao(): PhotoDao
}