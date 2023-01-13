package com.lbgdemo.data.respository

import com.lbgdemo.data.local.ArtistLocalDataSource
import com.lbgdemo.data.model.toDataResponseArtistListEntity
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse
import com.lbgdemo.domain.repository.ArtistRepository

internal class ArtistRepositoryImpl(
    private val artistRemoteDatasource: ArtistRemoteDatasource,
    private val artistLocalDataSource: ArtistLocalDataSource
) : ArtistRepository {

    private lateinit var artistResponseData: DataResponse<ArtistListEntity>

    /**
     * Method to get artist from remote or local database
     */
    override suspend fun getArtists(): DataResponse<ArtistListEntity> {
        if (!this::artistResponseData.isInitialized || artistResponseData is DataResponse.Error)
            artistResponseData = fetchArtists()
        return artistResponseData
    }

    /**
     * Method to get artists from local database if not present then from remote database
     */
    private suspend fun fetchArtists(): DataResponse<ArtistListEntity> {
        try {
            val list = artistLocalDataSource.getArtists()
            if (list.isNotEmpty()) {
                return list.toDataResponseArtistListEntity()
            }
            val artistListRespose = artistRemoteDatasource.getArtists()
            if (artistListRespose is DataResponse.Success) {
                if (artistListRespose.data.artists.isNotEmpty()) {
                    artistLocalDataSource.saveArtists(
                        artistListRespose.data.artists
                    )
                }
            }
            return artistListRespose.toDataResponseArtistListEntity()
        } catch (exception: Exception) {
            return DataResponse.Error(exception.localizedMessage ?: "unknown exception")
        }
    }

}