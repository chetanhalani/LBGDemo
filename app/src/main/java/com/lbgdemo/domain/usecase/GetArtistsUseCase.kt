package com.lbgdemo.domain.usecase

import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.respository.ArtistRepository
import javax.inject.Inject

class GetArtistsUseCase @Inject constructor(private val artistRepository: ArtistRepository) {
    suspend fun execute():List<Artist>? = artistRepository.getArtists()

}