package com.lbgdemo.domain.di

import com.lbgdemo.domain.GetArtistsUseCase
import com.lbgdemo.domain.GetArtistsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    internal abstract fun bindGetArtistsUseCase(getArtistsUseCaseImpl: GetArtistsUseCaseImpl): GetArtistsUseCase

}