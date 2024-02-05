package com.example.neoticket.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.databinding.CardShowTimeBinding
import com.example.neoticket.model.StartTime

class ShowTimeAdapter(private val showTimes: List<StartTime>, private val clickListener: (StartTime) -> Unit) :
    RecyclerView.Adapter<ShowTimeAdapter.ShowTimeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowTimeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CardShowTimeBinding.inflate(inflater, parent, false)

        return ShowTimeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ShowTimeViewHolder, position: Int) {
        val showTime = showTimes[position]
        holder.bind(showTime)
    }

    override fun getItemCount(): Int {
        return showTimes.size
    }

    inner class ShowTimeViewHolder(private val binding: CardShowTimeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = showTimes[position]
                    clickListener.invoke(clickedItem)
                }
            }
        }

        fun bind(showTime: StartTime) {
            binding.textStartTime.text = showTime.time
            binding.textPrice.text = showTime.base_ticket_price
        }
    }
}
