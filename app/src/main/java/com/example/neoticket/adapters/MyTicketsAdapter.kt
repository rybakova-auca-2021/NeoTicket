package com.example.neoticket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.neoticket.databinding.CardMyTicketBinding
import com.example.neoticket.model.Ticket

class MyTicketsAdapter(private var tickets: List<Ticket>) : RecyclerView.Adapter<MyTicketsAdapter.TicketViewHolder>() {

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onCinemaItemClick(item: Ticket)
    }

    fun updateData(newList: List<Ticket>) {
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
        val binding = CardMyTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    override fun getItemCount(): Int = tickets.size

    inner class TicketViewHolder(private val binding: CardMyTicketBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = tickets[position]
                    itemClickListener?.onCinemaItemClick(clickedItem)
                }
            }
        }
        @SuppressLint("SetTextI18n")
        fun bind(ticket: Ticket) {
            binding.apply {
                ticketTitle.text = ticket.concert_title ?: ticket.theater_title ?: ticket.movie_title ?: ticket.sport_title ?: ""
                ticketPlace.text = ticket.concert_place ?: ticket.theater_place ?: ticket.sport_place ?: ""
                ticketDate.text = ticket.concert_date ?: ticket.theater_date ?: ticket.movie_date ?: ticket.sport_date ?: ""
                ticketHour.text = "18:15" // Assuming you set this value statically, replace it with actual data
                numOfTickets.text = ticket.total_tickets.toString()
                // Set image based on type of ticket
                when (ticket.type) {
                    "Concert" -> Glide.with(itemView.context).load(ticket.concert_image).transform(CenterCrop(), RoundedCorners(50)).into(ticketImg)
                    "Theater" -> Glide.with(itemView.context).load(ticket.theater_image).transform(CenterCrop(), RoundedCorners(50)).into(ticketImg)
                    "Movie" -> Glide.with(itemView.context).load(ticket.movie_image).transform(CenterCrop(), RoundedCorners(50)).into(ticketImg)
                    "Sport" -> Glide.with(itemView.context).load(ticket.sport_image).transform(CenterCrop(), RoundedCorners(50)).into(ticketImg)
                }
            }
        }
    }

    class DisplayableItemDiffCallback(
        private val oldList: List<Ticket>,
        private val newList: List<Ticket>
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
