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
import com.example.neoticket.model.Seat

data class MovieSeatItem(val movieSeat: MovieSeat) : DisplayableItem {
    override val viewType: Int = 1
}

data class SeatItem(val seat: Seat) : DisplayableItem {
    override val viewType: Int = 2
}


class GridAdapterSeat(private val context: Context, private var seats: List<DisplayableItem>) : BaseAdapter() {

    private var seatClickListener: SeatClickListener? = null

    interface SeatClickListener {
        fun onSeatClick(rowNumber: Int, seatNumber: Int, seatId: Int)
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

    fun updateData(newSeats: List<DisplayableItem>) {
        seats = newSeats
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val seatView: View
        val item = getItem(position)

        if (convertView == null) {
            seatView = inflater.inflate(R.layout.grid_item, null)
        } else {
            seatView = convertView
        }

        val imageViewSeat = seatView.findViewById<ImageView>(R.id.img_seat)
        val textViewSeat = seatView.findViewById<TextView>(R.id.seat_number)

        when (item) {
            is MovieSeatItem -> {
                val movieSeat = item.movieSeat
                setupSeatView(seatView, movieSeat.is_available, movieSeat.seat_number, movieSeat.row_number, null, movieSeat.id, imageViewSeat, textViewSeat)
            }
            is SeatItem -> {
                val seat = item.seat
                setupSeatView(seatView, seat.is_available, seat.seat_number, seat.row_number, seat.price, seat.id, imageViewSeat, textViewSeat)
            }
            else -> throw IllegalArgumentException("Unsupported item type")
        }

        return seatView
    }

    private fun setupSeatView(seatView: View, isAvailable: Boolean, seatNumber: Int, rowNumber: Int, price: String?, seatId: Int, imageViewSeat: ImageView, textViewSeat: TextView) {
        if (isAvailable) {
            textViewSeat.text = seatNumber.toString()
            imageViewSeat.setImageResource(R.drawable.rounded_rectangle)
            seatView.setOnClickListener {
                seatClickListener?.onSeatClick(rowNumber, seatNumber, seatId)
            }
        } else {
            textViewSeat.text = "x"
            imageViewSeat.setImageResource(R.drawable.rounded_drawable_seat_grey)
        }
    }

}
