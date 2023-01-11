package com.lbgdemo.data.di

import com.lbgdemo.data.BuildConfig
import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.remote.ArtistRemoteDataSourceImpl
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideArtistRemoteDataSource(lbgDemoService: LBGDemoService): ArtistRemoteDatasource {
        return ArtistRemoteDataSourceImpl(
            lbgDemoService, BuildConfig.QUERY_FIELDS
        )
    }


}