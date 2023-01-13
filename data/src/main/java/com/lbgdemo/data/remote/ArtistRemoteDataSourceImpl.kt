package com.lbgdemo.data.remote

import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.domain.model.DataResponse

internal class ArtistRemoteDataSourceImpl(
    private val lbgService: LBGDemoService,
    private val fields: String
) : ArtistRemoteDatasource() {

    /**
     * Method to get artists from remote source
     */
    override suspend fun getArtists()
            : DataResponse<ArtistList> = getResponse { lbgService.getArtists(fields) }

}
