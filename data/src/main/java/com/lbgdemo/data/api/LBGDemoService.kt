package com.lbgdemo.data.api

import com.lbgdemo.data.model.ArtistList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LBGDemoService {

    @GET("api/v1/artworks")
    suspend fun getArtists(
        @Query(
            "fields"
        ) fields: String
    ): Response<ArtistList>

}