import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.CardScheduleTheaterBinding
import com.example.neoticket.model.TheaterShowTime
import java.text.SimpleDateFormat
import java.util.Locale

class TheaterScheduleAdapter(
    private var items: List<TheaterShowTime>
) : RecyclerView.Adapter<TheaterScheduleAdapter.ScheduleViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: TheaterShowTime)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardScheduleTheaterBinding.inflate(inflater, parent, false)

        return ScheduleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun updateData(newList: List<TheaterShowTime>) {
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

    inner class ScheduleViewHolder(private val binding: CardScheduleTheaterBinding) :
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

        fun bind(item: TheaterShowTime) {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
            val dayOfWeekFormat = SimpleDateFormat("EEEE", Locale.getDefault())
            val startDate = inputFormat.parse(item.start_date)
            val formattedDate = outputFormat.format(startDate)
            val dayOfWeek = dayOfWeekFormat.format(startDate)

            binding.textDate.text = formattedDate
            binding.textDay.text = dayOfWeek
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<TheaterShowTime>,
        private val newList: List<TheaterShowTime>
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
