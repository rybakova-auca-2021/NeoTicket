import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.neoticket.databinding.MovieCardBinding
import com.example.neoticket.model.Movie

class MoviesAdapter(private var items: List<Movie>) :
    RecyclerView.Adapter<MoviesAdapter.CinemaViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onCinemaItemClick(item: Movie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieCardBinding.inflate(inflater, parent, false)

        return CinemaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newList: List<Movie>) {
        val diffResult = DiffUtil.calculateDiff(
            DisplayableItemDiffCallback(
                items,
                newList
            )
        )
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CinemaViewHolder(private val binding: MovieCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = items[position]
                    itemClickListener?.onCinemaItemClick(clickedItem)
                }
            }
        }

        fun bind(item: Movie) {
            binding.itemTitle.text = item.title
            binding.itemRating.text = item.rating.toString()
            binding.itemAge.text = "Возрастной рейтинг: ${item.age_limit}"
            Glide.with(binding.itemImg.context)
                .load(item.image)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(binding.itemImg)
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<Movie>,
        private val newList: List<Movie>
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
