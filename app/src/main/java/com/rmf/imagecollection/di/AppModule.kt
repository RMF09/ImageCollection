package com.rmf.imagecollection.di

import android.app.Application
import androidx.room.Room
import com.rmf.imagecollection.data.local.PhotoDB
import com.rmf.imagecollection.data.local.PhotoDao
import com.rmf.imagecollection.data.remote.UnsplashAPI
import com.rmf.imagecollection.data.repository.UnsplashRepositoryImpl
import com.rmf.imagecollection.domain.repository.UnsplashRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideApi(): UnsplashAPI =
        Retrofit.Builder()
            .baseUrl(UnsplashAPI.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()


    @Singleton
    @Provides
    fun provideDatabase(app: Application): PhotoDB =
        Room.databaseBuilder(app, PhotoDB::class.java, "photo_db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun providePhotoDao(db: PhotoDB): PhotoDao = db.photoDao()

    @Singleton
    @Provides
    fun provideUnsplashRepository(
        api: UnsplashAPI,
        photoDao: PhotoDao
    ): UnsplashRepository = UnsplashRepositoryImpl(api = api, photoDao = photoDao)


}