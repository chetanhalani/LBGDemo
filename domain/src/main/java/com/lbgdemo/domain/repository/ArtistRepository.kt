package com.lbgdemo.domain.repository

import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse

interface ArtistRepository {
    suspend fun getArtists(): DataResponse<ArtistListEntity>
}