package com.lbgdemo.data.local

import com.lbgdemo.data.db.ArtistDao
import com.lbgdemo.data.model.Artist

internal class ArtistLocalDataSourceImpl(private val artistDao: ArtistDao) :
    ArtistLocalDataSource {
    override suspend fun getArtists(): List<Artist> {
        return artistDao.getArtists()
    }

    /**
     * Method to get artists from local database
     */
    override suspend fun saveArtists(artists: List<Artist>) {
        artistDao.saveArtists(artists)
    }

    /**
     * Method to clear artists from local database
     */
    override suspend fun clearArtists() {
        artistDao.deleteAllArtists()
    }
}