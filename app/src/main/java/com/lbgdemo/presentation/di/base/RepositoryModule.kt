package com.lbgdemo.presentation.di.base

import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import com.lbgdemo.data.respository.ArtistRepositoryImpl
import com.lbgdemo.data.respository.ArtistRepository
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