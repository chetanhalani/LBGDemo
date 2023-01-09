package com.lbgdemo.data.respository

import com.lbgdemo.data.model.Artist

interface ArtistRepository {
    suspend fun getArtists():List<Artist>?
}