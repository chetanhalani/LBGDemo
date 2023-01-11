package com.lbgdemo.data.respository

import android.util.Log
import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.model.Artist
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.remote.ArtistRemoteDatasource

class ArtistRepositoryImpl(
    private val artistRemoteDatasource: ArtistRemoteDatasource,
    private val artistLocalDataSource: ArtistLocalDataSource
) : ArtistRepository {

    private lateinit var artistResponseData: DataResponse<ArtistList>

    /**
     * Method to get artist from remote or local database
     */
    override suspend fun getArtists(): DataResponse<ArtistList> {
        if (!this::artistResponseData.isInitialized || artistResponseData is DataResponse.Error)
            artistResponseData = fetchArtists()
        return artistResponseData
    }

    /**
     * Method to get artists from local database if not present then from remote database
     */
    private suspend fun fetchArtists(): DataResponse<ArtistList> {
        try {
            val list = artistLocalDataSource.getArtists()
            if(list.isNotEmpty()) {
                return DataResponse.Success(ArtistList(list))
            }
            val artistListRespose = artistRemoteDatasource.getArtists()
            if(artistListRespose is DataResponse.Success) {
                if(artistListRespose.data.artists.isNotEmpty()) artistLocalDataSource.saveArtists(artistListRespose.data.artists)
            }
            return artistListRespose
        } catch (exception: Exception) {
            return DataResponse.Error(exception.localizedMessage.toString())
        }
    }


}