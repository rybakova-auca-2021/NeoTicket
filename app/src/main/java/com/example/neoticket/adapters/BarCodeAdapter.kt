package com.example.neoticket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoticket.databinding.CardCodeBinding

class BarCodeAdapter(private var tickets: List<DisplayableItem>) : RecyclerView.Adapter<BarCodeAdapter.TicketViewHolder>() {

    fun updateData(newList: List<DisplayableItem>) {
        val diffResult = DiffUtil.calculateDiff(
            DisplayableItemDiffCallback(
                tickets,
                newList
            )
        )
        tickets = newList
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = CardCodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    override fun getItemCount(): Int = tickets.size

    inner class TicketViewHolder(private val binding: CardCodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: DisplayableItem) {
            binding.apply {
                when (item) {
                    is MyMovieTicketItem -> {
                        val ticket = item.movie
                        Glide.with(itemView.context).load(ticket.bar_code).into(codeImg)
                        for (seat in ticket.seats_id) {
                            binding.ticketType.text =
                                "${seat.row_number} ряд, ${seat.seat_number} место"
                        }
                    }
                    is MyConcertTicketItem -> {
                        val ticket = item.concert
                        Glide.with(itemView.context).load(ticket.bar_code).into(codeImg)
                        for (seat in ticket.seats_id) {
                            binding.ticketType.text =
                                "${seat.row_number} ряд, ${seat.seat_number} место"
                        }
                    }
                    is MySportTicketItem -> {
                        val ticket = item.sport
                        Glide.with(itemView.context).load(ticket.bar_code).into(codeImg)
                        for (seat in ticket.seats_id) {
                            binding.ticketType.text =
                                "${seat.row_number} ряд, ${seat.seat_number} место"
                        }
                    }
                    is MyTheaterTicketItem -> {
                        val ticket = item.theater
                        Glide.with(itemView.context).load(ticket.bar_code).into(codeImg)
                        for (seat in ticket.seats_id) {
                            binding.ticketType.text =
                                "${seat.row_number} ряд, ${seat.seat_number} место"
                        }
                    }
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
