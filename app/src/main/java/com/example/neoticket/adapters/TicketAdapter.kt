package com.example.neoticket.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.CardTicketBinding
import com.example.neoticket.model.Cinema
import com.example.neoticket.room.TicketData

class TicketAdapter : RecyclerView.Adapter<TicketAdapter.TicketViewHolder>() {
    private var tickets: List<TicketData> = emptyList()

    private var itemClickListener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    interface OnItemClickListener {
        fun onItemClick(item: TicketData)
    }

    fun updateData(newOrders: List<TicketData>) {
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

        fun bind(ticket: TicketData) {
            binding.ticketSeat.text = "${ticket.rowNumber} ряд, ${ticket.seatNumber} место"
            binding.ticketType.text = "${ticket.ticketType},  ${ticket.price}"
        }
    }
}
