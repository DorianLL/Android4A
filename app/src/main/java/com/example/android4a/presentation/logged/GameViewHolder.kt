package com.example.android4a.presentation.logged

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android4a.R
import com.example.android4a.api.model.Game

/**
 * [RecyclerView] holder
 */
class GameViewHolder(inflater: LayoutInflater, parent: ViewGroup, context: Context) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.item_game_list, parent, false)) {

    /**
     * [ImageView] of the game
     */
    private var imageView: ImageView? = null

    /**
     * [TextView] title of the game
     */
    private var title: TextView? = null

    /**
     * [TextView] desc of the game
     */
    private var desc: TextView? = null

    /**
     * [TextView] date of release the game
     */
    private var date: TextView? = null

    /**
     * [Button] to show more
     */
    private var more_info_button: Button? = null

    /**
     * [Context] of the application
     */
    private var context: Context = context


    /**
     * Init the fields to their corresponding layout's ids
     */
    init {
        imageView = itemView.findViewById(R.id.image_view)
        title = itemView.findViewById(R.id.title)
        desc = itemView.findViewById(R.id.desc)
        date = itemView.findViewById(R.id.date)
        more_info_button = itemView.findViewById(R.id.more_info_button)
    }

    /**
     * Bind a [Game] values to the item's layout and execute Glide to load in an asynchronous the images
     *
     * @param game the [Game] to bind
     * @return [Void]
     */
    fun bind(game: Game) {
        Glide.with(context).load(game.image?.screen_url).into(imageView)
        title?.text = game.name
        desc?.text = game.deck
        date?.text = game.original_release_date
        more_info_button?.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(game.site_detail_url))
            context.startActivity(browserIntent)
        }
    }

}