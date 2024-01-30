package com.example.neoticket.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.neoticket.R

class GridAdapterSeat(private val context: Context) : BaseAdapter() {

    private val seatNumbers = arrayOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12",
    )

    override fun getCount(): Int {
        return seatNumbers.size
    }

    override fun getItem(position: Int): Any {
        return seatNumbers[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val seatView: View

        if (convertView == null) {
            seatView = inflater.inflate(R.layout.grid_item, null)
            val imageViewSeat = seatView.findViewById<ImageView>(R.id.img_seat)
            val textViewSeat = seatView.findViewById<TextView>(R.id.seat_number)

            // Set values for the seat
            imageViewSeat.setImageResource(R.drawable.rounded_rectangle)
            textViewSeat.text = seatNumbers[position]
        } else {
            seatView = convertView
        }

        return seatView
    }
}
