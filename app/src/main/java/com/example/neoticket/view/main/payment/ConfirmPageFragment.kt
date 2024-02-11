package com.example.neoticket.view.main.payment

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoticket.R
import com.example.neoticket.adapters.ConcertTicketItem
import com.example.neoticket.adapters.MovieTicketItem
import com.example.neoticket.adapters.SportTicketItem
import com.example.neoticket.adapters.TheaterTicketItem
import com.example.neoticket.adapters.TicketConfirmAdapter
import com.example.neoticket.databinding.FragmentConfirmPageBinding
import com.example.neoticket.viewModel.cinema.GetMovieOrderDetailViewModel
import com.example.neoticket.viewModel.concerts.GetConcertOrderDetailViewModel
import com.example.neoticket.viewModel.sport.GetSportOrderDetailViewModel
import com.example.neoticket.viewModel.theater.GetTheaterOrderViewModel

class ConfirmPageFragment : Fragment() {
    private lateinit var binding: FragmentConfirmPageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TicketConfirmAdapter
    private val viewModel: GetMovieOrderDetailViewModel by viewModels()
    private val concertViewModel: GetConcertOrderDetailViewModel by viewModels()
    private val sportViewModel: GetSportOrderDetailViewModel by viewModels()
    private val theaterViewModel: GetTheaterOrderViewModel by viewModels()

    private lateinit var countDownTimer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private var timeRemainingMillis: Long = 1199000

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmPageBinding.inflate(inflater, container, false)
        recyclerView = binding.rvTickets
        countdownTextView = binding.timeLeft
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        startCountdown()
        val order = arguments?.getInt("order", 0) ?: 0
        val source = arguments?.getString("source")
        setupRecyclerView()
        when (source) {
            "concertTicket" -> getConcertOrderData(order)
            "movieTicket" -> getMovieOrderData(order)
            "sportTicket" -> getSportOrderData(order)
            "theaterTicket" -> getTheaterOrderData(order)
        }
    }

    private fun setupRecyclerView() {
        adapter = TicketConfirmAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getMovieOrderData(id: Int) {
        viewModel.orderMovieLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                binding.textCinema.text = result.movie_cinema
                binding.movieTitle.text = result.movie_title
                binding.textMovieDate.text = result.tickets_movie[0].movie_data
                binding.sumPayment.text = result.total_price
                binding.btnPay.text = "Оплатить ${result.total_price} c"
                Glide.with(binding.imageView8).load(result.movie_image).into(binding.imageView8)
                val items = result.tickets_movie?.map { MovieTicketItem(it) }
                if (items != null) {
                    adapter.updateData(items)
                }

                binding.btnPay.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("ticket_type", "Кинотеатры")
                    bundle.putString("total_price", result.total_price)
                    bundle.putInt("order", result.id)
                    findNavController().navigate(R.id.paymentFragment, bundle)
                }
            }
        })
        viewModel.getMovieOrderDetail(id)
    }

    private fun getConcertOrderData(id: Int) {
        concertViewModel.orderConcertLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                binding.textCinema.text = result.tickets_concert[0].concert_place
                binding.movieTitle.text = result.tickets_concert[0].concert_title
                binding.textMovieDate.text = result.tickets_concert[0].concert_date
                binding.sumPayment.text = result.total_price
                binding.btnPay.text = "Оплатить ${result.total_price} c"
                Glide.with(binding.imageView8).load(result.tickets_concert[0].concert_images[0].image).into(binding.imageView8)
                val items = result.tickets_concert?.map { ConcertTicketItem(it) }
                if (items != null) {
                    adapter.updateData(items)
                }

                binding.btnPay.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("ticket_type", "Концерты")
                    bundle.putString("total_price", result.total_price)
                    bundle.putInt("order", result.id)
                    findNavController().navigate(R.id.paymentFragment, bundle)
                }
            }
        })
        concertViewModel.getConcertOrderDetail(id)
    }

    private fun getSportOrderData(id: Int) {
        sportViewModel.orderSportLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                binding.textCinema.text = result.tickets_sport[0].sport_place
                binding.movieTitle.text = result.tickets_sport[0].sport_title
                binding.textMovieDate.text = result.tickets_sport[0].sport_date
                binding.sumPayment.text = result.total_price
                binding.btnPay.text = "Оплатить ${result.total_price} c"
                Glide.with(binding.imageView8).load(result.tickets_sport[0].sport_images[0].image).into(binding.imageView8)
                val items = result.tickets_sport?.map { SportTicketItem(it) }
                if (items != null) {
                    adapter.updateData(items)
                }

                binding.btnPay.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("ticket_type", "Спорт")
                    bundle.putString("total_price", result.total_price)
                    bundle.putInt("order", result.id)
                    findNavController().navigate(R.id.paymentFragment, bundle)
                }
            }
        })
        sportViewModel.getSportOrderDetail(id)
    }
    private fun getTheaterOrderData(id: Int) {
        theaterViewModel.orderTheaterLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                binding.textCinema.text = result.tickets_theater[0].theater_place
                binding.movieTitle.text = result.tickets_theater[0].theater_title
                binding.textMovieDate.text = result.tickets_theater[0].theater_date
                binding.sumPayment.text = result.total_price
                binding.btnPay.text = "Оплатить ${result.total_price} c"
                Glide.with(binding.imageView8).load(result.tickets_theater[0].theater_images[0].image).into(binding.imageView8)
                val items = result.tickets_theater?.map { TheaterTicketItem(it) }
                if (items != null) {
                    adapter.updateData(items)
                }

                binding.btnPay.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("ticket_type", "Театры")
                    bundle.putString("total_price", result.total_price)
                    bundle.putInt("order", result.id)
                    findNavController().navigate(R.id.paymentFragment, bundle)
                }
            }
        })
        theaterViewModel.getTheaterOrderDetail(id)
    }

    private fun startCountdown() {
        countDownTimer = object : CountDownTimer(timeRemainingMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemainingMillis = millisUntilFinished
                updateCountdownText()
            }

            override fun onFinish() {
                countdownTextView.text = "00:00"
                Toast.makeText(requireContext(), "Оплата больше недействительна", Toast.LENGTH_SHORT).show()
            }
        }.start()
    }

    private fun updateCountdownText() {
        val minutes = (timeRemainingMillis / 1000) / 60
        val seconds = (timeRemainingMillis / 1000) % 60
        countdownTextView.text = String.format("%02d:%02d", minutes, seconds)
    }
}