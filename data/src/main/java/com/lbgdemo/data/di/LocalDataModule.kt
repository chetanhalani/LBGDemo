package com.lbgdemo.data.di

import com.lbgdemo.data.db.ArtistDao
import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.local.ArtistLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {

    @Singleton
    @Provides
    fun provideArtistLocalDataSource(artistDao: ArtistDao): ArtistLocalDataSource {
        return ArtistLocalDataSourceImpl(artistDao)
    }


}