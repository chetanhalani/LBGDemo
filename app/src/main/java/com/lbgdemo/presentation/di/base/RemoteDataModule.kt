package com.lbgdemo.presentation.di.base

import com.lbgdemo.BuildConfig
import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import com.lbgdemo.data.remote.ArtistRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule() {

    @Singleton
    @Provides
    fun provideArtistRemoteDataSource(lbgDemoService: LBGDemoService): ArtistRemoteDatasource {
        return ArtistRemoteDataSourceImpl(
            lbgDemoService, BuildConfig.QUERY_FIELDS
        )
    }


}