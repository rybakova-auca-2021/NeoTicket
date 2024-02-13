package com.example.neoticket.view.main.theaters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.adapters.GridAdapterSeat
import com.example.neoticket.adapters.SeatItem
import com.example.neoticket.databinding.FragmentTheaterChooseTicketsBinding
import com.example.neoticket.room.ConcertTicketData
import com.example.neoticket.room.MyApplication
import com.example.neoticket.room.TicketDao
import com.example.neoticket.viewModel.theater.TheaterSectionDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TheaterChooseTicketsFragment : Fragment() {
    private lateinit var binding: FragmentTheaterChooseTicketsBinding
    private val viewModel: TheaterSectionDetailViewModel by viewModels()
    private lateinit var gridAdapterSeat: GridAdapterSeat
    private val ticketDao: TicketDao by lazy {
        (requireActivity().application as MyApplication).database.ticketDao()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTheaterChooseTicketsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = arguments?.getInt("id")
        val theaterId = arguments?.getInt("theaterId")
        println("id $id")
        if (theaterId != null) {
            setupNavigation(theaterId)
        }
        val showTimeId = arguments?.getInt("showTimeId")
        if (id != null && showTimeId != null) {
            setupAdapter(showTimeId)
            setData(id)
        }
    }

    private fun setupNavigation(theaterId: Int) {
        binding.btnBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putInt("id", theaterId)
            findNavController().navigate(R.id.theaterChooseSectorFragment, bundle)
        }
    }

    private fun setupAdapter(showTimeId: Int) {
        val gridView: GridView = binding.gridView
        gridAdapterSeat = GridAdapterSeat(requireContext(), emptyList())
        gridView.adapter = gridAdapterSeat

        gridAdapterSeat.setSeatClickListener(object : GridAdapterSeat.SeatClickListener {
            override fun onSeatClick(rowNumber: Int, seatNumber: Int, seatId: Int) {
                CoroutineScope(Dispatchers.IO).launch {
                    val ticket = ConcertTicketData(seatId, showTimeId, rowNumber, seatNumber)
                    ticketDao.addConcertTicket(ticket)
                    val bottomSheetFragment = ConfirmTheaterTicketSeatsFragment.newInstance(showTimeId)
                    bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
                }
            }
        })
    }

    private fun setData(id: Int) {
        viewModel.getSectionDetail(id)
        viewModel.sectionLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                val items = result.theater_seats.map { SeatItem(it) }
                gridAdapterSeat.updateData(items)
                binding.artist.text = result.theater_name
                binding.place.text = result.place
                binding.textRoomNumber.text = result.name
            }
        }
    }
}