package com.example.neoticket.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.CardTicketBinding
import com.example.neoticket.model.Cinema
import com.example.neoticket.room.ConcertTicketData
import com.example.neoticket.room.TicketData

class ConcertTicketAdapter : RecyclerView.Adapter<ConcertTicketAdapter.TicketViewHolder>() {
    private var tickets: List<ConcertTicketData> = emptyList()

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: ConcertTicketData)
    }

    fun updateData(newOrders: List<ConcertTicketData>) {
        tickets = newOrders
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketViewHolder {
        val binding = CardTicketBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TicketViewHolder, position: Int) {
        holder.bind(tickets[position])
    }

    override fun getItemCount(): Int = tickets.size

    inner class TicketViewHolder(private val binding: CardTicketBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnClose.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = tickets[position]
                    itemClickListener?.onItemClick(clickedItem)
                }
            }
        }

        fun bind(ticket: ConcertTicketData) {
            binding.ticketSeat.text = "${ticket.rowNumber} ряд, ${ticket.seatNumber} место"
            binding.ticketType.visibility = View.GONE
        }
    }
}
