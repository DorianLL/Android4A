package com.example.android4a.api

import com.example.android4a.api.model.GameAPIResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface of the description of the API
 */
interface GameAPIService {

    /**
     * Add get games task
     *
     * @param api_key [String] the api key
     * @param format [String] the format of the return
     * @param field_list [String] the field returns of the api
     *
     * @return [Call] of [GameAPIResponse]
     */
    @GET("api/games/")
    fun getGames(
        @Query("api_key") api_key: String?,
        @Query("format") format: String?,
        @Query("field_list") field_list: String?
    ): Call<GameAPIResponse>

    /**
     * Add get games task
     *
     * @param api_key [String] the api key
     * @param format [String] the format of the return
     * @param field_list [String] the field returns of the api
     * @param filter [String] to filter the result
     *
     * @return [Call] of [GameAPIResponse]
     */
    @GET("api/games/")
    fun getGamesWithSearch(
        @Query("api_key") api_key: String?,
        @Query("format") format: String?,
        @Query("field_list") field_list: String?,
        @Query("filter") filter: String?
    ): Call<GameAPIResponse>

}