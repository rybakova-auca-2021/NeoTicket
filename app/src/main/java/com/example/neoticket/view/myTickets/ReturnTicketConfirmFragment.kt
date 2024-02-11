package com.example.neoticket.view.myTickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.neoticket.R
import com.example.neoticket.Utils.Util
import com.example.neoticket.adapters.MyConcertTicketItem
import com.example.neoticket.adapters.MyMovieTicketItem
import com.example.neoticket.adapters.MySportTicketItem
import com.example.neoticket.adapters.MyTheaterTicketItem
import com.example.neoticket.adapters.MyTicketsConfirmAdapter
import com.example.neoticket.databinding.FragmentReturnTicketConfirmBinding
import com.example.neoticket.viewModel.MyTicketsViewModel
import com.example.neoticket.viewModel.cinema.RefundMovieViewModel
import com.example.neoticket.viewModel.concerts.ConcertOrderRefundViewModel
import com.example.neoticket.viewModel.sport.SportOrderRefundViewModel
import com.example.neoticket.viewModel.theater.CheckoutTheaterOrderViewModel
import com.example.neoticket.viewModel.theater.TheaterOrderRefundViewModel

class ReturnTicketConfirmFragment : Fragment() {
    private lateinit var binding: FragmentReturnTicketConfirmBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyTicketsConfirmAdapter
    private val viewModel: MyTicketsViewModel by viewModels()
    private val checkOutViewModel: RefundMovieViewModel by viewModels()
    private val checkOutConcertViewModel: ConcertOrderRefundViewModel by viewModels()
    private val checkOutSportViewModel: SportOrderRefundViewModel by viewModels()
    private val checkOutTheaterViewModel: TheaterOrderRefundViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReturnTicketConfirmBinding.inflate(inflater, container, false)
        recyclerView = binding.rvTickets
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val orderId = arguments?.getInt("orderId")
        val id = arguments?.getInt("id")
        val type = arguments?.getString("type")
        setupRecyclerView()
        if (id != null) {
            setupTicketData(id)
        }
        if (orderId != null && type != null) {
            setupNavigation(type, orderId)
        }
    }

    private fun setupRecyclerView() {
        adapter = MyTicketsConfirmAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupNavigation(type: String, order: Int) {
        binding.btnConfirm.setOnClickListener {
            when(type) {
                "Movie" -> checkoutMovieOrder(order)
                "Concert" -> checkoutConcertOrder(order)
                "Sport" -> checkoutSportOrder(order)
                "Theater" -> checkoutTheaterOrder(order)
            }
        }

    }

    private fun checkoutMovieOrder(order: Int) {
        checkOutViewModel.orderMovieLiveData.observe(viewLifecycleOwner, Observer {
                result ->
            if (result != null) {
                findNavController().navigate(R.id.ticketConfirmedFragment)
            }
        })
        Util.id?.let { checkOutViewModel.orderMovie(it, order) }
    }

    private fun checkoutConcertOrder(order: Int) {
        checkOutConcertViewModel.orderConcertLiveData.observe(viewLifecycleOwner, Observer {
                result ->
            if (result != null) {
                findNavController().navigate(R.id.ticketConfirmedFragment)
            }
        })
        Util.id?.let { checkOutConcertViewModel.orderConcert(it, order) }
    }
    private fun checkoutSportOrder(order: Int) {
        checkOutSportViewModel.orderSportLiveData.observe(viewLifecycleOwner, Observer {
                result ->
            if (result != null) {
                findNavController().navigate(R.id.ticketConfirmedFragment)
            }
        })
        Util.id?.let { checkOutSportViewModel.orderSport(it, order) }
    }

    private fun checkoutTheaterOrder(order: Int) {
        checkOutTheaterViewModel.orderTheaterLiveData.observe(viewLifecycleOwner, Observer {
                result ->
            if (result != null) {
                findNavController().navigate(R.id.ticketConfirmedFragment)
            }
        })
        Util.id?.let { checkOutTheaterViewModel.orderTheater(it, order) }
    }
    private fun setupTicketData(id: Int) {
        viewModel.getTicketsList()
        viewModel.ticketLiveData.observe(viewLifecycleOwner, Observer { tickets ->
            tickets?.let { ticketList ->
                val ticket = ticketList.find { it.id == id }
                ticket?.let { foundTicket ->
                    println("Found ticket: $foundTicket")

                    val imageResource = when (foundTicket.type) {
                        "Concert" -> foundTicket.concert_image
                        "Theater" -> foundTicket.theater_image
                        "Movie" -> foundTicket.movie_image
                        "Sport" -> foundTicket.sport_image
                        else -> null
                    }

                    imageResource?.let {
                        Glide.with(binding.imageView8)
                            .load(it)
                            .transform(CenterCrop(), RoundedCorners(50))
                            .into(binding.imageView8)
                    }

                    binding.movieTitle.text = foundTicket.concert_title
                        ?: foundTicket.theater_title
                                ?: foundTicket.movie_title
                                ?: foundTicket.sport_title
                                ?: ""

                    binding.textMovieDate.text = foundTicket.concert_date
                        ?: foundTicket.theater_date
                                ?: foundTicket.movie_date
                                ?: foundTicket.sport_date
                                ?: ""

                    binding.textCinema.text = foundTicket.concert_place
                        ?: foundTicket.theater_place
                                ?: foundTicket.sport_place
                                ?: "Кинотеатр"

                    val items = foundTicket.tickets_concert?.map { MyConcertTicketItem(it) }
                        ?: foundTicket.tickets_movie?.map { MyMovieTicketItem(it) }
                        ?: foundTicket.tickets_sport?.map { MySportTicketItem(it) }
                        ?: foundTicket.tickets_theater?.map { MyTheaterTicketItem(it) }
                    if (items != null) {
                        adapter.updateData(items)
                    }

                    binding.sumPayment.text = foundTicket.total_price
                } ?: run {
                    println("Ticket with ID $id not found")
                }
            }
        })
    }
}