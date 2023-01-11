package com.lbgdemo.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "artists")
data class Artist(
    @PrimaryKey
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String?,
    @SerializedName("artist_display")
    val artistDisplay: String?,
    @SerializedName("image_id")
    val imageId: String?
)