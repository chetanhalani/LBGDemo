package com.lbgdemo.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lbgdemo.data.model.Artist

@Database(
    entities = [Artist::class],
    version = 1,
    exportSchema = false
)
abstract class LBGDatabase : RoomDatabase() {
    abstract fun artistDao(): ArtistDao
}