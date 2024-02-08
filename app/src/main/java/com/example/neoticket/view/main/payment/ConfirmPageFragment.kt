package com.example.neoticket.view.main.payment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoticket.R
import com.example.neoticket.adapters.ConcertTicketItem
import com.example.neoticket.adapters.MovieTicketItem
import com.example.neoticket.adapters.TicketAdapter
import com.example.neoticket.adapters.TicketConfirmAdapter
import com.example.neoticket.databinding.FragmentConfirmPageBinding
import com.example.neoticket.viewModel.cinema.GetMovieOrderDetailViewModel
import com.example.neoticket.viewModel.concerts.GetConcertOrderDetailViewModel

class ConfirmPageFragment : Fragment() {
    private lateinit var binding: FragmentConfirmPageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TicketConfirmAdapter
    private val viewModel: GetMovieOrderDetailViewModel by viewModels()
    private val concertViewModel: GetConcertOrderDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentConfirmPageBinding.inflate(inflater, container, false)
        recyclerView = binding.rvTickets
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val order = arguments?.getInt("order", 0) ?: 0
        val source = arguments?.getString("source")
        setupRecyclerView()
        when (source) {
            "concertTicket" -> getConcertOrderData(order)
            "movieTicket" -> getMovieOrderData(order)

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
                binding.textMovieDate.text = result.tickets[0].movie_data
                binding.sumPayment.text = result.total_price
                binding.btnPay.text = "Оплатить ${result.total_price} c"
                Glide.with(binding.imageView8).load(result.movie_image).into(binding.imageView8)
                val items = result.tickets?.map { MovieTicketItem(it) }
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
                binding.textCinema.text = result.tickets[0].concert_place
                binding.movieTitle.text = result.tickets[0].concert_title
                binding.textMovieDate.text = result.tickets[0].concert_date
                binding.sumPayment.text = result.total_price
                binding.btnPay.text = "Оплатить ${result.total_price} c"
                Glide.with(binding.imageView8).load(result.tickets[0].concert_images[0].image).into(binding.imageView8)
                val items = result.tickets?.map { ConcertTicketItem(it) }
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

}