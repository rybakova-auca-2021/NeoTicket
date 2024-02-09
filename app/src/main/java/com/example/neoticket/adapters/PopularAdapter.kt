import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.neoticket.databinding.TheaterCardBinding
import com.example.neoticket.model.Popular
import com.example.neoticket.viewModel.cinema.CinemaDetailViewModel

class PopularAdapter(private var items: List<Popular>) :
    RecyclerView.Adapter<PopularAdapter.TheaterViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: Popular)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TheaterCardBinding.inflate(inflater, parent, false)

        return TheaterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newList: List<Popular>) {
        val diffResult = DiffUtil.calculateDiff(
            DisplayableItemDiffCallback(
                items,
                newList
            )
        )
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class TheaterViewHolder(private val binding: TheaterCardBinding) :
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

        @SuppressLint("SetTextI18n")
        fun bind(item: Popular) {
            binding.itemTitle.text = item.title
            if (item.place != null) {
                binding.itemPlace.text = item.place.name
            } else {
                binding.itemPlace.text = "В ${item.cinema_count} кинотеатрах"
            }
            binding.itemDate.text = item.release_date
            Glide.with(binding.itemImg.context)
                .load(item.image)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(binding.itemImg)
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<Popular>,
        private val newList: List<Popular>
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
