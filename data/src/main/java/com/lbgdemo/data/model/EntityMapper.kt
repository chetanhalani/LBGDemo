package com.lbgdemo.data.model

import com.lbgdemo.domain.model.ArtistEntity
import com.lbgdemo.domain.model.ArtistListEntity
import com.lbgdemo.domain.model.DataResponse

fun List<Artist>.toDataResponseArtistListEntity(): DataResponse<ArtistListEntity> {
    return DataResponse.Success(ArtistListEntity(this.map { it.toArtistEntity() }))
}

fun DataResponse<ArtistList>.toDataResponseArtistListEntity(): DataResponse<ArtistListEntity> {
    return when(this) {
        is DataResponse.Success -> DataResponse.Success(this.data.toArtistListEntity())
        is DataResponse.Error -> DataResponse.Error(this.msg)
    }
}

fun ArtistList.toArtistListEntity(): ArtistListEntity {
    return ArtistListEntity(
        artistList = artists.map { it.toArtistEntity() }
    )
}

fun Artist.toArtistEntity(): ArtistEntity {
    return ArtistEntity(
        artistId = id,
        artistTitle = title,
        artistDisplayName = artistDisplay,
        artistImageId = imageId
    )
}