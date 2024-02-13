package com.example.neoticket.adapters

import com.example.neoticket.model.Notification
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.CardNotificationBinding
import com.example.neoticket.databinding.ItemHeaderBinding
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class NotificationsAdapter(private var notifications: List<Notification>,  private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val readNotificationIds = mutableSetOf<Int>()

    private val VIEW_TYPE_DATE_HEADER = 0
    private val VIEW_TYPE_NOTIFICATION = 1
    private val PREFS_NAME = "NotificationPrefs"

    init {
        val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        readNotificationIds.addAll(prefs.getStringSet("readNotificationIds", setOf())?.map { it.toInt() } ?: emptySet())
    }

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(notifications: Notification)
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 || !areSameDate(notifications[position - 1].created_date, notifications[position].created_date) -> VIEW_TYPE_DATE_HEADER
            else -> VIEW_TYPE_NOTIFICATION
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_DATE_HEADER -> {
                val binding = ItemHeaderBinding.inflate(inflater, parent, false)
                DateHeaderViewHolder(binding)
            }
            VIEW_TYPE_NOTIFICATION -> {
                val binding = CardNotificationBinding.inflate(inflater, parent, false)
                NotificationViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DateHeaderViewHolder -> {
                val header = notifications[position].created_date
                holder.bind(header)
            }
            is NotificationViewHolder -> {
                val notification = notifications[position]
                holder.bind(notification)
            }
        }
    }

    override fun getItemCount(): Int = notifications.size

    fun updateData(newList: List<Notification>) {
        val diffResult = DiffUtil.calculateDiff(
            NotificationDiffCallback(
                notifications,
                newList
            )
        )
        notifications = newList
        diffResult.dispatchUpdatesTo(this)
    }

    private fun areSameDate(date1: String, date2: String): Boolean {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        try {
            val parsedDate1 = sdf.parse(date1)
            val parsedDate2 = sdf.parse(date2)

            // If either of the dates is null, return false
            if (parsedDate1 == null || parsedDate2 == null) {
                return false
            }

            val cal1 = Calendar.getInstance()
            val cal2 = Calendar.getInstance()
            cal1.time = parsedDate1
            cal2.time = parsedDate2

            return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                    cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
        } catch (e: ParseException) {
            e.printStackTrace()
            return false
        }
    }



    inner class DateHeaderViewHolder(private val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(header: String) {
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            try {
                val parsedDate = sdf.parse(header)
                if (parsedDate != null && isToday(parsedDate)) {
                    binding.popularHeader.text = "Сегодня"
                } else {
                    binding.popularHeader.text = header
                }
            } catch (e: ParseException) {
                e.printStackTrace()
                binding.popularHeader.text = header
            }
        }

        private fun isToday(date: Date): Boolean {
            val sdf = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            val currentDate = Calendar.getInstance().time
            return sdf.format(currentDate) == sdf.format(date)
        }
    }


    fun markNotificationAsReadById(notificationId: Int) {
        val position = notifications.indexOfFirst { it.id == notificationId }
        if (position != -1) {
            readNotificationIds.add(notificationId)
            saveReadNotificationIdsToPrefs()
            notifyItemChanged(position)
            Log.d("NotificationsAdapter", "Marked notification $notificationId as read")
        } else {
            Log.e("NotificationsAdapter", "Notification $notificationId not found")
        }
    }

    fun clearAllNotifications() {
        readNotificationIds.clear()
        saveReadNotificationIdsToPrefs()
        Log.e("NotificationsAdapter", "Notification List after deletion $readNotificationIds")

    }

    private fun saveReadNotificationIdsToPrefs() {
        val prefs: SharedPreferences.Editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit()
        prefs.putStringSet("readNotificationIds", readNotificationIds.map { it.toString() }.toSet())
        prefs.apply()
    }

    fun getReadNotificationsSize(): Int {
        Log.e("NotificationsAdapter", "Read Notification List size ${readNotificationIds.size}")
        return readNotificationIds.size
    }

    inner class NotificationViewHolder(private val binding: CardNotificationBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = notifications[position]
                    if (clickedItem is Notification) {
                        itemClickListener?.onItemClick(clickedItem)
                        markNotificationAsReadById(clickedItem.id)
                    }
                }
            }
        }

        fun bind(notification: Notification) {
            binding.notificationTitle.text = notification.title
            binding.notificationBody.text = notification.body

            if (readNotificationIds.contains(notification.id)) {
                binding.dotIndicator.visibility = View.GONE
            } else {
                binding.dotIndicator.visibility = View.VISIBLE
            }
        }
    }


    class NotificationDiffCallback(
        private val oldList: List<Notification>,
        private val newList: List<Notification>
    ) : DiffUtil.Callback() {

        override fun getOldListSize() = oldList.size

        override fun getNewListSize() = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition].id == newList[newItemPosition].id && oldList[oldItemPosition].title == newList[newItemPosition].title

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            oldList[oldItemPosition] == newList[newItemPosition]
    }
}

