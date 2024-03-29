package com.example.neoticket.view.main.concerts

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Identity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.neoticket.R
import com.example.neoticket.databinding.FragmentSectorDetailBinding
import com.example.neoticket.viewModel.concerts.GetSectionDetailViewModel
import com.example.neoticket.viewModel.sport.GetSportSectionDetailViewModel
import com.example.neoticket.viewModel.theater.TheaterSectionDetailViewModel


class SectorDialogFragment : DialogFragment() {
    private lateinit var binding: FragmentSectorDetailBinding
    private val viewModel: GetSectionDetailViewModel by viewModels()
    private val sportViewModel: GetSportSectionDetailViewModel by viewModels()
    private val theaterViewModel: TheaterSectionDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSectorDetailBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val title = arguments?.getString("title")
        val theaterId = arguments?.getInt("theaterId")
        val source = arguments?.getString("source")
        val id = arguments?.getInt("id")
        if (id != null && source != null) {
            when(source) {
                "SportSector" -> setSportData(id)
                "ConcertSector" -> setConcertData(id)
                "TheaterSector" -> theaterId?.let { setTheaterData(id, it) }
            }
        }
    }

    private fun setConcertData(id: Int) {
        viewModel.getSectionDetail(id)
        viewModel.sectionLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.sectorTitle.text = result.name
                binding.ticketPrice.text = result.seats[0].price
                binding.btnChooseTickets.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("id", result.id)
                    bundle.putInt("showTimeId", result.show_times)
                    findNavController().navigate(R.id.concertChooseTicketFragment, bundle)
                    dismiss()
                }
            }
        }
    }

    private fun setSportData(id: Int) {
        sportViewModel.getSectionDetail(id)
        sportViewModel.sectionLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.sectorTitle.text = result.name
                binding.ticketPrice.text = result.sport_seats[0].price
                binding.btnChooseTickets.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("id", result.id)
                    bundle.putInt("showTimeId", result.show_time)
                    findNavController().navigate(R.id.sportChooseTicketFragment, bundle)
                    dismiss()
                }
            }
        }
    }
    private fun setTheaterData(id: Int, theaterId: Int) {
        theaterViewModel.getSectionDetail(id)
        theaterViewModel.sectionLiveData.observe(viewLifecycleOwner) { result ->
            if (result != null) {
                binding.sectorTitle.text = result.name
//                binding.ticketPrice.text = result.seats[0].price
                binding.btnChooseTickets.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putInt("id", result.id)
                    bundle.putInt("theaterId", theaterId)
                    bundle.putInt("showTimeId", result.show_time)
                    findNavController().navigate(R.id.theaterChooseTicketsFragment, bundle)
                    dismiss()
                }
            }
        }
    }
}