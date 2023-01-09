package com.lbgdemo.data.remote

import com.lbgdemo.data.api.LBGDemoService
import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.remote.ArtistRemoteDatasource
import retrofit2.Response

class ArtistRemoteDataSourceImpl(
    private val lbgService: LBGDemoService,
    private val fields:String
): ArtistRemoteDatasource {
    override suspend fun getArtists()
            : Response<ArtistList> = lbgService.getArtists(fields)

}
