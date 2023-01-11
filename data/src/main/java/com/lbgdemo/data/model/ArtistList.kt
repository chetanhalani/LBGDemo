package com.lbgdemo.data.model

import com.google.gson.annotations.SerializedName

data class ArtistList(
    @SerializedName("data")
    val artists: List<Artist>
)

sealed class DataResponse<out T> {
    class Success<out T>(val data: T) : DataResponse<T>()
    class Error(val msg: String) : DataResponse<Nothing>()
}