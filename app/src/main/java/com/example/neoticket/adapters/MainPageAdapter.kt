import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoticket.databinding.MainCardBinding
import com.example.neoticket.model.Concert
import com.example.neoticket.model.Movie
import com.example.neoticket.model.Theater

interface DisplayableItem {
    val viewType: Int
}

data class CinemaItem(val cinema: Movie) : DisplayableItem {
    override val viewType: Int = 1
}

data class ConcertItem(val concert: Concert) : DisplayableItem {
    override val viewType: Int = 2
}

data class TheaterItem(val theater: Theater) : DisplayableItem {
    override val viewType: Int = 3
}

class MainPageAdapter(private var items: List<DisplayableItem>) :
    RecyclerView.Adapter<MainPageAdapter.MainPageViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: MainPageAdapter.OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onCinemaItemClick(item: CinemaItem)
        fun onConcertItemClick(item: ConcertItem)
        fun onTheaterItemClick(item: TheaterItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MainCardBinding.inflate(inflater, parent, false)

        return MainPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainPageViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newList: List<DisplayableItem>) {
        val diffResult = DiffUtil.calculateDiff(
            DisplayableItemDiffCallback(
                items,
                newList
            )
        )
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class MainPageViewHolder(private val binding: MainCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    when (val clickedItem = items[position]) {
                        is CinemaItem -> itemClickListener?.onCinemaItemClick(clickedItem)
                        is ConcertItem -> itemClickListener?.onConcertItemClick(clickedItem)
                        is TheaterItem -> itemClickListener?.onTheaterItemClick(clickedItem)
                    }
                }
            }
        }

        fun bind(item: DisplayableItem) {
            when (item) {
                is CinemaItem -> {
                    val cinema = item.cinema
                    binding.itemTitle.text = cinema.title
                    binding.itemPlace.text = "В 4 кинотеатрах"
                    binding.itemPrice.text = "От 450с"
                    Glide.with(binding.itemImg.context)
                        .load(cinema.image)
                        .into(binding.itemImg)
                }
                is ConcertItem -> {
                    val concert = item.concert
                    binding.itemTitle.text = concert.title
                    binding.itemPlace.text = concert.place.toString()
                    binding.itemPrice.text = "От 450с"
                    Glide.with(binding.itemImg.context)
                        .load(concert.detailImages[1])
                        .into(binding.itemImg)
                }
                is TheaterItem -> {
                    val theater = item.theater
                    binding.itemTitle.text = theater.title
                    binding.itemPlace.text = theater.place.toString()
                    binding.itemPrice.text = "От 800с"
                    Glide.with(binding.itemImg.context)
                        .load(theater.detail_images[1])
                        .into(binding.itemImg)
                }
            }
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<DisplayableItem>,
        private val newList: List<DisplayableItem>
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
