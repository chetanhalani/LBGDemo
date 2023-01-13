package com.lbgdemo.artist

import androidx.lifecycle.*
import com.lbgdemo.domain.GetArtistsUseCase
import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArtistViewModel @Inject constructor(
    private val getArtistsUseCase: GetArtistsUseCase
) : ViewModel() {

    private val _artistList = MutableLiveData<DataResponse<ArtistListEntity>>()
    val artistList: LiveData<DataResponse<ArtistListEntity>> = _artistList

    init {
        getArtists()
    }

    /**
     * Method to get data of artist
     */
    fun getArtists() {
        viewModelScope.launch {
            _artistList.value = getArtistsUseCase.execute()
        }
    }
}