package com.example.neoticket.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.CardTicketBinding
import com.example.neoticket.databinding.CardTicketConfirmBinding
import com.example.neoticket.model.MovieTicket

class TicketConfirmAdapter : RecyclerView.Adapter<TicketConfirmAdapter.TicketViewHolder>() {
    private var tickets: List<MovieTicket> = emptyList()

    fun updateData(newOrders: List<MovieTicket>) {
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
        fun bind(ticket: MovieTicket) {
            binding.ticketType.text = ticket.type.name
            binding.ticketPrice.text = ticket.type.price
            for (seat in ticket.seats) {
                binding.ticketSeat.text =
                    "${seat.row_number} ряд, ${seat.seat_number} место"
            }
        }
    }
}
