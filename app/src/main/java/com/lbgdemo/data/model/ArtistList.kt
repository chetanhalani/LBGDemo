package com.lbgdemo.data.model

import com.google.gson.annotations.SerializedName

data class ArtistList(
    @SerializedName("data")
    val artists: List<Artist>
)