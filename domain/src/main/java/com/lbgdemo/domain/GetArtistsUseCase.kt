package com.lbgdemo.domain

import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse


interface GetArtistsUseCase {
    /**
     * method to fetch artists
     */
    suspend fun execute(): DataResponse<ArtistListEntity>
}