package com.example.neoticket.view.myTickets

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.neoticket.adapters.CodeAdapter
import com.example.neoticket.adapters.DisplayableItem
import com.example.neoticket.adapters.MyConcertTicketItem
import com.example.neoticket.adapters.MyMovieTicketItem
import com.example.neoticket.adapters.MySportTicketItem
import com.example.neoticket.adapters.MyTheaterTicketItem
import com.example.neoticket.databinding.FragmentTicketQrBinding
import com.example.neoticket.viewModel.MyTicketsViewModel
import java.io.File
import java.io.IOException

class TicketQrFragment : Fragment() {
    private lateinit var binding: FragmentTicketQrBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CodeAdapter
    private val viewModel: MyTicketsViewModel by viewModels()

    companion object {
        fun newInstance(id: Int): TicketQrFragment {
            val fragment = TicketQrFragment()
            val args = Bundle()
            args.putInt("id", id)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTicketQrBinding.inflate(inflater, container, false)
        recyclerView = binding.rvCode
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        setupAdapter()
        if (id != null) {
            setupTicketData(id)
        }
    }

    private fun setupAdapter() {
        adapter = CodeAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    private fun setupTicketData(id: Int) {
        viewModel.getTicketsList()
        viewModel.ticketLiveData.observe(viewLifecycleOwner, Observer { tickets ->
            tickets?.let { ticketList ->
                val ticket = ticketList.find { it.id == id }
                ticket?.let { foundTicket ->
                    val items = foundTicket.tickets_concert?.map { MyConcertTicketItem(it) }
                        ?: foundTicket.tickets_movie?.map { MyMovieTicketItem(it) }
                        ?: foundTicket.tickets_sport?.map { MySportTicketItem(it) }
                        ?: foundTicket.tickets_theater?.map { MyTheaterTicketItem(it) }
                    if (items != null) {
                        adapter.updateData(items)
                    }
                    binding.btnDownload.setOnClickListener {
                        if (items != null) {
                            downloadTicket(items)
                        }
                    }

                } ?: run {
                    println("Ticket with ID $id not found")
                }
            }
        })
    }

    private fun downloadTicket(items: List<DisplayableItem>) {
        for (item in items) {
            val ticketUrl = when (item) {
                is MyMovieTicketItem -> item.movie.qr_code
                is MyConcertTicketItem -> item.concert.qr_code
                is MySportTicketItem -> item.sport.qr_code
                is MyTheaterTicketItem -> item.theater.qr_code
                else -> null
            }

            ticketUrl?.let { url ->
                val sourceFile = File(url)
                val destinationFile = File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    "ticket_qr_code.jpg"
                )

                try {
                    sourceFile.copyTo(destinationFile, overwrite = true)
                    Toast.makeText(
                        requireContext(),
                        "Ticket QR code downloaded successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                } catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(
                        requireContext(),
                        "Failed to download ticket QR code: ${e.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } ?: run {
                Toast.makeText(
                    requireContext(),
                    "Ticket QR code not available for download",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}