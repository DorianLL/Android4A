package com.example.android4a.api.model

import com.google.gson.annotations.SerializedName

/**
 * Format of image field of a [Game]
 */
data class GameImages(
    @SerializedName("screen_url")
    val screen_url: String? = null
)