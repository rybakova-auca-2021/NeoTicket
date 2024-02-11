package com.example.neoticket.view.myTickets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.example.neoticket.adapters.MyConcertTicketItem
import com.example.neoticket.adapters.MyMovieTicketItem
import com.example.neoticket.adapters.MySportTicketItem
import com.example.neoticket.adapters.MyTheaterTicketItem
import com.example.neoticket.adapters.MyTicketsConfirmAdapter
import com.example.neoticket.databinding.FragmentTicketDetailsBinding
import com.example.neoticket.viewModel.MyTicketsViewModel

class TicketDetailsFragment : Fragment() {
    private lateinit var binding: FragmentTicketDetailsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyTicketsConfirmAdapter
    private val viewModel: MyTicketsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketDetailsBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView3
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        val orderId = arguments?.getInt("orderId")
        val type = arguments?.getString("type")
        setupRecyclerView()
        if (orderId != null && type != null) {
            setupNavigation(orderId, type)
        }
        if (id != null) {
            setupTicketData(id)
        }
    }

    private fun setupRecyclerView() {
        adapter = MyTicketsConfirmAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
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
                        Glide.with(binding.imageView12)
                            .load(it)
                            .transform(CenterCrop(), RoundedCorners(50))
                            .into(binding.imageView12)
                    }

                    binding.textTicketTitle.text = foundTicket.concert_title
                        ?: foundTicket.theater_title
                                ?: foundTicket.movie_title
                                ?: foundTicket.sport_title
                                ?: ""

                    binding.textTicketTime.text = foundTicket.concert_date
                        ?: foundTicket.theater_date
                                ?: foundTicket.movie_date
                                ?: foundTicket.sport_date
                                ?: ""

                    binding.textTicketPlace.text = foundTicket.concert_place
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

                    binding.numOfTickets.text = "${foundTicket.total_tickets} билет"
                    binding.numOfOrder.text = foundTicket.order_number
                } ?: run {
                    println("Ticket with ID $id not found")
                }
            }
        })
    }


    private fun setupNavigation(id: Int, type: String) {
        binding.btnReturnTicket.setOnClickListener {
            val bottomSheetFragment = ReturnTicketConfirmDialogFragment()
            val bundle = Bundle().apply {
                putInt("order_id", id)
                putString("type", type)
            }
            bottomSheetFragment.arguments = bundle
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }

}