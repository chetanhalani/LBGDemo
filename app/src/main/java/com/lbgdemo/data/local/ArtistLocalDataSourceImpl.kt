package com.lbgdemo.data.local

import com.lbgdemo.data.db.ArtistDao
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.local.ArtistLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArtistLocalDataSourceImpl(private val artistDao:ArtistDao):
    ArtistLocalDataSource {
    override suspend fun getArtists(): List<Artist> {
       return artistDao.getArtists()
    }

    override suspend fun saveArtists(artists: List<Artist>) {
        CoroutineScope(Dispatchers.IO).launch {
            artistDao.saveArtists(artists)
        }
    }

    override suspend fun clearArtists() {
       CoroutineScope(Dispatchers.IO).launch {
           artistDao.deleteAllArtists()
       }
    }
}