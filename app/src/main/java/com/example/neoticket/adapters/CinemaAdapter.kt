import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.neoticket.databinding.CinemaCardBinding
import com.example.neoticket.model.Cinema

class CinemaAdapter(private var items: List<Cinema>) :
    RecyclerView.Adapter<CinemaAdapter.CinemaViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onCinemaItemClick(item: Cinema)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CinemaCardBinding.inflate(inflater, parent, false)

        return CinemaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CinemaViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newList: List<Cinema>) {
        val diffResult = DiffUtil.calculateDiff(
            DisplayableItemDiffCallback(
                items,
                newList
            )
        )
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CinemaViewHolder(private val binding: CinemaCardBinding) :
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

        fun bind(item: Cinema) {
            binding.itemTitle.text = item.name
            binding.itemAddress.text = item.address
            Glide.with(binding.itemImg.context)
                .load(item.image)
                .transform(CenterCrop(), RoundedCorners(20))
                .into(binding.itemImg)
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<Cinema>,
        private val newList: List<Cinema>
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
