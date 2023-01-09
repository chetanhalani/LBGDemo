package com.lbgdemo.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.lbgdemo.data.model.Artist

@Dao
interface ArtistDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveArtists(artists : List<Artist>)

    @Query("DELETE FROM artists")
    suspend fun deleteAllArtists()

    @Query("SELECT * FROM artists")
    suspend fun getArtists():List<Artist>
}