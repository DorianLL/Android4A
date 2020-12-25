import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android4a.api.model.Game
import com.example.android4a.presentation.logged.GameViewHolder

/**
 * The adapter of the recycler view
 *
 * @param context of the [LoggedActivity]
 * @param dataSet [List] of [Game] to show
 */
class GameRecyclerAdapter(context: Context, private val dataSet: List<Game>) :
    RecyclerView.Adapter<GameViewHolder>() {

    /**
     * [context] of the [LoggedActivity]
     */
    private var context: Context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return GameViewHolder(inflater, parent, context)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game: Game = dataSet[position]
        holder.bind(game)
    }

    override fun getItemCount(): Int = dataSet.size

}
