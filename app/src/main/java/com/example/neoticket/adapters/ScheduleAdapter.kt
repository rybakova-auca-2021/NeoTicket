import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoticket.R
import com.example.neoticket.adapters.ShowTimeAdapter
import com.example.neoticket.databinding.CardCinemaScheduleBinding
import com.example.neoticket.databinding.TheaterCardBinding
import com.example.neoticket.model.ShowTime
import com.example.neoticket.model.StartTime
import com.example.neoticket.model.Theater
import com.example.neoticket.view.main.movie.ScheduleFragment

class ScheduleAdapter(
    private var items: List<ShowTime>,
    private val navigateToTicketPurchasePage: (StartTime, ShowTime) -> Unit
) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: ShowTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardCinemaScheduleBinding.inflate(inflater, parent, false)

        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newList: List<ShowTime>) {
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

    inner class ScheduleViewHolder(private val binding: CardCinemaScheduleBinding) :
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

        fun bind(item: ShowTime) {
            binding.cinemaName.text = item.cinema.name
            binding.cinemaAddress.text = item.cinema.address
//            val showTimesAdapter = ShowTimeAdapter(item.start_times) { startTime ->
//                fragment.navigateToTicketPurchasePage(startTime, item)
//            }
            val showTimesAdapter = ShowTimeAdapter(item.start_times) { startTime ->
                navigateToTicketPurchasePage(startTime, item)
            }
            binding.rvShowTime.layoutManager = GridLayoutManager(binding.root.context, 4)
            binding.rvShowTime.adapter = showTimesAdapter
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<ShowTime>,
        private val newList: List<ShowTime>
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
