package com.lbgdemo.data.remote

import com.lbgdemo.data.model.ArtistList
import retrofit2.Response

interface ArtistRemoteDatasource {
   suspend fun getArtists(): Response<ArtistList>
}