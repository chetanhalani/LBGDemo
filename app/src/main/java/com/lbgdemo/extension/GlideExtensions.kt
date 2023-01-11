package com.lbgdemo.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.lbgdemo.Constants
import com.lbgdemo.data.BuildConfig

/**
 * Extension function to set thumbnail image into imageview.
 * @param imageId of the artist
 */
fun ImageView.setThumbnailImage(
    imageId: String?
) {
    imageId?.let {
        Glide.with(context)
            .load(it.getThumbImageUrlFromImageId())
            .into(this)

    }
}

/**
 * Extension function to get thumbnail image from imageId of  the artist.
 */
fun String.getThumbImageUrlFromImageId(): String {
    return BuildConfig.IMAGE_URL + Constants.IMAGE_ID_PREFIX + this + Constants.IMAGE_ID_SUFFIX
}