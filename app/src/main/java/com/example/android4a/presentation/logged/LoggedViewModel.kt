package com.example.android4a.presentation.logged

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android4a.api.GameAPIController
import com.example.android4a.api.model.Game
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * The [ViewModel] of [LoggedActivity]
 */
class LoggedViewModel : ViewModel() {

    /**
     * [MutableLiveData] of a [List] of [Game]
     */
    internal val gamesLiveData: MutableLiveData<List<Game>> = MutableLiveData()

    /**
     * Controller of the Games's API
     */
    private val gameApiController = GameAPIController(gamesLiveData)

    /**
     * Function to get the games from the controller
     *
     * @return [Void]
     */
    fun getGames() {
        viewModelScope.launch(Dispatchers.IO) {
            gameApiController.getGames()
        }
    }

    /**
     * Function to get the games from the controller with a search
     *
     * @param search the search
     * @return [Void]
     */
    fun getGamesWithFilter(search: String) {
        viewModelScope.launch(Dispatchers.IO) {
            gameApiController.getGamesWithSearch(search)
        }
    }
}