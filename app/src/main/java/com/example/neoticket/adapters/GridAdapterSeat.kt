package com.example.neoticket.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.neoticket.R
import com.example.neoticket.model.MovieSeat

class GridAdapterSeat(private val context: Context, private var seats: List<MovieSeat>) : BaseAdapter() {

    private var seatClickListener: SeatClickListener? = null

    interface SeatClickListener {
        fun onSeatClick(rowNumber: Int, seatNumber: Int)
    }

    fun setSeatClickListener(listener: SeatClickListener) {
        seatClickListener = listener
    }


    override fun getCount(): Int {
        return seats.size
    }

    override fun getItem(position: Int): Any {
        return seats[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun updateData(newSeats: List<MovieSeat>) {
        seats = newSeats
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val seatView: View

        val seat = getItem(position) as MovieSeat

        if (convertView == null) {
            seatView = inflater.inflate(R.layout.grid_item, null)
            val imageViewSeat = seatView.findViewById<ImageView>(R.id.img_seat)
            val textViewSeat = seatView.findViewById<TextView>(R.id.seat_number)

            if (seat.is_available) {
                textViewSeat.text = seat.seat_number.toString()
                imageViewSeat.setImageResource(R.drawable.rounded_rectangle)
                seatView.setOnClickListener {
                    seatClickListener?.onSeatClick(seat.row_number, seat.seat_number)
                }
            } else {
                textViewSeat.text = "x"
                imageViewSeat.setImageResource(R.drawable.rounded_drawable_seat_grey)
            }
        } else {
            seatView = convertView
        }

        return seatView
    }

}

