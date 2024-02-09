import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.neoticket.R
import com.example.neoticket.adapters.ShowTimeAdapter
import com.example.neoticket.databinding.CardCinemaScheduleBinding
import com.example.neoticket.databinding.CardMovieScheduleBinding
import com.example.neoticket.databinding.TheaterCardBinding
import com.example.neoticket.model.CinemaShowTime
import com.example.neoticket.model.MovieDetail
import com.example.neoticket.model.ShowTime
import com.example.neoticket.model.StartTime
import com.example.neoticket.model.Theater
import com.example.neoticket.view.main.movie.ScheduleFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleMovieAdapter(
    private var items: List<CinemaShowTime>,
    private val navigateToTicketPurchasePage: (StartTime, CinemaShowTime) -> Unit
) : RecyclerView.Adapter<ScheduleMovieAdapter.ScheduleViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: CinemaShowTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardMovieScheduleBinding.inflate(inflater, parent, false)

        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newList: List<CinemaShowTime>) {
        val diffResult = DiffUtil.calculateDiff(
            DisplayableItemDiffCallback(
                items,
                newList
            )
        )
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    fun filterByDateTime(startDate: String) {
        val filteredList = items.filter { showTime ->
            showTime.start_date == startDate
        }

        updateData(filteredList)
    }

    inner class ScheduleViewHolder(private val binding: CardMovieScheduleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = items[position]
                    itemClickListener?.onItemClick(clickedItem)
                }
            }
        }

        fun bind(item: CinemaShowTime) {
            binding.movieTitle.text = item.movie_title
            binding.movieAgeLimit.text = item.movie_age_limit.toString()
            binding.movieRatimg.text = item.movie_rating.toString()
            Glide.with(binding.movieImg.context).load(item.movie_image).transform(CenterCrop(), RoundedCorners(20)).into(binding.movieImg)

            val showTimesAdapter = ShowTimeAdapter(item.start_times) { startTime ->
                navigateToTicketPurchasePage(startTime, item)
            }
            binding.rvShowTime.layoutManager = GridLayoutManager(binding.root.context, 4)
            binding.rvShowTime.adapter = showTimesAdapter
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<CinemaShowTime>,
        private val newList: List<CinemaShowTime>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
