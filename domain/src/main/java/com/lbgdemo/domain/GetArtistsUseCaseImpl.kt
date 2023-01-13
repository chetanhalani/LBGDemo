package com.lbgdemo.domain

import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse
import com.lbgdemo.domain.repository.ArtistRepository
import javax.inject.Inject


class GetArtistsUseCaseImpl @Inject constructor(private val artistRepository: ArtistRepository) : GetArtistsUseCase {

    /**
     * use case to write business logic if needed for getting artists
     */
    override suspend fun execute(): DataResponse<ArtistListEntity> = artistRepository.getArtists()

}