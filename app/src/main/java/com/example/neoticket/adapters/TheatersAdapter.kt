import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.neoticket.Utils.DateUtils
import com.example.neoticket.databinding.TheaterCardBinding
import com.example.neoticket.model.Theater

class TheatersAdapter(private var items: List<Theater>) :
    RecyclerView.Adapter<TheatersAdapter.TheaterViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onCinemaItemClick(item: Theater)
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

    fun updateData(newList: List<Theater>) {
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
                    itemClickListener?.onCinemaItemClick(clickedItem)
                }
            }
        }

        fun bind(item: Theater) {
            binding.itemTitle.text = item.title
            binding.itemPlace.text = item.place.name
            val formattedDate = DateUtils.formatRussianDate(item.theater_date, "yyyy-MM-dd")
            binding.itemDate.text = formattedDate
            Glide.with(binding.itemImg.context)
                .load(item.detail_images[0].image)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(binding.itemImg)
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<Theater>,
        private val newList: List<Theater>
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
