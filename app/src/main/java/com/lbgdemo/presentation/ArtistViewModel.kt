package com.lbgdemo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.lbgdemo.domain.GetArtistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getArtistsUseCase: GetArtistsUseCase
) : ViewModel() {

    /**
     * Method to get data of artist
     */
    fun getArtists() = liveData {
        val artistList = getArtistsUseCase.execute()
        emit(artistList)
    }
}