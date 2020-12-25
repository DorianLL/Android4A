package com.example.android4a.presentation.logged

import GameRecyclerAdapter
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android4a.R
import com.example.android4a.presentation.main.DISCONNECT
import com.example.android4a.presentation.main.LOGGED_ACIVITY
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.logged_activity.*
import org.koin.android.ext.android.inject


class LoggedActivity : AppCompatActivity() {

    /**
     * The [ViewModel] of the activity
     */
    private val loggedViewModel: LoggedViewModel by inject()

    /**
     * The [LinearLayoutManager] of the [RecyclerView]
     */
    private lateinit var linearLayoutManager: LinearLayoutManager

    /**
     * Function called while the [LoggedActivity] is launched.
     * - Init the layout
     * - Get the games
     * - The listeners of the search button ([initListeners])
     * - The observers of the liveData ([initObservers])
     *
     * @return [Void]
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        getGames()
        initObservers()
        initListeners()
    }

    /**
     * Init the layout
     *
     * @return [Void]
     */
    private fun initView() {
        setContentView(R.layout.logged_activity)
        linearLayoutManager = LinearLayoutManager(this)
        recycler_view.layoutManager = linearLayoutManager
    }

    /**
     * Init the menu that contains Log Out menu
     *
     * @param menu the menu of the layout
     * @return [Void]
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_logged, menu)
        return true
    }

    /**
     * Callback when an item is clicked on the menu
     *
     * @param item the item selected by the user
     * @return [Void]
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout_settings -> {
                val intent = Intent()
                intent.putExtra("action", DISCONNECT)
                setResult(LOGGED_ACIVITY, intent)
                finish()
            }
        }
        return true
    }

    /**
     * Init the listeners of the app
     *
     * @return [Void]
     */
    private fun initListeners() {
        search_button.setOnClickListener {
            if (search_input.text?.isNotEmpty()!!) {
                if (verifyAvailableNetwork()) {
                    network()
                    progressBarHolder.visibility = View.VISIBLE
                    loggedViewModel.getGamesWithFilter(search_input.text.toString())
                } else {
                    noNetwork()
                }
            } else {
                showErrorDialog()
            }
        }
    }

    /**
     * Show the error search dialog in case of empty search
     *
     * @return [Void]
     */
    private fun showErrorDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle(getString(R.string.search_dialog_error))
            .setMessage(getString(R.string.error_search))
            .setPositiveButton(resources.getString(R.string.accept)) { dialog, _ -> dialog.cancel() }
            .show()
    }

    /**
     * Init the obervers of the app
     *
     * @return [Void]
     */
    private fun initObservers() {
        loggedViewModel.gamesLiveData.observe(this, Observer {
            recycler_view.apply {
                progressBarHolder.visibility = View.GONE
                layoutManager = LinearLayoutManager(applicationContext)
                adapter = GameRecyclerAdapter(context, loggedViewModel.gamesLiveData.value!!)
            }
        })
    }

    /**
     * Request the games by calling [LoggedViewModel.getGames]
     */
    private fun getGames() {
        if (verifyAvailableNetwork()) {
            network()
            progressBarHolder.visibility = View.VISIBLE
            loggedViewModel.getGames()
        } else {
            noNetwork()
        }
    }

    /**
     * CallBack when no network detected
     *
     * @return [Void]
     */
    private fun noNetwork() {
        no_network.visibility = View.VISIBLE
        recycler_view.visibility = View.GONE
        progressBarHolder.visibility = View.GONE
    }

    /**
     * CallBack when network detected
     *
     * @return [Void]
     */
    private fun network() {
        no_network.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE
        progressBarHolder.visibility = View.VISIBLE
    }

    /**
     * Check if network is ready
     *
     * @return [Boolean]
     */
    private fun verifyAvailableNetwork(): Boolean {
        val connectivityManager =
            this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    /**
     * Block the return button to go on the first activity
     */
    override fun onBackPressed() {
        // do nothing
    }
}
