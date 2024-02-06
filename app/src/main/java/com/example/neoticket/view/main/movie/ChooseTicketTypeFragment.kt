package com.example.neoticket.view.main.movie

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.neoticket.databinding.FragmentChooseTicketTypeBinding
import com.example.neoticket.room.MyApplication
import com.example.neoticket.room.TicketDao
import com.example.neoticket.room.TicketData
import com.example.neoticket.view.main.payment.ConfirmTicketSeatsFragment
import com.example.neoticket.viewModel.cinema.GetTicketTypesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChooseTicketTypeFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentChooseTicketTypeBinding
    private val viewModel: GetTicketTypesViewModel by viewModels()
    private val ticketDao: TicketDao by lazy {
        (requireActivity().application as MyApplication).database.ticketDao()
    }

    companion object {
        fun newInstance(rowNumber: Int, seatNumber: Int, showTime: Int, seatId: Int): ChooseTicketTypeFragment {
            val fragment = ChooseTicketTypeFragment()
            val args = Bundle()
            args.putInt("rowNumber", rowNumber)
            args.putInt("seatNumber", seatNumber)
            args.putInt("showTimeId", showTime)
            args.putInt("seatId", seatId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseTicketTypeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rowNumber = arguments?.getInt("rowNumber", 0) ?: 0
        val seatNumber = arguments?.getInt("seatNumber", 0) ?: 0
        val seatId = arguments?.getInt("seatId", 0) ?: 0
        binding.textChoosenSeat.text = "$rowNumber ряд, $seatNumber место"
        getTicketTypes()
        setupNavigation(seatId)
    }

    private fun getTicketTypes() {
        viewModel.ticketTypeLiveData.observe(viewLifecycleOwner, Observer { result ->
            if (result != null) {
                binding.childPrice.text = result[2].price
                binding.studentPrice.text = result[1].price
                binding.adultPrice.text = result[0].price
            }
        })
        viewModel.getTicketTypes()
    }


    private fun setupNavigation(seatId: Int) {
        binding.cardChild.setOnClickListener {
            handleCardClick(3, seatId)
        }
        binding.cardStudent.setOnClickListener {
            handleCardClick(2, seatId)
        }
        binding.cardAdult.setOnClickListener {
            handleCardClick(1, seatId)
        }
    }

    private fun handleCardClick(ticketType: Int, seatId: Int) {
        val rowNumber = arguments?.getInt("rowNumber", 0) ?: 0
        val seatNumber = arguments?.getInt("seatNumber", 0) ?: 0
        val showTime = arguments?.getInt("showTimeId", 0) ?: 0
        println("show time id $showTime")
        val price = when (ticketType) {
            3 -> binding.childPrice.text.toString()
            2 -> binding.studentPrice.text.toString()
            1 -> binding.adultPrice.text.toString()
            else -> "0"
        }

        CoroutineScope(Dispatchers.IO).launch {
            val ticket = TicketData(seatId, showTime, rowNumber, seatNumber, ticketType, price)
            ticketDao.addTicket(ticket)
            dismiss()
            val bottomSheetFragment = ConfirmTicketSeatsFragment.newInstance(showTime)
            bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)
        }
    }
}