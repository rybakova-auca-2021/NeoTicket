import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoticket.databinding.CinemaCardBinding
import com.example.neoticket.databinding.ImageCardBinding
import com.example.neoticket.model.Cinema
import com.example.neoticket.model.MovieDetailImage

class DetailImageAdapter(private var items: List<MovieDetailImage>) :
    RecyclerView.Adapter<DetailImageAdapter.ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageCardBinding.inflate(inflater, parent, false)

        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newList: List<MovieDetailImage>) {
        val diffResult = DiffUtil.calculateDiff(
            DisplayableItemDiffCallback(
                items,
                newList
            )
        )
        items = newList
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ImageViewHolder(private val binding: ImageCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieDetailImage) {
            Glide.with(binding.itemImg.context)
                .load(item.image)
                .into(binding.itemImg)
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<MovieDetailImage>,
        private val newList: List<MovieDetailImage>
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
