package com.lbgdemo.data.remote

import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.model.DataResponse
import com.lbgdemo.data.remote.base.BaseRemoteDataSource
import retrofit2.Response

abstract class ArtistRemoteDatasource : BaseRemoteDataSource(){
    abstract suspend fun getArtists(): DataResponse<ArtistList>
}