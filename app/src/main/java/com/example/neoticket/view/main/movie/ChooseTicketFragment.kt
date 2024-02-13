package com.example.neoticket.view.main.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.adapters.GridAdapterSeat
import com.example.neoticket.adapters.MovieSeatItem
import com.example.neoticket.databinding.FragmentChooseTicketBinding
import com.example.neoticket.viewModel.cinema.GetShowTimeDetailViewModel

class ChooseTicketFragment : Fragment() {
    private lateinit var binding: FragmentChooseTicketBinding
    private val viewModel: GetShowTimeDetailViewModel by viewModels()
    private lateinit var gridAdapterSeat: GridAdapterSeat

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseTicketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val showTimeId = arguments?.getInt("showTimeId")
        val movie = arguments?.getInt("movie")
        if (showTimeId != null && movie != null) {
            setupAdapter(showTimeId)
            setupNavigation(movie)
        }
        if (showTimeId != null) {
            getShowTimeDetail(showTimeId)
        }
    }

    private fun setupNavigation(id: Int) {
        binding.btnBack.setOnClickListener {
        val bundle = Bundle()
        bundle.putInt("id", id)
            findNavController().navigate(R.id.scheduleFragment, bundle)
        }
    }

    private fun setupAdapter(showTimeId: Int) {
        val gridView: GridView = binding.gridView
        gridAdapterSeat = GridAdapterSeat(requireContext(), emptyList())
        gridView.adapter = gridAdapterSeat

        gridAdapterSeat.setSeatClickListener(object : GridAdapterSeat.SeatClickListener {
            override fun onSeatClick(rowNumber: Int, seatNumber: Int, seatId: Int) {
                val bottomSheetFragment = ChooseTicketTypeFragment.newInstance(rowNumber, seatNumber, showTimeId, seatId)
                bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
            }
        })

    }

    private fun getShowTimeDetail(showTimeId: Int) {
        viewModel.showTimeDetailLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                val items = result.seats?.map { MovieSeatItem(it) }
                if (items != null) {
                    gridAdapterSeat.updateData(items)
                }
                binding.textCinema.text = result.cinema_name
                binding.movieTitle.text = result.movie_title
            }
        })
        viewModel.getMoviesShowTime(showTimeId)
    }
}
