package com.example.neoticket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.CardTicketConfirmBinding
import com.example.neoticket.model.ConcertTicket
import com.example.neoticket.model.MovieTicket
import com.example.neoticket.model.SportTicket
import com.example.neoticket.model.TheaterTicket

interface DisplayableItem {
    val viewType: Int
}

data class ConcertTicketItem(val concert: ConcertTicket) : DisplayableItem {
    override val viewType: Int = 1
}

data class MovieTicketItem(val movie: MovieTicket) : DisplayableItem {
    override val viewType: Int = 2
}


data class SportTicketItem(val sport: SportTicket) : DisplayableItem {
    override val viewType: Int = 3
}

data class TheaterTicketItem(val theater: TheaterTicket) : DisplayableItem {
    override val viewType: Int = 3
}

class TicketConfirmAdapter(private var tickets: List<DisplayableItem>) : RecyclerView.Adapter<TicketConfirmAdapter.TicketViewHolder>() {

    fun updateData(newOrders: List<DisplayableItem>) {
        tickets = newOrders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = CardTicketConfirmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    override fun getItemCount(): Int = tickets.size

    inner class TicketViewHolder(private val binding: CardTicketConfirmBinding) : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: DisplayableItem) {
            when (item) {
                is MovieTicketItem -> {
                    val ticket = item.movie
                    binding.ticketType.text = ticket.type.name
                    binding.ticketPrice.text = ticket.type.price
                    for (seat in ticket.seats_id) {
                        binding.ticketSeat.text =
                            "${seat.row_number} ряд, ${seat.seat_number} место"
                    }
                }
                is ConcertTicketItem -> {
                    val ticket = item.concert
                    for (seat in ticket.seats_id) {
                        binding.ticketSeat.text =
                            "${seat.row_number} ряд, ${seat.seat_number} место"
                    }
                }
                is SportTicketItem -> {
                    val ticket = item.sport
                    for (seat in ticket.seats_id) {
                        binding.ticketSeat.text =
                            "${seat.row_number} ряд, ${seat.seat_number} место"
                        binding.ticketPrice.text = seat.price
                    }
                }
                is TheaterTicketItem -> {
                    val ticket = item.theater
                    for (seat in ticket.seats_id) {
                        binding.ticketSeat.text =
                            "${seat.row_number} ряд, ${seat.seat_number} место"
                        binding.ticketPrice.text = seat.price
                    }
                }
            }
        }
    }
}
