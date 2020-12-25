package com.example.android4a.api.model

import com.google.gson.annotations.SerializedName

/**
 * Format of the response API
 */
data class GameAPIResponse(
    @SerializedName("error")
    val error: String? = null,

    @SerializedName("results")
    val results: MutableList<Game>? = null
)