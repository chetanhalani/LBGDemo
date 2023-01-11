package com.lbgdemo.data.di

import android.app.Application
import androidx.room.Room
import com.lbgdemo.data.db.ArtistDao
import com.lbgdemo.data.db.LBGDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideLBGDataBase(app: Application): LBGDatabase {
        return Room.databaseBuilder(app, LBGDatabase::class.java, "lbgdatabase")
            .build()
    }


    @Singleton
    @Provides
    fun provideArtistDao(lbgDatabase: LBGDatabase): ArtistDao {
        return lbgDatabase.artistDao()
    }

}