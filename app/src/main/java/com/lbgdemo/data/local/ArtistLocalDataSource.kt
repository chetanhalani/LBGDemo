package com.lbgdemo.data.local

import com.lbgdemo.data.model.Artist

interface ArtistLocalDataSource {
  suspend fun getArtists():List<Artist>
  suspend fun saveArtists(artists:List<Artist>)
  suspend fun clearArtists()
}