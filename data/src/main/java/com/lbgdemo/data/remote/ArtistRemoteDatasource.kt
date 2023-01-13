package com.lbgdemo.data.remote

import com.lbgdemo.data.model.ArtistList
import com.lbgdemo.data.remote.base.BaseRemoteDataSource
import com.lbgdemo.domain.model.DataResponse

abstract class ArtistRemoteDatasource : BaseRemoteDataSource() {
    abstract suspend fun getArtists(): DataResponse<ArtistList>
}