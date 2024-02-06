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
import com.example.neoticket.adapters.TicketAdapter
import com.example.neoticket.adapters.TicketConfirmAdapter
import com.example.neoticket.databinding.FragmentConfirmPageBinding
import com.example.neoticket.viewModel.cinema.GetMovieOrderDetailViewModel

class ConfirmPageFragment : Fragment() {
    private lateinit var binding: FragmentConfirmPageBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: TicketConfirmAdapter
    private val viewModel: GetMovieOrderDetailViewModel by viewModels()

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
        setupRecyclerView()
        getOrderData(order)
    }

    private fun setupRecyclerView() {
        adapter = TicketConfirmAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getOrderData(id: Int) {
        viewModel.orderMovieLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                binding.textCinema.text = result.movie_cinema
                binding.movieTitle.text = result.movie_title
                binding.textMovieDate.text = result.tickets[0].movie_data
                binding.sumPayment.text = result.total_price
                binding.btnPay.text = "Оплатить ${result.total_price} c"
                Glide.with(binding.imageView8).load(result.movie_image).into(binding.imageView8)
                adapter.updateData(result.tickets)

                binding.btnPay.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("ticket_type", "Кинотеатры")
                    bundle.putString("total_price", result.total_price)
                    bundle.putInt("movie_order", result.id)
                    findNavController().navigate(R.id.paymentFragment, bundle)
                }
            }
        })
        viewModel.getMovieOrderDetail(id)
    }

}