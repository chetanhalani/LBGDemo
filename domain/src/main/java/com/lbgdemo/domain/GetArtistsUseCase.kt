package com.lbgdemo.domain

import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.respository.ArtistRepository
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(private val artistRepository: ArtistRepository) {

    /**
     * use case to write business logic if needed for getting artists
     */
    suspend fun execute(): DataResponse<ArtistList> = artistRepository.getArtists()

}