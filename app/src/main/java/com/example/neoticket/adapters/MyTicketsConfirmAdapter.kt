package com.example.neoticket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.CardTicketConfirmBinding
import com.example.neoticket.model.ConcertTicketDetail
import com.example.neoticket.model.MovieTicketDetail
import com.example.neoticket.model.SportTicketDetail
import com.example.neoticket.model.TheaterTicketDetail


data class MyConcertTicketItem(val concert: ConcertTicketDetail) : DisplayableItem {
    override val viewType: Int = 1
}

data class MyMovieTicketItem(val movie: MovieTicketDetail) : DisplayableItem {
    override val viewType: Int = 2
}


data class MySportTicketItem(val sport: SportTicketDetail) : DisplayableItem {
    override val viewType: Int = 3
}

data class MyTheaterTicketItem(val theater: TheaterTicketDetail) : DisplayableItem {
    override val viewType: Int = 3
}

class MyTicketsConfirmAdapter(private var tickets: List<DisplayableItem>) : RecyclerView.Adapter<MyTicketsConfirmAdapter.TicketViewHolder>() {

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
                is MyMovieTicketItem -> {
                    val ticket = item.movie
                    binding.ticketType.text = ticket.type?.name
                    binding.ticketPrice.text = ticket.type?.price
                    for (seat in ticket.seats_id) {
                        binding.ticketSeat.text =
                            "${seat.row_number} ряд, ${seat.seat_number} место"
                    }
                }
                is MyConcertTicketItem -> {
                    val ticket = item.concert
                    for (seat in ticket.seats_id) {
                        binding.ticketSeat.text =
                            "${seat.row_number} ряд, ${seat.seat_number} место"
                    }
                }
                is MySportTicketItem -> {
                    val ticket = item.sport
                    for (seat in ticket.seats_id) {
                        binding.ticketSeat.text =
                            "${seat.row_number} ряд, ${seat.seat_number} место"
                        binding.ticketPrice.text = seat.price
                    }
                }
                is MyTheaterTicketItem -> {
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
