package com.example.neoticket.view.myTickets

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
import com.example.neoticket.MainActivity
import com.example.neoticket.R
import com.example.neoticket.adapters.MyTicketsAdapter
import com.example.neoticket.databinding.FragmentMyTicketsBinding
import com.example.neoticket.model.Theater
import com.example.neoticket.model.Ticket
import com.example.neoticket.viewModel.MyTicketsViewModel


class MyTicketsFragment : Fragment() {
    private lateinit var binding: FragmentMyTicketsBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyTicketsAdapter
    private val viewModel: MyTicketsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyTicketsBinding.inflate(inflater, container, false)
        (requireActivity() as MainActivity).showBtmNav()
        recyclerView = binding.rvTickets
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        getTickets()
    }

    private fun setupAdapter() {
        adapter = MyTicketsAdapter(emptyList())
        recyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter


        adapter.setOnItemClickListener(object : MyTicketsAdapter.OnItemClickListener {
            override fun onCinemaItemClick(item: Ticket) {
                val orderId = item.tickets_theater?.get(0)?.order ?: item.tickets_concert?.get(0)?.order ?: item.tickets_movie?.get(0)?.order ?: item.tickets_sport?.get(0)?.order
                val bundle = Bundle()
                bundle.putInt("id", item.id)
                bundle.putString("type", item.type)
                if (orderId != null) {
                    bundle.putInt("orderId", orderId)
                }
                findNavController().navigate(R.id.ticketDetailsFragment, bundle)
            }
        })
    }

    private fun getTickets() {
        viewModel.ticketLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                adapter.updateData(result)
            }
        })
        viewModel.getTicketsList()
    }

}