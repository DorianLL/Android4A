package com.example.android4a.api.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Format of a Game contained in [GameAPIResponse]
 */
data class Game(
    @SerializedName("name")
    val name: String? = null,

    @SerializedName("original_release_date")
    val original_release_date: String? = null,

    @SerializedName("deck")
    val deck: String? = null,

    @SerializedName("image")
    val image: GameImages? = null,

    @SerializedName("site_detail_url")
    val site_detail_url: String? = null

) : Serializable
