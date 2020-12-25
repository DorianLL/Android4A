package com.example.android4a.api

import androidx.lifecycle.MutableLiveData
import com.example.android4a.api.model.Game
import com.example.android4a.api.model.GameAPIResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * [String] Api key of giantbomb
 */
const val API_KEY: String = "5a3fd29fd0d27cbdb5d7122b46c818e8a010d7da"

/**
 * [String] Return format of the API
 */
const val FORMAT: String = "json"

/**
 * [String] Fields list of the API
 */
const val FIELD_LIST: String = "name,original_release_date,image,deck,site_detail_url"

/**
 * [String] On success error return from API
 */
const val SUCCESS_REQUEST_CODE: String = "OK"

/**
 * [Int] success http code
 */
const val SUCCESS_HTTP_CODE: Int = 200

/**
 * [String] base url from the API
 */
const val BASE_URL = "https://www.giantbomb.com/"

/**
 * Controller that executes [retrofit] request to the API
 *
 * @param gameLiveData : [MutableLiveData] of [List] of [Game]
 */
class GameAPIController(private val gameLiveData: MutableLiveData<List<Game>>) :
    Callback<GameAPIResponse> {

    /**
     * Instance of [Gson] to parse the results into [GameAPIResponse]
     */
    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    /**
     * Instance of [Retrofit] to executes the requests
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    /**
     * Instance of [GameAPIService] that contains description of the API
     */
    private val gameAPI: GameAPIService = retrofit.create(GameAPIService::class.java)

    /**
     * Function to add get games task
     *
     * @return [Void]
     */
    fun getGames() {
        val call: Call<GameAPIResponse> =
            gameAPI.getGames(API_KEY, FORMAT, FIELD_LIST)
        call.enqueue(this)
    }

    /**
     * Function to add get games task with search param
     *
     * @return [Void]
     */
    fun getGamesWithSearch(
        query: String
    ) {
        val call: Call<GameAPIResponse> =
            gameAPI.getGamesWithSearch(API_KEY, FORMAT, FIELD_LIST, "name:$query")
        call.enqueue(this)
    }

    /**
     * Function called on response
     *
     * @return [Void]
     */
    override fun onResponse(
        call: Call<GameAPIResponse>,
        response: Response<GameAPIResponse>
    ) {
        if (response.isSuccessful && response.code() == SUCCESS_HTTP_CODE) {
            val apiResponse: GameAPIResponse = response.body()!!
            if (apiResponse.error == SUCCESS_REQUEST_CODE)
                gameLiveData.postValue(apiResponse.results)
        } else {
            gameLiveData.postValue(emptyList())
        }
    }

    /**
     * Function called on failure request
     *
     * @return [Void]
     */
    override fun onFailure(
        call: Call<GameAPIResponse>,
        t: Throwable
    ) {
        gameLiveData.postValue(emptyList())
    }

}