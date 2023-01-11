package com.lbgdemo.data.respository

import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse

interface ArtistRepository {
    suspend fun getArtists(): DataResponse<ArtistList>
}