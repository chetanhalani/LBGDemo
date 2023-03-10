package com.lbgdemo.data.di

import com.lbgdemo.data.BuildConfig
import com.lbgdemo.data.api.LBGDemoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    @Singleton
    @Provides
    fun provideLBGDemoService(retrofit: Retrofit): LBGDemoService {
        return retrofit.create(LBGDemoService::class.java)
    }

}