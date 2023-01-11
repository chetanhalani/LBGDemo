package com.lbgdemo.data.di

import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import com.lbgdemo.data.respository.ArtistRepository
import com.lbgdemo.data.respository.ArtistRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideArtistRepository(
        artistRemoteDatasource: ArtistRemoteDatasource,
        artistLocalDataSource: ArtistLocalDataSource
    ): ArtistRepository {
        return ArtistRepositoryImpl(
            artistRemoteDatasource,
            artistLocalDataSource
        )
    }

}